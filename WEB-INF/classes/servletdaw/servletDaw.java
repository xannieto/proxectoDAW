package servletdaw;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class servletDaw extends HttpServlet {
    private Connection conexionP;
    private Connection conexionA;
    private String mensaxe;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            String controlador = "org.mariadb.jdbc.Driver";
            //String controlador = "com.mysql.cj.jdbc.Driver"

            //Class.forName(controladorBDProfesor).getDeclaredConstructor().newInstance();
            Class.forName(controlador);

            mensaxe = getServletConfig().getInitParameter("urlP");
            String URL_BD_PROFESOR = getServletConfig().getInitParameter("urlP"); 
            String URL_BD_ALUMNO = getServletConfig().getInitParameter("urlA");
            String usuario = getServletConfig().getInitParameter("usuario");
            String contrasinal = getServletConfig().getInitParameter("contrasinal");

            conexionP = DriverManager.getConnection(URL_BD_PROFESOR, usuario, contrasinal);
            conexionA = DriverManager.getConnection(URL_BD_ALUMNO, usuario, contrasinal);

        } catch(ClassNotFoundException e) {
            mensaxe = "Clase non atopada: " + e.getMessage();
        } catch(InstantiationError e) {
            mensaxe = "Obxecto non atopado: " + e.getMessage();
        }/* catch(IllegalAccessException e) { 
			mensaxe = "Acceso ilegal: " + e.getMessage();
		}*/ catch(SQLException e) { 
			mensaxe = "Excepcion SQL: " + e.getMessage();
		} catch(Exception e){
            mensaxe = "Excepcion: " + e.getMessage();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter saida = response.getWriter();
        String nome = request.getParameter("nome"), apelidos = request.getParameter("apelidos"), email = request.getParameter("email");
        Cookie galleta = this.darDeAlta(nome, apelidos, email, saida);
       
        response.addCokie(galleta);

        saida.println("Chegaches ate aqui");
    }

    private Cookie darDeAlta(String nome, String apelidos, String email, PrintWriter saida){
        Boolean alta = Boolean.TRUE;
        PreparedStatement stmConsulta,stmInsercion;
        ResultSet rsConsulta;

        try {
            stmConsulta = conexionP.prepareStatement("select email from altadaw");
            rsConsulta = stmConsulta.executeQuery("select email from altadaw");

            while(rsConsulta.next()){
                if (rsConsulta.getString("email").equals(email)){
                    alta = Boolean.FALSE;
                }
            }

            if (alta){
                try {
                    stmInsercion = conexionP.prepareStatement("insert into altadaw (nombre, apellidos, email) values (?,?,?)");
                    stmInsercion.setString(1, nome);
                    stmInsercion.setString(2, apelidos);
                    stmInsercion.setString(3, email);
                    stmInsercion.executeUpdate();

                    stmConsulta = conexionP.prepareStatement("select id from altadaw where email = ?");
                    stmConsulta.setString(1, email);
                    rsConsulta = stmConsulta.executeQuery();

                    if (rsConsulta.next()){
                        Cookie galleta = new Cookie("nietoGarcia",rsConsulta.getInt("id"));
                        galleta.setMaxAge(60*30); //está en segundos

                        return galleta;
                    }

                } catch (SQLException e){
                    saida.println("Problema de inserción SQL: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace(saida);
                }

            }

        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        }

        return null;
    }

}