package bbdd;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import aplicacion.Artigo;

public final class ArtigoDAO extends AbstractDAO {

    public ArtigoDAO(Connection conexion){
        super.setConexion(conexion);
    }

    public Integer comprobarExistencias(String id, PrintWriter saida){
        Connection conexion;
        PreparedStatement stmProduto = null;
        ResultSet rsProduto;

        conexion = this.getConexion();

        try {
            stmProduto = conexion.prepareStatement("select cantidade from produto where id = ?");
            stmProduto.setInt(1, Integer.valueOf(id));

            rsProduto = stmProduto.executeQuery();
            
            if (rsProduto.next()){ return rsProduto.getInt("cantidade"); }
        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        } finally {
            try { stmProduto.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
        }
        return null;
    }

    public Artigo obterProduto(String id, PrintWriter saida){
        Connection conexion;
        PreparedStatement stmArtigo = null;
        ResultSet rsArtigo;

        conexion = this.getConexion();

        try {
            stmArtigo = conexion.prepareStatement("select * from produto where id = ?");
            stmArtigo.setInt(1, Integer.valueOf(id));

            rsArtigo = stmArtigo.executeQuery();
            
            if (rsArtigo.next()){
                return new Artigo(Integer.valueOf(rsArtigo.getInt("id")).toString(), rsArtigo.getString("nome"), rsArtigo.getString("descricion"), rsArtigo.getDouble("prezo"), rsArtigo.getInt("cantidade"), rsArtigo.getString("imaxe"));
            }
        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        } finally {
            try { stmArtigo.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
        }
        return null;
    }

    public Boolean eliminarStock(String id, Integer cantidade, PrintWriter saida){
        Connection conexion;
        PreparedStatement stmArtigo = null;
        
        if (cantidade > this.comprobarExistencias(id, saida)) { return false;}
        
        conexion = this.getConexion();

        try {
            stmArtigo = conexion.prepareStatement("update produto set cantidade = cantidade - ? where id = ?");
            stmArtigo.setInt(1, cantidade);
            stmArtigo.setInt(2, Integer.valueOf(id));

            stmArtigo.executeUpdate();
            return true;
        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        } finally {
            try { stmArtigo.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
        }
        return false;
    }

    public List<Artigo> obterStock(PrintWriter saida){
        Connection conexion;
        PreparedStatement stmStock = null;
        ResultSet rsStock;
        List<Artigo> stock = new ArrayList<>();

        conexion = this.getConexion();

        try {
            stmStock = conexion.prepareStatement("select * from produto");
            rsStock = stmStock.executeQuery();

            while(rsStock.next()){
                stock.add(new Artigo(Integer.valueOf(rsStock.getInt("id")).toString(), rsStock.getString("nome"), rsStock.getString("descricion"), rsStock.getDouble("prezo"),rsStock.getInt("cantidade"), rsStock.getString("imaxe")));
            }
            
        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        } finally {
            try { stmStock.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
        }

        return stock;
    }

}