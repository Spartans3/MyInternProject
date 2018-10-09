package client;

import java.awt.Dimension;
import java.awt.Toolkit;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dbConnection.HibernateUtil;

public class Test {

	public static void main(String[] args) {
//		Configuration cfg = new Configuration();
//        cfg.configure("hibernate.cfg.xml");
// 
//        SessionFactory factory = cfg.buildSessionFactory();
//        Session session = factory.openSession();
//        /*
//        UserDTO user = new UserDTO();
//        user.setUsername("Mu11i");
//        user.setPassword("101111");
//		*/
//        PatientDTO patient = new PatientDTO();
//        MedicineDTO medicine = new MedicineDTO();
//         
//        MedicineOfPatientDTO medic = new MedicineOfPatientDTO();
//        medic.setMedicine(medicine);
//        medic.setPatient(patient);
//        medic.setDaily("2");
//        medic.setQuantity("4");
//       // medic.setStartDate(12,1,2018);
//        medic.setTreatmentDays("7");
//        
// 
//        Transaction tx = session.beginTransaction();
//        session.save(medic);
//        System.out.println("Object saved successfully.....!!");
//        tx.commit();
//        session.close();
//        factory.close();
		
//		Dimension wtf = Toolkit.getDefaultToolkit().getScreenSize();
//		System.out.println(wtf.getHeight());
		Session session = HibernateUtil.getSession();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Transaction tx = session.beginTransaction();
		
	    System.out.println("Session Factory : " + sessionFactory.hashCode());
	    System.out.println("Session Factory 2 : " + sessionFactory.hashCode());
	    System.out.println("Session Factory 3 : " + sessionFactory.hashCode());
	    tx.commit();
	    System.out.println("committed");

	    tx.begin();
	    System.out.println("began");
	    
	    System.out.println("Session Factory : " + session.hashCode());
	    System.out.println("Session Factory : " + session.hashCode());
	    System.out.println("Session Factory : " + session.hashCode());
	}

}
