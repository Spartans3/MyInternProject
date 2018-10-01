package client;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import businessClass.DataLabelFormatter;
import businessClass.MedicineManager;
import businessClass.MedicineOfPatientManager;
import client.components.MedicineCombobox;
import object.MedicineDTO;
import object.PatientDTO;

import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MedicineOfPatientDialog extends MedicineDialogMethods {

	private JFrame frmAddMedceFor;
	private JTextField quantityTextField;
	private JTextField treatmentTextField;
	private JTextField dailyTextField;
	private PatientDTO patient = null;
	private String tmpString;

	public MedicineOfPatientDialog(PatientDTO patient) {
		this.patient = patient;

		initialize();

	}

	private void initialize() {

		frmAddMedceFor = new JFrame();
		frmAddMedceFor.setTitle("ADD MEDICINE FOR PATIENT");
		frmAddMedceFor.setBounds(100, 100, 573, 513);
		frmAddMedceFor.setVisible(true);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		frmAddMedceFor.getContentPane().setLayout(null);

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
		datePanel.setBounds(121, 280, 210, 173);
		frmAddMedceFor.getContentPane().add(datePanel);
		datePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s = datePicker.getJFormattedTextField().getText();
				tmpString = s;
				System.out.println("hi" + s);

			}
		});
		MedicineCombobox mc = new MedicineCombobox();
		/*for (MedicineDTO medicineObject : mc.ListMedicine()) {
			mc.addItem(medicineObject);
		}*/
		//mc.setItemList();
		mc.setBounds(new Rectangle(121, 64, 201, 36));
		frmAddMedceFor.getContentPane().add(mc);
		
		

		JLabel lblMedicineName = new JLabel("Medicine Name:");
		lblMedicineName.setBounds(12, 63, 97, 36);
		frmAddMedceFor.getContentPane().add(lblMedicineName);

		JLabel lblNewLabel = new JLabel("Quantity: ");
		lblNewLabel.setBounds(12, 120, 97, 36);
		frmAddMedceFor.getContentPane().add(lblNewLabel);

		JLabel lblTreatmentDays = new JLabel("Treatment Days:");
		lblTreatmentDays.setBounds(12, 178, 97, 37);
		frmAddMedceFor.getContentPane().add(lblTreatmentDays);

		JLabel lblDaily = new JLabel("Daily:");
		lblDaily.setBounds(12, 228, 97, 36);
		frmAddMedceFor.getContentPane().add(lblDaily);

		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setBounds(12, 280, 97, 28);
		frmAddMedceFor.getContentPane().add(lblStartDate);

		quantityTextField = new JTextField();
		quantityTextField.setBounds(121, 121, 201, 36);
		frmAddMedceFor.getContentPane().add(quantityTextField);
		quantityTextField.setColumns(10);

		treatmentTextField = new JTextField();
		treatmentTextField.setBounds(121, 179, 201, 36);
		frmAddMedceFor.getContentPane().add(treatmentTextField);
		treatmentTextField.setColumns(10);

		dailyTextField = new JTextField();
		dailyTextField.setBounds(121, 228, 201, 36);
		frmAddMedceFor.getContentPane().add(dailyTextField);
		dailyTextField.setColumns(10);

		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
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
				MedicineDTO medicine = new MedicineDTO();
				medicine = (MedicineDTO) mc.getSelectedMedicine();
				System.out.println(medicine.getId());

				MedicineOfPatientManager save = new MedicineOfPatientManager();
				save.SetMedicineOfPatient(quantityTextField.getText(), treatmentTextField.getText(),
						dailyTextField.getText(), date, patient.getId(), medicine.getId());
			}
		});
		btnSave.setBounds(400, 128, 105, 64);
		frmAddMedceFor.getContentPane().add(btnSave);

		JButton btnNewButton = new JButton("CANCEL");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmAddMedceFor.setVisible(false);
			}
		});
		btnNewButton.setBounds(400, 243, 105, 64);
		frmAddMedceFor.getContentPane().add(btnNewButton);

		JLabel patientNameLabel = new JLabel();
		patientNameLabel.setText(patient.getName() + " " + patient.getSurname());
		patientNameLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		patientNameLabel.setBounds(0, 0, 543, 51);
		frmAddMedceFor.getContentPane().add(patientNameLabel);

	}
}
