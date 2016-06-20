/* Jerrylab, Copyright (c) 2010-2016 Jost Neigenfind  <jostie@gmx.de>
 * 
 * Jerrylab - A free Java alternative to Tomlab
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package instance;

import ilp.*;
import crnt.*;
import miscellaneous.*;

import java.util.*;
import java.text.*;

public class GetShortestPathway {
	private Constraints constraints;
	private ReactionNetwork reaction_network;
	private int[] forced_reactions;
	
	public GetShortestPathway(ReactionNetwork reaction_network, Constraints constraints, int[] forced_reactions) throws Exception{
		this.reaction_network = reaction_network;
		this.constraints = constraints;
		this.forced_reactions = forced_reactions;
	}
	
	public void makeConstraints() throws Exception{
		MyMatrix<Double,Species,Reaction> N = this.reaction_network.getSimpleNMatrix();	// get stoichiometric matrix
		int m = N.getHeight();															// get number of species
		int r = N.getWidth();															// get number of reactions
		
		this.constraints.addVariables("v", new int[]{r}, Type.REAL);					// create flux vector variables
		this.constraints.addVariables("b", new int[]{r}, Type.BINARY);					// create binary on/off-variables
		this.constraints.addVariables("t", new int[]{2}, Type.REAL);
		
		// create objective function
		Sum objective_function = this.constraints.createSum();
		for (int i = 0; i < 2; i++)
			objective_function.add(new Summand("t", new int[]{i}, 1));
		this.constraints.setObjectiveFunction(objective_function);
		
		// since scip can only read lines of limited length
		Sum objective_function_a = this.constraints.createSum().add(new Summand("t", new int[]{0}, 1));
		for (int i = 0; i < (int)r/2; i++)
			objective_function_a.add(new Summand("b", new int[]{i}, -1));
		this.constraints.appendSum(objective_function_a, "=", 0);
		
		Sum objective_function_b = this.constraints.createSum().add(new Summand("t", new int[]{1}, 1));
		for (int i = (int)r/2; i < r; i++)
			objective_function_b.add(new Summand("b", new int[]{i}, -1));		
		this.constraints.appendSum(objective_function_b, "=", 0);
		
		// create steady state condition from stoichiometric matrix
		Sum sum = this.constraints.createSum();
		ArrayList<Species> species_list = N.getFirstDimensionSet().toArrayList();		// get list of species
		ArrayList<Reaction> reaction_list = N.getSecondDimensionSet().toArrayList();	// get list of reactions
		for (int i = 0; i < m; i++){													// loop over species
			Species species = species_list.get(i);										// get current species
			sum = this.constraints.createSum();											// create sum for change of given species
			for (int j = 0; j < r; j++){												// loop over reactions
				Reaction reaction = reaction_list.get(j);								// get current reaction
				if (reaction.containsSpecies(species))									// if reaction uses species
					// current reaction influences change in current species and has to be added to sum
					sum.add(new Summand("v", new int[]{j}, N.getSimpleEntry(i, j)));
			}
			this.constraints.appendSum(sum, "=", 0);									// add resulting constraint
		}
		
		// create on/off-constraints for reactions:
	
		for (int i = 0; i < r; i++)
			this.constraints.appendSum(sum.clear().add(new Summand("v", new int[]{i}, 1)).add(new Summand("b", new int[]{i}, -1000)), "<=", 0); // v_i <= 1000 b_i
		for (int i = 0; i < r; i++)
			this.constraints.appendSum(sum.clear().add(new Summand("v", new int[]{i}, 1)).add(new Summand("b", new int[]{i}, -1)), ">=", 0);	// v_i >= b_i
		
		// turn forced reactions on
		for (int i = 0; i < this.forced_reactions.length; i++)
			this.constraints.appendSum(sum.clear().add(new Summand("b", new int[]{this.forced_reactions[i]}, 1)), "=", 1);
	}
	
	public void showResult() throws Exception{
		MyMatrix<Double,Species,Reaction> N = this.reaction_network.getSimpleNMatrix();	// get stoichiometric matrix
		ArrayList<Reaction> reaction_list = N.getSecondDimensionSet().toArrayList();
		//-------------
		// show result
		//-------------
		DecimalFormat df = new DecimalFormat(",##0.000");
		for (int i = 0; i < reaction_list.size(); i++){
			Reaction reaction = reaction_list.get(i);
			double value = this.constraints.getValueOfVariable("v", new int[]{N.getSecondDimensionIndex(reaction)});
			if (value > 0){
				String value_string = df.format(value);
				while (value_string.length() < 7)
					value_string = " " + value_string;
				System.out.println(value_string + "\t" + reaction.getName());
			}
		}
	}
}
