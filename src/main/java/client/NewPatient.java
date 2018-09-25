package client;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import businessClass.DataLabelFormatter;
import businessClass.PatientManager;
import object.PatientDTO;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.channels.ShutdownChannelGroupException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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


	private void initialize() throws ParseException {
		frmNewPatient = new JFrame();
		frmNewPatient.setTitle("New Patient Entry");
		frmNewPatient.setBounds(100, 100, 768, 579);
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
		
		String[] days = {"1","2","3","4","5","6","7","8","9","10","11","12",
				"13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		JComboBox day = new JComboBox(days);
		day.setBounds(94, 140, 45, 22);
		frmNewPatient.getContentPane().add(day);
		
		String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		JComboBox month = new JComboBox(months);
		month.setBounds(151, 140, 45, 22);
		frmNewPatient.getContentPane().add(month);
		
		String[] years = {"1990","1991","1992","1993","1994","1995","1996","1997","1998","1999",
				"2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010",
				"2011","2012","2013","2014","2015","2016","2017","2018"};
		JComboBox year = new JComboBox(years);
		year.setBounds(208, 140, 63, 22);
		frmNewPatient.getContentPane().add(year);
		
		
		
		

		
		
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		PatientManager manager =new PatientManager();
		List tableData = manager.ListPatients();
		
		Iterator it = tableData.listIterator();
		
		

		
        List<String> columns = new ArrayList<String>();
        List<String[]> values = new ArrayList<String[]>();

        columns.add("id");
        columns.add("name");
        columns.add("surname");
        columns.add("tc");
        columns.add("tel");
        columns.add("birthday");
		
		while(it.hasNext()) {
			
			PatientDTO pati = (PatientDTO) it.next();
			String data1 = pati.getName();
			String data2 = pati.getSurname();
			String data3 = String.valueOf(pati.getTc());
			String data4 = String.valueOf(pati.getTel());
			String id = String.valueOf(pati.getId());
			String birthday = String.valueOf(pati.getBirthday());
			
			String [] row = {id,data1,data2,data3,data4,birthday };
			
			values.add(new String[] {id,data1,data2,data3,data4,birthday});
		}
		
        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());		
		table = 	new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(12, 261, 534, 258);
		
        JButton button_1 = new JButton("DELETE");
  		button_1.setBounds(558, 261, 179, 258);
  		frmNewPatient.getContentPane().add(button_1);
  		button_1.setVisible(true);
		frmNewPatient.getContentPane().add(table);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	              int s =  table.getSelectedRow();
	              Long id =Long.valueOf((String) table.getValueAt(s, 0)) ;
	              

	      		button_1.addActionListener(new ActionListener() {
	      			public void actionPerformed(ActionEvent e) {
	      				manager.RemovePatient(id);
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
		
		JButton button = new JButton("ADD");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
				
				String a = (String) day.getSelectedItem() + "-" + (String) month.getSelectedItem() + "-" + (String) year.getSelectedItem() ;
				
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
		button.setBounds(302, 58, 127, 48);
		frmNewPatient.getContentPane().add(button);
		
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(12, 143, 56, 16);
		frmNewPatient.getContentPane().add(lblBirthday);
		

		
		
	}
}
