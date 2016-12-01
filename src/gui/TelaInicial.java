package gui;

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
        
        
       for(int i = 0; i < 12; i ++){
        	for(int j = 0; j < 10; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        } 
        
		JLabel txt_Title = new JLabel("LIC - Livros Ideias Cultura", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(1000,100);
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		JButton btn_FazerPedido = new JButton("Fazer Pedido");
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.gridheight = 2;
		c.insets = new Insets(10,10,10,10);
		c.gridx = 4;
		c.gridy = 2;
		btn_FazerPedido.setFont(btn_FazerPedido.getFont().deriveFont((float)(Screen.width/40)));
		this.add(btn_FazerPedido, c);
		
		JButton btn_verEstoque = new JButton("Checar Estoque");
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 4;
		c.gridheight = 2;
		c.gridx = 4;
		c.gridy = 4;
		btn_verEstoque.setFont(btn_FazerPedido.getFont().deriveFont(Screen.width/25));
		this.add(btn_verEstoque, c);
		
		JButton btn_registrarEditora = new JButton("Registrar Editora");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.gridx = 1;
		c.gridy = 6;
		c.ipadx = 0;
		c.ipady = 0;
		this.add(btn_registrarEditora, c);
		
		
		JButton btn_registrarEscola = new JButton("Registrar Escola");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 7;
		c.ipadx = 0;
		this.add(btn_registrarEscola, c);
		
		JButton btn_registrarCliente = new JButton("Registrar Cliente");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 8;
		c.gridy = 6;
		c.ipadx = 0;
		this.add(btn_registrarCliente, c);
		
		JButton btn_registrarPacoteLivros = new JButton("Registrar Pacote de Livros");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 8;
		c.gridy = 7;
		this.add(btn_registrarPacoteLivros, c);
		
		
	
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
				guiManager.mudarParaTela("telaPedidoCliente");
			}
		});
		
		btn_registrarEditora.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiManager.mudarParaTela("telaEditora");
			}
		});
		
		this.guiManager.getCards().add(this, "telaInicial");
	}

}
