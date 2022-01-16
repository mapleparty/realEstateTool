/**
 * The class AccountCreateGUI creates a screen for users to create accounts
 */
package realEstate;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AccountCreateGUI extends JFrame implements ActionListener {

	// JFrame
	private JFrame frame = new JFrame();

	// JPanel
	private JPanel screen = new JPanel();

	// JLabel
	// Title
	private JLabel title = new JLabel();
	private JLabel firstNameTitle = new JLabel();
	private JLabel lastNameTitle = new JLabel();
	private JLabel usernameTitle = new JLabel();
	private JLabel passwordTitle = new JLabel();
	private JLabel confirmPasswordTitle = new JLabel();

	// JTextField for users to enter information
	private JTextField firstNameField = new JTextField();
	private JTextField lastNameField = new JTextField();
	private JTextField usernameField = new JTextField();

	// JPasswordField to hide password
	private JPasswordField passwordField = new JPasswordField();
	private JPasswordField confirmPasswordField = new JPasswordField();

	// ButtonGroup of JRadioButton
	private ButtonGroup userTypeSelection = new ButtonGroup(); // For sellerType & buyerType

	// JRadioButton for user to choose their identities
	private JRadioButton sellerType = new JRadioButton();
	private JRadioButton buyerType = new JRadioButton();

	// JButton for actions
	private JButton createButton = new JButton();
	private JButton toLoginGUI = new JButton();

	// boolean
	private boolean sellerMode; // If the user is seller

	/**
	 * Constructor Create a new AccountCreateGUI object
	 */
	public AccountCreateGUI() {

		// Add the JRadioButton to ButtonGroup
		userTypeSelection.add(sellerType);
		userTypeSelection.add(buyerType);

		// Build the frame
		Build.buildFrame(frame, screen);

		panelDesign();
	}

	/**
	 * Set up JPanel, JLabel, JTextField. JPasswordField, JButton, JRadioButton
	 */
	private void panelDesign() {

		// JPanel
		Build.buildPanel(screen);

		// JLabel
		Build.createLabel(screen, title, "Account Creation", 20, 20, 500, 60, 52, true);
		Build.createLabel(screen, firstNameTitle, "First Name: ", 80, 120, 250, 50, 36, false);
		Build.createLabel(screen, lastNameTitle, "Last Name: ", 80, 200, 250, 50, 36, false);
		Build.createLabel(screen, usernameTitle, "Username: ", 80, 280, 250, 50, 36, false);
		Build.createLabel(screen, passwordTitle, "Password: ", 80, 360, 250, 50, 36, false);
		Build.createLabel(screen, confirmPasswordTitle, "Confirmed Password: ", 80, 440, 500, 50, 36, false);

		// Specific color setting
		title.setForeground(new java.awt.Color(70, 190, 255));

		// JField
		Build.createField(screen, firstNameField, 280, 120, 380, 50, 40);
		Build.createField(screen, lastNameField, 280, 200, 380, 50, 40);
		Build.createField(screen, usernameField, 280, 280, 380, 50, 40);
		Build.createField(screen, passwordField, 280, 360, 380, 50, 40);
		Build.createField(screen, confirmPasswordField, 470, 440, 380, 50, 40);

		// JRadioButton
		Build.createRadioButton(screen, sellerType, "  Seller", 100, 520, 250, 50, 36, false);
		Build.createRadioButton(screen, buyerType, "  Buyer", 400, 520, 250, 50, 36, false);

		// Default selection setting
		sellerType.setSelected(true);

		// JButton
		Build.createButton(screen, createButton, this, "CREATE", 510, 590, 230, 60, 40, true);
		Build.createButton(screen, toLoginGUI, this, "BACK", 1100, 20, 150, 60, 40, true);

		// Specific color setting
		toLoginGUI.setForeground(Color.RED);

	}

	/**
	 * Run the method whenever a button is clicked
	 */
	public void actionPerformed(ActionEvent event) {

		// If toLoginGUI is clicked
		if (event.getSource() == toLoginGUI) {

			// Direct to LoginGUI page
			new LoginGUI();

			// Close this frame
			frame.dispose();
		}

		// If createButton is clicked
		if (event.getSource() == createButton) {

			// If sellerType is selected
			if (sellerType.isSelected()) {
				sellerMode = true;

				// If buyerType is selected
			} else {
				sellerMode = false;
			}

			// If nothing is entered in the fields
			if (firstNameField.getText().equals("") || lastNameField.getText().equals("")
					|| usernameField.getText().equals("") || passwordField.getText().equals("")) {

				// Error message
				JOptionPane.showMessageDialog(null, "Please enter all information before proceeding!", "Error",
						JOptionPane.ERROR_MESSAGE);

			// If the confirmed password fieid is different from the password
			} else if (!confirmPasswordField.getText().equals(passwordField.getText())) {

				// Error message
				JOptionPane.showMessageDialog(null, "Please check your password entry!", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {

				// Try to save this new account information
				if (SaveUserData.createAccount(firstNameField.getText(), lastNameField.getText(),
						usernameField.getText(), passwordField.getText(), sellerMode)) {
					
					// Successful, close the frame
					frame.dispose();
					
				} else {

					// Unsuccessful, error message
					JOptionPane.showMessageDialog(null, "Username used!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}
}
