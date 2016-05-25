package br.com.javaweb.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Session {
	private static FacesContext context;
	private static HttpSession session = null;
	
	private static void criarSessao(){
		context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);	
	}
	
	public static void criarSessao(String nomeSessao, String nomeUsuario) {
		criarSessao();
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
}
