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
public class Net {

	public Net(Vector<Integer> topology){
		int numLayers = topology.size();
		Random rand = new Random();
		for (int layerNum = 0; layerNum < numLayers; layerNum++){
			if (debug) System.out.println("added layer number "+layerNum);
			layers.add(new Layer());
			
			int numOutputs = (layerNum == topology.size() - 1)?0:topology.get(layerNum+1);
			
			for (int neuronNum = 0; neuronNum <= topology.get(layerNum); neuronNum++){
				if (debug) System.out.println("added neuron number "+layerNum+"."+neuronNum);
				layers.lastElement().add(new Neuron(numOutputs, neuronNum, rand));
			}
			
			/* force the bias output val to 1.0 */ 
			layers.lastElement().lastElement().setOutputVal(1.0);
		}
	}
	
	public void feedForward(Vector<Double> inputVals){
		/* check if the input size is correct and keep the bias in account */
		if (inputVals.size() != layers.get(0).size() - 1){
			System.err.println("The size of the inputValls is not correct");
		}
		
		for (int i =0; i < inputVals.size(); i++){
			layers.get(0).get(i).setOutputVal(inputVals.get(i));
		}
		
		/* forward propagate */
		for (int i = 1; i < layers.size(); i++){
			for (int j = 0; j < layers.get(i).size() - 1 ; j++){
				layers.get(i).get(j).feedForward(layers.get(i-1));
			}
		}
		
	}
	public void backProp(Vector<Double> targetVals){
		Layer outputLayer = layers.lastElement();
		
		/* calculate the overall net error (in this case using RMS) */
		this.error = 0.0;
		
		for (int i = 0; i < outputLayer.size() - 1; i++){
			double delta = targetVals.get(i) - outputLayer.get(i).getOutputVal();
			error += delta * delta;
		}
		
		error /= outputLayer.size() - 1;
		error = Math.sqrt(error); // The rms
		
		/* a recent average measurement */
		this.recentAverageError = (this.recentAverageError * this.recentAverageSmoothingsFactor + error) / (this.recentAverageSmoothingsFactor + 1);
		
		/* calculate the output layer gradients */
		for (int i = 0; i < outputLayer.size() - 1; i++){
			outputLayer.get(i).calcOutputGradients(targetVals.get(i));
		}
		
		/* calculate the hidden layers gradients */
		for(int i =  layers.size() -2; i > 0; i--){
			Layer hiddenLayer = layers.get(i);
			Layer nextLayer = layers.get(i + 1);
			for (int j = 0; j < hiddenLayer.size() - 1; j++){
				hiddenLayer.get(i).calcHiddenGradients(nextLayer);
			}
		}
		
		/* update connection weights */
		for (int i = layers.size() - 1; i > 0; i--){
			Layer layer = layers.get(i);
			Layer prevLayer = layers.get(i-1);
			
			for (int j = 0; j < layer.size() -1; j++){
				layer.get(j).updateInputWeights(prevLayer);
			}
		}
	}
	public Vector<Double> getResults(){
		Vector<Double> resultVals = new Vector<Double>();
		for (int i = 0; i < layers.lastElement().size() - 1; i++)
			resultVals.add(layers.lastElement().get(i).getOutputVal());
		return resultVals;
	}

	/**
	 * @return the recentAverageError
	 */
	public double getRecentAverageError() {
		return recentAverageError;
	}
	
	protected double error = 0.0;
	protected double recentAverageError;


	protected double recentAverageSmoothingsFactor = 100;
	protected boolean debug = DebugConst.debug_net;
	protected Vector<Layer> layers = new Vector<Layer>();
	
}

