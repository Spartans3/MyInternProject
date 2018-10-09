package client;

import javax.swing.JFrame;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import com.jgoodies.forms.layout.FormSpecs;

public class MainPage extends JFrame {
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MainPage() {
		initialize();
		setSize(screenSize.width, screenSize.height);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void initialize() {

		setTitle("Main Page");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btnPatient = new JButton("Patient");
		btnPatient.setForeground(new Color(153, 51, 255));
		btnPatient.setFont(new Font("Tahoma", Font.BOLD, 35));
		btnPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);

				try {
					PatientPage a = new PatientPage();
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("969px"),
				ColumnSpec.decode("102px"),
				ColumnSpec.decode("867px"),},
			new RowSpec[] {
				RowSpec.decode("359px"),
				RowSpec.decode("104px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("524px"),}));
		getContentPane().add(btnPatient, "1, 2, fill, fill");
		
		
		
		JButton btnNewButton_1 = new JButton("Medicines");
		btnNewButton_1.setForeground(new Color(204, 51, 255));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 35));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				MedicinePage a = new MedicinePage();

			}
		});
		getContentPane().add(btnNewButton_1, "3, 2, fill, fill");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MainPage.class.getResource("/images/blue-vector-5.png")));
		getContentPane().add(label, "1, 4, 3, 1, fill, fill");

	}
}
