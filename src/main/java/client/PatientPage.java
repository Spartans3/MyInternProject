package client;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.jdbc.Connection;

import businessClass.DataLabelFormatter;
import businessClass.MedicineManager;
import businessClass.PatientManager;
import client.components.MedicineTableModel;
import client.components.PatientTableModel;
import client.components.SoundAlarm;
import dbConnection.MySQLConnUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import object.PatientDTO;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPopupMenu;
import java.awt.Toolkit;

import javax.swing.JMenuItem;
import java.awt.event.KeyAdapter;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Font;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.CompoundBorder;

public class PatientPage extends JFrame  implements MouseMotionListener {

	private JTextField nameTextField;
	private JTextField tcTextField;
	private JTextField surnameTextField;
	private JTextField telTextField;
	private PatientManager patientManager = new PatientManager();
	private MedicineManager medicineManager = new MedicineManager();
	private List<PatientDTO> tableData = patientManager.ListPatients();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable patientTable = new JTable();
	private String tc;
	private String tempoString;
	private MedicineTableModel medicineTableModel = new MedicineTableModel();
	private JTable medicineTable = new JTable(medicineTableModel);
	private String barcode;
	private String tmpString;
	private int selectedRow;
	private JTextField medicineNameTextField;
	private JTextField barcodeTextField;
	private JTextField producerTextField;
	private PatientTableModel tableModel = new PatientTableModel();
	private SoundAlarm alarm = new SoundAlarm();

	
	public PatientPage() throws ParseException {
	
		initialize();
		medicineTableModel.setList();
		tableModel.setList();
		pack();
		int h = (int) screenSize.getHeight();
		int w = (int) screenSize.getWidth();
		setSize(w, h);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		addMouseMotionListener(this);
	}



	private void initialize() throws ParseException {
		alarm.Start();

		setTitle("Manage Patient");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		UtilDateModel model2 = new UtilDateModel();
		Properties p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("800px"),
				ColumnSpec.decode("262px"),
				ColumnSpec.decode("801px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("max(14dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("163px:grow"),
				RowSpec.decode("39px"),
				RowSpec.decode("22px"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(19dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(181dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("34px"),}));
		JTextPane txtpnPlease = new JTextPane();
		txtpnPlease.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
		txtpnPlease.setText(
				"!!!Date Panel Info!!!\nAfter selecting date, please click the white field on the left of the \"x\" button ");
		txtpnPlease.setEditable(false);
		getContentPane().add(txtpnPlease, "4, 2, fill, center");

		JPanel medicinePanel = new JPanel();
		medicinePanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 191, 255), 4, true),
				new LineBorder(new Color(245, 255, 250), 4, true)));
		medicinePanel.setBackground(new Color(175, 238, 238));
		getContentPane().add(medicinePanel, "5, 2, 1, 22, fill, fill");
		medicinePanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("110px"),
				ColumnSpec.decode("78px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("258px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("93px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("104px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("109px"),},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("180px"),
				RowSpec.decode("45px"),
				RowSpec.decode("max(16dlu;default)"),
				RowSpec.decode("453px"),}));

		JLabel lblMedcnes = new JLabel("Medicines");
		lblMedcnes.setForeground(new Color(25, 25, 112));
		lblMedcnes.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 20));
		medicinePanel.add(lblMedcnes, "2, 2, 9, 1, center, fill");

		JPanel panel = new JPanel();
		panel.setBackground(new Color(25, 25, 112));
		medicinePanel.add(panel, "2, 3, 9, 1, fill, fill");

		JButton btnAdd_1 = new JButton("ADD");
		btnAdd_1.setBackground(new Color(0, 139, 139));
		btnAdd_1.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
		medicinePanel.add(btnAdd_1, "10, 5, fill, bottom");
		btnAdd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tempoString != null && barcodeTextField.getText().length() == 13
						&& !medicineNameTextField.getText().isEmpty() && !producerTextField.getText().isEmpty()) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
					String a = tempoString;
					Date date = null;
					try {
						date = formatter.parse(a);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						medicineManager.SetMedicine(medicineNameTextField.getText(), barcodeTextField.getText(), date,
								producerTextField.getText());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					medicineTableModel.setList();
					JOptionPane.showMessageDialog(null, medicineNameTextField.getText() + " has been added", "Message!",
							1);
				} else if (medicineNameTextField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Please enter name field!", "ERROR", 0);
				else if (producerTextField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Please enter producer field!", "ERROR", 0);
				else if (tempoString == null)
					JOptionPane.showMessageDialog(null, "Please select the date correctly!", "ERROR", 0);
				else if (barcodeTextField.getText().length() != 13)
					JOptionPane.showMessageDialog(null, "Please enter the barcode as 13 digits!", "ERROR", 0);

			}
		});

		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		medicinePanel.add(datePanel2, "4, 11, fill, fill");
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel2, new DataLabelFormatter());

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		medicinePanel.add(lblName, "2, 5, fill, center");

		medicineNameTextField = new JTextField();
		medicinePanel.add(medicineNameTextField, "4, 5, 5, 1, fill, fill");
		medicineNameTextField.setColumns(10);

		JLabel lblBarcode = new JLabel("Barcode:");
		lblBarcode.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		medicinePanel.add(lblBarcode, "2, 7, left, bottom");

		barcodeTextField = new JTextField();
		medicinePanel.add(barcodeTextField, "4, 7, 5, 1, fill, fill");
		barcodeTextField.setColumns(10);
		barcodeTextField.setColumns(10);

		JLabel lblProducer = new JLabel("Producer:");
		lblProducer.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		medicinePanel.add(lblProducer, "2, 9, left, bottom");

		producerTextField = new JTextField();
		medicinePanel.add(producerTextField, "4, 9, 5, 1, fill, fill");

		JLabel lblExpireDate = new JLabel("Expire Date:");
		lblExpireDate.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
		medicinePanel.add(lblExpireDate, "2, 11, fill, top");

		JLabel lblNewLabel = new JLabel("Medicine List");
		lblNewLabel.setForeground(new Color(25, 25, 112));
		lblNewLabel.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 15));
		medicinePanel.add(lblNewLabel, "2, 13, 3, 1, fill, fill");
		medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane2 = new JScrollPane(medicineTable);
		medicinePanel.add(scrollPane2, "2, 14, 7, 1, fill, fill");

		JButton btnDelete_1 = new JButton("DELETE");
		btnDelete_1.setBackground(new Color(0, 139, 139));
		btnDelete_1.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
		medicinePanel.add(btnDelete_1, "10, 14, fill, top");
		btnDelete_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String action = e.getActionCommand();
				if (action.equals("errorAction"))
					JOptionPane.showMessageDialog(null, "Please select medicine from the table",
							"Missing something out?", 0);
				else if (action.equals("deleteAction")) {
					medicineManager.RemoveMedicine(barcode);
					JOptionPane.showMessageDialog(null, "Deleted!", "Message!", 1);
					medicineTableModel.setList();
					btnDelete_1.setActionCommand("errorAction");
				} else
					JOptionPane.showMessageDialog(null, "Please select medicine from the table",
							"Missing something out?", 0);
			}
		});

		medicineTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int s = medicineTable.getSelectedRow();
				System.out.println(s);
				barcode = (String) medicineTable.getValueAt(s, 1);
				System.out.println(barcode);
				btnDelete_1.setActionCommand("deleteAction");

			}
		});
		barcodeTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char vchar = e.getKeyChar();
				if (!(Character.isDigit(vchar)) || vchar == KeyEvent.VK_BACK_SPACE || vchar == KeyEvent.VK_DELETE) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}

			}
		});
		datePanel2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s = datePicker.getJFormattedTextField().getText();
				tempoString = s;
				System.out.println("hi" + s);

			}
		});

		JPanel patientPanel = new JPanel();
		patientPanel.setBackground(new Color(255, 228, 196));
		patientPanel.setBorder(new CompoundBorder(new LineBorder(new Color(255, 165, 0), 4, true),
				new LineBorder(new Color(245, 245, 245), 4, true)));
		getContentPane().add(patientPanel, "3, 2, 1, 22, fill, fill");
		patientPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("110px"),
				ColumnSpec.decode("79px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("116px"),
				ColumnSpec.decode("68px"),
				ColumnSpec.decode("73px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("93px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("104px"),
				ColumnSpec.decode("110px"),},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("180px"),
				RowSpec.decode("15px"),
				RowSpec.decode("22px"),
				RowSpec.decode("28px"),
				RowSpec.decode("431px"),}));

		JLabel lblPatients = new JLabel("Patients");
		patientPanel.add(lblPatients, "2, 2, 11, 1, center, center");
		lblPatients.setForeground(Color.RED);
		lblPatients.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 20));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 0, 0));
		patientPanel.add(panel_1, "2, 3, 11, 1, fill, fill");

		JLabel label = new JLabel("Name:");
		patientPanel.add(label, "2, 5, left, center");
		label.setFont(new Font("Helvetica Neue", Font.BOLD, 14));

		nameTextField = new JTextField();
		patientPanel.add(nameTextField, "4, 5, 7, 1, fill, fill");
		nameTextField.setColumns(10);

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePanel.setBackground(new Color(255, 255, 255));
		patientPanel.add(datePanel, "4, 13, 3, 1, fill, fill");
		JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel, new DataLabelFormatter());
		datePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s = datePicker2.getJFormattedTextField().getText();
				tmpString = s;
				System.out.println("hi" + s);

			}
		});
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.setBackground(new Color(218, 165, 32));
		patientPanel.add(btnAdd, "12, 5, fill, fill");
		btnAdd.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tcTextField.getText().length() == 11 && tmpString != null && telTextField.getText().length() == 13
						&& !nameTextField.getText().isEmpty() && !surnameTextField.getText().isEmpty()) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
					String a = tmpString;

					Date date = null;
					try {
						date = formatter.parse(a);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					patientManager.SetPatient(nameTextField.getText(), surnameTextField.getText(),
							tcTextField.getText(), telTextField.getText(), date);
					JOptionPane.showMessageDialog(null,
							nameTextField.getText() + " " + surnameTextField.getText() + " has been added", "Message!",
							1);

					tableModel.setList();
					

				} else if (nameTextField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Please enter name!", "ERROR!", 0);
				else if (surnameTextField.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "Please enter surname!", "ERROR!", 0);
				else if (tcTextField.getText().length() != 11)
					JOptionPane.showMessageDialog(null, "TC must be 11 digits", "ERROR!", 0);
				else if (tmpString == null)
					JOptionPane.showMessageDialog(null, "Please select the date correctly!", "ERROR!", 0);
				else if (tcTextField.getText().length() != 11)
					JOptionPane.showMessageDialog(null, "TC must be 11 digits", "ERROR!", 0);
				else if (telTextField.getText().length() != 13)
					JOptionPane.showMessageDialog(null, "Check your phone number with starting +90 (total lenght 13)",
							"ERROR!", 0);

			}
		});

		JLabel label_2 = new JLabel("Surname:");
		patientPanel.add(label_2, "2, 7, left, center");
		label_2.setFont(new Font("Helvetica Neue", Font.BOLD, 14));

		surnameTextField = new JTextField();
		patientPanel.add(surnameTextField, "4, 7, 7, 1, fill, fill");
		surnameTextField.setColumns(10);

		JLabel label_1 = new JLabel("TC:");
		patientPanel.add(label_1, "2, 9, left, center");
		label_1.setFont(new Font("Helvetica Neue", Font.BOLD, 14));

		tcTextField = new JTextField();
		patientPanel.add(tcTextField, "4, 9, 7, 1, fill, fill");
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

		JLabel label_3 = new JLabel("Tel:");
		patientPanel.add(label_3, "2, 11, left, center");
		label_3.setFont(new Font("Helvetica Neue", Font.BOLD, 14));

		telTextField = new JTextField();
		patientPanel.add(telTextField, "4, 11, 7, 1, fill, fill");
		telTextField.setColumns(10);
		telTextField.setText("+90");

		JLabel lblBirthday = new JLabel("Birthday:");
		patientPanel.add(lblBirthday, "2, 13, left, top");
		lblBirthday.setFont(new Font("Helvetica Neue", Font.BOLD, 14));


		JLabel lblPatientList = new JLabel("Patient List");
		lblPatientList.setForeground(new Color(255, 0, 0));
		patientPanel.add(lblPatientList, "2, 15, 9, 1, left, center");
		lblPatientList.setBackground(Color.RED);
		lblPatientList.setFont(new Font("Helvetica Neue", Font.BOLD | Font.ITALIC, 15));
		patientTable.setBorder(new LineBorder(new Color(255, 140, 0)));

		patientTable.setModel(tableModel);
		patientTable.setBackground(new Color(0, 0, 0));
		patientTable.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
		JScrollPane scrollPane = new JScrollPane(patientTable);
		patientPanel.add(scrollPane, "2, 16, 9, 2, fill, top");
		scrollPane.setForeground(Color.RED);
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem medicineItem = new JMenuItem("Add medicine");
		JMenuItem listMedicine = new JMenuItem("List medicines of this patient");

		medicineItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tableData =patientManager.ListPatients();
				selectedRow = patientTable.getSelectedRow();
				if (selectedRow > -1) {
					PatientDTO selectedObject = new PatientDTO();
					selectedObject =tableData.get(selectedRow);
					System.out.println(selectedRow);
					MedicineOfPatientDialog dialog = new MedicineOfPatientDialog(selectedObject);

				} else
					JOptionPane.showMessageDialog(null, "Please Select the patient first", "hello", 1);

			}
		});
		popupMenu.add(medicineItem);

		listMedicine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(tableModel.getRowCount());
				tableData =patientManager.ListPatients();
				selectedRow = patientTable.getSelectedRow();
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
						PatientDTO selectedObject = new PatientDTO();
						
						selectedObject= tableData.get(selectedRow);
						System.out.println(selectedObject.getName() + "\n id: " + selectedObject.getId());
						String path = "/jasperReports/report.jrxml";

						HashMap a = new HashMap();
						a.put("patientId", selectedObject.getId());
						JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream(path));
						JasperPrint jp = JasperFillManager.fillReport(jr, a, conn);

						JasperViewer.viewReport(jp, false);
					} catch (Exception a) {
						a.printStackTrace();
						// TODO: handle exception
					}
				} else
					JOptionPane.showMessageDialog(null, "Please Select the patient first", "hello", 1);

			}
		});
		popupMenu.add(listMedicine);
		setBackground(Color.CYAN);

		patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		patientTable.setBackground(Color.LIGHT_GRAY);
		patientTable.setBounds(12, 326, 534, 241);
		patientTable.setComponentPopupMenu(popupMenu);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.setBackground(new Color(218, 165, 32));
		btnDelete.setForeground(new Color(0, 0, 0));
		patientPanel.add(btnDelete, "12, 16, fill, fill");
		btnDelete.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
		btnDelete.setVisible(true);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String action = e.getActionCommand();
				if (action.equals("errorAction"))
					JOptionPane.showMessageDialog(null, "Please select patient first!", "Missing something??", 0);
				else if (action.equals("deleteAction")) {
					patientManager.RemovePatient(tc);
					JOptionPane.showMessageDialog(null, "Deleted!", "Message!", 1);
					tableModel.setList();
					btnDelete.setActionCommand("errorAction");
				} else
					JOptionPane.showMessageDialog(null, "Please select patient first!", "Missing something??", 0);

			}
		});

		patientTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				selectedRow = patientTable.getSelectedRow();
				if (selectedRow < 0) {
					btnDelete.setActionCommand("errorAction");
				}

				System.out.println(selectedRow);
				tc = (String) patientTable.getValueAt(selectedRow, 2);
				System.out.println(tc);
				btnDelete.setActionCommand("deleteAction");

			}

		});

		

		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		while(true) {
			System.out.println("");
		}
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {

		alarm.Restart();
		
	}
	


	
	
}


