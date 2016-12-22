package gui;

import utilidades.ServicoDeDigito;

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
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import bd.OperacoesClientes;
import principais.Cliente;
import principais.ClienteManager;
import utilidades.Screen;
import utilidades.Acao;


public class TelaCliente extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	private JTable table;
	
	public TelaCliente(GUIManager guiManager){
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 24; i ++){
        	for(int j = 0; j < 20; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
     
		JTextField[] textFields = new JTextField[7];
		int[] widthX = new int[] { 1, 9, 4, 4, 2, 2, 2 };
		int posicaoAtual = 0;
		c.gridy = 18;
		c.fill = GridBagConstraints.HORIZONTAL;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = posicaoAtual ;
			c.gridwidth = widthX[i];
			c.anchor = GridBagConstraints.PAGE_START;
			posicaoAtual += widthX[i];
			this.add(textFields[i],c);
		}
		
		
		 String[] columnNames = {"ID",
                 "NOME",
                 "BAIRRO",
                 "RUA",
                 "Nº / COMPLEMENTO",
                 "TELEFONE",
                 "CELULAR"};
		JLabel[] labels = new JLabel[7];
		c.gridy = 17;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		posicaoAtual = 0;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = posicaoAtual ;
			c.gridwidth = widthX[i];
			posicaoAtual += widthX[i];
			this.add(labels[i],c);
		}
		

        JLabel txt_Title = new JLabel("CLIENTES", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		table = new JTable(new MyTableModelCliente());
		JScrollPane scrollPane = new JScrollPane(table);
		for (int i = 0; i < (table.getColumnModel().getColumnCount()); i++) {
	            table.getColumnModel().getColumn(i).setPreferredWidth(150);
	    }
		table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		DefaultTableCellRenderer left = new DefaultTableCellRenderer();
		left.setHorizontalAlignment(SwingConstants.LEFT);
		table.getColumnModel().getColumn(0).setCellRenderer(left);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 24;
		c.gridheight = 15;
		this.add(scrollPane, c);
		
		String[] acoes = new String[2];
		acoes[0] = Acao.ADICIONAR.name();
		acoes[1] = Acao.ATUALIZAR.name();
		JComboBox comboBox = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.gridx = 10;
		c.gridy = 19;
		this.add(comboBox, c);
		
		JButton btn_fazerAcao = new JButton("Fazer Ação!");
		c.fill = GridBagConstraints.HORIZONTAL;;
		c.gridx = 13;
		c.gridy = 19;
		c.gridwidth = 5;
		c.gridheight = 2;
		this.add(btn_fazerAcao, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 20;
		c.gridwidth = 2;
		c.gridheight = 2;
		this.add(btn_Voltar, c);
		
		
		JButton btn_OrdenarAlfabeticamente = new JButton("Ordem Alfabetica");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		this.add(btn_OrdenarAlfabeticamente, c);
		
		JButton btn_OrdenarNumeralmente = new JButton("Ordem Numeral");
		c.fill = GridBagConstraints.NONE;
		c.gridx = 19;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		this.add(btn_OrdenarNumeralmente, c);
		

		textFields[0].setEditable(false);
		textFields[0].setBackground(Color.lightGray);
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				servicoDeDigito.limparCampos(textFields);
				Acao acao = Acao.valueOf(comboBox.getSelectedItem().toString());
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
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		
		
		btn_OrdenarAlfabeticamente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ordenarAlfabeticamente();
			}
		});
		
		btn_OrdenarNumeralmente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				ordenarNumeralmente();
			}
		});
				
		this.guiManager.getCards().add(this);
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
	
	private void checarId(String text, JTextField[] textFields){
		Runnable runnable = new Runnable() {
			@Override
			public void run(){
				String idSelecionado = text;
				int id = -1;
				id = servicoDeDigito.transformarStringEmInt(idSelecionado);
				if(id >= 0 && id < ClienteManager.getInstance().getTodosClientes().size()){
					Object[] params = ClienteManager.getInstance().getClientePeloId(id).pegarTodosParametros();
					for(int i = 1; i < 7; i++){
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
	
	@Override
	public void prepararComponentes(){
		ClienteManager.getInstance().getTodosClientesDoBD();
		this.repintarTabela();
	}
	
	private void ordenarNumeralmente(){
		ClienteManager.getInstance().organizarEmOrdemDeId();
		((MyTableModelCliente)table.getModel()).updateData();
		table.repaint();
	}
	private void ordenarAlfabeticamente(){
		ClienteManager.getInstance().organizarEmOrdemAlfabetica();
		((MyTableModelCliente)table.getModel()).updateData();
		table.repaint();
	}
	
	private void fazerAcao(JTextField[] textFields, JTable table, Acao acao) throws ArrayIndexOutOfBoundsException {
		//AQUI PEGAMOS TODOS OS TEXTOS DOS CAMPOS E ORDENAMOS A LISTA EM ORDEM NUMERAL ANTES DE QUALQUER AÇÃO
		String[] camposEmTexto = servicoDeDigito.transformarCamposEmTexto(textFields);
		this.ordenarNumeralmente();
		
		if(acao == Acao.ADICIONAR){
			Cliente cliente = new Cliente(camposEmTexto);
			if(cliente.isValidCliente()) {
				JOptionPane.showConfirmDialog(this, "Confirmar a adição do cliente: " + cliente.getNome(), "Confirmar Adição", JOptionPane.OK_CANCEL_OPTION);
				ClienteManager.getInstance().adicionarNovoCliente(cliente);
				((MyTableModelCliente)table.getModel()).updateData();
				table.repaint();
				servicoDeDigito.limparCampos(textFields);
				
				//teste
				ClienteManager.getInstance().getOperacoes().INSERT_DATA(cliente);
			}
			else {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos com informações válidas","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}
			
		}
		else if(acao == Acao.ATUALIZAR){
			String idSelecionado = camposEmTexto[0];
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			if(id >= 0 && id < ClienteManager.getInstance().getTodosClientes().size()){
				Cliente novoCliente = new Cliente(camposEmTexto);
				Cliente cliente = ClienteManager.getInstance().getClientePeloId(id);
				Cliente clienteASerAdicionado = cliente;
				//Lógica de só atualizar os estatus que ESTIVEREM PREENCHIDOS
				String mensage = "";
				Object[] parametrosDoNovoCliente = novoCliente.pegarTodosParametros();
				Object[] parametrosDoClienteASerAdicionado = clienteASerAdicionado.pegarTodosParametros();
				for(int i = 1; i < camposEmTexto.length; i++){
					if(parametrosDoNovoCliente[i].toString().length() > 0) {
						mensage = mensage.concat(cliente.pegarTodosParametros()[i] + " ---> " + novoCliente.pegarTodosParametros()[i] + "\n");
						parametrosDoClienteASerAdicionado[i] = parametrosDoNovoCliente[i];
					}
				}
				if(mensage.length() > 0) {
					JOptionPane.showConfirmDialog(this, mensage	,"Atualizacao", JOptionPane.OK_CANCEL_OPTION);
					clienteASerAdicionado.setarTodosParametros(parametrosDoClienteASerAdicionado);
					ClienteManager.getInstance().atualizarCliente(ClienteManager.getInstance().getIndexPeloId(id), clienteASerAdicionado);
					this.repintarTabela();
					servicoDeDigito.limparCampos(textFields);
					
					ClienteManager.getInstance().getOperacoes().UPDATE_DATA(clienteASerAdicionado);
				}
				else
					JOptionPane.showMessageDialog(this, "Não há informação a ser atualizada","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
		}
			else{
				JOptionPane.showMessageDialog(this, "Id Inexistente","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
			}
		}
	}
	
	public void repintarTabela(){
		if(table != null){
			((MyTableModelCliente)table.getModel()).updateData();
			table.repaint();
		}
	}
}

class MyTableModelCliente extends AbstractTableModel {
	
	private static Boolean DEBUG;
	
    private String[] columnNames = {"ID",
                                    "NOME",
                                    "BAIRRO",
                                    "RUA",
                                    "COMPLEMENTO",
                                    "TELEFONE",
                                    "CELULAR"};
   
    private Object[][] data;
    
    public MyTableModelCliente(){
    	updateData();
    }
    
    public void updateData(){
    	data = new Object[ClienteManager.getInstance().getTodosClientes().size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = ClienteManager.getInstance().getTodosClientes().get(i).pegarTodosParametros();
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
