package trabalho;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JanelaItem extends JFrame {

    private final List<Item> itens = new ArrayList<Item>();
    //private JComboBox<Item> jComboBox = new JComboBox();
    private final JButton btAdicionaItem = new JButton("OK");
    private final JButton btExcluirItem = new JButton("Excluir Item");
    private final JButton btCancelar = new JButton("Voltar");
    private final JLabel lbQuantidade = new JLabel("Quantidade");
    private JTextField txQuantidade = new JTextField("");
    private Pedido selectedPedido;
    private JList<Pedido> lstPedidos; 
    private final JList<Item> lstItens = new JList<Item>(new DefaultListModel<>());
    
    private final JLabel lbItem = new JLabel("Digite item");
    private JTextField txItem = new JTextField("");

    
    
    public JanelaItem() {
        
        lstItens.setModel(new ItemListModel(itens));
        lstItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JPanel botoes = new JPanel(new GridLayout(4, 2));
        JPanel edits = new JPanel(new GridLayout(14, 2));
        JPanel pComboBox = new JPanel(new GridLayout(14, 2));
        //pComboBox.add(jComboBox);
        add(pComboBox, BorderLayout.CENTER);

        this.setTitle("Inserir/Remover Item");
       

        add(botoes, BorderLayout.SOUTH);
        botoes.add(btAdicionaItem);
        botoes.add(btExcluirItem);
        botoes.add(btCancelar);
        // jComboBox.setModel(new ItemListModel());
        pComboBox.add(lbItem);
        pComboBox.add(txItem);
        pComboBox.add(lbQuantidade);
        pComboBox.add(txQuantidade);
        
        lstItens.setModel(new ItemListModel(itens));
        lstItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(lstItens), BorderLayout.EAST); 
        
        JanelaItem ji = this;
        btCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ji.setVisible(false);
            }
        });
        
        
        btAdicionaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //selectedPedido.adicionaItem((Item)jComboBox.getSelectedItem(), Integer.parseInt(txQuantidade.getText()));
                Item i;
                i = new Item(txItem.getText());
               // String item = txItem.getText();
               itens.add(i);
               lstPedidos.getSelectedValue().adicionaItem(i, Integer.parseInt(txQuantidade.getText()));
               lstPedidos.updateUI();
               lstItens.updateUI();                
              //  ji.setVisible(true);
                
            }
        });
        
        
        
        btExcluirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lstItens.isSelectionEmpty()) {
                    return;
                }                
                lstPedidos.getSelectedValue().removeItem(lstItens.getSelectedValue());
                itens.remove(lstItens.getSelectedValue());
                lstPedidos.updateUI();
                lstItens.updateUI();
                                
            }
        });
        
         lstItens.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Item selecionada = lstItens.getSelectedValue();
                if (selecionada != null) {
                    txItem.setText(selecionada.getNome());                
                                       
                } else {
                    
                    lstItens.setModel(new DefaultListModel<>());
                   
                }                
            }
        });  
        
    }
    
    public void setListPedidos (JList<Pedido> lstPedidos) {
        this.lstPedidos = lstPedidos;
    }

    public Pedido getSelectedPedido() {
        return selectedPedido;
    }

    public void setSelectedPedido(Pedido selectedPedido) {
        this.selectedPedido = selectedPedido;
    }
    
     
    
}