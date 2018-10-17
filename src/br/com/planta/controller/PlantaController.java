package br.com.planta.controller;

import br.com.planta.dao.PlantaDAO;
import br.com.planta.model.Planta;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ViewScoped
@ManagedBean
public class PlantaController {/*Classe de Ações de Negócios*/
    private Planta planta;
    private List<Planta> listaPlantas;
    private String campoBusca;
    private String campoBuscaTipo;
    private FacesMessage msg;
    private FacesContext ct;
    private PlantaDAO plantaDAO = new PlantaDAO();
    
    public PlantaController() {
        planta = new Planta();
        this.campoBusca = "";
        this.campoBuscaTipo = "N";//N = nome
    }
    
    public void limparBusca() {
    	this.listaPlantas = null;
    	this.campoBusca = "";
    	this.campoBuscaTipo = "N";
    }

    public void limparCampos(){
    	this.listaPlantas = null;
        planta = new Planta();
    }
    
    //está sendo chamado no JSF no principal.xhtml
    public void busca(){
        if(this.campoBuscaTipo.equals("N")) {
            buscaNome();
        }else{
            buscaTipo();
        }
    }
    
    private void buscaTipo() {
        this.listaPlantas = plantaDAO.buscaTipo(this.campoBusca);
    }
    
    private void buscaNome() {
        this.listaPlantas = plantaDAO.buscaNome(this.campoBusca);
    }
    
     public void delPlanta() {         
        boolean deletou = plantaDAO.delPlanta(this.planta);        
        if(deletou) {
            limparCampos();
            msg = new FacesMessage("Planta Removida!");
        } else
            msg = new FacesMessage("Erro ao Remover Planta!");
        ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, msg);
    }

     public void altPlanta() {
        boolean alterou = plantaDAO.altPlanta(planta);        
        if(alterou) {
            limparCampos();            
            RequestContext.getCurrentInstance().execute("PF('principalAlterPlanta').hide();");
            msg = new FacesMessage("Alterado com Sucesso!");
        } else
        	msg = new FacesMessage("Erro ao Alterar!");
        ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, msg);
    }
    
    public void cadPlanta() {
        boolean cadastrou = plantaDAO.cadPlanta(planta);        
        if(cadastrou) {            
            limparCampos();            
            RequestContext.getCurrentInstance().execute("PF('principalCadPlanta').hide();");            
            msg = new FacesMessage("Planta Cadastrada!");
        } else
            msg = new FacesMessage("Erro ao Cadastrar Planta!");
        ct = FacesContext.getCurrentInstance();
        ct.addMessage(null, msg);
    }
   
    public Planta getPlanta() {
        return this.planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }

    public List<Planta> getListaPlanta() {
        if (this.listaPlantas == null) {
        	this.listaPlantas = plantaDAO.listaPlantas();
        }
        return this.listaPlantas;
    }

    public void setListaPlanta(List<Planta> listaPlanta) {
        this.listaPlantas = listaPlanta;
    }

    public String getCampoBusca() {
        return this.campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

    public String getCampoBuscaTipo() {
        return this.campoBuscaTipo;
    }

    public void setCampoBuscaTipo(String campoBuscaTipo) {
        this.campoBuscaTipo = campoBuscaTipo;
    }
}