package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import bd.OperacoesEscolas;
import principais.Editora;
import principais.EditoraManager;
import principais.Escola;
import principais.EscolaManager;
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
        	for(int j = 0; j < 10; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
     
		JTextField[] textFields = new JTextField[2];
		int[] gridWidth = {1, 3};
		int posAtual = 9;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridy = 7;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = posAtual;
			c.gridwidth = gridWidth[i];
			posAtual += gridWidth[i];
			this.add(textFields[i],c);
		}
		
		String[] columnNames = {"ID", "NOME"};
		JLabel[] labels = new JLabel[2];
		posAtual = 9;
		c.gridy = 6;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = posAtual;
			c.gridwidth = gridWidth[i];
			posAtual += gridWidth[i];
			this.add(labels[i], c);
		}
		

        JLabel txt_Title = new JLabel("ESCOLAS", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 1;
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
		c.gridx = 9;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 4;
		c.gridheight = 5;
		this.add(scrollPane, c);
		
		String[] acoes = {Acao.ADICIONAR.toString(), Acao.ATUALIZAR.toString()};
		JComboBox comboBox = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 9;
		c.gridy = 8;
		this.add(comboBox, c);
		
		JButton btn_fazerAcao = new JButton("Fazer Ação!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 10;
		c.gridy = 8;
		c.gridwidth = 3;
		this.add(btn_fazerAcao, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
		
		prepararParaAcao(Acao.ADICIONAR, textFields);
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				servicoDeDigito.limparCampos(textFields);
				Acao acao = Acao.valueOf(comboBox.getSelectedItem().toString());
				prepararParaAcao(acao, textFields);
			}
		});
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBox.getSelectedItem().toString());
				fazerAcao(textFields, table, acao);
			}
		});
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiManager.mudarParaTela("telaInicial");
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
		

		this.guiManager.getCards().add(this);
	}
	
	private void checarId(String text, JTextField[] textFields){
		Runnable runnable = new Runnable() {
			@Override
			public void run(){
				String idSelecionado = text;
				int id = -1;
				id = servicoDeDigito.transformarStringEmInt(idSelecionado);
				if(id >= 0 && id < EscolaManager.getInstance().getEscolas().size()){
					Object[] params = EscolaManager.getInstance().getEscolaPeloId(id).pegarTodosParametros();
					for(int i = 1; i < 2; i++){
						textFields[i].setText(params[i].toString());
						textFields[i].setEditable(true);
						textFields[i].setBackground(Color.WHITE);
					}
				}
				else{
					servicoDeDigito.limparCampos(textFields);
					prepararParaAcao(Acao.ATUALIZAR, textFields);
				}
			}
		};
		
		SwingUtilities.invokeLater(runnable);
	}
	
	private void fazerAcao(JTextField[] textFields, JTable table, Acao acao) throws ArrayIndexOutOfBoundsException {
		//AQUI PEGAMOS TODOS OS TEXTOS DOS CAMPOS E ORDENAMOS A LISTA EM ORDEM NUMERAL ANTES DE QUALQUER AÇÃO
		String[] camposEmTexto = servicoDeDigito.transformarCamposEmTexto(textFields);
		
		if(acao == Acao.ADICIONAR){
			Escola escola = new Escola(EscolaManager.getInstance().getEscolas().size(), camposEmTexto[1]);
			JOptionPane.showConfirmDialog(this, "Confirmar a adição da escola: " + escola.getNome() + "?", "Confirmar Adição", JOptionPane.OK_CANCEL_OPTION);
			EscolaManager.getInstance().adicionarNovaEscola(escola);
			this.repintarTabela();
			
			EscolaManager.getInstance().getOperacoes().INSERT_DATA(escola);
		}
		else if (acao == Acao.ATUALIZAR){
			String idSelecionado = camposEmTexto[0];
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			if(id >= 0 && id < EscolaManager.getInstance().getEscolas().size()){
				Escola velhaEscola = EscolaManager.getInstance().getEscolas().get(id);
				Escola novaEscola = new Escola(camposEmTexto[1]);
				
				String mensage = "";
				if(novaEscola.getNome().toString().length() > 0){
					mensage = mensage.concat(velhaEscola.getNome() + " ---> " + novaEscola.getNome() + "\n");
				}
				
				if(mensage.length() > 0){
					JOptionPane.showConfirmDialog(this, mensage	,"Atualizacao", JOptionPane.OK_CANCEL_OPTION);
					novaEscola.setId(velhaEscola.getId());
					EscolaManager.getInstance().atualizarEscola(id, novaEscola);
					this.repintarTabela();
					

					EscolaManager.getInstance().getOperacoes().UPDATE_DATA(novaEscola);
				}
				else
					JOptionPane.showMessageDialog(this, "Não há informação a ser atualizada","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
			}
		}
		else{
			JOptionPane.showMessageDialog(this, "Id inexistente!","Erro ao remover", JOptionPane.OK_CANCEL_OPTION);
		}
	}
	
	private void prepararParaAcao(Acao acao, JTextField[] textFields){
		if(acao == Acao.ADICIONAR){
			textFields[0].setEditable(false);
			textFields[0].setBackground(Color.lightGray);
			
			for(int i = 1; i < textFields.length; i++){
				textFields[i].setEditable(true);
				textFields[i].setBackground(Color.WHITE);
			}
		}
		else if(acao == Acao.ATUALIZAR){
			textFields[0].setEditable(true);
			textFields[0].setBackground(Color.WHITE);
			
			for(int i = 1; i < textFields.length; i++){
				textFields[i].setEditable(false);
				textFields[i].setBackground(Color.lightGray);
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


