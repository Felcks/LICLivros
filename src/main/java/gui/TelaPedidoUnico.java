package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

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

import bd.OperacoesLivrosPacotes;
import principais.AnoEscolar;
import principais.Cliente;
import principais.Escola;
import principais.EscolaManager;
import principais.EstoqueManager;
import principais.Livro;
import principais.Pacote;
import principais.PacoteManager;
import principais.Pedido;
import principais.PedidoManager;
import principais.TipoPedido;
import utilidades.AutoSuggestor;
import utilidades.FormaDePagamento;
import utilidades.ServicoDeDigito;
import utilidades.Status;

public class TelaPedidoUnico extends JPanel implements IPrepararComponentes
{
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	
	public Escola escolaSelecionada;
	public AnoEscolar anoEscolarSelecionado;
	private JTable table;
	
	Pacote pacote;
	private static double precoTotal;
	private static JTextField fieldPreco, fieldDesconto, fieldFinal, fieldDescontoDado;
	
	public JTextField fieldNome, fieldBairro, fieldComplemento, fieldRua, fieldTelefone, fieldCel, fieldObs;
	JComboBox pagamentoBox;

	JComboBox escolaBox;
	
	public TelaPedidoUnico(GUIManager guiManager)
	{
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
       
       JLabel labelEscola = new JLabel("Escola: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1;
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelEscola, c);

       String[] todasEscolas = EscolaManager.getInstance().getTodosNomesEscolas();
       escolaBox = new JComboBox(todasEscolas);
       c.gridx = prox;
       c.gridwidth = 5;
       prox += 5;
       c.fill = GridBagConstraints.HORIZONTAL;
       c.anchor = GridBagConstraints.CENTER;
       this.add(escolaBox, c);
       
       JLabel labelAno = new JLabel("Ano: ");
       c.gridx = prox;
       c.gridwidth = 1;
       prox += 1;
       c.fill = GridBagConstraints.NONE;
       c.anchor = GridBagConstraints.LINE_END;
       this.add(labelAno, c);

       String[] todosAnos = AnoEscolar.getTodosNomesAnosEscolares();
       JComboBox anoBox = new JComboBox(todosAnos);
       c.gridx = prox;
       c.gridwidth = 2;
       prox += 2;
       c.fill = GridBagConstraints.HORIZONTAL;
       this.add(anoBox, c);
       
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
       
       
       this.escolaSelecionada = EscolaManager.getInstance().getEscolaPeloNome(escolaBox.getSelectedItem().toString());
       this.anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(anoBox.getSelectedItem().toString());
		
		this.table = new JTable(new MyTableModelPedidoPacote(escolaSelecionada, anoEscolarSelecionado));
		minimizarTamanhoDaColuna(table, 3, 80, true);
		minimizarTamanhoDaColuna(table, 2, 80, true);
		minimizarTamanhoDaColuna(table, 1, 200, true);
		JScrollPane scrollPane  = new JScrollPane(this.table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 50;
		c.gridheight = 5;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);
		
		
		prox = 17;
		c.gridy = 9;
		
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
	    c.gridwidth = 1;
	    prox += 1;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    this.add(fieldDesconto, c);
		
	    JLabel labelDescontoDado = new JLabel("Desc: ");
	    c.gridx = prox;
	    c.gridwidth = 2;
	    prox += 2;
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
	    
	    escolaBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(escolaBox != null)
					if(escolaBox.getItemCount() > 0)
						escolaSelecionada = EscolaManager.getInstance().getEscolaPeloNome(escolaBox.getSelectedItem().toString());
				
				repintarTabela();
			}
		});
	    
	    anoBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(anoBox.getSelectedItem().toString());
				repintarTabela();
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
	    
	    fieldDesconto.setText("0");
	    aplicarDesconto(fieldDesconto.getText());
	    this.repintarTabela();
	}
	
	private void concluirPedido(){
		Cliente cliente = criarCliente();
		if(cliente.isValidClienteParaPedido() == false){
			JOptionPane.showMessageDialog(this, "Complete as informações.", "Pedido não terminado", JOptionPane.OK_CANCEL_OPTION);
			return;
		}

		Pacote pacote = PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado);

		OperacoesLivrosPacotes operacoesLivrosPacotes = new OperacoesLivrosPacotes();
		ArrayList<Livro> livros = (ArrayList<Livro>)operacoesLivrosPacotes.GET_LIVROS_DE_PACOTE(pacote.getId());
		pacote.setLivros(livros);
		
		Pedido pedido = new Pedido(cliente);
		pedido.setId(PedidoManager.getInstance().getPedidos().size());
		pedido.setPacote(pacote);
		
		int[] idsDosLivrosComprados = new int[livros.size()];
		for(int i = 0; i < idsDosLivrosComprados.length; i++)
			idsDosLivrosComprados[i] = livros.get(i).getId();
		pedido.setIdsDosLivrosComprados(idsDosLivrosComprados);
		
		Object[][] data = ((MyTableModelPedidoPacote)table.getModel()).getData();
		int[] qtdDosLivrosComprados = new int[pacote.getLivros().size()];
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
		pedido.setTipoPedido(TipoPedido.NORMAL);
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

		guiManager.mudarParaTela("telaPedidoUnico");
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
			((MyTableModelPedidoPacote)this.table.getModel()).updateData(escolaSelecionada, anoEscolarSelecionado);
			this.table.repaint();

			Pacote pacote = PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado);

			OperacoesLivrosPacotes operacoesLivrosPacotes = new OperacoesLivrosPacotes();
			ArrayList<Livro> livros = (ArrayList<Livro>)operacoesLivrosPacotes.GET_LIVROS_DE_PACOTE(pacote.getId());


			Object[][] data = ((MyTableModelPedidoPacote)table.getModel()).getData();
			precoTotal = 0;
			int i = 0;
			for(Livro livro : livros){
				precoTotal += livro.getPreco() * (int)data[i][2];
				i++;
			}
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			String formatado = nf.format (precoTotal);

			fieldPreco.setText(formatado);
			aplicarDesconto(fieldDesconto.getText());

			/*this.pacote = PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado);
			precoTotal = 0;
			for(int i = 0; i < pacote.getLivros().size(); i++){
				precoTotal += pacote.getLivros().get(i).getPreco();
			}
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();  
			String formatado = nf.format (precoTotal);
			
			fieldPreco.setText(formatado);
			aplicarDesconto(fieldDesconto.getText());*/
		}
	
	}
	
	public void prepararComponentes()
	{

		escolaBox.removeAllItems();
		String[] todasEscolas = new String[EscolaManager.getInstance().getEscolas().size()];
		for(int i = 0; i < EscolaManager.getInstance().getEscolas().size(); i++){
			todasEscolas[i] = EscolaManager.getInstance().getEscolas().get(i).getNome();
			escolaBox.addItem(todasEscolas[i]);
		}

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
        