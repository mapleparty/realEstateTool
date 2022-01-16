/**
 * The Class Email sends email containing property info to clients
 * Some external resources are used to accomplish the class
 * 
 * Link of the sources: https://www.javatpoint.com/example-of-sending-email-using-java-mail-api
 * https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail
 * https://stackoverflow.com/questions/28687514/how-to-send-mail-with-multiple-attachments-in-java
 */

package realEstate;

import java.io.*;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

	// The email address used to send out email
	private final static String user = "realestatetoolemail@gmail.com";

	// The password of the email address
	private final static String password = "compscifinal";

	// The processing file that will be attached to the email
	private static File file;

	/**
	 * The method sendProperty send the information of the property selected to the
	 * email address entered.
	 * 
	 * External resources are used to help build this method. The link is provided
	 * in the header comment of this class
	 * 
	 * @param property
	 * @param email
	 */
	public static void sendProperty(Property property, String email) {

		// Get system properties
		Properties properties = System.getProperties();

		// Email server setup
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Get the default session object, but override the user/password
		Session session = Session.getDefaultInstance(properties, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try { // try-catch

			// Create MimeMessage object
			MimeMessage message = new MimeMessage(session);

			// Create Multipart objects for adding different components
			Multipart multipart = new MimeMultipart();

			// Set message from the email address sent
			message.setFrom(new InternetAddress(user));

			// Set message to the user's input email
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			// Set the subject of the email
			message.setSubject("Your Requested Property Information");

			// BodyPart object for text
			BodyPart part1 = new MimeBodyPart();

			// Text
			String text = "";

			// Adding information
			text += "Address: " + property.getAddress() + "\n";
			text += "Postal Code: " + property.getPostalCode() + "\n";
			text += "Number of Bedrooms: " + property.getNumBedroom() + "\n";
			text += "Number of Bathrooms: " + property.getNumBath() + "\n";
			text += "Type: " + property.getPropertyType() + "\n";
			text += "Size: " + property.getSize() + "  sqft\n";
			text += "Price: $" + property.getPrice() + "\n";

			// If property is sold
			if (property.isSold()) {

				// Add that it is sold
				text += "This property is sold!\n\n";

				// If it is not sold
			} else {

				// Add that is is not yet sold
				text += "This property is open for purchasing!\n\n";
			}

			// Features title
			text += "Features: \n";

			// Run through each feature
			for (int x = 0; x < property.getFeatures().size(); x++) {

				// Add the feature
				text += property.getFeatures().get(x) + "\n";
			}
			
			//Add the ending
			text+="\n Thanks for using Real Estate Tool!";

			// Set part 1 the text of the email
			part1.setText(text);

			// Add the part to the multipart
			multipart.addBodyPart(part1);

			// Tha path of property data
			String path = "PropertyData/" + Initialize.properties.indexOf(property) + "/";

			// Run through each image
			for (int x = 0; x < property.getImage().size(); x++) {

				// Try png version
				file = new File(path + x + ".png");

				// If not exist
				if (!file.exists()) {

					// It has to be jpg version
					file = new File(path + x + ".jpg");
				}

				// attach the file to a new part, and add the part to the multipart
				attachFile(file, multipart, new MimeBodyPart());
			}

			// Set the content the multipart
			message.setContent(multipart);

			// Send the message
			Transport.send(message);

		} catch (MessagingException e) { //catch exception

		}

	}

	/**
	 * The method to attach files to the body part, and add the body part to the
	 * multipart
	 * 
	 * @param file
	 * @param multipart
	 * @param messageBodyPart
	 * @throws MessagingException
	 */
	private static void attachFile(File file, Multipart multipart, MimeBodyPart messageBodyPart)
			throws MessagingException {

		// New DataSource object with the file
		DataSource source = new FileDataSource(file);

		// Set DataHandler with the DataSource
		messageBodyPart.setDataHandler(new DataHandler(source));

		// Set the file name sent
		messageBodyPart.setFileName("Picture_" + file.getName());

		// Add the body part to the multipart
		multipart.addBodyPart(messageBodyPart);
	}

}
