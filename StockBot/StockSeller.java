import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The StockSeller class simulates a stock trading bot using various trading strategies such as RSI and Moving Average. 
 * 
 * It handles reading stock data from a CSV file, calculates trading indicators, executes trades based on those strategies, 
 * and writes the results back to a CSV file.
 *
 * @author Rocco Vulpis
 * @version 4/25/2024
 */

public class StockSeller {

	// Fields
	private double startingBalance = 10000;
	private double balance = 10000;
	private int numberOfShares = 0;
	private double mean = 0.0;
	private double variance = 0.0;
	private double standardDeviataion = 0.0;
	private String header = null;

	private ArrayList<String> date = new ArrayList<>();
	private ArrayList<Double> adjClose = new ArrayList<>();

	private ArrayList<Double> avgClose = new ArrayList<>();
	private ArrayList<Double> RSIPoints = new ArrayList<>();

	/**
	 * Executes the simulation of the stock bot.
	 */
	public void run() {

		readFile();
		calculateRSI();
		calculateMA(5);
		//updateInternalDataUsingRSI();
		System.out.println("Balance: " + balance);
		System.out.println("Number of shares: " + numberOfShares);
		getProfitOrLoss();
		writeFile();
	}

	/**
	 * Executes the simulation using a custom trading strategy.
	 */
	public void runUsingHold() {

		readFile();

	}

	/**
	 * Executes the simulation using RSI trading strategy.
	 */
	public void runUsingRSI() {

		readFile();
		calculateRSI();
		updateInternalDataUsingRSI();
		System.out.println(balance);
		System.out.println(numberOfShares);
		getProfitOrLoss();

	}

	/**
	 * Executes the simulation using moving average trading strategy.
	 */
	public void runUsingMA() {

		readFile();
		calculateMA(10);
		updateInternalDataUsingMA();
		System.out.println(balance);
		System.out.println(numberOfShares);
		getProfitOrLoss();

	}

	/**
	 * Reads a CSV file named "AMZN.csv" and stores its dates and adjusted closing values in ArrayLists.
	 */
	public void readFile() {

		try {
			// Reads in a file named "AMZN.csv" from the default workspace directory.
			BufferedReader reader = new BufferedReader(new FileReader("AMZN.csv"));
			String csvLine;
			// Skips the header of the CSV file.
			reader.readLine();
			while((csvLine = reader.readLine()) != null) {
				// Splits the readLine separating the date and adjusted closing values.
				String[] plotData = csvLine.split(",");
				if(plotData.length >= 2) {
					// Parses the values from the plotData array and adds them to the respected xValues and yValues ArrayList.
					date.add((plotData[0]));
					adjClose.add(Double.parseDouble(plotData[1]));	
				}
			} 
			reader.close();
			System.out.println("\n Values successfully loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Updates internal data after running strategies.
	 */
	//	public void updateInternalData() {
	//
	//		int sharesToBuyOrSell = (int)(numberOfShares * .10);
	//
	//		for(int i = 28; i < adjClose.size(); i++) {
	//			if(RSIPoints.get(i-28) < 30 && adjClose.get(i) < avgClose.get(i)) {
	//				buyShares(adjClose.get(i), sharesToBuyOrSell);
	//			}
	//			if(RSIPoints.get(i-28) > 70 && adjClose.get(i) > avgClose.get(i)) {
	//				sellShares(adjClose.get(i), sharesToBuyOrSell) ;
	//			}
	//		}
	//
	//	}

	/**
	 * Updates internal data by buying or selling shares after running RSI calculations.
	 */
	public void updateInternalDataUsingRSI() {

		int sharesToBuyOrSell = (int)(numberOfShares * .33);
		for(int i = 28; i < adjClose.size(); i++) {
			if(RSIPoints.get(i-28) >= 70) {
				buyShares(adjClose.get(i), sharesToBuyOrSell);
			}
			if(RSIPoints.get(i-28) <= 30) {
				sellShares(adjClose.get(i), sharesToBuyOrSell);
			}
		}

	}

	/**
	 * Updates internal data by buying or selling shares after running moving average calculations.
	 */
	public void updateInternalDataUsingMA() {

		int sharesToSell = (int)(numberOfShares * .33);
		for(int i = 0; i < adjClose.size(); i++) {
			if(adjClose.get(i) < avgClose.get(i)) {
				buyShares(adjClose.get(i), sharesToSell);
			}
			if(adjClose.get(i) > avgClose.get(i)) {
				sellShares(adjClose.get(i),sharesToSell);
			}
		}

	}

	/**
	 * Buys shares based on the current closing price and the number of shares specified.
	 *
	 * @param closingPrice The price at which shares are bought.
	 * @param sharesToBuy The number of shares to buy.
	 */
	public void buyShares(double closingPrice, int sharesToBuy) {

		double totalBuyAmount = sharesToBuy * closingPrice;
		if(balance >= totalBuyAmount) {
			numberOfShares += sharesToBuy;
			balance -= totalBuyAmount;
		}
	}

	/**
	 * Sells shares based on the current closing price and the number of shares specified.
	 *
	 * @param closingPrice The price at which shares are sold.
	 * @param sharesToBuy The number of shares to sell.
	 */
	public void sellShares(double closingPrice, int sharesToSell) {
		if(numberOfShares >= sharesToSell) {

			double totalSellAmount = sharesToSell * closingPrice;
			balance += totalSellAmount;
			numberOfShares -= sharesToSell;
		}
	}

	/**
	 * Prints the current balance to show profit or loss.
	 */
	public void getProfitOrLoss() {
		double endValue = balance;
		System.out.println(endValue);
	}

	/**
	 * Calculates the RSI points for the stock data over a 14-day period.
	 */
	public void calculateRSI() {

		int N = 14;
		// Loops through the size of the list of closing values starting from day 15.
		for (int i = N + 1; i < adjClose.size(); i++) {
			RSIPoints.add(calculateRSIPoints(i));
		}

	}

	/**
	 * Calculates the RSI for a specific day.
	 * 
	 * @param currentDay The day for which to calculate the RSI.
	 * @return The RSI value.
	 */
	public double calculateRSIPoints(int currentDay) {

		double RSI = 0.0;
		double RS = 0.0;
		double upMoves = 0.0;
		double downMoves = 0.0;
		double avgUpMoves = 0.0;
		double avgDownMoves = 0.0;
		int N = 14;

		for(int i = currentDay - N; i < currentDay ; i++) {

			double change = adjClose.get(i) - adjClose.get(i-1);
			if(change > 0) {
				upMoves += change;
			}else {
				downMoves += Math.abs(change);
			}
		}

		avgUpMoves = upMoves / N;
		avgDownMoves = downMoves / N;
		RS = avgUpMoves / avgDownMoves;
		RSI = 100 - 100 / (1 + RS);

		return RSI;
	}

	/**
	 * Calculates the moving average of adjusted close prices over a specified window around each day.
	 *
	 * @param windowValue The window size to calculate the moving average.
	 */
	public void calculateMA(int windowValue) {

		int numberOfRuns = 6;
		// Smoothes the ArrayList of y values by the specified number of runs.
		for (int i = 0; i < numberOfRuns; i++) {
			// Loops through x values.
			for (int j = 0; j < date.size(); j++) {
				// Sets the beginning constraint to either 0 or i - windowValue.
				// It determines which value is larger to prevent the window from starting out of range of the list. 
				int beginningConstraint = Math.max(0, j - windowValue);
				// Sets end constraint to either the end of the ArrayList of x values or i + windowValue.
				// It determines which value is smaller to prevent the window from ending out of range of the list.
				int endConstraint = Math.min(date.size() - 1, j + windowValue);
				// Stores the sum of the y values within the range of the window.
				double sumOfValues = 0;
				// Loops over the range from the beginning constraint to the end constraint.
				for (int k = beginningConstraint; k <= endConstraint; k++) {
					// Adds the current y value to the sum.
					sumOfValues += adjClose.get(k);
				}
				// Holds the average of the current iteration.
				double avg = sumOfValues / (double)(endConstraint - beginningConstraint + 1);
				// Sets the y value at index i to the calculated average.
				adjClose.set(j, avg);
			}
		} 
	}

	/**
	 * Writes the calculated stock data back to a CSV file named "StockData.csv".
	 */
	public void writeFile() {

		header = "Date,AverageClose,RSI";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("StockData.csv"));
			writer.write(header);
			writer.newLine();  
			for (int i = 0; i < date.size(); i++) {
				if (i < RSIPoints.size()) {
					writer.write(date.get(i + 14) + "," + avgClose.get(i).toString() + "," + RSIPoints.get(i).toString());
					writer.newLine();
				}
			}
			writer.close();
			System.out.println("\n Values successfully exported");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}