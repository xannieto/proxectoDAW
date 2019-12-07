<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        <link rel="stylesheet" media=screen href="../css/bootstrap-grid.css">
        <link rel="stylesheet" media=screen href="../css/formulario.css">
        <link rel="stylesheet" media=screen href="../css/principal.css">
        <script type="text/javascript" src="../js/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="../js/popper.min.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>

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
                <a href="../index.html">
                    <img src="../imaxes/logo.svg" alt="Logotipo Ruralia" width="256" height="65">
                </a>
            </figure>
            <nav class="col-12" id="barra_navegacion">
                <div class="menu-hamburguesa">
                    <a href="javascript:void(0);" id="boton_menu">
                    <figure class="imaxe-menu-hamburguesa">
                        <img src="../imaxes/menu_hamburguesa.svg" width="32">
                    </figure>
                    <h1>Menú</h1>
                    </a>
                </div>
                <ul class="menu-principal">
                    <li>
                        <a href="../index.html"> Sobre nós </a>
                    </li>
                    <li>
                        <a href="./instalacions.html">
                            Instalacións
                        </a>
                        <ul class="submenu">
                            <li> <a href="./historia.html"> Historia </a> </li>
                            <li> <a href="./vinha.html"> A viña </a> </li>
                            <li> <a href="./xardins.html"> Xardíns </a> </li>
                            <li> <a href="./lareira.html"> Lareira </a> </li>
                            <li> <a href="./comedor.html"> Comedor </a> </li>
                            <li> <a href="./habitacions.html"> Habitacións </a> </li>
                        </ul>
                    </li>
                    <li>
                        <a href="./gastronomia.html">
                            Gastronomía
                        </a>
                        <ul class="submenu">
                            <li> <a href="./comida.html"> Comida </a> </li>
                            <li> <a href="./bebida.html"> Bebida </a> </li>
                        </ul>
                    </li>
                    <li>
                        <a href="./lecer.html">
                            Lecer
                        </a>
                    </li>
                    <li id="li_info" >
                        <a href="./contacto.html">
                            Contacto
                        </a>
                        <ul class="submenu">
                            <li> <a href="./info_contacto.html"> Horarios e teléfono </a> </li>
                            <li> <a href="./formulario_reserva.html"> Dar de alta</a> </li>
                            <li> <a href="./modificar_reserva.html"> Rexistro </a> </li>
                        </ul>   
                    </li>
                </ul>
            </nav>
        </header>

        <section class="row">
            <h1 class="col-12 titulo"> Acceso </h1> 
            <article class="col-sm-10 col-md-10 artigo">
                <h2> Formulario de acceso </h2>
                <form action="../sesion" method="POST" name="acceso" onsubmit="return validar();">
                    <label for="email">Usuario</label>
                    <input type="text" name="email" id="in_email" placeholder="Escribe o teu usuario..." required autofocus>

                    <label for="contrasinal">contrasinal</label>
                    <input type="password" name="contrasinal" id="in_contrasinal" placeholder="Escribe o contrasinal..." required>

                    <input type="reset" name="reset" id="reset" value="Borrar todo">
                    <input type="submit" id="submit" name="submit" value="Entrar">
                </form>
            </article>  
        </section>

        <section class="row">
            <footer class="col-md-12 col-sm-12 pe-paxina">
                <h1> Síguenos nas nosas redes sociais!</h1>
                
                <p id="p_redes">
                    <a href="https://www.facebook.com/ruralia/"><img src="../imaxes/facebook.png" title="Facebook" width="32" height="32"></a>  
                    <a href="https://www.twitter.com/ruralia/"><img src="../imaxes/twitter.png" title="Twitter" width="32" height="32"></a>
                    <a href="https://www.instagram.com/ruralia/"><img src="../imaxes/instagram.png" title="Instagram" width="32" height="32"></a>
                </p>
                    
                <p class="font-italic"> Ruralia © - Tódolos dereitos reservados </p>
            </footer>
        </section>

        <script>
            function validar() {
                var usuario, contrasinal;

                usuario = document.forms["acceso"]["email"].value;
                contrasinal = document.forms["acceso"]["contrasinal"].value;

                if (nome === "" || contrasinal === "" ) {
                    alert("Tódolos campos son obrigatorios");
                    return false;
                
                } else if (usuario.length > 40 ) {
                    alert("O nome de usuario é demasiado longo.");
                    return false;
                } else if (contrasinal.length > 40) {
                    alert("O contrasinal é demasiado longo.");
                    return false;
                }

                return true;
            }
        </script>
    </body>
</html>
    