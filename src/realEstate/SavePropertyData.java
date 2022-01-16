/**
 * The class SavePropertyData saves the property data to text files
 */

package realEstate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class SavePropertyData {

	//Property
	private static Property currentProperty;
	
	//ImageIcon ArrayList for images
	private static ArrayList<ImageIcon> image=new ArrayList<ImageIcon>();
	
	//File ArrayList for images
	private static ArrayList<File> photoFile;


	/**
	 * The method to create a new property
	 * @param currentUser
	 * @param address
	 * @param postalCode
	 * @param features
	 * @param numBedroom
	 * @param numBath
	 * @param propertyType
	 * @param size
	 * @param price
	 * @param imageFile
	 */
	public static void newProperty(User currentUser, String address, String postalCode, ArrayList<String> features, int numBedroom,
			int numBath, String propertyType, int size, int price, ArrayList<File> imageFile) {

		image=new ArrayList<ImageIcon>();
		
		//Save the imageFile ArrayList to photoFile
		photoFile=imageFile;
		
		//Run through each photo file
		for(int x=0; x<photoFile.size(); x++) {
			
			//Convert that to ImageIcon ArrayList
			image.add(new ImageIcon(imageFile.get(x).getPath()));
		}
		
		//Create an new offer ArrayList
		ArrayList<Offer>offer=new ArrayList<Offer>();
		
		//Create the new property
		currentProperty = new Property(address, postalCode, features, numBedroom, numBath, propertyType, size,
				price, image, offer, false);
		
		//Add the property to the properties ArrayList
		Initialize.properties.add(currentProperty);
		
		//Add the property to user's data
		SaveUserData.addProperty(currentUser, currentProperty);
		
		//Save the property
		saveProperty(currentProperty);
	}
	
	/**
	 * Save the property data to a text file
	 * @param property
	 */
	public static void saveProperty(Property property) {
		
		//Generate the saving link
		String link= "PropertyData/"+ Initialize.properties.indexOf(property);
		
		//Create a folder with the link
		File file = new File(link);
		file.mkdir();
		
		//FileWriter
		FileWriter writer;
		
		try { //try-catcg
			
			//Write in the link with a text file called data.txt
			//If an existing file exists, overwrite
			writer = new FileWriter(link+"/data.txt", false);
			
			//Write in the data
			writer.write(property.getAddress()+",\n");
			writer.write(property.getPostalCode()+",\n");
			writer.write(property.getNumBedroom()+",\n");
			writer.write(property.getNumBath()+",\n");
			writer.write(property.getPropertyType()+",\n");
			writer.write(property.getSize()+",\n");
			writer.write(property.getPrice()+",\n");
			writer.write(property.getImage().size()+",\n"); //How many images
			
			//Run through each feature
			for(int x=0; x<property.getFeatures().size() ; x++) {
				
				//Write in the feature
				writer.write(property.getFeatures().get(x)+",\n");
			}
			
			//Close the writer
			writer.close();
			
			//Write in the link with a text file called offer.txt
			//If an existing file exists, overwrite
			writer= new FileWriter(link+"/offer.txt", false);
			
			//Write in whether the property is sold
			writer.write(Boolean.toString(property.isSold())+",\n");
			
			//How many offers
			writer.write(property.getOffers().size()+",\n");
			
			//Run through each offer
			for(int x=0; x<property.getOffers().size(); x++) {
				
				//Save the current over
				Offer offer=property.getOffers().get(x);
				
				//Write in the offer data
				writer.write(offer.getMaker()+",\n"+offer.getAmount()+",\n"+Boolean.toString(offer.isAccepted())+",\n"+Boolean.toString(offer.isRefused())+",\n");
			}
			
			//Close the writer
			writer.close();
		
			
			
		} catch (IOException e) {
			
		
		}
		
		//If there's photoFile
		if(photoFile !=null) {
			
			//Run through each file
			for(int x=0; x<photoFile.size(); x++) {
				
				try { //try-catch
					
					//If it's a png
					if(photoFile.get(x).toPath().endsWith("png")) {
						
						//Generate the file with the link ending png
						file=new File(link+"/"+x+".png");
						
					//If it's a jpg
					}else {
						
						//Generate the file with the link ending jpg
						file=new File(link+"/"+x+".jpg");
					}
					
					//Copy the file to the generated link
					Files.copy(photoFile.get(x).toPath(), file.toPath());
					
				} catch (IOException e) {
					
				}
			}
		}
		
	}
	

}
