package br.com.planta.controller;

import br.com.planta.dao.UsuarioDAO;
import br.com.planta.model.Usuario;
import br.com.planta.util.SessionUtil;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

@SessionScoped
@ManagedBean
public class UsuarioSessionController {
    
    private Usuario usuario;
    private Usuario usuarioLogado;
    private String sessaoExpirada;
    private FacesMessage msg;
    private FacesContext ct;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioSessionController() {
        usuario = new Usuario();
        usuarioLogado = null;
        sessaoExpirada = "N";
    }
    
    public String login() {
        usuarioDAO = new UsuarioDAO();
        usuarioLogado = usuarioDAO.login(usuario);
        ct = FacesContext.getCurrentInstance();
        
        if(usuarioLogado != null) {
        	ct.getExternalContext().getSessionMap().put("usuario_session", usuarioLogado);           
            return "principal.faces?faces-redirect=true";
        } else {
        	msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Email ou Senha inválido(a)!");
            ct.addMessage(null, msg);
            return "";
        }
    }
    
    public void cadastrar() {    	
    	boolean cadastrou = usuarioDAO.cadastrar(usuario);	
    	if(cadastrou) {
    		RequestContext.getCurrentInstance().execute("PF('indexCadUsuario').hide();");
    		msg = new FacesMessage("Usuário(a) Cadastrado(a)!");
    	}else {
    		msg = new FacesMessage("Erro ao Cadastrar Usuário(a)!");
    	} 
    	ct = FacesContext.getCurrentInstance();
		ct.addMessage(null, msg);
    }
    
    public static void timeOut() throws IOException {
        if(SessionUtil.getSession() != null) {
            SessionUtil.getSession().invalidate();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessaoExpirada", "S");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/redirect.faces");
        }
    }

    public String logout() {
        SessionUtil.getSession().invalidate();
        return "redirect.faces?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
    
    public String getSessaoExpirada() {
    	ct = FacesContext.getCurrentInstance();
        sessaoExpirada = (String) ct.getExternalContext().getSessionMap().get("sessaoExpirada");
        return sessaoExpirada;
    }
}