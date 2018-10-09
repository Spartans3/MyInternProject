package client;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.hibernate.cfg.Configuration;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import businessClass.DataLabelFormatter;
import businessClass.MedicineManager;
import client.components.MedicineTableModel;
import client.components.PatientTableModel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class MedicinePage extends JFrame{


	private JTextField nameTextField;
	private JTextField barcodeTextField;
	private JTextField producerTextField;
	private String tmpString;
	private JTable table;
	
	public MedicinePage() {
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
	}

	private void initialize() {
		
		setTitle("Manage Medicine");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		JButton btnMainPage = new JButton("<--Main Page");
		btnMainPage.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnMainPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				MainPage b = new MainPage();
			}
		});
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("156px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("230px"),
				ColumnSpec.decode("248px"),
				ColumnSpec.decode("882px"),
				ColumnSpec.decode("28px"),
				ColumnSpec.decode("179px"),
				ColumnSpec.decode("40px"),
				ColumnSpec.decode("149px"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("46px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("18px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("158px"),
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("154px"),
				RowSpec.decode("31px"),
				RowSpec.decode("495px"),}));
		getContentPane().add(btnMainPage, "9, 2, 1, 3, left, center");

		JLabel lblMedicineName = new JLabel("Medicine Name:");
		getContentPane().add(lblMedicineName, "1, 2, center, center");

		JLabel lblBarcodeNumber = new JLabel("Barcode:");
		getContentPane().add(lblBarcodeNumber, "1, 4, center, center");

		JLabel lblExpireDate = new JLabel("Expire Date:");
		getContentPane().add(lblExpireDate, "1, 8, center, bottom");

		JLabel lblProducer = new JLabel("Producer:");
		getContentPane().add(lblProducer, "1, 6, center, top");

		nameTextField = new JTextField();
		getContentPane().add(nameTextField, "3, 2, left, center");
		nameTextField.setColumns(10);

		barcodeTextField = new JTextField();
		getContentPane().add(barcodeTextField, "3, 4, left, top");
		barcodeTextField.setColumns(10);
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

		producerTextField = new JTextField();
		getContentPane().add(producerTextField, "3, 6, left, top");
		producerTextField.setColumns(10);

		UtilDateModel model = new UtilDateModel();
		Properties pro = new Properties();
		pro.put("text.today", "Today");
		pro.put("text.month", "Month");
		pro.put("text.year", "Year");

		JDatePanelImpl datePanel = new JDatePanelImpl(model, pro);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
		getContentPane().add(datePanel, "3, 8, 1, 3, fill, fill");
		datePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s = datePicker.getJFormattedTextField().getText();
				tmpString = s;
				System.out.println("hi" + s);

			}
		});

		// Delete Section

		JButton btnDelete = new JButton("DELETE");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 35));
		getContentPane().add(btnDelete, "7, 4, 1, 5, fill, fill");
		btnDelete.setVisible(true);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Please select medicine first from the table",
						"Missing something out?", 0);
			}
		});

		// Table and List Section Below
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		MedicineManager manager = new MedicineManager();
		MedicineTableModel tableModel = new MedicineTableModel();
		
		table = new JTable(tableModel){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setBackground(Color.CYAN);

		table.setBounds(29, 331, 741, 260);
		
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, "5, 4, 1, 9, fill, fill");

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int s = table.getSelectedRow();
				System.out.println(s);
				String barcode = (String) table.getValueAt(s, 1);
				System.out.println(barcode);

				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						manager.RemoveMedicine(barcode);
						JOptionPane.showMessageDialog(null, "Deleted!", "Message!", 1);
						MedicineTableModel model2=new MedicineTableModel();
						
						table.setModel(model2);
						table.repaint();

					}
				});

			}
		});

		// Add medicine section is below here
		JButton btnAdd = new JButton("ADD");
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 35));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tmpString != null && barcodeTextField.getText().length() == 13 && nameTextField != null
						&& producerTextField != null) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
					String a = tmpString;
					Date date = null;
					try {
						date = formatter.parse(a);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						manager.SetMedicine(nameTextField.getText(), barcodeTextField.getText(), date,
								producerTextField.getText()

						);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, nameTextField.getText() + " has been added",
							"Message!", 1);
					
					MedicineTableModel model2=new MedicineTableModel();
					
					table.setModel(model2);
					table.repaint();
					
				} else
					JOptionPane.showMessageDialog(null, "Check the fields please!", "ERROR", 0);

			}
		});
		getContentPane().add(btnAdd, "3, 12, fill, top");
		
		JTextPane txtpndatePanelInfo = new JTextPane();
		txtpndatePanelInfo.setText("!!!Date Panel Info!!!\nAfter selecting date, please click the white field on the left of the \"x\" button");
		getContentPane().add(txtpndatePanelInfo, "1, 10, fill, top");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MedicinePage.class.getResource("/images/blue-vector-5.png")));
		getContentPane().add(label, "1, 14, 9, 1, fill, fill");
		
		JLabel lblMedcneLst = new JLabel("MEDICINE LIST");
		lblMedcneLst.setFont(new Font("Tahoma", Font.BOLD, 35));
		getContentPane().add(lblMedcneLst, "5, 2, center, fill");
		
        
	}
}
