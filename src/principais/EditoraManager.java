package principais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bd.JavaConnection;
import bd.Operacoes;
import bd.OperacoesEditoras;

public class EditoraManager {
	
	private List<Editora> editoras;
	private static EditoraManager editoraManager;
	private Operacoes operacoes;
	
	private EditoraManager(){
		this.editoras = new ArrayList<Editora>();
		this.operacoes = new OperacoesEditoras();
	}
	
	public static EditoraManager getInstance(){
		if(editoraManager == null)
			editoraManager = new EditoraManager();
		
		return editoraManager;
	}
	
	public Operacoes getOperacoes(){
		return this.operacoes;
	}
	
	public void getTodasEditorasDoBD(){
		this.editoras.clear();
		
		this.operacoes.GET_AND_SET_ALL_DATA();
	}
	
	public List<Editora> getEditoras(){
		return this.editoras;
	}
	
	public Editora getEditoraPeloId(int id){
		Editora editora = new Editora();
		for(int i = 0; i < editoras.size(); i++){
			if(id == this.editoras.get(i).getId())
				return  this.editoras.get(i);
		}
		return editora;
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
