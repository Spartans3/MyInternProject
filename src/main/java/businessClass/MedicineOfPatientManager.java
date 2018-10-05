package businessClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.fasterxml.classmate.util.ResolvedTypeCache.Key;

import net.sf.jasperreports.components.items.Item;
import object.MedicineDTO;
import object.MedicineOfPatientDTO;
import object.PatientDTO;

public class MedicineOfPatientManager {

	public void SetMedicineOfPatient(String quantity, String treatmentDays, String daily, Date startDate,
			Long patientId, Long medicineId) {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();

		PatientDTO patient = (PatientDTO) session.get(PatientDTO.class, new Long(patientId));
		MedicineDTO medicine = (MedicineDTO) session.get(MedicineDTO.class, new Long(medicineId));

		MedicineOfPatientDTO medpati = new MedicineOfPatientDTO();
		medpati.setQuantity(quantity);
		medpati.setTreatmentDays(treatmentDays);
		medpati.setDaily(daily);
		medpati.setStartDate(startDate);
		medpati.setPatient(patient);
		medpati.setMedicine(medicine);

		Transaction tx = session.beginTransaction();
		session.save(medpati);
		System.out.println("Object saved successfully.....!!");
		tx.commit();
		session.close();
		factory.close();
	}

	public HashMap GetMedicineOfPatient(Long patientId) {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		HashMap<String, String> map = new HashMap<String, String>();
		List<HashMap> values = new ArrayList<HashMap>();
		PatientDTO patient = (PatientDTO) session.get(PatientDTO.class, new Long(patientId));
		List<MedicineOfPatientDTO> q = session.createQuery("from MedicineOfPatientDTO a where a.patient=" + patientId)
				.list();
		for (MedicineOfPatientDTO medicineOfPatientDTO : q) {
			System.out.println("Döngüdeyiz keke");
			
			String daily = medicineOfPatientDTO.getDaily();
			String quantity = medicineOfPatientDTO.getQuantity();
			String treatmentDays = medicineOfPatientDTO.getTreatmentDays();

			SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String startDate = format.format(medicineOfPatientDTO.getStartDate());
			startDate = startDate.substring(0, 10);

			MedicineDTO medicine = medicineOfPatientDTO.getMedicine();
			String medicineName = medicine.getName();

			//values.add(new String[] { medicineName, startDate, quantity, daily, treatmentDays });
			System.out.println(startDate);
			
			map.put("medicineName", medicineName);
			map.put("startDate", startDate);
			map.put("quantity",quantity);
			map.put("daily",daily );
			map.put("treatmentDays", treatmentDays);
			values.add(map);
		}
		
		values.forEach(item->System.out.println(item));
		
		
		
		System.out.println("Parameters put succesfully ...!!");
		return map;

	}
}
