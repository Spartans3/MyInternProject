package businessClass;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import dbConnection.DbUtil;
import object.MedicineDTO;

public class MedicineManager {
	
	public void SetMedicine(String name, String barcode,Date expire_date ,String producer ) {
		Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
 
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        MedicineDTO medicine = new MedicineDTO();
        medicine.setName(name);
        medicine.setBarcode(barcode);
        medicine.setExpire_date(expire_date);
        medicine.setProducer(producer);
 
        Transaction tx = session.beginTransaction();
        session.save(medicine);
        System.out.println("medicine saved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();
	}
	
	public void RemoveMedicine(String barcode) {
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
				
		MedicineDTO medicine = new MedicineDTO();
		
		String hql= " from MedicineDTO where barcode = '"+ barcode +"' ";
		Query query = DbUtil.getConnection().createQuery(hql);
		
		List<MedicineDTO> result = query.list();
		
		medicine = (MedicineDTO) session.get(MedicineDTO.class, result.get(0).getId());
		
		session.delete(medicine);
		session.remove(medicine);
		
		session.getTransaction().commit();
		session.close();
		
	}
	
public List ListMedicines() {
		

		List data2;
		
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		data2 = session.createQuery("from MedicineDTO").list();
		
		return data2;		
	}
	
	
	
	
	
	
}
