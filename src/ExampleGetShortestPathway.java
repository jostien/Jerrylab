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

import crnt.*;
import instance.*;
import ilp.scip.*;
import system.process.scip.*;
import system.parsers.sbml.cobra.*;

public class ExampleGetShortestPathway {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String path_to_scip = System.getProperty("user.dir") + "/lib/solvers/scipoptsuite-3.0.1/scip-3.0.1/bin";
		
		System.out.print("Parsing input file ... ");
		ReactionNetwork reaction_network = (new COBRAParser()).parse(System.getProperty("user.dir") + "/../CRNToolkit/examples/SBML/BiGG/13_01_15_H_sapiens_Recon_1.xml");
		System.out.println("done");
		
		System.out.print("Generating constraints ... ");
		SCIPConstraints constraints = new SCIPConstraints();
		GetShortestPathway gsp = new GetShortestPathway(reaction_network, constraints, new int[]{925});
		gsp.makeConstraints();
		System.out.println("done");
		
		System.out.print("Starting solver ... ");		
		//----------------------
		// solve linear program
		//----------------------	
		MySCIP scip = new MySCIP(constraints);
		scip.setProgramName("scip");
		scip.setProgramPath(path_to_scip);
		scip.setInputPath("/tmp");
		scip.setOutputPath("/tmp");
		scip.makeInputFile(false);
		scip.run(true);
		
		scip.parseOutputFile();
		gsp.showResult();
	}

}
