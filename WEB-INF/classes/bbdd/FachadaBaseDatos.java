package bbdd;

import java.io.PrintWriter;
import java.sql.*;
import java.util.List;

import javax.servlet.*;

import aplicacion.*;

public final class FachadaBaseDatos {
    private Connection conexion;
    private ArtigoDAO daoArtigo;
    private UsuariosDAO daoUsuario;
    private TendaDAO daoTenda;

    public FachadaBaseDatos(String driver, String url, String user, String password){
        try {
            Class.forName(driver).newInstance();
            this.conexion = DriverManager.getConnection(url, user, password);

            this.daoArtigo = new ArtigoDAO(this.conexion);
            this.daoUsuario = new UsuariosDAO(this.conexion);
            this.daoTenda = new TendaDAO(this.conexion);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /* daoUsuarios */
    public Integer darDeAltaBDAlumnos(String nome, String apelidos, String email, PrintWriter saida) {
        return daoUsuario.darDeAltaBDAlumnos(nome, apelidos, email, saida);
    }

    public Boolean existeUsuarioBDAlumnos(String id, PrintWriter saida) {
        return daoUsuario.existeUsuarioBDAlumnos(id, saida);
    }

    public Boolean darDeAltaBDPropia(Usuario usuario, PrintWriter saida) {
        return daoUsuario.darDeAltaBDPropia(usuario, saida);
    }

    public Usuario verificarAcceso(String email, String contrasinal, PrintWriter saida) {
        return daoUsuario.verificarAcceso(email, contrasinal, saida);
    }

    /* daoArtigo */

    public Integer comprobarExistencias(String id, PrintWriter saida){
        return daoArtigo.comprobarExistencias(id, saida);
    }

    public Artigo obterProduto(String id, PrintWriter saida){
        return daoArtigo.obterProduto(id, saida);
    }

    public Boolean eliminarStock(String id, Integer cantidade, PrintWriter saida){
        return daoArtigo.eliminarStock(id, cantidade, saida);
    }

    public List<Artigo> obterStock(PrintWriter saida){
        return daoArtigo.obterStock(saida);
    }

    /* daoTenda */
    public void realizarCompra(Carro compra, Usuario usuario, PrintWriter saida){
        daoTenda.realizarCompra(compra, usuario, saida);
    }

}