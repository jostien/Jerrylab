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

import java.io.*;
import java.util.*;

public class Solutions extends ArrayList<String[][]>{
	private static final long serialVersionUID = 1L;

	public String[][] getArray(){
		String[][] ret = new String[this.get(0).length][this.size() + 1];
		for (int j = 0; j < this.size(); j++){
			String[][] solution = this.get(j);
			if (j == 0)
				for (int i = 0; i < solution.length; i++)
					ret[i][j] = solution[i][0];

			for (int i = 0; i < solution.length; i++)
				ret[i][j + 1] = solution[i][1];
		}
		
		return ret;
	}
	
	public void write(String file_name, String delimiter, String quotation) throws Exception{
		String[][] string_array = this.getArray();

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file_name)));
		for (int i = 0; i < string_array.length; i++){
			StringBuffer string_buffer = new StringBuffer();
			for (int j = 0; j < string_array[i].length; j++)
				string_buffer.append(quotation + string_array[i][j] + quotation + delimiter);
			string_buffer.setLength(string_buffer.length() - 1);
			String line = string_buffer.toString();
			bw.write(line + "\n");
		}
		bw.close();
	}
}
