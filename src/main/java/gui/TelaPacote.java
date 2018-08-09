package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
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
import bd.OperacoesLivrosPacotes;
import bd.OperacoesPacotes;
import principais.*;
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
	private OperacoesLivrosPacotes operacoesLivrosPacotes = new OperacoesLivrosPacotes();

	private JTable tableLivros;
	
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

        JLabel txt_Title = new JLabel("PACOTE", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(30,30);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 10;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		JLabel labelEscola = new JLabel("ESCOLA: ");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		this.add(labelEscola, c);
		
		String[] todasEscolas = EscolaManager.getInstance().getTodosNomesEscolas();
		comboBox = new JComboBox(todasEscolas);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 5;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBox,c);
		
		JLabel labelAno = new JLabel("ANO: ");
		c.gridx = 6;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		this.add(labelAno, c);
		
		String[] todosAnos = AnoEscolar.getTodosNomesAnosEscolares();
		JComboBox comboBoxAno = new JComboBox(todosAnos);
		c.gridx = 7;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBoxAno,c);

		this.escolaSelecionada = EscolaManager.getInstance().getEscolaPeloNome(comboBox.getSelectedItem().toString());
		this.anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());

		this.table = new JTable(new MyTableModelPacote(escolaSelecionada, anoEscolarSelecionado));
		minimizarTamanhoDaColuna(table, 0, 40);
		minimizarTamanhoDaColuna(table, 3, 80);
		minimizarTamanhoDaColuna(table, 2, 175);
		JScrollPane scrollPane  = new JScrollPane(this.table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 10;
		c.gridheight = 10;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);

		this.inicializarViewLivros(c);
		
		JButton btn_fazerAcao = new JButton("Adicionar ao Pacote");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 14;
		c.gridy = 16;
		c.gridwidth = 10;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(btn_fazerAcao, c);
		
		JButton btn_fazerAcao2 = new JButton("Remover do Pacote");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 16;
		c.gridwidth = 10;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(btn_fazerAcao2, c);
		
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(comboBox != null)
					if(comboBox.getItemCount() > 0)
						escolaSelecionada = EscolaManager.getInstance().getEscolaPeloNome(comboBox.getSelectedItem().toString());
								///new Escola(comboBox.getSelectedItem().toString());
				
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
				fazerAcao(table, tableLivros, Acao.ADICIONAR);
			}
		});
		
		btn_fazerAcao2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fazerAcao(table, tableLivros, Acao.REMOVER);
			}
		});

		atualizarLivros(table, comboBox);
	        
	}


	private JComboBox comboBoxEditora;
	private void inicializarViewLivros(GridBagConstraints c){

		JLabel txt_Title = new JLabel("LIVROS", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(30,30);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 14;
		c.gridy = 0;
		c.gridwidth = 10;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);

		JLabel editoraLabel = new JLabel("EDITORA: ");
		c.gridx = 14;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(editoraLabel, c);

		String[] todasEditoras = new String[EditoraManager.getInstance().getEditoras().size()];
		for(int i = 0; i < EditoraManager.getInstance().getEditoras().size(); i++){
			todasEditoras[i] = EditoraManager.getInstance().getEditoras().get(i).getNome();
		}
		comboBoxEditora = new JComboBox(todasEditoras);
		c.gridx = 15;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBoxEditora,c);


		this.tableLivros = new JTable(new MyTableModelLivro(""));
		minimizarTamanhoDaColuna(tableLivros, 0, 40);
		minimizarTamanhoDaColuna(tableLivros, 3, 80);
		minimizarTamanhoDaColuna(tableLivros, 2, 175);
		JScrollPane scrollPane2  = new JScrollPane(this.tableLivros);
		tableLivros.setFillsViewportHeight(true);
		c.gridx = 14;
		c.gridy = 4;
		c.gridwidth = 10;
		c.gridheight = 10;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane2, c);

		comboBoxEditora.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				atualizarLivros(table, comboBox);
			}
		});
	}

	private void atualizarLivros(JTable table, JComboBox comboBox){
		try{
			if(comboBox != null){
				this.repintarTabelaLivro(comboBoxEditora.getSelectedItem().toString());
				//textField.setText(comboBox.getSelectedItem().toString());
			}

		}
		catch(java.lang.NullPointerException e){

		}
	}

	private void repintarTabelaLivro(String editora, Ordenar ordenar){
		if(this.tableLivros != null){
			((MyTableModelLivro)this.tableLivros.getModel()).updateData(editora, ordenar);
			this.tableLivros.repaint();
			//TODO atualizarCampos();
		}
	}

	private void repintarTabelaLivro(String editora){
		if(this.tableLivros != null){
			((MyTableModelLivro)this.tableLivros.getModel()).updateData(editora);
			this.tableLivros.repaint();
			//TODO atualizarCampos();
		}
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

		comboBoxEditora.removeAllItems();
		String[] todasEditoras = new String[EditoraManager.getInstance().getEditoras().size()];
		for(int i = 0; i < EditoraManager.getInstance().getEditoras().size(); i++){
			todasEditoras[i] = EditoraManager.getInstance().getEditoras().get(i).getNome();
			comboBoxEditora.addItem(todasEditoras[i]);
		}
		
		this.repintarTabela();
	}

	private void fazerAcao(JTable table, JTable tableLivros, Acao acao){
		
		if(acao == Acao.ADICIONAR){

			int id = -1;
			id = tableLivros.getSelectedRow();
			if(id == -1 || id >= tableLivros.getRowCount()){
				JOptionPane.showMessageDialog(this, "Selecione um livro para ser adicionado.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
				return;
			}
			id = Integer.parseInt(tableLivros.getValueAt(tableLivros.getSelectedRow(),0).toString());

			//Livro livro = EstoqueManager.getInstance().getLivroPeloId(id);
			Pacote pacote = PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado);
			this.operacoesLivrosPacotes.INSERT_DATA(id, pacote.getId());
			this.repintarTabela();

			//this.repintarTabelaLivro(comboBoxEditora.getSelectedItem().toString());

			/*Livro livro = EstoqueManager.getInstance().getLivroPeloNome(text);
			if(livro.getNome().equals("LivroInexistente"))
			{
				JOptionPane.showMessageDialog(this, "Esse livro não está registrado.","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}
			else if(PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado).getLivros().contains(livro))
			{
				JOptionPane.showMessageDialog(this, "Esse livro já está inserido nesse pacote.","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}
			else
			{
				textField.setText("");
				JOptionPane.showMessageDialog(this, "Livro Adicionado: " + livro.getNome(), "Adicionado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
				PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado).adicionarLivro(livro);
				this.repintarTabela();
				
				((OperacoesPacotes)PacoteManager.getInstance().getOperacoes()).DELETE_PACOTE(PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado));
				PacoteManager.getInstance().getOperacoes().INSERT_DATA(PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado));
			}*/
		}
		else if(acao == Acao.REMOVER){

			int id = -1;
			id = table.getSelectedRow();
			if(id == -1 || id >= table.getRowCount()) {
				JOptionPane.showMessageDialog(this, "Selecione um livro para ser removido.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
				return;
			}
			id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());


			Pacote pacote = PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado);
			this.operacoesLivrosPacotes.DELETE_LIVRO_DE_PACOTE(id, pacote.getId());
			this.repintarTabela();

			/*
			if(id >= 0 && id < PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado).getLivros().size()){
				Pacote pacoteAtual = PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado);
				Object[][] data = ((MyTableModelPacote)table.getModel()).getData();
				int idReal = (int)data[id][0];
				Livro livro = pacoteAtual.getLivroPeloId(idReal);
				
				JOptionPane.showMessageDialog(this, "Livro removido: " + livro.getNome(), "Removido com sucesso!", JOptionPane.INFORMATION_MESSAGE);
				pacoteAtual.removerLivro(livro);
				this.repintarTabela();
				((OperacoesPacotes)PacoteManager.getInstance().getOperacoes()).DELETE_PACOTE(PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado));
				PacoteManager.getInstance().getOperacoes().INSERT_DATA(pacoteAtual);
				
			}
			else{
				JOptionPane.showMessageDialog(this, "Selecione um livo para remover.","Erro ao remover", JOptionPane.OK_CANCEL_OPTION);
			}*/
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

    	Pacote pacote = PacoteManager.getInstance().getPacote(escola.getId(), anoEscolar);

    	OperacoesLivrosPacotes operacoesLivrosPacotes = new OperacoesLivrosPacotes();
    	ArrayList<Livro> livros = (ArrayList<Livro>)operacoesLivrosPacotes.GET_LIVROS_DE_PACOTE(pacote.getId());

    	data = new Object[livros.size()][];
    	for(int i = 0; i < livros.size(); i++){
    		Livro livro = EstoqueManager.getInstance().getLivroPeloId(livros.get(i).getId());
    		data[i] = livro.pegarTodosParametrosEspecial();

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

class MyTableModelLivro extends AbstractTableModel {


	private String[] columnNames = {"ID",
									"NOME",
									"EDITORA",
									"PREÇO"};

	private Object[][] data;
	private Ordenar lastOrdem = Ordenar.NUMERO;

	public Object[][] getData(){
		return data;
	}
	public MyTableModelLivro(String editora){
		updateData(editora, Ordenar.NUMERO);
	}

	public void updateData(String editora, Ordenar ordenar){


		/*List<Livro> livros = EstoqueManager.getInstance().getTodosLivros();
		data = new Object[livros.size()][];
		for(int i = 0; i < data.length; i++){
			data[i] = livros.get(i).pegarTodosParametrosEspecial();
		}*/

		/*data = new Object[EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).size()][];
		for(int i = 0; i < data.length; i++){
			data[i] = EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).get(i).pegarTodosParametrosEspecial();
		}*/

		//lastOrdem = ordenar;
		//ordenar(editora);
	}

	public void updateData(String editora){
		/*data = new Object[EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).size()][];
		for(int i = 0; i < data.length; i++){
			data[i] = EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).get(i).pegarTodosParametrosEspecial();
		}

		ordenar(editora);*/

        List<Livro> livros = EstoqueManager.getInstance().getTodosLivros();
        data = new Object[livros.size()][];
        for(int i = 0; i < data.length; i++){
            data[i] = livros.get(i).pegarTodosParametrosEspecial();
        }
	}

	private void ordenar(String editora){
		if(lastOrdem == Ordenar.NOME){
			for(int i = 0; i < data.length; i++){
				ArrayList<Livro> livrosOrdenados= (ArrayList<Livro>) EstoqueManager.getInstance().getLivrosDeUmaEditora(editora);
				NomeLivroComparator nLC = new NomeLivroComparator();
				Collections.sort(livrosOrdenados, nLC);
				data[i] = livrosOrdenados.get(i).pegarTodosParametrosEspecial();
			}
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

		data[row][col] = value;
		fireTableCellUpdated(row, col);

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

