/**
 * The Property class that can be used to create Property objects
 */

package realEstate;

import java.util.ArrayList;

import javax.swing.*;

public class Property {

	//Fields
	private String address; //Address
	private String postalCode; //Postal Code
	private int numBedroom; //Number of bedroom
	private int numBath; //Number of bathroom
	private String propertyType; //The type of the property (house/condo)
	private int size; //Size
	private int price; //Price
	private boolean sold; //whether the property is sold

	ArrayList<String> features = new ArrayList<String>(); //Features of the property
	private ArrayList<ImageIcon> image=new ArrayList<ImageIcon>(); //Images of the property
	private ArrayList<Offer> offers=new ArrayList<Offer>(); //Offers of the property

	/**
	 * Constructor
	 * Creating a new property
	 * @param address
	 * @param postalCode
	 * @param features
	 * @param numBedroom
	 * @param numBath
	 * @param propertyType
	 * @param size
	 * @param price
	 * @param image
	 * @param offers
	 * @param sold
	 */
	public Property(String address, String postalCode, ArrayList<String> features, int numBedroom, int numBath,
			String propertyType, int size, int price, ArrayList<ImageIcon> image, ArrayList<Offer> offers, boolean sold) {
		
		//Initialize Fields
		this.address = address;
		this.postalCode = postalCode;
		this.features = features;
		this.numBedroom = numBedroom;
		this.numBath = numBath;
		this.propertyType = propertyType;
		this.size = size;
		this.price = price;
		this.image=image;
		this.offers=offers;
		this.sold=sold;
	}

	/**
	 * Getter for the address
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Getter for the postal code
	 * @return
	 */

	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Getter for the features
	 * @return
	 */
	public ArrayList<String> getFeatures() {
		return features;
	}

	/**
	 * Getter for the number of bedrooms
	 * @return
	 */
	public int getNumBedroom() {
		return numBedroom;
	}

	/**
	 * Getter for the number of bathrooms
	 * @return
	 */
	public int getNumBath() {
		return numBath;
	}

	/**
	 * Getter for the type of the property
	 * @return
	 */
	public String getPropertyType() {
		return propertyType;
	}

	/**
	 * getter for the size of the property
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Getter for the price of the property
	 * @return
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Getter of whether the property is sold
	 * @return
	 */
	public boolean isSold() {
		return sold;
	}

	/**
	 * Setter of whether the property is sold
	 * @param sold
	 */
	public void setSold(boolean sold) {
		this.sold = sold;
	}

	/**
	 * Getter of the image ArrayList
	 */
	public ArrayList<ImageIcon> getImage() {
		return image;
	}

	/**
	 * Setter of the image ArrayList
	 * @param image
	 */
	public void setImage(ArrayList<ImageIcon> image) {
		this.image = image;
	}

	/**
	 * Gettter of the offers ArrayList
	 */
	public ArrayList<Offer> getOffers() {
		return offers;
	}

	/**
	 * Setter of the offers ArrayList
	 */
	public void setOffers(ArrayList<Offer> offers) {
		this.offers = offers;
	}
	
	
	
}
