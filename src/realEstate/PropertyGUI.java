/**
 * The class PropertyGUI creates a page to display the information of a property
 */
package realEstate;

import java.awt.*;
import java.io.*;
import java.nio.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class PropertyGUI extends JFrame implements ActionListener {

	// JFrame
	private JFrame frame = new JFrame();

	// JPanel
	private JPanel screen = new JPanel();

	// JLabel for titles/information
	private JLabel title = new JLabel();
	private JLabel addressLabel = new JLabel();
	private JLabel postalCodeLabel = new JLabel();
	private JLabel bedroomLabel = new JLabel();
	private JLabel bathLabel = new JLabel();
	private JLabel typeLabel = new JLabel();
	private JLabel sizeLabel = new JLabel();
	private JLabel priceLabel = new JLabel();
	private JLabel offerTitle = new JLabel();
	private JLabel offerInfoLabel = new JLabel();
	private JLabel moneyLabel = new JLabel();
	private JLabel pictureLabel = new JLabel();

	// JTextField for offer entry
	private JTextField offerField = new JTextField();

	// JButton for actions
	private JButton leftButton = new JButton();
	private JButton rightButton = new JButton();
	private JButton acceptButton = new JButton();
	private JButton declineButton = new JButton();
	private JButton makeOfferButton = new JButton();
	private JButton estimatePriceButton = new JButton();
	private JButton addProperty = new JButton();
	private JButton closeFrameButton = new JButton();
	private JButton sendEmailButton = new JButton();

	// The image index
	private int index = 0;

	// JList for features displaying
	private JList<String> featureList = new JList<String>();

	// JScrollPane to make JList scrollable
	private JScrollPane listScroller = new JScrollPane();

	// ImageIcon
	private final ImageIcon leftImage = new ImageIcon(
			new ImageIcon("Images/left.png").getImage().getScaledInstance(50, 50, 0)); // left arrow
	private final ImageIcon rightImage = new ImageIcon(
			new ImageIcon("Images/right.png").getImage().getScaledInstance(50, 50, 0)); // right arrow
	private final ImageIcon emailImage = new ImageIcon(
			new ImageIcon("Images/emailImage.png").getImage().getScaledInstance(50, 50, 0)); // email icon

	// User
	private User currentUser;

	// Property
	private Property currentProperty;

	// The offer scenerio
	private int offerScenerio;

	// The current offer
	private Offer offer;

	// whether there's a previous offer
	private boolean previousOffer;

	/**
	 * The constructor Initialize the fields and create screen
	 * 
	 * @param currentUser
	 * @param currentProperty
	 */
	public PropertyGUI(User currentUser, Property currentProperty) {

		// Fields
		this.currentUser = currentUser;
		this.currentProperty = currentProperty;

		// Create frame
		Build.buildFrame(frame, screen);

		panelDesign();

		// Repaint frame
		frame.repaint();
	}

	/**
	 * The method to create JPanel, JLabel, JButton, JTextField, JList and
	 * JScrollPane
	 */
	private void panelDesign() {

		// JPanel
		Build.buildPanel(screen);

		// JLabel for title/information
		Build.createLabel(screen, title, "Property", 20, 20, 500, 60, 52, true);
		Build.createLabel(screen, addressLabel, "Address: " + currentProperty.getAddress(), 50, 90, 700, 60, 36, false);
		Build.createLabel(screen, postalCodeLabel, "Postal Code: " + currentProperty.getPostalCode(), 50, 160, 500, 60,
				36, false);
		Build.createLabel(screen, bedroomLabel, "Bedroom: " + currentProperty.getNumBedroom(), 50, 230, 250, 60, 36,
				false);
		Build.createLabel(screen, bathLabel, "Bath: " + currentProperty.getNumBath(), 350, 230, 300, 60, 36, false);
		Build.createLabel(screen, typeLabel, "Type: " + currentProperty.getPropertyType(), 50, 300, 300, 60, 36, false);
		Build.createLabel(screen, sizeLabel, "Size: " + currentProperty.getSize() + "  sqft", 50, 370, 500, 60, 36,
				false);
		Build.createLabel(screen, priceLabel, "Price: $" + currentProperty.getPrice(), 50, 440, 500, 60, 36, false);
		Build.createLabel(screen, pictureLabel, "No Image", 730, 100, 460, 260, 36, false);
		Build.createLabel(screen, offerTitle, "Offer", 730, 390, 200, 60, 36, true);

		// Specific colour setting
		title.setForeground(new java.awt.Color(70, 190, 255));

		// If there's image
		if (currentProperty.getImage().size() != 0) {

			// Display the first image
			pictureLabel.setIcon(new ImageIcon(
					currentProperty.getImage().get(0).getImage().getScaledInstance(460, 260, Image.SCALE_SMOOTH)));
		}

		// JList
		featureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Can only choose one at a time

		// Set the data
		featureList
				.setListData(currentProperty.getFeatures().toArray(new String[currentProperty.getFeatures().size()]));
		featureList.setLayoutOrientation(JList.VERTICAL); // veritical list
		featureList.setFont(new Font("Sansserif", Font.PLAIN, 20)); // font
		featureList.setBounds(50, 530, 500, 120); // size/location
		screen.add(featureList);// add to the panel

		// JScrollPane to make JList scrollable
		listScroller = new JScrollPane(featureList);
		listScroller.setBounds(50, 530, 500, 120); // size/location
		screen.add(listScroller); // Add listScroller to screen

		// JButton for actions
		Build.createButton(screen, closeFrameButton, this, "CLOSE", 1050, 30, 150, 50, 36, true);
		Build.createButton(screen, leftButton, this, leftImage, 670, 220, 50, 50);
		Build.createButton(screen, rightButton, this, rightImage, 1200, 220, 50, 50);
		Build.createButton(screen, sendEmailButton, this, emailImage, 950, 30, 50, 50);

		// For Icon Button, set no border painted
		sendEmailButton.setBorderPainted(false);
		leftButton.setBorderPainted(false);
		rightButton.setBorderPainted(false);

		// Specfic Color Setting
		closeFrameButton.setForeground(Color.RED);

		// If the user is buyer
		if (!currentUser.isSellerMode()) {
			// Create a button to add the property to favourite list
			Build.createButton(screen, addProperty, this, "FAVOURITE", 750, 30, 250, 50, 36, true);
			sendEmailButton.setBounds(650, 30, 50, 50);
		}

		// Build the offer section
		checkOffer();

	}

	/**
	 * The method is run whenever a button is clicked
	 */
	public void actionPerformed(ActionEvent event) {

		// If left button is clicked
		if (event.getSource() == leftButton) {

			// If the displayed picture is not the first pic
			if (index != 0) {

				// Show the previous pic
				index--;

				// Display
				pictureLabel.setIcon(new ImageIcon(currentProperty.getImage().get(index).getImage()
						.getScaledInstance(460, 260, Image.SCALE_SMOOTH)));
			}
		}

		// If right button is clicked
		if (event.getSource() == rightButton) {

			// If the diplayed picture is not the last pic
			if (index != currentProperty.getImage().size() - 1) {

				// Show the next pic
				index++;

				// Display
				pictureLabel.setIcon(new ImageIcon(currentProperty.getImage().get(index).getImage()
						.getScaledInstance(460, 260, Image.SCALE_SMOOTH)));
			}
		}

		// If closeFrameButton is clicked
		if (event.getSource() == closeFrameButton) {

			// Close the frame
			frame.dispose();

		}

		// If makeOfferButton is clicked
		if (event.getSource() == makeOfferButton) {

			// Intialize variable previousAmount
			int previousAmount = 0;

			try {
				// Retrieve the offer amount
				int newAmount = Integer.parseInt(offerField.getText());

				if (newAmount >= 0) {
					// Run through all offers
					for (int x = 0; x < currentProperty.getOffers().size(); x++) {

						// If a pending offer is found
						if (!currentProperty.getOffers().get(x).isAccepted()
								&& !currentProperty.getOffers().get(x).isRefused()) {

							// Save the offer
							offer = currentProperty.getOffers().get(x);

							// Record the pending offer amount
							previousAmount = offer.getAmount();
						}
					}

					// If the offer made is higher than the previous offer
					if (newAmount > previousAmount) {

						// If there's an previous offer
						if (offer != null) {

							// This offer is no longer valid
							offer.setRefused(true);
						}

						// Add the newly made offer
						currentProperty.getOffers().add(new Offer(currentUser.getUsername(), newAmount, false, false));

						// Store the offer
						SavePropertyData.saveProperty(currentProperty);

						// If the offer made is lower
					} else {

						// Display error
						JOptionPane.showMessageDialog(null, "You offer must be higher than previous one!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

					// Create a new page for refresh
					new PropertyGUI(currentUser, currentProperty);

					// Close this frame
					frame.dispose();
				} else {

					// Display error
					JOptionPane.showMessageDialog(null, "Offer cannot be negative or 0!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			} catch (NumberFormatException e) {

				// Display error
				JOptionPane.showMessageDialog(null, "You can only enter number without decimals for offers!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		// If acceptButton is clicked
		if (event.getSource() == acceptButton) {

			// Run through each offer
			for (int x = 0; x < currentProperty.getOffers().size(); x++) {

				// Find the pending ofeer
				if (!currentProperty.getOffers().get(x).isAccepted()
						&& !currentProperty.getOffers().get(x).isRefused()) {

					// Store the offer
					offer = currentProperty.getOffers().get(x);
				}
			}

			// Accept the offer
			offer.setAccepted(true);

			// The property is now sold
			currentProperty.setSold(true);

			// Save all changes
			SavePropertyData.saveProperty(currentProperty);

			// Create a new page for refresh
			new PropertyGUI(currentUser, currentProperty);

			// Close this frame
			frame.dispose();
		}

		// If decline button is clicked
		if (event.getSource() == declineButton) {

			// Run through each offer
			for (int x = 0; x < currentProperty.getOffers().size(); x++) {

				// Find the pending ofeer
				if (!currentProperty.getOffers().get(x).isAccepted()
						&& !currentProperty.getOffers().get(x).isRefused()) {

					// Store the offer
					offer = currentProperty.getOffers().get(x);
				}
			}

			// Accept the offer
			offer.setRefused(true);

			// Save all changes
			SavePropertyData.saveProperty(currentProperty);

			// Create a new page for refresh
			new PropertyGUI(currentUser, currentProperty);

			// Close this frame
			frame.dispose();
		}

		// If addProperty button is clicked
		if (event.getSource() == addProperty) {

			// If the buyer has not yet added the property to favourtie list
			if (!currentUser.getProperties().contains(currentProperty)) {

				// Add the property to the favourite list
				SaveUserData.addProperty(currentUser, currentProperty);

				// Show success message
				JOptionPane.showMessageDialog(null, "Successfully added to favourite list!", "Success",
						JOptionPane.INFORMATION_MESSAGE);

				// If the buyer has already added
			} else {

				// Show error message
				JOptionPane.showMessageDialog(null, "Already in the favourite list!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// if estimatePriceButton is clicked
		if (event.getSource() == estimatePriceButton) {

			try { // try-catch
					// Call PriceEstimator to estimate the price
				double estimatedPrice = PriceEstimator.estimatePrice(currentProperty.getPostalCode());

				// If the estimatedPrice is not -1
				if (estimatedPrice != -1) {

					// Display
					offerField.setText(String.format("%.0f", estimatedPrice * currentProperty.getSize()));

					// If estimatedPrice is -1
				} else {

					// Show no data
					JOptionPane.showMessageDialog(null, "No data is available for calculating!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			} catch (NumberFormatException e) {

			} catch (IOException e) {

			}

		}

		// If sendEmailButton is clicked
		if (event.getSource() == sendEmailButton) {

			// Prompt the user with the email
			String email = JOptionPane.showInputDialog("Please enter the email address you'd like to send to: ");

			if(email!=null) {
				// If email contains both "@" and "."
				if (email.contains("@") && email.contains(".")) {

					// Send the email
					Email.sendProperty(currentProperty, email);

					// If not
				} else {

					// Display an error that the email is not valid
					JOptionPane.showMessageDialog(null, "Please enter a valid email address!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			

		}

		// Repaint the frame
		frame.repaint();
	}

	/**
	 * The method determine the offer scenerio and display the corresponding message
	 */
	private void checkOffer() {

		// If the property is sold
		if (currentProperty.isSold()) {

			// Initialize a buyer Stringf filed
			String buyer = null;

			// Run through all the offers
			for (int x = 0; x < currentProperty.getOffers().size(); x++) {

				// Find the offer that gets accepted
				if (currentProperty.getOffers().get(x).isAccepted()) {

					// Record the buyer
					buyer = currentProperty.getOffers().get(x).getMaker();
				}
			}

			// If the currentuser is a seller and its his/her listing
			if (currentUser.isSellerMode() && currentUser.properties.contains(currentProperty)) {

				// Display message "Your property is sold!"
				Build.createLabel(screen, offerInfoLabel, "Your property is sold!", 730, 500, 500, 60, 28, false);

				// If the currentuser is a buyer and he/she purchased the property
			} else if (currentUser.getUsername().equals(buyer)) {

				// Display message "You successfully purchased it"
				Build.createLabel(screen, offerInfoLabel, "You successfully purchased it!", 730, 500, 500, 60, 28,
						false);

				// If it's other sellers/buyers viewing it
			} else {

				// Display message "This property is sold!"
				Build.createLabel(screen, offerInfoLabel, "This property is sold!", 730, 500, 500, 60, 28, false);
			}

			// If this property is not sold
		} else {

			// If the current user is a seller
			if (currentUser.isSellerMode()) {

				// If it's his/her listing
				if (currentUser.properties.contains(currentProperty)) {

					// It will be offer scenerio 1
					offerScenerio = 1;

					// Run through all offers
					for (int x = 0; x < currentProperty.getOffers().size(); x++) {

						// If there's a pending offer
						if (!currentProperty.getOffers().get(x).isAccepted()
								&& !currentProperty.getOffers().get(x).isRefused()) {

							// Retrieve the offer
							offer = currentProperty.getOffers().get(x);

							// It will be offer scenerio 2
							offerScenerio = 2;
						}
					}

					// If it is not his/her listing
				} else {

					// It will be offer scenerio 0
					offerScenerio = 0;
				}

				// If the current user is a buyer
			} else {

				// It will be offer scenerio 3
				offerScenerio = 3;

				// Run through each offer
				for (int x = 0; x < currentProperty.getOffers().size(); x++) {

					// If there's an pending offer
					if (!currentProperty.getOffers().get(x).isAccepted()
							&& !currentProperty.getOffers().get(x).isRefused()) {

						// Save the property
						offer = currentProperty.getOffers().get(x);
						// It will be offer scenerio 4
						offerScenerio = 4;

						// If the pending offer is made by the current user
						if (currentProperty.getOffers().get(x).getMaker().equals(currentUser.getUsername())) {

							// It will be offer scenerio 5
							offerScenerio = 5;
						}

					}

					// If the currentuser has ever made an offer
					if (currentProperty.getOffers().get(x).getMaker().equals(currentUser.getUsername())) {

						// Set previous offer to true
						previousOffer = true;
					}

				}

			}

			// For offer scenerio 0
			if (offerScenerio == 0) {

				// Display the message that seller cannot make offer
				Build.createLabel(screen, offerInfoLabel, "As a seller, you cannot make offer.", 730, 500, 500, 60, 28,
						false);

				// For offer scenerio 1
			} else if (offerScenerio == 1) {

				// Display the message that there's no new offer
				Build.createLabel(screen, offerInfoLabel, "There's no new offer!", 730, 500, 500, 60, 28, false);

				// For offer scenerio 2
			} else if (offerScenerio == 2) {

				// Display the highest offer
				Build.createLabel(screen, offerInfoLabel, "The highest offer is $" + offer.getAmount(), 730, 500, 500,
						60, 28, false);

				// Create accept button and decline button
				Build.createButton(screen, acceptButton, this, "Accept", 800, 600, 150, 60, 28, false);
				Build.createButton(screen, declineButton, this, "Decline", 1060, 600, 150, 60, 28, false);

				// For offer scenerio 3
			} else if (offerScenerio == 3) {

				// If the current user has made an offer before
				if (previousOffer) {

					// Display the fact that his offer is declined
					Build.createLabel(screen, offerInfoLabel, "Your previous offer is declined.", 730, 450, 500, 60, 28,
							false);

				} else {

					// Display the listing is open to receive offer
					Build.createLabel(screen, offerInfoLabel, "You can make an offer!", 730, 450, 500, 60, 28, false);

				}

				// label $ symbol
				Build.createLabel(screen, moneyLabel, "$", 730, 520, 300, 60, 32, false);

				// Field for offer entry
				Build.createField(screen, offerField, 760, 520, 300, 60, 32);

				// Button for price estimator
				Build.createButton(screen, estimatePriceButton, this, "Estimate", 1090, 520, 150, 60, 28, false);

				// Button to send out offer
				Build.createButton(screen, makeOfferButton, this, "Make Offer", 900, 600, 200, 60, 28, false);

				// For offer scenerio 4
			} else if (offerScenerio == 4) {

				// If the current user has made an offer before
				if (previousOffer) {

					// Display the offer is declined and the current highest offer
					Build.createLabel(screen, offerInfoLabel,
							"<html>Your previous offer is declined.<br>The highest offer is $" + offer.getAmount()
									+ "<html>",
							730, 420, 500, 120, 28, false);

					// The current user never made an offer
				} else {

					// Display the current highest offer
					Build.createLabel(screen, offerInfoLabel, "The highest offer is $" + offer.getAmount(), 730, 450,
							600, 60, 28, false);

				}

				// label $ symbol
				Build.createLabel(screen, moneyLabel, "$", 730, 520, 300, 60, 32, false);

				// Field for offer entry
				Build.createField(screen, offerField, 760, 520, 300, 60, 32);

				// Button for price estimator
				Build.createButton(screen, estimatePriceButton, this, "Estimate", 1090, 520, 150, 60, 28, false);

				// Button to send out offer
				Build.createButton(screen, makeOfferButton, this, "Make Offer", 900, 600, 200, 60, 28, false);

				// For offer scenerio t
			} else if (offerScenerio == 5) {

				// Display the message that the offer is pending for approval
				Build.createLabel(screen, offerInfoLabel,
						"<html>Your offer is pending for approval.<br>Please check regularly for update! <html>", 730,
						450, 500, 120, 28, false);
			}

		}
	}

}
