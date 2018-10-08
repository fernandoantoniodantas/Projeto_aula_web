package br.com.planta.util;

import br.com.planta.controller.UsuarioSessionController;
import br.com.planta.model.Usuario;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AuthorizationListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        // Adquirindo o FacesContext.
        FacesContext facesContext = event.getFacesContext();

        // Página Atual.
        String currentPage = facesContext.getViewRoot().getViewId();

        // Verifica se está na página de login.
        boolean isLoginPage = (currentPage.lastIndexOf("index") > -1);

        // Recuperando objetos da sessão.
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        Usuario usuario = (Usuario) session.getAttribute("usuario_session");

        // Redireciona para a página de login quando a sessão expira.
        if(!isLoginPage && usuario == null) {
            try {
                UsuarioSessionController.timeOut();
                if(SessionUtil.getSession() != null) {
                    SessionUtil.getSession().invalidate();

                    FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("sessaoExpirada", "S");
                }
                FacesContext.getCurrentInstance().getExternalContext().redirect("redirect.faces");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
        	System.out.println("PÁGINA DE  LOGIN");
           /* if(isLoginPage) {
                System.out.println("PÁGINA DE  LOGIN");
            } else {
                System.out.println("NÃO É A PÁGINA DE LOGIN");
            }*/
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // Nada...
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}