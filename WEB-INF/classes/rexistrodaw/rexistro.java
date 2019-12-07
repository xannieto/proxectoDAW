package rexistrodaw;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import aplicacion.Usuario;
import bbdd.FachadaBaseDatos;

import java.util.*;

public class rexistro extends HttpServlet {
    private String mensaxe;
    private FachadaBaseDatos faProfesor, faAlumno;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            /* bd_alumnos */
            String URLP = getServletConfig().getInitParameter("urlP"); 
            String usuarioP = getServletConfig().getInitParameter("usuarioP");
            String contrasinalP = getServletConfig().getInitParameter("contrasinalP");

            /* nietoGarcia */
            String URLA = getServletConfig().getInitParameter("urlA"); 
            String usuarioA = getServletConfig().getInitParameter("usuarioA");
            String contrasinalA = getServletConfig().getInitParameter("contrasinalA");

            this.faProfesor = new FachadaBaseDatos(getServletConfig().getInitParameter("driver"), URLP, usuarioP, contrasinalP);
            this.faAlumno = new FachadaBaseDatos(getServletConfig().getInitParameter("driver"), URLA, usuarioA, contrasinalA); 
        } catch(InstantiationError e) {
            mensaxe = "Obxecto non atopado: " + e.getMessage();
        } catch(Exception e){
            mensaxe = "Excepcion: " + e.getMessage();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        ServletContext sc = getServletContext();
        PrintWriter saida = response.getWriter();
        
        if (faProfesor.existeUsuarioBDAlumnos(request.getParameter("identificador"), saida)){
            Usuario usuario = new Usuario(String.valueOf(sc.getAttribute("nome")), String.valueOf(sc.getAttribute("apelidos")), String.valueOf(sc.getAttribute("email")), String.valueOf(request.getParameter("identificador")), request.getParameter("contrasinal"),String.valueOf(sc.getAttribute("dni")), String.valueOf(sc.getAttribute("estado_civil")), String.valueOf(sc.getAttribute("provincia")), String.valueOf(sc.getAttribute("provincia")), String.valueOf(sc.getAttribute("direccion")), String.valueOf(sc.getAttribute("codigo_postal")), String.valueOf(sc.getAttribute("numero_telefono")));

            faAlumno.darDeAltaBDPropia(usuario, saida);

            saida.println("<!DOCTYPE html>");
            saida.println("<html>");
            saida.println("<head> <meta charset=\"UTF-8\"> </head>");
            saida.println("<body>");
            saida.println("<p>Rexistrado satisfactoriamente</p>");
            saida.println("<p>Usuario: "+ sc.getAttribute("nome")+" Id: "+ request.getParameter("identificador") +"</p>");
            saida.println("<a href=\""+ getServletContext().getInitParameter("ligazon_home") +"\" style=\"color : ligthcoral \">Voltar a paxina de inicio</a>");
            saida.println("</body>");
            saida.println("</html>");

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

}