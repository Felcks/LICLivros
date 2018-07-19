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
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jws.soap.SOAPBinding.ParameterStyle;
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
import principais.Livro;
import principais.Pedido;
import principais.PedidoManager;
import principais.TipoPedido;
import utilidades.Acao;
import utilidades.Screen;
import utilidades.ServicoDeDigito;
import utilidades.Status;
import utilidades.StatusDaEntrega;
import utilidades.StatusDoPagamento;

public class TelaPedido extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private JTable table;
	ServicoDeDigito servicoDeDigito;
	JTextField textField_arrecadado;
	private double total = 0;
	public static JTextField pesquisaField;
	
	public TelaPedido(GUIManager guiManager){
		this.guiManager = guiManager;
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        this.servicoDeDigito = new ServicoDeDigito();
        
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
		
		JLabel pesquisaLabel = new JLabel("PESQUISAR: ");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 4;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(pesquisaLabel, c);
		
		pesquisaField = new JTextField();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 5;
		c.gridheight = 4;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(pesquisaField, c);
        
		pesquisaField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				repintarTabela();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
	        	repintarTabela();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				repintarTabela();
			}
		});
		
		
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
		
		JLabel statusLabel = new JLabel("Status do pedido: ");
		c.gridx = 11;
		c.gridy = 19;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(statusLabel, c);
		
		String[] allStatus = new String[Status.values().length];
		for(int i = 0; i < Status.values().length; i++)
			allStatus[i] = Status.values()[i].getNome();
		JComboBox boxStatus = new JComboBox(allStatus);
		c.gridx = 12;
		c.gridy = 19;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(boxStatus,c);
	
		
		String[] acoes = {Acao.ATUALIZAR.toString(), Acao.ABRIR_DOCUMENTO.toString()};
		JComboBox comboBoxAcoes = new JComboBox(acoes);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 11;
		c.gridy = 20;
		this.add(comboBoxAcoes, c);

		JButton btn_fazerAcao = new JButton("Confirmar!");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 12;
		c.gridy = 20;
		c.gridwidth = 3;
		this.add(btn_fazerAcao, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 23;
		c.gridwidth = 1;
		c.gridheight = 1;
		//this.add(btn_Voltar, c);
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		
		btn_fazerAcao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Acao acao = Acao.NENHUMA;
				acao = Acao.valueOf(comboBoxAcoes.getSelectedItem().toString());
				fazerAcao(boxStatus, table, acao);
			}
		});
     
	}
	
	private void fazerAcao(JComboBox comboBox,  JTable table, Acao acao) throws ArrayIndexOutOfBoundsException {
		int id = -1;
		id = table.getSelectedRow();
		if(id == -1){
			JOptionPane.showMessageDialog(this, "Selecione um pedido.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
			return;
		}
		id = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());

		
		if (acao == Acao.ATUALIZAR){
			Pedido velhoPedido = PedidoManager.getInstance().getPedidoPeloId(id);
			String novo = comboBox.getSelectedItem().toString();
			Status novoStatus = Status.EM_ANDAMENTO;
			for(int i = 0; i < Status.values().length; i++){
				if(novo.equals(Status.values()[i].getNome())){
					novoStatus = Status.values()[i];
					break;
				}
			}
			
			if(novoStatus == Status.CANCELADO)
			{
				if(velhoPedido.getStatus() == Status.EM_ANDAMENTO){
					{

						for(int i = 0; i < velhoPedido.getIdsDosLivrosComprados().length; i++){
							Livro livro = EstoqueManager.getInstance().getLivroPeloId(velhoPedido.getIdsDosLivrosComprados()[i]);
							int quantidadeComprada = velhoPedido.getQtdDosLivrosComprados()[i];
							livro.setReservado(livro.getReservado() - quantidadeComprada);
							if(livro.getReservado() < 0)
								livro.setReservado(0);

							EstoqueManager.getInstance().atualizarLivro(livro.getId(), livro);
							EstoqueManager.getInstance().getOperacoes().UPDATE_DATA(livro);
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(this, "Mundança de estado não permitida.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
					return;
				}
			}
			else if(novoStatus == Status.EM_ANDAMENTO) {
				if (velhoPedido.getStatus() == Status.CANCELADO) {

					for (int i = 0; i < velhoPedido.getIdsDosLivrosComprados().length; i++) {
						Livro livro = EstoqueManager.getInstance().getLivroPeloId(velhoPedido.getIdsDosLivrosComprados()[i]);
						int quantidadeComprada = velhoPedido.getQtdDosLivrosComprados()[i];
						livro.setReservado(livro.getReservado() + quantidadeComprada);
						if(livro.getReservado() < 0)
							livro.setReservado(0);

						EstoqueManager.getInstance().atualizarLivro(livro.getId(), livro);
						EstoqueManager.getInstance().getOperacoes().UPDATE_DATA(livro);
					}
				}
				else{
					JOptionPane.showMessageDialog(this, "Mundança de estado não permitida.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
					return;
				}
			}
			else if(novoStatus == Status.PRONTO) {
				if(velhoPedido.getStatus() == Status.EM_ANDAMENTO){

					for (int i = 0; i < velhoPedido.getIdsDosLivrosComprados().length; i++) {
						Livro livro = EstoqueManager.getInstance().getLivroPeloId(velhoPedido.getIdsDosLivrosComprados()[i]);
						int quantidadeComprada = velhoPedido.getQtdDosLivrosComprados()[i];
						livro.setReservado(livro.getReservado() - quantidadeComprada);
						if(livro.getReservado() < 0)
							livro.setReservado(0);
						livro.setVendidos(livro.getVendidos() + quantidadeComprada);
						livro.setQuantidade(livro.getQuantidade() - quantidadeComprada);
						if(livro.getQuantidade() < 0)
							livro.setQuantidade(0);

						EstoqueManager.getInstance().atualizarLivro(livro.getId(), livro);
						EstoqueManager.getInstance().getOperacoes().UPDATE_DATA(livro);
					}
				}
				else if(velhoPedido.getStatus() == Status.CANCELADO ){
					JOptionPane.showMessageDialog(this, "Para finalizar o pedido coloque-o em andamento primeiro.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
					return;
				}
				else{
					JOptionPane.showMessageDialog(this, "Mundança de estado não permitida.","Erro ao concluir ação", JOptionPane.CANCEL_OPTION);
					return;
				}
			}


			velhoPedido.setStatus(novoStatus);
			
			PedidoManager.getInstance().getOperacoes().UPDATE_DATA(velhoPedido);
			PedidoManager.getInstance().atualizarPedidoPorId(id, velhoPedido);
			
			String mensage = "";
			mensage = mensage.concat("Status do pedido atualizado: " + novoStatus.getNome());
			JOptionPane.showMessageDialog(this, mensage	,"Atualização concluída!", JOptionPane.INFORMATION_MESSAGE);	
			this.repintarTabela();
		}
		else if(acao == Acao.ABRIR_DOCUMENTO){
			String PATH = "PEDIDOS/";
			
			Object[][] data = ((MyTableModelPedido)table.getModel()).getData();
			int row = table.getSelectedRow();
			
			PATH = PATH.concat(id + " - " + data[row][1].toString() + ".pdf");
			try {
				Desktop.getDesktop().open(new File(PATH));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		
	}

	public void prepararComponentes(){
		PedidoManager.getInstance().getTodosPedidosBD();
		repintarTabela();
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
		String pesquisa = "";
		if(TelaPedido.pesquisaField != null)
			pesquisa = TelaPedido.pesquisaField.getText();
		
		CharSequence sequencePesquisa = pesquisa.toUpperCase();
		String pesquisaReal = Normalizer.normalize(sequencePesquisa, Normalizer.Form.NFKD);
		
		//SETANDO TODOS OS DADOS - PEGANDO O TAMANHO DOS DADOS PESQUISADOS
    	data = new Object[PedidoManager.getInstance().getPedidos().size()][];
    	int dataSearchLength = 0;
    	for(int i = 0; i < data.length; i++){
    		Pedido pedido = PedidoManager.getInstance().getPedidos().get(i);
    		data[i] = pedido.pegarTodosParametros();
    		
    		CharSequence sequenceNome = pedido.getCliente().getNome().toUpperCase();
    		String nomeReal = Normalizer.normalize(sequenceNome, Normalizer.Form.NFKD);
    		if(nomeReal.contains(pesquisaReal))
    			dataSearchLength++;
    	}
    	
    	//SETANDO SOMENTE OS DADOS PESQUISADOS
    	data = new Object[dataSearchLength][];
    	int dataCurrentIndex = 0;
    	for(int i = 0; i < PedidoManager.getInstance().getPedidos().size(); i++){
    		Pedido pedido = PedidoManager.getInstance().getPedidos().get(i);
    		
    		CharSequence sequenceNome = pedido.getCliente().getNome().toUpperCase();
    		String nomeReal = Normalizer.normalize(sequenceNome, Normalizer.Form.NFKD);
    		if(nomeReal.contains(pesquisaReal)){
    			data[dataCurrentIndex] = pedido.pegarTodosParametros();
    			dataCurrentIndex++;
    		}
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

