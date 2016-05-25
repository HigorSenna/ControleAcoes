
package Utils.padrao.strategy;

import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.transacoes.model.Venda;
import java.util.List;

public class CalculoDiferenciado implements CalculoAcao {
    /*TAXAS*/
    @Override
    public double calcularCompraAcao(List<Compra> acoesCompradas) {
       return 0;
    }

    @Override
    public double calcularVendaAcao(List<Venda> acoesVendias) {
        return 0;
    }
    
}
