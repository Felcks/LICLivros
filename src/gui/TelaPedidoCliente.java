package gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import utilidades.Screen;

public class TelaPedidoCliente extends JPanel {

	private GUIManager guiManager;
	public TelaPedidoCliente(GUIManager guiManager){
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
        
        
        JLabel txt_Title = new JLabel("Pedido - Passo 1", SwingConstants.CENTER);
  		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
  		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
  		c.gridx = 5;
  		c.gridy = 0;
  		c.gridwidth = 1;
  		c.gridheight = 1;
  		c.anchor = GridBagConstraints.PAGE_START;
  		this.add(txt_Title, c);
  		
  		JTextField[] textFields = new JTextField[6];
  		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 5;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridy = i + 1 ;
			this.add(textFields[i],c);
		}
  		
  		 String[] columnNames = {"NOME",
                 "BAIRRO",
                 "RUA",
                 "COMPLEMENTO",
                 "TELEFONE",
                 "CELULAR"};
		JLabel[] labels = new JLabel[6];
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridy = i  + 1;
			this.add(labels[i],c);
		}
		
		JButton btn_Avancar = new JButton("Avançar");
		c.gridx = 10;
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 9;
		this.add(btn_Avancar, c);
        

		guiManager.getCards().add(this, "telaPedidoCliente");
	}
}
