package gui;

import utilidades.ServicoDeDigito;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.swing.table.AbstractTableModel;

import principais.Cliente;
import principais.ClienteManager;
import principais.Editora;
import principais.EstoqueManager;
import utilidades.Screen;
import utilidades.Acao;

public class TelaCliente extends JPanel {
	
	private GUIManager guiManager;
	private ServicoDeDigito servicoDeDigito;
	
	public TelaCliente(GUIManager guiManager){
		this.guiManager = guiManager;
		this.servicoDeDigito = new ServicoDeDigito();
		
		this.setLayout(new GridBagLayout());
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        

        GridBagConstraints c = new GridBagConstraints();
     
		
		JTextField[] textFields = new JTextField[7];
		c.gridy = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		for(int i = 0; i < textFields.length; i++){
			textFields[i] = new JTextField();
			c.gridx = i ;
			this.add(textFields[i],c);
		}
		
		
		 String[] columnNames = {"ID",
                 "NOME",
                 "BAIRRO",
                 "RUA",
                 "COMPLEMENTO",
                 "TELEFONE",
                 "CELULAR"};
		JLabel[] labels = new JLabel[7];
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		for(int i = 0; i < textFields.length; i++){
			labels[i] = new JLabel(columnNames[i]);
			c.gridx = i ;
			this.add(labels[i],c);
		}
		

        JLabel txt_Title = new JLabel("CLIENTES", SwingConstants.CENTER);
		txt_Title.setFont(txt_Title.getFont().deriveFont((float)(Screen.width/25)));
		txt_Title.setSize(100,100);
        c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		//c.ipady = 10;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 7;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		this.add(txt_Title, c);
		
		JTable table = new JTable(new MyTableModelCliente());
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 7;
		this.add(scrollPane, c);
		
		JButton btn_adicionarCliente = new JButton("Adicionar Cliente");
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.gridx = 6;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_adicionarCliente, c);
		
		JButton btn_OrdenarAlfabetica = new JButton("Ordem Alfabetica");
		c.fill = GridBagConstraints.NONE;
		c.weighty = 1;
		c.gridx = 5;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_OrdenarAlfabetica, c);
		
		JButton btn_OrdenarNumeralmente = new JButton("Ordem Numeral");
		c.fill = GridBagConstraints.NONE;
		c.weighty = 1;
		c.gridx = 4;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_OrdenarNumeralmente, c);
		
		JButton btn_Voltar = new JButton("Voltar");
		c.fill = GridBagConstraints.NONE;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		this.add(btn_Voltar, c);
		
		btn_adicionarCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				adicionarCliente(textFields, table);
					/*int id = Integer.parseInt(todasInfoEmString[0]);
					Cliente cliente = ClienteManager.getInstance().getClientePeloId(id);
					int indexNaLista = ClienteManager.getInstance().getIndexPeloId(id);
					if(cliente.isValid()){
						System.out.println("bb");
						ClienteManager.getInstance().atualizarCliente(indexNaLista, new Cliente(todasInfoEmString));
						((MyTableModelCliente)table.getModel()).updateData();
						table.repaint();
					}
					else{
						System.out.println("Operação invalida. Deixe vazio para adicionar, -id para remover e id para atualizar");
					}*/
				
				
			}
		});
		
		btn_OrdenarAlfabetica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClienteManager.getInstance().organizarEmOrdemAlfabetica();
				((MyTableModelCliente)table.getModel()).updateData();
				table.repaint();
			}
		});
		
		btn_OrdenarNumeralmente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				ClienteManager.getInstance().organizarEmOrdemDeId();
				((MyTableModelCliente)table.getModel()).updateData();
				table.repaint();
			}
		});
		
		btn_Voltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiManager.mudarParaTela("telaInicial");
			}
		});
		
		this.guiManager.getCards().add(this);
	}
	
	private void adicionarCliente(JTextField[] textFields, JTable table) throws ArrayIndexOutOfBoundsException {
		String[] camposEmTexto = servicoDeDigito.transformarCamposEmTexto(textFields);
		Acao acao = servicoDeDigito.conferirAcao(camposEmTexto[0]);
		System.out.println(acao);
		
		if(acao == Acao.ADICIONAR){
			Cliente cliente = new Cliente(camposEmTexto);
			if(cliente.isValidCliente()){
				JOptionPane.showConfirmDialog(this, "Confirmar a adição do cliente: " + cliente.getNome(), "Confirmar Adição", JOptionPane.OK_CANCEL_OPTION);
				ClienteManager.getInstance().adicionarNovoCliente(cliente);
				((MyTableModelCliente)table.getModel()).updateData();
				table.repaint();
			}
			else{
				JOptionPane.showMessageDialog(this, "Preencha todos os campos com informações válidas","Erro ao adicionar", JOptionPane.OK_CANCEL_OPTION);
			}
			
		}
		else if(acao == Acao.REMOVER){
			String idSelecionado = camposEmTexto[0].substring(1, camposEmTexto[0].length());
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			System.out.println(id);
			if(id != -1 && id < ClienteManager.getInstance().getTodosClientes().size()){
				Cliente cliente = ClienteManager.getInstance().getClientePeloId(id);
				JOptionPane.showConfirmDialog(this, "Cliente: " + cliente.getNome(), "Confirmar Remoção", JOptionPane.OK_CANCEL_OPTION);
				ClienteManager.getInstance().removerCliente(id);
				ClienteManager.getInstance().reorganizarLista();
				((MyTableModelCliente)table.getModel()).updateData();
				table.repaint();
			}
			else{
				JOptionPane.showMessageDialog(this, "Id inexistente!","Erro ao remover", JOptionPane.OK_CANCEL_OPTION);
			}
		}
		else if(acao == Acao.ATUALIZAR){
			String idSelecionado = camposEmTexto[0];
			int id = -1;
			id = servicoDeDigito.transformarStringEmInt(idSelecionado);
			if(id != -1 && id < ClienteManager.getInstance().getTodosClientes().size()){
				Cliente novoCliente = new Cliente(camposEmTexto);
				Cliente cliente = ClienteManager.getInstance().getClientePeloId(id);
				Cliente clienteASerAdicionado = cliente;
				//Lógica de só atualizar os estatus que ESTIVEREM PREENCHIDOS
				String mensage = "";
				Object[] parametrosDoNovoCliente = novoCliente.pegarTodosParametros();
				Object[] parametrosDoClienteASerAdicionado = clienteASerAdicionado.pegarTodosParametros();
				for(int i = 1; i < camposEmTexto.length; i++){
					if(parametrosDoNovoCliente[i].toString().length() > 0){
						mensage = mensage.concat(cliente.pegarTodosParametros()[i] + " ---> " + novoCliente.pegarTodosParametros()[i] + "\n");
						parametrosDoClienteASerAdicionado[i] = parametrosDoNovoCliente[i];
					}
				}
				if(mensage.length() > 0){
					JOptionPane.showConfirmDialog(this, mensage	,"Atualizacao", JOptionPane.OK_CANCEL_OPTION);
					clienteASerAdicionado.setarTodosParametros(parametrosDoClienteASerAdicionado);
					ClienteManager.getInstance().atualizarCliente(ClienteManager.getInstance().getIndexPeloId(id), clienteASerAdicionado);
					((MyTableModelCliente)table.getModel()).updateData();
					table.repaint();
				}
				else
					JOptionPane.showMessageDialog(this, "Não há informação a ser atualizada","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
			}
			else{
				JOptionPane.showMessageDialog(this, "Id Inexistente","Erro ao atualizar", JOptionPane.OK_CANCEL_OPTION);
			}
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
   
    //Aqui eu tenho que pegar todos os livros do EstoqueManager(que acessa o BD de livros por sua vez)
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
