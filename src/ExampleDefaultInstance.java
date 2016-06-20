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

import instance.*;
import ilp.scip.*;
import system.process.scip.*;

public class ExampleDefaultInstance {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String path_to_scip = System.getProperty("user.dir") + "/lib/solvers/scipoptsuite-3.0.1/scip-3.0.1/bin";
		path_to_scip.getClass();
		
		DefaultConstraints constraints = new DefaultConstraints();
		constraints.getClass();
		
		MyDefaultSolver solver = new MyDefaultSolver(constraints);
		solver.getClass();
		
		DefaultInstance di = new DefaultInstance(constraints);
		di.getClass();
	}
}
