import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * References
 * https://www.jfree.org/jfreechart/api/javadoc/index.html
 * https://www.codejava.net/java-se/graphics/using-jfreechart-to-draw-xy-line-chart-with-xydataset
 * https://www.youtube.com/watch?v=5o3fMLPY7qY
 * References: https://github.com/roccovulpis/Probability-and-Applied-Stats-Project-2/tree/f54ca2eddf404608e995c81af324c2688d1f2072/PlottingSaltingSmoothing/Java/Code
 */

/**
 * The JFreePlotterSalterSmoother class creates and displays graphs using the JFreeChart library. 
 * It uses scatter plots to display, salt, and smooth a quadratic function.
 * 
 * @author Rocco Vulpis
 * @version 4/25/2024
 */
public class JFreePlotterSalterSmoother extends JFrame{

	// Fields
	private double[] xValues;
	private double[] yValues;
	private String graphHeading = null;

	/**
	 * Called in the main method to run the generation of the function.
	 * 
	 * It creates and displays the function in its original, salted, and smoothed forms by calling the createParabolaFunction, showFunctionGraph, 
	 * showSaltedFunctionGraph, and showSmoothedFunctionGraph methods.
	 * 
	 * @param a The coefficient of x^2.
	 * @param b The coefficient of x.
	 * @param c A constant. 
	 * @param xRangeBeginning The starting value of the x range.
	 * @param xRangeEnd The ending value of the x range.
	 * @param numberOfPoints The number of points to calculate in the specified range.
	 */
	public void run(double a, double b, double c, int xRangeBeginning, 
			int xRangeEnd, int numberOfPoints) {

		createParabolaFunction(a, b, c, xRangeBeginning, xRangeEnd, numberOfPoints);
		showFunctionGraph();
		showSaltedFunctionGraph();
		showSmoothedFunctionGraph();
	}

	/**
	 * Calculates the y values of the function for a set of x values within the specified range and interval.
	 * 
	 * @param a The coefficient of x^2.
	 * @param b The coefficient of x.
	 * @param c A constant. 
	 * @param xRangeBeginning The starting value of the x range.
	 * @param xRangeEnd The ending value of the x range.
	 * @param numberOfPoints The number of points to calculate in the specified range.
	 */
	public void createParabolaFunction(double a, double b, double c, int xRangeBeginning, int xRangeEnd, int numberOfPoints) {
		// Calculates the range of x values.
		int xRange = xRangeEnd - xRangeBeginning;
		// Determines the interval between each x value based on the number of points and the range.
		double interval = (double)xRange / numberOfPoints;
		// Initializes arrays to store the x and y values.
		xValues = new double[numberOfPoints];
		yValues = new double[numberOfPoints];
		// Loops through each point to calculate the x and y values.
		for(int i = 0; i < numberOfPoints; i++) {
			// Calculates the x value for the current point.
			double x = xRangeBeginning + i * interval;
			// Calculates the y value using the formula y = ax^2 + bx + c.
			double y = a * Math.pow(x, 2) + b * x + c;
			// Stores the calculated x and y values in the arrays.
			xValues[i] = x;
			yValues[i] = y;
		}
	}

	/**
	 * Creates and displays the graph of the original function.
	 */
	public void showFunctionGraph() {
		graphHeading = "Graph of Quadratic Function";
		createGraph(graphHeading);
	}

	/**
	 * Creates and displays the graph of the salted function.
	 */
	public void showSaltedFunctionGraph() {
		graphHeading = "Salted Graph of Function";
		saltValues(350);
		createGraph(graphHeading);
	}

	/**
	 * Creates and displays the graph of the smoothed function.
	 */
	public void showSmoothedFunctionGraph() {
		graphHeading = "Smoothed Graph of Function";
		smoothValues(5, 8);
		createGraph(graphHeading);
	}

	/**
	 * Creates and displays a scatter plot graph.
	 * 
	 * @param graphHeading Title of the graph
	 */
	public void createGraph(String graphHeading) {
		// Creates an XYDataset.
		XYDataset dataset = createDataset();
		// Creates a scatter plot as well as labels for X and Y axes, and sets the plot orientation to vertical.
		// The boolean parameters disable legends, tooltips, and URLs.
		JFreeChart chart = ChartFactory.createScatterPlot(graphHeading, "X-Axis", "Y-Axis", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		// Creates a ChartPanel object to wrap the chart.
		ChartPanel chartPanel = new ChartPanel(chart);
		// Creates a JFrame to display the graphs as a GUI.
		JFrame frame = new JFrame();
		frame.add(chartPanel, BorderLayout.CENTER);
		frame.setTitle("Scatter Plot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(575, 500);
		frame.setVisible(true);

	}

	/**
	 * Creates an XYDataset containing a series of data points.
	 * The data points are defined by the arrays xValues and yValues. 
	 *
	 * @return dataset XYSeriesCollection containing the data series.
	 */
	private XYDataset createDataset() {
		// Creates an XYSeriesCollection object.
		XYSeriesCollection dataset = new XYSeriesCollection();
		// Create an XYSeries labeled y = mx + b.
		XYSeries series1 = new XYSeries("y = ax^2 + bx + c");
		// Adds the x and y values to the XYSeries.
		for (int i = 0; i < xValues.length; i++) {
			series1.add(xValues[i], yValues[i]);
		}
		// Adds the series to the data set.
		dataset.addSeries(series1);
		return dataset;
	}

	/**
	 * Randomly adjusts the y values stored in the yValues array.
	 * Each y value is either increased or decreased by a randomly generated integer between 0 and the maximum adjustment range.
	 */
	public void saltValues(int adjustmentRange) {

		// Creates a Random object named rng.
		Random rng = new Random();
		// Salts the y values for 80 iterations.
		for (int i = 0; i < 80; i++) {
			// Loops through x values.
			for (int j = 0; j < xValues.length; j++) {
				// Sets adjustment range.
				double adjust = rng.nextInt(adjustmentRange);	
				// If rng from 0-1 generates a 0, add the adjust value. Otherwise, subtract the adjust value.
				if(rng.nextInt(2) == 0) {
					double adjustedyValue = yValues[j] + adjust;
					yValues[j] = adjustedyValue;
				} else {
					double adjustedyValue = yValues[j] - adjust;
					yValues[j] = adjustedyValue;
				}
			}
		}

	}

	/**
	 * Smoothes the y values using a moving average. 
	 * Each y value is replaced with the average of y values within the window around it.
	 * 
	 * @param numberOfRuns The amount of times the yValues ArrayList is smoothed. 
	 * @param windowValue The size of the window used for smoothing, determining the range of values to average.
	 */

	public void smoothValues(int numberOfRuns, int windowValue) {
		
		// Smoothes the ArrayList of y values by the specified number of runs.
		for (int i = 0; i < numberOfRuns; i++) {
			// Loops through x values.
			for (int j = 0; j < xValues.length; j++) {
				// Sets the beginning constraint to either 0 or i - windowValue.
				// It determines which value is larger to prevent the window from starting out of range of the list. 
				int beginningConstraint = Math.max(0, j - windowValue);
				// Sets end constraint to either the end of the ArrayList of x values or i + windowValue.
				// It determines which value is smaller to prevent the window from ending out of range of the list.
				int endConstraint = Math.min(xValues.length - 1, j + windowValue);
				// Stores the sum of the y values within the range of the window.
				double sumOfValues = 0;
				// Loops over the range from the beginning constraint to the end constraint.
				for (int k = beginningConstraint; k <= endConstraint; k++) {
					// Adds the current y value to the sum.
					sumOfValues += yValues[k];
				}
				// Holds the average of the current iteration.
				double avg = sumOfValues / (endConstraint - beginningConstraint + 1);
				// Sets the y value at index i to the calculated average.
				yValues[j] = avg;
			}
		}

	}

}