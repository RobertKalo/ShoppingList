package hu.kalo.shoppinglist;

import java.sql.*;

public class DB {

	private static final String	DRIVER			= "com.mysql.jdbc.Driver";
	private static final String	HOST_ADDRESS	= "jdbc:mysql://localhost/bevasarlo?characterEncoding=utf-8";
	private static final String	USER_NAME		= "szombati_javas";
	private static final String	PASSWORD		= "jelszo123";

	private static Connection	connection;

	public DB() {

		try {
			Class.forName( DRIVER );
			connection=DriverManager.getConnection( HOST_ADDRESS, USER_NAME, PASSWORD );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		return connection;
	}

}
