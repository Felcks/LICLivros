package gui;

import utilidades.CurvedBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import principais.ClienteManager;
import principais.EstoqueManager;
import principais.Pedido;
import principais.TipoPedido;
import utilidades.Screen;

public class TelaInicial extends JPanel{
	
	private GUIManager guiManager;
	
	public TelaInicial(GUIManager guiManager) {
		this.guiManager = guiManager;	
		
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
        
		JLabel txt_Title = new JLabel("LIC - Livros Ideias Cultura", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(1000,100);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 5;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		JButton btn_FazerPedido = new JButton("Fazer Pedido");
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.gridheight = 2;
		c.gridx = 10;
		c.gridy = 5;
		btn_FazerPedido.setFont(btn_FazerPedido.getFont().deriveFont((float)(Screen.width/50)));
		this.add(btn_FazerPedido, c);
		
		JButton btn_verEstoque = new JButton("Estoque / Registro de Livros");
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.gridheight = 2;
		c.gridx = 10;
		c.gridy = 8;
		btn_verEstoque.setFont(btn_FazerPedido.getFont().deriveFont(Screen.width/50));
		this.add(btn_verEstoque, c);
		
		JButton btn_registrarEditora = new JButton("Registrar Editora");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 11;
		c.gridy = 15;
		this.add(btn_registrarEditora, c);
		
		
		JButton btn_registrarEscola = new JButton("Registrar Escola");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		//c.gridx = 2;
		c.gridy = 16;
		c.ipadx = 0;
		this.add(btn_registrarEscola, c);
		
		JButton btn_registrarCliente = new JButton("Registrar Cliente");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		//c.gridx = 21;
		c.gridy = 17;
		c.ipadx = 0;
		//this.add(btn_registrarCliente, c);
		
		JButton btn_registrarPacoteLivros = new JButton("Registrar Pacote de Livros");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		//c.gridx = 21;
		c.gridy = 17;
		this.add(btn_registrarPacoteLivros, c);
		
		JButton btn_verPedidos = new JButton("Ver Pedidos");
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;;
		//c.gridx = 5;
		c.gridy = 18;
		this.add(btn_verPedidos, c);
		
		JButton btn_pedidoEspecial = new JButton("Fazer pedido avulso");
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.gridx = 10;
		c.gridy = 7;
		this.add(btn_pedidoEspecial, c);
		
	
		btn_registrarCliente.addActionListener(new ActionListener() {
			@Override
			  public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaCliente");
			  }
		});
		
		btn_verEstoque.addActionListener( new ActionListener() {
			@Override
			  public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaEstoque");
			  }
		});
		
		btn_FazerPedido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Pedido.tipoProximoPedido = TipoPedido.NORMAL;
				guiManager.mudarParaTela("telaPedidoUnico");
			}
		});
		
		btn_pedidoEspecial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Pedido.tipoProximoPedido = TipoPedido.AVULSO;
				guiManager.mudarParaTela("telaPedidoCliente");
			}
		});
		
		btn_registrarEditora.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiManager.mudarParaTela("telaEditora");
			}
		});
		
		btn_registrarEscola.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaEscola");
			}
		});
		
		btn_registrarPacoteLivros.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaPacote");
			}
		});
		
		btn_verPedidos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiManager.mudarParaTela("telaPedido");
			}
		});
		
		//this.guiManager.getCards().add(this, "telaInicial");
	}

}
