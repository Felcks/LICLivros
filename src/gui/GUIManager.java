package gui;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import principais.ClienteManager;
import principais.EditoraManager;
import principais.EscolaManager;
import principais.EstoqueManager;
import principais.PacoteManager;
import principais.PedidoManager;
import utilidades.Screen;

public class GUIManager{
	private JFrame janela;
	private JPanel cards;
	private TelaInicial telaInicial;
	private TelaEstoque telaEstoque;
	private TelaPacote telaPacote;
	private TelaCliente telaCliente;
	private TelaEditora telaEditora;
	private TelaPedidoCliente telaPedidoCliente;
	private TelaPedidoPacote telaPedidoPacote;
	private TelaPedidoFinalizacao telaPedidoFinalizacao;
	private TelaPedido telaPedido;
	private TelaEscola telaEscola;
	
	public GUIManager(){
		this.criarJanela();
		this.criarCards();

		this.prepararComponentes();
		
		this.telaInicial = new TelaInicial(this);
		this.telaEstoque = new TelaEstoque(this);
		this.telaPacote = new TelaPacote(this);
		this.telaCliente = new TelaCliente(this);
		this.telaEditora = new TelaEditora(this);
		this.telaPedidoCliente = new TelaPedidoCliente(this);
		this.telaPedidoPacote = new TelaPedidoPacote(this);
		this.telaPedidoFinalizacao = new TelaPedidoFinalizacao(this);
		this.telaEscola = new TelaEscola(this);
		this.telaPedido = new TelaPedido(this);
		this.cards.add(this.telaInicial, "telaInicial");
		this.cards.add(this.telaEstoque, "telaEstoque");
		this.cards.add(this.telaPacote, "telaPacote");
		this.cards.add(this.telaCliente, "telaCliente");
		this.cards.add(this.telaEditora, "telaEditora");
		this.cards.add(this.telaPedidoCliente, "telaPedidoCliente");
		this.cards.add(this.telaPedidoPacote, "telaPedidoPacote");
		this.cards.add(this.telaPedidoFinalizacao, "telaPedidoFinalizacao");
		this.cards.add(this.telaEscola, "telaEscola");		
		this.cards.add(this.telaPedido, "telaPedido");
		this.mudarParaTela("telaInicial");
	}
	
	private void criarJanela(){
		this.janela = new JFrame("Livraria");
		this.janela.setSize(Screen.width, Screen.height);
		this.janela.setResizable(true);
		this.janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.janela.setVisible(true);
		this.janela.setTitle("LIC - LIVROS IDEIAS CULTURA");
		this.janela.setLocationRelativeTo(null);
	}
	private void criarCards(){
		this.cards = new JPanel(new CardLayout());
		this.janela.getContentPane().add(this.cards, BorderLayout.CENTER);
	}
	
	public void mudarParaTela(String tela){		
		// ESSE IF AQUI AINDA É RUIM. MAS FOI A MELHOR FORMA QUE ENCONTREI. PELO MENOS O MÉTODO ESTÁ NA INTERFACE.
		if(tela.equals("telaCliente")){
			this.telaCliente.prepararComponentes();
		}
		else if(tela.equals("telaPedidoCliente")){
			this.telaPedidoCliente.prepararComponentes();
		}
		else if(tela.equals("telaEstoque")){
			this.telaEstoque.prepararComponentes();
		}
		else if(tela.equals("telaEditora")){
			this.telaEditora.prepararComponentes();
		}
		else if(tela.equals("telaEscola")){
			this.telaEscola.prepararComponentes();
		}
		else if(tela.equals("telaPacote")){
			this.telaPacote.prepararComponentes();
		}
		else if(tela.equals("telaPedidoPacote")){
			this.telaPedidoPacote.prepararComponentes();
		}
		else if(tela.equals("telaPedido")){
			this.telaPedido.prepararComponentes();
		}
		
		CardLayout cl = (CardLayout)(this.cards.getLayout());
		cl.show(cards, tela);
	}
	
	public JPanel getCards(){
		return this.cards;
	}
	
	public JFrame getJanela(){
		return this.janela;
	}
	
	private void prepararComponentes(){
		ClienteManager.getInstance().getTodosClientesDoBD();
		EstoqueManager.getInstance().getLivrosDoBancoDeDados();
		EditoraManager.getInstance().getTodasEditorasDoBD();
		EscolaManager.getInstance().getTodasEscolasDoBD();
		PacoteManager.getInstance().getTodosOsPacotesDoBD();
		PedidoManager.getInstance().getTodosPedidosBD();
	}
	
}
