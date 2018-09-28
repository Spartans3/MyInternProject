package client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import object.MedicineDTO;
import object.MedicineOfPatientDTO;
import object.PatientDTO;
import object.UserDTO;

public class Test {

	public static void main(String[] args) {
		Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
 
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        /*
        UserDTO user = new UserDTO();
        user.setUsername("Mu11i");
        user.setPassword("101111");
		*/
        PatientDTO patient = new PatientDTO();
        MedicineDTO medicine = new MedicineDTO();
         
        MedicineOfPatientDTO medic = new MedicineOfPatientDTO();
        medic.setMedicine(medicine);
        medic.setPatient(patient);
        medic.setDaily("2");
        medic.setQuantity("4");
       // medic.setStartDate(12,1,2018);
        medic.setTreatmentDays("7");
        
 
        Transaction tx = session.beginTransaction();
        session.save(medic);
        System.out.println("Object saved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();

	}

}
