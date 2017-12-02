package trabalho;

public class Item {

    @Override
    public String toString() {
        return "Item{" + "nome=" + nome + "}";
    }
    
    

    private String nome;

    public Item(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
