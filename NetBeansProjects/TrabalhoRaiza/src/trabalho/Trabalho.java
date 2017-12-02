/*hora de sistema OK; 
2 - adicionar e remover item OK; 
3 - janela texto pedidos; ler na janela ?
Hora término (botão editar está fechando e editando)! OK
gravar automático OK
*/

package trabalho;

import javax.swing.JFrame;
import java.io.IOException;

/**
 *
 * @author Raiza Silva Campos
 */
public class Trabalho {

    public static void main(String[] args)throws IOException, Exception  {
        
       
        
        JanelaPedido janela = new JanelaPedido();
        janela.setSize(500, 500);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
        
        
    }
}
