package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import principais.Editora;
import principais.EstoqueManager;
import principais.Livro;

public class TelaEstoque extends JPanel {
	
	private GUIManager guiManager;
	
	public TelaEstoque(GUIManager guiManager) {
		this.guiManager = guiManager;
		
		this.setLayout(null);
		this.add(new TextTitle("Estoque"));
		
		JTable table = new JTable(new MyTableModel(Editora.ED_ATICA));
		table.setBounds(1366/2 - table.getPreferredSize().width/2 ,300, 500, 500);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(497);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);
		table.getColumnModel().getColumn(4).setPreferredWidth(75);
		table.getColumnModel().getColumn(5).setPreferredWidth(75);		
		//table.setDefaultRenderer(Boolean.class, new CustomCellRenderer());
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scrollPane.setSize(1000, 400);
		scrollPane.setLocation(1366/2 - scrollPane.getSize().width/2 ,180);
		this.add(scrollPane);
		

		String[] columns = {"ID", "NOME", "EDITORA", "QTD",  "COMPRAR", "PREÇO"};
		JLabel[] labels = new JLabel[columns.length];
		JTextField[] textFields = new JTextField[columns.length];
		
		for(int i = 0; i < labels.length; i++){
			labels[i] = new JLabel(columns[i]);
			labels[i].setSize(100, 30);
			labels[i].setFont(labels[i].getFont().deriveFont(5));
			
			textFields[i] = new JTextField();
			textFields[i].setSize(50, 30);
			
			this.add(textFields[i]);
			this.add(labels[i]);	
		}
		
		labels[0].setLocation(200, 590);
		textFields[0].setLocation(185,610 );
		
		textFields[1].setSize(497, 30);
		textFields[1].setLocation(270 ,610 );
		labels[1].setLocation(480 ,590 );

		textFields[2].setSize(200, 30);
		textFields[2].setLocation(775 ,610 );
		textFields[2].setEditable(false);
		labels[2].setLocation(825 ,590 );
		
		textFields[3].setLocation(993 ,610 );
		labels[3].setLocation(998 ,590 );
		
		textFields[4].setLocation(1063 ,610 );
		labels[4].setLocation(1058 ,590 );
		
		textFields[5].setLocation(1133 ,610 );
		labels[5].setLocation(1133 ,590 );

		
		String[] todasEditoras = new String[Editora.values().length];
		for(int i = 0; i < todasEditoras.length; i++){
			todasEditoras[i] = Editora.values()[i].getNome();
		}
		JComboBox comboBox = new JComboBox(todasEditoras);
		comboBox.setSelectedItem(0);
		comboBox.setBounds(1366/2 - 200, 110, 400, 40);
		comboBox.setFont(comboBox.getFont().deriveFont(20.0f));
		
		ActionListener verEscolaSelecionada = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				atualizarLivros(table, comboBox, textFields[2]);
			}
		};
		comboBox.addActionListener(verEscolaSelecionada);
		this.add(comboBox);
		atualizarLivros(table, comboBox, textFields[2]);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setLocation(30, 680);
		btnVoltar.setSize(200, 50);
		ActionListener irParaTelaInicial = new ActionListener() {
			@Override
			  public void actionPerformed(ActionEvent e) {
			    guiManager.mudarParaTela("telaInicial");
			  }
			};
		btnVoltar.addActionListener(irParaTelaInicial);
		this.add(btnVoltar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setLocation(1124, 680);
		btnSalvar.setSize(200, 50);
		ActionListener salvar = new ActionListener() {
			@Override
			  public void actionPerformed(ActionEvent e) {
			    /*TODO pega as informações do EstoqueManager e salva no Banco de Dados
			     * 
			     * 
			     */
			  }
			};
		btnSalvar.addActionListener(salvar);
		this.add(btnSalvar);
		
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setLocation(1366/2 - 100, 670);
		btnAdicionar.setSize(200, 50);
		ActionListener adicionarOuAtualizar = new ActionListener() {
			@Override
			  public void actionPerformed(ActionEvent e) {
				if(textFields[0].getText().length() > 0){
					if(checarCampos(textFields, labels, true)){
						atualizarUmLivro(textFields, comboBox, table);
					}
				}
				else{
					if(checarCampos(textFields, labels, false)){
						adicionarLivro(textFields, comboBox, table);
					}
				}
				
			  }
			};
		btnAdicionar.addActionListener(adicionarOuAtualizar);
		this.add(btnAdicionar);
		
		
		this.guiManager.getCards().add(this, "telaRegistrarLivro");
	}
	
	private void adicionarLivro(JTextField[] textFields, JComboBox comboBox, JTable table){
		Editora editora = Editora.getEditoraDeUmaString(comboBox.getSelectedItem().toString());
		int id = EstoqueManager.getInstance().gerarId(editora);//5;//Integer.parseInt(textFields[0].getText());
		String nome = textFields[1].getText();
		int quantidade = Integer.parseInt(textFields[3].getText());
		int comprar = Integer.parseInt(textFields[4].getText());
		double preco = Float.parseFloat(textFields[5].getText());
		Livro livro = new Livro(id, nome, editora, quantidade, comprar, preco);
		EstoqueManager.getInstance().getLivros().add(livro);
		
		atualizarLivros(table, comboBox, null);
	}
	
	private void atualizarUmLivro(JTextField[] textFields, JComboBox comboBox, JTable table){
		Editora editora = Editora.getEditoraDeUmaString(comboBox.getSelectedItem().toString());
		int id = Integer.parseInt(textFields[0].getText());
		String nome = textFields[1].getText();
		int quantidade = Integer.parseInt(textFields[3].getText());
		int comprar = Integer.parseInt(textFields[4].getText());
		double preco = Float.parseFloat(textFields[5].getText());
		
		for(int i = 0; i < EstoqueManager.getInstance().getLivros().size(); i++){
			if((int)id == EstoqueManager.getInstance().getLivros().get(i).getId()){
				 Livro livro = EstoqueManager.getInstance().getLivros().get(i);
				 livro.setNome(nome);
				 livro.setQuantidade(quantidade);
				 livro.setPreco(preco);
				 livro.setComprar(comprar);
				 EstoqueManager.getInstance().getLivros().set(i, livro);
			}
		}
		atualizarLivros(table, comboBox, null);
	}
	
	private boolean checarCampos(JTextField[] textFields, JLabel[] labels, Boolean checarId){
		Boolean todosCamposPreenchidosCorretamente = true;
		for(int i = 0; i < textFields.length; i ++){
			if(textFields[i].getText().length() == 0){
				if(checarId == true || checarId == false && i != 0){
					todosCamposPreenchidosCorretamente = false;
					JOptionPane.showMessageDialog(this, "O campo: " + labels[i].getText() + "\n não contém informações válidas!" , "Falha", JOptionPane.INFORMATION_MESSAGE);
					break;	
				}
			}
			if(i != 1 && i != 2){ 
				if(isNumber(textFields[i].getText()) == false){
					if(checarId == true || checarId == false && i != 0){
						todosCamposPreenchidosCorretamente = false;
						JOptionPane.showMessageDialog(this, "O campo: " + labels[i].getText() + "\n não contém informações válidas!" , "Falha", JOptionPane.INFORMATION_MESSAGE);
						break;
					}
				}
			}
		}
		
		return todosCamposPreenchidosCorretamente;
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
		Editora editora = Editora.getEditoraDeUmaString(comboBox.getSelectedItem().toString());
		((MyTableModel)table.getModel()).updateData(editora);
		if(textField != null)
		textField.setText(editora.getNome());
		table.repaint();
	}
}



class MyTableModel extends AbstractTableModel {
	
	private static Boolean DEBUG;
	
    private String[] columnNames = {"ID",
                                    "NOME",
                                    "EDITORA",
                                    "QUANTIDADE",
                                    "COMPRAR",
                                    "PREÇO"};
   
    //Aqui eu tenho que pegar todos os livros do EstoqueManager(que acessa o BD de livros por sua vez)
    private Object[][] data;
    
    public MyTableModel(Editora editora){
    	data = new Object[EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).get(i).pegarTodosParametros();
    	}
    }
    
    public void updateData(Editora editora){
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
