package gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import utilidades.ServicoDeDigito;

public class TelaCredito extends JPanel {
	
	private GUIManager guiManager;
	 
	public TelaCredito(GUIManager guiManager) {
		this.guiManager = guiManager;
		
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		 GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 24; i ++){
        	for(int j = 0; j < 40; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        JLabel titulo = new JLabel("LIC - Livros Ideias Cultura");
        titulo.setFont(titulo.getFont().deriveFont(50F));
        c.gridwidth = 24;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 5;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        this.add(titulo, c);
        
        JLabel data = new JLabel("Data: 11/01/2017");
        data.setFont(data.getFont().deriveFont(20f));
        JLabel versao = new JLabel("Versão: 1.0.0.1.38");
        versao.setFont(versao.getFont().deriveFont(20f));
        c.anchor = GridBagConstraints.CENTER;
        c.gridheight = 1;
        c.gridy = 7;
        this.add(versao, c);
        c.gridy = 8;
        this.add(data, c);
        
        JLabel desenvolvedores = new JLabel("Desenvolvedores: ");
        desenvolvedores.setFont(desenvolvedores.getFont().deriveFont(30f));
        JLabel gabriel = new JLabel("Gabriel Nogueira");
        gabriel.setFont(gabriel.getFont().deriveFont(20f));
        JLabel matheus = new JLabel("Matheus Felipe");
        matheus.setFont(matheus.getFont().deriveFont(20f));
        
        c.gridy = 15;
        this.add(desenvolvedores, c);
        c.gridy = 16;
        this.add(gabriel, c);
        c.gridy = 17;
        this.add(matheus, c);
        
        JLabel consideracoes = new JLabel("\"Que o presente software ajude a LIC em mais 25 anos de prosperidade\"");
        c.gridy = 30;
        consideracoes.setFont(consideracoes.getFont().deriveFont(30F));
        //this.add(consideracoes, c);
	}
}