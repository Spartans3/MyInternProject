package client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dbConnection.HibernateUtil;

public class Test {

	
	
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
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
//		Session session = HibernateUtil.getSession();
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Transaction tx = session.beginTransaction();
//		
//	    System.out.println("Session Factory : " + sessionFactory.hashCode());
//	    System.out.println("Session Factory 2 : " + sessionFactory.hashCode());
//	    System.out.println("Session Factory 3 : " + sessionFactory.hashCode());
//	    tx.commit();
//	    System.out.println("committed");
//
//	    tx.begin();
//	    System.out.println("began");
//	    
//	    System.out.println("Session Factory : " + session.hashCode());
//	    System.out.println("Session Factory : " + session.hashCode());
//	    System.out.println("Session Factory : " + session.hashCode());
		
		
//		while(true) {
//			long tStart = System.nanoTime();
//			System.out.println(System.nanoTime()/1000);
//			if(tStart/1000==2) {
//				System.out.println("10 saniye");
//			}
//			System.out.println(System.nanoTime()/1000);
//		}
	
		
		Thread thread = new Thread();



			for (int i=60;i>0;i--) {

					try {
						Thread.sleep(1000);
						System.out.println(i);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (i==55) {
//					URL url = getClass().getResource("/sounds/Work.wav");
//					File riho = new File(url.getPath());
						
					File riho = new File("C:\\Users\\LENOVO\\eclipse-workspace\\InternProject\\src\\main\\resources\\sounds\\Work.wav");
						
						
						Clip clip = null;
						try {
							clip = AudioSystem.getClip();
						} catch (LineUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						clip.open(AudioSystem.getAudioInputStream(riho));
						clip.start();
						
						
						Timer timer = new Timer();
						
						
						
					}
				
				
			}
		}

		
		
	}

	
