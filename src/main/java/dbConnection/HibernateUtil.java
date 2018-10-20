package dbConnection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	public static Session session;
	public static SessionFactory factory;

	private void HibernateUtility() {
	}

	public static synchronized SessionFactory getSessionFactory() {

		if (factory == null) {
			factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		}
		return factory;
	}

	public static synchronized Session getSession() {
		if (session == null) {
			session = getSessionFactory().openSession();
		}
		return session;
	}

}
