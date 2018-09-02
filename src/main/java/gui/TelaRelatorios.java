package gui;

import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.itextpdf.awt.geom.Dimension;
import com.itextpdf.text.DocumentException;

import utilidades.Acao;
import utilidades.Print;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaRelatorios extends JPanel {
	
	private GUIManager guiManager;
	 
	public TelaRelatorios(GUIManager guiManager) {
		this.guiManager = guiManager;
		
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
	
		GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 24; i ++){
        	for(int j = 0; j < 50; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        JLabel txt_Title = new JLabel("RELATÓRIOS", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
        
        JButton relatorioParcial = new JButton("GERAR RELATÓRIO PARCIAL");
        relatorioParcial.setFont(relatorioParcial.getFont().deriveFont(30f));
        c.gridwidth = 5;
        c.gridx = 10;
        c.gridy = 10;
        c.gridheight = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        this.add(relatorioParcial, c);
        
       
        JButton relatorioFinal = new JButton("GERAR RELATÓRIO FINAL");
        relatorioFinal.setFont(relatorioFinal.getFont().deriveFont(30f));
        c.gridwidth = 5;
        c.gridx = 10;
        c.gridy = 30;
        c.gridheight = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        this.add(relatorioFinal, c);
        
        JTextField fieldFinal = new JTextField();
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy = 29;
        c.gridx = 12;
        fieldFinal.setHorizontalAlignment(JTextField.CENTER);
        this.add(fieldFinal, c);
        
        JLabel labelFinal = new JLabel("Para gerar o relatório final escreva a palavra 'FINAL' no campo indicado");
        c.gridy = 28;
        labelFinal.setFont(labelFinal.getFont().deriveFont(15f));
        c.fill = GridBagConstraints.NONE;
        c.gridx = 10;
        c.gridwidth = 5;
        this.add(labelFinal, c);
        
        relatorioParcial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				imprimirRelatorio(" - parcial");
			}
		});
        
        relatorioFinal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tentarImprimir(" - FINAL", fieldFinal);
			}
		});
        
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	tentarImprimir(" - FINAL", fieldFinal);
            }
        };
        fieldFinal.addActionListener(action);
	}
	
	private void tentarImprimir(String parcialOuFinal, JTextField fieldFinal)
	{
		if(fieldFinal.getText().equals("FINAL")){
			imprimirRelatorio(parcialOuFinal);

			//ESVAZIAR TUDO QUE JÁ FOI PRO RELATORIO FINAL
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Siga a instrução descrita previamente.", "Erro", JOptionPane.OK_CANCEL_OPTION);
		}
		fieldFinal.setText("");
	}
	
	private void imprimirRelatorio(String parcialOuFinal)
	{
		JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso.", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
		try {
			Print.getInstance().printRelatorio(parcialOuFinal);
			//Desktop.getDesktop().open(new File(sb_PATH.toString()));
		} catch (IOException | DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

}
