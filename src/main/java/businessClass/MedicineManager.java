package businessClass;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dbConnection.HibernateUtil;
import object.MedicineDTO;

public class MedicineManager {

	// HibernateUtil hbCon = new HibernateUtil();

	Session session = HibernateUtil.getSession();
	Transaction tx;

	// for adding medicine
	public void SetMedicine(String name, String barcode, Date expire_date, String producer)
			throws InterruptedException {

		MedicineDTO medicine = new MedicineDTO();
		medicine.setName(name);
		medicine.setBarcode(barcode);
		medicine.setExpire_date(expire_date);
		medicine.setProducer(producer);

		session.joinTransaction();
		session.getTransaction();
		session.save(medicine);
		System.out.println("medicine saved successfully.....!!");

	}

	// for removing a medicine as you see from the name of method ,does not check if
	// it has a relation with medicineofpatient db,
	// if it has any patient attached, mmedicine can't be deleted
	// because of foreign key is in the medicineofpatient

	public void RemoveMedicine(String barcode) {
		if (!session.getTransaction().isActive()) {
			tx = session.beginTransaction();
		}

		String hql = " update MedicineDTO set status=0 where barcode = '" + barcode + "' ";
		Query query = HibernateUtil.getSession().createQuery(hql);
		System.out.println(query.executeUpdate());

		tx.commit();

	}

	// for listing medicines
	public List ListMedicines() {

		List data;
		data = session.createQuery("from MedicineDTO where status=1").list();
		return data;
	}

}
