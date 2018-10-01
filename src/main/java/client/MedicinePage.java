package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.hibernate.cfg.Configuration;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import businessClass.DataLabelFormatter;
import businessClass.MedicineManager;
import businessClass.PatientManager;
import object.MedicineDTO;
import object.PatientDTO;

import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;

public class MedicinePage {

	private JFrame frmManageMedicine;
	private JTextField nameTextField;
	private JTextField barcodeTextField;
	private JTextField producerTextField;
	private JTable table;
	private String tmpString;

	public MedicinePage() {
		initialize();
	}


	private void initialize() {
		frmManageMedicine = new JFrame();
		frmManageMedicine.setTitle("Manage Medicine");
		frmManageMedicine.setBounds(100, 100, 925, 651);
		frmManageMedicine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManageMedicine.getContentPane().setLayout(null);
		frmManageMedicine.setLocationRelativeTo(null);
		frmManageMedicine.setVisible(true);
		
		JButton btnMainPage = new JButton("Main Page");
		btnMainPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmManageMedicine.setVisible(false);
				MainPage b = new MainPage();
			}
		});
		btnMainPage.setBounds(798, 29, 97, 112);
		frmManageMedicine.getContentPane().add(btnMainPage);
		
		JLabel lblMedicineName = new JLabel("Medicine Name:");
		lblMedicineName.setBounds(29, 29, 97, 16);
		frmManageMedicine.getContentPane().add(lblMedicineName);
		
		JLabel lblBarcodeNumber = new JLabel("Barcode:");
		lblBarcodeNumber.setBounds(29, 59, 87, 16);
		frmManageMedicine.getContentPane().add(lblBarcodeNumber);
		
		JLabel lblExpireDate = new JLabel("Expire Date:");
		lblExpireDate.setBounds(29, 117, 87, 16);
		frmManageMedicine.getContentPane().add(lblExpireDate);
		
		JLabel lblProducer = new JLabel("Producer:");
		lblProducer.setBounds(29, 88, 87, 16);
		frmManageMedicine.getContentPane().add(lblProducer);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(138, 26, 116, 22);
		frmManageMedicine.getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		barcodeTextField = new JTextField();
		barcodeTextField.setBounds(138, 56, 116, 22);
		frmManageMedicine.getContentPane().add(barcodeTextField);
		barcodeTextField.setColumns(10);
		
		producerTextField = new JTextField();
		producerTextField.setBounds(138, 88, 116, 22);
		frmManageMedicine.getContentPane().add(producerTextField);
		producerTextField.setColumns(10);
		
		UtilDateModel model = new UtilDateModel();
		Properties pro = new Properties();
		pro.put("text.today", "Today");
		pro.put("text.month", "Month");
		pro.put("text.year", "Year");
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model,pro);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
		datePanel.setBounds(138,123,220,173);
		frmManageMedicine.getContentPane().add(datePanel, "Today");
		datePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s = datePicker.getJFormattedTextField().getText();
				tmpString = s;
				System.out.println("hi" + s);

				
			}
		});
		
		//Delete Section
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.setBounds(798, 318, 97, 273);
		frmManageMedicine.getContentPane().add(btnDelete);
		btnDelete.setVisible(true);
		
		//Table and List Section Below
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		MedicineManager manager =new MedicineManager();
		List tableData = manager.ListMedicines();
		
		Iterator it = tableData.listIterator();
				
        List<String> columns = new ArrayList<String>();
        List<String[]> values = new ArrayList<String[]>();

        columns.add("Name");
        columns.add("Barcode");
        columns.add("Producer");
        columns.add("Expire Date");
        
		while(it.hasNext()) {
			
			MedicineDTO medi = (MedicineDTO) it.next();
			String data1 = medi.getName();
			String data2 = medi.getBarcode();
			String data3 = medi.getProducer();
			
			
	         SimpleDateFormat  format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	         String expire_date = format.format(medi.getExpire_date());
	         expire_date = expire_date.substring(0, 10);
			
			String [] row = {data1,data2,data3,expire_date };
			
			values.add(new String[] {data1,data2,data3,expire_date});
		}
		
        TableModel tableModel2 = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		
		
		table = new JTable(tableModel2){
			 public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBackground(Color.CYAN);
		table.setBounds(29, 331, 741, 260);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(29, 331, 741, 260);
		frmManageMedicine.getContentPane().add(scrollPane);
				
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	              int s =  table.getSelectedRow();
	              System.out.println(s);
	              String barcode =(String) table.getValueAt(s, 1) ;
	              System.out.println(barcode);
	              
	      		btnDelete.addActionListener(new ActionListener() {
	      			public void actionPerformed(ActionEvent e) {
	      				manager.RemoveMedicine(barcode);
	      				JOptionPane.showMessageDialog(frmManageMedicine, "Deleted!", "Message!", 1);
	      				
	      			}
	      		});

	              
			}
		});
		
		//Add medicine section is below here
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
						
						manager.SetMedicine(
								nameTextField.getText(), barcodeTextField.getText(), date ,
								producerTextField.getText() 
							
								);
					  JOptionPane.showMessageDialog(frmManageMedicine,nameTextField.getText()  + " has been added", "Message!", 1);
					}
				});

				btnAdd.setBounds(403, 132, 97, 164);
				frmManageMedicine.getContentPane().add(btnAdd);
	}
}
