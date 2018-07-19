package gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

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

import bd.OperacoesLivrosPacotes;
import bd.OperacoesPacotes;
import principais.*;
import utilidades.Acao;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaPedidoPacote extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	JTable table;
	JComboBox comboBox;
	private Escola escolaSelecionada;
	private AnoEscolar anoEscolarSelecionado;
	Pacote pacote;
	
	private static int[] idsDosLivrosSelecionados;
	private static JTextField textFieldPreco;
	private static double precoTotal;
	
	
	public TelaPedidoPacote(GUIManager guiManager) {
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
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
		
		String[] todasEscolas = EscolaManager.getInstance().getTodosNomesEscolas();
		comboBox = new JComboBox(todasEscolas);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 4;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBox,c);
		
		String[] todosAnos = AnoEscolar.getTodosNomesAnosEscolares();
		JComboBox comboBoxAno = new JComboBox(todosAnos);
		c.gridx = 20;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 4;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		this.add(comboBoxAno,c);
		
		JLabel precoTotalEscrito = new JLabel("Preço:");
		c.gridwidth = 1;
		c.gridx = 11;
		c.gridy = 18;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		precoTotalEscrito.setFont(precoTotalEscrito.getFont().deriveFont(20F));
		this.add(precoTotalEscrito, c);
		
		textFieldPreco = new JTextField();
		textFieldPreco.setFont(precoTotalEscrito.getFont().deriveFont(20F));
		c.gridwidth = 1;
		textFieldPreco.setEditable(false);
		textFieldPreco.setBackground(Color.LIGHT_GRAY);
		c.gridx = 12;
		c.gridy = 18;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(textFieldPreco, c);
		
		this.escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
		this.anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());
		
		this.table = new JTable(new MyTableModelPacote(escolaSelecionada, anoEscolarSelecionado));
		minimizarTamanhoDaColuna(table, 0, 40, true);
		minimizarTamanhoDaColuna(table, 3, 80, true);
		minimizarTamanhoDaColuna(table, 2, 175, false);
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
					if(pacote != null){
						Pedido.pedidoAtual.setPacote(pacote);
						Pedido.pedidoAtual.setPreco(precoTotal);
						Pedido.pedidoAtual.setIdsDosLivrosComprados(idsDosLivrosSelecionados);
						
						guiManager.mudarParaTela("telaPedidoFinalizacao");
					}
				}
				else{
					mostrarAviso();
				}
				
			}
		});
		
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(comboBox != null)
					if(comboBox.getItemCount() > 0)
						escolaSelecionada = EscolaManager.getInstance().getEscolaPeloNome(comboBox.getSelectedItem().toString());
						//escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
				
				repintarTabela();
			}
		});
		comboBoxAno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());
				repintarTabela();
			}
		});
			
	}
	
	private void mostrarAviso(){
		JOptionPane.showMessageDialog(this, "Insira pelo menos um livro!","Sem livros no pedido!", JOptionPane.OK_CANCEL_OPTION);
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
		comboBox.removeAllItems();
		String[] todasEscolas = new String[EscolaManager.getInstance().getEscolas().size()];
		for(int i = 0; i < EscolaManager.getInstance().getEscolas().size(); i++){
			todasEscolas[i] = EscolaManager.getInstance().getEscolas().get(i).getNome();
			comboBox.addItem(todasEscolas[i]);
		}

		this.repintarTabela();
	}
	
	private void repintarTabela(){
		if(this.table != null){
			((MyTableModelPacote)this.table.getModel()).updateData(escolaSelecionada, anoEscolarSelecionado);
			this.table.repaint();
			
			/*pacote = PacoteManager.getInstance().getPacote(escolaSelecionada.getId(), anoEscolarSelecionado);
			idsDosLivrosSelecionados = new int[pacote.getLivros().size()];
			for(int i = 0; i < idsDosLivrosSelecionados.length; i++)
				idsDosLivrosSelecionados[i] = pacote.getLivros().get(i).getId();
			
			precoTotal = 0;
			for(int i = 0; i < pacote.getLivros().size(); i++){
				precoTotal += pacote.getLivros().get(i).getPreco();
			}
			
			NumberFormat nf = NumberFormat.getCurrencyInstance();  
			String formatado = nf.format (precoTotal);
			
			textFieldPreco.setText(formatado);*/
		}
	}
	
	public static void atualizarPrecoTotal(double valor){
		precoTotal += valor;
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();  
		String formatado = nf.format (precoTotal);
		
		textFieldPreco.setText(formatado);
	}
	
	public static void adicionarOuRemoverId(int index){
		try{
		idsDosLivrosSelecionados[index] += 1;
		idsDosLivrosSelecionados[index] *= -1;
		}
		catch(java.lang.NullPointerException e){
			//System.out.println("conto do exception:" + index);
		}
	}
}


class MyTableModelPedidoPacote extends AbstractTableModel {
	
	private static Boolean DEBUG = false;
	public static Boolean firstTime = true;
	
    private String[] columnNames = {"NOME",
                                    "EDITORA",
                                    "QUANTIDADE",
                                    "PREÇO"};
   
    private Object[][] data;
    
    
    public MyTableModelPedidoPacote(Escola escola, AnoEscolar anoEscolar){
    	updateData(escola, anoEscolar);
    }
    
    public void updateData(Escola escola, AnoEscolar anoEscolar){
		Pacote pacote = PacoteManager.getInstance().getPacote(escola.getId(), anoEscolar);

		OperacoesLivrosPacotes operacoesLivrosPacotes = new OperacoesLivrosPacotes();
		ArrayList<Livro> livros = (ArrayList<Livro>)operacoesLivrosPacotes.GET_LIVROS_DE_PACOTE(pacote.getId());

		data = new Object[livros.size()][];
		for(int i = 0; i < data.length; i++){
			data[i] = EstoqueManager.getInstance().getLivroPeloId(livros.get(i).getId()).pegarParametrosParaPedido();
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

        if(col == 2){
        	TelaPedidoUnico.atualizarPreco(data);
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

	public Object[][] getData() {
		return data;
	}
}


