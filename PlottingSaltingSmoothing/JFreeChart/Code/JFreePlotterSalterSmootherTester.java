/**
 * The JFreePlotterSalterSmootherTester class contains the main method for running the plotter, salter, and smoother.
 * 
 * It creates an instance of the JFreePlotterSalterSmoother class and creates the function by passing two 
 * coefficients (a and b), a constant(c), a range of x values, and amount of points to plot to its run method.
 * 
 * @author Rocco Vulpis
 * @version 4/25/2024
 */
public class JFreePlotterSalterSmootherTester {

	public static void main(String[] args) {
		JFreePlotterSalterSmoother plot = new JFreePlotterSalterSmoother();
		plot.run(1, 0, 0, -80, 80, 150);
	}
	
}
