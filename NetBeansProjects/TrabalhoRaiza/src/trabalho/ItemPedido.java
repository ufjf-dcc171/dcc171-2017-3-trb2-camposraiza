package trabalho;

public class ItemPedido {

    public ItemPedido(){}
    
    public ItemPedido(Item item, Pedido pedido, int quantidade) {
        this.item = item;
        this.pedido = pedido;
        this.quantidade = quantidade;
    }

    private Item item; 
    private Pedido pedido; 

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    private int quantidade;



    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemPedido{" + "item=" + item + ", pedido=" + pedido + ", quantidade=" + quantidade + '}';
    }

 }
