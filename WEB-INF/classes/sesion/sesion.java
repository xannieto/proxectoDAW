package sesion;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import bbdd.FachadaBaseDatos;

public final class sesion extends HttpServlet {

    public FachadaBaseDatos fa;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
       
        this.fa = new FachadaBaseDatos(getServletConfig().getInitParameter("driver"), getServletConfig().getInitParameter("url"),
            getServletConfig().getInitParameter("usuario"), getServletConfig().getInitParameter("contrasinal"));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher vista;
        String email, contrasinal;
        PrintWriter saida = response.getWriter();
        response.setContentType("text/html");
        
        email = String.valueOf(request.getParameter("email"));
        contrasinal = String.valueOf(request.getParameter("contrasinal"));

        if (fa.verificarAcceso(email, contrasinal, saida)){
            HttpSession sesion = request.getSession(false);
            vista = request.getRequestDispatcher("jsp/sesion.jsp");

            if (sesion == null){
                sesion = request.getSession();

                sesion.setAttribute("email",email);
                sesion.setAttribute("id", sesion.getId());
                sesion.setAttribute("inicio","false");
                vista.forward(request, response);
            } else { 
                request.setAttribute("id", sesion.getId());
                request.setAttribute("inicio","true");
                vista.forward(request, response);
            }
        } else {
            saida.println("Fóra");
        }
    }
}
