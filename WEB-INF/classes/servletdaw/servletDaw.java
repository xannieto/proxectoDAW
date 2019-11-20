package servletdaw;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class servletDaw extends HttpServlet {
    private Connection conexion;
    private String mensaxe;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            Class.forName(getServletConfig().getInitParameter("driver"));

            String URL = getServletConfig().getInitParameter("urlP"); 
            String usuario = getServletConfig().getInitParameter("usuarioP");
            String contrasinal = getServletConfig().getInitParameter("contrasinalP");

            conexion = DriverManager.getConnection(URL, usuario, contrasinal);
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
        String nome = request.getParameter("nome"), apelidos = request.getParameter("apelidos"), email = request.getParameter("email");
        
        Cookie galleta = this.darDeAlta(nome, apelidos, email, saida);
       
        if (galleta instanceof Cookie){
            response.addCookie(galleta);

            /* gardando parametros para despois */
            sc.setAttribute("nome", nome); sc.setAttribute("apelidos", apelidos); sc.setAttribute("email", email);
            sc.setAttribute("dni", request.getParameter("dni")); sc.setAttribute("estado_civil",request.getParameter("estado_civil"));
            sc.setAttribute("data_nacemento", request.getParameter("data_nacemento")); sc.setAttribute("provincia",request.getParameter("provincia"));
            sc.setAttribute("direccion", request.getParameter("direccion")); sc.setAttribute("codigo_postal",request.getParameter("codigo_postal"));
            sc.setAttribute("numero_telefono", request.getParameter("numero_telefono"));

            saida.println("<!DOCTYPE html>");
            saida.println("<html>");
            saida.println("<head> <meta charset=\"UTF-8\"> </head>");
            saida.println("<body>");
            saida.println("<a href=\""+ getServletContext().getInitParameter("ligazon_rexistro") +"\" style=\"color : ligthcoral \">Prema aquí</a>");
            saida.println("<p>Id (importante non esquecerse del): " + galleta.getValue() + "</p>");
            saida.println("</body>");
            saida.println("</html>");
        
        } else {
            saida.println("<!DOCTYPE html>");
            saida.println("<html>");
            saida.println("<head> <meta charset=\"UTF-8\"> </head>");
            saida.println("<body>");
            saida.println("<h2 style=\"color : lightcoral\" >MENSAXE: Vostede xa realizou este paso anteriormente</h2>");
            saida.println("<a href=\""+ getServletContext().getInitParameter("ligazon_rexistro") +"\" style=\"color : ligthcoral \">Prema aquí</a>");
            saida.println("</body>");
            saida.println("</html>");
        }       
        
    }

    private Cookie darDeAlta(String nome, String apelidos, String email, PrintWriter saida){
        Cookie galleta = null;
        Boolean alta = Boolean.TRUE;
        PreparedStatement stmConsulta,stmInsercion;
        ResultSet rsConsulta;

        try {
            stmConsulta = conexion.prepareStatement("select email from altadaw");
            rsConsulta = stmConsulta.executeQuery("select email from altadaw");

            while(rsConsulta.next()){
                if (rsConsulta.getString("email").equals(email)){
                    alta = Boolean.FALSE;
                }
            }

            if (alta){
                try {
                    stmInsercion = conexion.prepareStatement("insert into altadaw (nombre, apellidos, email) values (?,?,?)");
                    stmInsercion.setString(1, nome);
                    stmInsercion.setString(2, apelidos);
                    stmInsercion.setString(3, email);
                    stmInsercion.executeUpdate();

                    stmConsulta = conexion.prepareStatement("select id from altadaw where email = ?");
                    stmConsulta.setString(1, email);
                    rsConsulta = stmConsulta.executeQuery();

                    if (rsConsulta.next()){
                        galleta = new Cookie("nietoGarcia",String.valueOf(rsConsulta.getInt("id")));
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

        return galleta;
    }

    public void destroy(){
        try {
            conexion.close();

        } catch (SQLException e){
            System.out.println("Problema de inserción SQL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Excepcion: " + e.getMessage());
        }
    }
}