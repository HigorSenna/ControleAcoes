package br.com.javaweb.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesAndRedirect {
	private static final FacesContext context = FacesContext.getCurrentInstance();
//	FacesContext context = FacesContext.getCurrentInstance();
//	context.getExternalContext().getFlash().setKeepMessages(true);
//	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"","Registro Salvo com Sucesso!"));
//	FacesContext.getCurrentInstance().getExternalContext().redirect("principal.xhtml");
	
	private static void manterMensagemAoRedirecionar(){
		context.getExternalContext().getFlash().setKeepMessages(true);		
	}
	
	public static void exibirMensagemSucessoRedirect(String msg,String paginaRedirecionar) throws Exception{
		FacesMessage formatoMensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"",msg);		
		manterMensagemAoRedirecionar();
		context.addMessage(null,formatoMensagem);
		redirecionarPara(paginaRedirecionar);
	}
	
	public static void exibirMensagemAviso(String msg){
		FacesMessage formatoMensagem = new FacesMessage(FacesMessage.SEVERITY_WARN,"",msg);		
		manterMensagemAoRedirecionar();
		context.addMessage(null,formatoMensagem);
	}
	
	public static void exibirMensagemErroRedirect(String msg,String paginaRedirecionar) throws Exception{
		FacesMessage formatoMensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,"",msg);		
		manterMensagemAoRedirecionar();
		context.addMessage(null,formatoMensagem);
		redirecionarPara(paginaRedirecionar);
	}
	
	public static void redirecionarPara(String pagina) throws Exception{
		context.getExternalContext().redirect(pagina);
	}
}
