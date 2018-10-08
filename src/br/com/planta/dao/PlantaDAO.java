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
    
  //  public PlantaDAO() {
  //  	this.CONNECTION = new ConnectionFactory();
  //  }
    
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
            String sql = "select * from planta.planta where upper(nome) like upper(?) order by nome";
        	
            List<Planta> lista = new ArrayList<>();
             
            PreparedStatement ps = conexao.prepareStatement(sql);
            //ps.setString(1, "%"+nome+"%");
            //ps.setString(1, nome);
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
    
    public boolean delPlanta(Planta p){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "delete from planta.planta where id=?";
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            
            ps.setInt(1, p.getId());
            
            ps.execute();
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return false;
    }
    
    public boolean altPlanta(Planta p){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "update planta.planta set nome=?, tipo=?, valor=? where id=?";
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getTipo());
            ps.setDouble(3, p.getValor());
            ps.setInt(4, p.getId());
            
            ps.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return false;
    }
    
    public boolean cadPlanta(Planta p){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "insert into planta.planta (nome, tipo, valor) values (?,?,?)";
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getTipo());
            ps.setDouble(3, p.getValor());
            
            ps.execute();
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return false;
    }
    
    public List<Planta> listaPlanta(){
        
        conexao = ConnectionFactory.getConnection();
        
        String sql = "select * from planta.planta order by nome";
        
        List<Planta> lista = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Planta p = new Planta();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setTipo(rs.getString("tipo"));
                p.setValor(rs.getDouble("valor"));
                
                lista.add(p);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            try {
                conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }return lista;
    }
    
}
