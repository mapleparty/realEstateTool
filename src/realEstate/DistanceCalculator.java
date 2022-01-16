/**
 * The class DistanceCalculator calculates the distances between properties and postal codes
 */

package realEstate;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class DistanceCalculator {

	// Stoting links
	private static String link1;
	private static String link2;

	// The FileInputStream
	private static FileInputStream input;

	final static int EARTH_RADIUS = 6371; // Final integer variable representing the radius of the Earth

	/**
	 * Read the data from the website and stores it into a text file. The website
	 * being read is geocoder.ca
	 * 
	 * Some external sources are used to help me build this method. (The source is in Mandarin)
	 * Source Link: https://robertvmp.pixnet.net/blog/post/26585200
	 */
	private static void readWebPage(int index, String link) {

		int chunksize = 4096;
		byte[] chunk = new byte[chunksize];
		int count;

		try {
			// Generate the URL
			URL pageURL = new URL(link);

			// BufferedInputStream and BufferedOutputStream
			BufferedInputStream input = new BufferedInputStream(pageURL.openStream());
			BufferedOutputStream output = new BufferedOutputStream(
					new FileOutputStream("Postal Code Web Data/" + String.valueOf(index) + ".txt", false));

			// Read the website and write it into the text file
			while ((count = input.read(chunk, 0, chunksize)) != -1) {
				output.write(chunk, 0, count);
			}

			// Close BufferedInputStream and BufferedOutputStream
			input.close();
			output.close();

		} catch (IOException e) {

			//If the webpage reading fails
			System.out.println("Throttled! Cannot access the website! Distance data may be WRONG!");

		}
	}

	/**
	 * Retrieve the latitude of the postal code in the text file downloaded
	 * 
	 * @param index
	 * @return
	 * @throws IOException
	 */
	private static double getLatitude(int index) throws IOException {

		try {
			// Use the text file
			input = new FileInputStream("Postal Code Web Data/" + String.valueOf(index) + ".txt");

			// BufferedReader
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			// Skip through line 1 to line 20
			for (int i = 0; i < 20; i++) {
				reader.readLine();
			}

			// Retrieve line 21
			String titleLine = reader.readLine();

			// Retrieve line 21 word index 22 to 29
			String latitudeString = titleLine.substring(21, 29);

			// Cast string to double
			double latitude = Double.parseDouble(latitudeString);

			return latitude;

			// If file is not found
		} catch (FileNotFoundException e) {

			// If user entered the wrong postal code
		} catch (StringIndexOutOfBoundsException k) {

			// Line 21 will not be read properly and therefore the substring method will not
			// be working

		}

		// return -1 if failed
		return -1;

	}

	/**
	 * Retrieve the longitude of the postal code in the text file downloaded
	 * 
	 * @param index
	 * @return
	 * @throws IOException
	 */
	private static double getLongitude(int index) throws IOException {

		try {
			// Use the text file
			input = new FileInputStream("Postal Code Web Data/" + String.valueOf(index) + ".txt");

			// BufferedReader
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			// Skip through line 1 to line 20
			for (int i = 0; i < 20; i++) {
				reader.readLine();
			}

			// Retrieve line 21
			String titleLine = reader.readLine();

			// Retrieve line 21 word index 32 to 40
			String latitudeString = titleLine.substring(31, 40);

			// Cast string to double
			double longitude = Double.parseDouble(latitudeString);

			return longitude;

			// If file is not found
		} catch (FileNotFoundException e) {
			// If user entered the wrong postal code

		} catch (StringIndexOutOfBoundsException k) {

			// Line 21 will not be read properly and therefore the substring method will not
			// be working

		}
		
		// return -1 if failed
		return -1;

	}

	/**
	 * Get the distance between two properties
	 * 
	 * @param property1
	 * @param property2
	 * @return
	 * @throws IOException
	 */
	public static double getDistance(Property property1, Property property2) throws IOException {

		// Get the postal codes
		String postalCode1 = property1.getPostalCode();
		String postalCode2 = property2.getPostalCode();

		// Generate the web link
		link1 = "https://geocoder.ca/?locate=" + postalCode1 + "&geoit=GeoCode";
		link2 = "https://geocoder.ca/?locate=" + postalCode2 + "&geoit=GeoCode";

		// Read the web page
		readWebPage(0, link1);
		readWebPage(1, link2);

		// Return the caluculated distance
		return (calculateDistance(getLatitude(0), getLongitude(0), getLatitude(1), getLongitude(1)));

	}

	/**
	 * Get the distance list from one postal code to all properties
	 * 
	 * @param postalCode
	 * @return
	 * @throws IOException
	 */

	public static ArrayList<Double> getDistanceList(String postalCode) throws IOException {
		
		//If there is space
		if(postalCode.contains(" ")) {
			
			//Remove the space on index 3
			postalCode=postalCode.substring(0,3)+postalCode.substring(4,7);
			
		}
		
		// Generate the web link
		link1 = "https://geocoder.ca/?locate=" + postalCode + "&geoit=GeoCode";

		// Read the webpage
		readWebPage(-1, link1);

		// Create a new ArrayList
		ArrayList<Double> distanceList = new ArrayList<Double>();

		// Loop through all the properties
		for (int x = 0; x < Initialize.properties.size(); x++) {

			// Add the calculated distance
			distanceList.add(calculateDistance(getLatitude(-1), getLongitude(-1), getLatitude(x), getLongitude(x)));
		}

		// Returrn the ArrayList
		return distanceList;

	}

	/**
	 * Update the database of all existing properties longitude and latitude using
	 * multithreading
	 * 
	 * @param allProperties
	 */
	public static void updateDatabase(ArrayList<Property> allProperties) {

		// Create a new thread
		UpdateDatabase object = new UpdateDatabase(allProperties);

		// Starts the thread
		object.start();

	}

	/**
	 * Using the latitude and the longitude to calculate the distance
	 * 
	 * @param latitude1
	 * @param longitude1
	 * @param latitude2
	 * @param longitude2
	 * @return
	 */
	private static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {

		if (latitude1 == -1 || longitude1 == -1 || latitude2 == -1 || longitude2 == -1) {
			return -1;
		}

		// Convert each arguments from degree to radian
		double latitude1Radian = Math.toRadians(latitude1);
		double longitude1Radian = Math.toRadians(longitude1);
		double latitude2Radian = Math.toRadians(latitude2);
		double longitude2Radian = Math.toRadians(longitude2);

		// Calculate the distance using the Great Circle Distance Formula
		double distance = EARTH_RADIUS
				* Math.acos(Math.sin(latitude1Radian) * Math.sin(latitude2Radian) + Math.cos(latitude1Radian)
						* Math.cos(latitude2Radian) * Math.cos(longitude1Radian - longitude2Radian));

		//Return the final result
		return distance;

	}

	/**
	 * multithreading class that allows the database to be updated in the background
	 */

	private static class UpdateDatabase extends Thread {

		// Property ArrayList
		ArrayList<Property> allProperties = new ArrayList<Property>();

		/**
		 * Constructor Takes in the properties updating
		 * 
		 * @param allProperties
		 */
		public UpdateDatabase(ArrayList<Property> allProperties) {

			this.allProperties = allProperties;

		}

		/**
		 * The method that starts the thread
		 */
		public void run() {
			//try-catch
			try {
				//Loop through all the properties from the end (To make sure new properties are updated first
				for (int x = allProperties.size() - 1; x >= 0; x--) {
					
					//generate link
					String link = "https://geocoder.ca/" + allProperties.get(x).getPostalCode() + "?bounds";
					
					//Stops for 500ms, or otherwise the website will block
					Thread.sleep(500);
					
					//Read the webpage
					readWebPage(x, link);

				}
			} catch (Exception e) { //Catch the exception

			}
		}
	}

}
