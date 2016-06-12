
package br.com.javaweb.transacoes.model;

import br.com.javaweb.model.Investidor;
import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="vendas",catalog = "controle_acoes", schema = "")
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VENDA", nullable = false)
    private Integer idVenda;
    
    @Column(name = "DT_VENDA")
    @Temporal(TemporalType.DATE)
    private Date dtVenda;
    
    @JoinColumn(name = "ID_INVESTIDOR", referencedColumnName = "ID_INVESTIDOR")
    @ManyToOne(fetch = FetchType.LAZY)
    private Investidor idInvestidor;
    
    @Column(name="NM_ACAO")
    private String nomeAcao;
    
    @Column(name="VL_VENDA")
    private double valorVendaAcao;
    
    @Column(name="VL_RECEBIDO")
    private double valorRecebido;
    
    @Column(name="QTD_VENDA")
    private int quantidade;
    
    public Venda() {
    }

    public Venda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Date getDtVenda() {
        return dtVenda;
    }

    public void setDtVenda(Date dtVenda) {
        this.dtVenda = dtVenda;
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

	public double getValorFinalAcao() {
		return valorVendaAcao;
	}

	public void setValorFinalAcao(double valorVendaAcao) {
		this.valorVendaAcao = valorVendaAcao;
	}

	public double getValorRecebido() {
		return valorRecebido;
	}

	public void setValorRecebido(double valorRecebido) {
		this.valorRecebido = valorRecebido;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenda != null ? idVenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.idVenda == null && other.idVenda != null) || (this.idVenda != null && !this.idVenda.equals(other.idVenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.javaweb.model.Vendas[ idVenda=" + idVenda + " ]";
    }
    
}
