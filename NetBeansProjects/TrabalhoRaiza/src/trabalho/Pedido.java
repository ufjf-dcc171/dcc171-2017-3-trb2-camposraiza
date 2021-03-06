package trabalho;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class Pedido {

    private int mesa;    
    private Long data;
    private Date termino;
    private boolean status;
    private List<ItemPedido> itens;
   // private StringBuilder descricao = new StringBuilder();

    public Pedido() {

    }

    public Pedido(int mesa, Long data) {
        this.mesa = mesa;
        this.data = data;
        this.status = true;
        this.termino = null;
        this.itens = new ArrayList<>();
      //  this.descricao.append("Descrição do Pedido");
    }

        public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void adicionaItem(Item item, int quantidade) {
        itens.add(new ItemPedido(item, this, quantidade));       
    }
    
    public void removeItem(Item item) {
        for (ItemPedido i : itens) {
            if (i.getItem() == item) {
                itens.remove(i);
                return;
            }
        }
    }

    @Override
    public String toString() {
        String statusConvertido; //para aparecer o texto e não 'true' ou 'false'
        if (status) {
            statusConvertido = "Aberto";
        } else {
            statusConvertido = "Fechado";
        } 
               
      
        String detalhePedido = "Pedido{" + "mesa= " + mesa + ", status= " + statusConvertido + ", abertura= " + new Date(data);
        
        if (termino != null) {
            detalhePedido += ", término= " + termino.toString();
        }
        
        for (ItemPedido p : this.itens) {
            detalhePedido += System.lineSeparator() + "   - " + p.getItem().getNome() + "(" + p.getQuantidade() + ")";
        }
        return detalhePedido + "}";
    }
 
 

}
