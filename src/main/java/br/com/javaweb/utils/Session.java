package br.com.javaweb.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.javaweb.model.Investidor;

public class Session  {
	private static FacesContext context;
	private static HttpSession session;
	
	private static void inicializarSessao(){
		context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);	
	}
	
	public static void criarSessao(String nomeSessao, Object nomeUsuario) {
		inicializarSessao();
		session.setAttribute(nomeSessao, nomeUsuario);
	}

	public static void fecharSessao() {			
		session.invalidate();
	}

	public static boolean existeSessao(String nomeSessao) {		
		if (session.getAttribute(nomeSessao) != null) {
			return true;
		}
		return false;
	}
	
	public static Object pegarSessao(){
		return (Investidor) session.getAttribute("user");
	}
}
