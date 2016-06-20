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

import system.process.*;
import ilp.scip.*;

import java.io.*;

public class MyFiberSCIP extends MyProcess{
	private SCIPConstraints constraints;
	private int number_of_threads;
	private String path_to_settings_file;
	
	public MyFiberSCIP(SCIPConstraints constraints, String path_to_settings_file){
		this.constraints = constraints;
		this.number_of_threads = 1;
		this.path_to_settings_file = path_to_settings_file;
		
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
		String[] cmd = new String[5 + 2*this.getParameterNames().length];
		cmd[0] = this.getProgramPath() + this.getProgramName();
		cmd[1] = this.path_to_settings_file;
		cmd[2] = this.getInputFile();
		cmd[3] = "-sth";
		cmd[4] = "" + this.number_of_threads;
		
		for (int i = 0; i < this.getParameterNames().length; i++){
			cmd[5 + 2*i] = this.getParameterNames()[i];
			cmd[5 + 2*i + 1] = this.getParameterValues()[i];
		}
		
		return cmd;
	}
	
	public SCIPConstraints parseOutputFile(SCIPConstraints constraints) throws Exception{
		System.out.println(this.getOutputFile());
		BufferedReader br = new BufferedReader(new FileReader(new File(this.getOutputFile())));
		String line = br.readLine(); // first two lines are not from interest, only the rest
		line = br.readLine();

		constraints.resetSolution();
		while ((line = br.readLine()) != null){
			line = this.convert(line);

			String[] cells = line.split(" ");
			constraints.setValueOfVariable(cells[0], new Double(cells[1]));
		}
		br.close();
		
		return constraints;
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
	
	public void setNumberOfThreads(int number_of_threads){
		this.number_of_threads = number_of_threads;
	}
}
