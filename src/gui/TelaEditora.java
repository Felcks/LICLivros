package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

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
		c.gridwidth = 4;
		this.add(fieldName, c);
		
		JLabel labelName = new JLabel("NOME");
		posAtual = 9;
		c.gridy = 17;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridwidth = 4;
		c.gridx = posAtual;
		this.add(labelName, c);
		

        JLabel txt_Title = new JLabel("EDITORAS", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		table = new JTable(new MyTableModelEditora());
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		DefaultTableCellRenderer left = new DefaultTableCellRenderer();
		left.setHorizontalAlignment(SwingConstants.LEFT);
		table.getColumnModel().getColumn(0).setCellRenderer(left);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		c.gridx = 9;
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 4;
		c.gridheight = 15;
		this.add(scrollPane, c);
		
		String[] acoes = {Acao.ADICIONAR.toString(), Acao.ATUALIZAR.toString()};
		JComboBox comboBox = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 10;
		c.gridy = 19;
		this.add(comboBox, c);
		
		JButton btn_fazerAcao = new JButton("Confirmar!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 11;
		c.gridy = 19;
		c.gridwidth = 1;
		this.add(btn_fazerAcao, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 20;
		c.gridwidth = 2;
		c.gridheight = 2;
		//this.add(btn_Voltar, c);
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Acao acao = Acao.valueOf(comboBox.getSelectedItem().toString());
				fieldName.setText("");
			}
		});
		
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBox.getSelectedItem().toString());
				fazerAcao(fieldName, table, acao);
				fieldName.setText("");
			}
		});
		
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fieldName.setText("");
				guiManager.mudarParaTela("telaInicial");
			}
		});	
	}

	
	private void fazerAcao(JTextField textField, JTable table, Acao acao) throws ArrayIndexOutOfBoundsException {
		String text = textField.getText();
		
		if(text.length() == 0)
		{
			JOptionPane.showMessageDialog(this, "O campo nome encontra-se vazio.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
			return;
		}
		
		if(acao == Acao.ADICIONAR){
			Editora editora = new Editora(EditoraManager.getInstance().getEditoras().size(), text);
			EditoraManager.getInstance().adicionarNovaEditora(editora);
			this.repintarTabela();
			EditoraManager.getInstance().getOperacoes().INSERT_DATA(editora);
			JOptionPane.showMessageDialog(this, "Nova editora: " + text,"Adicionado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (acao == Acao.ATUALIZAR){
			int id = -1;
			id = table.getSelectedRow();
			if(id == -1){
				JOptionPane.showMessageDialog(this, "Selecione uma editora para ser atualizada.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
				return;
			}
			
			Editora velhaEditora = EditoraManager.getInstance().getEditoras().get(id);
			Editora novaEditora = new Editora(text);	
			String mensage = "";
			mensage = mensage.concat("Editora atualizada: " + novaEditora.getNome());
			JOptionPane.showMessageDialog(this, mensage	,"Atualização concluída!", JOptionPane.INFORMATION_MESSAGE);
			
			novaEditora.setId(velhaEditora.getId());
			EditoraManager.getInstance().getOperacoes().UPDATE_DATA(novaEditora);
			EditoraManager.getInstance().atualizarEditora(id, novaEditora);
			this.repintarTabela();
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

