/**
 * 
 */
package test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author ing. R.J.H.M. Stevens
 * this class generats a file with the following format

topology: 2 4 1
in: 1.0 0.0
out: 1.0
in: 1.0 1.0
out: 0.0

 */
public class TrainingDataGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrintWriter writer;
		try {
			Random rand = new Random();
			writer = new PrintWriter("test.txt", "UTF-8");
			writer.println("topology: 2 4 1");
			for (int i = 0; i < 1000; i++){
				Options option;
				switch(rand.nextInt(4)){
				case 0:
					option = Options.FF;
					break;
				case 1:
					option = Options.FT;
					break;
				case 2:
					option = Options.TF;
					break;
				default:
					option = Options.TT;
				
				}
				switch(option){
				case FF:
					writer.println("in: 0.0 0.0");
					writer.println("out: 0.0");
					break;
				case FT:
					writer.println("in: 0.0 1.0");
					writer.println("out: 1.0");
					break;
				case TF:
					writer.println("in: 1.0 0.0");
					writer.println("out: 1.0");
					break;
				case TT:
					writer.println("in: 1.0 1.0");
					writer.println("out: 0.0");
					break;
				}
			}
			writer.close();
			System.err.println("finished");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	enum Options{
		FF,
		FT,
		TF,
		TT;
	}

}
