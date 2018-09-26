package client;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.hibernate.cfg.Configuration;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import businessClass.DataLabelFormatter;
import businessClass.PatientManager;
import object.PatientDTO;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JPopupMenu;
import java.awt.Component;

public class NewPatient {

	private JFrame frmNewPatient;
	private JTable table;
	private JTextField nameTextField;
	private JTextField tcTextField;
	private JTextField surnameTextField;
	private JTextField telTextField;
	private JTextField textField_4;



	public NewPatient() throws ParseException {
		initialize();
	}
	
	private String tmpString;

	private void initialize() throws ParseException {
		frmNewPatient = new JFrame();
		frmNewPatient.setTitle("New Patient Entry");
		frmNewPatient.setBounds(100, 100, 809, 627);
		frmNewPatient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNewPatient.getContentPane().setLayout(null);
		frmNewPatient.setLocationRelativeTo(null);
		frmNewPatient.setVisible(true);
		
		
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
		datePanel.setBounds(94,140,220,173);
		frmNewPatient.getContentPane().add(datePanel, "Today");
		
		
		
		datePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s = datePicker.getJFormattedTextField().getText();
				tmpString = s;
				System.out.println("hi" + s);

				
			}
		});
		
		
		
		
		JButton btnNewButton = new JButton("<--MAIN PAGE");
		btnNewButton.setBackground(Color.ORANGE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmNewPatient.setVisible(false);
				MainPage b = new MainPage();
			}
		});
		btnNewButton.setBounds(610, 16, 127, 45);
		frmNewPatient.getContentPane().add(btnNewButton);
		


		
		
		

		
		
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		PatientManager manager =new PatientManager();
		List tableData = manager.ListPatients();
		
		Iterator it = tableData.listIterator();
		
		

		
        List<String> columns = new ArrayList<String>();
        List<String[]> values = new ArrayList<String[]>();


        columns.add("Name");
        columns.add("Surname");
        columns.add("Tc");
        columns.add("Tel");
        columns.add("birthday");
		
		while(it.hasNext()) {
			
			PatientDTO pati = (PatientDTO) it.next();
			String data1 = pati.getName();
			String data2 = pati.getSurname();
			String data3 = String.valueOf(pati.getTc());
			String data4 = String.valueOf(pati.getTel());
			
	         SimpleDateFormat  format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	         String birthday = format.format(pati.getBirthday());
	         birthday = birthday.substring(0, 10);
			
			String [] row = {data1,data2,data3,data4,birthday };
			
			values.add(new String[] {data1,data2,data3,data4,birthday});
		}
		
        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());		
		table = 	new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(12, 326, 534, 241);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		scrollPane.setBounds(12, 326, 534, 241);
		frmNewPatient.getContentPane().add(scrollPane);
		
        JButton btnDelete = new JButton("DELETE");

        
  		btnDelete.setBounds(558, 326, 179, 241);
  		frmNewPatient.getContentPane().add(btnDelete);
  		btnDelete.setVisible(true);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	              int s =  table.getSelectedRow();
	              System.out.println(s);
	              String tc =(String) table.getValueAt(s, 2) ;
	              System.out.println(tc);
	              

	      		btnDelete.addActionListener(new ActionListener() {
	      			public void actionPerformed(ActionEvent e) {
	      				manager.RemovePatient(tc);
	      				JOptionPane.showMessageDialog(frmNewPatient, "Deleted!", "Message!", 1);
	      				
	      			}
	      		});

	              
			}
			
			
			
		});
		
		
		
		
		
		
		
		
		
		
		JLabel label = new JLabel("Name:");
		label.setBounds(12, 16, 38, 16);
		frmNewPatient.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("TC:");
		label_1.setBounds(12, 74, 21, 16);
		frmNewPatient.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Surname:");
		label_2.setBounds(12, 45, 57, 16);
		frmNewPatient.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("Tel:");
		label_3.setBounds(12, 108, 23, 16);
		frmNewPatient.getContentPane().add(label_3);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(94, 13, 177, 22);
		frmNewPatient.getContentPane().add(nameTextField);
		
		tcTextField = new JTextField();
		tcTextField.setColumns(10);
		tcTextField.setBounds(94, 71, 177, 22);
		frmNewPatient.getContentPane().add(tcTextField);
		
		surnameTextField = new JTextField();
		surnameTextField.setColumns(10);
		surnameTextField.setBounds(94, 42, 177, 22);
		frmNewPatient.getContentPane().add(surnameTextField);
		
		telTextField = new JTextField();
		telTextField.setColumns(10);
		telTextField.setBounds(94, 105, 177, 22);
		frmNewPatient.getContentPane().add(telTextField);
		telTextField.setText("+90");
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
				String a =tmpString;
				
				
				
				Date date = null;
				try {
					date = formatter.parse(a);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				manager.SetPatient(
						nameTextField.getText(), surnameTextField.getText(),
						tcTextField.getText() , telTextField.getText(),date
					
						);
			  JOptionPane.showMessageDialog(frmNewPatient,nameTextField.getText() + " "	+ surnameTextField.getText() + " has been added", "Message!", 1);
			}
		});
		btnAdd.setBounds(302, 58, 127, 48);
		frmNewPatient.getContentPane().add(btnAdd);
		
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(12, 143, 56, 16);
		frmNewPatient.getContentPane().add(lblBirthday);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(frmNewPatient, popupMenu);
		popupMenu.add("Medicine Entry");
		
		

		
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
