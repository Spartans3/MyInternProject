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
	Session session = DbUtil.getConnection();

	// checks if username and password true or not
	public boolean getUserbyUsername(UserDTO userDTO) {
		String hql = " from UserDTO where username = '" + userDTO.getUsername() + "' ";
		Query query = DbUtil.getConnection().createQuery(hql);
		List<UserDTO> listUser = query.list();
		if (listUser != null && !listUser.isEmpty()) {
			if (listUser.get(0).getPassword().equals(userDTO.getPassword()))
				return true;
			else
				return false;
		}

		return false;
	}

	// for adding an user , does not get called from anywhere
	// if need, just call it from a test class
	public void SetUser(String username, String password) {
		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setPassword(password);

		Transaction tx = session.beginTransaction();
		session.save(user);
		System.out.println("Patient saved successfully.....!!");
		tx.commit();
		session.close();
	}

}
