package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLAccess {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	//change according to your port shown on xampp
	final private String host = "localhost:3309";
	//change to your own user name & pw
	final private String user = "root";
	final private String password = "";

	public MySQLAccess() throws Exception {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://" + host + "/newsagent?" + "user=" + user + "&password=" + password);
			System.out.println("Database connected successfully!");
		} catch (Exception e) {
			throw e;
		}	
	}
	
	public Connection getConnection() {
		return connect;
	}

}
