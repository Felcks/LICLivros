package gui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utilidades.Screen;

public class GUIManager{
	private JFrame janela;
	private JPanel cards;
	private TelaInicial telaInicial;
	private TelaEstoque telaEstoque;
	private TelaPacote telaPacote;
	private TelaCliente telaCliente;
	private TelaPedidoCliente telaPedidoCliente;
	
	public GUIManager(){
		this.criarJanela();
		this.criarCards();
		
		this.telaInicial = new TelaInicial(this);
		this.telaEstoque = new TelaEstoque(this);
		this.telaPacote = new TelaPacote(this);
		this.telaCliente = new TelaCliente(this);
		this.telaPedidoCliente = new TelaPedidoCliente(this);
		this.cards.add(this.telaInicial, "telaInicial");
		this.cards.add(this.telaEstoque, "telaEstoque");
		this.cards.add(this.telaPacote, "telaPacote");
		this.cards.add(this.telaCliente, "telaCliente");
		this.cards.add(this.telaPedidoCliente, "telaPedidoCliente");
		
		this.mudarParaTela("telaInicial");
	}
	
	private void criarJanela(){
		this.janela = new JFrame("Livraria");
		this.janela.setSize(Screen.width, Screen.height);
		this.janela.setResizable(true);
		this.janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.janela.setVisible(true);
		this.janela.setTitle("LIC - LIVROS IDEIAS CULTURA");
		//this.janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.janela.setLocationRelativeTo(null);
	}
	private void criarCards(){
		this.cards = new JPanel(new CardLayout());
		this.janela.getContentPane().add(this.cards, BorderLayout.CENTER);
	}
	
	public void mudarParaTela(String tela){		
		CardLayout cl = (CardLayout)(this.cards.getLayout());
		cl.show(cards, tela);
	}
	
	public JPanel getCards(){
		return this.cards;
	}
	
	public JFrame getJanela(){
		return this.janela;
	}
}
