package businessClass;

import java.util.List;

import org.hibernate.query.Query;

import dbConnection.DbUtil;
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
}
