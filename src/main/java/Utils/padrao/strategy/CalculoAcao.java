
package Utils.padrao.strategy;

import br.com.javaweb.transacoes.model.Compra;
import br.com.javaweb.transacoes.model.Venda;
import java.util.List;

public interface CalculoAcao {
    
    public double calcularCompraAcao(List<Compra> acoesCompradas);
    public double calcularVendaAcao(List<Venda> acoesVendidas);
}
