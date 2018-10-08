package br.com.planta.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.primefaces.component.api.UIData;

import br.com.planta.factory.ConnectionFactory;
import br.com.planta.model.Planta;

@ManagedBean
@ViewScoped

public class ReportController implements Serializable{
	private Planta Dados = new Planta();
	private UIData linhalistagem;
	
	public ReportController() {}
	
    public void gerarRelatorioPlantas(ActionEvent action) throws IOException, ParseException {
        
        //String caminho = "WEB-INF/relatorios/plantan.jasper";
        
        //Map map = new HashMap();   
        
        //map.put("BRASAO", this.getServleContext().getRealPath("/WEB-INF/relatorios/brasao_novo.png"));
        //map.put("SUBREPORT_DIR", this.getServleContext().getRealPath(caminho) + File.separator);
        
        
        //this.executeReport(caminho, map, "WEB-INF/relatorios/");
        
		String caminho = "/WEB-INF/Relatorios/";
		String relatorio = caminho + "plantan.jasper";
		this.Dados = (Planta) linhalistagem.getRowData();
//		Integer matricula = Dados.getMatricula();

		Map map = new HashMap();
	//	map.put("mat", matricula);
		
//		map.put("brasao",
				
	//			this.getServleContext().getRealPath(
		//				"/WEB-INF/Relatorios/brasao.jpg"));
	//	map.put("SUBREPORT_DIR", this.getServleContext().getRealPath(caminho)
//				+ File.separator);
		this.executeReport(relatorio, map, "plantan.pdf");
        
    }
        
    private void executeReport(String relatorio, Map map, String filename) 
        throws IOException, ParseException {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        InputStream reportStream = context.getExternalContext().getResourceAsStream(relatorio);

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);

        ServletOutputStream servletOutputStream = response.getOutputStream();
        
        Connection conexao = null;
        try {
            conexao = ConnectionFactory.getConnection();

            JasperRunManager.runReportToPdfStream(reportStream, response.getOutputStream(), map, conexao);

            conexao.close();
        } catch(JRException | SQLException ex) {
            throw new RuntimeException(ex);
        }
        this.getFacesContext().responseComplete();
        servletOutputStream.flush();
        servletOutputStream.close();
    }
    
    private FacesContext getFacesContext() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context;
    }

    private ServletContext getServleContext() {
        ServletContext scontext = (ServletContext) this.getFacesContext()
            .getExternalContext().getContext();
        return scontext;
    }

	public Planta getDados() {
		return Dados;
	}

	public void setDados(Planta dados) {
		Dados = dados;
	}

	public UIData getLinhalistagem() {
		return linhalistagem;
	}

	public void setLinhalistagem(UIData linhalistagem) {
		this.linhalistagem = linhalistagem;
	}
}