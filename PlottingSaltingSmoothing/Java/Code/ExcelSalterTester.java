/**
 * The ExcelSalterTester class contains the main method for salting the plot.
 * 
 * It creates an instance of the ExcelSalter class and salts the plot points by calling its run method and passing 
 * in a specified adjustment range.
 * 
 * @author Rocco Vulpis
 * @version 4/25/2024
 */

public class ExcelSalterTester {

	public static void main(String[] args) {
		
		ExcelSalter salter = new ExcelSalter();
		salter.run(80, 250);
		
	}
}
