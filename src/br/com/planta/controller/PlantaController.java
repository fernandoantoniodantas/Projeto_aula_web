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
public class PlantaController {

    private Planta planta;
    private List<Planta> listaPlanta;
    private String campoBusca;
    private String campoBuscaTipo;
     
    public PlantaController() {
        planta = new Planta();
        campoBusca = "";
        campoBuscaTipo = "N";//N?
    }
    
    public void limparBusca() {
        listaPlanta = null;
        campoBusca = "";
        campoBuscaTipo = "N";
    }

    public void limparCampos(){
        listaPlanta = null;
        planta = new Planta();
    }
    
    public void busca(){
        if(campoBuscaTipo.equals("N")) {
            buscaNome();
        }else{
            buscaTipo();
        }
    }
    
    private void buscaTipo() {
        PlantaDAO pd = new PlantaDAO();
        listaPlanta = pd.buscaTipo(campoBusca);
    }
    
     private void buscaNome() {
        PlantaDAO pd = new PlantaDAO();
        listaPlanta = pd.buscaNome(campoBusca);
    }
    
     public void delPlanta() {
         
        PlantaDAO pd = new PlantaDAO();
        boolean deletou = pd.delPlanta(planta);
        
        if(deletou) {
            limparCampos();
            FacesMessage msg = new FacesMessage("deletado");
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("erro ao deletar");
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, msg);
        }
    }
    /*Update*/
     public void altPlanta() {
         
        PlantaDAO pd = new PlantaDAO();
        boolean alterou = pd.altPlanta(planta);
        
        if(alterou) {
            limparCampos();
            
            RequestContext.getCurrentInstance().execute("PF('dlgAltPlanta').hide();");
            
            FacesMessage msg = new FacesMessage("alterado");
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("erro ao alterar");
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, msg);
        }
    }
    
    public void cadPlanta() {
        
        PlantaDAO pd = new PlantaDAO();
        boolean cadastrou = pd.cadPlanta(planta);
        
        if(cadastrou) {
            
            limparCampos();
            
            RequestContext.getCurrentInstance().execute("PF('dlgCadPlanta').hide();");
            
            FacesMessage msg = new FacesMessage("cadastrado");
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("erro ao cadastrar");
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, msg);
        }
    }

    public Planta getP() {
        return planta;
    }

    public void setP(Planta planta) {
        this.planta = planta;
    }

    public List<Planta> getListaPlanta() {
        PlantaDAO pd = new PlantaDAO();
        if (listaPlanta == null) {
            listaPlanta = pd.listaPlanta();
        }
        return listaPlanta;
    }

    public void setListaPlanta(List<Planta> listaPlanta) {
        this.listaPlanta = listaPlanta;
    }

    public String getCampoBusca() {
        return campoBusca;
    }

    public void setCampoBusca(String campoBusca) {
        this.campoBusca = campoBusca;
    }

    public String getCampoBuscaTipo() {
        return campoBuscaTipo;
    }

    public void setCampoBuscaTipo(String campoBuscaTipo) {
        this.campoBuscaTipo = campoBuscaTipo;
    }
}