package trabalho;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 //   private JComboBox<Pedido> jComboBox = new JComboBox();
    



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
       
       /* JPanel pComboBox = new JPanel(new GridLayout(14, 2));
        pComboBox.add(jComboBox);
        add(pComboBox, BorderLayout.SOUTH);*/
       
        
        add(new JScrollPane(lstPedidos), BorderLayout.EAST);    
                 
      LerArquivo();
        
        
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
                lstPedidos.updateUI();
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
                        
                       /* SimpleDateFormat s = new SimpleDateFormat("HH:mm");
                        Date termino = new Date();
                        s.format(termino);
                        s.setLenient(false);*/
                        
                        p.setTermino((new Date())); 
                        
                        txTermino.setText(txDataFim.toString());
                    
                        
                        JOptionPane.showMessageDialog(null, "Pedido encerrado com sucesso!");
                        
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
    
    public void LerArquivo(){ 
            
        Path caminho = Paths.get("/home/raiza/Transferências/file.txt");
        try{
            byte[] texto = Files.readAllBytes(caminho);
            String leitura = new String(texto);
            JOptionPane.showMessageDialog(null, leitura);
    }
        catch(Exception erro){
        
        }
    }
    
/*    public List<Pedido> retornarPedido() throws Exception{
        String linha = "";
        
       String mesa = "";
       String status = "";
       String abertura = "";
       String fechamento = "";
       String itens = "";
        
        Pedido pedido = null;
        String [] linhaArquivo;
        List<Pedido> listaPedido = new ArrayList<>();
        
        try{
            File Arquivo = new File ("/home/raiza/Transferências/file.txt");
            FileInputStream FI = new FileInputStream(Arquivo);
            Scanner SC = new Scanner(FI);
            
            while(SC.hasNext()){
            
            pedido = new Pedido();
            
            
            
            linha = SC.nextLine();
            linhaArquivo = linha.split(",");
            
            
            
            mesa = linhaArquivo[0];
            status = linhaArquivo[1];
            abertura = linhaArquivo[2];
            fechamento = linhaArquivo[3];
            
            
           // itens = linhaArquivo[4];
            
            pedido.setMesa(Integer.parseInt(mesa));
            pedido.setStatus(Boolean.parseBoolean(status));
            pedido.setData(Long.parseLong(abertura));
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");            
            Calendar c = Calendar.getInstance();            
            c.setTime(dateFormat.parse(fechamento));            
            Date dataFechamento = c.getTime();            
            pedido.setTermino(dataFechamento);
            
           // pedido.setItens(itens);
            
            listaPedido.add(pedido);
            
            
            }
            return listaPedido;
        }
        catch (Exception ex){
            throw new Exception (ex);
            
        }
        
    }
    
    public void inicializarTela() throws Exception{
        try{
            for (Pedido ped:retornarPedido()){
               jComboBox.addItem(ped);
            }
        }
        catch(Exception ex){
            throw new Exception (ex);
        }
    }
    */
    
   /*  public void arquivoPedidos() {
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
            }*/
}

