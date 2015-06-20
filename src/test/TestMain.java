/**
 * 
 */
package test;
import java.util.Vector;

import nn.Net;
import nn.ReadTrainingData;
/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadTrainingData data = new ReadTrainingData("test.txt");
		Vector<Integer> topology = data.getTopology();
		System.err.println(topology.size());
		
		Net myNet = new Net(topology);
		
		int pass = 0;
		while (true){
			Vector<Double> inputVals = data.getNextInput();
			if (inputVals == null)
				break;
			myNet.feedForward(inputVals);
		
			Vector<Double> targetVals = data.getNextOutput();
			if (targetVals == null)
				break;
			myNet.backProp(targetVals);
		
			Vector<Double> results = myNet.getResults();
			
			pass++;
			
			System.err.println("-------------------------------");
			System.err.println("run: " + pass);
			System.err.print("Input:");
			for (double print: inputVals){
				System.err.print(" " + print);
			}
			System.err.println(".");
			
			System.err.print("Target:");
			for (double print: targetVals){
				System.err.print(" " + print);
			}
			System.err.println(".");
			
			System.err.print("Results:");
			for (double print: results){
				System.err.print(" " + print);
			}
			System.err.println(".");
			
			System.err.println("averageError: " + myNet.getRecentAverageError());
		}

	}

}
