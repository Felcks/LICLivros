package gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bd.OperacoesPacotes;
import principais.AnoEscolar;
import principais.Escola;
import principais.EscolaManager;
import principais.PacoteManager;
import utilidades.Acao;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaPedidoPacote extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	JTable table;
	JComboBox comboBox;
	private Escola escolaSelecionada;
	private AnoEscolar anoEscolarSelecionado;
	
	public TelaPedidoPacote(GUIManager guiManager) {
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		 GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 6; i ++){
        	for(int j = 0; j < 10; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        JTextField[] textFields = new JTextField[2];
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 7;
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = i + 2;
			this.add(textFields[i],c);
		}
		
		String[] columnNames = {"ID", "NOME"};
		JLabel[] labels = new JLabel[2];
		c.gridy = 6;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = i + 2;
			this.add(labels[i],c);
		}
        
        JLabel txt_Title = new JLabel("PACOTE", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		String[] todasEscolas = EscolaManager.getInstance().getTodosNomesEscolas();
		comboBox = new JComboBox(todasEscolas);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(comboBox,c);
		
		String[] todosAnos = AnoEscolar.getTodosNomesAnosEscolares();
		JComboBox comboBoxAno = new JComboBox(todosAnos);
		c.gridx = 5;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(comboBoxAno,c);
		
		this.escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
		this.anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());
		
		this.table = new JTable(new MyTableModelPacote(escolaSelecionada, anoEscolarSelecionado));
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		JScrollPane scrollPane  = new JScrollPane(this.table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6;
		c.gridheight = 5;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);
		
		
		JButton btn_Avancar = new JButton("Avançar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 5;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Avancar, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
		
	
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(comboBox != null)
					if(comboBox.getItemCount() > 0)
						escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
				
				repintarTabela();
			}
		});
		comboBoxAno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());
				repintarTabela();
			}
		});
	

		//table.setDefaultRenderer(Boolean.class, new CustomCellRenderer());		
		
		this.guiManager.getCards().add(this, "telaPedidoPacote");
	}
	
	@Override
	public void prepararComponentes(){
		comboBox.removeAllItems();
		String[] todasEscolas = new String[EscolaManager.getInstance().getEscolas().size()];
		for(int i = 0; i < EscolaManager.getInstance().getEscolas().size(); i++){
			todasEscolas[i] = EscolaManager.getInstance().getEscolas().get(i).getNome();
			comboBox.addItem(todasEscolas[i]);
		}

		this.repintarTabela();
	}
	
	private void repintarTabela(){
		if(this.table != null){
			((MyTableModelPacote)this.table.getModel()).updateData(escolaSelecionada, anoEscolarSelecionado);
			this.table.repaint();
		}
	}
	
}
