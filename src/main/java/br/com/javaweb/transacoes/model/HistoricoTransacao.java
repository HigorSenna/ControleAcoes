package br.com.javaweb.transacoes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.javaweb.model.Investidor;


@Entity
@Table(name = "historicos_transacoes", catalog = "controle_acoes", schema = "")
public class HistoricoTransacao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_HISTORICO", nullable = false)
    private Integer idHistorico;
    
    @JoinColumn(name = "ID_INVESTIDOR", referencedColumnName = "ID_INVESTIDOR")
    @ManyToOne(fetch = FetchType.LAZY)
    private Investidor idInvestidor;
    
    @Column(name="NM_ACAO")
    private String nomeAcao;
    
    @Column(name="QTD_TOTAL")
    private int quantidadeTotal;
    
    @Column(name="VL_COMPRA")
    private double valorDeCompra;
    
    @Column(name="DT_ULT_ATUALIZACAO")
    private Date dataAtualizacao;
    
    @Transient
    private double valorVendaAcao;
    
    public double getLucroOuPrejuizo(){    	
    	double valorLucroPrejuizo = this.valorVendaAcao - this.valorDeCompra;
    	BigDecimal bd = new BigDecimal(valorLucroPrejuizo).setScale(3, RoundingMode.HALF_EVEN);   	
    	
    	return bd.doubleValue();
    }
    
    public String getStyleClass(){    	
    	if(getLucroOuPrejuizo()> 0){
    		return "lucro";
    	}
    	return "prejuizo";
    }

    public HistoricoTransacao() {
    }

    public HistoricoTransacao(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Integer getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Investidor getIdInvestidor() {
        return idInvestidor;
    }

    public void setIdInvestidor(Investidor idInvestidor) {
        this.idInvestidor = idInvestidor;
    }

    public String getNomeAcao() {
		return nomeAcao;
	}

	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
	}

	public int getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(int quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public double getValorDeCompra() {
		valorDeCompra = this.quantidadeTotal / this.valorDeCompra;
		BigDecimal bd = new BigDecimal(valorDeCompra).setScale(3, RoundingMode.HALF_EVEN);   
		return bd.doubleValue();
	}

	public void setValorDeCompra(double valorDeCompra) {
		this.valorDeCompra = valorDeCompra;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	public double getValorVendaAcao() {		
		return valorVendaAcao;
	}	

	public void setValorVendaAcao(double valorVendaAcao) {
		this.valorVendaAcao = valorVendaAcao;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistorico != null ? idHistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoTransacao)) {
            return false;
        }
        HistoricoTransacao other = (HistoricoTransacao) object;
        if ((this.idHistorico == null && other.idHistorico != null) || (this.idHistorico != null && !this.idHistorico.equals(other.idHistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.javaweb.model.HistoricosTransacoes[ idHistorico=" + idHistorico + " ]";
    }
    
}
