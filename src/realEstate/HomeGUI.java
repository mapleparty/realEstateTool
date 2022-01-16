/**
 * The HomeGUI crates a screen as the home page
 */
package realEstate;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class HomeGUI extends JFrame implements ActionListener {

	// JFrame
	private JFrame frame = new JFrame();

	// JPanel
	private JPanel screen = new JPanel();

	// JLabel
	// Logo Image
	private JLabel logoLabel = new JLabel();
	//Greeting
	private JLabel greeting = new JLabel();

	// JButton for actions
	private JButton toPropertySearchGUI = new JButton();
	private JButton toPropertyListGUI = new JButton();
	private JButton toPropertyCreateGUI = new JButton();
	private JButton logOutButton = new JButton();

	// User
	private User currentUser;

	// ImageIcon for storing the logo
	private static ImageIcon logo;

	/**
	 * Contructor Initialize the fields and create the page
	 * 
	 * @param currentUser
	 */
	public HomeGUI(User currentUser) {

		// Initialize
		this.currentUser = currentUser;
		logo = new ImageIcon(new ImageIcon("Images/logo.png").getImage().getScaledInstance(300, 190, 0)); //Logo image

		// Build the frame
		Build.buildFrame(frame, screen);

		panelDesign();
	}

	/**
	 * Set up JPanel, JLabel, JButton
	 */
	private void panelDesign() {

		// Create the panel
		Build.buildPanel(screen);

		// Icon label of the page
		Build.createLabel(screen, logoLabel, logo, 490, 70, 300, 190);

		// If the user is a seller, seller homepage is set up
		if (currentUser.isSellerMode()) {

			// Greeting label
			Build.createLabel(screen, greeting, "Hello Seller " + currentUser.getFirstName() + "!", 490, 250, 500, 190,
					36, false);

			// JButtons
			Build.createButton(screen, toPropertySearchGUI, this, "<html><center>SEARCH<br>PROPERTY</center></html>", 140, 430, 300, 120,
					36, true);
			Build.createButton(screen, toPropertyListGUI, this, "<html><center>VIEW MY<br>PROPERTY</center></html>", 490, 430, 300, 120,
					36, true);
			Build.createButton(screen, toPropertyCreateGUI, this, "<html><center>CREATE<br>PROPERTY</center></html>", 840, 430, 300, 120,
					36, true);

			// If the user is a buyer
		} else {

			// Greeting label
			Build.createLabel(screen, greeting, "Hello Buyer " + currentUser.getFirstName() + "!", 480, 250, 500, 190,
					36, false);

			// Function buttons
			Build.createButton(screen, toPropertySearchGUI, this, "<html><center>SEARCH<br>PROPERTY</center></html>", 235, 430, 300, 120,
					36, true);
			Build.createButton(screen, toPropertyListGUI, this, "<html><center>VIEW MY<br>FAVOURITE</center></html>", 735, 430, 300, 120,
					36, true);

		}

		
		// Logout button
		Build.createButton(screen, logOutButton, this, "LOGOUT", 540, 580, 200, 60, 36, true);

		//Specific colour setting
		logOutButton.setForeground(Color.RED);
	}

	/**
	 * This method is run whenever a button is clicked
	 */
	public void actionPerformed(ActionEvent event) {

		// If logOut button is clicked
		if (event.getSource() == logOutButton) {

			// Prompt the user if he/she really wants to quit
			int exit = JOptionPane.showConfirmDialog(null,
					"<html>Are you sure you want to logout and close application?<html>", "Logout",
					JOptionPane.YES_NO_OPTION);
			if (exit == JOptionPane.YES_OPTION) { // If yes

				System.exit(0); // application is closed
			}

		}

		// If toPropertyCreateGUI is clicked
		if (event.getSource() == toPropertyCreateGUI) {
			// Create new PropertyCreateGUI page for the user
			new PropertyCreateGUI(currentUser);
			// This screen is closed
			frame.dispose();
		}

		// If toPropertySearchGUI is clicked
		if (event.getSource() == toPropertySearchGUI) {

			try {//try-catch
				// Created new PropertySearchGUI page for the user
				new PropertySearchGUI(currentUser);
				
			} catch (IOException e) {//catch

			}

			// This screen is closed
			frame.dispose();
		}

		// If toPropertyListGUI is clicked
		if (event.getSource() == toPropertyListGUI) {

			try { //try-catch
				// Created new PropertySearchGUI page with only the user's properties
				new PropertySearchGUI(currentUser, currentUser.getProperties());
				
			} catch (IOException e) { //catch

			}

			// This screen is closed
			frame.dispose();
		}
	}

}
