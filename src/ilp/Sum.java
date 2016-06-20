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

package ilp;

import miscellaneous.*;
import java.util.*;

public class Sum {
	private ArrayList<Summand> sum;
	private VariablesContainerILP variables;
	
	public Sum(VariablesContainerILP variables){
		this.variables = variables;
		this.sum = new ArrayList<Summand>();
	}
	
	public Sum add(Summand summand){
		this.sum.add(summand);
		
		return this;
	}
	
	public Sum clear(){
		this.sum.clear();
		
		return this;
	}
	
	public Summand get(int index){
		return this.sum.get(index);
	}
	
	public String getSum() throws Exception{
		StringBuffer ret = new StringBuffer();
		if (this.sum.size() == 0)
			return ret.toString();
		
		for (int i = 0; i < this.sum.size(); i++){
			Summand summand = this.sum.get(i);
			
			String key = summand.getKey();
			int[] indices = summand.getIndices();
			double factor = summand.getFactor();
			
			String parity = "";
			if (factor > 0)
				parity = "+";

			if (factor != 0)
				if (key != null)
					ret.append(" ").append(parity).append(factor).append(" ").append(this.variables.getEnumerationILP(key, indices));
				else
					ret.append(" ").append(parity).append(factor);
		}
		
		return ret.toString();
	}
	
	public String getSum(String comparator, double right_side) throws Exception{
		String ret = this.getSum();
		if (ret.length() == 0)
			return ret;
		
		String parity = "";
		if (right_side > 0)
			parity = "+";		
		
		ret = ret + " " + comparator + " " + parity + right_side;
		
		return ret;
	}
	
	public Sum negate(){
		for (int i = 0; i < this.sum.size(); i++)
			this.sum.get(i).negate();
		
		return this;
	}
	
	public int size(){
		return this.sum.size();
	}

	public Sum copy(){
		Sum ret = new Sum(this.variables);
		
		for (int i = 0; i < this.sum.size(); i++)
			ret.add(this.sum.get(i).copy());
		
		return ret;
	}
}
