/**
 * The class PropertySearchGUI creates an page for the user to search desired properties
 */

package realEstate;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.event.*;

public class PropertySearchGUI extends JFrame implements ActionListener {

	// JFrame
	private JFrame frame = new JFrame();

	// JPanel
	private JPanel screen = new JPanel();

	// JLabel for different titles
	private JLabel title = new JLabel();
	private JLabel filterTitle = new JLabel();
	private JLabel bedroomTitle = new JLabel();
	private JLabel priceTitle = new JLabel();
	private JLabel bathTitle = new JLabel();
	private JLabel typeTitle = new JLabel();
	private JLabel sizeTitle = new JLabel();
	private JLabel distanceTitle = new JLabel();
	private JLabel postalCodeTitle = new JLabel();
	private JLabel[] toSymbolLabel = new JLabel[4];

	// JTextField for user's entry
	private JTextField postalCodeField = new JTextField();
	private JTextField sizeLowField = new JTextField();
	private JTextField sizeHighField = new JTextField();
	private JTextField priceLowField = new JTextField();
	private JTextField priceHighField = new JTextField();
	private JTextField rangeField = new JTextField();

	// JComboBox for user's selection
	private JComboBox<String> bedLowSelection = new JComboBox();
	private JComboBox<String> bedHighSelection = new JComboBox();
	private JComboBox<String> bathLowSelection = new JComboBox();
	private JComboBox<String> bathHighSelection = new JComboBox();
	private JComboBox<String> typeSelection = new JComboBox();

	// String array for comboBox selection
	private String[] propertyType = new String[3]; // for typeSelection
	private String[] number = new String[50]; // for bedLowSelection/bathLowSelection

	// JButton for actions
	private JButton toHomeGUI = new JButton();
	private JButton toSelectedProperty = new JButton();
	private JButton applyFilter = new JButton();

	// User
	private User currentUser;

	// JTabel for displaying listing
	private JTable table = new JTable();

	// JScrollPane in case there are too many listing
	private JScrollPane scrollPane;

	// The JTabelHeader for information
	private JTableHeader header;

	// The tableModel for customization and data storing
	private tableModel model;

	// The column of JTabel
	public static String[] type = { "ID", "Distance", "Type", "Size", "Price" };

	// The converted property data
	public static Object[][] propertyData;

	// The original displayed property list
	private ArrayList<Property> originalList = new ArrayList<Property>();

	// The modified property list after filtering
	private ArrayList<Property> modifiedList = new ArrayList<Property>();

	// Whether the search mode
	private boolean searchMode;

	// The distance list from the postal code to each property
	private ArrayList<Double> distanceList = new ArrayList<Double>();

	/**
	 * Overloaded Constructor. Only the user is given. No properties are specified.
	 * 
	 * @param currentUser
	 * @throws IOException
	 */
	public PropertySearchGUI(User currentUser) throws IOException {

		// The search mode
		searchMode = true;

		// Initialize variables/page with all properties
		initialize(currentUser, Initialize.properties);

	}

	/**
	 * Overloaded Constructor. Both the user and properties are specified
	 * 
	 * @param currentUser
	 * @param properties
	 * @throws IOException
	 */
	public PropertySearchGUI(User currentUser, ArrayList<Property> properties) throws IOException {

		// The search mode is false
		searchMode = false;

		// Initialize variables/page with specified properties
		initialize(currentUser, properties);

	}

	/**
	 * Initialize variables and display the page
	 * 
	 * @param currentUser
	 * @param properties
	 * @throws IOException
	 */
	private void initialize(User currentUser, ArrayList<Property> properties) throws IOException {

		// This current user
		this.currentUser = currentUser;

		// propertyType initialization for comboBox
		propertyType[0] = "All";
		propertyType[1] = "Condo";
		propertyType[2] = "House";

		// number initialization for comboBox
		for (int x = 0; x < 50; x++) {
			number[x] = Integer.toString(x);
		}

		// Clone the original properties to originalList
		originalList = (ArrayList<Property>) properties.clone();

		// Clone originalList to the modifiedList
		modifiedList = (ArrayList<Property>) originalList.clone();

		// Get the distances
		filterDistance("L6E1G4", -1, modifiedList);

		// Import the table data with modifiedList and the distances
		importData(modifiedList, distanceList);

		// Set the model
		model = new tableModel(this.propertyData, type);

		// Bulid the frame
		Build.buildFrame(frame, screen);

		panelDesign();

		// Repaint the frame
		frame.repaint();
	}

	/**
	 * The method to create JPanel, JLabel, JButton, JTextField, JComboBox, JTabel,
	 * JHeader and JScrollPane
	 * 
	 * @throws IOException
	 */
	private void panelDesign() {

		// Create JPanel
		Build.buildPanel(screen);

		// if the search mode is open
		if (searchMode) {

			// Title sets the tile "Search Property"
			Build.createLabel(screen, title, "Search Property", 20, 20, 500, 60, 52, true);

			// If the search mode is not open
		} else {

			// If the user is seller
			if (currentUser.isSellerMode()) {

				// Title sets the tile "My Listings"
				Build.createLabel(screen, title, "My Listings", 20, 20, 500, 60, 52, true);

				// If the user is buyer
			} else {

				// Title sets the tile "Favourtie Properties"
				Build.createLabel(screen, title, "Favourite Properties", 20, 20, 600, 60, 52, true);
			}

		}

		// Specific colour setting
		title.setForeground(new java.awt.Color(70, 190, 255));

		// JLabel for titles
		Build.createLabel(screen, filterTitle, "Filter", 800, 100, 500, 60, 32, false);
		Build.createLabel(screen, typeTitle, "Type:", 800, 160, 500, 60, 28, false);
		Build.createLabel(screen, bedroomTitle, "Bed:", 800, 220, 500, 60, 28, false);
		Build.createLabel(screen, bathTitle, "Bath:", 800, 280, 500, 60, 28, false);
		Build.createLabel(screen, sizeTitle, "Size:                                       sqft", 800, 340, 500, 60, 28,
				false);
		Build.createLabel(screen, priceTitle, "Price: $", 800, 400, 190, 60, 28, false);
		Build.createLabel(screen, distanceTitle, "Distance: Within                   km", 800, 460, 500, 60, 28, false);
		Build.createLabel(screen, postalCodeTitle, "Postal Code: ", 800, 520, 200, 60, 28, false);

		// For the first two symbols
		for (int x = 0; x < 2; x++) {

			// Initialize
			toSymbolLabel[x] = new JLabel();

			// Create two labels
			Build.createLabel(screen, toSymbolLabel[x], "~", 985, 228 + 60 * x, 50, 50, 40, false);
		}

		// For the last two symbols
		for (int x = 2; x < 4; x++) {

			// Initialize
			toSymbolLabel[x] = new JLabel();

			// Create two labels
			Build.createLabel(screen, toSymbolLabel[x], "~", 1020 + 40 * (x - 2), 348 + 60 * (x - 2), 50, 50, 40,
					false);
		}

		// JButton for actions
		Build.createButton(screen, toHomeGUI, this, "BACK", 1050, 30, 150, 50, 36, true);
		Build.createButton(screen, toSelectedProperty, this, "VIEW", 290, 600, 150, 50, 36, true);
		Build.createButton(screen, applyFilter, this, "APPLY", 960, 600, 150, 50, 36, true);

		// Specific Colour Setting
		toSelectedProperty.setForeground(new java.awt.Color(128, 0, 0));
		applyFilter.setForeground(new java.awt.Color(0, 128, 128));
		toHomeGUI.setForeground(Color.RED);

		// JTextField for user's entry
		Build.createField(screen, sizeLowField, 880, 350, 130, 45, 28);
		Build.createField(screen, sizeHighField, 1060, 350, 130, 45, 28);
		Build.createField(screen, priceLowField, 920, 410, 130, 45, 28);
		Build.createField(screen, priceHighField, 1100, 410, 130, 45, 28);
		Build.createField(screen, postalCodeField, 980, 530, 200, 45, 28);
		Build.createField(screen, rangeField, 1020, 470, 155, 45, 28);

		// Default entry
		postalCodeField.setText("L6E1G4"); // Bur Oak Secondary School

		// JComboBox for user's selection
		Build.createComboBox(screen, typeSelection, propertyType, 880, 165, 200, 60, 20, false);
		Build.createComboBox(screen, bathLowSelection, number, 880, 285, 100, 60, 20, false);
		Build.createComboBox(screen, bathHighSelection, number, 1020, 285, 100, 60, 20, false);
		Build.createComboBox(screen, bedLowSelection, number, 880, 225, 100, 60, 20, false);
		Build.createComboBox(screen, bedHighSelection, number, 1020, 225, 100, 60, 20, false);

		// Default Entry
		bathHighSelection.setSelectedIndex(49);
		bedHighSelection.setSelectedIndex(49);

		// Set the tableModel
		table.setModel(model);

		// JTable showing listings
		table.setPreferredScrollableViewportSize(new Dimension(300, 100));
		table.setFillsViewportHeight(true);
		table.setFont(new Font("Sansserif", Font.PLAIN, 16));
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// JHeader for the table headers
		header = table.getTableHeader();
		header.setBackground(Color.CYAN);
		header.setForeground(Color.BLACK);
		header.setFont(new Font("Sansserif", Font.BOLD, 16));

		// JScrollPane to make the table scrollable
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(30, 120, 700, 450);
		screen.add(scrollPane);

	}

	/**
	 * Convert the property data to Object two dimensional array
	 * 
	 * @param data
	 */
	public void importData(ArrayList<Property> data, ArrayList<Double> distance) {

		// Initialize propertyData with the inputed size
		propertyData = new Object[data.size()][5];

		// Loop through each property
		for (int x = 0; x < data.size(); x++) {

			// Get the ID
			propertyData[x][0] = new Integer(Initialize.properties.indexOf(data.get(x)));

			if (distance.get(Initialize.properties.indexOf(data.get(x))) != -1) {
				// Distance
				propertyData[x][1] = new Double(distance.get(Initialize.properties.indexOf(data.get(x))));
			} else {

				propertyData[x][1] = 0;
			}

			// PropertyType
			propertyData[x][2] = data.get(x).getPropertyType();

			// Size
			propertyData[x][3] = new Integer(data.get(x).getSize());

			// Price
			propertyData[x][4] = new Integer(data.get(x).getPrice());
		}
	}

	/**
	 * Filter the price outside the range
	 * 
	 * @param price
	 * @param data
	 * @return
	 */
	public ArrayList<Property> filterPrice(int priceLow, int priceHigh, ArrayList<Property> data) {

		// Loop through each property
		for (int x = 0; x < data.size(); x++) {

			// The property's price higher than requested
			if (priceLow > data.get(x).getPrice() || priceHigh < data.get(x).getPrice()) {

				// Remove the property
				data.remove(x);

				// Reset x to correspond to the new ArrayList
				x--;
			}
		}

		// Return the filtered properties
		return data;
	}

	/**
	 * Filter the unwanted type
	 * 
	 * @param type
	 * @param data
	 * @return
	 */
	public ArrayList<Property> filterType(String type, ArrayList<Property> data) {

		// If all types are accepted
		if (type == "All") {
			// Directly return
			return data;
		}
		// Loop through each property
		for (int x = 0; x < data.size(); x++) {

			// The property type is unwanted
			if (!type.equals(data.get(x).getPropertyType())) {

				// Remove the property
				data.remove(x);

				// Reset x to correspond to the new ArrayList
				x--;
			}
		}
		// Return the filtered properties
		return data;
	}

	/**
	 * Filter the properties outside the range
	 * 
	 * @param postalCode
	 * @param distance
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Property> filterDistance(String postalCode, double distance, ArrayList<Property> data)
			throws IOException {

		// Retrieve the distance list
		distanceList = DistanceCalculator.getDistanceList(postalCode.toUpperCase());
		boolean distanceError = false;
		// Loop through each property
		for (int x = 0; x < data.size(); x++) {

			// Retrive the ID
			int index = Initialize.properties.indexOf(data.get(x));

			// The property is outside the range
			if (distance != -1 && distance < distanceList.get(index) && distanceList.get(index) != -1) {

				// Remove the property
				data.remove(x);

				// Reset x to correspond to the new ArrayList
				x--;
			}

			// If the distance is -1
			if (distanceList.get(index) == -1) {

				// Set to 0
				distanceList.set(index, 0.0);

				// The distance is an error
				distanceError = true;

			}

		}

		// If there is an error
		if (distanceError) {

			// Display error
			JOptionPane.showMessageDialog(null, "Distance calculation error due to Internet/Invalid Postal Code!",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		// Return the filtered properties
		return data;
	}

	/**
	 * Filter the size outside the range
	 * 
	 * @param size
	 * @param data
	 * @return
	 */
	public ArrayList<Property> filterSize(int sizeLow, int sizeHigh, ArrayList<Property> data) {

		// Loop through each property
		for (int x = 0; x < data.size(); x++) {

			// The property is smaller than requested size
			if (sizeLow > data.get(x).getSize() || sizeHigh < data.get(x).getSize()) {

				// Remove the property
				data.remove(x);

				// Reset x to correspond to the new ArrayList
				x--;
			}
		}
		// Return the filtered properties
		return data;
	}

	/**
	 * Filter the properties without the requested room
	 * 
	 * @param size
	 * @param data
	 * @return
	 */

	public ArrayList<Property> filterRoom(int bathLow, int bathHigh, int bedLow, int bedHigh,
			ArrayList<Property> data) {

		// Loop through each property
		for (int x = 0; x < data.size(); x++) {

			// If bath is not entered or property has less than requested
			if (bathLow > data.get(x).getNumBath() || bathHigh < data.get(x).getNumBath()) {

				// Remove the property
				data.remove(x);

				// Reset x to correspond to the new ArrayList
				x--;
			}

		}

		// Loop through each property
		for (int x = 0; x < data.size(); x++) {

			// If bed is not entered or property has less than requested
			if (bedLow > data.get(x).getNumBedroom() || bedHigh < data.get(x).getNumBedroom()) {

				// Remove the property
				data.remove(x);

				// Reset x to correspond to the new ArrayList
				x--;
			}

		}

		// Return the filtered properties
		return data;
	}

	/**
	 * The method is run if any button is clicked
	 */
	public void actionPerformed(ActionEvent event) {

		// if toSelectedProperty is clicked
		if (event.getSource() == toSelectedProperty) {

			// If none of the property is selected
			if (table.getSelectedRow() == -1) {

				// Display error
				JOptionPane.showMessageDialog(null, "Please select one property before viewing", "Error",
						JOptionPane.INFORMATION_MESSAGE);

				// If a property selected
			} else if (propertyData[table.getSelectedRow()][0] != null) {

				// Open the PropertyGUI page
				new PropertyGUI(currentUser, Initialize.properties.get((int) propertyData[table.getSelectedRow()][0]));

				// If the selected is not a property
			} else {

				// Display error
				JOptionPane.showMessageDialog(null, "Please select one property before viewing", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}

		// If applyFilter is clicked
		if (event.getSource() == applyFilter) {

			// whether validEntry
			boolean validEntry = true;

			try { // try-catch

				int testing; // The variable used in testing

				int big, small; // The variable used in comparing

				if (!sizeLowField.getText().equals("")) { // If sizeLowField is entered
					testing = Integer.parseInt(sizeLowField.getText()); // Convert to integer

					// If the number is negative
					if (testing < 0) {

						// Show the error
						negativeError("Size");

						// The entry is not valid
						validEntry = false;
					}

				}
				if (!sizeHighField.getText().equals("")) { // If sizeHighField is entered
					testing = Integer.parseInt(sizeHighField.getText()); // Convert to integer

					// If the number is negative
					if (testing < 0) {

						// Show the error
						negativeError("Size");

						// The entry is not valid
						validEntry = false;
					}
				}

				if (!priceLowField.getText().equals("")) { // If priceLowField is entered
					testing = Integer.parseInt(priceLowField.getText()); // Convert to integer

					// If the number is negative
					if (testing < 0) {

						// Show the error
						negativeError("Price");

						// The entry is not valid
						validEntry = false;
					}
				}
				if (!priceHighField.getText().equals("")) { // If priceHighField is entered
					testing = Integer.parseInt(priceHighField.getText()); // Convert to integer

					// If the number is negative
					if (testing < 0) {

						// Show the error
						negativeError("Price");

						// The entry is not valid
						validEntry = false;
					}
				}
				if (!rangeField.getText().equals("")) { // If rangeField is entered
					testing = Integer.parseInt(rangeField.getText()); // Convert to integer
					// If the number is negative
					if (testing < 0) {

						// Show the error
						negativeError("Distance");

						// The entry is not valid
						validEntry = false;
					}
				}

				//If the entry is still valid
				if (validEntry) {
					
					// If both size max and size min is set
					if (!sizeLowField.getText().equals("") && !sizeHighField.getText().equals("")) {

						// Convert to Integer
						big = Integer.parseInt(sizeHighField.getText());
						small = Integer.parseInt(sizeLowField.getText());

						// If min is biggger than max
						if (small > big) {
							// Display the error
							JOptionPane.showMessageDialog(null,
									"Size maximum must be bigger than or equal to size minimum!", "Error",
									JOptionPane.ERROR_MESSAGE);

							// The entry is invalid
							validEntry = false;
						}
					}

					// If both price max and price min is set
					if (!priceLowField.getText().equals("") && !priceHighField.getText().equals("")) {

						// Convert to Integer
						big = Integer.parseInt(priceHighField.getText());
						small = Integer.parseInt(priceLowField.getText());

						// If min is biggger than max
						if (small > big) {
							// Display the error
							JOptionPane.showMessageDialog(null,
									"Price maximum must be bigger than or equal to price minimum!", "Error",
									JOptionPane.ERROR_MESSAGE);

							// The entry is invalid
							validEntry = false;
						}
					}

					// If bed min is bigger than bed max
					if (bedLowSelection.getSelectedIndex() > bedHighSelection.getSelectedIndex()) {
						// Display the error
						JOptionPane.showMessageDialog(null, "Bed maximum must be bigger than or equal to bed minimum!",
								"Error", JOptionPane.ERROR_MESSAGE);

						// The entry is invalid
						validEntry = false;
					}

					// If bath low is bigger than bath high
					if (bathLowSelection.getSelectedIndex() > bathHighSelection.getSelectedIndex()) {
						// Display the error
						JOptionPane.showMessageDialog(null,
								"Bath maximum must be bigger than or equal to bath minimum!", "Error",
								JOptionPane.ERROR_MESSAGE);

						// The entry is invalid
						validEntry = false;
					}
				}

			} catch (NumberFormatException e) { // If any of the above conversion failed

				// Display the error
				JOptionPane.showMessageDialog(null,
						"Please enter only number without decimal for size, price and distance!", "Error",
						JOptionPane.ERROR_MESSAGE);

				// The entry is not valid
				validEntry = false;

			}

			// If the entry is valid
			if (validEntry) {

				// clone the original list to the modified list
				modifiedList = (ArrayList<Property>) originalList.clone();

				// Filter the unwanted properties
				modifiedList = filterType((String) typeSelection.getSelectedItem(), modifiedList);

				// Filter the properties outside the range
				modifiedList = filterRoom(bathLowSelection.getSelectedIndex(), bathHighSelection.getSelectedIndex(),
						bedLowSelection.getSelectedIndex(), bedHighSelection.getSelectedIndex(), modifiedList);

				// If both size max and size min are entered
				if (!sizeLowField.getText().equals("") && !sizeHighField.getText().equals("")) {

					// Filter the properties outside the range
					modifiedList = filterSize(Integer.parseInt(sizeLowField.getText()),
							Integer.parseInt(sizeHighField.getText()), modifiedList);

					// If only size max is entered
				} else if (sizeLowField.getText().equals("") && !sizeHighField.getText().equals("")) {

					// Filter out the properties larger than the maximum
					modifiedList = filterSize(0, Integer.parseInt(sizeHighField.getText()), modifiedList);

					// If only size min is entered
				} else if (!sizeLowField.getText().equals("") && sizeHighField.getText().equals("")) {

					// Filter out the properties smaller than the minimum
					modifiedList = filterSize(Integer.parseInt(sizeLowField.getText()), 999999999, modifiedList);
				}

				// If both price max and price min is entered
				if (!priceLowField.getText().equals("") && !priceHighField.getText().equals("")) {

					// Filter the properties outside the range
					modifiedList = filterPrice(Integer.parseInt(priceLowField.getText()),
							Integer.parseInt(priceHighField.getText()), modifiedList);

					// If only price max is entered
				} else if (priceLowField.getText().equals("") && !priceHighField.getText().equals("")) {

					// Filter out the properties larger than the maximum
					modifiedList = filterPrice(0, Integer.parseInt(priceHighField.getText()), modifiedList);

					// If only price min is entered
				} else if (!priceLowField.getText().equals("") && priceHighField.getText().equals("")) {

					// Filter out the properties smaller than the minim
					modifiedList = filterPrice(Integer.parseInt(priceLowField.getText()), 999999999, modifiedList);
				}

				// postal code and range is entered
				if (!postalCodeField.getText().equals("") && !rangeField.getText().equals("")) {

					try { // try-catch

						// Filter the properties outside the range
						modifiedList = filterDistance(postalCodeField.getText(), Integer.parseInt(rangeField.getText()),
								modifiedList);

					} catch (IOException e) {

					}

					// If only the postal code is entered
				} else if (!postalCodeField.getText().equals("") && rangeField.getText().equals("")) {

					try { // try-catch

						// get the distance
						modifiedList = filterDistance(postalCodeField.getText(), -1, modifiedList);

					} catch (IOException e) {

					}

				} else if (postalCodeField.getText().equals("") && !rangeField.getText().equals("")) {

					JOptionPane.showMessageDialog(null,
							"A postal code must be entered to filter out the distance outside the range!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}

		// If toHomeGUI button is clicked
		if (event.getSource() == toHomeGUI) {

			// Come back to the HomeGUI
			new HomeGUI(currentUser);

			// Close the frame
			frame.dispose();
		}

		// Import the table data with modifiedList

		importData(modifiedList, distanceList);

		// Reset the tableModel
		model.setpropertyData(propertyData);
		model.fireTableDataChanged();

		// Repaint the frame
		frame.repaint();
	}

	public void negativeError(String error) {

		JOptionPane.showMessageDialog(null, error + " must be positive or 0!", "Error", JOptionPane.ERROR_MESSAGE);
	}

}
