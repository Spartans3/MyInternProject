package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessClass.UserManager;
import object.UserDTO;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Component;

public class LoginPage extends JFrame {

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPanel contentPane;
	private JLabel imageLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 1329, 1123);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setTitle("LOGIN PAGE");

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(662, 381, 165, 56);
		lblUsername.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
		getContentPane().add(lblUsername);

		usernameField = new JTextField();
		usernameField.setBounds(865, 381, 217, 56);
		usernameField.setText("root");
		getContentPane().add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(865, 468, 217, 56);
		passwordField.setText("root");

		JButton btnLogn = new JButton("LOGIN");
		btnLogn.setBounds(1146, 374, 165, 150);
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
					setVisible(false);
					MainPage main = new MainPage();

				} else
					JOptionPane.showMessageDialog(new JFrame(), "Wrong username or password!");

			}
		});
		getContentPane().add(btnLogn);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(662, 468, 160, 56);
		lblPassword.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
		getContentPane().add(lblPassword);

		imageLabel = new JLabel();
		imageLabel.setBounds(87, 86, 56, 16);

		contentPane.add(imageLabel);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					UserDTO user = new UserDTO();
					String username = usernameField.getText();
					String password = new String(passwordField.getPassword());
					user.setUsername(username);
					user.setPassword(password);

					UserManager userManager = new UserManager();
					boolean bol = userManager.getUserbyUsername(user);
					if (bol) {
						// new screen
						setVisible(false);
						MainPage main = new MainPage();

					} else
						JOptionPane.showMessageDialog(new JFrame(), "Wrong username or password!");
				}
			}
		});
		getContentPane().add(passwordField);
	}
}
