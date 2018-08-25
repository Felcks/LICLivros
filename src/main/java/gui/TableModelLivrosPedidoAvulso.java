package gui;

import principais.EstoqueManager;
import principais.Livro;

import javax.swing.table.AbstractTableModel;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class TableModelLivrosPedidoAvulso extends AbstractTableModel {

    private static Boolean DEBUG = false;
    public static Boolean firstTime = true;

    private String[] columnNames = {"NOME",
            "EDITORA" };

    private Object[][] data;

    public Object[][] getData(){
        return this.data;
    }


    public TableModelLivrosPedidoAvulso(){
        updateData("");
    }

    public void updateData(String nome){

        List<Livro> livros = EstoqueManager.getInstance().getTodosLivros();
        ArrayList<Livro> livrosContidos = new ArrayList<>();
        for(Livro l : livros){
            if(stripAccents(l.getNome().toUpperCase()).contains(stripAccents(nome.toUpperCase()))){
                livrosContidos.add(l);
            }
        }


        data = new Object[livrosContidos.size()][];
        for(int i = 0; i < data.length; i++){
            data[i] = livrosContidos.get(i).pegarParametrosNomeEditora();
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
        if (col != 2) {
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
        TelaPedidoUnicoAvulso.atualizarPreco(data);

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

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}