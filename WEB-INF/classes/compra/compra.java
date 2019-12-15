package compra;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;

import aplicacion.*;
import bbdd.FachadaBaseDatos;

public final class compra extends HttpServlet {

    public FachadaBaseDatos fa;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
       
        this.fa = new FachadaBaseDatos(getServletConfig().getInitParameter("driver"), getServletConfig().getInitParameter("url"),
            getServletConfig().getInitParameter("usuario"), getServletConfig().getInitParameter("contrasinal"));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sesion = request.getSession(false);
        PrintWriter saida = response.getWriter();
        RequestDispatcher vista, verCarro;
        List<Artigo> stock;
        List<Artigo> artigosProblematicos;
        Carro carro;
        Artigo artigo;
        String opcion = request.getParameter("opcion");

        if (sesion == null){
            sesion = request.getSession();
            carro = new Carro();
            sesion.setAttribute("carro", carro);         

        } else {
            carro = (Carro) sesion.getAttribute("carro");

            if (carro == null){
                carro = new Carro();
                sesion.setAttribute("carro", carro);
            }  

        }

        switch(opcion){

            case "iniciarCompra":
                stock = (ArrayList<Artigo>)fa.obterStock(saida);
                sesion.setAttribute("stock", stock);

                vista = request.getRequestDispatcher("jsp/tenda.jsp");
                vista.forward(request, response);
                break;
                
            case "engadirArtigo":
                artigo = fa.obterProduto(request.getParameter("identificador"), saida);

                synchronized(sesion){
                    if (artigo != null){
                        Integer seleccion = Integer.valueOf(request.getParameter("seleccion"));
                        artigo.setSeleccion(seleccion);
                        artigo.setExistencias(artigo.getExistencias() - seleccion);
                        carro.engadirArtigo(artigo);
                    } else {
                        saida.println("Problema para atopar o artigo");
                    }
                }

                vista = request.getRequestDispatcher("jsp/tenda.jsp");
                vista.forward(request, response);
                break;

            case "verCarro":
                verCarro = request.getRequestDispatcher("jsp/carro.jsp");
                verCarro.forward(request, response);
                break;

            case "formalizarCompra":
                vista = request.getRequestDispatcher("jsp/resumo.jsp");    
                vista.forward(request, response);
                break;

            case "quitarArtigo":
                Integer seleccion = Integer.valueOf(request.getParameter("seleccion"));
                String id = request.getParameter("identificador");
                
                synchronized(sesion){
                    if (seleccion > 0){
                        artigo = fa.obterProduto(id, saida);
                        carro.quitarArtigo(artigo, seleccion);
                        sesion.setAttribute("carro",carro);
                    }
                }

                vista = request.getRequestDispatcher("jsp/carro.jsp");    
                vista.forward(request, response);
                break;
            
            case "quitarTodoArtigoProblematico":
                artigosProblematicos = (ArrayList<Artigo>)sesion.getAttribute("artigosProblematicos");
                
                synchronized(carro){
                    for (Artigo art: artigosProblematicos){
                        carro.quitarArtigo(art, art.getSeleccion() - art.getExistencias());
                    }
                }   
                vista = request.getRequestDispatcher("jsp/carro.jsp");    
                vista.forward(request, response);
                break;

            case "volverTenda":
                vista = request.getRequestDispatcher("jsp/tenda.jsp");
                vista.forward(request, response);
                break;

            case "rematarCompra":
                String nome = null, email = null;
                
                for (Cookie ck: request.getCookies()){
                    if (ck.getName().equals("nome")){
                        nome = ck.getValue();
                    } else if (ck.getName().equals("email")) {
                        email = ck.getValue();
                    }
                }
                
                if (nome == null || email == null){
                    response.sendRedirect("./recursos/login.html");
                
                } else {
                    artigosProblematicos = new ArrayList<>();
                    for (Artigo art: carro.getArtigos()){
                        Integer existencias = fa.comprobarExistencias(art.getId(), saida);
                        if (art.getSeleccion() > existencias){
                            art.setExistencias(existencias);
                            artigosProblematicos.add(art);
                        }
                    }

                    if (!artigosProblematicos.isEmpty()){
                        sesion.setAttribute("artigosProblematicos", artigosProblematicos);
                        vista = request.getRequestDispatcher("jsp/erroCompra.jsp");
                        vista.forward(request, response);
                    } else {
                        Usuario usuario = new Usuario(email);
                        synchronized(sesion){
                            fa.realizarCompra(carro, usuario, saida);
                            
                            
                            
                            String emailContacto = getServletContext().getInitParameter("email_contacto");
                            String telefonoContacto = getServletContext().getInitParameter("telefono_contacto");
    
                            ServletContext aplicacion= request.getServletContext();
                            aplicacion.setAttribute("email_contacto", emailContacto);
                            aplicacion.setAttribute("telefono_contacto", telefonoContacto);
                            
                            if (sesion != null) sesion.invalidate();
                        }
                        
                        vista = request.getRequestDispatcher("jsp/final.jsp");
                        vista.forward(request, response);
                    }
                }                
                break;
        }   
    }
}
