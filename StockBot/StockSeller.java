import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class StockSeller {

	private double startingBalance = 10000;
	private double balance = 10000;
	private int numberOfShares = 0;
	private double mean = 0.0;
	private double variance = 0.0;
	private double standardDeviataion = 0.0;

	private ArrayList<String> date = new ArrayList<>();
	private ArrayList<Double> adjClose = new ArrayList<>();

	private ArrayList<Double> avgClose = new ArrayList<>();
	private ArrayList<Double> RSIPoints = new ArrayList<>();

	public void run() {
		readFile();
		calculateRSI();
		calculateMA(10);
		updateInternalData();
		System.out.println("Balance: " + balance);
		System.out.println("Number of shares: " + numberOfShares);
		getProfitOrLoss();
	}
	
	public void runUsingHold() {
		readFile();

	}

	public void runUsingRSI() {
		readFile();
		calculateRSI();
		updateInternalDataUsingRSI();
		System.out.println(balance);
		System.out.println(numberOfShares);
		getProfitOrLoss();
	}

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
			BufferedReader reader = new BufferedReader(new FileReader("INTC.csv"));
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

	public void updateInternalData() {

		int sharesToBuyOrSell = (int)(numberOfShares * .10);

		for(int i = 28; i < adjClose.size(); i++) {
			if(RSIPoints.get(i-28) < 30 && adjClose.get(i) < avgClose.get(i)) {
				buyShares(adjClose.get(i), sharesToBuyOrSell);
			}
			if(RSIPoints.get(i-28) > 70 && adjClose.get(i) > avgClose.get(i)) {
				sellShares(adjClose.get(i), sharesToBuyOrSell) ;
			}
		}

	}

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

	public void buyShares(double closingPrice, int sharesToBuy) {
		
		double totalBuyAmount = sharesToBuy * closingPrice;
		
		if(balance >= totalBuyAmount) {
			
			
			numberOfShares += totalBuyAmount;
			balance -= totalBuyAmount;
		}
	}

	public void sellShares(double closingPrice, int sharesToSell) {
		if(numberOfShares >= sharesToSell) {
		
		double totalSellAmount = sharesToSell * closingPrice;
		balance += totalSellAmount;
		numberOfShares -= sharesToSell;
		}
	}

	public void getProfitOrLoss() {
		double endValue = balance + startingBalance;
		System.out.println(endValue);
	}

	// Loops through the size of the list storing the closing values. 
	public void calculateRSI() {

		int N = 14;

		// Loops through the size of the list of closing values starting from day 15.
		for (int i = N + 1; i < adjClose.size(); i++) {
			RSIPoints.add(calculateRSIPoints(i));
		}
	}

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

	public void calculateMA(int windowValue) {

		for (int i = 0; i < adjClose.size(); i++) {
			int beginningConstraint = Math.max(0, i - windowValue);
			int endConstraint = Math.min(date.size() - 1, i + windowValue);

			double sumOfValues = 0;
			for (int j = beginningConstraint; j <= endConstraint; j++) {
				sumOfValues += adjClose.get(j);
			}

			double avg = sumOfValues / (endConstraint - beginningConstraint);
			avgClose.add(avg);
		} 
	}

}
