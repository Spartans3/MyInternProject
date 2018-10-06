package dbConnection;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class DbUtil {
	public static Session getConnection() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");

		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		return session;
	}

}
