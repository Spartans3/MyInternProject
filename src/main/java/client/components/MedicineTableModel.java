package client.components;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.hibernate.Session;

import dbConnection.HibernateUtil;
import object.MedicineDTO;

public class MedicineTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	Session session = HibernateUtil.getSession();
	private List<MedicineDTO> listData = getMedicineData();

	
	private String[] columnNames = { "Name", "Barcode", "Producer", "ExpireDate" };
	SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

	public MedicineTableModel() {

	}

	public List<MedicineDTO> getMedicineData(){
		return session.createQuery("from MedicineDTO where status=1").list();
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return listData.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		MedicineDTO medicine = (MedicineDTO) listData.get(row);

		switch (col) {
		case 0:
			return medicine.getName();
		case 1:
			return medicine.getBarcode();
		case 2:
			return medicine.getProducer();
		case 3:
			String expire_date = format.format(medicine.getExpire_date());
			return expire_date.substring(0, 10);
		default:
			return "";
		}

	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public void setList() {
		listData = getMedicineData();
		fireTableDataChanged();
	}
}
