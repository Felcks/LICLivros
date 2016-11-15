package gui;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import principais.EstoqueManager;

public class TelaInicial extends JPanel {
	
	private GUIManager guiManager;
	
	public TelaInicial(GUIManager guiManager) {
		this.guiManager = guiManager;
		
		this.setLayout(null);
		this.add(new TextTitle("LIC - Livros Ideias Cultura"));
		
		JButton btnRegistrarLivro = new JButton("Ver Estoque");
		btnRegistrarLivro.addActionListener( new ActionListener() {
			@Override
			  public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaEstoque");
				
			  }
		});
		btnRegistrarLivro.setBounds(1366/2 - 150, 300, 300, 100);
		btnRegistrarLivro.setFont(btnRegistrarLivro.getFont().deriveFont(15.0f));
		this.add(btnRegistrarLivro);
		
		JButton btnPacoteDeLivros = new JButton("Ver Pacotes de Livros");
		btnPacoteDeLivros.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				guiManager.mudarParaTela("telaPacote");
			}
		});
		btnPacoteDeLivros.setBounds(1366/2 - 150, 400, 300, 100);
		btnPacoteDeLivros.setFont(btnPacoteDeLivros.getFont().deriveFont(15.0f));
		this.add(btnPacoteDeLivros);
		
		this.guiManager.getCards().add(this);
	}
	

}
