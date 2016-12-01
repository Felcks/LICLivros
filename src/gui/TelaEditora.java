package gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import bd.OperacoesClientes;
import bd.OperacoesEditoras;
import principais.Cliente;
import principais.ClienteManager;
import principais.Editora;
import principais.EditoraManager;
import utilidades.Acao;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaEditora extends JPanel implements IPrepararComponentes{

	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	private JTable table;
	
	public TelaEditora(GUIManager guiManager){
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 7; i ++){
        	for(int j = 0; j < 10; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
     
		JTextField[] textFields = new JTextField[2];
		c.gridy = 7;
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = i + 2 ;
			this.add(textFields[i],c);
			c.gridwidth = 2;
		}
		
		String[] columnNames = {"ID", "NOME"};
		JLabel[] labels = new JLabel[2];
		c.gridy = 6;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = i + 2;
			this.add(labels[i],c);
			c.gridwidth = 2;
		}
		

        JLabel txt_Title = new JLabel("EDITORAS", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 7;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		table = new JTable(new MyTableModelEditora());
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth =5;
		c.gridheight = 5;
		this.add(scrollPane, c);
		
		String[] acoes = new String[Acao.values().length - 1];
		for(int i = 0; i < acoes.length; i++)
			acoes[i] = Acao.values()[i].name();
		JComboBox comboBox = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 8;
		this.add(comboBox, c);
		
		JButton btn_fazerAcao = new JButton("Fazer Ação!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 3;
		c.gridy = 8;
		c.gridwidth = 2;
		this.add(btn_fazerAcao, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
		
		JButton btn_Salvar = new JButton("Salvar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 6;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Salvar, c);
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBox.getSelectedItem().toString());
				fazerAcao(textFields, table, acao);
			}
		});
		
		btn_Salvar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				OperacoesEditoras operacoesEditoras = new OperacoesEditoras();
				operacoesEditoras.INSERT_TODOSCLIENTES(EditoraManager.getInstance().getEditoras());
			}
		});
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		

		this.guiManager.getCards().add(this);
	}
	
	private void fazerAcao(JTextField[] textFields, JTable table, Acao acao) throws ArrayIndexOutOfBoundsException {
		//AQUI PEGAMOS TODOS OS TEXTOS DOS CAMPOS E ORDENAMOS A LISTA EM ORDEM NUMERAL ANTES DE QUALQUER AÇÃO
		String[] camposEmTexto = servicoDeDigito.transformarCamposEmTexto(textFields);
		
		if(acao == Acao.ADICIONAR){
			Editora editora = new Editora(EditoraManager.getInstance().getEditoras().size(), camposEmTexto[1]);
			JOptionPane.showConfirmDialog(this, "Confirmar a adição da editora: " + editora.getNome() + "?", "Confirmar Adição", JOptionPane.OK_CANCEL_OPTION);
			EditoraManager.getInstance().adicionarNovaEditora(editora);
			this.repintarTabela();
		}
		else if(acao == Acao.REMOVER){
			String idSelecionado = camposEmTexto[0];
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			if(id >= 0 && id < EditoraManager.getInstance().getEditoras().size()){
				Editora editora = EditoraManager.getInstance().getEditoras().get(id);
				JOptionPane.showConfirmDialog(this, "Remover editora: " + editora.getNome() + "?", "Confirmar Remoção", JOptionPane.OK_CANCEL_OPTION);
				EditoraManager.getInstance().removerEditora(id);
				EditoraManager.getInstance().reorganizarLista();
				this.repintarTabela();
			}
		}
		else if (acao == Acao.ATUALIZAR){
			String idSelecionado = camposEmTexto[0];
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			if(id >= 0 && id < EditoraManager.getInstance().getEditoras().size()){
				Editora velhaEditora = EditoraManager.getInstance().getEditoras().get(id);
				Editora novaEditora = new Editora(camposEmTexto[1]);
				
				String mensage = "";
				if(novaEditora.getNome().toString().length() > 0){
					mensage = mensage.concat(velhaEditora.getNome() + " ---> " + novaEditora.getNome() + "\n");
				}
				
				if(mensage.length() > 0){
					JOptionPane.showConfirmDialog(this, mensage	,"Atualizacao", JOptionPane.OK_CANCEL_OPTION);
					novaEditora.setId(velhaEditora.getId());
					EditoraManager.getInstance().atualizarEditora(id, novaEditora);
					this.repintarTabela();
				}
				else
					JOptionPane.showMessageDialog(this, "Não há informação a ser atualizada","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
			}
		}
		else{
			JOptionPane.showMessageDialog(this, "Id inexistente!","Erro ao remover", JOptionPane.OK_CANCEL_OPTION);
		}
			

	}
	
	@Override
	public void prepararComponentes(){
		EditoraManager.getInstance().getTodasEditorasDoBD();
		this.repintarTabela();
	}
	
	public void repintarTabela(){
		if(table != null){
			((MyTableModelEditora)table.getModel()).updateData();
			table.repaint();
		}
	}
}


class MyTableModelEditora extends AbstractTableModel {
	
	private static Boolean DEBUG;
	
    private String[] columnNames = {"ID",
                                    "NOME"};
   
    private Object[][] data;
    
    public MyTableModelEditora(){
    	updateData();
    }
    
    public void updateData(){
    	System.out.println("estou no updateData " + EditoraManager.getInstance().getEditoras().size() );
    	data = new Object[EditoraManager.getInstance().getEditoras().size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = EditoraManager.getInstance().getEditoras().get(i).pegarTodosParametros();
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

    public Object getValueAt(int row, int col) throws NullPointerException {
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

