package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import bd.OperacoesLivros;
import bd.OperacoesPacotes;
import principais.AnoEscolar;
import principais.ClienteManager;
import principais.EditoraManager;
import principais.Escola;
import principais.EscolaManager;
import principais.EstoqueManager;
import principais.Livro;
import principais.Pacote;
import principais.PacoteManager;
import utilidades.Acao;
import utilidades.AutoSuggestor;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaPacote extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	JTable table;
	JComboBox comboBox;
	private Escola escolaSelecionada;
	private AnoEscolar anoEscolarSelecionado;
	AutoSuggestor autoSuggestor;
	
	public TelaPacote(GUIManager guiManager) {
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
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
        
        JTextField fieldName = new JTextField();
    	c.gridy = 18;
		int posAtual = 9;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = posAtual;
		c.gridwidth = 6;
		this.add(fieldName, c);
		
        autoSuggestor = new AutoSuggestor(fieldName, guiManager.getJanela(), EstoqueManager.getInstance().getTodosLivrosNomes(), 
				Color.white.brighter(), Color.blue, Color.red, 0.1f);
		
        JLabel labelName = new JLabel("NOME");
		posAtual = 9;
		c.gridy = 17;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridwidth = 6;
		c.gridx = posAtual;
		this.add(labelName, c);

        JLabel txt_Title = new JLabel("PACOTE", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		JLabel labelEscola = new JLabel("ESCOLA: ");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		this.add(labelEscola, c);
		
		String[] todasEscolas = EscolaManager.getInstance().getTodosNomesEscolas();
		comboBox = new JComboBox(todasEscolas);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBox,c);
		
		JLabel labelAno = new JLabel("ANO: ");
		c.gridx = 21;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		this.add(labelAno, c);
		
		String[] todosAnos = AnoEscolar.getTodosNomesAnosEscolares();
		JComboBox comboBoxAno = new JComboBox(todosAnos);
		c.gridx = 22;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBoxAno,c);
		
		this.escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
		this.anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());
		
		this.table = new JTable(new MyTableModelPacote(escolaSelecionada, anoEscolarSelecionado));
		minimizarTamanhoDaColuna(table, 0, 40);
		minimizarTamanhoDaColuna(table, 3, 80);
		minimizarTamanhoDaColuna(table, 2, 175);
		JScrollPane scrollPane  = new JScrollPane(this.table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 24;
		c.gridheight = 15;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);
		
		
		String[] acoes = { Acao.ADICIONAR.toString(), Acao.REMOVER.toString() };
		JComboBox comboBoxAcoes = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 10;
		c.gridy = 19;
		this.add(comboBoxAcoes, c);
		
		JButton btn_fazerAcao = new JButton("Confirmar!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 12;
		c.gridy = 19;
		c.gridwidth = 2;
		c.gridheight = 1;
		this.add(btn_fazerAcao, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 20;
		c.gridwidth = 1;
		c.gridheight = 1;
		//this.add(btn_Voltar, c);
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldName.setText("");
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
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBoxAcoes.getSelectedItem().toString());
				fazerAcao(fieldName, table, acao);
				fieldName.setText("");
			}
		});
		
		comboBoxAcoes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldName.setText("");
			}
		});	
	}

	@Override
	public void prepararComponentes(){
		comboBox.removeAllItems();
		String[] todasEscolas = new String[EscolaManager.getInstance().getEscolas().size()];
		for(int i = 0; i < EscolaManager.getInstance().getEscolas().size(); i++){
			todasEscolas[i] = EscolaManager.getInstance().getEscolas().get(i).getNome();
			comboBox.addItem(todasEscolas[i]);
		}

		PacoteManager.getInstance().getOperacoes().GET_AND_SET_ALL_DATA();
		
		autoSuggestor = new AutoSuggestor(autoSuggestor.getTextField(), guiManager.getJanela(), EstoqueManager.getInstance().getTodosLivrosNomes(), 
				  Color.white.brighter(), Color.blue, Color.red, 0.75f);
		
		
		this.repintarTabela();
	}
		
	private void fazerAcao(JTextField textField, JTable table, Acao acao){
		String text = textField.getText();
		
		if(acao == Acao.ADICIONAR){
			Livro livro = EstoqueManager.getInstance().getLivroPeloNome(text);
			if(livro.getNome().equals("LivroInexistente"))
			{
				JOptionPane.showMessageDialog(this, "Esse livro não está registrado.","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}
			else if(PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado).getLivros().contains(livro))
			{
				JOptionPane.showMessageDialog(this, "Esse livro já está inserido nesse pacote.","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Livro Adicionado: " + livro.getNome(), "Adicionado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
				PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado).adicionarLivro(livro);
				this.repintarTabela();
				
				((OperacoesPacotes)PacoteManager.getInstance().getOperacoes()).DELETE_PACOTE(PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado));
				PacoteManager.getInstance().getOperacoes().INSERT_DATA(PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado));
			}
		}
		else if(acao == Acao.REMOVER){
			int id = -1;
			id = table.getSelectedRow();
			
			if(id >= 0 && id < PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado).getLivros().size()){
				Pacote pacoteAtual = PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado);
				Object[][] data = ((MyTableModelPacote)table.getModel()).getData();
				int idReal = (int)data[id][0];
				Livro livro = pacoteAtual.getLivroPeloId(idReal);
				
				JOptionPane.showMessageDialog(this, "Livro removido: " + livro.getNome(), "Removido com sucesso!", JOptionPane.INFORMATION_MESSAGE);
				pacoteAtual.removerLivro(livro);
				this.repintarTabela();
				((OperacoesPacotes)PacoteManager.getInstance().getOperacoes()).DELETE_PACOTE(PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado));
				PacoteManager.getInstance().getOperacoes().INSERT_DATA(pacoteAtual);
				
			}
			else{
				JOptionPane.showMessageDialog(this, "Selecione um livo para remover.","Erro ao remover", JOptionPane.OK_CANCEL_OPTION);
			}
		}
	}
	
	private void minimizarTamanhoDaColuna(JTable table, int index, int tam){
		table.getColumnModel().getColumn(index).setMinWidth(tam);
		table.getColumnModel().getColumn(index).setPreferredWidth(tam);
		table.getColumnModel().getColumn(index).setMaxWidth(tam);
		DefaultTableCellRenderer left = new DefaultTableCellRenderer();
		left.setHorizontalAlignment(SwingConstants.LEFT);
		table.getColumnModel().getColumn(index).setCellRenderer(left);
	}
	
	private void repintarTabela(){
		if(this.table != null){
			((MyTableModelPacote)this.table.getModel()).updateData(escolaSelecionada, anoEscolarSelecionado);
			this.table.repaint();
		}
	}
	
}



class MyTableModelPacote extends AbstractTableModel {
	
	private static Boolean DEBUG = false;
	
    private String[] columnNames = {"ID",
                                    "NOME",
                                    "EDITORA",
                                    "PREÇO"};
   
    private Object[][] data;
    
    public MyTableModelPacote(Escola escola, AnoEscolar anoEscolar){
    	updateData(escola, anoEscolar);
    }
    
    public Object[][] getData(){
    	return this.data;
    }
    
    public void updateData(Escola escola, AnoEscolar anoEscolar){
    	data = new Object[PacoteManager.getInstance().getPacote(escola, anoEscolar).getLivros().size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = PacoteManager.getInstance().getPacote(escola, anoEscolar).getLivros().get(i).pegarParametrosDePacote();
    	}	
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }

        data[row][col] = value;
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}

