/**
 * 
 */
package nn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class ReadTrainingData {

	public ReadTrainingData(String fileName){
		try {
			this.br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("cant read file");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	public Vector<Integer> getTopology(){
		Vector<Integer> topology = new Vector<Integer>();
		if (this.br == null)
			return null;
		
		try {
			String line = br.readLine();
			if (line == null){ 
				System.err.println("incorrect file");
				System.exit(0);
			}
				
			if (!line.contains("topology:")){ 
				System.err.println("topology not found");
				System.exit(0);
			}
			line = line.replace("topology: ", "");
			String[] items = line.split(" ");
			for (String item: items){
				int val = Integer.parseInt(item);
				topology.add(val);
				if (debug) System.out.println("The totology value " + val);
			}
			
		} catch (IOException e) {
			System.err.println("something went wrong with reading the topology");
			e.printStackTrace();
			
			System.exit(0);
		}
		

		return topology;
	}
	
	public Vector<Double> getNextInput(){
		Vector<Double> input = new Vector<Double>();
		try {
			String line = br.readLine();
			if (line == null){ 
				//System.err.println("incorrect file");
				System.exit(0);
			}
				
			if (!line.contains("in:")){ 
				if (debug)System.err.println("in not found");
				return null;
			}
			line = line.replace("in: ", "");
			String[] items = line.split(" ");
			for (String item: items){
				double val = Double.parseDouble(item);
				input.add(val);
				if (debug) System.out.println("The in value " + val);
			}
			
		} catch (IOException e) {
			System.err.println("something went wrong with reading the in");
			e.printStackTrace();
			
			System.exit(0);
		}
		return input;
	}
	
	public Vector<Double> getNextOutput(){
		Vector<Double> output = new Vector<Double>();
		try {
			String line = br.readLine();
			if (line == null){ 
				System.err.println("incorrect file");
				System.exit(0);
			}
				
			if (!line.contains("out:")){ 
				if (debug)System.err.println("out not found");
				return null;
			}
			line = line.replace("out: ", "");
			String[] items = line.split(" ");
			for (String item: items){
				double val = Double.parseDouble(item);
				output.add(val);
				if (debug) System.out.println("The out value " + val);
			}
			
		} catch (IOException e) {
			System.err.println("something went wrong with reading the out");
			e.printStackTrace();
			
			System.exit(0);
		}
		return output;
	}
	
	protected BufferedReader br;
	private boolean debug = DebugConst.debug_readTraining;

}
