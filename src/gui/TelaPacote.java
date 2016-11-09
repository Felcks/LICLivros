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

import principais.Escola;
import principais.EstoqueManager;
import principais.Livro;
import principais.PacoteManager;
import principais.AnoEscolar;
import principais.Editora;

public class TelaPacote extends JPanel {
	private GUIManager guiManager;
	private Escola escola = Escola.ESCOLA_1;
	private AnoEscolar anoEscolar = AnoEscolar.JARDIM_1;
	
	public TelaPacote(GUIManager guiManager){
		this.guiManager = guiManager;
		
		this.setLayout(null);
		this.add(new TextTitle("Pacotes de Livros"));
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setLocation(30, 680);
		btnVoltar.setSize(200, 50);
		btnVoltar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		this.add(btnVoltar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setLocation(1124, 680);
		btnSalvar.setSize(200, 50);
		ActionListener salvar = new ActionListener() {
			@Override
			  public void actionPerformed(ActionEvent e) {
			    /*TODO pega as informações do pacoteManager e joga no banco de dados
			     * 
			     * 
			     */
			  }
			};
		btnSalvar.addActionListener(salvar);
		this.add(btnSalvar);
		
		JTable table = new JTable(new TableModelPacote(Escola.ESCOLA_1, AnoEscolar.JARDIM_1));
		table.setBounds(1366/2 - table.getPreferredSize().width/2 ,250, 500, 500);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(1).setPreferredWidth(497);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);	
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scrollPane.setSize(850, 400);
		scrollPane.setLocation(1366/2 - scrollPane.getSize().width/2 ,200);
		this.add(scrollPane);
		
		String[] todosAnos = new String[AnoEscolar.values().length];
		for(int i = 0; i < todosAnos.length; i++){
			todosAnos[i] = AnoEscolar.values()[i].getNome();
		}
		JComboBox comboBoxAnos = new JComboBox(todosAnos);
		comboBoxAnos.setSelectedItem(0);
		comboBoxAnos.setBounds(1366/2 - 200, 155, 400, 40);
		comboBoxAnos.setFont(comboBoxAnos.getFont().deriveFont(20.0f));
		comboBoxAnos.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				anoEscolar = AnoEscolar.getAnoEscolarDeUmaString(comboBoxAnos.getSelectedItem().toString());
				((TableModelPacote)table.getModel()).updateData(escola, anoEscolar);
				table.repaint();
			}
		});
		this.add(comboBoxAnos);
		
		String[] todasEscolas = new String[Escola.values().length];
		for(int i = 0; i < todasEscolas.length; i++){
			todasEscolas[i] = Escola.values()[i].getNome();
		}
		JComboBox comboBox = new JComboBox(todasEscolas);
		comboBox.setSelectedItem(0);
		comboBox.setBounds(1366/2 - 200, 110, 400, 40);
		comboBox.setFont(comboBox.getFont().deriveFont(20.0f));
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				escola = Escola.getEscolaDeUmaString(comboBox.getSelectedItem().toString());
				((TableModelPacote)table.getModel()).updateData(escola, anoEscolar);
				table.repaint();
			}
		});
		this.add(comboBox);
		
		
		JLabel label = new JLabel("NOME");
		JTextField textField = new JTextField();

		label.setLocation(1366/2 - 20, 620);
		label.setSize(300,30 );
		label.setFont(label.getFont().deriveFont(5));
		
		textField.setLocation(1366/2 - 200, 650);
		textField.setSize(400, 30);
		
		this.add(textField);
		this.add(label);
		
		TelaPacote telaPacote = this;
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setLocation(1366/2 - 100, 690);
		btnAdicionar.setSize(200, 50);
		ActionListener adicionarOuAtualizar = new ActionListener() {
			@Override
			  public void actionPerformed(ActionEvent e) {
				//Adiciona o livro digitado, se ao detectar o nome mostra JMessage bla bla
				Livro livroDesejado = EstoqueManager.getInstance().getLivroPeloNome(textField.getText());
				if(livroDesejado.getNome() == "LivroInexistente"){
					JOptionPane.showMessageDialog(telaPacote, "O Livro: " + textField.getText() + "\n não consta na base de dados" , "Livro não encontrado", JOptionPane.INFORMATION_MESSAGE);	
				}
				else {
					int livroAdicionado = PacoteManager.getInstance().getPacote(escola, anoEscolar).adicionarLivro(livroDesejado);
					if(livroAdicionado == -1)
						JOptionPane.showMessageDialog(telaPacote, "Esse livro já contem no pacote" , "Livro já adicionado", JOptionPane.INFORMATION_MESSAGE);	
					
					((TableModelPacote)table.getModel()).updateData(escola, anoEscolar);
					table.repaint();
				}
			  }
			};
		btnAdicionar.addActionListener(adicionarOuAtualizar);
		this.add(btnAdicionar);
		
		
		this.guiManager.getCards().add(this, "telaPacote");
	}
}

class TableModelPacote extends AbstractTableModel {
	
	private static Boolean DEBUG;
	
    private String[] columnNames = {"ID",
                                    "NOME",
                                    "EDITORA",
                                    "PREÇO"};
   
    //Aqui eu tenho que pegar todos os livros do EstoqueManager(que acessa o BD de livros por sua vez)
    private Object[][] data;
    
    public TableModelPacote(Escola escola, AnoEscolar anoEscolar){
    	/*data = new Object[EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = EstoqueManager.getInstance().getLivrosDeUmaEditora(editora).get(i).pegarTodosParametros();
    	}*/
    	
    	data = new Object[PacoteManager.getInstance().getPacote(escola, anoEscolar).getLivros().size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = PacoteManager.getInstance().getPacote(escola, anoEscolar).getLivros().get(i).pegarParametrosDePacote();
    	}
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

