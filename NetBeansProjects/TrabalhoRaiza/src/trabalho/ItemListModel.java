package trabalho;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class ItemListModel implements ListModel<Item>, ComboBoxModel<Item> {

    private final List<Item> itens;
    private final List<ListDataListener> dataLinisters;
    private Item selectedItem;

    public ItemListModel() {
        this.itens = new ArrayList();
        // Arrays.asList(new Item[]{new Item("refrigerante", 2.00), new Item("salgado", 3.00), new Item("suco", 2.00), new Item("sanduíche", 5.00), new Item("bala", 0.10), new Item("brigadeiro", 1.00)});
        this.dataLinisters = new ArrayList();
    }
    
     public ItemListModel(List<Item>itens) {
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

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem instanceof Item) {
            this.selectedItem = (Item) anItem;
        }
    }

    @Override
    public Item getSelectedItem() {
        return selectedItem;
    }
}
