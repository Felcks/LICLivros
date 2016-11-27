package gui;

import javax.swing.JLabel;

import utilidades.Screen;

public class TextTitle extends JLabel {
	
	public TextTitle(String texto){
		super(texto);
		//this.setSize(10000, 100);
		this.setFont(this.getFont().deriveFont((float)(Screen.width/10)));
		//this.setLocation(Screen.width/2 - this.getMinimumSize().width/2, 10);
	}
}
