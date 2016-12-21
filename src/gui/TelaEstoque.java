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

import bd.JavaConnection;
import bd.OperacoesClientes;
import bd.OperacoesLivros;
import principais.Cliente;
import principais.ClienteManager;
import principais.Editora;
import principais.EditoraManager;
import principais.EstoqueManager;
import principais.Livro;
import utilidades.Acao;
import utilidades.Screen;

public class TelaEstoque extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	JTable table;
	JComboBox comboBox;
	JComboBox comboBoxAcoes;
	JTextField[] textFields;
	 
	public TelaEstoque(GUIManager guiManager) {
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		 GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 24; i ++){
        	for(int j = 0; j < 10; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        textFields = new JTextField[7];
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 7;
		c.fill = GridBagConstraints.HORIZONTAL;
		int[] widthX = new int[] {1, 11, 4, 2, 2, 2, 2};
		int posAtual = 0;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = posAtual ;
			c.gridwidth = widthX[i];
			posAtual += widthX[i];
			c.anchor = GridBagConstraints.PAGE_START;
			if(i == 2){
				textFields[i].setEditable(false);
				textFields[i].setBackground(Color.lightGray);
			}
			
			this.add(textFields[i],c);
		}
		
		String[] columnNames = {"ID", "NOME", "EDITORA", "QUANTIDADE", "COMPRAR", "VENDIDOS", "PREÇO"};
		JLabel[] labels = new JLabel[7];
		c.gridy = 6;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		posAtual = 0;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = posAtual ;
			c.gridwidth = widthX[i];
			posAtual += widthX[i];
			this.add(labels[i],c);
		}
        
        JLabel txt_Title = new JLabel("ESTOQUE", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		String[] todasEditoras = new String[EditoraManager.getInstance().getEditoras().size()];
		for(int i = 0; i < EditoraManager.getInstance().getEditoras().size(); i++){
			todasEditoras[i] = EditoraManager.getInstance().getEditoras().get(i).getNome();
		}
		comboBox = new JComboBox(todasEditoras);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(comboBox,c);
		
		this.table = new JTable(new MyTableModel(""));
		JScrollPane scrollPane  = new JScrollPane(this.table);
		minimizarTamanhoDaColuna(table, 0, 40);
		minimizarTamanhoDaColuna(table, 2, 175);
		minimizarTamanhoDaColuna(table, 3, 90);
		minimizarTamanhoDaColuna(table, 4, 90);
		minimizarTamanhoDaColuna(table, 5, 90);
		minimizarTamanhoDaColuna(table, 6, 90);
		
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 24;
		c.gridheight = 5;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);
		
		
		String[] acoes = new String[2];
		acoes[0] = Acao.ADICIONAR.name();
		acoes[1] = Acao.ATUALIZAR.name();
		comboBoxAcoes = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 10;
		c.gridy = 8;
		this.add(comboBoxAcoes, c);
		
		JButton btn_fazerAcao = new JButton("Fazer Ação!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 12;
		c.gridy = 8;
		c.gridwidth = 4;
		c.gridheight = 1;
		this.add(btn_fazerAcao, c);
		
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 2;
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
				atualizarLivros(table, comboBox, textFields[2]);
			}
		});
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBoxAcoes.getSelectedItem().toString());
				fazerAcao(textFields, table, acao);
			}
		});	
		
		comboBoxAcoes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {;
				servicoDeDigito.limparCampos(textFields, 2);
				Acao acao = Acao.valueOf(comboBoxAcoes.getSelectedItem().toString());
				prepararParaAcao(acao, textFields);
			}
		});
		
		textFields[0].getDocument().addDocumentListener(new DocumentListener() {	
			@Override
			public void removeUpdate(DocumentEvent e) {
				checarId(textFields[0].getText(), textFields);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				checarId(textFields[0].getText(), textFields);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				checarId(textFields[0].getText(), textFields);
			}
		});
		
		prepararParaAcao(Acao.ADICIONAR, textFields);
		
		this.guiManager.getCards().add(this, "telaRegistrarLivro");
	}
	
	private void minimizarTamanhoDaColuna(JTable table, int index, int tam){
		table.getColumnModel().getColumn(index).setMinWidth(tam);
		table.getColumnModel().getColumn(index).setPreferredWidth(tam);
		table.getColumnModel().getColumn(index).setMaxWidth(tam);
		DefaultTableCellRenderer left = new DefaultTableCellRenderer();
		left.setHorizontalAlignment(SwingConstants.LEFT);
		table.getColumnModel().getColumn(index).setCellRenderer(left);
	}
	
	private void checarId(String text, JTextField[] textFields){
		Runnable runnable = new Runnable() {
			@Override
			public void run(){
				String idSelecionado = text;
				int id = -1;
				id = servicoDeDigito.transformarStringEmInt(idSelecionado);
				System.out.println(textFields[2].getText());
				ArrayList<Livro> livros = (ArrayList<Livro>) EstoqueManager.getInstance().getLivrosDeUmaEditora(textFields[2].getText());
				ArrayList<Integer> ids = new ArrayList<Integer>();
				for(int i = 0; i < livros.size(); i++)
					ids.add(livros.get(i).getId());

				System.out.println(ids.size());
				
				if(id >= 0 && id < EstoqueManager.getInstance().getLivros().size() && ids.contains(id)){
					Object[] params = EstoqueManager.getInstance().getLivroPeloId(id).pegarTodosParametros();
					for(int i = 1; i < textFields.length; i++){
						if(i != 2){
							textFields[i].setText(params[i].toString());
							textFields[i].setEditable(true);
							textFields[i].setBackground(Color.WHITE);
						}
					}
				}
				else{
					servicoDeDigito.limparCampos(textFields, 2);
					prepararParaAcao(Acao.ATUALIZAR, textFields);
				}
			}
		};
		
		SwingUtilities.invokeLater(runnable);
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
	
	private void prepararParaAcao(Acao acao, JTextField[] textFields){
		if(acao == Acao.ADICIONAR){
			textFields[0].setEditable(false);
			textFields[0].setBackground(Color.lightGray);
			
			for(int i = 1; i < textFields.length; i++){
				if(i != 2){
					textFields[i].setEditable(true);
					textFields[i].setBackground(Color.WHITE);
				}
			}
		}
		else if(acao == Acao.ATUALIZAR){
			textFields[0].setEditable(true);
			textFields[0].setBackground(Color.WHITE);
			
			for(int i = 1; i < textFields.length; i++){
				if(i != 2){
					textFields[i].setEditable(false);
					textFields[i].setBackground(Color.lightGray);
				}
			}
		}
	}
	
	private void fazerAcao(JTextField[] textFields, JTable table, Acao acao){
		String[] camposEmTexto = servicoDeDigito.transformarCamposEmTexto(textFields);
		
		if(acao == Acao.ADICIONAR){
			Livro livro = new Livro(camposEmTexto);
			livro.setId(EstoqueManager.getInstance().getLivros().size());
			if(livro.isValidLivro()){
				JOptionPane.showConfirmDialog(this, "Confirmar a adição do livro: " + livro.getNome(), "Confirmar Adição", JOptionPane.OK_CANCEL_OPTION);
				EstoqueManager.getInstance().adicionarNovoLivro(livro);
				this.repintarTabela(comboBox.getSelectedItem().toString());
				EstoqueManager.getInstance().getOperacoes().INSERT_DATA(livro);
			}
			else{
				JOptionPane.showMessageDialog(this, "Preencha todos os campos com informações válidas","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}	
		}
		else if(acao == Acao.ATUALIZAR){
			String idSelecionado = camposEmTexto[0];
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			if(id >= 0 && id < EstoqueManager.getInstance().getLivros().size()){
				Livro novoLivro = new Livro(camposEmTexto);
				Livro livro = EstoqueManager.getInstance().getLivroPeloId(id);
				Livro livroASerAdicionado = livro;
				
				String mensage = "";
				Object[] parametrosDoNovoLivro = novoLivro.pegarTodosParametrosValidos();
				Object[] parametrosDoLivroASerAdicionado = livroASerAdicionado.pegarTodosParametros();
				String[] nomeParametros = { "ID", "NOME", "EDITORA", "QUANTIDADE", "COMPRAR", "VENDIDOS", "PREÇO"};
				
				
				for(int i = 1; i < camposEmTexto.length; i++){
					if(parametrosDoNovoLivro[i].toString().length() > 0 &&
							parametrosDoNovoLivro[i].equals(livro.pegarTodosParametros()[i]) == false){
						
						mensage = mensage.concat(nomeParametros[i] + ": " + livro.pegarTodosParametros()[i] + " ---> " 
												 + novoLivro.pegarTodosParametros()[i] + "\n");
						parametrosDoLivroASerAdicionado[i] = parametrosDoNovoLivro[i];
					}
				}
				if(mensage.length() > 0){
					JOptionPane.showConfirmDialog(this, mensage	,"Atualizacao", JOptionPane.OK_CANCEL_OPTION);
					livroASerAdicionado.setarTodosParametros(parametrosDoLivroASerAdicionado);
					EstoqueManager.getInstance().atualizarLivro(id, livroASerAdicionado);
					this.repintarTabela(comboBox.getSelectedItem().toString());
					EstoqueManager.getInstance().getOperacoes().UPDATE_DATA(livroASerAdicionado);
					servicoDeDigito.limparCampos(textFields);
				}
				else
					JOptionPane.showMessageDialog(this, "Não há informação a ser atualizada","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
		}
			else{
				JOptionPane.showMessageDialog(this, "Id Inexistente","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
			}
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
    
    public MyTableModel(String editora){
    	updateData(editora);
    }
    
    public void updateData(String editora){
    	data = new Object[EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).get(i).pegarTodosParametros();
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
        if (col < 2) {
            return false;
        } else {
            return true;
        }
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
