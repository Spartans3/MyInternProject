package dbConnection;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
//	public static Session getSession() {
//		Configuration cfg = new Configuration();
//		cfg.configure("hibernate.cfg.xml");
//
//		SessionFactory factory = cfg.buildSessionFactory();
//		Session session = factory.openSession();
//		return session;
//	}
	
	

//	private static HibernateUtil instance = null;
//
//	   private static Session session;
//	   private static StandardServiceRegistry serviceRegistry;
//	   
//
//	   private HibernateUtil(){
//
//	        Configuration configuration = new Configuration();
//	        configuration.configure("hibernate.cfg.xml");
//
//	        SessionFactory factory = configuration.buildSessionFactory();
//	        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//	        session = (Session) configuration.buildSessionFactory(serviceRegistry);
//	   }
//
//	   public static HibernateUtil getInstance(){
//	        if(instance == null){
//	            instance  = new HibernateUtil();
//	        }
//	        return instance;
//	   }
//
//	   public static Session getSession() {
//	        return session;
//	   }
	
	public static Session session;
	public static SessionFactory factory;
//	public static Transaction tx=session.beginTransaction();
	
	private void HibernateUtility() {
    }
	public static synchronized SessionFactory getSessionFactory() {

        if (factory == null) {
            factory = new Configuration().configure("hibernate.cfg.xml").
                    buildSessionFactory();
        }
        return factory;
    }
	
	
	public static synchronized Session getSession() {
		if(session==null) {
			session =getSessionFactory().openSession();
		}
		return session;
	}
	
	
//	public static synchronized Transaction getTransAction() {
//		if(!tx.isActive()) {
//			tx= session.beginTransaction();
//		}
//		return tx;
//	}
//	
	

	}



