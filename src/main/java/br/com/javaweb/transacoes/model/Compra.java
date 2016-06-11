package br.com.javaweb.transacoes.model;

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
import javax.validation.constraints.NotNull;

import br.com.javaweb.model.Investidor;

@Entity
@Table(name="compras",catalog = "controle_acoes", schema = "")

public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMPRA", nullable = false)
    private Integer idCompra;
    
    @Column(name = "DT_COMPRA")
    @Temporal(TemporalType.DATE)
    private Date dtCompra;
    
    @JoinColumn(name = "ID_INVESTIDOR", referencedColumnName = "ID_INVESTIDOR")
    @ManyToOne(fetch = FetchType.LAZY)
    private Investidor idInvestidor;
    
    @Column(name="NM_ACAO")
    private String nomeAcao;
    
    @Column(name="VL_FIM_ACAO")
    private double valorFinalAcao;
    
//    @Column(name="QTD_ACOES")
//    private int quantidade;
       
    public Compra() {
    }    

    public Compra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Date getDtCompra() {
        return dtCompra;
    }

    public void setDtCompra(Date dtCompra) {
        this.dtCompra = dtCompra;
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
		return valorFinalAcao;
	}

	public void setValorFinalAcao(double valorFinalAcao) {
		this.valorFinalAcao = valorFinalAcao;
	}

//	public int getQuantidade() {
//		return quantidade;
//	}
//
//	public void setQuantidade(int quantidade) {
//		this.quantidade = quantidade;
//	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.javaweb.model.Compas[ idCompra=" + idCompra + " ]";
    }
    
}
