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

import com.mysql.jdbc.Connection;

import businessClass.DataLabelFormatter;
import businessClass.MedicineManager;
import businessClass.MedicineOfPatientManager;
import businessClass.PatientManager;
import dbConnection.MySQLConnUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import object.MedicineDTO;
import object.PatientDTO;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JMenuItem;

public class PatientPage {

	private JFrame frmNewPatient;
	private JTable table;
	private JTextField nameTextField;
	private JTextField tcTextField;
	private JTextField surnameTextField;
	private JTextField telTextField;
	private JTextField textField_4;
	private List<PatientDTO> tableData = null;

	public PatientPage() throws ParseException {
		initialize();
	}

	private String tmpString;
	private int selectedRow;

	private void initialize() throws ParseException {
		frmNewPatient = new JFrame();
		frmNewPatient.setTitle("Manage Patient");
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

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
		datePanel.setBounds(94, 140, 220, 173);
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

		PatientManager manager = new PatientManager();
		tableData = manager.ListPatients();

		Iterator it = tableData.listIterator();

		List<String> columns = new ArrayList<String>();
		List<String[]> values = new ArrayList<String[]>();

		columns.add("Name");
		columns.add("Surname");
		columns.add("Tc");
		columns.add("Tel");
		columns.add("birthday");

		while (it.hasNext()) {

			PatientDTO pati = (PatientDTO) it.next();
			String data1 = pati.getName();
			String data2 = pati.getSurname();
			String data3 = String.valueOf(pati.getTc());
			String data4 = String.valueOf(pati.getTel());

			SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String birthday = format.format(pati.getBirthday());
			birthday = birthday.substring(0, 10);

			String[] row = { data1, data2, data3, data4, birthday };

			values.add(new String[] { data1, data2, data3, data4, birthday });
		}

		TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		table = new JTable(tableModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(12, 326, 534, 241);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 326, 534, 241);
		frmNewPatient.getContentPane().add(scrollPane);

		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem medicineItem = new JMenuItem("Add medicine");
		JMenuItem listMedicine = new JMenuItem("List medicines of this patient");

		medicineItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				selectedRow = table.getSelectedRow();
				if (selectedRow > -1) {
					PatientDTO selectedObject = tableData.get(selectedRow);
					System.out.println(selectedRow);
					MedicineOfPatientDialog dialog = new MedicineOfPatientDialog(selectedObject);

				} else
					JOptionPane.showMessageDialog(frmNewPatient, "Please Select the patient first", "hello", 1);

			}
		});
		popupMenu.add(medicineItem);

		listMedicine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedRow = table.getSelectedRow();
				if (selectedRow > -1) {
					Connection conn = null;
					try {
						conn = (Connection) MySQLConnUtils.getMySQLConnection();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						PatientDTO selectedObject = tableData.get(selectedRow);
						System.out.println(selectedObject.getName()+"\n id: "+selectedObject.getId());
						JasperDesign jd = JRXmlLoader.load("C:\\Users\\LENOVO\\Desktop\\report_files\\report.jrxml");
						
						/*HashMap hashMapList = new MedicineOfPatientManager().GetMedicineOfPatient(selectedObject.getId());
						hashMapList.put("name", selectedObject.getName()+" "+selectedObject.getSurname());
						*/
						JRDesignQuery query = new JRDesignQuery();
						/*query.setText("SELECT\r\n" + 
								"     medicineofpatient.`medicineofpatientId` AS medicineofpatient_medicineofpatientId,\r\n" + 
								"     medicineofpatient.`quantity` AS medicineofpatient_quantity,\r\n" + 
								"     medicineofpatient.`treatmentDays` AS medicineofpatient_treatmentDays,\r\n" + 
								"     medicineofpatient.`daily` AS medicineofpatient_daily,\r\n" + 
								"     medicineofpatient.`startDate` AS medicineofpatient_startDate,\r\n" + 
								"     medicineofpatient.`patientId` AS medicineofpatient_patientId,\r\n" + 
								"     medicineofpatient.`medicineId` AS medicineofpatient_medicineId,\r\n" + 
								"     patient.`patientId` AS patient_patientId,\r\n" + 
								"     patient.`name` AS patient_name,\r\n" + 
								"     patient.`surname` AS patient_surname,\r\n" + 
								"     patient.`tc` AS patient_tc,\r\n" + 
								"     patient.`tel` AS patient_tel,\r\n" + 
								"     patient.`birthday` AS patient_birthday,\r\n" + 
								"     medicine.`medicineId` AS medicine_medicineId,\r\n" + 
								"     medicine.`name` AS medicine_name,\r\n" + 
								"     medicine.`barcode` AS medicine_barcode,\r\n" + 
								"     medicine.`expire_date` AS medicine_expire_date,\r\n" + 
								"     medicine.`producer` AS medicine_producer\r\n" + 
								"FROM\r\n" + 
								"     `patient` patient INNER JOIN `medicineofpatient` medicineofpatient ON patient.`patientId` = medicineofpatient.`patientId`\r\n" + 
								"     INNER JOIN `medicine` medicine ON medicineofpatient.`medicineId` = medicine.`medicineId`\r\n" + 
								"\r\n" + 
								"WHERE\r\n" + 
								"	patient.`patientId`="+selectedObject.getId());
						jd.setQuery(query);
						*/
						
						HashMap a = new HashMap();
						a.put("patientId",selectedObject.getId() );
						
						
						
						
						
						JasperReport jr=JasperCompileManager.compileReport(jd);						
						JasperPrint jp = JasperFillManager.fillReport(jr, a , conn);
						
						JasperViewer.viewReport(jp,false);
					} catch (Exception a) {
						a.printStackTrace();
						// TODO: handle exception
					}
				} else
					JOptionPane.showMessageDialog(frmNewPatient, "Please Select the patient first", "hello", 1);

			}
		});
		popupMenu.add(listMedicine);
		table.setComponentPopupMenu(popupMenu);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.setBounds(558, 326, 179, 241);
		frmNewPatient.getContentPane().add(btnDelete);
		btnDelete.setVisible(true);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int s = table.getSelectedRow();
				System.out.println(s);
				String tc = (String) table.getValueAt(s, 2);
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
				String a = tmpString;

				Date date = null;
				try {
					date = formatter.parse(a);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				manager.SetPatient(nameTextField.getText(), surnameTextField.getText(), tcTextField.getText(),
						telTextField.getText(), date

				);
				JOptionPane.showMessageDialog(frmNewPatient,
						nameTextField.getText() + " " + surnameTextField.getText() + " has been added", "Message!", 1);
			}
		});
		btnAdd.setBounds(302, 58, 127, 48);
		frmNewPatient.getContentPane().add(btnAdd);

		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(12, 143, 56, 16);
		frmNewPatient.getContentPane().add(lblBirthday);

	}

}
