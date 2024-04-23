import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * References:
 * https://github.com/roccovulpis/Probability-and-Applied-Stats-Project-2/tree/f54ca2eddf404608e995c81af324c2688d1f2072/PlottingSaltingSmoothing/Java/Code
 */

/**
 * The ExcelSmoother class smoothes values by reading a CSV file containing salted x and y values of a quadratic function.
 * This class averages a set window of points around each y value then writes the modified data back to a new CSV file.
 *
 * @author Rocco Vulpis
 * @version 4/25/2024
 */

public class ExcelSmoother {

	// Fields
	private ArrayList<Double> xValues = new ArrayList<>();
	private ArrayList<Double> yValues = new ArrayList<>();
	private String header = null;

	/**
	 * This method is called in the main method to smooth the values.
	 * It calls the readFile method, smoothValues method, and the writeFile method.
	 * 
	 * @param numberOfRuns The amount of times the yValues ArrayList is smoothed. 
	 * @param windowValue The size of the window used for smoothing.
	 */
	public void run(int numberOfRuns, int windowValue) {
		readFile();
		smoothValues(numberOfRuns, windowValue);
		writeFile();
	}

	/**
	 * Reads a CSV file named "data.csv" and stores its x and y values in ArrayLists.
	 */
	public void readFile() {

		try {
			//Read from a directory
			BufferedReader reader = new BufferedReader(new FileReader("saltedData.csv"));
			String csvLine;
			//Skip the header of the CSV file
			header = reader.readLine();
			while((csvLine = reader.readLine()) != null) {
				// Splits the readLine separating the x and y values.
				String[] plotData = csvLine.split(",");
				if(plotData.length >= 2) {
					// Parses the values from the plotData array and adds them to the respected xValues and yValues ArrayList.
					xValues.add(Double.parseDouble(plotData[0]));
					yValues.add(Double.parseDouble(plotData[1]));	
				}
			} 
			reader.close();

			System.out.println("\n Values successfully loaded");
		} catch (IOException e) {
			e.printStackTrace();
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
			for (int j = 0; j < xValues.size(); j++) {
				// Sets the beginning constraint to either 0 or i - windowValue.
				// It determines which value is larger to prevent the window from starting out of range of the list. 
				int beginningConstraint = Math.max(0, j - windowValue);
				// Sets end constraint to either the end of the ArrayList of x values or i + windowValue.
				// It determines which value is smaller to prevent the window from ending out of range of the list.
				int endConstraint = Math.min(xValues.size() - 1, j + windowValue);
				// Stores the sum of the y values within the range of the window.
				double sumOfValues = 0;
				// Loops over the range from the beginning constraint to the end constraint.
				for (int k = beginningConstraint; k <= endConstraint; k++) {
					// Adds the current y value to the sum.
					sumOfValues += yValues.get(k);
				}
				// Holds the average of the current iteration.
				double avg = sumOfValues / (endConstraint - beginningConstraint + 1);
				// Sets the y value at index i to the calculated average.
				yValues.set(j, avg);
			}
		}
		 
	}

	/**
	 * Writes an ArrayList of x values and an ArrayList of y values to a CSV file.
	 */
	public void writeFile() {

		try {
			// Writes to a file named "smoothedData.csv" in the default workspace directory.
			BufferedWriter writer = new BufferedWriter(new FileWriter("smoothedData.csv"));
			// Writes the header to the CSV file outside of the loop.
			writer.write(header);
			// Loops through the size of the xValues ArrayList.
			for(int i = 0; i < xValues.size(); i++) {
				writer.write("\n");
				// Writes each adjusted x and y value to the CSV.
				writer.write(xValues.get(i).toString() + "," + yValues.get(i).toString());
			}
			writer.close();
			System.out.println("\n Values successfully exported");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
