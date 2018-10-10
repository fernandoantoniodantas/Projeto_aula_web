package br.com.planta.dao;

import br.com.planta.factory.ConnectionFactory;
import br.com.planta.model.Planta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlantaDAO {
	private static ConnectionFactory CONNECTION;
    Connection conexao;
    
    public PlantaDAO() {
    	this.CONNECTION = new ConnectionFactory();
    }
    
    public List<Planta> buscaTipo(String tipo){       
        try {
        	conexao = CONNECTION.getConnection();
        	List<Planta> lista = new ArrayList<>();
        	String sql = "select * from planta.planta where tipo = ? order by nome";
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {                
                Planta planta = new Planta();
                planta.setId(rs.getInt("id"));
                planta.setNome(rs.getString("nome").toUpperCase());
                planta.setTipo(rs.getString("tipo"));
                planta.setValor(rs.getDouble("valor"));                
                lista.add(planta);
            }
            
            CONNECTION.closeConnection();
            return lista;        
        } 
        catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
     public List<Planta> buscaNome(String nome){   
        try {
        	conexao = CONNECTION.getConnection();
        	List<Planta> lista = new ArrayList<>();
        	String sql = "select * from planta.planta where upper(nome) like upper(?) order by nome";       	
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "%"+nome+"%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {                
                Planta planta = new Planta();
                planta.setId(rs.getInt("id"));
                planta.setNome(rs.getString("nome").toUpperCase());
                planta.setTipo(rs.getString("tipo"));
                planta.setValor(rs.getDouble("valor"));                
                lista.add(planta);
            }
            
            CONNECTION.closeConnection();            
            return lista;            
        }catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public boolean delPlanta(Planta planta){               
        try {
        	conexao =  CONNECTION.getConnection();   
        	String sql = "delete from planta.planta where id=?";
            
            PreparedStatement ps = conexao.prepareStatement(sql);            
            ps.setInt(1, planta.getId());            
            ps.execute();
            
            CONNECTION.closeConnection();
            return true;
        }catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /*altPlanta: método que edita dados da planta.*/
    public boolean altPlanta(Planta planta){        
        try {
        	conexao = CONNECTION.getConnection();      
        	String sql = "update planta.planta set nome=?, tipo=?, valor=? where id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            
            ps.setString(1, planta.getNome());
            ps.setString(2, planta.getTipo());
            ps.setDouble(3, planta.getValor());
            ps.setInt(4, planta.getId());            
            ps.executeUpdate();
            
            CONNECTION.closeConnection();
            return true;
            
        }catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /*altPlanta: método que cadastra uma planta.*/
    public boolean cadPlanta(Planta planta){
        try {
        	conexao = CONNECTION.getConnection();
        	String sql = "insert into planta.planta (nome, tipo, valor) values (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            
            ps.setString(1, planta.getNome());
            ps.setString(2, planta.getTipo());
            ps.setDouble(3, planta.getValor());
            ps.execute();
            
            CONNECTION.closeConnection();            
            return true;
        }catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }
    
    /*listaPlantas: lista todas as plantas cadastradas.*/
    public List<Planta> listaPlantas(){
        try {
        	conexao = CONNECTION.getConnection();
            List<Planta> lista = new ArrayList<>();
            String sql = "select * from planta.planta order by nome";
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {                
                Planta planta = new Planta();
                planta.setId(rs.getInt("id"));
                planta.setNome(rs.getString("nome"));
                planta.setTipo(rs.getString("tipo"));
                planta.setValor(rs.getDouble("valor"));
                lista.add(planta);               
            }
            CONNECTION.closeConnection();
            return lista;
        } catch (Exception e) {
			e.printStackTrace();
		}        
        return null;
    }
    
}
