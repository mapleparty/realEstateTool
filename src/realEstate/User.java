/**
 * The user class can be used to create User object.
 */

package realEstate;

import java.util.ArrayList;

public class User {

	// Fields
	private String username; // The username for the user
	private String password; // The password for the user
	private String firstName;
	private String lastName;
	private boolean sellerMode; // Whether the user is seller

	// ArrayList that store the user's properties
	ArrayList<Property> properties = new ArrayList<Property>(); // Sellers - their properties. Buyers- favourite list

	/**
	 * Constructor to create new users
	 * Overloaded
	 * @param username
	 * @param password
	 */

	public User(String username, String password) {

		//Initialize
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Constructor to create new users
	 * Overloaded
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param sellerMode
	 */

	public User(String username, String password, String firstName, String lastName, boolean sellerMode) {

		//Initialize
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sellerMode = sellerMode;
	}

	/**
	 * Getter for the username.
	 * 
	 * @return
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * Getter for the password.
	 * 
	 * @return
	 */

	public String getPassword() {
		return password;
	}

	/**
	 * Getter for sellerMode.
	 * 
	 * @return
	 */

	public boolean isSellerMode() {
		return sellerMode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Setter for sellerMode
	 * 
	 * @param sellerMode
	 */

	public void setSellerMode(boolean sellerMode) {
		this.sellerMode = sellerMode;
	}

	/**
	 * Getter for properties
	 * 
	 * @return
	 */
	public ArrayList<Property> getProperties() {
		return properties;
	}

	/**
	 * Setter for properties
	 * 
	 * @param properties
	 */

	public void setProperties(ArrayList<Property> properties) {
		this.properties = properties;
	}

}
