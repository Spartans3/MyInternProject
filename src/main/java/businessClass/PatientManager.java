package businessClass;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import dbConnection.DbUtil;
import object.PatientDTO;

public class PatientManager {
	
	public void SetPatient(String name, String surname, String tc, String tel, Date birthday) {
		Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
 
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        PatientDTO patient = new PatientDTO();
        patient.setName(name);
        patient.setSurname(surname);
        patient.setTc(tc);
        patient.setTel(tel);
        patient.setBirthday(birthday);
 
        Transaction tx = session.beginTransaction();
        session.save(patient);
        System.out.println("Patient saved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();
	}
	
	public void RemovePatient(String tc) {
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		PatientDTO patient = new PatientDTO();
		
		
		String hql= " from PatientDTO where tc = '"+ tc +"' ";
		Query query = DbUtil.getConnection().createQuery(hql);
		List<PatientDTO> result = query.list();
		
		
		
		patient = (PatientDTO) session.get(PatientDTO.class, result.get(0).getId());
		
		session.delete(patient);
		session.remove(patient);
		
		session.getTransaction().commit();
		session.close();
		
	}
	
	public List ListPatients() {
		

		List data;
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		data = session.createQuery("from PatientDTO").list();
		
		return data;		
	}

}

