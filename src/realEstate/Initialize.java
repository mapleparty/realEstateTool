/**
 * The class Initialize initialize the properties and the user
 */

package realEstate;

import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;

public class Initialize {

	// The current user
	public static User currentUser;

	// The properties
	public static ArrayList<Property> properties;

	// If the user's credential matched
	private static boolean matched;

	// User information
	private static String username;
	private static String password;
	private static String firstName;
	private static String lastName;
	private static boolean sellerMode;
	private static ArrayList<Property> userProperties;
	private static int index;
	private static int imageQuantity;

	// Property Information
	private static String address;
	private static String postalCode;
	private static ArrayList<String> features = new ArrayList<String>();
	private static int numBedroom;
	private static int numBath;
	private static String propertyType;
	private static int size;
	private static int price;
	private static boolean sold;
	private static int offerQuantity;
	private static ArrayList<Offer> offers = new ArrayList<Offer>();
	private static ArrayList<ImageIcon> image = new ArrayList<ImageIcon>();

	/**
	 * The constuctor. 
	 * Initialize the properties, the variables and the ArrayList
	 */
	public Initialize() {
		index = 0;
		properties = new ArrayList<Property>();
		userProperties = new ArrayList<Property>();
		initializeProperty();
	}

	/**
	 * Initialize the properties
	 */

	private void initializeProperty() {

		// try-catch
		try {
			
			do {
				// Start with new ArrayList
				features = new ArrayList<String>();
				image = new ArrayList<ImageIcon>();
				offers=new ArrayList<Offer>();

				// Start with 0 images
				imageQuantity = 0;

				// New scanner
				Scanner input = new Scanner(new File("PropertyData/" + index + "/data.txt"));

				// Seperate each argument with ",\n"
				input.useDelimiter(",\n");

				// Input the data
				address = input.next();
				postalCode = input.next();
				numBedroom = input.nextInt();
				numBath = input.nextInt();
				propertyType = input.next();
				size = input.nextInt();
				price = input.nextInt();
				imageQuantity = input.nextInt();

				// Input the rest data as features
				while (input.hasNext()) {
					
					//Add feature
					features.add(input.next());
				}

				// Close the input
				input.close();

				// Import the images
				for (int x = 0; x < imageQuantity; x++) {

					//Create new File object
					File file = new File("PropertyData/" + index + "/" + x + ".png");
					
					//The link of the image with png
					String link = "PropertyData/" + index + "/" + x + ".png";
					
					//If the file does not exist
					if (!file.exists()) {
						
						//The link of the image with jpg
						link = "PropertyData/" + index + "/" + x + ".jpg";
					}
					
					//Add the image
					image.add(new ImageIcon(link));
				}

				// New scanner
				input = new Scanner(new File("PropertyData/" + index + "/offer.txt"));

				// Seperate each argument with ",\n"
				input.useDelimiter(",\n");

				// Input the data
				sold = input.nextBoolean();
				offerQuantity = input.nextInt();

				// Input the rest data as offers
				for (int x = 0; x < offerQuantity; x++) {
					
					//Add offer
					offers.add(new Offer(input.next(), input.nextInt(), input.nextBoolean(), input.nextBoolean()));
				}

				// Add the property
				properties.add(new Property(address, postalCode, features, numBedroom, numBath, propertyType, size,
						price, image, offers, sold));

				// Starts with new index
				index++;

				// Until all properties are initialized
			} while (new File("PropertyData/" + index + "/data.txt").exists());

		} catch (FileNotFoundException e) { // If file is not found

		}

	}

	/**
	 * Initialize the user with user's entered credential
	 * 
	 * @param usernameEntered
	 * @param passwordEntered
	 * @return
	 */

	public static boolean initializeUser(String usernameEntered, String passwordEntered) {

		// try-catch
		try {
			// New scanner
			Scanner input = new Scanner(new File("UserData/LoginInfo.txt"));

			// Seperate each argument with ","
			input.useDelimiter(",");

			// Loop until no data or the user is match
			while (input.hasNext() && !matched) {

				// Input the data
				username = input.next();
				password = input.next();

				// if there is the line change
				if (username.contains("\n")) {
					
					//Remove the line change
					username = username.substring(1);
				}

				// Check if the entered credential is matched
				if (username.equals(usernameEntered) && password.equals(passwordEntered)) {

					matched = true; // matched
					currentUser = new User(username, password); // Initialize the user

				}

			}
			input.close(); // Close the input

			// If matched
			if (matched) {

				// New scanner
				input = new Scanner(new File("UserData/" + username + ".txt"));

				// Seperate each argument with ","
				input.useDelimiter(",");

				// Input the data
				firstName = input.next();
				lastName = input.next();
				sellerMode = Boolean.parseBoolean(input.next());

				// Seperate each argument with ",\n"
				input.useDelimiter(",\n");

				// Loop unitl no new data
				while (input.hasNext()) {

					// Add the user's favourite list/listings
					userProperties.add(properties.get(input.nextInt()));

				}

				// set the user's info
				currentUser.setFirstName(firstName);
				currentUser.setLastName(lastName);
				currentUser.setSellerMode(sellerMode);
				currentUser.setProperties(userProperties);

				// Open home screen
				new HomeGUI(currentUser);

				// Report an user is created
				return true;
			}

			// Report an user is not created
			return false;

		} catch (FileNotFoundException e) { // If no file is found

			// Report an user is not created
			return false;

		}
	}

}
