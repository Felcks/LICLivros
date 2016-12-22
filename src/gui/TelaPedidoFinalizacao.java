package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import bd.OperacoesPedidos;
import principais.EstoqueManager;
import principais.Livro;
import principais.Pedido;
import principais.PedidoManager;
import principais.TipoPedido;
import utilidades.Screen;
import utilidades.ServicoDeDigito;
import utilidades.Status;
import utilidades.StatusDaEntrega;
import utilidades.StatusDoPagamento;
import utilidades.FormaDePagamento;
import utilidades.Acao;
import utilidades.FormaDeEntrega;

public class TelaPedidoFinalizacao extends JPanel implements IPrepararComponentes{
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	private JTable table;
	private JLabel nomeCliente;
	JTextField textField_desconto;
	JTextField textField_cliente;
	JTextField textField_preco;
	JTextField textField_comDesconto;
	JTextField textField_valorDesconto;
	private static double novoPreco;
	
	public TelaPedidoFinalizacao(GUIManager guiManager){
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 24; i ++){
        	for(int j = 0; j < 24; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        JLabel txt_Title = new JLabel("Pedido - Passo 3", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 4;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
        
        JLabel label_pagamento = new JLabel("Forma de Pagamento: ", SwingConstants.CENTER);
        label_pagamento.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 10;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        this.add(label_pagamento, c);
        
        JLabel label_entrega = new JLabel("Forma de Entrega: ", SwingConstants.CENTER);
        label_entrega.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.gridy = 6;
        this.add(label_entrega, c);
        
        JLabel label_Desconto = new JLabel("Desconto(%): ", SwingConstants.CENTER);
        label_Desconto.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.gridy = 7;
        this.add(label_Desconto, c);
        
        JLabel label_Cliente = new JLabel("Cliente: ", SwingConstants.CENTER);
        label_Cliente.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.gridy = 12;
        this.add(label_Cliente, c);
        
        JLabel label_preco = new JLabel("Preço: ", SwingConstants.CENTER);
        label_preco.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.gridy = 13;
        this.add(label_preco, c);
        
        JLabel label_comDisconto = new JLabel("Preço com Desconto: ", SwingConstants.CENTER);
        label_comDisconto.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.gridy = 15;
        this.add(label_comDisconto, c);
        
        JLabel label_valorDesconto = new JLabel("Desconto: ", SwingConstants.CENTER);
        label_valorDesconto.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.gridy = 14;
        this.add(label_valorDesconto, c);
    
        
        
        FormaDePagamento[] enumerado = FormaDePagamento.values();
        String[] formasPagamento = new String[enumerado.length];
        for(int i = 0; i < formasPagamento.length; i++)
        	formasPagamento[i] = enumerado[i].getNome();
        
        JComboBox comboBox_pagamento = new JComboBox(formasPagamento);
        //comboBox_pagamento.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.gridx = 11;
        c.gridy = 5;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;
        this.add(comboBox_pagamento, c);
        
        FormaDeEntrega[] enumerado2 = FormaDeEntrega.values();
        String[] formasEntrega = new String[enumerado2.length];
        for(int i = 0; i < formasEntrega.length; i++)
        	formasEntrega[i] = enumerado2[i].getNome();
        
        JComboBox comboBox_entrega = new JComboBox(formasEntrega);
        c.gridy = 6;
        this.add(comboBox_entrega, c);
        
        textField_desconto = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 7;
        c.gridwidth = 1;
        this.add(textField_desconto, c);
        
        
        textField_cliente = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 12;
        c.gridwidth = 3;
        textField_cliente.setEditable(false);
        textField_cliente.setBackground(Color.LIGHT_GRAY);
        this.add(textField_cliente, c);
        
        textField_preco = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 13;
        c.gridwidth = 1;
        textField_preco.setEditable(false);
        textField_preco.setBackground(Color.LIGHT_GRAY);
        this.add(textField_preco, c);
        
        textField_comDesconto = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 15;
        c.gridwidth = 1;
        textField_comDesconto.setEditable(false);
        textField_comDesconto.setBackground(Color.LIGHT_GRAY);
        this.add(textField_comDesconto, c);
        
        textField_valorDesconto = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 14;
        c.gridwidth = 1;
        textField_valorDesconto.setEditable(false);
        textField_valorDesconto.setBackground(Color.LIGHT_GRAY);
        this.add(textField_valorDesconto, c);
        
        JButton btn_Avancar = new JButton("Concluir!");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 23;
		c.gridy = 23;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Avancar, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 23;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
		
	
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaPedidoPacote");
			}
		});
		
		btn_Avancar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				atualizarPedido(comboBox_entrega, comboBox_pagamento, textField_desconto);
			
			}
		});
		
		textField_desconto.getDocument().addDocumentListener(new DocumentListener() {	
			@Override
			public void removeUpdate(DocumentEvent e) {
				aplicarDesconto(textField_desconto.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				aplicarDesconto(textField_desconto.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				aplicarDesconto(textField_desconto.getText());
			}
		});
	

		this.guiManager.getCards().add(this);
	}
	
	private void aplicarDesconto(String text){
		Runnable runnable = new Runnable() {
			@Override
			public void run(){
				int desconto = servicoDeDigito.transformarStringEmInt(text);
				System.out.println(desconto);
				if(desconto > 0 && desconto <= 100){
					double precoAtual = Pedido.pedidoAtual.getPreco();
					double valorDoDesconto = ((precoAtual * desconto) / 100);
					NumberFormat nf = NumberFormat.getCurrencyInstance();  
					String formatado = nf.format (precoAtual - valorDoDesconto);
					String formatado2 = nf.format(valorDoDesconto);
					textField_comDesconto.setText(formatado);
					textField_valorDesconto.setText(formatado2);
					novoPreco = precoAtual - valorDoDesconto;
					Pedido.pedidoAtual.setDesconto(desconto);
				}
				else{
					NumberFormat nf = NumberFormat.getCurrencyInstance();  
					String formatado = nf.format (00);
					textField_comDesconto.setText(formatado);
					textField_valorDesconto.setText(formatado);
					novoPreco = Pedido.pedidoAtual.getPreco();
					Pedido.pedidoAtual.setDesconto(0);
				}
			}
		};
		
		SwingUtilities.invokeLater(runnable);
	}
	
	private void atualizarPedido(JComboBox comboBox_pagamento, JComboBox comboBox_entrega, JTextField textField_obs){
		String livrosNomes = "";
		if(Pedido.pedidoAtual.getTipoPedido() == TipoPedido.NORMAL){
			for(int i = 0; i < Pedido.pedidoAtual.getPacote().getLivros().size(); i++){
				if(Pedido.pedidoAtual.getIdsDosLivrosComprados()[i] >= 0){
					livrosNomes += Pedido.pedidoAtual.getPacote().getLivros().get(i).getNome() + ", ";
					EstoqueManager.getInstance().retirarDoEstoque(Pedido.pedidoAtual.getIdsDosLivrosComprados()[i]);
				}
			}
		}
		else{
			for(int i = 0; i < Pedido.pedidoAtual.getIdsDosLivrosComprados().length; i++){
				int id = Pedido.pedidoAtual.getIdsDosLivrosComprados()[i];
				if(id >= 0){
					Livro livro = EstoqueManager.getInstance().getLivroPeloId(id);
					livrosNomes += livro.getNome() + ", ";
					EstoqueManager.getInstance().retirarDoEstoque(id);
				}
			}
		}
		
		Pedido.pedidoAtual.setFormaDePagamento(FormaDePagamento.getFormaDePagamentoPeloNome(comboBox_entrega.getSelectedItem().toString()));
		Pedido.pedidoAtual.setFormaDeEntrega(FormaDeEntrega.getFormaDeEntregaPeloNome(comboBox_pagamento.getSelectedItem().toString()));
		Pedido.pedidoAtual.setTipoPedido(Pedido.tipoProximoPedido);
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (novoPreco);
		JOptionPane.showMessageDialog(this, "Cliente: " + Pedido.pedidoAtual.getCliente().getNome() + 
				"\nPreço: " + formatado +
				"\nFormaDePagamento: " + Pedido.pedidoAtual.getFormaDePagamento().getNome() +
				"\nFormaDeEntrega: " + Pedido.pedidoAtual.getFormaDeEntrega().getNome() +
				"\nLivros: " + livrosNomes, "Finalização", JOptionPane.INFORMATION_MESSAGE);
		
		Pedido.pedidoAtual.setId(PedidoManager.getInstance().gerarId());
		Pedido.pedidoAtual.setStatus(Status.EM_ANDAMENTO);
		Pedido.pedidoAtual.setStatusDaEntrega(StatusDaEntrega.NAO_ENTREGUE);
		Pedido.pedidoAtual.setStatusDoPagamento(StatusDoPagamento.NAO_PAGO);
		Pedido.pedidoAtual.setData();
		Pedido.pedidoAtual.setPrecoNormal(Pedido.pedidoAtual.getPreco());
		Pedido.pedidoAtual.setPreco(novoPreco);
		PedidoManager.getInstance().adicionarPedido(Pedido.pedidoAtual);
		PedidoManager.getInstance().getOperacoes().INSERT_DATA(Pedido.pedidoAtual);
		
		this.guiManager.mudarParaTela("telaInicial");
		this.guiManager.getTelaPedidoCliente().limparCampos();
	}
	
	public void prepararComponentes(){
		String a = "";
		if(Pedido.pedidoAtual.getCliente() != null){
			textField_cliente.setText(Pedido.pedidoAtual.getCliente().getNome());
		}
		if(Pedido.pedidoAtual.getPreco() != null){
			NumberFormat nf = NumberFormat.getCurrencyInstance();  
			String formatado = nf.format (Pedido.pedidoAtual.getPreco());
			
			textField_preco.setText(formatado);
			textField_comDesconto.setText(formatado);
			novoPreco = Pedido.pedidoAtual.getPreco();
		}
		
	}
    
}
