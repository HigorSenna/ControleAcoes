package br.com.javaweb.model;

public class Acao {
	
	private String nomeAcao;
	private String aberturaCotacao;
	private String valorUltimaCotacao;
	private String variacao;
	private String maximoCotacaoDia;
	private String minimoCotacaoDia;
	private String mediaAcaoDia;
	
	public Acao() {
		
	}
	public String getNomeAcao() {
		return nomeAcao;
	}
	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
	}
	public String getAberturaCotacao() {
		return aberturaCotacao;
	}
	public void setAberturaCotacao(String aberturaCotacao) {
		this.aberturaCotacao = aberturaCotacao;
	}
	public String getValorUltimaCotacao() {
		return valorUltimaCotacao;
	}
	public void setValorUltimaCotacao(String valorUltimaCotacao) {
		this.valorUltimaCotacao = valorUltimaCotacao;
	}
	public String getVariacao() {
		return variacao;
	}
	public void setVariacao(String variacao) {
		this.variacao = variacao;
	}
	public String getMaximoCotacaoDia() {
		return maximoCotacaoDia;
	}
	public void setMaximoCotacaoDia(String maximoCotacaoDia) {
		this.maximoCotacaoDia = maximoCotacaoDia;
	}
	public String getMinimoCotacaoDia() {
		return minimoCotacaoDia;
	}
	public void setMinimoCotacaoDia(String minimoCotacaoDia) {
		this.minimoCotacaoDia = minimoCotacaoDia;
	}
	public String getMediaAcaoDia() {
		return mediaAcaoDia;
	}
	public void setMediaAcaoDia(String mediaAcaoDia) {
		this.mediaAcaoDia = mediaAcaoDia;
	}	
}
