/**
 * The class Build provides GUI classes with different methods to create java swing components
 */

package realEstate;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class Build {

	/**
	 * Method that builds a new frame
	 * 
	 * @param frame
	 * @param screen
	 */
	public static void buildFrame(JFrame frame, JPanel screen) {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280, 720); //Size
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false); //Cannot resize the frame
		frame.setContentPane(screen); //Add the screen
		frame.setTitle("Real Estate Tool"); //The title of the frame
	}

	/**
	 * Method that builds a new panel
	 * 
	 * @param screen
	 */
	public static void buildPanel(JPanel screen) {
		
		screen.setBorder(null);
		screen.setBackground(new java.awt.Color(87, 87, 87)); //Set colour dark grey
		screen.setBounds(0, 0, 1280, 720); //Set size/location
		screen.setLayout(null);
	}

	/**
	 * Method that takes in the information of a label and create the label
	 * Overloaded - text version
	 * 
	 * @param label
	 * @param text
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param fontSize
	 * @param bold
	 */
	public static void createLabel(JPanel screen, JLabel label, String text, int x, int y, int width, int height,
			int fontSize, boolean bold) {

		label.setText(text); // Set the text of the label

		if (bold) { // If bold is true

			label.setFont(new Font("Sanserif", Font.BOLD, fontSize)); // Set the font

		} else {

			label.setFont(new Font("Sanserif", Font.PLAIN, fontSize)); // Set the font

		}

		label.setForeground(Color.WHITE); // Set the white foreground
		label.setBounds(x, y, width, height); // Set the size/location
		screen.add(label); // Add the label
	}

	/**
	 * Method that takes in the information of a label and create the label
	 * Overloaded - icon version
	 * 
	 * @param screen
	 * @param label
	 * @param icon
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void createLabel(JPanel screen, JLabel label, ImageIcon icon, int x, int y, int width, int height) {

		label.setIcon(icon); // Set the icon
		label.setBounds(x, y, width, height); // Set the size/location
		screen.add(label); // Add the label to the panel
	}

	/**
	 * Method that takes in the information of a radio button and create the radio
	 * button
	 * 
	 * @param screen
	 * @param button
	 * @param text
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param fontSize
	 * @param bold
	 */
	public static void createRadioButton(JPanel screen, JRadioButton button, String text, int x, int y, int width,
			int height, int fontSize, boolean bold) {

		button.setText(text); // Set the text

		if (bold) { // If bold is true

			button.setFont(new Font("Sansserif", Font.BOLD, fontSize)); // Set the font (bold)

		} else {

			button.setFont(new Font("Sansserif", Font.PLAIN, fontSize)); // Set the font (plain)

		}

		button.setBounds(x, y, width, height); // Set the size/location
		button.setForeground(Color.WHITE); // Set the text colour white
		screen.add(button); // Add the label to the panel
	}

	/**
	 * Method that takes in the information of a button and create the button
	 * Overloaded - text version
	 * 
	 * @param screen
	 * @param button
	 * @param listener
	 * @param text
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param fontSize
	 * @param bold
	 */

	public static void createButton(JPanel screen, JButton button, ActionListener listener, String text, int x, int y,
			int width, int height, int fontSize, boolean bold) {

		button.setText(text); // Set the text

		button.addActionListener(listener); // Set the action listener

		if (bold) { // If bold is true

			button.setFont(new Font("Sansserif", Font.BOLD, fontSize)); // Set the font (bold)

		} else {

			button.setFont(new Font("Sansserif", Font.PLAIN, fontSize)); // Set the font (plain)

		}

		button.setFocusable(false); // Set the button not focusable
		button.setBounds(x, y, width, height); // Set the size/location
		screen.add(button); // Add the button to the panel
	}

	/**
	 * Method that takes in the information of a button and create the button
	 * Overloaded - icon version
	 * 
	 * @param screen
	 * @param button
	 * @param listener
	 * @param icon
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void createButton(JPanel screen, JButton button, ActionListener listener, ImageIcon icon, int x,
			int y, int width, int height) {

		createButton(screen, button, listener, "", x, y, width, height, 10, false); // Create a button
		button.setIcon(icon); // Set the icon
	}

	/**
	 * Method that takes in the information of a field and create the field
	 * 
	 * @param screen
	 * @param field
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param fontSize
	 */
	public static void createField(JPanel screen, JTextField field, int x, int y, int width, int height, int fontSize) {

		field.setBounds(x, y, width, height); // Set the size/location
		field.setFont(new Font("Sansserif", Font.PLAIN, fontSize)); // Set the font
		screen.add(field); // Add the field to the frame
	}

	/**
	 * Method that takes in the information of a comboBox and create the comboBox
	 * 
	 * @param screen
	 * @param box
	 * @param data
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param fontSize
	 * @param editable
	 */
	public static void createComboBox(JPanel screen, JComboBox box, String[] data, int x, int y, int width, int height,
			int fontSize, boolean editable) {

		box.setModel(new DefaultComboBoxModel(data)); //Set the data for user's selection
		box.setBounds(x, y, width, height); //Set the size/location
		box.setFont(new Font("Sansserif", Font.PLAIN, fontSize)); //Set the font
		box.setAlignmentX(Component.LEFT_ALIGNMENT);
		screen.add(box); //Add to the screen
		box.setEditable(editable); //Set whether the ComboBox is editable
	}

}

/**
 * The tabelModel stores the information of the table
 * Override some methods in the default model to customize
 *
 */
class tableModel extends AbstractTableModel {

	//Array storing info
	Object[][] propertyData;
	String[] columnNames;

	/**
	 * Constructor
	 * @param propertyData
	 * @param columnNames
	 */
	public tableModel(Object[][] propertyData, String[] columnNames) {

		this.propertyData = propertyData;
		this.columnNames = columnNames;
	}

	/**
	 * setter for propertyData
	 * @param propertyData
	 */
	
	public void setpropertyData(Object[][] propertyData) {
		this.propertyData = propertyData;
	}

	/**
	 * Getter for the length of columnNames
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * Getter for the length of propertyData
	 */
	public int getRowCount() {
		return propertyData.length;
	}

	/**
	 * Getter for a specific objext in columnNames
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/**
	 * Getter for a specific object in propertyData
	 */
	public Object getValueAt(int row, int col) {
		return propertyData[row][col];
	}

	/**
	 * Getter for the class type of a specific column
	 */
	public Class getColumnClass(int column) {
		
		//For different column, the type of data it is containing
		if (column == 0) {
			return Integer.class;
		} else if (column == 1) {
			return Double.class;
		} else if (column == 2) {
			return String.class;
		} else {
			return Double.class;
		}
	}

	/***
	 * Set the tabel not editable
	 */
	public boolean isCellEditable(int row, int column) {
		// all cells false
		return false;
	}
}
