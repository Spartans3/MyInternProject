package client;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.hibernate.cfg.Configuration;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.jdbc.Connection;

import businessClass.DataLabelFormatter;
import businessClass.PatientManager;
import dbConnection.MySQLConnUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import object.PatientDTO;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JPopupMenu;
import java.awt.Toolkit;

import javax.swing.JMenuItem;
import java.awt.event.KeyAdapter;

public class PatientPage {

	private JFrame frmNewPatient;
	private JTable table;
	private JTextField nameTextField;
	private JTextField tcTextField;
	private JTextField surnameTextField;
	private JTextField telTextField;
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
		SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

		while (it.hasNext()) {
			PatientDTO pati = (PatientDTO) it.next();
			String data1 = pati.getName();
			String data2 = pati.getSurname();
			String data3 = String.valueOf(pati.getTc());
			String data4 = String.valueOf(pati.getTel());

			String birthday = format.format(pati.getBirthday());
			birthday = birthday.substring(0, 10);

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
						System.out.println(selectedObject.getName() + "\n id: " + selectedObject.getId());
						JasperDesign jd = JRXmlLoader.load(
								"C:\\Users\\LENOVO\\eclipse-workspace\\InternProject\\src\\main\\resources\\jasperReports\\report.jrxml");
						HashMap a = new HashMap();
						a.put("patientId", selectedObject.getId());
						JasperReport jr = JasperCompileManager.compileReport(jd);
						JasperPrint jp = JasperFillManager.fillReport(jr, a, conn);

						JasperViewer.viewReport(jp, false);
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
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmNewPatient, "Please select patient first!", "Missing something??", 0);
				
			}
		});
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
		tcTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char vchar = e.getKeyChar();
				if (!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}

			}
		});

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

				if (tcTextField.getText().length() == 11 && tmpString != null
						&& telTextField.getText().length() == 13) {
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
							telTextField.getText(), date);
					JOptionPane.showMessageDialog(frmNewPatient,
							nameTextField.getText() + " " + surnameTextField.getText() + " has been added", "Message!",
							1);
				} else
					JOptionPane.showMessageDialog(frmNewPatient, "Check the fields if correct", "ERROR!", 0);

			}
		});
		btnAdd.setBounds(302, 58, 127, 48);
		frmNewPatient.getContentPane().add(btnAdd);

		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(12, 143, 56, 16);
		frmNewPatient.getContentPane().add(lblBirthday);
		
		JTextPane txtpnPlease = new JTextPane();
		txtpnPlease.setText("!!!Date Panel Info!!!\nAfter selecting date, please click the white field on the left of the \"x\" button ");
		txtpnPlease.setBounds(326, 140, 129, 92);
		frmNewPatient.getContentPane().add(txtpnPlease);

	}

}
