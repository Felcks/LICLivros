package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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
import bd.OperacoesPedidos;
import principais.ClienteManager;
import principais.Escola;
import principais.EscolaManager;
import principais.EstoqueManager;
import principais.Pedido;
import principais.PedidoManager;
import principais.TipoPedido;
import utilidades.Acao;
import utilidades.Screen;
import utilidades.ServicoDeDigito;
import utilidades.StatusDaEntrega;
import utilidades.StatusDoPagamento;

public class TelaPedido extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private JTable table;
	ServicoDeDigito servicoDeDigito;
	JTextField textField_arrecadado;
	private double total = 0;
	
	public TelaPedido(GUIManager guiManager){
		this.guiManager = guiManager;
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        this.servicoDeDigito = new ServicoDeDigito();
        
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 24; i ++){
        	for(int j = 0; j < 24; j++){
        		c.gridx = i;
        		c.gridy = j;
        		c.fill = GridBagConstraints.BOTH;
        		this.add(new JLabel(""), c);
        	}
        }
        
        JLabel txt_Title = new JLabel("PEDIDOS", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 4;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
        
        table = new JTable(new MyTableModelPedido());
        table.getColumnModel().getColumn(3).setMinWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);

        table.getColumnModel().getColumn(6).setMinWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setMaxWidth(80);

        table.getColumnModel().getColumn(4).setMinWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setMaxWidth(100);
	
        table.getColumnModel().getColumn(5).setMinWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setMaxWidth(150);
	
        table.getColumnModel().getColumn(7).setMinWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setMaxWidth(150);
		
        table.getColumnModel().getColumn(0).setMinWidth(40);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		
		DefaultTableCellRenderer left = new DefaultTableCellRenderer();
		left.setHorizontalAlignment(SwingConstants.LEFT);
		table.getColumnModel().getColumn(0).setCellRenderer(left);;
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 24;
		c.gridheight = 14;
		this.add(scrollPane, c);
		
		
		
		int posAtual = 6;
		int[] gridWidth = { 1, 4, 4 };
		JTextField textField = new JTextField();
		JComboBox[] comboBoxes = new JComboBox[2];
		comboBoxes[0] = new JComboBox(StatusDaEntrega.getTodosNomesStatus());
		comboBoxes[1] = new JComboBox(StatusDoPagamento.getTodosNomesStatus());
		c.gridy = 19;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		for(int i = 0; i < 3; i++){
			c.gridwidth = gridWidth[i];
			c.gridx = posAtual;
			if(i == 0){
				this.add(textField, c);
			}
			else{
				this.add(comboBoxes[i-1], c);
			}
			posAtual
			+= gridWidth[i];
		}
		String[] text = { "ID", "STATUS DA ENTREGA", "STATUS DO PAGAMENTO" };
		JLabel[] labels = new JLabel[3];
		posAtual = 6;
		c.gridy = 18;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		for(int i = 0; i < 3; i++){
			c.gridwidth = gridWidth[i];
			c.gridx = posAtual;
			labels[i] = new JLabel(text[i]);
			this.add(labels[i], c);
			posAtual += gridWidth[i];
		}
		
		String[] acoes = {Acao.ATUALIZAR.toString(), Acao.ABRIR_DOCUMENTO.toString()};
		JComboBox comboBoxAcoes = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridx = 6;
		c.gridy = 20;
		this.add(comboBoxAcoes, c);
		
		JLabel txt_totalArrecadado = new JLabel("Total Arrecadado:");
		txt_totalArrecadado.setFont(txt_totalArrecadado.getFont().deriveFont(20F));
		c.gridx = 8;
		c.gridy = 21;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.fill = GridBagConstraints.NONE;
		this.add(txt_totalArrecadado, c);
		
		textField_arrecadado = new JTextField();
		textField_arrecadado.setBackground(Color.LIGHT_GRAY);
		c.gridx = 9;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		textField_arrecadado.setEditable(false);
		this.add(textField_arrecadado, c);

		JButton btn_fazerAcao = new JButton("Fazer Ação!");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 9;
		c.gridy = 20;
		c.gridwidth = 6;
		this.add(btn_fazerAcao, c);
		
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 23;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		
		textField.getDocument().addDocumentListener(new DocumentListener() {	
			@Override
			public void removeUpdate(DocumentEvent e) {
				checarId(textField.getText(), comboBoxes, textField);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				checarId(textField.getText(), comboBoxes, textField);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				checarId(textField.getText(), comboBoxes, textField);
			}
		});
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBoxAcoes.getSelectedItem().toString());
				fazerAcao(comboBoxes, textField, table, acao);
			}
		});
     
		guiManager.getCards().add(this);
	}
	
	private void fazerAcao(JComboBox[] comboBoxes, JTextField textField, JTable table, Acao acao) throws ArrayIndexOutOfBoundsException {
		//AQUI PEGAMOS TODOS OS TEXTOS DOS CAMPOS E ORDENAMOS A LISTA EM ORDEM NUMERAL ANTES DE QUALQUER AÇÃO
		JTextField[] a = { textField };
		String[] camposEmTexto = servicoDeDigito.transformarCamposEmTexto(a);
		
		if (acao == Acao.ATUALIZAR){
			String idSelecionado = camposEmTexto[0];
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			if(id >= 0 && id < PedidoManager.getInstance().getPedidos().size()){
				Pedido velhoPedido = PedidoManager.getInstance().getPedidos().get(id);
				Pedido novoPedido = new Pedido(comboBoxes[0].getSelectedItem().toString(), comboBoxes[1].getSelectedItem().toString());
				
				String mensage = "";
				/*if(novoPedido.getStatusDaEntrega() != velhoPedido.getStatusDaEntrega()){
					mensage = mensage.concat(velhoPedido.getStatusDaEntrega().getNome() + " ---> " + novoPedido.getStatusDaEntrega().getNome() + "\n");
					velhoPedido.setStatusDaEntrega(novoPedido.getStatusDaEntrega());
					if(novoPedido.getStatusDaEntrega().getNome().equalsIgnoreCase("entregue"))
						EstoqueManager.getInstance().adicionarDoEstoque(velhoPedido.getIdsDosLivrosComprados());				
				}
				if(novoPedido.getStatusDoPagamento() != velhoPedido.getStatusDoPagamento()){
					mensage = mensage.concat(velhoPedido.getStatusDoPagamento().getNome() + " ---> " + novoPedido.getStatusDoPagamento().getNome() + "\n");
					velhoPedido.setStatusDoPagamento(novoPedido.getStatusDoPagamento());
					
					if(novoPedido.getStatusDoPagamento() == StatusDoPagamento.PAGO){
						NumberFormat nf = NumberFormat.getCurrencyInstance();  
						total += velhoPedido.getPreco();
						String formatado = nf.format (total);
						textField_arrecadado.setText(formatado);
					}
				}*/
				
				if(mensage.length() > 0){
					JOptionPane.showMessageDialog(this, velhoPedido.getCliente().getNome() + "\n" + mensage	,"Atualizacao bem sucedida!", JOptionPane.INFORMATION_MESSAGE);
					PedidoManager.getInstance().atualizarPedido(id, velhoPedido);
					this.repintarTabela();
					
					OperacoesPedidos opc = new OperacoesPedidos();
					opc.UPDATE_DATA(velhoPedido);
				}
				else
					JOptionPane.showMessageDialog(this, "Não há informação a ser atualizada","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
			}
		}
		else if(acao == Acao.ABRIR_DOCUMENTO){
			String PATH = "PEDIDOS/";
			String idSelecionado = camposEmTexto[0];
			//int id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			
					
			Object[][] data = ((MyTableModelPedido)table.getModel()).getData();
			int id = (int)data[table.getSelectedRow()][0];

			PATH = PATH.concat(id + " - " + data[id][1].toString() + ".pdf");
			try {
				Desktop.getDesktop().open(new File(PATH));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
	}

	
	private void checarId(String text, JComboBox[] comboBoxes, JTextField textField){
		Runnable runnable = new Runnable() {
			@Override
			public void run(){
				String idSelecionado = text;
				int id = -1;
				id = servicoDeDigito.transformarStringEmInt(idSelecionado);
				if(id >= 0 && id < PedidoManager.getInstance().getPedidos().size()){
					Object[] params = PedidoManager.getInstance().getPedidoPeloId(id).pegarTodosParametros();
					for(int i = 0; i < 2; i++){
						if(i == 0){
						comboBoxes[i].setSelectedItem(StatusDaEntrega.getStatusPeloNome((params[i + 6].toString())).getNome());
						}
						else
						comboBoxes[i].setSelectedItem(StatusDoPagamento.getStatusPeloNome((params[i + 6].toString())).getNome());
					}
				}
				else{
					textField.setText("");
				}
			}
		};
		
		SwingUtilities.invokeLater(runnable);
	}
	
	public void prepararComponentes(){
		PedidoManager.getInstance().getTodosPedidosBD();
		repintarTabela();
		
		for(int i = 0; i < PedidoManager.getInstance().getPedidos().size(); i++){
			//if(PedidoManager.getInstance().getPedidos().get(i).getStatusDoPagamento() == StatusDoPagamento.PAGO){
				//total += PedidoManager.getInstance().getPedidos().get(i).getPreco();
			//}
		}
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (total);
		textField_arrecadado.setText(formatado);
	}
	
	public void repintarTabela(){
		if(table != null){
			((MyTableModelPedido)table.getModel()).updateData();
			table.repaint();
		}
	}
	
	
		
}


class MyTableModelPedido extends AbstractTableModel {
	
	private static Boolean DEBUG = true;
	
    private String[] columnNames = {"Nº",
                                    "CLIENTE",
                                    "LIVROS",
                                    "PREÇO",
                                    "FORMA DE PAGAMENTO",
                                    "ESTATUS DO PEDIDO",
                                    "TIPO", 
                                    "DATA"};
   
    private Object[][] data;
    
    public Object[][] getData()
    {
    	return data;
    }
    
    public MyTableModelPedido(){
    	updateData();
    }
    
    public void updateData(){
    	data = new Object[PedidoManager.getInstance().getPedidos().size()][];
    	for(int i = 0; i < data.length; i++){
    		data[i] = PedidoManager.getInstance().getPedidos().get(i).pegarTodosParametros();
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
    	System.out.println("erro na coluna: " + c);
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

