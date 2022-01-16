/**
 * 
 * Features:
 * 
 * -Distance Calculator for distances between different properties
 * -Price Estimator to estimate the price of a property
 * -Filter the properties
 * -Add pictures to enhance the listings
 * -Offer function to send/accept/decline offers
 * -Multithreading allows updating database in the background
 * -Email feature sending the information of property
 * -Using try/catch to error check the user's input
 * 
 * Major Skills:
 * 
 * -APIs (Mailing)
 * -OOP
 * -Utilization of a variety of data types
 * -file reader/file writer for saving user's / property's data
 * -Overloaded constructors and methods
 * -Multithreading
 * -Webscraping
 * -GUI (Including but not limited to JList, JScrollPane, JTabel)
 * -JFileChooser for user to select files
 * -File class to save user's selected pictures
 * -try/catch
 * 
 * 
 * Area of Concerns:
 * 
 * - I used geocoder.ca to retrieve latitude/longitude. geocoder.ca has stated out that free
 * 	 users are limited to 2,500 inquiries per day. Intense tesing may result in the usage more than 2500 inquires. The program will
 * 	 print a message if throttled, which may result in wrong data/estimation.
 * 
 * - Sometimes geocoder.ca runs slow and whenever the user access distance service there will be an delay. This includes but not
 *   limited to "Search Property", "View My Property", "View My Favourite", "Filter", and "Price Estimator"
 *   
 * - For the editable ComboBox, it seems like there is a limitation whenever user enters anything other than the recommended options.
 *   The user has to leave the box (click elsewhere) before they can add their desired entry.
 * 
 */

package realEstate;

import java.io.IOException;

public class RealEstateToolTest {

	public static void main(String[] args) throws IOException, InterruptedException {

		// Initialize properties
		new Initialize();

		//Update database
		DistanceCalculator.updateDatabase(Initialize.properties);
		
		// Create Login page
		new LoginGUI();

	}

}
