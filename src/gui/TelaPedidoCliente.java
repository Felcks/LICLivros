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
        
        JLabel txt_Title = new JLabel("CLIENTES", SwingConstants.CENTER);
  		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
  		txt_Title.setSize(100,100);
         c.fill = GridBagConstraints.NONE;
  		c.gridx = 3;
  		c.gridy = 0;
  		//c.ipady = 10;
  		c.weightx = 1;
  		c.weighty = 1;
  		c.gridwidth = 1;
  		c.gridheight = 1;
  		c.anchor = GridBagConstraints.PAGE_START;
  		this.add(txt_Title, c);
  		
  		JTextField[] textFields = new JTextField[7];
  		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridy = i * 2 + 1 ;
			this.add(textFields[i],c);
		}
  		
  		 String[] columnNames = {"ID",
                 "NOME",
                 "BAIRRO",
                 "RUA",
                 "COMPLEMENTO",
                 "TELEFONE",
                 "CELULAR"};
		JLabel[] labels = new JLabel[7];
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.CENTER;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridy = i * 2 + 1;
			this.add(labels[i],c);
		}
		
		JButton btn_Avancar = new JButton("Avançar");
		c.gridx = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 15;
		this.add(btn_Avancar, c);
        

		guiManager.getCards().add(this, "telaPedidoCliente");
	}
}
