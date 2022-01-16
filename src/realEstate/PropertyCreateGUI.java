/**
 * The class PropertyCreateGUI creates a page for sellers to create their listings
 */
package realEstate;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PropertyCreateGUI extends JFrame implements ActionListener {

	// User
	private User currentUser;

	// ImageIcon
	private ImageIcon leftImage = new ImageIcon(
			new ImageIcon("Images/left.png").getImage().getScaledInstance(50, 50, 0)); // Left arrow
	private ImageIcon rightImage = new ImageIcon(
			new ImageIcon("Images/right.png").getImage().getScaledInstance(50, 50, 0)); // right arrow

	// Double variables used in the processing
	private double size, price;

	// The index of the property image
	private int imageIndex;

	// Whether the entry is valid
	private boolean entryValid;

	// String array for comboBox selection
	private String[] type = new String[2]; // Type of property
	private String[] number = new String[50]; // Number (0-49) for numBedroom/numBath selection
	private String[] featureSuggestion = new String[10]; // Recommended features selection

	// File ArrayList containing the user's selected image files
	private ArrayList<File> image = new ArrayList<File>();

	// String ArrayList containing the features of the property
	private ArrayList<String> features = new ArrayList<String>();

	// JFrame
	private JFrame frame = new JFrame();

	// JPanel
	private JPanel screen = new JPanel();

	// JLabel
	// Titles
	private JLabel title = new JLabel();
	private JLabel addressTitle = new JLabel();
	private JLabel postalCodeTitle = new JLabel();
	private JLabel bedroomTitle = new JLabel();
	private JLabel bathTitle = new JLabel();
	private JLabel typeTitle = new JLabel();
	private JLabel sizeTitle = new JLabel();
	private JLabel sqftTitle = new JLabel();
	private JLabel priceTitle = new JLabel();
	private JLabel featureTitle = new JLabel();
	private JLabel pictureTitle = new JLabel();

	// Image
	private JLabel imageLabel = new JLabel();

	// JTextField for the user's entry
	private JTextField addressField = new JTextField();
	private JTextField postalCodeField = new JTextField();
	private JTextField sizeField = new JTextField();
	private JTextField priceField = new JTextField();

	// JComboBox for the user's selection
	private JComboBox<String> bedSelection = new JComboBox();
	private JComboBox<String> bathSelection = new JComboBox();
	private JComboBox<String> typeSelection = new JComboBox();
	private JComboBox<String> featureSelection = new JComboBox();

	// JButton for the user's action
	private JButton addFeatureButton = new JButton();
	private JButton toHomeGUI = new JButton();
	private JButton estimatePriceButton = new JButton();
	private JButton saveButton = new JButton();
	private JButton deleteFeature = new JButton();
	private JButton addPictureButton = new JButton();
	private JButton deletePictureButton = new JButton();
	private JButton leftButton = new JButton();
	private JButton rightButton = new JButton();

	// JList for the features preview
	private JList<String> featureList = new JList<String>();

	// JScrollPane for the features in case there are too many features
	JScrollPane listScroller = new JScrollPane();

	/**
	 * Constructor Initialize the variable and create the page
	 * 
	 * @param currentUser
	 */
	public PropertyCreateGUI(User currentUser) {

		// Initialize
		this.currentUser = currentUser;
		image = new ArrayList<File>();
		setUpDefaultData();

		// Create the frame
		Build.buildFrame(frame, screen);

		panelDesign();
	}

	/**
	 * The method to create JPanel, JLabel, JButton, JTextField, JComboBox, JList
	 * and JScrollPane
	 */

	private void panelDesign() {

		// JPanel
		Build.buildPanel(screen);

		// JLabel
		Build.createLabel(screen, title, "Create Property", 20, 20, 500, 60, 52, true);
		Build.createLabel(screen, addressTitle, "Address:", 50, 100, 500, 60, 36, false);
		Build.createLabel(screen, postalCodeTitle, "Postal Code:", 50, 180, 500, 60, 36, false);
		Build.createLabel(screen, bedroomTitle, "Bedroom:", 50, 260, 170, 60, 36, false);
		Build.createLabel(screen, bathTitle, "Bath:", 350, 268, 100, 60, 36, false);
		Build.createLabel(screen, typeTitle, "Type: ", 50, 340, 110, 60, 36, false);
		Build.createLabel(screen, sizeTitle, "Size: ", 50, 420, 100, 60, 36, false);
		Build.createLabel(screen, sqftTitle, "sqft", 300, 420, 85, 60, 36, false);
		Build.createLabel(screen, priceTitle, "Price: $", 50, 500, 130, 60, 36, false);
		Build.createLabel(screen, featureTitle, "Feature:", 700, 100, 140, 60, 36, false);
		Build.createLabel(screen, pictureTitle, "Picture:", 50, 580, 150, 60, 36, false);

		// Specific colour setting
		title.setForeground(new java.awt.Color(70, 190, 255));

		// Label for the image of the property
		Build.createLabel(screen, imageLabel, "\n\n\n\n\n                  Please Insert Image", 730, 370, 460, 260, 20,
				false);

		// Specific setting
		imageLabel.setOpaque(true);
		imageLabel.setBackground(Color.BLACK);

		// JButton for actions
		Build.createButton(screen, deleteFeature, this, "Delete Feature", 890, 310, 150, 50, 20, false);
		Build.createButton(screen, addPictureButton, this, "Add Picture", 180, 585, 150, 50, 20, false);
		Build.createButton(screen, deletePictureButton, this, "Delete Picture", 350, 585, 150, 50, 20, false);
		Build.createButton(screen, saveButton, this, "SAVE", 860, 30, 150, 50, 36, true);
		Build.createButton(screen, toHomeGUI, this, "BACK", 1050, 30, 150, 50, 36, true);
		Build.createButton(screen, estimatePriceButton, this, "Estimate", 400, 515, 100, 40, 20, false);
		Build.createButton(screen, addFeatureButton, this, "Add", 1160, 110, 80, 50, 20, false);
		Build.createButton(screen, leftButton, this, leftImage, 670, 480, 50, 50);
		Build.createButton(screen, rightButton, this, rightImage, 1200, 480, 50, 50);

		// For Icon Button, the no border painted
		leftButton.setBorderPainted(false);
		rightButton.setBorderPainted(false);

		// Specific Colour Setting
		toHomeGUI.setForeground(Color.RED);
		saveButton.setForeground(new java.awt.Color(0, 128, 128));

		// JTextField for the entry
		Build.createField(screen, addressField, 210, 105, 360, 45, 36);
		Build.createField(screen, postalCodeField, 270, 185, 300, 45, 36);
		Build.createField(screen, sizeField, 135, 430, 150, 45, 36);
		Build.createField(screen, priceField, 180, 510, 200, 45, 36);

		// JComboBox for the selection
		Build.createComboBox(screen, bedSelection, number, 455, 265, 85, 60, 20, false);
		Build.createComboBox(screen, bathSelection, number, 220, 265, 85, 60, 20, false);
		Build.createComboBox(screen, typeSelection, type, 155, 345, 200, 60, 20, false);
		Build.createComboBox(screen, featureSelection, featureSuggestion, 845, 110, 300, 45, 20, true);

		// JList for the features
		featureList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Can only select one at a time
		featureList.setListData(features.toArray(new String[features.size()])); // Set the list data
		featureList.setLayoutOrientation(JList.VERTICAL); // Vertical
		featureList.setFont(new Font("Sansserif", Font.PLAIN, 20)); // Font
		featureList.setBounds(730, 175, 462, 120); // Size/location
		screen.add(featureList); // Add the JList to screen

		// JScrollPane for the JList featureList
		listScroller = new JScrollPane(featureList); // featureList scrollable
		listScroller.setBounds(730, 175, 462, 120); // Same size/location
		screen.add(listScroller); // Add listScoller to the screen
	}

	/**
	 * The method is run whenever a button is clicked
	 */

	public void actionPerformed(ActionEvent event) {

		// The addFeatureButton is clicked
		if (event.getSource() == addFeatureButton) {

			// if none of feature is selected
			if (featureSelection.getSelectedItem() != null) {

				// If the feature list does not contain the feature
				if (!features.contains((String) featureSelection.getSelectedItem())) {

					// Add the feature to the feature list
					features.add((String) featureSelection.getSelectedItem());

				}
			}

		}

		// The deleteFeature button is clicked
		if (event.getSource() == deleteFeature) {

			// Get the selected index
			int index = featureList.getSelectedIndex();

			// If the index is not -1
			if (index != -1)

				// Remove that feature
				features.remove(index);

		}

		// If addPictureButton button is clicked
		if (event.getSource() == addPictureButton) {

			// Create a new JFileChooser
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
			fileChooser.setAcceptAllFileFilterUsed(false);

			// whether it is approved
			int option = fileChooser.showOpenDialog(this);

			// If approved
			if (option == JFileChooser.APPROVE_OPTION) {

				// Get the file selected
				File file = fileChooser.getSelectedFile();

				// Add the file
				image.add(file);

				// The index for the added picture
				imageIndex = image.size() - 1;

				// Display the added picture
				imageLabel.setIcon(new ImageIcon(new ImageIcon(image.get(imageIndex).getPath()).getImage()
						.getScaledInstance(460, 260, Image.SCALE_SMOOTH)));
			}
		}

		// If deletePictureButton is clicked
		if (event.getSource() == deletePictureButton) {

			// If there's picture
			if (image.size() != 0) {

				// Remove the picture with the index
				image.remove(imageIndex);
			}

			// If there's previous picture
			if (imageIndex != 0) {

				// Move to that picture
				imageIndex--;

				// Display
				imageLabel.setIcon(new ImageIcon(new ImageIcon(image.get(imageIndex).getPath()).getImage()
						.getScaledInstance(460, 260, Image.SCALE_SMOOTH)));

				// If there's no previous picture
			} else {

				// If there's still picture
				if (image.size() > 0) {

					// Move to the last picture
					imageIndex = image.size() - 1;

					// Display
					imageLabel.setIcon(new ImageIcon(new ImageIcon(image.get(imageIndex).getPath()).getImage()
							.getScaledInstance(460, 260, Image.SCALE_SMOOTH)));
					// If there's no picture
				} else {
					// Cancel the display of the original picture
					imageLabel.setIcon(null);
				}
			}
		}

		// If the leftButton is clicked
		if (event.getSource() == leftButton) {

			// If there's picture and there's still previous picture
			if (image.size() != 0 && imageIndex != 0) {

				// Move to the previous picture
				imageIndex--;

				// Display
				imageLabel.setIcon(new ImageIcon(new ImageIcon(image.get(imageIndex).getPath()).getImage()
						.getScaledInstance(500, 300, Image.SCALE_SMOOTH)));
			}
		}

		// If the rightButton is clicked
		if (event.getSource() == rightButton) {

			// if there's still picture and there's still next picture
			if (image.size() != 0 && imageIndex < image.size() - 1) {

				// Move to the next picture
				imageIndex++;

				// Display
				imageLabel.setIcon(new ImageIcon(new ImageIcon(image.get(imageIndex).getPath()).getImage()
						.getScaledInstance(500, 300, Image.SCALE_SMOOTH)));
			}
		}

		// if the toHomeGUI button is clicked
		if (event.getSource() == toHomeGUI) {

			// Prompt the user if there really want to quit.
			int exit = JOptionPane.showConfirmDialog(null,
					"<html>Are you sure you want to return to home screen? <br>Anything not saved will be lost.<html>",
					"Exit Screen", JOptionPane.YES_NO_OPTION);

			// If user chooses to quit
			if (exit == JOptionPane.YES_OPTION) {

				// Create new home page
				new HomeGUI(currentUser);

				// The frame is closed
				frame.dispose();
			}
		}

		// If the saveButton is clicked
		if (event.getSource() == saveButton) {

			// Initialize entryValid
			entryValid = true;

			// If address field is not entered
			if (addressField.getText().equals("")) {

				// Display the error
				incompleteError("address");

				// The entry is not valud
				entryValid = false;
			}

			// If postalCodeField is not entered
			if (postalCodeField.getText().equals("")) {

				// Display the error
				incompleteError("postal code");

				// The entry is not valid
				entryValid = false;

				// If the postal code entry is not valid
			} else if (postalCodeField.getText().length() != 6 && postalCodeField.getText().length() != 7) {

				// Show the error
				JOptionPane.showMessageDialog(null, "Invalid Postal Code Entered!", "Error", JOptionPane.ERROR_MESSAGE);

				// The entry is not valid
				entryValid = false;

			}

			// try-catch
			try {

				size = Integer.parseInt(sizeField.getText()); // try to see if size is valid
				price = Integer.parseInt(priceField.getText()); // try to see if pricce is valid

				// If size/price is less or equal to 0
				if (size <= 0 || price <= 0) {

					JOptionPane.showMessageDialog(null, "The size and/or price cannot be negative or 0", "Error",
							JOptionPane.ERROR_MESSAGE); // Display the error
					entryValid = false; // The entry is not valid
				}

			} catch (NumberFormatException e) { // If the fields contain anything other than intiger

				// If size or price is not filled
				if (sizeField.getText().equals("") || priceField.getText().equals("")) {

					// Display incomplete error
					incompleteError("size and price");

					// If both size and price are filled
				} else {

					JOptionPane.showMessageDialog(null, "Only enter numbers without decimals for size and/or price!",
							"Error", JOptionPane.ERROR_MESSAGE); // Display the error
				}

				entryValid = false; // The entry is not valid
			}

			// If the entry is valid
			if (entryValid) {

				// Create the new property
				SavePropertyData.newProperty(currentUser, addressField.getText(),
						postalCodeField.getText().toUpperCase(), features,
						Integer.parseInt((String) bedSelection.getSelectedItem()),
						Integer.parseInt((String) bathSelection.getSelectedItem()),
						(String) typeSelection.getSelectedItem(), (int) Double.parseDouble(sizeField.getText()),
						(int) Double.parseDouble(priceField.getText()), image);

				// Display the message
				JOptionPane.showMessageDialog(null, "Your property have been created.",
						"Confirmation: " + "Property Created", JOptionPane.INFORMATION_MESSAGE);

				// Update the database
				DistanceCalculator.updateDatabase(Initialize.properties);

				// Close the frame
				frame.dispose();

				// Redirect the user to the home page
				new HomeGUI(currentUser);
			}

		}

		// if estimatePriceButton is clicked
		if (event.getSource() == estimatePriceButton) {

			// if both postal code and size are entered
			if (!postalCodeField.getText().equals("") && !sizeField.getText().equals("")) {

				try { // try-catch

					// Try to see size is valid
					if (Integer.parseInt(sizeField.getText()) > 0) {

						// Call PriceEstimator to estimate the price
						double estimatedPrice = PriceEstimator.estimatePrice(postalCodeField.getText().toUpperCase());

						// If the estimated price is not -1
						if (estimatedPrice != -1) {

							// Display
							priceField.setText(
									String.format("%.0f", estimatedPrice * Integer.parseInt(sizeField.getText())));

							// If -1
						} else {

							// If the postal code entry is not valid
							if (postalCodeField.getText().length() != 6 && postalCodeField.getText().length() != 7) {

								// Show the error
								JOptionPane.showMessageDialog(null, "Invalid Postal Code Entered!", "Error",
										JOptionPane.ERROR_MESSAGE);

								// If entry is valid
							} else {

								// Show no data is available for calculating
								JOptionPane.showMessageDialog(null, "No data is available for calculating!", "Error",
										JOptionPane.ERROR_MESSAGE);
							}

						}

					} else {
						JOptionPane.showMessageDialog(null, "Please enter a positive value for size!", "Error",
								JOptionPane.ERROR_MESSAGE); // Display the error
					}

				} catch (NumberFormatException error) { // If size/price is not integer

					JOptionPane.showMessageDialog(null, "Only enter numbers without decimals for size!", "Error",
							JOptionPane.ERROR_MESSAGE); // Display the error

				} catch (IOException e) {

				}

				// Not both are entered
			} else {

				// Display the error
				incompleteError("postal code and size");
			}

		}

		// Relist the featureList data
		featureList.setListData(features.toArray(new String[features.size()]));

		// Repaint and revalidate the frame
		frame.revalidate();
		frame.repaint();

	}

	/**
	 * The method that displays an error to the user
	 * 
	 * @param error
	 */
	private void incompleteError(String error) {

		JOptionPane.showMessageDialog(null, "Please enter " + error + " before proceeding", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Initialize the comboBox selections
	 */
	private void setUpDefaultData() {

		// Set the type
		type[0] = "House";
		type[1] = "Condo";

		// Run through number 0-49
		for (int x = 0; x < 50; x++) {

			// Set the number
			number[x] = Integer.toString(x);
		}

		// Set the features
		featureSuggestion[0] = "Near to School!";
		featureSuggestion[1] = "Perfect For The Investor";
		featureSuggestion[2] = "Double Detached Garage";
		featureSuggestion[3] = "Smooth Finished Ceilings";
		featureSuggestion[4] = "Energy Efficient Windows";
		featureSuggestion[5] = "Newly Renovated Heritage Home";
		featureSuggestion[6] = "A Must See";
		featureSuggestion[7] = "Fabulous For Entertaining";
		featureSuggestion[8] = "Extensive Landscaping";
		featureSuggestion[9] = "Dream Kitchen Open Concept";

	}
}
