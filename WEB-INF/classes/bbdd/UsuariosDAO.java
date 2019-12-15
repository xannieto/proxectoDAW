package bbdd;

import java.io.PrintWriter;
import java.sql.*;

import aplicacion.Usuario;

public final class UsuariosDAO extends AbstractDAO {

    public UsuariosDAO(Connection conexion){
        super.setConexion(conexion);
    }

    public Integer darDeAltaBDAlumnos(String nome, String apelidos, String email, PrintWriter saida){
        Connection conexion;
        PreparedStatement stmEmail = null , stmUsuario = null, stmId = null;
        ResultSet rsEmail, rsId;

        conexion = this.getConexion();

        try {
            stmEmail = conexion.prepareStatement("select email from altadaw where email = ?");
            stmEmail.setString(1, email);
            rsEmail = stmEmail.executeQuery();

            if (!rsEmail.next()){ /* significa que hai que dalo de alta */
                try {
                    stmUsuario = conexion.prepareStatement("insert into altadaw (nombre, apellidos, email) values (?,?,?)");
                    stmUsuario.setString(1, nome);
                    stmUsuario.setString(2, apelidos);
                    stmUsuario.setString(3, email);
                    stmUsuario.executeUpdate();

                    stmId = conexion.prepareStatement("select id from altadaw where email = ?");
                    stmId.setString(1, email);
                    rsId = stmId.executeQuery();

                    if (rsId.next()) {
                        return Integer.valueOf(rsId.getInt("id"));
                    
                    } else {
                        return null;
                    
                    }
                    
                } catch (SQLException e){
                    saida.println("Problema de inserción SQL: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace(saida);
                } finally {
                    try { stmUsuario.close(); stmId.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
                }
            } else {
                return null;
            }

        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        } finally {
            try { stmEmail.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
        }
        return null;
    }

    public Boolean existeUsuarioBDAlumnos(String id, PrintWriter saida){
        Connection conexion;
        PreparedStatement stmAlumno = null;
        ResultSet rsAlumno;

        conexion = this.getConexion();

        try {
            stmAlumno = conexion.prepareStatement("select id from altadaw where id = ?");
            stmAlumno.setInt(1, Integer.valueOf(id));
            rsAlumno = stmAlumno.executeQuery();

            return rsAlumno.next();

        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        } finally {
            try { stmAlumno.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
        }
        return false;
    }

    public Boolean darDeAltaBDPropia(Usuario usuario, PrintWriter saida){
        PreparedStatement stmUsuario = null;
        Connection conexion;

        conexion = this.getConexion();

        try {
            stmUsuario = conexion.prepareStatement("insert into usuario "+
                    "(nome, apelidos, email, id, contrasinal, dni, estado_civil, data_nacemento, provincia, direccion, codigo_postal, numero_telefono) "+
                    "values (?,?,?,?,?,?,?,?,?,?,?,?)");

            stmUsuario.setString(1, usuario.getNome());
            stmUsuario.setString(2, usuario.getApelidos());
            stmUsuario.setString(3, usuario.getEmail());
            stmUsuario.setInt(4, Integer.valueOf(usuario.getId()));
            stmUsuario.setString(5, usuario.getContrasinal());
            stmUsuario.setString(6, usuario.getDni());
            stmUsuario.setString(7, usuario.getEstadoCivil());
            stmUsuario.setString(8, usuario.getDataNacemento());
            stmUsuario.setString(9, usuario.getProvincia());
            stmUsuario.setString(10, usuario.getDireccion());
            stmUsuario.setString(11, usuario.getCodigoPostal());
            stmUsuario.setString(12, usuario.getNumeroTelefono());

            stmUsuario.executeUpdate();
            
            return true;

        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        } finally {
            try { stmUsuario.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
        }
        
        return false;
    }

    public Usuario verificarAcceso(String email, String contrasinal, PrintWriter saida){
        Connection conexion;
        PreparedStatement stmUsuario = null;
        ResultSet rsUsuario;

        conexion = this.getConexion();

        try {
            stmUsuario = conexion.prepareStatement("select nome, email, id from usuario where email = ? and contrasinal = ?");
            stmUsuario.setString(1, email);
            stmUsuario.setString(2, contrasinal);

            rsUsuario = stmUsuario.executeQuery();

            if (rsUsuario.next()){
                return new Usuario(rsUsuario.getString("nome"), rsUsuario.getString("email"), String.valueOf(rsUsuario.getInt("id")));
            
            }

        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        } finally {
            try { stmUsuario.close(); } catch (SQLException e) { e.printStackTrace(saida);} 
        }

        return null;
    }
    
}