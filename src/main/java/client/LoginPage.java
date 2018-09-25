package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import businessClass.UserManager;
import object.UserDTO;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.persistence.*;

public class LoginPage {

	private JFrame frmLognPage;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.frmLognPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLognPage = new JFrame();
		frmLognPage.setTitle("LOGIN PAGE");
		frmLognPage.setBounds(100, 100, 574, 375);
		frmLognPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLognPage.getContentPane().setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
		lblUsername.setBounds(139, 92, 87, 16);
		frmLognPage.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
		lblPassword.setBounds(139, 133, 87, 16);
		frmLognPage.getContentPane().add(lblPassword);

		usernameField = new JTextField();
		usernameField.setBounds(283, 89, 116, 22);
		frmLognPage.getContentPane().add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(283, 130, 116, 22);
		frmLognPage.getContentPane().add(passwordField);

		JButton btnLogn = new JButton("LOGIN");
		btnLogn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserDTO user = new UserDTO();
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				user.setUsername(username);
				user.setPassword(password);

				UserManager userManager = new UserManager();
				boolean bol = userManager.getUserbyUsername(user);
				if (bol) {
					// new screen
					frmLognPage.setVisible(false);
					MainPage main = new MainPage();

				} else
					JOptionPane.showMessageDialog(frmLognPage, "Wrong username or password!");
				

			}
		});
		btnLogn.setBounds(283, 197, 116, 45);
		frmLognPage.getContentPane().add(btnLogn);
	}
}
