package client.components;

import java.util.List;

import javax.swing.JComboBox;

import businessClass.MedicineManager;
import object.MedicineDTO;

public class MedicineCombobox extends JComboBox {

	MedicineManager medicine = new MedicineManager();
	public MedicineCombobox() {
		try {
			setItemList();
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MedicineDTO getSelectedMedicine() {
		MedicineDTO selected = (MedicineDTO) getSelectedItem();
		return selected;
	}
	
	public List<MedicineDTO> ListMedicine() {
		List<MedicineDTO> medicineNames = medicine.ListMedicines();
		return medicineNames;
	}
	private void setItemList() {
		for (MedicineDTO medicineObject : ListMedicine()) {
			addItem(medicineObject);
		}
	}

}
