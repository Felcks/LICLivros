package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import principais.*;
import utilidades.*;

public class TelaPedidoUnicoAvulso extends JPanel implements IPrepararComponentes
{
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	public AutoSuggestor autoSuggestor;
	
	private JTable table;
	public static List<Integer> idsDosLivrosAdicionados;
	public static List<Integer> qtdDosLivrosAdicionados;
	
	Pacote pacote;
	private static double precoTotal;
	private static JTextField fieldPreco, fieldDesconto, fieldFinal, fieldDescontoDado;
	
	JTextField fieldNome, fieldBairro, fieldComplemento, fieldRua, fieldTelefone, fieldCel, fieldObs;
	JComboBox pagamentoBox;
	private static JTextField fieldLivro;

	private JTable tableLivros;
	
	public TelaPedidoUnicoAvulso(GUIManager guiManager)
	{
		idsDosLivrosAdicionados = new ArrayList<Integer>();
		qtdDosLivrosAdicionados = new ArrayList<Integer>();
		
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		pacote = new Pacote();
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.weightx = 1;
        c.weighty = 1;
        
       for(int i = 0; i < 50; i ++){
        	for(int j = 0; j < 20; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
       
       int prox = 0;
       
       JLabel labelNome = new JLabel("Nome: ");
       c.gridx = prox;
       c.gridy = 0;
       c.gridwidth =  1;
       prox += 1;
       c.gridheight = 1; 
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelNome, c);
  
       
       fieldNome = new JTextField();
       c.gridx = prox;
       c.gridwidth = 20;
       prox += 20;
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.CENTER;
       this.add(fieldNome, c);
       
       
       JLabel labelBairro = new JLabel("Bairro: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1;
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelBairro, c);
       
       fieldBairro = new JTextField();
       c.gridx = prox;
       c.gridwidth = 8;
       prox += 8;
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.CENTER;
       this.add(fieldBairro, c);
       
       
       JLabel labelRua = new JLabel("Rua: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1;
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelRua, c);
       
       fieldRua = new JTextField();
       c.gridx = prox;
       c.gridwidth = 14;
       prox += 14;
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.CENTER;
       this.add(fieldRua, c);
       
       
       JLabel labelComplemento = new JLabel("Compl: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1;
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelComplemento, c);
       
       fieldComplemento = new JTextField();
       c.gridx = prox;
       c.gridwidth = 4;
       prox += 4;
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.CENTER;
       this.add(fieldComplemento, c);
       
       
       prox = 0;
       c.gridy = 1;
       
       JLabel labelTelefone = new JLabel("Tel: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1;
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelTelefone, c);
       
       fieldTelefone = new JTextField();
       c.gridx = prox;
       c.gridwidth = 9;
       prox += 9;
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.CENTER;
       this.add(fieldTelefone, c);    
       
       JLabel labelCel = new JLabel("Cel: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1;
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelCel, c);
       
       fieldCel = new JTextField();
       c.gridx = prox;
       c.gridwidth = 8;
       prox += 8;
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.CENTER;
       this.add(fieldCel, c);
       
       
       JLabel labelObs = new JLabel("Obs: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1; 
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelObs, c);
       
       fieldObs = new JTextField();
       c.gridx = prox;
       c.gridwidth = 17;
       prox += 17;
       c.fill = GridBagConstraints.HORIZONTAL;
       this.add(fieldObs, c);
       
       JLabel labelPagamento = new JLabel("Pgt: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1;
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelPagamento, c);
       
       FormaDePagamento[] enumerado = FormaDePagamento.values();
       String[] formasPagamento = new String[enumerado.length];
       for(int i = 0; i < formasPagamento.length; i++)
       	formasPagamento[i] = enumerado[i].getNome();
       pagamentoBox = new JComboBox(formasPagamento);
       c.gridx = prox;
       c.gridwidth = 2;
       prox += 2;
       c.fill = GridBagConstraints.HORIZONTAL;
       this.add(pagamentoBox, c);


		JLabel txt_Title = new JLabel("PACOTE", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(30,30);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 10;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
 
		this.table = new JTable(new TableModelPedidoAvulso());
		minimizarTamanhoDaColuna(table, 1, 175, true);
		minimizarTamanhoDaColuna(table, 2, 90, true);
		minimizarTamanhoDaColuna(table, 3, 90, true);
		JScrollPane scrollPane  = new JScrollPane(this.table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 10;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);


		this.inicializarViewLivros(c);
		
		c.gridy = 10;
		prox = 17;
		
		JLabel novosLivros = new JLabel("Novos Livros: ");
		c.gridheight = 1;
		c.gridx = prox;
        c.gridwidth = 2;
	    prox += 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        this.add(novosLivros, c);
	       
	    fieldLivro = new JTextField();
	    c.gridx = prox;
	    c.gridwidth = 14;
	    prox += 14;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.anchor = GridBagConstraints.CENTER;
	    //this.add(fieldLivro, c);
	       
	    Action action = new AbstractAction()
	    {
	         @Override
	         public void actionPerformed(ActionEvent e)
	         {
				adicionarLivroAoPedido(fieldLivro);
	         }
	    };
	       fieldLivro.addActionListener(action);
	       
	     JButton adicionar = new JButton("Adicionar");
	     c.gridx = prox;
	     c.gridwidth = 1;
	     prox += 1;
	     //this.add(adicionar, c);
	       
	     autoSuggestor = new AutoSuggestor(fieldLivro, guiManager.getJanela(), EstoqueManager.getInstance().getTodosLivrosNomes(), 
					Color.white, Color.blue, Color.BLACK, 0.55f);
	     
	     JButton remover = new JButton("Remover");
	     c.gridx = 24;
	     c.gridwidth = 4;
	     c.gridy = 15;
	     prox += 1;
	     c.anchor = GridBagConstraints.CENTER;
	     //this.add(remover, c);
	     
	     remover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				remover();
			}
		});
	       
			
		
		prox = 17;
		c.gridy = 7;
		
		JLabel labelPreco = new JLabel("Preço: ");
		c.fill = GridBagConstraints.NONE;
	    c.anchor = GridBagConstraints.LINE_END;
	    c.gridx = prox;
	    c.gridwidth = 1;
	    prox += 1;
	    this.add(labelPreco, c);
	    
	    fieldPreco = new JTextField();
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridx = prox;
	    c.gridwidth = 4;
	    prox += 4;
	    fieldPreco.setEditable(false);
	    fieldPreco.setBackground(Color.LIGHT_GRAY);
	    this.add(fieldPreco, c);
	    
	    JLabel labelDesconto = new JLabel("Desc(%): ");
	    c.gridx = prox;
	    c.gridwidth = 1;
	    prox += 1;
	    c.fill = GridBagConstraints.NONE;
	    c.anchor = GridBagConstraints.LINE_END;
	    this.add(labelDesconto, c);
	    
	    fieldDesconto = new JTextField();
	    c.gridx = prox;
	    c.gridwidth = 2;
	    prox += 2;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    this.add(fieldDesconto, c);
		
	    JLabel labelDescontoDado = new JLabel("Desc: ");
	    c.gridx = prox;
	    c.gridwidth = 1;
	    prox += 1;
	    c.fill = GridBagConstraints.NONE;
	    c.anchor = GridBagConstraints.LINE_END;
	    this.add(labelDescontoDado, c);
	    
	    fieldDescontoDado = new JTextField();
	    c.gridx = prox;
	    c.gridwidth = 2;
	    prox += 2;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    fieldDescontoDado.setEditable(false);
	    fieldDescontoDado.setBackground(Color.LIGHT_GRAY);
	    this.add(fieldDescontoDado, c);
	    
	    JLabel labelFinal = new JLabel("Final: ");
	    c.gridx = prox;
	    c.gridwidth = 1;
	    prox += 1;
	    c.fill = GridBagConstraints.NONE;
	    c.anchor = GridBagConstraints.LINE_END;
	    this.add(labelFinal, c);
	    
	    fieldFinal = new JTextField();
	    c.gridx = prox;
	    c.gridwidth = 5;
	    prox += 5;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    fieldFinal.setEditable(false);
	    fieldFinal.setBackground(Color.LIGHT_GRAY);
	    this.add(fieldFinal, c);
	    
	    
		JButton concluirButton = new JButton("Confirmar!");
	    c.gridx = 48;
	    c.gridwidth = 2;
	    c.gridheight = 2;
	    prox += 2;
	    c.gridy = 18;
	    c.fill = GridBagConstraints.BOTH;
	    this.add(concluirButton, c);
	    
	    JButton voltarButton = new JButton("Voltar");
	    c.gridx = 0;
	    c.gridwidth = 2;
	    c.gridheight = 2;
	    prox += 2;
	    c.gridy = 18;
	    c.fill = GridBagConstraints.BOTH;
	    //this.add(voltarButton, c);
	    
	    
	    voltarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
	 
	   
	    
	    concluirButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				concluirPedido();
			}
		});
	    
	    fieldDesconto.getDocument().addDocumentListener(new DocumentListener() {	
			@Override
			public void removeUpdate(DocumentEvent e) {
				aplicarDesconto(fieldDesconto.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				aplicarDesconto(fieldDesconto.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				aplicarDesconto(fieldDesconto.getText());
			}
		});
	    
	    adicionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				adicionarLivroAoPedido(fieldLivro);
				
			}
		});
	    
	    fieldDesconto.setText("0");
	    aplicarDesconto(fieldDesconto.getText());
	    this.repintarTabela();
	}

	private JComboBox comboBoxEditora;
	private void inicializarViewLivros(GridBagConstraints c){

		JLabel txt_Title = new JLabel("LIVROS", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(30,30);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 14;
		c.gridy = 2;
		c.gridwidth = 10;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);

		JLabel editoraLabel = new JLabel("EDITORA: ");
		c.gridx = 14;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(editoraLabel, c);

		String[] todasEditoras = new String[EditoraManager.getInstance().getEditoras().size()];
		for(int i = 0; i < EditoraManager.getInstance().getEditoras().size(); i++){
			todasEditoras[i] = EditoraManager.getInstance().getEditoras().get(i).getNome();
		}
		comboBoxEditora = new JComboBox(todasEditoras);
		c.gridx = 15;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBoxEditora,c);


		this.tableLivros = new JTable(new MyTableModelLivro(""));
		minimizarTamanhoDaColuna(tableLivros, 0, 40, true);
		minimizarTamanhoDaColuna(tableLivros, 3, 80, true);
		minimizarTamanhoDaColuna(tableLivros, 2, 175, true);
		JScrollPane scrollPane2  = new JScrollPane(this.tableLivros);
		tableLivros.setFillsViewportHeight(true);
		c.gridx = 14;
		c.gridy = 5;
		c.gridwidth = 10;
		c.gridheight = 10;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane2, c);

		comboBoxEditora.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				atualizarLivros(table, comboBoxEditora);
			}
		});
	}

	private void atualizarLivros(JTable table, JComboBox comboBox){
		try{
			if(comboBoxEditora != null){
				this.repintarTabelaLivro(comboBoxEditora.getSelectedItem().toString());
				//textField.setText(comboBox.getSelectedItem().toString());
			}

		}
		catch(java.lang.NullPointerException e){

		}
	}

	private void repintarTabelaLivro(String editora){
		if(this.tableLivros != null){
			((MyTableModelLivro)this.tableLivros.getModel()).updateData(editora);
			this.tableLivros.repaint();
			//TODO atualizarCampos();
		}
	}


	private void remover()
	{
		int id = -1;
		id = table.getSelectedRow();
		if(id >= 0 && id < idsDosLivrosAdicionados.size())
		{
			idsDosLivrosAdicionados.remove(id);
			qtdDosLivrosAdicionados.remove(id);
			
			this.repintarTabela();
			atualizarPreco(((TableModelPedidoAvulso)table.getModel()).getData());
		}
		else{
			JOptionPane.showMessageDialog(this, "Selecione um livro a ser removido.", "Falha na remoção", JOptionPane.OK_CANCEL_OPTION);
		}
	}
	
	private void concluirPedido(){
		Cliente cliente = criarCliente();
		if(cliente.isValidClienteParaPedido() == false){
			JOptionPane.showMessageDialog(this, "Complete as informações.", "Pedido não terminado", JOptionPane.OK_CANCEL_OPTION);
			return;
		}
		
		Pedido pedido = new Pedido(cliente);
		pedido.setId(PedidoManager.getInstance().getPedidos().size());
		pedido.setPacote(pacote);
		
		int[] idsDosLivrosComprados = new int[TelaPedidoUnicoAvulso.idsDosLivrosAdicionados.size()];
		for(int i = 0; i < idsDosLivrosComprados.length; i++)
			idsDosLivrosComprados[i] = idsDosLivrosAdicionados.get(i);
		pedido.setIdsDosLivrosComprados(idsDosLivrosComprados);
		
		Object[][] data = ((TableModelPedidoAvulso)table.getModel()).getData();
		int[] qtdDosLivrosComprados = new int[idsDosLivrosAdicionados.size()];
		for(int i = 0; i < qtdDosLivrosComprados.length; i++)
			qtdDosLivrosComprados[i] = (int)data[i][2];
		pedido.setQtdDosLivrosComprados(qtdDosLivrosComprados);
		
		int desconto = 0;
		if(fieldDesconto.getText().length() > 0)
			desconto = Integer.parseInt(fieldDesconto.getText());
		pedido.setDesconto(desconto);
		
		double precoFinal = precoTotal;
		String precoEmString = fieldFinal.getText().substring(3, fieldFinal.getText().toString().length());
		precoEmString = precoEmString.replace(',', '.');
		int count = 0;
		for(int i = precoEmString.length() - 1; i > 0; i--){
			if(precoEmString.charAt(i) == '.'){
				if(count == 0)
					count++;
				else{
					String before = precoEmString.substring(0, i);
					precoEmString = precoEmString.substring(i + 1, precoEmString.length());
					precoEmString = before.concat(precoEmString);
				}
			}
		}
		precoFinal = ((double)Double.parseDouble(precoEmString));
		pedido.setPreco(precoFinal);
		
		pedido.setPrecoNormal(precoTotal);
		
		pedido.setObs(fieldObs.getText());
		pedido.setTipoPedido(TipoPedido.AVULSO);
		pedido.setStatus(Status.EM_ANDAMENTO);
		
		FormaDePagamento fp = FormaDePagamento.getFormaDePagamentoPeloNome(pagamentoBox.getSelectedItem().toString());
		pedido.setFormaDePagamento(fp);
		pedido.setData();


		for(int i = 0; i < pedido.getIdsDosLivrosComprados().length; i++){
			Livro livro = EstoqueManager.getInstance().getLivroPeloId(pedido.getIdsDosLivrosComprados()[i]);
			int quantidadeComprada = pedido.getQtdDosLivrosComprados()[i];
			livro.setReservado(livro.getReservado() + quantidadeComprada);

			EstoqueManager.getInstance().atualizarLivro(livro.getId(), livro);
			EstoqueManager.getInstance().getOperacoes().UPDATE_DATA(livro);
		}
		
		
		JOptionPane.showMessageDialog(this, "Pedido realizado com sucesso!", "Pedido concluído!", JOptionPane.INFORMATION_MESSAGE);

		PedidoManager.getInstance().getOperacoes().INSERT_DATA(pedido);
		Pedido pedido2 = PedidoManager.getInstance().getOperacoes().GET_ULTIMO_PEDIDO();
		pedido.setId(pedido2.getId());
		PedidoManager.getInstance().adicionarPedidoEAbrirDoc(pedido);

		idsDosLivrosAdicionados = new ArrayList<Integer>();
		qtdDosLivrosAdicionados = new ArrayList<Integer>();
		guiManager.mudarParaTela("telaPedidoUnicoAvulso");
	}
	
	private Cliente criarCliente()
	{
		Cliente cliente = new Cliente();
		cliente.setNome(fieldNome.getText());
		cliente.setBairro(fieldBairro.getText());
		cliente.setRua(fieldRua.getText());
		cliente.setComplemento(fieldComplemento.getText());
		cliente.setTelefone(fieldTelefone.getText());
		cliente.setCelular(fieldCel.getText());
		
		return cliente;
	}
	
	public static void atualizarPreco(Object[][] data)
	{
		precoTotal = 0;
		for(int i = 0; i < data.length; i++)
		{
			String precoEmString = data[i][3].toString().substring(3, data[i][3].toString().length());
			precoEmString = precoEmString.replace(',', '.');
			double preco = ((double)Double.parseDouble(precoEmString));
			
			//preço x quantidade
			precoTotal += preco * (int)data[i][2];	
		}
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (precoTotal);
		fieldPreco.setText(formatado);
		
		aplicarDesconto(fieldDesconto.getText());
	}
	
	private static void aplicarDesconto(String text){
		Runnable runnable = new Runnable() {
			@Override
			public void run(){
				ServicoDeDigito servicoDeDigito = new ServicoDeDigito();
				int desconto = servicoDeDigito.transformarStringEmInt(text);
				if(desconto >= 0 && desconto < 100){
					double precoAtual = precoTotal;
					double valorDoDesconto = ((precoAtual * desconto) / 100);
					NumberFormat nf = NumberFormat.getCurrencyInstance();  
					String formatado = nf.format (precoAtual - valorDoDesconto);
					String formatado2 = nf.format(valorDoDesconto);
					fieldFinal.setText(formatado);
					fieldDescontoDado.setText(formatado2);
				}
				else{
					NumberFormat nf = NumberFormat.getCurrencyInstance();  
					String formatado = nf.format (00);
					fieldDescontoDado.setText(formatado);
					fieldFinal.setText(fieldPreco.getText());
					fieldDesconto.setText("");

				}
			}
		};
		
		SwingUtilities.invokeLater(runnable);
	}
	
	
	private void repintarTabela(){
		if(this.table != null){
			((TableModelPedidoAvulso)this.table.getModel()).updateData();
			this.table.repaint();
			
			//this.pacote = PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado);
			precoTotal = 0;
			for(int i = 0; i < idsDosLivrosAdicionados.size(); i++){
				precoTotal += EstoqueManager.getInstance().getLivroPeloId(idsDosLivrosAdicionados.get(i)).getPreco();
			}
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();  
			String formatado = nf.format (precoTotal);
			
			fieldPreco.setText(formatado);
			aplicarDesconto(fieldDesconto.getText());
		}
	
	}
	
	public void prepararComponentes()
	{	
		idsDosLivrosAdicionados = new ArrayList<Integer>();
		qtdDosLivrosAdicionados = new ArrayList<Integer>();
		
		this.repintarTabela();
		if(fieldNome != null)
			fieldNome.setText("");
		if(fieldBairro != null)
			fieldBairro.setText("");
		if(fieldComplemento != null)
			fieldComplemento.setText("");
		if(fieldTelefone != null)
			fieldTelefone.setText("");
		if(fieldCel != null)
			fieldCel.setText("");
		if(fieldObs != null)
			fieldObs.setText("");
		if(fieldRua != null)
			fieldRua.setText("");
		if(fieldDesconto != null)
			fieldDesconto.setText("");
		if(fieldLivro != null)
		{
			 autoSuggestor = new AutoSuggestor(fieldLivro, guiManager.getJanela(), EstoqueManager.getInstance().getTodosLivrosNomes(), 
						Color.white, Color.blue, Color.black, 0.55f);
			 fieldLivro.setText("");
		}
	}
	
	private void adicionarLivroAoPedido(JTextField textField){
		String nome = textField.getText();
		if(nome.length() == 0)
			nome.concat(" ");
		
		String nomeSemEspacoFinal = nome.substring(0, nome.length() - 1);
	
		for(int i = 0; i < EstoqueManager.getInstance().getLivros().size(); i++){
			Livro livro = EstoqueManager.getInstance().getLivros().get(i);
			if(livro.getNome().equals(nome) ||	livro.getNome().equals(nomeSemEspacoFinal)){
				if(idsDosLivrosAdicionados.contains(livro.getId()) == false){
					
					idsDosLivrosAdicionados.add(livro.getId());
					qtdDosLivrosAdicionados.add(1);
					
					this.repintarTabela();
					textField.setText("");
					atualizarPreco(((TableModelPedidoAvulso)table.getModel()).getData());
					return;
				}
				else{
					JOptionPane.showMessageDialog(this, "Esse livro já foi adicionado!","Livro repetido!", JOptionPane.OK_CANCEL_OPTION);
				}
			}
		}
	}
	
	private void minimizarTamanhoDaColuna(JTable table, int index, int tam, Boolean goLeft)
	{
		table.getColumnModel().getColumn(index).setMinWidth(tam);
		table.getColumnModel().getColumn(index).setPreferredWidth(tam);
		table.getColumnModel().getColumn(index).setMaxWidth(tam);
		if(goLeft){
			DefaultTableCellRenderer left = new DefaultTableCellRenderer();
			left.setHorizontalAlignment(SwingConstants.LEFT);
			table.getColumnModel().getColumn(index).setCellRenderer(left);
		}
	}
}
        