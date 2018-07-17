package bd;

import principais.EstoqueManager;
import principais.Livro;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OperacoesLivrosPacotes extends JavaConnection implements Operacoes
{

    @Override
    public void INSERT_DATA(Object obj) {
    }

    public void UPDATE_DATA(Object obj)
    {}

    public void INSERT_DATA(int livroId, int pacoteId){

        try{
            ConnectBd();
            connection.setAutoCommit(false);
            stmt  = connection.createStatement();

            String sql = "INSERT INTO LIVROS_PACOTES (id_livro, id_pacote)"
                    + "VALUES (" + "'" + livroId + "'" + "," +
                    "'" + pacoteId +  "');";

            stmt.executeUpdate(sql);
            connection.commit();
            stmt.close();
            connection.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void DELETE_LIVRO_DE_PACOTE(int livroId, int pacoteId){
        try{
            ConnectBd();
            connection.setAutoCommit(false);
            stmt  = connection.createStatement();


            String sql = "DELETE FROM LIVROS_PACOTES WHERE id_livro = " + livroId + "AND id_pacote = " + pacoteId;
            stmt.executeUpdate(sql);

            connection.commit();
            this.closeConnections();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void GET_AND_SET_ALL_DATA(){
    }

    public List<Livro> GET_LIVROS_DE_PACOTE(int pacoteId){

        List<Livro> livros = new ArrayList<Livro>();
        try{
            this.ConnectBd();
            stmt = connection.createStatement();

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM LIVROS_PACOTES WHERE id_pacote");
            while (resultSet.next()){
                int id = resultSet.getInt("id_livro");
                Livro livro = EstoqueManager.getInstance().getLivroPeloId(id);
                livros.add(livro);
            }
            this.closeConnections();
        } catch(Exception e){}


        return livros;
    }

}
