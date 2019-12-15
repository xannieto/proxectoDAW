<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" import="javax.servlet.http.*" %>
<%@page isELIgnored="false"%>

<!DOCTYPE html>

<html lang="gl">
    <head>
        <meta charset="UTF-8" >
        <meta name="keywords" content="casa, rural, turismo, gastronomia" >
        <meta name="author" content="Juan Carlos Nieto García" >
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="A túa casa rural" >

        <title> Ruralia </title>
        <link rel="stylesheet" media=screen href="./css/bootstrap-grid.css">
        <link rel="stylesheet" media=screen href="./css/formulario.css">
        <link rel="stylesheet" media=screen href="./css/principal.css">
        <link rel="stylesheet" meida=screen href="./css/tenda.css">
        <script type="text/javascript" src="./js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="./js/popper.min.js"></script>
        <script type="text/javascript" src="./js/bootstrap.min.js"></script>

        <script>
            $(document).ready(function(){
                /* barra de navegación fixada ao desprazar */
                const sticky = $("#barra_navegacion").last().offset().top
                $(window).scroll(function(){
                    if ($(window).scrollTop() >= sticky) {
                        $("#barra_navegacion").addClass("sticky");
                    } else {
                        $("#barra_navegacion").removeClass("sticky");
                    }
                });

                $(window).resize(function(){
                    if ($(window).width() > 700 && $("nav > ul > li").css("display") == "none"){
                        $("nav > ul > li").css("display","block");
                    } else if ($(window).width() <= 700 && $("nav > ul > li").css("display") == "block"){
                        $("nav > ul > li").css("display","none");
                    }
                });

                $("#boton_menu").click(function(){
                    if ($("nav > ul > li").css("display") == "block" ){
                        $("nav > ul > li").css("display","none");
                    } else {
                        $("nav > ul > li").css("display","block");
                    }
                });

                
            });
        </script>
    </head> 
    
    <body class="container-fluid">
        <header class="row">
            <figure class="col-12 imaxe-cabeceira">
                <a href="./index.html">
                    <img src="./imaxes/logo.svg" alt="Logotipo Ruralia" width="256" height="65">
                </a>
            </figure>
            <nav class="col-12" id="barra_navegacion">
                <div class="menu-hamburguesa">
                    <a href="javascript:void(0);" id="boton_menu">
                    <figure class="imaxe-menu-hamburguesa">
                        <img src="./imaxes/menu_hamburguesa.svg" width="32">
                    </figure>
                    <h1>Menú</h1>
                    </a>
                </div>
                <ul class="menu-principal">
                    <c:choose>
                        <c:when test="${(!empty cookie.nome.value) and (!empty cookie.email.value)}">
                            <li>
                                <a> Ola, ${cookie.nome.value} </a>
                                <ul class="submenu">
                                    <li> <a href="./jsp/logout.jsp"> Pechar sesión </a> </li>
                                </ul>
                            </li>
                        </c:when>
                    </c:choose>
                    <li>
                        <a href="./index.html"> Sobre nós </a>
                    </li>
                    <li>
                        <a href="./recursos/instalacions.html">
                            Instalacións
                        </a>
                        <ul class="submenu">
                            <li> <a href="./recursos/historia.html"> Historia </a> </li>
                            <li> <a href="./recursos/vinha.html"> A viña </a> </li>
                            <li> <a href="./recursos/xardins.html"> Xardíns </a> </li>
                            <li> <a href="./recursos/lareira.html"> Lareira </a> </li>
                            <li> <a href="./recursos/comedor.html"> Comedor </a> </li>
                            <li> <a href="./recursos/habitacions.html"> Habitacións </a> </li>
                        </ul>
                    </li>
                    <li>
                        <a href="./recursos/gastronomia.html">
                            Gastronomía
                        </a>
                        <ul class="submenu">
                            <li> <a href="./recursos/comida.html"> Comida </a> </li>
                            <li> <a href="./recursos/bebida.html"> Bebida </a> </li>
                        </ul>
                    </li>
                    <li>
                        <a href="./recursos/lecer.html">
                            Lecer
                        </a>
                    </li>
                    <li>
                        <form action="./compra" method="POST" name="entrarTenda" class="form-tenda">
                            <input type="hidden" id="opcion" name="opcion" value="iniciarCompra">
                            <input type="submit" id="submit" name="submit" value="Tenda">
                        </form>
                    </li>
                    <li id="li_info" >
                        <a href="./recursos/contacto.html">
                            Contacto
                        </a>
                        <ul class="submenu">
                            <li> <a href="./recursos/info_contacto.html"> Horarios e teléfono </a> </li>
                            <li> <a href="./recursos/formulario_reserva.html"> Dar de alta </a> </li>
                            <li> <a href="./recursos/modificar_reserva.html"> Rexistro </a> </li>
                            <li> <a href="./recursos/login.html"> Login </a> </li>
                        </ul>   
                    </li>
                </ul>
            </nav>
        </header>

        <section class="row">
            <h1 class="col-12 titulo"> Factura </h1> 
            <fmt:setLocale value="es_ES"/>
            <article class="col-sm-10 col-md-10 artigo">
                <c:set var="total" value="${0}"/>
                <table class="factura">
                    <tr>
                        <th> Produto </th>
                        <th> Prezo unitario </th>
                        <th> Cantidade </th>
                        <th> Prezo total (sen IVE) </th>
                        <th> Prezo total (IVE incluído) </th>
                    </tr>
                    <c:forEach var="artigo" items="${sessionScope.carro.artigos}">
                    <tr>
                        <c:set var="prezoActual" value="${artigo.prezo*artigo.seleccion}" />
                        <td> <c:out value="${artigo.nome}"/> </td>
                        <td> <fmt:formatNumber value = "${artigo.prezo}" currencySymbol="&euro;" type ="currency"/></td>
                        <td> <c:out value="${artigo.seleccion}"/> </td>
                        <td> <fmt:formatNumber value="${prezoActual}" currencySymbol="&euro;" type="currency"/> </td>
                        <td> <fmt:formatNumber value="${prezoActual*1.21}" currencySymbol="&euro;" type="currency"/> </td>
                        <c:set var="total" value="${total + prezoActual * 1.21}" />
                    </tr>
                    </c:forEach>
                    <tr>
                        <th> TOTAL </th>
                        <th colspan="3"> </th>
                        <th> <fmt:formatNumber value="${total}" currencySymbol="&euro;" type="currency"/></th>
                    </tr>
                </table>
            </article>
            <article class="col-sm-10 col-md-10 artigo">
                <form action="./compra" method="POST" name="form-carro" class="form-botons">                    
                    <input type="hidden" name="opcion" value="rematarCompra">
                    <input id="id_rematar" type="submit" name="submit" value="Rematar a compra">
                </form>
                <form action="./compra" method="POST" name="form-carro" class="form-botons">                    
                    <input type="hidden" name="opcion" value="volverTenda">
                    <input id="id_voltar" type="submit" name="submit" value="Voltar">
                </form>
            </article>
        </section>

        <section class="row">
            <footer class="col-md-12 col-sm-12 pe-paxina">
                <h1> Síguenos nas nosas redes sociais!</h1>
                
                <p id="p_redes">
                    <a href="https://www.facebook.com/ruralia/"><img src="./imaxes/facebook.png" title="Facebook" width="32" height="32"></a>  
                    <a href="https://www.twitter.com/ruralia/"><img src="./imaxes/twitter.png" title="Twitter" width="32" height="32"></a>
                    <a href="https://www.instagram.com/ruralia/"><img src="./imaxes/instagram.png" title="Instagram" width="32" height="32"></a>
                </p>
                    
                <p class="font-italic"> Ruralia © - Tódolos dereitos reservados </p>
            </footer>
        </section>
    </body>
</html>
    
