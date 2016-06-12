package br.com.javaweb.model;

import java.util.List;

import br.com.javaweb.transacoes.model.Compra;

public class Corretora {
	
	private List<Compra> compras;
	private double lucro;
	private int numeroTransacoesFeitas;
	
	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public double getLucro() {
		for(int i = 0; i< compras.size(); i++){
			this.lucro += compras.get(i).getTaxa();
		}
		return this.lucro;
	}

	public int getNumeroTransacoesFeitas() {
		numeroTransacoesFeitas = compras.size();
		return numeroTransacoesFeitas;
	}
}
