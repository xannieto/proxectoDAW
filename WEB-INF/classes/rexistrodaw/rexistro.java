package rexistrodaw;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class rexistro extends HttpServlet {
    private Connection conexionP;
    private Connection conexionA;
    private String mensaxe;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            Class.forName(getServletConfig().getInitParameter("driver"));

            /* bd_alumnos */
            String URLP = getServletConfig().getInitParameter("urlP"); 
            String usuarioP = getServletConfig().getInitParameter("usuarioP");
            String contrasinalP = getServletConfig().getInitParameter("contrasinalP");

            /* nietoGarcia */
            String URLA = getServletConfig().getInitParameter("urlA"); 
            String usuarioA = getServletConfig().getInitParameter("usuarioA");
            String contrasinalA = getServletConfig().getInitParameter("contrasinalA");

            conexionP = DriverManager.getConnection(URLP, usuarioP, contrasinalP);
            conexionA = DriverManager.getConnection(URLA, usuarioA, contrasinalA);
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
        response.setContentType("text/html");
        ServletContext sc = getServletContext();
        PrintWriter saida = response.getWriter();
        
        if (comprobarConBDProfesor(request.getParameter("identificador"), saida)){
            PreparedStatement stmConsulta;

            try {
                stmConsulta = conexionA.prepareStatement("insert into usuario "+
                    "(nome, apelidos, email, id, contrasinal, dni, estado_civil, data_nacemento, provincia, direccion, codigo_postal, numero_telefono) "+
                    "values (?,?,?,?,?,?,?,?,?,?,?,?)");
                stmConsulta.setString(1, String.valueOf(sc.getAttribute("nome")));
                stmConsulta.setString(2, String.valueOf(sc.getAttribute("apelidos")));
                stmConsulta.setString(3, String.valueOf(sc.getAttribute("email")));
                stmConsulta.setInt(4, Integer.valueOf(request.getParameter("identificador")));
                stmConsulta.setString(5, request.getParameter("contrasinal"));
                stmConsulta.setString(6, String.valueOf(sc.getAttribute("dni")));
                stmConsulta.setString(7, String.valueOf(sc.getAttribute("estado_civil")));
                stmConsulta.setString(8, String.valueOf(sc.getAttribute("data_nacemento")));
                stmConsulta.setString(9, String.valueOf(sc.getAttribute("provincia")));
                stmConsulta.setString(10, String.valueOf(sc.getAttribute("direccion")));
                stmConsulta.setString(11, String.valueOf(sc.getAttribute("codigo_postal")));
                stmConsulta.setString(12, String.valueOf(sc.getAttribute("numero_telefono")));

                stmConsulta.executeUpdate();

                saida.println("<!DOCTYPE html>");
                saida.println("<html>");
                saida.println("<head> <meta charset=\"UTF-8\"> </head>");
                saida.println("<body>");
                saida.println("<p>Rexistrado satisfactoriamente</p>");
                saida.println("<p>Usuario: "+ sc.getAttribute("nome")+" Id: "+ request.getParameter("identificador") +"</p>");
                saida.println("<a href=\""+ getServletContext().getInitParameter("ligazon_home") +"\" style=\"color : ligthcoral \">Voltar a paxina de inicio</a>");
                saida.println("</body>");
                saida.println("</html>");
                
            } catch (SQLException e){
                saida.println("Problema de inserción SQL: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace(saida);
            }
            
        } else {
            saida.println("<!DOCTYPE html>");
            saida.println("<html>");
            saida.println("<head> <meta charset=\"UTF-8\"> </head>");
            saida.println("<body>");
            saida.println("<p>Non hai constancia do id proporcianado: "+ request.getParameter("identificador") +"</p>");
            saida.println("</body>");
            saida.println("</html>");
        }
        
    }

    private Boolean comprobarConBDProfesor(String id, PrintWriter saida){
        PreparedStatement stmConsulta;
        ResultSet rsConsulta;

        try {
            stmConsulta = conexionP.prepareStatement("select id from altadaw where id = ?");
            stmConsulta.setInt(1, Integer.valueOf(id));
            rsConsulta = stmConsulta.executeQuery();

            if (rsConsulta.next()) {
                return true;
            }

            return false;
        } catch (SQLException e){
            saida.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(saida);
        }
        return false;
    }

    public void destroy(){
        try {
            conexionP.close();
            conexionA.close();
        
        } catch (SQLException e){
            System.out.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Excepcion: " + e.getMessage());
        }
    }
}