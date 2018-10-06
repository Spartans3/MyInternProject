package businessClass;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dbConnection.DbUtil;
import object.PatientDTO;

public class PatientManager {
	Session session = DbUtil.getConnection();
	Transaction tx = session.beginTransaction();

	// for adding a patient
	public void SetPatient(String name, String surname, String tc, String tel, Date birthday) {

		PatientDTO patient = new PatientDTO();
		patient.setName(name);
		patient.setSurname(surname);
		patient.setTc(tc);
		patient.setTel(tel);
		patient.setBirthday(birthday);

		session.joinTransaction();
		session.save(patient);
		System.out.println("Patient saved successfully.....!!");

		tx.commit();

	}

	// for removing a patient , does not check if it has a medicine,
	// if it has any medicine attached, patient can't be deleted
	// because of foreign key is in the medicineofpatient
	public void RemovePatient(String tc) {

		session.joinTransaction();
		String hql = " delete  from PatientDTO where tc = :myTC ";
		Query query = session.createQuery(hql);
		query.setString("myTC", tc);
		System.out.println(query.executeUpdate());
		tx.commit();
	}

	public List ListPatients() {

		List data;
		data = session.createQuery("from PatientDTO").list();

		return data;
	}

}
