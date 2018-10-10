package br.com.planta.controller;

import br.com.planta.dao.UsuarioDAO;
import br.com.planta.model.Usuario;
import br.com.planta.util.SessionUtil;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class UsuarioSessionController {
    
    private Usuario usuario;
    private Usuario usuarioLogado;
    private String sessaoExpirada;

    public UsuarioSessionController() {
        usuario = new Usuario();
        usuarioLogado = null;
        sessaoExpirada = "N";
    }
    
    public String login() {
        UsuarioDAO usarioDAO = new UsuarioDAO();
        usuarioLogado = usarioDAO.login(usuario);
        
        if(usuarioLogado != null) {
        	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario_session", usuarioLogado);           
            return "principal.faces?faces-redirect=true";
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Email ou senha inválidos!");
            FacesContext ct = FacesContext.getCurrentInstance();
            ct.addMessage(null, msg);
            return "";
        }
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
        return "/redirect.faces?faces-redirect=true";
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
        sessaoExpirada = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessaoExpirada");
        return sessaoExpirada;
    }
}