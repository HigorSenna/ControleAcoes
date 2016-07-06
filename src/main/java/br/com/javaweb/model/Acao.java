package br.com.javaweb.model;

public class Acao {
	
	private String nomeAcao;
	private String aberturaCotacao;
	private String valorUltimaCotacao;
	private String variacao;
	private String maximoCotacaoDia;
	private String minimoCotacaoDia;
	private String mediaAcaoDia;
	private int quantidade;
	
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
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nomeAcao == null) ? 0 : nomeAcao.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Acao other = (Acao) obj;
		if (nomeAcao == null) {
			if (other.nomeAcao != null)
				return false;
		} else if (!nomeAcao.equals(other.nomeAcao))
			return false;
		return true;
	}
	
}
