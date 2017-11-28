package trabalho;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class JanelaPedido extends JFrame {

    private final List<Pedido> pedidos = new ArrayList<Pedido>();
    private final JButton btCriaPedido = new JButton("Criar Pedido");
    private final JButton btAdicionaItem = new JButton("Inserir/Remover Item");
    private final JButton btEncerraPedido = new JButton("Encerrar Pedido");    
    private final JButton btExcluiPedido = new JButton("Excluir Pedido");
    private final JLabel lbMesa = new JLabel("Número da mesa");
    private JTextField txMesa = new JTextField("");
    private JTextField txStatus = new JTextField("Status: Aberto");
    private JTextField txAbertura = new JTextField("--:--");
    private Date txData = new Date();
    private final JLabel lbData = new JLabel("Horário de abertura");
    private JTextField txTermino = new JTextField("--:--");
    private final JLabel lbTermino = new JLabel("Horário de fechamento");
    private Date txDataFim = new Date();
    private final JList<Pedido> lstPedidos = new JList<Pedido>(new DefaultListModel<>());


    private JanelaItem janela = new JanelaItem();

    public JanelaPedido() {

        lstPedidos.setModel(new PedidoListModel(pedidos));
        lstPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel botoes = new JPanel(new GridLayout(4, 2));
        JPanel edits = new JPanel(new GridLayout(14, 2));

        this.setTitle("Controle de pedidos");
        add(edits, BorderLayout.CENTER);
        edits.add(lbMesa);
        edits.add(txMesa);
        edits.add(txStatus);
        txStatus.setEnabled(false);
        edits.add(lbData);
        edits.add(txAbertura);
        txAbertura.setEnabled(false);
        edits.add(lbTermino);
        edits.add(txTermino);
        txTermino.setEnabled(false);
        add(botoes, BorderLayout.SOUTH);
        botoes.add(btCriaPedido);
        botoes.add(btAdicionaItem);
        botoes.add(btEncerraPedido);
        botoes.add(btExcluiPedido);        
       
        add(new JScrollPane(lstPedidos), BorderLayout.EAST);    
                 
              
        btCriaPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int mesa = Integer.parseInt(txMesa.getText());
                    SimpleDateFormat s = new SimpleDateFormat("HH:mm");
                    Date data = new Date();
                    Date termino = new Date();
                    s.format(data);
                    s.format(termino);
                    s.setLenient(false);
                    /*if (txTermino.getText().isEmpty()) {
                        termino = null; // Fechamento vazio;

                    }*/

                    Pedido p = new Pedido(mesa, txData.getTime());

                    txAbertura.setText(txData.toString());
                    pedidos.add(p);

                    txMesa.setText(""); //retorna as mensagens para default
                    txStatus.setText("");
                    txStatus.setEnabled(false); //deixa o status bloquado enquanto o pedido está aberto
                    txAbertura.setText("");
                    txTermino.setText("");
                    lstPedidos.updateUI();                    
                    salvaEvento();
                } catch (NumberFormatException ex) { //verifica formatação dos dados
                    JOptionPane.showMessageDialog(null, "Não foi possível criar o pedido. Favor verificar se todos os campos foram corretamente preechidos.");
                }
            }
        });

        btAdicionaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lstPedidos.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "É necessário selecionar um pedido para incluir itens");
                    return;
                } else if (lstPedidos.getSelectedValue().getStatus() == false) {
                    JOptionPane.showMessageDialog(null, "Não é possível acrescentar itens, pois o pedido está fechado");
                    return;
                }
                Pedido p = lstPedidos.getSelectedValue();
                janela.setSelectedPedido(p);
                janela.setListPedidos(lstPedidos);
                janela.setSize(500, 500);
                janela.setLocationRelativeTo(null);
                janela.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //faz o X só esconder
                janela.setVisible(true);
                salvaEvento();
            }
        });

        btExcluiPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lstPedidos.isSelectionEmpty()) {
                    return;
                }
                pedidos.remove(lstPedidos.getSelectedValue());
                lstPedidos.updateUI();
                salvaEvento();
            }
        });

        btEncerraPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lstPedidos.isSelectionEmpty()) {
                    return;
                }
                Pedido p = pedidos.get(lstPedidos.getSelectedIndex());
                if (p.getStatus()) { //verifica se o status está Aberto                
                    try {
                        p.setTermino(txDataFim.getTime());
                        JOptionPane.showMessageDialog(null, "Pedido encerrado com sucesso!");
                        arquivoPedidos();
                    } catch (Exception ex) {
                        Logger.getLogger(JanelaPedido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (p.getTermino() != null) {
                        p.setStatus(false);
                    }

                    txMesa.setText(""); //retorna mensagens para default
                    txStatus.setText("Status: Aberto");
                    txStatus.setEnabled(false);
                    txAbertura.setEnabled(false);
                    txTermino.setEnabled(false);

                    lstPedidos.updateUI();

                } else {
                    txMesa.setText(""); //se o pedido estiver fechado, mostra mensagem de erro
                    txStatus.setText("");
                    txStatus.setEnabled(false);
                    txAbertura.setText("");
                    txTermino.setText("");
                    JOptionPane.showMessageDialog(null, "O pedido está fechado, não pode ser alterado");
                }
                salvaEvento();
            }
        });

        /*btEditaPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lstPedidos.isSelectionEmpty()) {
                    return;
                }
                Pedido p = pedidos.get(lstPedidos.getSelectedIndex());
                if (p.getStatus()) { //verifica se o status está Aberto
                    p.setMesa(Integer.parseInt(txMesa.getText()));
                    Date data = new Date();
                    Date termino = new Date();
                    SimpleDateFormat s = new SimpleDateFormat("HH:mm");
                    s.format(data);
                    s.format(termino);
                    s.setLenient(false);
                    try {
                        p.setData(s.parse(txData.getText()));                        
                        p.setTermino(s.parse(txTermino.getText()));
                    } catch (ParseException ex) {
                        Logger.getLogger(JanelaPedido.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (p.getTermino() != null) {
                        p.setStatus(false);
                    }
                    txMesa.setText(""); //retorna mensagens para default
                    txStatus.setText("Status: Aberto");
                    txStatus.setEnabled(false);
                    txAbertura.setText("");
                    txTermino.setText("");

                    lstPedidos.updateUI();
                } else {
                    txMesa.setText(""); //se o pedido estiver fechado, mostra mensagem de erro
                    txStatus.setText("");
                    txStatus.setEnabled(false);
                    txAbertura.setText("");
                    txTermino.setText("");
                    JOptionPane.showMessageDialog(null, "O pedido está fechado, não pode ser editado");
                }
            }
        });*/
        lstPedidos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Pedido selecionada = lstPedidos.getSelectedValue();
                if (selecionada != null) {
                    txMesa.setText(Integer.toString(selecionada.getMesa()));
                    if (selecionada.getStatus()) { //deixa o status editável quando o pedido é clicado
                        txStatus.setText("Status: Aberto");
                        txStatus.setEnabled(false);

                    } else {
                        txStatus.setText("Status: Fechado");
                        txStatus.setEnabled(false);
                    }
                    SimpleDateFormat s = new SimpleDateFormat("HH:mm");

                    s.setLenient(false);
                    txAbertura.setText(s.format(selecionada.getData()));
                    if (selecionada.getTermino() != null) {
                        txTermino.setText(s.format(selecionada.getTermino()));
                    }
                } else {
                    
                    lstPedidos.setModel(new DefaultListModel<>());
                   
                }
                salvaEvento();
            }
        });
        
         
    }
    public void salvaEvento() {
                FileWriter arquivo = null;
                try {
                    arquivo = new FileWriter("/home/raiza/Transferências/file.txt", true);
                    PrintWriter gravarArquivo = new PrintWriter(arquivo);
                    for (Pedido p : pedidos) {
                        gravarArquivo.println(p);
                    }
                    arquivo.flush(); //libera a gravaçao
                    arquivo.close(); //fecha o arquivo
                } catch (IOException ex) {
                    Logger.getLogger(JanelaPedido.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        arquivo.close();
                    } catch (IOException ex) {
                        Logger.getLogger(JanelaPedido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    
     public void arquivoPedidos() {
                FileReader arq = null;
                try {
                    String linha = "a";
                    arq = new FileReader("/home/raiza/Transferências/file.txt");
                    //armazenando conteudo no arquivo no buffer
                    BufferedReader lerArq = new BufferedReader(arq);
                    
                    //a variavel linha recebe o valor 'null' quando chegar no final do arquivo
                    while (linha != null) {
                        System.out.printf("%s\n", linha);
                        //lendo a segundo até a última
                        linha = lerArq.readLine();                        
                    }   arq.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JanelaPedido.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(JanelaPedido.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        arq.close();
                    } catch (IOException ex) {
                        Logger.getLogger(JanelaPedido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
}

