package br.com.planta.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static Connection CONNECTION = null;
	private static final String USER = "postgres";
	private static final String PASS = "post";
	private static final String URL = "localhost:5432/aula";
	
	public static Connection getConnection() {
		
		try {
			Class.forName("org.postgresql.Driver");
			CONNECTION = DriverManager.getConnection("jdbc:postgresql://"+URL, USER, PASS);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return CONNECTION;
	}
	
	public static void closeConnection() {
		if (CONNECTION != null) {
			try {
				CONNECTION.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}