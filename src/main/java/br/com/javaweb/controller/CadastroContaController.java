package br.com.javaweb.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.javaweb.model.ContaBancaria;

@ManagedBean
@ViewScoped
public class CadastroContaController {
	private ContaBancaria contaBancaria = new ContaBancaria();

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}	
	
}
