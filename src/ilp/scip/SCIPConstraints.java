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

package ilp.scip;

import ilp.*;

public class SCIPConstraints extends Constraints{
	public SCIPConstraints(){
		super();
	}
	
	public String getProgram(boolean maximize) throws Exception{
		StringBuffer program = new StringBuffer();
		
		if (maximize)
			program.append("MAXIMIZE\n");
		else
			program.append("MINIMIZE\n");
		
		program.append(this.getObjectiveFunction().getSum() + "\n");
		program.append("SUBJECT TO" + "\n");
		program.append(this.getConstraints());

		String[] integer_variables = this.getIntegerVariables();
		for (int i = 0; i < integer_variables.length; i++)
			program.append("INT " + integer_variables[i] + "\n");
		
		String[] binary_variables = this.getBinaryVariables();
		for (int i = 0; i < binary_variables.length; i++)
			program.append("BIN " + binary_variables[i] + "\n");
		
		program.append("END");
		
		return program.toString();
	}
}
