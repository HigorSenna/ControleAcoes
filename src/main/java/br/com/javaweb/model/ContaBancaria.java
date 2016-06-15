/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.javaweb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "contas_bancarias", catalog = "controle_acoes", schema = "")
public class ContaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTA", nullable = false)
    private Integer idConta;
    
    @Size(min = 1, max = 10)
    @Column(name="NUM_AGENCIA",nullable = false, length = 10)
    private String agencia;
    
    @Size(min = 1, max = 45)
    @Column(name = "NM_BANCO", nullable = false, length = 45)
    private String nmBanco;
    
    @Column(name = "NUM_CONTA", nullable = false)
    private long numConta;

    @Column(name="SALDO",nullable = false, precision = 7, scale = 2)
    private double saldo;
    @OneToMany(mappedBy = "idConta", fetch = FetchType.LAZY)
    private List<Investidor> investidoresList;

    public ContaBancaria() {
    	this.saldo = 500;
    }

    public ContaBancaria(Integer idConta) {
        this.idConta = idConta;
    }

    public ContaBancaria(Integer idConta, String agencia, String nmBanco, int numConta, double saldo) {
        this.idConta = idConta;
        this.agencia = agencia;
        this.nmBanco = nmBanco;
        this.numConta = numConta;
        this.saldo = saldo;
    }

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNmBanco() {
        return nmBanco;
    }

    public void setNmBanco(String nmBanco) {
        this.nmBanco = nmBanco;
    }

    public long getNumConta() {
        return numConta;
    }

    public void setNumConta(long numConta) {
        this.numConta = numConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @XmlTransient
    public List<Investidor> getInvestidoresList() {
        return investidoresList;
    }

    public void setInvestidoresList(List<Investidor> investidoresList) {
        this.investidoresList = investidoresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConta != null ? idConta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContaBancaria)) {
            return false;
        }
        ContaBancaria other = (ContaBancaria) object;
        if ((this.idConta == null && other.idConta != null) || (this.idConta != null && !this.idConta.equals(other.idConta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.javaweb.model.ContasBancarias[ idConta=" + idConta + " ]";
    }
    
}
