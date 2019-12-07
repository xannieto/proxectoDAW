package servletdaw;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import bbdd.FachadaBaseDatos;

import java.util.*;

public class servletDaw extends HttpServlet {
    private String mensaxe;
    private FachadaBaseDatos fa;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            String URL = getServletConfig().getInitParameter("urlP"); 
            String usuario = getServletConfig().getInitParameter("usuarioP");
            String contrasinal = getServletConfig().getInitParameter("contrasinalP");

            this.fa = new FachadaBaseDatos(getServletConfig().getInitParameter("driver"), URL, usuario, contrasinal);
        } catch(InstantiationError e) {
            mensaxe = "Obxecto non atopado: " + e.getMessage();
        }  catch(Exception e){
            mensaxe = "Excepcion: " + e.getMessage();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        PrintWriter saida = response.getWriter();
        String nome = request.getParameter("nome"), apelidos = request.getParameter("apelidos"), email = request.getParameter("email");
        Integer id = fa.darDeAltaBDAlumnos(nome, apelidos, email, saida);
        
        response.setContentType("text/html");

        if (id instanceof Integer){
            /* gardando parametros para despois */
            sc.setAttribute("nome", nome); sc.setAttribute("apelidos", apelidos); sc.setAttribute("email", email);
            sc.setAttribute("dni", request.getParameter("dni")); sc.setAttribute("estado_civil",request.getParameter("estado_civil"));
            sc.setAttribute("data_nacemento", request.getParameter("data_nacemento")); sc.setAttribute("provincia",request.getParameter("provincia"));
            sc.setAttribute("direccion", request.getParameter("direccion")); sc.setAttribute("codigo_postal",request.getParameter("codigo_postal"));
            sc.setAttribute("numero_telefono", request.getParameter("numero_telefono"));

            /* creación da cookie */
            Cookie galleta = new Cookie("nietoGarcia", String.valueOf(id));
            galleta.setMaxAge(60*30); 
            response.addCookie(galleta);

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

}