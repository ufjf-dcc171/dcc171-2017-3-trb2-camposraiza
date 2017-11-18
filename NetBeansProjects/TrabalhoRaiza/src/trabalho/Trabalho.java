/*hora de sistema entrada OK termino - 1 - atualizar//mostrar; 
2 - adicionar e remover item; 
janela texto pedidos; 
Hora término (botão editar está fechando e editando)! OK
3 - gravar automático
4 - ler na janela ?
*/

package trabalho;

import javax.swing.JFrame;
import java.io.IOException;

/**
 *
 * @author Raiza Silva Campos
 */
public class Trabalho {

    public static void main(String[] args)throws IOException  {
        
       
        
        JanelaPedido janela = new JanelaPedido();
        janela.setSize(500, 500);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
        
        
    }
}
