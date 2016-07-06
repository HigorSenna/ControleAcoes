
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
    
    @Column(name="VL_TOTAL_VENDA")
    private double valorVendaAcao;
    
    @Column(name="QTD_VENDA")
    private int quantidade;
    
    @Column(name="LUCRO_PREJUIZO")
    private double lucroPrejuizo;
    
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

	public double getValorVendaAcao() {
		return valorVendaAcao;
	}

	public void setValorVendaAcao(double valorVendaAcao) {
		this.valorVendaAcao = valorVendaAcao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}	

	public double getLucroPrejuizo() {
		return lucroPrejuizo;
	}

	public void setLucroPrejuizo(double lucroPrejuizo) {
		this.lucroPrejuizo = lucroPrejuizo;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idVenda == null) ? 0 : idVenda.hashCode());
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
		Venda other = (Venda) obj;
		if (idVenda == null) {
			if (other.idVenda != null)
				return false;
		} else if (!idVenda.equals(other.idVenda))
			return false;
		if (nomeAcao == null) {
			if (other.nomeAcao != null)
				return false;
		} else if (!nomeAcao.equals(other.nomeAcao))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "br.com.javaweb.model.Vendas[ idVenda=" + idVenda + " ]";
    }
    
}
