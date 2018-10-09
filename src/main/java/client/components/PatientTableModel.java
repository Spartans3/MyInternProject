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

import businessClass.PatientManager;
import object.PatientDTO;

public class PatientTableModel extends DefaultTableModel{
	
	PatientManager manager = new PatientManager();
	

	
	public PatientTableModel() {
		
		
		getTableList();
		
	}
	
	
public List<PatientDTO> getTableList() {
	List<PatientDTO> tableData = manager.ListPatients();

	Iterator it = tableData.listIterator();
	List<String[]> values = new ArrayList<String[]>();
	List<String> columns = new ArrayList<String>();
	

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
	setDataVector(values.toArray(new Object[][] {}), columns.toArray());
	return tableData;
	}
	










}


