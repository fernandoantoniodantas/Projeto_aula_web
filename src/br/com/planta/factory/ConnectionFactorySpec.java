package br.com.planta.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactorySpec {
	
	private static ConnectionFactory CONNECTION;
		
	public ConnectionFactorySpec() {//constructor
		this.CONNECTION = new ConnectionFactory();
	}	
	public static void main(String args []) throws SQLException{
		try {
			Connection connection = CONNECTION.getConnection();
			PreparedStatement ps = connection.prepareStatement("select * from planta.usuario order by nome");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("nome").toUpperCase());
			}			
			System.out.println("Conexão realizada com sucesso!");
			CONNECTION.closeConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
