package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessClass.UserManager;
import object.UserDTO;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

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
		setSize(getMaximumSize());

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setTitle("LOGIN PAGE");
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(745, 432, 92, 63);
		lblUsername.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
		getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(746, 508, 91, 63);
		lblPassword.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
		getContentPane().add(lblPassword);

		JPanel panel = new JPanel();
		panel.setBounds(685, 335, 655, 280);
		panel.setBorder(new LineBorder(Color.BLUE, 8, true));
		panel.setForeground(Color.DARK_GRAY);
		contentPane.add(panel);

		passwordField = new JPasswordField();
		//passwordField.setText("root");

		usernameField = new JTextField();
		//usernameField.setText("root");
		usernameField.setColumns(10);

		JButton btnLogn = new JButton("LOGIN");

		JLabel lblLogn = new JLabel("LOGIN");
		lblLogn.setForeground(Color.RED);
		lblLogn.setFont(new Font("Tahoma", Font.BOLD, 58));
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("187px"),
				ColumnSpec.decode("262px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("110px"),},
			new RowSpec[] {
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("70px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("63px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("62px"),}));
		panel.add(passwordField, "2, 6, fill, fill");
		panel.add(usernameField, "2, 4, fill, fill");
		panel.add(btnLogn, "4, 6, fill, fill");
		panel.add(lblLogn, "2, 2, right, top");
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
					try {
						PatientPage p = new PatientPage();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else
					JOptionPane.showMessageDialog(new JFrame(), "Wrong username or password!");

			}
		});
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
						try {
							PatientPage p = new PatientPage();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else
						JOptionPane.showMessageDialog(new JFrame(), "Wrong username or password!");
				}
			}
		});
		imageLabel = new JLabel();
		imageLabel.setBounds(0, 0, 1929, 1059);
		imageLabel.setIcon(new ImageIcon(LoginPage.class.getResource("/images/hospital-new.jpg")));

		contentPane.add(imageLabel);
	}
}
