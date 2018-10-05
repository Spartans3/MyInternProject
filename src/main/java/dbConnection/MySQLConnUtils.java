package dbConnection;


import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;



public class MySQLConnUtils {
	public static Connection getMySQLConnection()
            throws ClassNotFoundException, SQLException {
        String hostName = "localhost";
        String dbName = "intern_project";
        String userName = "root";
        String password = "1593572846";
        return getMySQLConnection(hostName, dbName, userName, password);
    }
 
    public static Connection getMySQLConnection(String hostName, String dbName,
            String userName, String password) throws SQLException, ClassNotFoundException {
 
        // Declare the class Driver for MySQL DB
        // This is necessary with Java 5 (or older)
        // Java6 (or newer) automatically find the appropriate driver.
        // If you use Java> 5, then this line is not needed.
        Class.forName("com.mysql.jdbc.Driver");
 

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName+ "?autoReconnect=true&useSSL=false";
 
        Connection conn = (Connection) DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
    
	/*public static Connection getMySQLConnection() {
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}  
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo","root","root");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null; 
	}*/
}
