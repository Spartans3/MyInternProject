package dbConnection;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class MySQLConnUtils {
	public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String dbName = "intern_project";
		String userName = "root";
		String password = "1593572846";
		return getMySQLConnection(hostName, dbName, userName, password);
	}

	public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password)
			throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");

		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";

		Connection conn = (Connection) DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}

}
