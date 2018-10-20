package client.components;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.hibernate.Session;

import dbConnection.HibernateUtil;
import object.PatientDTO;

public class PatientTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	Session session = HibernateUtil.getSession();
	
	private List<PatientDTO> listData = getPatientData();
	private String[] columnNames = { "Name", "Surname", "TC", "Tel", "Birthday" };
	SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");


	
	public PatientTableModel() {
		
	}

	private List<PatientDTO> getPatientData() {
		return session.createQuery("from PatientDTO where status=1").list();
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return listData.size();
	}
	
	

	@Override
	public Object getValueAt(int row, int col) {
		PatientDTO patient = listData.get(row);

		switch (col) {
		case 0:
			return patient.getName();
		case 1:
			return patient.getSurname();
		case 2:
			return patient.getTc();
		case 3:
			return patient.getTel();
		case 4:
			String birthday = format.format(patient.getBirthday());
			return birthday.substring(0, 10);
		default:
			return "";

		}

	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	public void setList() {
		listData = getPatientData();
		fireTableDataChanged();
	}

}
