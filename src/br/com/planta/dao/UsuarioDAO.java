package br.com.planta.dao;

import br.com.planta.factory.ConnectionFactory;
import br.com.planta.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private static ConnectionFactory CONNECTION;
    private Connection conexao;
    private Usuario user = null;
    
    public UsuarioDAO() {
    	this.CONNECTION = new ConnectionFactory();
    	
    }
    public Usuario login(Usuario usuario){    	
        try {       	 
        	 conexao = CONNECTION.getConnection();
        	 String sql = "select * from planta.usuario where email = ? and senha = ?";    
        	 PreparedStatement ps = conexao.prepareStatement(sql);        	 
             ps.setString(1, usuario.getEmail().toUpperCase());
             ps.setString(2, usuario.getSenha().toUpperCase());
             ResultSet rs = ps.executeQuery();
             
             while(rs.next()) {
            	 user = new Usuario();
                 user.setId(rs.getInt("id"));
            	 user.setNome(rs.getString("nome"));
            	 user.setEmail(rs.getString("email"));
            	 user.setSenha(rs.getString("senha"));
            	 user.setAtivo(rs.getBoolean("ativo"));
             }
             CONNECTION.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return user;
    }
    
    public boolean cadastrar(Usuario usuario){
    	try {
			conexao = CONNECTION.getConnection();
			String sql = "insert into planta.usuario (nome, email, senha) VALUES (?,?,?)";			
			PreparedStatement ps = conexao.prepareStatement(sql);
			
			ps.setString(1, usuario.getNome().toUpperCase());
			ps.setString(2, usuario.getEmail().toUpperCase());
			ps.setString(3, usuario.getSenha().toUpperCase());
			ps.execute();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
}