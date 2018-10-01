package businessClass;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import object.MedicineDTO;
import object.MedicineOfPatientDTO;
import object.PatientDTO;

public class MedicineOfPatientManager {
	
	public void SetMedicineOfPatient(String quantity, String treatmentDays, String daily, Date startDate, Long patientId, Long medicineId) {
		Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
 
        SessionFactory factory = cfg.buildSessionFactory();
                Session session = factory.openSession();
        
     PatientDTO patient = (PatientDTO) session.get(PatientDTO.class, new Long(patientId));
     MedicineDTO medicine =(MedicineDTO) session.get(MedicineDTO.class, new Long(medicineId));
      
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
	
	
	
	
	

}
