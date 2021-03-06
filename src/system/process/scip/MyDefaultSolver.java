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

package system.process.scip;

import ilp.scip.*;
import system.process.*;

import java.io.*;

public class MyDefaultSolver extends MyProcess{
	private DefaultConstraints constraints;
	
	public MyDefaultSolver(DefaultConstraints constraints){
		this.constraints = constraints;
	}
	
	public void makeInputFile(boolean maximize) throws Exception{
	}
	
	public Object getCmd(){
		return null;
	}
	
	public DefaultConstraints parseOutputFile() throws Exception{
		return this.constraints;
	}
	
	public boolean isUnbounded() throws Exception{
		return false;
	}
}
