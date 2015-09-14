/**
 * 
 */
package nn;

import java.util.Random;
import java.util.Vector;


/**
 * @author ing. R.J.H.M. Stevens
 *
 */
public class Neuron {

	public Neuron(int numOutputs, int myIndex){
		this.myIndex = myIndex;
		for(int i = 0; i < numOutputs; ++i){
			outputWeights.add(new Connection());
		}
	}
	

	/**
	 * @param prevLayer The previous layer
	 */
	public void feedForward(Layer prevLayer) {
		double sum = 0.0;
		
		for (int i = 0; i < prevLayer.size(); ++i){
			sum += prevLayer.get(i).getOutputVal() * prevLayer.get(i).getOutputWeights().get(myIndex).weight;
		}
		
		this.outputVal = Neuron.transferFunction(sum);
		
	}
	
	/**
	 * @param targetVal
	 */
	public void calcOutputGradients(Double targetVal) {
		double delta = targetVal - this.outputVal;
		this.gradient = delta * Neuron.transferFunctionDerivative(this.outputVal);
	}

	/**
	 * @param nextLayer
	 */
	public void calcHiddenGradients(Layer nextLayer) {
		double dow = sumDow(nextLayer);
		this.gradient = dow * Neuron.transferFunctionDerivative(this.outputVal);
	}
	
	/**
	 * @param nextLayer
	 * @return
	 */
	private double sumDow(Layer nextLayer) {
		double sum = 0.0;
		
		for (int i =0; i < nextLayer.size() -1; i++){
			sum += this.outputWeights.get(i).weight * nextLayer.get(i).getGradient();
		}
		return sum;
	}

	/**
	 * @param prevLayer
	 */
	public void updateInputWeights(Layer prevLayer) {
		for (int i =0; i < prevLayer.size(); i++){
			Neuron neuron = prevLayer.get(i);
			double oldDeltaWeight = neuron.outputWeights.get(myIndex).deltaWeight;
			
			/* eta is the learning rate */
			double newDeltaWeight = 
					eta 
					* neuron.getOutputVal() 
					* this.gradient
					/* also add momentum a fractrion of the previous delta weight (alpha)*/
					+alpha
					*oldDeltaWeight;
			
			neuron.outputWeights.get(myIndex).deltaWeight = newDeltaWeight;
			neuron.outputWeights.get(myIndex).weight += newDeltaWeight;
		}
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param x
	 * @return
	 */
	private static double transferFunction(double x){
		/* this function can be changed the output value should be scaled to this function */
		/* tanh - output range [-1.0 ... 1.0] */
		return Math.tanh(x);

	}
	private static double transferFunctionDerivative(double x){
		/* tanh derivative */
		/* this is an approximation */
		return  1.0 - (x * x);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @return the outputWeights
	 */
	public Vector<Connection> getOutputWeights() {
		return outputWeights;
	}

	/**
	 * @param outputVal
	 */
	public void setOutputVal(Double outputVal) {
		this.outputVal = outputVal;
	}
	
	/**
	 * 
	 */
	public double getOutputVal() {
		return this.outputVal;
	}
	
	public double getGradient(){
		return this.gradient;
	}
	
	

	
	////////////////////////////////////////////////////////////////////////////////////////////////	
	private double outputVal;
	private Vector<Connection> outputWeights = new Vector<Connection>();
	private int myIndex;
	private double gradient;
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * eta overall net learning rate
	 * [0.0 ... 1.0]
	 * 0.0 - slow learner
	 * 0.2 medium learner
	 * 1.0 reckless learner
	 */
	static private double eta = 0.15;//0.15; 
	
	/**
	 * alpha - momentum
	 * [0.0 ... n]
	 * n is usual 1
	 * 0.0 no momentum
	 * 0.5 moderate momentum
	 */
	static private double alpha = 0.5; 
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	protected class Connection{
		public Connection(){
			weight = Math.random();
			deltaWeight = Math.random();
			if(debug)System.out.println("weight = "+ weight + "  deltaweight = "+ deltaWeight);
		}
		private boolean debug = DebugConst.debug_connection;
		public double weight = 0.0;
		public double deltaWeight = 0.0;
	}









}
