package trabalho;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;


public class ItemListModel implements ListModel <Item>{
    
    private final List<Item> itens;
    private final List<ListDataListener> dataLinisters;

    public ItemListModel(List<Item> itens) {
        this.itens = itens;
        this.dataLinisters = new ArrayList();
    }

    public int getSize() {
        return itens.size();
    }

    public Item getElementAt(int index) {
        return itens.get(index);
    }

    public void addListDataListener(ListDataListener l) {
        this.dataLinisters.add(l);
    }

    public void removeListDataListener(ListDataListener l) {
        this.dataLinisters.remove(l);
    }
}

