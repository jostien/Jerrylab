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

public abstract class Constraints {
	private VariablesContainerILP variables;
	private StringBuffer constraints;
	private Sum objective_function;
	private HashMap<String, Integer> variable_types;
	
	public Constraints(){
		this.variables = new VariablesContainerILP();
		this.constraints = new StringBuffer();
		this.objective_function = new Sum(this.variables);
		this.variable_types = new HashMap<String, Integer>();
	}
	
	public Constraints(Constraints constraints){
		this.variables = constraints.getVariables();
		this.constraints = constraints.getConstraints();
		this.objective_function = constraints.getObjectiveFunction();
		this.variable_types = constraints.getVariableTypes();
		
	}
	
	public void addVariables(String variable_name, int[] sizes, int type){
		this.variables.add(variable_name, sizes);
		this.variable_types.put(variable_name, new Integer(type));
	}

	public String[] getBinaryVariables() throws Exception{
		int total_size = 0;
		ArrayList<String[]> list = new ArrayList<String[]>();
		Iterator<String> iterator = this.variable_types.keySet().iterator();
		while (iterator.hasNext()){
			String key = iterator.next();
			if (this.variable_types.get(key)==Type.BINARY){
				int size = this.variables.getObjectSize(key);
				String[] string_array = new String[size];
				for (int i = 0; i < size; i++)
					string_array[i] = this.variables.getEnumerationILP(key, i);
				list.add(string_array);
				total_size = total_size + size;
			}
		}
		
		int c = 0;
		String[] ret = new String[total_size];
		for (int i = 0; i < list.size(); i++){
			String[] string_array = list.get(i);
			for (int j = 0; j < string_array.length; j++){
				ret[c] = string_array[j];
				c++;
			}
		}
		
		return ret;
	}
	
	public String[] getIntegerVariables() throws Exception{
		int total_size = 0;
		ArrayList<String[]> list = new ArrayList<String[]>();
		Iterator<String> iterator = this.variable_types.keySet().iterator();
		while (iterator.hasNext()){
			String key = iterator.next();
			if (this.variable_types.get(key)==Type.INTEGER){
				int size = this.variables.getObjectSize(key);
				String[] string_array = new String[size];
				for (int i = 0; i < size; i++){
					string_array[i] = this.variables.getEnumerationILP(key, i);
				}
				list.add(string_array);
				total_size = total_size + size;
			}
		}
		
		int c = 0;
		String[] ret = new String[total_size];
		for (int i = 0; i < list.size(); i++){
			String[] string_array = list.get(i);
			for (int j = 0; j < string_array.length; j++){
				ret[c] = string_array[j];
				c++;
			}
		}
		
		return ret;
	}
	
	public VariablesContainerILP getVariables(){
		return this.variables;
	}
	
	public HashMap<String,Integer> getVariableTypes(){
		return this.variable_types;
	}
	
	public StringBuffer getConstraints(){
		return this.constraints;
	}
	
	public void setVariables(VariablesContainerILP variables){
		this.variables = variables;
	}
	
	public void setVariableTypes(HashMap<String,Integer> variable_types){
		this.variable_types = variable_types;
	}
	
	public void setConstraints(StringBuffer constraints){
		this.constraints = constraints;
	}
	
	public int getVariableType(String variable_name){
		return this.variable_types.get(variable_name).intValue();
	}
	
	public Sum createSum(){
		return new Sum(this.variables);
	}
	
	public void appendSum(Sum sum, String comparator, double right_side) throws Exception{
		this.constraints.append(sum.getSum(comparator, right_side) + "\n");
	}
	
	public Sum getObjectiveFunction(){
		return this.objective_function;
	}
	
	public void setObjectiveFunction(Sum sum){
		this.objective_function = sum;
	}
	
	public void setValueOfVariable(String variable, Double value) throws Exception{
		this.variables.setValueILP(variable, value);
	}
	
	public double getValueOfVariable(String variable, int[] indices) throws Exception{
		return this.variables.getEvaluatedIdILP(variable, indices).doubleValue();
	}
	
	public void resetSolution(){
		this.variables.resetSolution();
	}
	
	public abstract String getProgram(boolean maximize) throws Exception;
}
