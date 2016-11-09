package gui;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIManager{
	private JFrame janela;
	private JPanel cards;
	private TelaInicial telaInicial;
	private TelaEstoque telaEstoque;
	private TelaPacote telaPacote;
	
	public GUIManager(){
		this.criarJanela();
		this.criarCards();
		
		this.telaInicial = new TelaInicial(this);
		this.telaEstoque = new TelaEstoque(this);
		this.telaPacote = new TelaPacote(this);
		this.cards.add(this.telaInicial, "telaInicial");
		this.cards.add(this.telaEstoque, "telaEstoque");
		this.cards.add(this.telaPacote, "telaPacote");
		
		this.mudarParaTela("telaInicial");
	}
	
	private void criarJanela(){
		this.janela = new JFrame("Livraria");
		this.janela.setSize(1366, 768);
		this.janela.setResizable(false);
		this.janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.janela.setUndecorated(true);
		this.janela.setVisible(true);
		this.janela.setTitle("LIC - LIVROS IDEIAS CULTURA");
		this.janela.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
