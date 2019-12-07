package bbdd;

import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;

import aplicacion.*;

public final class FachadaBaseDatos {
    private Connection conexion;
    private ArtigoDAO artigoDAO;
    private UsuariosDAO usuarioDAO;
    private CarroDAO carroDAO;

    public FachadaBaseDatos(String driver, String url, String user, String password){
        try {
            Class.forName(driver).newInstance();
            this.conexion = DriverManager.getConnection(url, user, password);

            this.artigoDAO = new ArtigoDAO(this.conexion);
            this.usuarioDAO = new UsuariosDAO(this.conexion);
            this.carroDAO = new CarroDAO(this.conexion);
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
        return usuarioDAO.darDeAltaBDAlumnos(nome, apelidos, email, saida);
    }

    public Boolean existeUsuarioBDAlumnos(String id, PrintWriter saida) {
        return usuarioDAO.existeUsuarioBDAlumnos(id, saida);
    }

    public Boolean darDeAltaBDPropia(Usuario usuario, PrintWriter saida) {
        return usuarioDAO.darDeAltaBDPropia(usuario, saida);
    }

    public Boolean verificarAcceso(String email, String contrasinal, PrintWriter saida) {
        return usuarioDAO.verificarAcceso(email, contrasinal, saida);
    }

    /*  */


}