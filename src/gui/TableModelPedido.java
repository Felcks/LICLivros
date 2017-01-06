package gui;

import javax.swing.table.AbstractTableModel;

import principais.AnoEscolar;
import principais.Escola;
import principais.PacoteManager;

class TableModelPedido extends AbstractTableModel {
	
	private static Boolean DEBUG = false;
	public static Boolean firstTime = true;
	
    private String[] columnNames = {"NOME",
                                    "EDITORA",
                                    "QUANTIDADE",
                                    "PREÇO",
                                    };
   
    private Object[][] data;
    
    
    public TableModelPedido(Escola escola, AnoEscolar anoEscolar){
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


