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
    
    public UsuarioDAO() {
    	this.CONNECTION = new ConnectionFactory();
    }
    
    public Usuario login(Usuario usuario) {
        
        conexao = CONNECTION.getConnection();
        
        String sql = "select * from planta.usuario where email = ? and senha = ?";
        
        Usuario u = null;
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setAtivo(rs.getBoolean("ativo"));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }
}