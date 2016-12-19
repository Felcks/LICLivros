package gui;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import principais.ClienteManager;
import principais.PedidoManager;
import utilidades.Screen;
import utilidades.ServicoDeDigito;

public class TelaPedido extends JPanel implements IPrepararComponentes {
	
	private GUIManager guiManager;
	private JTable table;
	
	public TelaPedido(GUIManager guiManager){
		this.guiManager = guiManager;
		
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
        
        JLabel txt_Title = new JLabel("PEDIDOS", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 7;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(txt_Title, c);
        
        table = new JTable(new MyTableModelPedido());
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 7;
		c.gridheight = 5;
		this.add(scrollPane, c);
		
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
				guiManager.mudarParaTela("telaInicial");
			}
		});
     
		guiManager.getCards().add(this);
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
	
	private static Boolean DEBUG;
	
    private String[] columnNames = {"ID",
                                    "CLIENTE",
                                    "LIVROS",
                                    "PREÇO",
                                    "FORMA DE ENTREGA",
                                    "FORMA DE PAGAMENTO",
                                    "OBSERVAÇÃO",
                                    "ESTATUS ENTREGA",
                                    "ESTATUS PAGAMENTO",
                                    "ESTATUS DO PEDIDO",
                                    "DATA"};
   
    private Object[][] data;
    
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

