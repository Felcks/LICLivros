package gui;

import utilidades.CurvedBorder;
import utilidades.Screen;

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TelaInicial2 extends JPanel{
	
	private GUIManager guiManager;
	
	public TelaInicial2(GUIManager guiManager) {
		this.guiManager = guiManager;	
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        GridBagConstraints c = new GridBagConstraints();    
        c.weightx = 1;
        c.weighty = 1;
        
        
       for(int i = 0; i < 24; i ++){
        	for(int j = 0; j < 24; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        } 
     
       JLabel backgroundLabel = new JLabel();
       c.gridx = 0;
       c.gridy = 0;
       c.gridwidth = 24;
       c.gridheight = 24;
       ImageIcon iconLogo = new ImageIcon("Images/Wallpaper.jpg");
       backgroundLabel.setIcon(iconLogo);
       this.add(backgroundLabel, c);
    
	}

}
