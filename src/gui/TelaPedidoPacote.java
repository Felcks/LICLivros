package gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import bd.OperacoesPacotes;
import principais.AnoEscolar;
import principais.Escola;
import principais.EscolaManager;
import principais.Pacote;
import principais.PacoteManager;
import principais.Pedido;
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
	private static JLabel labelPreco;
	private static double precoTotal;
	
	
	public TelaPedidoPacote(GUIManager guiManager) {
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		 GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        
        for(int i = 0; i < 6; i ++){
        	for(int j = 0; j < 10; j++){
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
		c.gridwidth = 6;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
		
		String[] todasEscolas = EscolaManager.getInstance().getTodosNomesEscolas();
		comboBox = new JComboBox(todasEscolas);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(comboBox,c);
		
		String[] todosAnos = AnoEscolar.getTodosNomesAnosEscolares();
		JComboBox comboBoxAno = new JComboBox(todosAnos);
		c.gridx = 5;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		this.add(comboBoxAno,c);
		
		JLabel precoTotalEscrito = new JLabel("Preço:");
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 2;
		c.gridy = 8;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		precoTotalEscrito.setFont(precoTotalEscrito.getFont().deriveFont(20F));
		this.add(precoTotalEscrito, c);
		
		labelPreco = new JLabel("R$ 00,00");
		labelPreco.setFont(precoTotalEscrito.getFont().deriveFont(20F));
		c.gridx = 3;
		c.gridy = 8;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(labelPreco, c);
		
		this.escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
		this.anoEscolarSelecionado = AnoEscolar.getAnoEscolarPeloNome(comboBoxAno.getSelectedItem().toString());
		
		this.table = new JTable(new MyTableModelPedidoPacote(escolaSelecionada, anoEscolarSelecionado));
		minimizarTamanhoDaColuna(table, 1, 175, true);
		minimizarTamanhoDaColuna(table, 2, 90, true);
		minimizarTamanhoDaColuna(table, 3, 90, true);
		minimizarTamanhoDaColuna(table, 4, 70, false);
		JScrollPane scrollPane  = new JScrollPane(this.table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6;
		c.gridheight = 5;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		this.add(scrollPane, c);
		
		
		JButton btn_Avancar = new JButton("Avançar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 5;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Avancar, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 9;
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
				if(pacote != null){
					Pedido.pedidoAtual.setPacote(pacote);
					Pedido.pedidoAtual.setPreco(precoTotal);
					Pedido.pedidoAtual.setIdsDosLivrosComprados(idsDosLivrosSelecionados);
					
					guiManager.mudarParaTela("telaPedidoFinalizacao");
				}
				
			}
		});
		
		comboBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(comboBox != null)
					if(comboBox.getItemCount() > 0)
						escolaSelecionada = new Escola(comboBox.getSelectedItem().toString());
				
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
	

		//table.setDefaultRenderer(Boolean.class, new CustomCellRenderer());		
		
		this.guiManager.getCards().add(this, "telaPedidoPacote");
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
			((MyTableModelPedidoPacote)this.table.getModel()).updateData(escolaSelecionada, anoEscolarSelecionado);
			this.table.repaint();
			
			pacote = PacoteManager.getInstance().getPacote(escolaSelecionada, anoEscolarSelecionado);
			idsDosLivrosSelecionados = new int[pacote.getLivros().size()];
			for(int i = 0; i < idsDosLivrosSelecionados.length; i++)
				idsDosLivrosSelecionados[i] = pacote.getLivros().get(i).getId();
			
			precoTotal = 0;
			for(int i = 0; i < pacote.getLivros().size(); i++){
				precoTotal += pacote.getLivros().get(i).getPreco();
			}
			labelPreco.setText("R$ " + precoTotal);
		}
	}
	
	public static void atualizarPrecoTotal(double valor){
		precoTotal += valor;
		labelPreco.setText("R$ " + precoTotal);
	}
	
	public static void adicionarOuRemoverId(int index){
		idsDosLivrosSelecionados[index] += 1;
		
		idsDosLivrosSelecionados[index] *= -1;
	}
}

class MyTableModelPedidoPacote extends AbstractTableModel {
	
	private static Boolean DEBUG = false;
	public static Boolean firstTime = true;
	
    private String[] columnNames = {"NOME",
                                    "EDITORA",
                                    "QUANTIDADE",
                                    "PREÇO",
                                    "INCLUIR"};
   
    private Object[][] data;
    
    
    public MyTableModelPedidoPacote(Escola escola, AnoEscolar anoEscolar){
    	updateData(escola, anoEscolar);
    }
    
    public void updateData(Escola escola, AnoEscolar anoEscolar){
    	data = new Object[PacoteManager.getInstance().getPacote(escola, anoEscolar).getLivros().size()][];
    	for(int i = 0; i < data.length; i++){
    		Object[] parametros = PacoteManager.getInstance().getPacote(escola, anoEscolar).getLivros().get(i).pegarParametrosParaPedido();
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
        	if((Boolean)data[row][col] == true)
        		TelaPedidoPacote.atualizarPrecoTotal((double)data[row][3]);
        	else
        		TelaPedidoPacote.atualizarPrecoTotal(-(double)data[row][3]);
        	
        	TelaPedidoPacote.adicionarOuRemoverId(row);
        		
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


