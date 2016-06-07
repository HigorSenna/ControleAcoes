package br.com.javaweb.controller;


import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.javaweb.model.Acao;
import br.com.javaweb.utils.ConnectorURL;

@ManagedBean
@ViewScoped
public class ConsultaAcoesController {

	public List<Acao> exibirAcoes(){
		try {
			return ConnectorURL.conectarNaUrlPegandoValoresDoWebService("http://cotacao.davesmartins.com.br/webCotacao/?cod=VALE5;PETR4;ITSA3;BBDC4;ABEV3;");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
