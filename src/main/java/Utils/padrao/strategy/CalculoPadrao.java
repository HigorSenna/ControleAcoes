
package Utils.padrao.strategy;

import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.transacoes.model.Venda;
import java.util.List;

public class CalculoPadrao implements CalculoAcao{
    
    @Override
    public double calcularCompraAcao(List<Compra> acoesCompradas) {
        return 0;
    }

    @Override
    public double calcularVendaAcao(List<Venda> acoesVendidas) {
        return 0;
    }       
}
