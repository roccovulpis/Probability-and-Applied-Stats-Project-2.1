
/**
 * The StockSellerTester class contains the main method for testing the StockSeller simulation.
 * This class creates an instance of StockSeller and initiates a stock trading simulation.
 * To test different trading strategies, the corresponding methods can be uncommented.
 * 
 * @author Rocco Vulpis
 * @version 4/25/2024
 */
public class StockSellerTester {

	public static void main(String[] args) {
		StockSeller bot = new StockSeller();
		//bot.runUsingRSI();
		//bot.runUsingMA();
		bot.run();
	}
	
}
