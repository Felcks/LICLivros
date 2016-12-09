package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import principais.Cliente;
import principais.ClienteManager;
import utilidades.AutoSuggestor;
import utilidades.Screen;

public class TelaPedidoCliente extends JPanel implements IPrepararComponentes {

	private GUIManager guiManager;
	AutoSuggestor autoSuggestor;
	private Boolean clienteValido = false;
	
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
			if(i > 0){
				textFields[i].setEditable(false);
				textFields[i].setBackground(Color.LIGHT_GRAY);
			}
			this.add(textFields[i],c);
		}
  		
  		 String[] columnNames = {"NOME",
                 "BAIRRO",
                 "RUA",
                 "COMPLEMENTO",
                 "TELEFONE",
                 "CELULAR"};
		JLabel[] labels = new JLabel[6];
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
		
		JButton btn_Voltar = new JButton("Voltar");
		c.gridx = 0;
		c.gridy = 9;
		this.add(btn_Voltar, c);
        
		autoSuggestor = new AutoSuggestor(textFields[0], guiManager.getJanela(), ClienteManager.getInstance().getTodosNomesClientes(), 
				Color.white.brighter(), Color.blue, Color.red, 0.75f);
		
		
		textFields[0].getDocument().addDocumentListener(new DocumentListener() {	
			@Override
			public void removeUpdate(DocumentEvent e) {
				checarNome(textFields[0].getText(), textFields);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				checarNome(textFields[0].getText(), textFields);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				checarNome(textFields[0].getText(), textFields);
			}
		});
		
		btn_Avancar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				avancar();
			}
		});
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		
		guiManager.getCards().add(this, "telaPedidoCliente");
	}
	
	private void checarNome(String nome, JTextField[] textFields){
		if(nome.length() == 0)
			return;
		
		List<String> todosNomesClientes = ClienteManager.getInstance().getTodosNomesClientes();
		if(todosNomesClientes.contains(nome))
		{
			atualizarCampos(textFields, nome);
		}
		else if(todosNomesClientes.contains(nome.substring(0, nome.length()-1)))
		{
			atualizarCampos(textFields,nome.substring(0, nome.length()-1));
		}
		else
			limparCampos(textFields);
	}
	
	private void atualizarCampos(JTextField[] campos, String nomeCliente){
		this.clienteValido = true;
		Cliente cliente = ClienteManager.getInstance().getClientePeloNome(nomeCliente);
		Object[] parametros = cliente.pegarTodosParametros();
		
		for(int i = 1; i < campos.length; i++){
			campos[i].setText(parametros[i+1].toString());
		}
	}
	
	private void limparCampos(JTextField[] campos){
		this.clienteValido = false;
		for(int i = 1; i < campos.length; i++)
			campos[i].setText("");
	}
	
	private void avancar(){
		if(clienteValido == true){
			System.out.println("Cliente Válido. Podemos avançar!");
			guiManager.mudarParaTela("telaPedidoPacote");
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Insira um cliente","Cliente Inválido", JOptionPane.OK_CANCEL_OPTION);
		}
	}
	
	@Override
	public void prepararComponentes(){
		autoSuggestor = new AutoSuggestor(autoSuggestor.getTextField(), guiManager.getJanela(), ClienteManager.getInstance().getTodosNomesClientes(), 
										  Color.white.brighter(), Color.blue, Color.red, 0.75f);
	}
}
