package client;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import org.hibernate.cfg.Configuration;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.jdbc.Connection;

import businessClass.DataLabelFormatter;
import businessClass.PatientManager;
import client.components.PatientTableModel;
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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
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

public class PatientPage extends JFrame {
	
	private JTable table;
	private JTextField nameTextField;
	private JTextField tcTextField;
	private JTextField surnameTextField;
	private JTextField telTextField;
	private PatientManager manager = new PatientManager();
	private List<PatientDTO> tableData = manager.ListPatients();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private PatientTableModel pt = new PatientTableModel();
	public PatientPage() throws ParseException {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
	            public void actionPerformed(ActionEvent e)
	            {
	                setVisible(false);
	                MainPage mp = new MainPage();
	            }
	        });
		initialize();
		pack();
			int h = (int) screenSize.getHeight();
			int w = (int) screenSize.getWidth();
			setSize(w,h);
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}

	private String tmpString;
	private int selectedRow;

	private void initialize() throws ParseException {
		setTitle("Manage Patient");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("69px"),
				ColumnSpec.decode("25px"),
				ColumnSpec.decode("291px"),
				ColumnSpec.decode("20px"),
				ColumnSpec.decode("129px"),
				ColumnSpec.decode("143px"),
				ColumnSpec.decode("534px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("179px"),
				ColumnSpec.decode("278px"),
				ColumnSpec.decode("max(34dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("174px"),},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("33px"),
				RowSpec.decode("180px"),
				RowSpec.decode("196px"),
				RowSpec.decode("495px"),}));

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
		getContentPane().add(datePanel, "3, 9, fill, top");

		datePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s = datePicker.getJFormattedTextField().getText();
				tmpString = s;
				System.out.println("hi" + s);

			}
		});

		JButton btnNewButton = new JButton("<--MAIN PAGE");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBackground(Color.ORANGE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				MainPage b = new MainPage();
			}
		});
		getContentPane().add(btnNewButton, "13, 2, 1, 4, fill, fill");

		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		PatientManager manager = new PatientManager();

			PatientTableModel tableModel = new PatientTableModel();
		table =new JTable(tableModel){
			public boolean isCellEditable(int row, int column) {
				return false;
				}};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(12, 326, 534, 241);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "7, 2, 1, 10, fill, top");
		

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
					JOptionPane.showMessageDialog(null, "Please Select the patient first", "hello", 1);

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
						String path = "/jasperReports/report.jrxml" ;
						
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
		table.setComponentPopupMenu(popupMenu);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 35));
		getContentPane().add(btnDelete, "9, 2, 1, 7, fill, fill");
		btnDelete.setVisible(true);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Please select patient first!", "Missing something??", 0);

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
						JOptionPane.showMessageDialog(null, "Deleted!", "Message!", 1);
						tableData = manager.ListPatients();
						PatientTableModel model2=new PatientTableModel();
						
						table.setModel(model2);
						table.repaint();

					}
				});

			}

		});

		JLabel label = new JLabel("Name:");
		getContentPane().add(label, "1, 2, center, center");

		JLabel label_1 = new JLabel("TC:");
		getContentPane().add(label_1, "1, 6, center, center");

		JLabel label_2 = new JLabel("Surname:");
		getContentPane().add(label_2, "1, 4, right, center");

		JLabel label_3 = new JLabel("Tel:");
		getContentPane().add(label_3, "1, 8, center, center");

		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		getContentPane().add(nameTextField, "3, 2, fill, top");

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
		getContentPane().add(tcTextField, "3, 6, fill, top");

		surnameTextField = new JTextField();
		surnameTextField.setColumns(10);
		getContentPane().add(surnameTextField, "3, 4, fill, top");

		telTextField = new JTextField();
		telTextField.setColumns(10);
		getContentPane().add(telTextField, "3, 8, fill, top");
		telTextField.setText("+90");

		JButton btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 35));
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
					JOptionPane.showMessageDialog(null,
							nameTextField.getText() + " " + surnameTextField.getText() + " has been added", "Message!",
							1);
					tableData = manager.ListPatients();
					PatientTableModel model2=new PatientTableModel();
					
					table.setModel(model2);
					table.repaint();
					

					
					
					
				} else
					JOptionPane.showMessageDialog(null, "Check the fields please!", "ERROR!", 0);

			}
		});
		getContentPane().add(btnAdd, "5, 2, 1, 7, fill, fill");

		JLabel lblBirthday = new JLabel("Birthday");
		getContentPane().add(lblBirthday, "1, 9, right, top");

		JTextPane txtpnPlease = new JTextPane();
		txtpnPlease.setText(
				"!!!Date Panel Info!!!\nAfter selecting date, please click the white field on the left of the \"x\" button ");
		getContentPane().add(txtpnPlease, "5, 9, fill, top");
		
		JLabel label2 = new JLabel("");
		label2.setIcon(new ImageIcon(MedicinePage.class.getResource("/images/blue-vector-5.png")));
		getContentPane().add(label2, "1, 11, 13, 1, fill, fill");
		
		addKeyListener(new KeyAdapter() 
		 {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ESCAPE) {
				setVisible(false);
				MainPage b = new MainPage();
				}
			}
		});
		
	}
}
