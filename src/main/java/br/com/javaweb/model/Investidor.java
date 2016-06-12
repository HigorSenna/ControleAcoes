
package br.com.javaweb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.transacoes.model.HistoricoTransacao;
import br.com.javaweb.transacoes.model.Venda;


@Entity
@Table(name="investidores"  ,catalog = "controle_acoes", schema = "")
public class Investidor implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    @Column(name = "ID_INVESTIDOR", nullable = true)
    private Integer idInvestidor;
    
    @Column(name="CPF" , nullable = true)
    private int cpf;
      
    @Size(min = 1, max = 45)
    @Column(name="LOGIN",nullable = true, length = 45)
    private String login;
      
    @Size(min = 1, max = 40)
    @Column(name = "NM_INVESTIDOR", nullable = true, length = 40)
    private String nomeInvestidor;
        
    @Size(min = 1, max = 45)
    @Column(nullable = true, length = 45)
    private String profissao;
        
    @Size(min = 1, max = 12)
    @Column(name="RG",nullable = true, length = 12)
    private String rg;
     
    @Size(min = 1, max = 45)
    @Column(name="SENHA",nullable = true, length = 45)
    private String senha;
    
    @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID_CONTA")
    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    private ContaBancaria idConta;
    
    @OneToMany(mappedBy = "idInvestidor", fetch = FetchType.LAZY)
    private List<HistoricoTransacao> historicosTransacoesList;
    
    @OneToMany(mappedBy = "idInvestidor", fetch = FetchType.LAZY)
    private List<Venda> vendasList;
    
    @OneToMany(mappedBy = "idInvestidor", fetch = FetchType.LAZY)
    private List<Compra> comprasList;

    public Investidor() {
    }

    public Investidor(Integer idInvestidor) {
        this.idInvestidor = idInvestidor;
    }

    public Investidor(Integer idInvestidor, int cpf, String login, String nomeInvestidor, String profissao, String rg, String senha) {
        this.idInvestidor = idInvestidor;
        this.cpf = cpf;
        this.login = login;
        this.nomeInvestidor = nomeInvestidor;
        this.profissao = profissao;
        this.rg = rg;
        this.senha = senha;
    }
    
    public String calcularLucro(){
    	int elementoFinalLista = this.comprasList.size()-1;
    	int antesDoUltimoElementoLista = elementoFinalLista - 1;
    	if(elementoFinalLista > 0){
    		double diferenca = comprasList.get(elementoFinalLista).getValorFinalAcao() - comprasList.get(antesDoUltimoElementoLista).getValorFinalAcao();

        	if(diferenca < 0){
        		diferenca = diferenca*(-1);
        		return String.format("%.2f", diferenca);
        	}
        	else{
        		return "Nao obteve Lucro";
        	}
    	}
    	return "Fez apenas uma compra";
    	
    }
    
    //LUCRO / PREJUIZO NAO ESTAO SENDO EXIBIDOS CORRETAMENTE, ACOES IGUAIS QUE DEVEM SER COMPARADAS, 
    //E MESMO ASSIM QUANDO COMPARADAS AS MESMAS ACOES (CASO OCORRA POR COINCIDENCIA) ESTA CALCULANDO ERRADO
    public String calcularPrejuizo(){
    	int elementoFinalLista = this.comprasList.size()-1;
    	int antesDoUltimoElementoLista = elementoFinalLista - 1;    	
    	if(elementoFinalLista > 0){
    		double diferenca = comprasList.get(elementoFinalLista).getValorFinalAcao() - comprasList.get(antesDoUltimoElementoLista).getValorFinalAcao();
        	if(diferenca > 0){        		
        		return String.format("%.2f", diferenca);
        	}
        	else{
        		return "Nao obteve Prejuizo";
        	}
    	}
    	return "Fez apenas uma compra";
    }    

    public Integer getIdInvestidor() {
        return idInvestidor;
    }

    public void setIdInvestidor(Integer idInvestidor) {
        this.idInvestidor = idInvestidor;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNomeInvestidor() {
        return nomeInvestidor;
    }

    public void setNomeInvestidor(String nomeInvestidor) {
        this.nomeInvestidor = nomeInvestidor;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ContaBancaria getIdConta() {
        return idConta;
    }

    public void setIdConta(ContaBancaria idConta) {
        this.idConta = idConta;
    }

    public List<HistoricoTransacao> getHistoricosTransacoesList() {
        return historicosTransacoesList;
    }

    public void setHistoricosTransacoesList(List<HistoricoTransacao> historicosTransacoesList) {
        this.historicosTransacoesList = historicosTransacoesList;
    }

    public List<Venda> getVendasList() {
        return vendasList;
    }

    public void setVendasList(List<Venda> vendasList) {
        this.vendasList = vendasList;
    }
    
    public List<Compra> getComprasList() {
		return comprasList;
	}

	public void setComprasList(List<Compra> comprasList) {
		this.comprasList = comprasList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idInvestidor != null ? idInvestidor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Investidor)) {
            return false;
        }
        Investidor other = (Investidor) object;
        if ((this.idInvestidor == null && other.idInvestidor != null) || (this.idInvestidor != null && !this.idInvestidor.equals(other.idInvestidor))) {
            return false;
        }
        return true;
    }
}
