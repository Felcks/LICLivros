package gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
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
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import bd.JavaConnection;
import bd.OperacoesLivros;
import principais.Editora;
import principais.EditoraManager;
import principais.EstoqueManager;
import principais.Livro;
import utilidades.Acao;
import utilidades.Screen;

public class TelaEstoque extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	JTable table;
	JComboBox comboBox;
	
	public TelaEstoque(GUIManager guiManager) {
		this.guiManager = guiManager;
		
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
        
        JTextField[] textFields = new JTextField[6];
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridy = 7;
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = i ;
			this.add(textFields[i],c);
		}
		
		String[] columnNames = {"ID", "NOME", "EDITORA", "QUANTIDADE", "COMPRAR", "PREÇO"};
		JLabel[] labels = new JLabel[7];
		c.gridy = 6;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = i ;
			this.add(labels[i],c);
		}
        
        JLabel txt_Title = new JLabel("ESTOQUE", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		String[] todasEditoras = new String[EditoraManager.getInstance().getEditoras().size()];
		for(int i = 0; i < EditoraManager.getInstance().getEditoras().size(); i++){
			todasEditoras[i] = EditoraManager.getInstance().getEditoras().get(i).getNome();
		}
		comboBox = new JComboBox(todasEditoras);
		c.gridx = 5;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(comboBox,c);
		
		this.table = new JTable(new MyTableModel(""));
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
		
		
		String[] acoes = new String[Acao.values().length - 1];
		for(int i = 0; i < acoes.length; i++)
			acoes[i] = Acao.values()[i].name();
		JComboBox comboBoxAcoes = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 8;
		this.add(comboBoxAcoes, c);
		
		JButton btn_fazerAcao = new JButton("Fazer Ação!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 3;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_fazerAcao, c);
		
		JButton btn_Salvar = new JButton("Salvar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 5;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Salvar, c);
		
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
				atualizarLivros(table, comboBox, textFields[2]);
			}
		});

		//table.setDefaultRenderer(Boolean.class, new CustomCellRenderer());		
		
		this.guiManager.getCards().add(this, "telaRegistrarLivro");
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
		((MyTableModel)this.table.getModel()).updateData(editora);
		this.table.repaint();
	}
	
	private void adicionarLivro(JTextField[] textFields, JComboBox comboBox, JTable table){
		System.out.println("passar em TelaEstoque linha 300");
		//Editora editora = Editora.getEditoraDeUmaString(comboBox.getSelectedItem().toString());
		//int id = EstoqueManager.getInstance().gerarId(editora);//5;//Integer.parseInt(textFields[0].getText());
		int id = 1;
		String nome = textFields[1].getText();
		String editora = textFields[2].getText();
		int quantidade = Integer.parseInt(textFields[3].getText());
		int comprar = Integer.parseInt(textFields[4].getText());
		double preco = Float.parseFloat(textFields[5].getText());
		Livro livro = new Livro(id, nome, editora, quantidade, comprar, preco);
		
		OperacoesLivros op = new OperacoesLivros();
		op.INSERT_LIVROS(livro);
		EstoqueManager.getInstance().getLivrosDoBancoDeDados();
		
		atualizarLivros(table, comboBox, null);
	}
	
	private void atualizarUmLivro(JTextField[] textFields, JComboBox comboBox, JTable table){
		//Editora editora = Editora.getEditoraDeUmaString(comboBox.getSelectedItem().toString());
		
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
				 OperacoesLivros op = new OperacoesLivros();
				 op.UPADTE_LIVROS(livro);
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
		//Editora editora = Editora.getEditoraDeUmaString(comboBox.getSelectedItem().toString());
		
		((MyTableModel)table.getModel()).updateData("");
		//if(textField != null)
		//	textField.setText(editora.getNome());
		
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
