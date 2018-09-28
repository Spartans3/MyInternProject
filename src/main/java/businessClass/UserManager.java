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
import object.UserDTO;

public class UserManager {
	public boolean getUserbyUsername(UserDTO userDTO) {
		String hql= " from UserDTO where username = '"+userDTO.getUsername()+"' ";
		Query query = DbUtil.getConnection().createQuery(hql);
		List<UserDTO> listUser = query.list();
		if(listUser!=null && !listUser.isEmpty()) {
			if(listUser.get(0).getPassword().equals(userDTO.getPassword()))
				return true;
			else
				return false;
		}
			 
		return false;
	}
	
	public void SetUser(String username, String password) {
		Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
 
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setPassword(password);

 
        Transaction tx = session.beginTransaction();
        session.save(user);
        System.out.println("Patient saved successfully.....!!");
        tx.commit();
        session.close();
        factory.close();
	}
	
}
