package gui;

import javax.swing.JLabel;

public class TextTitle extends JLabel {
	
	public TextTitle(String texto){
		super(texto);
		this.setSize(1000, 100);
		this.setFont(this.getFont().deriveFont(50.0f));
		this.setLocation(1366/2 - this.getMinimumSize().width/2, 10);
	}
}
