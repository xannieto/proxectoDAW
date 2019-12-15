package bbdd;

import java.io.PrintWriter;
import java.sql.*;

import aplicacion.*;

public final class TendaDAO extends AbstractDAO {

    public TendaDAO(Connection conexion){
        super.setConexion(conexion);
    }

    public void realizarCompra(Carro compra, Usuario usuario, PrintWriter saida){
        Connection conexion;
        PreparedStatement stmItems = null, stmCompra = null;

        conexion = this.getConexion();
        
        for (Artigo art : compra.getArtigos()){
            try {
                stmItems = conexion.prepareStatement("update produto set cantidade = cantidade - ? where id = ?");
                stmItems.setInt(1, art.getSeleccion());
                stmItems.setInt(2, Integer.valueOf(art.getId()));

                stmItems.executeUpdate();

                try {
                    stmCompra = conexion.prepareStatement("insert into compra (email, produto, cantidade) values (?,?,?)");
                    stmCompra.setString(1, usuario.getEmail());
                    stmCompra.setInt(2, Integer.valueOf(art.getId()));
                    stmCompra.setInt(3, art.getSeleccion());

                    stmCompra.executeUpdate();

                } catch (SQLException e){
                    saida.println("Problema de inserción SQL: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace(saida);
                } finally {
                    try { stmCompra.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
                }
            } catch (SQLException e){
                saida.println("Problema de inserción SQL: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace(saida);
            } finally {
                try { stmItems.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
            }
        }

        

    }
}