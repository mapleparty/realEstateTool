/**
 * The class SaveUserData saves the user's data in text files
 */
package realEstate;

import java.io.*;
import java.util.*;

public class SaveUserData {

	//ArrayList used in the processing
	private static ArrayList<Property> userProperties=new ArrayList<Property>();
	
	//File writer
	private static FileWriter writer;


	/**
	 * The method to create an new account
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param password
	 * @param sellerMode
	 * @return
	 */
	public static boolean createAccount(String firstName, String lastName, String username, String password,
			boolean sellerMode) {

		//Read the file with the same username
		File file = new File("UserData/" + username + ".txt");

		//If the file exists
		if (file.exists()) {
			
			//Report the process unsuccessful due to duplicated user name
			return false;
			
		} else {

			
			try {
				
				//Write in the LoginInfo, without deleting originals
				writer = new FileWriter("UserData/LoginInfo.txt", true);
				
				//write in the login credetials
				writer.write("\n" + username + "," + password + ",");
				
				//close the writer
				writer.close();

				//Create a new file with the entered username
				File userFile = new File("UserData/" + username + ".txt");
				userFile.createNewFile();
				
				//Write in the file
				writer = new FileWriter(userFile);
				
				//The first name, last name, and whether the user is a seller
				writer.write(firstName + "," + lastName + "," + Boolean.toString(sellerMode));
				writer.close();

				//Initialize the user
				Initialize.initializeUser(username, password);

				//Report the process successful
				return true;
			} catch (IOException e) {

				//Report the process unsuccessful
				return false;
			}

		}

	}
	
	/**
	 * The method used when buyers add a property to their favourite list,
	 * or when sellers create a property listing
	 * @param currentUser
	 * @param property
	 * @return
	 */
	public static boolean addProperty(User currentUser, Property property) {
		
		//Get the original property ArrayList
		userProperties=currentUser.getProperties();
		
		//Check if already contains
		if(userProperties.contains(property)) {
			
			//Process unsuccessful
			return false;
		}
		
		//Add the property in the ArrayList
		userProperties.add(property);
		
		//Add back the ArrayList to the user
		currentUser.setProperties(userProperties);
		
		//try-catch
		try {
			
			//new writer for user data
			FileWriter writer = new FileWriter("UserData/" + currentUser.getUsername()+ ".txt", true);
			
			//Write in the index of the property
			writer.write(",\n"+Initialize.properties.indexOf(property));
			
			//Close the writer
			writer.close();
			
		} catch (IOException e) {
			
			//Process unsuccessful
			return false;
		}
		
		//Process successful
		return true;
	}

}
