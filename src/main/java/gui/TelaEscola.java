package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import principais.Editora;
import principais.EditoraManager;
import principais.Escola;
import principais.EscolaManager;
import principais.NomeEditoraComparator;
import principais.NomeEscolaComparator;
import utilidades.Acao;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaEscola extends JPanel implements IPrepararComponentes {

	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	private JTable table;
	
	public TelaEscola(GUIManager guiManager){
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
		int posAtual = 11;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = posAtual;
		c.gridwidth = 3;
		this.add(fieldName, c);
		
		JLabel labelName = new JLabel("NOME");
		posAtual = 11;
		c.gridy = 17;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridwidth = 3;
		c.gridx = posAtual;
		this.add(labelName, c);

		JTextField fieldId = new JTextField();
		c.gridy = 18;
		posAtual = 10;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = posAtual;
		c.gridwidth = 1;
		fieldId.setEditable(false);
		fieldId.setBackground(Color.lightGray);
		this.add(fieldId, c);

		JLabel labelId = new JLabel("  ID");
		posAtual = 10;
		c.gridy = 17;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridwidth = 1;
		c.gridx = posAtual;
		this.add(labelId, c);

        JLabel txt_Title = new JLabel("ESCOLAS", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		table = new JTable(new MyTableModelEscola());
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		DefaultTableCellRenderer left = new DefaultTableCellRenderer();
		left.setHorizontalAlignment(SwingConstants.LEFT);
		table.getColumnModel().getColumn(0).setCellRenderer(left);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		c.gridx = 10;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 4;
		c.gridheight = 15;
		this.add(scrollPane, c);
		
		String[] acoes = {Acao.ADICIONAR.toString(), Acao.ATUALIZAR.toString(), Acao.REMOVER.toString()};
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
		c.gridwidth = 3;
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

				if(acao == Acao.ADICIONAR) {
					fieldName.setText("");
					fieldId.setText("");
				}
				else if (table.getSelectedRow() != -1) {
					fieldId.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
					fieldName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
				}
			}
		});
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBox.getSelectedItem().toString());
				fazerAcao(fieldId, fieldName, table, acao);
				//fieldName.setText("");
			}
		});
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fieldName.setText("");
				fieldId.setText("");
				guiManager.mudarParaTela("telaInicial");
			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String comboBoxValue = comboBox.getSelectedItem().toString();
				if(comboBoxValue.equalsIgnoreCase(Acao.ATUALIZAR.toString()) ||
					comboBoxValue.equalsIgnoreCase(Acao.REMOVER.toString())) {

					if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
						fieldId.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
						fieldName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
					}
					else{
						fieldId.setText("");
						fieldName.setText("");
					}
				}
			}
		});
	}

	
	private void fazerAcao(JTextField textFieldId, JTextField textField, JTable table, Acao acao) throws ArrayIndexOutOfBoundsException {
		String text = textField.getText();
		
		if(acao == Acao.ADICIONAR){

			if(text.length() == 0) {
				JOptionPane.showMessageDialog(this, "O campo nome encontra-se vazio.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
				return;
			}


			Escola escola = new Escola(text);
			//EscolaManager.getInstance().adicionarNovaEscola(escola);
			EscolaManager.getInstance().getOperacoes().INSERT_DATA(escola);
			EscolaManager.getInstance().getTodasEscolasDoBD();

			textField.setText("");
			JOptionPane.showMessageDialog(this, "Nova escola: " + text,"Adicionado com sucesso!", JOptionPane.INFORMATION_MESSAGE);

			this.repintarTabela();
		}
		else if (acao == Acao.ATUALIZAR){

			if(text.length() == 0) {
				JOptionPane.showMessageDialog(this, "O campo nome encontra-se vazio.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
				return;
			}

			int id = -1;
			id = table.getSelectedRow();
			if(id == -1 || id >= table.getRowCount()){
				JOptionPane.showMessageDialog(this, "Selecione uma escola para ser atualizada.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
				return;
			}
			id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
			
			Escola velhaEscola = EscolaManager.getInstance().getEscolaPeloId(id);
			Escola novaEscola = new Escola(text);	

			
			novaEscola.setId(velhaEscola.getId());
			EscolaManager.getInstance().getOperacoes().UPDATE_DATA(novaEscola);
			EscolaManager.getInstance().getTodasEscolasDoBD();

			String mensage = "";
			mensage = mensage.concat("Escola atualizada: " + novaEscola.getNome());
			JOptionPane.showMessageDialog(this, mensage	,"Atualização concluída!", JOptionPane.INFORMATION_MESSAGE);

			this.repintarTabela();

			if (table.getSelectedRow() != -1 && table.getSelectedRow() < table.getRowCount()) {
				textFieldId.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				textField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
			}
			else{
				textFieldId.setText("");
				textField.setText("");
			}
		}
		else if(acao == Acao.REMOVER){

			int id = -1;
			id = table.getSelectedRow();
			if(id == -1 && id < table.getRowCount()){
				JOptionPane.showMessageDialog(this, "Selecione uma escola para ser removida.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
				return;
			}
			id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());

			Escola velhaEscola = EscolaManager.getInstance().getEscolaPeloId(id);
			Escola novaEscola = new Escola("");

			novaEscola.setId(velhaEscola.getId());
			EscolaManager.getInstance().getOperacoes().UPDATE_DATA(novaEscola);
			EscolaManager.getInstance().getTodasEscolasDoBD();

			String mensage = "";
			mensage = mensage.concat("Escola removida: " + velhaEscola.getNome());
			JOptionPane.showMessageDialog(this, mensage	,"Remoção concluída!", JOptionPane.INFORMATION_MESSAGE);


			this.repintarTabela();

			if (table.getSelectedRow() != -1 && table.getSelectedRow() < table.getRowCount()) {
				textFieldId.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
				textField.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
			}
			else{
				textFieldId.setText("");
				textField.setText("");
			}
		}
	}
	
	@Override
	public void prepararComponentes(){
		EscolaManager.getInstance().getTodasEscolasDoBD();
		this.repintarTabela();
	}
	
	public void repintarTabela(){
		if(table != null){
			((MyTableModelEscola)table.getModel()).updateData();
			table.repaint();
		}
	}
}



class MyTableModelEscola extends AbstractTableModel {
	
	private static Boolean DEBUG;
	
    private String[] columnNames = {"ID",
                                    "NOME"};
   
    private Object[][] data;
    
    public MyTableModelEscola(){
    	updateData();
    }
    
    public void updateData(){

		data = new Object[EscolaManager.getInstance().getEscolas().size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = EscolaManager.getInstance().getEscolas().get(i).pegarTodosParametros();
    	}
    	
    	ordenar();
    }
    
    private void ordenar()
    {
    	for(int i = 0; i < data.length; i++){
			ArrayList<Escola> escolasOrdenadas= (ArrayList<Escola>) EscolaManager.getInstance().getEscolas();
			NomeEscolaComparator nEC = new NomeEscolaComparator();
    		Collections.sort(escolasOrdenadas, nEC);
    		data[i] = escolasOrdenadas.get(i).pegarTodosParametros();
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


