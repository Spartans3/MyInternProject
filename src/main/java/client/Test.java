package client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import object.UserDTO;

public class Test {

	public static void main(String[] args) {
		Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
 
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        UserDTO user = new UserDTO();
        user.setUsername("Mu11i");
        user.setPassword("101111");

 
        Transaction tx = session.beginTransaction();
        session.save(user);
        System.out.println("Object saved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();

	}

}
