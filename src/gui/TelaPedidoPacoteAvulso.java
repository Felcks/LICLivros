package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.IdentityScope;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import bd.OperacoesPacotes;
import principais.AnoEscolar;
import principais.ClienteManager;
import principais.Escola;
import principais.EscolaManager;
import principais.EstoqueManager;
import principais.Livro;
import principais.Pacote;
import principais.PacoteManager;
import principais.Pedido;
import utilidades.Acao;
import utilidades.AutoSuggestor;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaPedidoPacoteAvulso extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	JTable table;
	Pacote pacote;
	public AutoSuggestor autoSuggestor;
	
	public static List<Integer> idsDosLivrosAdicionados;
	public static int[] idsDosLivrosSelecionados;
	private static JTextField textFieldPreco;
	private static double precoTotal;
	
	
	public TelaPedidoPacoteAvulso(GUIManager guiManager) {
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		idsDosLivrosAdicionados = new ArrayList<Integer>();
		idsDosLivrosSelecionados = new int[0];
		
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
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
     
        
        JLabel txt_Title = new JLabel("Pedido - Passo 2", SwingConstants.CENTER);
        txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 24;
		c.gridheight = 4;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		JLabel precoTotalEscrito = new JLabel("Preço:");
		c.gridwidth = 1;
		c.gridx = 11;
		c.gridy = 18;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		precoTotalEscrito.setFont(precoTotalEscrito.getFont().deriveFont(20F));
		this.add(precoTotalEscrito, c);
		
		JLabel label_livro = new JLabel("Adicionar Livro:");
		c.gridy = 20;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		this.add(label_livro, c);
		
		textFieldPreco = new JTextField();
		textFieldPreco.setFont(precoTotalEscrito.getFont().deriveFont(20F));
		c.gridwidth = 1;
		textFieldPreco.setEditable(false);
		textFieldPreco.setBackground(Color.LIGHT_GRAY);
		c.gridx = 12;
		c.gridy = 18;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(textFieldPreco, c);
		
		JTextField textField_livro = new JTextField();
		c.gridy = 20;
		c.gridwidth = 5;
		this.add(textField_livro, c);
		
		JButton button = new JButton("Adicionar");
		c.gridx = 17;
		c.gridwidth = 1;
		c.gridy = 20;
		this.add(button, c);
		
		
		this.table = new JTable(new TableModelPedidoAvulso());
		minimizarTamanhoDaColuna(table, 1, 175, true);
		minimizarTamanhoDaColuna(table, 2, 90, true);
		minimizarTamanhoDaColuna(table, 3, 90, true);
		minimizarTamanhoDaColuna(table, 4, 70, false);
		JScrollPane scrollPane  = new JScrollPane(this.table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 24;
		c.gridheight = 14;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);
		
		
		JButton btn_Avancar = new JButton("Avançar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 23;
		c.gridy = 23;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Avancar, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 23;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
		
		autoSuggestor = new AutoSuggestor(textField_livro, guiManager.getJanela(), ClienteManager.getInstance().getTodosNomesClientes(), 
				Color.white.brighter(), Color.blue, Color.red, 0.75f);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adicionarLivroAoPedido(textField_livro);
			}
		}); 
	
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaPedidoCliente");
			}
		});
		
		btn_Avancar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(precoTotal > 0){
					Pacote pacoteVazio = new Pacote();
					Pedido.pedidoAtual.setPacote(pacoteVazio);
					Pedido.pedidoAtual.setPreco(precoTotal);
					Pedido.pedidoAtual.setIdsDosLivrosComprados(idsDosLivrosSelecionados);
					
					guiManager.mudarParaTela("telaPedidoFinalizacao");
				}
				else
				{
					mostrarAviso();
				}
			}
		});	
		
	}
	
	private void mostrarAviso(){
		JOptionPane.showMessageDialog(this, "Insira pelo menos um livro!","Sem livros no pedido!", JOptionPane.OK_CANCEL_OPTION);
	}
	
	private void adicionarLivroAoPedido(JTextField textField){
		String nome = textField.getText();
		if(nome.length() == 0)
			nome.concat(" ");
		
		String nomeSemEspacoFinal = nome.substring(0, nome.length() - 1);
	
		for(int i = 0; i < EstoqueManager.getInstance().getLivros().size(); i++){
			Livro livro = EstoqueManager.getInstance().getLivros().get(i);
			if(livro.getNome().equals(nome) ||	livro.getNome().equals(nomeSemEspacoFinal)){
				if(idsDosLivrosAdicionados.contains(livro.getId()) == false){
					int [] antigoIdsDosLivrosSelecionados = idsDosLivrosSelecionados;
					idsDosLivrosSelecionados = new int[antigoIdsDosLivrosSelecionados.length + 1];
					for(int j = 0; j < antigoIdsDosLivrosSelecionados.length; j++)
						idsDosLivrosSelecionados[j] = antigoIdsDosLivrosSelecionados[j];
					
					idsDosLivrosSelecionados[idsDosLivrosSelecionados.length - 1] = livro.getId();
					
					idsDosLivrosAdicionados.add(livro.getId());
					this.repintarTabela();
					textField.setText("");
					atualizarPrecoTotal(livro.getPreco())	;
					return;
				}
				else{
					JOptionPane.showMessageDialog(this, "Esse livro já foi adicionado!","Livro repetido!", JOptionPane.OK_CANCEL_OPTION);
				}
			}
		}
	}
	
	private void minimizarTamanhoDaColuna(JTable table, int index, int tam, Boolean goLeft){
		table.getColumnModel().getColumn(index).setMinWidth(tam);
		table.getColumnModel().getColumn(index).setPreferredWidth(tam);
		table.getColumnModel().getColumn(index).setMaxWidth(tam);
		if(goLeft){
			DefaultTableCellRenderer left = new DefaultTableCellRenderer();
			left.setHorizontalAlignment(SwingConstants.LEFT);
			table.getColumnModel().getColumn(index).setCellRenderer(left);
		}
	}
	
	private void atualizarPedido(){
		Pedido.pedidoAtual.setPacote(pacote);
	}
	
	@Override
	public void prepararComponentes(){
		this.repintarTabela();
		
		autoSuggestor = new AutoSuggestor(autoSuggestor.getTextField(), guiManager.getJanela(), EstoqueManager.getInstance().getTodosLivrosNomes(), 
				  Color.white.brighter(), Color.blue, Color.red, 0.75f);
	}
	
	private void repintarTabela(){
		if(this.table != null){
			
			((MyTableModelPedidoPacoteAvulso)this.table.getModel()).updateData();
			this.table.repaint();
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();  
			String formatado = nf.format (precoTotal);
			
			textFieldPreco.setText(formatado);
		}
	}
	
	public static void atualizarPrecoTotal(double valor){
		precoTotal += valor;
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (precoTotal);
		
		textFieldPreco.setText(formatado);
	}
	
	public static void adicionarOuRemoverId(int index){
		idsDosLivrosSelecionados[index] += 1;
		
		idsDosLivrosSelecionados[index] *= -1;
	}
}

class MyTableModelPedidoPacoteAvulso extends AbstractTableModel {
	
	private static Boolean DEBUG = false;
	public static Boolean firstTime = true;
	
    private String[] columnNames = {"NOME",
                                    "EDITORA",
                                    "QUANTIDADE",
                                    "PREÇO",
                                    "INCLUIR"};
   
    private Object[][] data;
    
    
    public MyTableModelPedidoPacoteAvulso(){
    	updateData();
    }
    
    public void updateData(){
    	data = new Object[TelaPedidoPacoteAvulso.idsDosLivrosAdicionados.size()][];
    	for(int i = 0; i < data.length; i++){
    		Object[] parametros = EstoqueManager.getInstance().getLivroPeloId(TelaPedidoPacoteAvulso.idsDosLivrosAdicionados.get(i)).pegarParametrosParaPedido();
    		data[i] = new Object[parametros.length + 1];
    		for(int j = 0; j < parametros.length; j++)
    			data[i][j] = parametros[j];
    	
    			data[i][parametros.length] = true;    		
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
    	try{
        return getValueAt(0, c).getClass();
    	}
    	catch(java.lang.NullPointerException e){
    		return new Boolean(true).getClass();
    	}
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
        
        //Se for a coluna que contém a boleana, atualizamos a tabela de preço
        if(col == 4){
        	if((Boolean)data[row][col] == true){
        		String a = data[row][3].toString().substring(3, data[row][3].toString().length());
        		a = a.replace(',', '.');
        		TelaPedidoPacoteAvulso.atualizarPrecoTotal((double)Double.parseDouble(a));
        	}
        	else{
        		String a = data[row][3].toString().substring(3, data[row][3].toString().length());
        		a = a.replace(',', '.');
        		TelaPedidoPacoteAvulso.atualizarPrecoTotal(-(double)Double.parseDouble(a));
        	}
        	
        	TelaPedidoPacoteAvulso.adicionarOuRemoverId(row);
        		
        }

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

