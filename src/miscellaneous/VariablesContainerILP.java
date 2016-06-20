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

package miscellaneous;

import java.util.HashMap;
import jsatbox.miscellaneous.*;

public class VariablesContainerILP extends VariablesContainer{
	private HashMap<String, Double> ILPsolution;	

	public VariablesContainerILP(){
		super();
		this.ILPsolution = new HashMap<String, Double>();
	}
	
	public String getEnumerationILP(String key, int[] indices) throws Exception{
		int ident = Math.abs(((Integer)((IndicesContainer)hm.get(key)).get(indices)).intValue());
		
		int size = 0;
		if (!this.id.containsKey(new Integer(ident))){
			size = this.id.size() + 1;
			this.id.put(new Integer(ident), new Integer(size));
			
			this.enumeration.put(new Integer(size), new Integer(ident));
		
			return "ilp." + size;
		}
		
		return "ilp." + this.id.get(new Integer(ident)).intValue();
	}

	public String getEnumerationILP(String key, int index) throws Exception{
		int ident = Math.abs(((Integer)((IndicesContainer)hm.get(key)).get(index)).intValue());
		
		int size = 0;
		if (!this.id.containsKey(new Integer(ident))){
			size = this.id.size() + 1;
			this.id.put(new Integer(ident), new Integer(size));
			
			this.enumeration.put(new Integer(size), new Integer(ident));
		
			return "ilp." + size;
		}
	
		return "ilp." + this.id.get(new Integer(ident)).intValue();
	}

	public Double getEvaluatedIdILP(String key, int[] indices) throws Exception{
		int ident = ((Integer)((IndicesContainer)hm.get(key)).get(indices)).intValue();
		
		if (this.id.containsKey((new Integer(ident)))){
			String k = "ilp." + this.id.get(new Integer(ident));
			if (this.ILPsolution.containsKey(k))
				return this.ILPsolution.get(k);
			else
				return new Double(0.0);
		}
		
		return new Double(0.0);
	}
	
	public void setValueILP(String ilp_key, Double value) throws Exception{
		this.ILPsolution.put(ilp_key, value);
	}
	
	public void resetSolution(){
		this.ILPsolution = new HashMap<String,Double>();
	}
}
