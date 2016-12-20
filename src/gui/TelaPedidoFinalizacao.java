package gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bd.OperacoesPedidos;
import principais.EstoqueManager;
import principais.Pedido;
import principais.PedidoManager;
import utilidades.Screen;
import utilidades.ServicoDeDigito;
import utilidades.Status;
import utilidades.StatusDaEntrega;
import utilidades.StatusDoPagamento;
import utilidades.FormaDePagamento;
import utilidades.FormaDeEntrega;

public class TelaPedidoFinalizacao extends JPanel{
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	private JTable table;
	
	public TelaPedidoFinalizacao(GUIManager guiManager){
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 7; i ++){
        	for(int j = 0; j < 10; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        JLabel txt_Title = new JLabel("Pedido - Passo 3", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 7;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
        
        JLabel label_pagamento = new JLabel("Forma de Pagamento: ", SwingConstants.CENTER);
        label_pagamento.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        this.add(label_pagamento, c);
        
        JLabel label_entrega = new JLabel("Forma de Entrega: ", SwingConstants.CENTER);
        label_entrega.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        c.gridx = 2;
        c.gridy = 3;
        this.add(label_entrega, c);
        
        /*JLabel label_obs = new JLabel("Observações: ", SwingConstants.CENTER);
        c.gridx = 2;
        c.gridy = 5;
        label_obs.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/50)));
        this.add(label_obs, c);*/
        
        FormaDePagamento[] enumerado = FormaDePagamento.values();
        String[] formasPagamento = new String[enumerado.length];
        for(int i = 0; i < formasPagamento.length; i++)
        	formasPagamento[i] = enumerado[i].getNome();
        
        JComboBox comboBox_pagamento = new JComboBox(formasPagamento);
        c.gridx = 2;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(comboBox_pagamento, c);
        
        FormaDeEntrega[] enumerado2 = FormaDeEntrega.values();
        String[] formasEntrega = new String[enumerado2.length];
        for(int i = 0; i < formasEntrega.length; i++)
        	formasEntrega[i] = enumerado2[i].getNome();
        
        JComboBox comboBox_entrega = new JComboBox(formasEntrega);
        c.gridx = 2;
        c.gridy = 4;
        this.add(comboBox_entrega, c);
        
        JTextField textField_obs = new JTextField();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 3;
        c.gridheight = 3;
        //this.add(textField_obs, c);
        
        JButton btn_Avancar = new JButton("Concluir Pedido!");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 6;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Avancar, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 9;
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
				atualizarPedido(comboBox_entrega, comboBox_pagamento, textField_obs);
			
			}
		});
	

		this.guiManager.getCards().add(this);
	}
	
	private void atualizarPedido(JComboBox comboBox_pagamento, JComboBox comboBox_entrega, JTextField textField_obs){
		String livrosNomes = "";
		for(int i = 0; i < Pedido.pedidoAtual.getPacote().getLivros().size(); i++){
			if(Pedido.pedidoAtual.getIdsDosLivrosComprados()[i] >= 0){
				livrosNomes += Pedido.pedidoAtual.getPacote().getLivros().get(i).getNome() + ", ";
				EstoqueManager.getInstance().retirarDoEstoque(Pedido.pedidoAtual.getIdsDosLivrosComprados()[i]);
			}
		}
		
		Pedido.pedidoAtual.setFormaDePagamento(FormaDePagamento.getFormaDePagamentoPeloNome(comboBox_entrega.getSelectedItem().toString()));
		Pedido.pedidoAtual.setFormaDeEntrega(FormaDeEntrega.getFormaDeEntregaPeloNome(comboBox_pagamento.getSelectedItem().toString()));
		Pedido.pedidoAtual.setObs(textField_obs.getText());
		JOptionPane.showConfirmDialog(this, "Cliente: " + Pedido.pedidoAtual.getCliente().getNome() + 
				"\nPreço: " + Pedido.pedidoAtual.getPreco() +
				"\nFormaDePagamento: " + Pedido.pedidoAtual.getFormaDePagamento().getNome() +
				"\nFormaDeEntrega: " + Pedido.pedidoAtual.getFormaDeEntrega().getNome() +
				"\nLivros: " + livrosNomes, "Finalização", JOptionPane.OK_CANCEL_OPTION);
		
		Pedido.pedidoAtual.setId(PedidoManager.getInstance().gerarId());
		Pedido.pedidoAtual.setStatus(Status.EM_ANDAMENTO);
		Pedido.pedidoAtual.setStatusDaEntrega(StatusDaEntrega.NÂO_ENTREGUE);
		Pedido.pedidoAtual.setStatusDoPagamento(StatusDoPagamento.NAO_PAGO);
		Pedido.pedidoAtual.setData();
		PedidoManager.getInstance().adicionarPedido(Pedido.pedidoAtual);
		OperacoesPedidos op = new OperacoesPedidos();
		op.INSERT_PEDIDOS(PedidoManager.getInstance().getPedidos());
		
		this.guiManager.mudarParaTela("telaInicial");
		this.guiManager.getTelaPedidoCliente().limparCampos();
	}
	
    
}
