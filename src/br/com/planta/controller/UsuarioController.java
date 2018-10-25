package br.com.planta.controller;

import br.com.planta.dao.UsuarioDAO;
import br.com.planta.model.Usuario;
import br.com.planta.util.SessionUtil;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

@SessionScoped
@ManagedBean
public class UsuarioController {
    
    private Usuario usuario;
    private Usuario usuarioLogado;
    private String sessaoExpirada;
    private FacesMessage msg;
    private FacesContext ct;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioController() {
        usuario = new Usuario();
        usuarioLogado = null;
        sessaoExpirada = "N";
    }
    
    public String login() {
        usuarioDAO = new UsuarioDAO();
        usuarioLogado = usuarioDAO.login(usuario);
        this.ct = FacesContext.getCurrentInstance();
        
        if(usuarioLogado != null) {
        	this.ct.getExternalContext().getSessionMap().put("usuario_session", usuarioLogado);           
            return "principal.faces?faces-redirect=true";
        } else {
        	this.msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Email ou Senha inválido(a)!");
        	this.ct.addMessage(null, this.msg);
            return "";
        }
    }
    
    public void cadastrar() {    	
    	boolean cadastrou = usuarioDAO.cadastrar(usuario);	
    	if(cadastrou) {
    		setUsuario(new Usuario());
    		RequestContext.getCurrentInstance().execute("PF('indexCadUsuario').hide();");
    		this.msg = new FacesMessage("Usuário(a) Cadastrado(a)!");
    	}else {
    		this.msg = new FacesMessage("E-mail Já Existe!");
    	} 
    	this.ct = FacesContext.getCurrentInstance();
    	this.ct.addMessage(null, this.msg);
    }
    
    public void excluir() {
    	boolean excluiu = usuarioDAO.excluir(usuarioLogado);
    	if(excluiu) {
    		this.msg = new FacesMessage("Usuário(a) Removido(a)!");
    		setUsuarioLogado(usuario);
    	}else {
    		this.msg = new FacesMessage("Erro ao Excluir Usuário(a)!"); 
    	}
    	this.ct = FacesContext.getCurrentInstance();
    	this.ct.addMessage(null, this.msg);
    }
    
    public static void timeOut() throws IOException {
        if(SessionUtil.getSession() != null) {
            SessionUtil.getSession().invalidate();
        }
    }

    public String logout() {
        SessionUtil.getSession().invalidate();
        return "/index.faces?faces-redirect=true";
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