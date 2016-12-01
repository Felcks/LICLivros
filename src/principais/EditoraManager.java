package principais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.JavaConnection;

public class EditoraManager {
	
	private List<Editora> editoras;
	private Statement stmt;
	private Connection conn = null;
	private static EditoraManager editoraManager;
	
	private EditoraManager(){
		this.editoras = new ArrayList<Editora>();
	}
	
	public static EditoraManager getInstance(){
		if(editoraManager == null)
			editoraManager = new EditoraManager();
		
		return editoraManager;
	}
	
	public void getTodasEditorasDoBD(){
		try{
			JavaConnection.getInstance().ConnectBd();
			conn = JavaConnection.getInstance().connection;
			stmt = conn.createStatement();
			
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM EDITORAS");
			this.editoras.clear();
			while (resultSet.next()){
				Editora editora = new Editora(resultSet);
				this.editoras.add(editora);
			}
			resultSet.close();
			stmt.close();
		} catch(Exception e){}
	}
	
	public List<Editora> getEditoras(){
		return this.editoras;
	}
	
	public void adicionarNovaEditora(Editora editora){
		this.editoras.add(editora);
	}
	public void removerEditora(int index){
		this.editoras.remove(index);
	}
	public void atualizarEditora(int index, Editora editora){
		this.editoras.set(index, editora);
	}
	public int getIndexPeloNome(String nome){
		for(int i = 0; i < this.editoras.size(); i++){
			if(this.editoras.get(i).getNome().equals(nome)){
				return i;
			}
		}
		
		return -1;
	}
	
	public void reorganizarLista(){
		for(int i= 0; i < this.editoras.size(); i++){
			this.editoras.get(i).setId(i);
		}
	}
}
