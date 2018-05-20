package gui;

import utilidades.ServicoDeDigito;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.List;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.itextpdf.text.DocumentException;

import bd.JavaConnection;
import bd.OperacoesClientes;
import bd.OperacoesLivros;
import principais.Cliente;
import principais.ClienteManager;
import principais.Editora;
import principais.EditoraManager;
import principais.EstoqueManager;
import principais.Livro;
import principais.NomeLivroComparator;
import principais.Ordenar;
import principais.Pedido;
import utilidades.Acao;
import utilidades.Print;
import utilidades.Screen;

public class TelaEstoque extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	JTable table;
	JComboBox comboBox;
	JComboBox comboBoxAcoes;
	JTextField[] textFields;

	private enum Field{
	    ID(0), NOME(1), EDITORA(2), QUANTIDADE(3), PRECO(4);

        public int index = 0;
        private Field(int num){
	        this.index =  num;
        }
    }
	 
	public TelaEstoque(GUIManager guiManager) {
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
        
        textFields = new JTextField[5];
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 18;
		c.fill = GridBagConstraints.HORIZONTAL;
		int[] widthX = new int[] {2, 8, 3, 2, 2};
		int posAtual = 2;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = posAtual ;
			c.gridwidth = widthX[i];
			posAtual += widthX[i];
			c.anchor = GridBagConstraints.PAGE_START;
			if(i == 2 || i == 0){
				textFields[i].setEditable(false);
				textFields[i].setBackground(Color.lightGray);
			}
			
			this.add(textFields[i],c);
		}
		
		String[] columnNames = {"ID", "NOME", "EDITORA", "QUANTIDADE", "PREÇO"};
		JLabel[] labels = new JLabel[5];
		c.gridy = 17;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		posAtual = 2;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = posAtual ;
			c.gridwidth = widthX[i];
			posAtual += widthX[i];
			this.add(labels[i],c);
		}
        
        JLabel txt_Title = new JLabel("LIVROS", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		JLabel editoraLabel = new JLabel("EDITORA: ");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(editoraLabel, c);
		
		String[] todasEditoras = new String[EditoraManager.getInstance().getEditoras().size()];
		for(int i = 0; i < EditoraManager.getInstance().getEditoras().size(); i++){
			todasEditoras[i] = EditoraManager.getInstance().getEditoras().get(i).getNome();
		}
		comboBox = new JComboBox(todasEditoras);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBox,c);
		
		this.table = new JTable(new MyTableModel(""));
		JScrollPane scrollPane  = new JScrollPane(this.table);
		minimizarTamanhoDaColuna(table, 0, 40);
		minimizarTamanhoDaColuna(table, 2, 140);
		minimizarTamanhoDaColuna(table, 3, 90);
		minimizarTamanhoDaColuna(table, 4, 90);
		minimizarTamanhoDaColuna(table, 5, 90);
		minimizarTamanhoDaColuna(table, 6, 90);
		
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 24;
		c.gridheight = 15;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);
		
		
		String[] acoes = new String[3];
		acoes[0] = Acao.ADICIONAR.name();
		acoes[1] = Acao.ATUALIZAR.name();
		acoes[2] = Acao.REMOVER.name();
		comboBoxAcoes = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 9;
		c.gridy = 19;
		this.add(comboBoxAcoes, c);
		
		JButton btn_fazerAcao = new JButton("Confirmar!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 11;
		c.gridy = 19;
		c.gridwidth = 2;
		c.gridheight = 1;
		this.add(btn_fazerAcao, c);
		
		JRadioButton numeroRadio = new JRadioButton("NÚMERO");
		c.gridx = 15;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 0;
		numeroRadio.setSelected(true);
		this.add(numeroRadio, c);
		
		JRadioButton nomeRadio = new JRadioButton("NOME");
		c.gridx = 16;
		this.add(nomeRadio, c);
		
		JLabel ordernarLabel = new JLabel("Ordernar por:", SwingConstants.CENTER);
		c.gridx = 15;
		c.gridwidth = 2;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		this.add(ordernarLabel, c);
		
		ButtonGroup grupo = new ButtonGroup();
		grupo.add(numeroRadio);
		grupo.add(nomeRadio);
		
		numeroRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repintarTabela(comboBox.getSelectedItem().toString(), Ordenar.NUMERO);
			}
		});
		
		nomeRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repintarTabela(comboBox.getSelectedItem().toString(), Ordenar.NOME);
			}
		});
		
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				atualizarLivros(table, comboBox, textFields[Field.EDITORA.index]);
			}
		});
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBoxAcoes.getSelectedItem().toString());
				fazerAcao(textFields, table, acao);
				//servicoDeDigito.limparCampos(textFields, 1);
			}
		});	
		
		comboBoxAcoes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Acao acao = Acao.valueOf(comboBoxAcoes.getSelectedItem().toString());

				if(acao == Acao.ADICIONAR) {

                    servicoDeDigito.limparCampos(textFields, Field.EDITORA.index);
				}
				else if (table.getSelectedRow() != -1) {

					textFields[Field.ID.index].setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                    textFields[Field.NOME.index].setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                    textFields[Field.QUANTIDADE.index].setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                    textFields[Field.PRECO.index].setText(
                            table.getValueAt(table.getSelectedRow(), 6).toString().substring(3));
				}
			}
		});

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String comboBoxValue = comboBoxAcoes.getSelectedItem().toString();

                if(comboBoxValue.equalsIgnoreCase(Acao.ATUALIZAR.toString()) ||
                        comboBoxValue.equalsIgnoreCase(Acao.REMOVER.toString())) {

                    if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                        textFields[Field.ID.index].setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                        textFields[Field.NOME.index].setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                        textFields[Field.QUANTIDADE.index].setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                        textFields[Field.PRECO.index].setText(
                                table.getValueAt(table.getSelectedRow(), 6).toString().substring(3));
                    }
                    else{
                        textFields[Field.ID.index].setText("");
                        textFields[Field.NOME.index].setText("");
                        textFields[Field.QUANTIDADE.index].setText("");
                        textFields[Field.PRECO.index].setText("");
                    }
                }
            }
        });
		
		atualizarLivros(table, comboBox, textFields[Field.EDITORA.index]);
	}
	
	private void minimizarTamanhoDaColuna(JTable table, int index, int tam){
		table.getColumnModel().getColumn(index).setMinWidth(tam);
		table.getColumnModel().getColumn(index).setPreferredWidth(tam);
		table.getColumnModel().getColumn(index).setMaxWidth(tam);
		DefaultTableCellRenderer left = new DefaultTableCellRenderer();
		left.setHorizontalAlignment(SwingConstants.LEFT);
		table.getColumnModel().getColumn(index).setCellRenderer(left);
	}
	
	private void showMessage(){
		JOptionPane.showMessageDialog(this, "Relatório impresso com sucesso", "Impressão bem-sucedida!" , JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void prepararComponentes(){

		comboBox.removeAllItems();
		String[] todasEditoras = new String[EditoraManager.getInstance().getEditoras().size()];
		for(int i = 0; i < EditoraManager.getInstance().getEditoras().size(); i++){
			todasEditoras[i] = EditoraManager.getInstance().getEditoras().get(i).getNome();
			comboBox.addItem(todasEditoras[i]);
		}

		String editora = "";
		if(EditoraManager.getInstance().getEditoras().isEmpty() == false)
			editora  = EditoraManager.getInstance().getEditoras().get(0).getNome();
		
		EstoqueManager.getInstance().getLivrosDoBancoDeDados();
		
		this.repintarTabela(editora);
	}
	
	private void fazerAcao(JTextField[] textFields, JTable table, Acao acao){
		String[] camposEmTexto = servicoDeDigito.transformarCamposEmTexto(textFields);
		camposEmTexto[camposEmTexto.length - 1] =  camposEmTexto[camposEmTexto.length - 1].replace(',', '.');
		
		if(acao == Acao.ADICIONAR){
			Livro livro = new Livro(camposEmTexto);
			//livro.setId(EstoqueManager.getInstance().getLivros().size());

			if(livro.isValidLivroSemId()){
				JOptionPane.showMessageDialog(this, "Novo livro adicionado: " + livro.getNome(), "Adicionado com sucesso!", JOptionPane.INFORMATION_MESSAGE);

				//EstoqueManager.getInstance().adicionarNovoLivro(livro);
				EstoqueManager.getInstance().getOperacoes().INSERT_DATA(livro);
				EstoqueManager.getInstance().getLivrosDoBancoDeDados();

				servicoDeDigito.limparCampos(textFields, Field.EDITORA.index);

                this.repintarTabela(comboBox.getSelectedItem().toString());
			}
			else{
				JOptionPane.showMessageDialog(this, "Preencha todos os campos com informações válidas.","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}	
		}
		else if(acao == Acao.ATUALIZAR){
			int id = -1;
			id = table.getSelectedRow();

            if(id == -1 || id >= table.getRowCount()){
                JOptionPane.showMessageDialog(this, "Selecione uma escola para ser atualizada.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
                return;
            }

            Object[][] data = ((MyTableModel)table.getModel()).getData();
            int idReal = (int)data[id][0];

            Livro novoLivro = new Livro(camposEmTexto);
            Livro livro = EstoqueManager.getInstance().getLivroPeloId(idReal);
            Livro livroASerAdicionado = livro;

            //Checando nome
            if(novoLivro.getNome().length() > 0)
                livroASerAdicionado.setNome(novoLivro.getNome());

            //Checando quantidade
            if(novoLivro.getQuantidade() != livro.getQuantidade()){
                livroASerAdicionado.setComprar(livro.getComprar() - (novoLivro.getQuantidade()));
            }

            if(camposEmTexto[Field.QUANTIDADE.index].length() > 0)
                livroASerAdicionado.setQuantidade(novoLivro.getQuantidade());

            //Checando preço
            if(camposEmTexto[Field.PRECO.index].length() > 0)
                livroASerAdicionado.setPreco(novoLivro.getPreco());

            EstoqueManager.getInstance().getOperacoes().UPDATE_DATA(livroASerAdicionado);
            EstoqueManager.getInstance().getLivrosDoBancoDeDados();
            //servicoDeDigito.limparCampos(textFields);

            JOptionPane.showMessageDialog(this, "Livro Atualizado: " + livroASerAdicionado.getNome(),"Atualizado com sucesso!", JOptionPane.INFORMATION_MESSAGE);

            this.repintarTabela(comboBox.getSelectedItem().toString());
		}
	}
	
	private void repintarTabela(String editora, Ordenar ordenar){
		if(this.table != null){
			((MyTableModel)this.table.getModel()).updateData(editora, ordenar);
			this.table.repaint();
		}
	}
	
	private void repintarTabela(String editora){
		if(this.table != null){
			((MyTableModel)this.table.getModel()).updateData(editora);
			this.table.repaint();
		}
	}
	
	 private boolean isNumber(String text) {
	      try {
	         Float.parseFloat(text);
	         return true;
	      } catch (NumberFormatException e) {
	         return false;
	      }
	   }
	
	private void atualizarLivros(JTable table, JComboBox comboBox, JTextField textField){	
		try{
			if(comboBox != null){
				this.repintarTabela(comboBox.getSelectedItem().toString());
				textField.setText(comboBox.getSelectedItem().toString());
			}
			
		}
		catch(java.lang.NullPointerException e){
			
		}
	}
}



class MyTableModel extends AbstractTableModel {
	
	private static Boolean DEBUG;
	
    private String[] columnNames = {"ID",
                                    "NOME",
                                    "EDITORA",
                                    "QUANTIDADE",
                                    "COMPRAR",
                                    "VENDIDOS",
                                    "PREÇO"};
   
    private Object[][] data;
    private Ordenar lastOrdem = Ordenar.NUMERO;
    
    public Object[][] getData(){
    	return data;
    }
    public MyTableModel(String editora){
    	updateData(editora, Ordenar.NUMERO);
    }
    
    public void updateData(String editora, Ordenar ordenar){
    	data = new Object[EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).get(i).pegarTodosParametros();
    	}	
    	
    	lastOrdem = ordenar;
    	ordenar(editora);
    }
    
    public void updateData(String editora){
    	data = new Object[EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).get(i).pegarTodosParametros();
    	}	
    	
    	ordenar(editora);
    }
    
    private void ordenar(String editora){
    	if(lastOrdem == Ordenar.NOME){
    		for(int i = 0; i < data.length; i++){
    			ArrayList<Livro> livrosOrdenados= (ArrayList<Livro>) EstoqueManager.getInstance().getLivrosDeUmaEditora(editora);
    			NomeLivroComparator nLC = new NomeLivroComparator();
        		Collections.sort(livrosOrdenados, nLC);
        		data[i] = livrosOrdenados.get(i).pegarTodosParametros();
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
