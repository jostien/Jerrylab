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

public class Summand {
	private String key;
	private int[] indices;
	private double factor;
	
	public Summand(String key, int[] indices, int factor){
		this.key = key;
		this.indices = indices;
		this.factor = factor;
	}
	
	public Summand(String key, int[] indices, double factor){
		this.key = key;
		this.indices = indices;
		this.factor = factor;
	}

	public Summand(double factor){
		this.key = null;
		this.indices = null;
		this.factor = factor;
	}

	public String getKey(){
		return this.key;
	}
	
	public int[] getIndices(){
		return this.indices;
	}
	
	public double getFactor(){
		return this.factor;
	}
	
	public void setFactor(double factor){
		this.factor = factor;
	}
	
	public void negate(){
		this.factor = -this.factor;
	}
	
	public Summand copy(){
		Summand ret = new Summand(this.key, this.indices, this.factor);
		
		return ret;
	}
}
