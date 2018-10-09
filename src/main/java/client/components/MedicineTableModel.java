package client.components;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import businessClass.MedicineManager;
import object.MedicineDTO;

public class MedicineTableModel extends DefaultTableModel{
	
	MedicineManager manager = new MedicineManager();
	
	public MedicineTableModel() {
		List tableData = manager.ListMedicines();

		Iterator it = tableData.listIterator();

		List<String> columns = new ArrayList<String>();
		List<String[]> values = new ArrayList<String[]>();

		columns.add("Name");
		columns.add("Barcode");
		columns.add("Producer");
		columns.add("Expire Date");

		while (it.hasNext()) {

			MedicineDTO medi = (MedicineDTO) it.next();
			String data1 = medi.getName();
			String data2 = medi.getBarcode();
			String data3 = medi.getProducer();

			SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String expire_date = format.format(medi.getExpire_date());
			expire_date = expire_date.substring(0, 10);

			values.add(new String[] { data1, data2, data3, expire_date });
		}

		setDataVector(values.toArray(new Object[][] {}), columns.toArray());
	}
	
	public void getMedicineTable() {
//		List tableData = manager.ListMedicines();
//
//		Iterator it = tableData.listIterator();
//
//		List<String> columns = new ArrayList<String>();
//		List<String[]> values = new ArrayList<String[]>();
//
//		columns.add("Name");
//		columns.add("Barcode");
//		columns.add("Producer");
//		columns.add("Expire Date");
//
//		while (it.hasNext()) {
//
//			MedicineDTO medi = (MedicineDTO) it.next();
//			String data1 = medi.getName();
//			String data2 = medi.getBarcode();
//			String data3 = medi.getProducer();
//
//			SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//			String expire_date = format.format(medi.getExpire_date());
//			expire_date = expire_date.substring(0, 10);
//
//			values.add(new String[] { data1, data2, data3, expire_date });
//		}
//
//		DefaultTableModel tableModel2 = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		
		//return tableModel2;
	}
}
