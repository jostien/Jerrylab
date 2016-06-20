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

public class MySCIP extends MyProcess{
	private SCIPConstraints constraints;
	
	public MySCIP(SCIPConstraints constraints){
		this.constraints = constraints;
		
		this.setIdPrefix("scip_");
		this.setCmdToStringArray();
		
		FileInformation input_file_information = this.getInputFileInformation();
		String input_file_id = input_file_information.getId();
		input_file_information.setId(input_file_id + ".lp");
		this.setInputFileInformation(input_file_information);
		
		this.setRegex("^SCIP Status.*$");
	}
	
	public void makeInputFile(boolean maximize) throws Exception{
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.getInputFile())));
		bw.write(this.constraints.getProgram(maximize));
		bw.close();
	}
	
	public Object getCmd(){
		String[] cmd = new String[1 + 10 + 2*(this.getParameterNames().length)];
		cmd[0] = this.getProgramPath() + this.getProgramName();
		
		for (int i = 0; i < this.getParameterNames().length; i++){
			cmd[2*i + 1] = this.getParameterNames()[i];
			cmd[2*i + 2] = this.getParameterValues()[i];
		}
		
		cmd[2*this.getParameterNames().length + 1] = "-c"; cmd[2*this.getParameterNames().length + 2] = "read " + this.getInputFile();
		cmd[2*this.getParameterNames().length + 3] = "-c"; cmd[2*this.getParameterNames().length + 4] = "lp";
		cmd[2*this.getParameterNames().length + 5] = "-c"; cmd[2*this.getParameterNames().length + 6] = "optimize";
		cmd[2*this.getParameterNames().length + 7] = "-c"; cmd[2*this.getParameterNames().length + 8] = "write solution " + this.getOutputFile();
		cmd[2*this.getParameterNames().length + 9] = "-c"; cmd[2*this.getParameterNames().length +10] = "quit";
			
		return cmd;
	}
	
	public SCIPConstraints parseOutputFile() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File(this.getOutputFile())));
		String line = br.readLine(); // first two lines are not from interest, only the rest
		line = br.readLine();

		this.constraints.resetSolution();
		while ((line = br.readLine()) != null){
			line = this.convert(line);

			String[] cells = line.split(" ");
			
			this.constraints.setValueOfVariable(cells[0], new Double(cells[1]));
		}
		br.close();
		
		return this.constraints;
	}
	
	public boolean isUnbounded() throws Exception{
		if (this.getStatus() == null || this.getStatus().length == 0)
			throw new Exception("Error executing SCIP solver.");
		
		String status = this.getStatus()[0];
		if (status.indexOf("unbounded") > -1)
			return true;
		return false;
	}
	
	private String convert(String line){
		line = line.replaceAll("\t", " ");
		while (!this.test(line))
			line = line.replaceAll("  ", " ");
		
		return line;
	}
	
	private boolean test(String line){
		return line.indexOf("  ") == -1;
	}
	
	public String getCmdString(){
		String ret = "";
		if (this.isCmdString())
			return (String)this.getCmd();
		else {
			String[] cmd = (String[])this.getCmd();
			ret = cmd[0] + " ";
			for (int i = 1; i < cmd.length; i++)
				if (i%2 == 0)
					ret = ret + "\"" + cmd[i] + "\" ";
				else
					ret = ret + cmd[i] + " ";
		}
		return ret.substring(0, ret.length() - 1);
	}
}
