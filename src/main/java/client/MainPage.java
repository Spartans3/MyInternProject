package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class MainPage {

	private JFrame frmMainPage;


	public MainPage() {
		initialize();
		
	}

	private void initialize() {
		frmMainPage = new JFrame();
		frmMainPage.setSize(618, 396);
		frmMainPage.setTitle("Main Page");
		frmMainPage.setVisible(true);
		frmMainPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainPage.setLocationRelativeTo(null);
		frmMainPage.getContentPane().setLayout(null);
		
		JButton btnNewPatient = new JButton("New Patient");
		btnNewPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMainPage.setVisible(false);
				try {
					NewPatient a = new NewPatient();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
		});
		btnNewPatient.setBounds(61, 68, 114, 25);
		frmMainPage.getContentPane().add(btnNewPatient);
		
		JButton btnNewButton = new JButton("Discharge");
		btnNewButton.setBounds(236, 68, 114, 25);
		frmMainPage.getContentPane().add(btnNewButton);
		
		JButton btnReport = new JButton("Report");
		btnReport.setBounds(414, 68, 114, 25);
		frmMainPage.getContentPane().add(btnReport);
		
		JButton btnNewButton_1 = new JButton("Medicines");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmMainPage.setVisible(false);
				MedicinePage a = new MedicinePage();
				
				
			}
		});
		btnNewButton_1.setBounds(61, 159, 114, 25);
		frmMainPage.getContentPane().add(btnNewButton_1);
		
	}
}
