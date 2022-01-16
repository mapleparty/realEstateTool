/**
 * The class LoginGUI creates a screen for user to enter their login credential
 */

package realEstate;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class LoginGUI extends JFrame implements ActionListener {

	// JFrame
	private JFrame frame = new JFrame();

	// JPanel
	private JPanel screen = new JPanel();

	// JLabel
	// ImageIcon
	private JLabel logoLabel = new JLabel();

	// Title
	private JLabel usernameTitle = new JLabel();
	private JLabel passwordTitle = new JLabel();

	// JTextField for entry
	private JTextField usernameField = new JTextField();

	// JPasswordField for password entry
	private JPasswordField passwordField = new JPasswordField();

	// JButton for actions
	private JButton loginButton = new JButton();
	private JButton toAccountCreateGUI = new JButton();

	// ImageIcon
	private static ImageIcon logo;

	/**
	 * Constructor Create a new LoginGUI object
	 */
	public LoginGUI() {

		// Set up the logo ImageIcon
		logo = new ImageIcon(new ImageIcon("Images/logo.png").getImage().getScaledInstance(300, 190, 0));

		// Build the frame
		Build.buildFrame(frame, screen);

		panelDesign();
	}

	/**
	 * Set up JPanel, JLabel, JTextField. JPasswordField, JButton
	 */

	private void panelDesign() {

		// JPanel
		Build.buildPanel(screen);

		// JLabel
		// ImageIcon
		Build.createLabel(screen, logoLabel, logo, 490, 90, 300, 190);

		// Title
		Build.createLabel(screen, usernameTitle, "Username: ", 320, 330, 250, 50, 44, false);
		Build.createLabel(screen, passwordTitle, "Password: ", 320, 430, 250, 50, 44, false);

		// JButton
		Build.createButton(screen, loginButton, this, "LOGIN", 540, 520, 200, 60, 40, true);
		Build.createButton(screen, toAccountCreateGUI, this, "OR CLICK HERE TO CREATE ACCOUNT", 230, 600, 800, 60, 35,
				true);
		
		//Specific setting
		toAccountCreateGUI.setForeground(new java.awt.Color(70, 190, 235));
		toAccountCreateGUI.setBackground(new java.awt.Color(87, 87, 87));
		toAccountCreateGUI.setBorderPainted(false);

		// Field
		Build.createField(screen, usernameField, 550, 330, 380, 50, 40);
		Build.createField(screen, passwordField, 550, 430, 380, 50, 40);

	}

	/**
	 * Actions that will be performed whenever a button is clicked
	 */
	public void actionPerformed(ActionEvent event) {

		// toAccountCreateGUI is clicked
		if (event.getSource() == toAccountCreateGUI) {

			new AccountCreateGUI(); // Create new Account Create screen
			frame.dispose(); // Close this screen
		}

		// loginButton is clicked
		if (event.getSource() == loginButton) {

			// If the user did not enter both username and password
			if (usernameField.getText().equals("") || passwordField.getText().equals("")) {

				// Show the error message
				JOptionPane.showMessageDialog(null, "Enter both username and password before proceeding!", "Error!",
						JOptionPane.ERROR_MESSAGE);

				// If user enters the correct credential
			} else if (Initialize.initializeUser(usernameField.getText(), passwordField.getText())) {

				// Close the screen
				frame.dispose();

				// If user enters the wrong credential
			} else {

				// Show the error message
				JOptionPane.showMessageDialog(null, "Login information entered incorrectly!", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

}
