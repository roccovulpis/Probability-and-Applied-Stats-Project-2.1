import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * References:
 * https://github.com/roccovulpis/Probability-and-Applied-Stats-Project-2/tree/f54ca2eddf404608e995c81af324c2688d1f2072/PlottingSaltingSmoothing/Java/Code
 */

/**
 * The Salter class salts values by reading a CSV file containing x and y values of a quadratic function.
 * 
 * This class randomly adjusts the y values by a specified number passed into the run method then writes 
 * the modified data back to a new CSV file.
 *
 * @author Rocco Vulpis
 * @version 4/25/2024
 */

public class ExcelSalter {

	// Fields
	private ArrayList<Double> xValues = new ArrayList<>();
	private ArrayList<Double> yValues = new ArrayList<>();
	private String header = null;

	/**
	 * This method is called in the main method to salt the values.
	 * It calls the readFile method, saltValues method, and the writeFile method.
	 * 
	 * @param numberOfRuns The amount of times the yValues ArrayList is salted. 
	 * @param adjustmentRange The maximum range that each y value can be randomly adjusted by.
	 */
	public void run(int numberOfRuns, int adjustmentRange) {
		readFile();
		saltValues(numberOfRuns, adjustmentRange);
		writeFile();
	}

	/**
	 * Reads a CSV file named "data.csv" and stores its x and y values in ArrayLists.
	 */
	public void readFile() {

		try {
			// Reads in a file named "data.csv" from the default workspace directory.
			BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
			String csvLine;
			// Skips the header of the CSV file.
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
	 * Randomly adjusts the y values stored in the yValues ArrayList.
	 * Each y value is either increased or decreased by a randomly generated integer between 0 and 
	 * the maximum adjustment range.
	 * 
	 * @param numberOfRuns The amount of times the yValues ArrayList is salted. 
	 * @param adjustmentRange The maximum range that each y value can be randomly adjusted by.
	 */
	public void saltValues(int numberOfRuns, int adjustmentRange) {

		// Creates a Random object named rng.
		Random rng = new Random();
		// Salts the yValues ArrayList by a specified number of runs. 
		for (int i = 0; i < numberOfRuns; i++) {
			// Loops through x values.
			for (int j = 0; j < xValues.size(); j++) {
				// Sets adjustment range.
				double adjust = rng.nextInt(adjustmentRange);	
				// If rng from 0-1 generates a 0, add the adjust value. Otherwise, subtract the adjust value.
				if(rng.nextInt(2) == 0) {
					double adjustedyValue = yValues.get(j) + adjust;
					yValues.set(j, adjustedyValue);
				} else {
					double adjustedyValue = yValues.get(j) - adjust;
					yValues.set(j, adjustedyValue);
				}
			}
		}

	}

	/**
	 * Writes an ArrayList of x values and an ArrayList of y values to a CSV file.
	 */
	public void writeFile() {

		try {
			// Writes to a file named "saltedData.csv" in the default workspace directory.
			BufferedWriter writer = new BufferedWriter(new FileWriter("saltedData.csv"));
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