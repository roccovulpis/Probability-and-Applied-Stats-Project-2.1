package src;

/**
 * The ExcelPlotterTester class contains the main method for creating the plot.
 * It creates an instance of the ExcelPlotter class and creates the plot points by calling and passing two coefficients (a and b),
 * a constant(c), a range of x values, and amount of points to plot to its run method.
 * 
 * @author Rocco Vulpis
 * @version 4/25/2024
 */

public class ExcelPlotterTester {
	
	public static void main(String[] args) {
		ExcelPlotter plotter = new ExcelPlotter();
		plotter.run(1, 1, 1, -80, 80, 100);
		
	}
}
