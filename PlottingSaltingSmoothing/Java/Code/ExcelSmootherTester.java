/**
 * The ExcelSmootherTester class contains the main method for smoothing the plot.
 * 
 * It creates an instance of the Smoother class and smoothes the plot points by passing a number of runs 
 * and a window value to its run method.
 * 
 * @author Rocco Vulpis
 * @version 4/25/2024
 */

public class ExcelSmootherTester {

	public static void main(String[] args) {
		
		ExcelSmoother smoother = new ExcelSmoother();
		smoother.run(6, 5);
		
	}
}
