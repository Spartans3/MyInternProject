package businessClass;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dbConnection.HibernateUtil;
import object.MedicineDTO;
import object.MedicineOfPatientDTO;
import object.PatientDTO;

public class MedicineOfPatientManager {
	Session session = HibernateUtil.getSession();
	Transaction tx ;
	//adding a new medicine for a patient
	public void SetMedicineOfPatient(String quantity, String treatmentDays, String daily, Date startDate,
			Long patientId, Long medicineId) {
		if(!session.getTransaction().isActive()) {
			tx= session.beginTransaction();
		}
		session.joinTransaction();
		PatientDTO patient = (PatientDTO) session.get(PatientDTO.class, new Long(patientId));
		MedicineDTO medicine = (MedicineDTO) session.get(MedicineDTO.class, new Long(medicineId));

		MedicineOfPatientDTO medpati = new MedicineOfPatientDTO();
		medpati.setQuantity(quantity);
		medpati.setTreatmentDays(treatmentDays);
		medpati.setDaily(daily);
		medpati.setStartDate(startDate);
		medpati.setPatient(patient);
		medpati.setMedicine(medicine);

		
		session.save(medpati);
		System.out.println("Patient saved successfully.....!!");
		tx.commit();

	}
	
//	public void DeleteMedicineOfPatient(long patientId) {
//		if(!session.getTransaction().isActive()) {
//			tx= session.beginTransaction();
//		}
//		session.joinTransaction();
//		String hql = "delete from MedicineOfPatientDTO where patientID="+patientId;
//		Query query = session.createQuery(hql);
//		
//		
//	}

}
