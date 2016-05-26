package br.com.javaweb.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesAndRedirect {
	private static FacesContext context;
	
	private static void manterMensagemAoRedirecionar(){
		context.getExternalContext().getFlash().setKeepMessages(true);		
	}
	
	public static void exibirMensagemSucessoRedirect(String msg,String paginaRedirecionar){
		context = FacesContext.getCurrentInstance();
		FacesMessage formatoMensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,"",msg);		
		manterMensagemAoRedirecionar();
		context.addMessage(null,formatoMensagem);
		redirecionarPara(paginaRedirecionar);
	}
	
	public static void exibirMensagemAviso(String msg){
		context = FacesContext.getCurrentInstance();
		FacesMessage formatoMensagem = new FacesMessage(FacesMessage.SEVERITY_WARN,"",msg);		
		manterMensagemAoRedirecionar();
		context.addMessage(null,formatoMensagem);
	}
	
	public static void exibirMensagemErroRedirect(String msg,String paginaRedirecionar){
		context = FacesContext.getCurrentInstance();
		FacesMessage formatoMensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,"",msg);		
		manterMensagemAoRedirecionar();
		context.addMessage(null,formatoMensagem);
		redirecionarPara(paginaRedirecionar);
	}
	
	public static void redirecionarPara(String pagina){
		context = FacesContext.getCurrentInstance();
		try{
			context.getExternalContext().redirect(pagina);
		}
		catch(Exception ex){			
			System.out.print(ex.getMessage());
		}		
	}
}
