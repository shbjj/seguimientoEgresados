<%-- 
    Document   : administrarUsuarios
    Created on : 4/06/2021, 12:39:09 PM
    Author     : hbdye
--%>

<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />

        <!-- Bootstrap CSS -->
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
            />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
            crossorigin="anonymous"
            />
        <script
            language="JavaScript"
            type="text/javascript"
            src="jquery/jquery-3.6.0.min.js"
        ></script>
        <link rel="stylesheet" href="css/estilos.css" />
        <title>Usuarios</title>
    </head>
    <body>
        <%
    String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

         if (tipoS != null)//Si se inicio sesión
         {
             if(tipoS.compareTo("2")==0) //Inicia sesión un admin
             {
                 int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                 if(rol==0 || rol==1)//Si es SuperAdministrador o Administrador, entonces puede administrar usuarios 
                 {    
                    //Obtener los Atributos enviados desde el Servlet
                    Usuario[] usuarios = (Usuario[]) request.getAttribute("USUARIOS");

        %>
        <div class="container">
            <%@ include file = "../navbar.jsp" %>
            
            <div class="row">
                <div class="col-sm-10">
                    <p class="fs-3 font-titulo-enc">Usuarios</p>
                </div>
                <div class="d-grid gap-2 col-sm-2 mx-auto py-2">
                    <button
                        class="btn btn-secondary bi bi-question-lg"
                        type="button"
                        data-bs-toggle="offcanvas"
                        data-bs-target="#offcanvasWithBackdrop"
                        aria-controls="offcanvasWithBackdrop"

                        >
                        Ayuda
                    </button>
                </div>
            </div>

            <div class="table-responsive-md">
                <table class="table table-striped table-hover">
                    <thead class="barra-card-enc text-white">
                        <tr>
                            <th scope="col" class="col-6">Nombre de usuario</th>
                            <th scope="col" class="col-2">Rol</th>
                            <th scope="col" class="col-3">Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (usuarios.length == 0) { %>
                        <tr>
                            <th scope="row" colspan="4" class="text-center">
                                No hay ningun usuario.
                            </th>
                        </tr>
                        <% } else {
                            for (int usuario = 0; usuario < usuarios.length; usuario++) 
                            {
                        %>   
                        <tr>
                            <th scope="row"><%=usuarios[usuario].getNombre()%>
                            </th>
                            <td class=""><%
                                if (usuarios[usuario].getRol().compareTo("1") == 0) { %>
                                <span class="text-success fw-bold">Administrador</span>   
                                <%} 
                                else 
                                { 
                                    if (usuarios[usuario].getRol().compareTo("2") == 0)
                                    { %>
                                        <span class="text-success fw-bold">Seguimiento de egresados</span>  
                                    <% }
                                    else
                                    { %>
                                        <span class="text-success fw-bold">Talleres</span>
                                    <% }
                                }
                                %>
                            </td>
                            <td class="">
                                <div class="row">
                                    
                                    <!--Boton editar-->
                                    <div class="col-lg-2 me-xl-2">
                                        <a href="<%=request.getContextPath()%>/CargarUsuario?nombre=<%=usuarios[usuario].getNombre()%>"
                                           class="btn btn-primary bi bi-pencil-fill"
                                           data-bs-toggle="tooltip" data-bs-placement="bottom" title="Editar"></a>
                                    </div>
                                    <!--Boton borrar-->
                                    <div class="col-lg-2 me-xl-2">
                                        <a href="<%=request.getContextPath()%>/EliminarUsuario?nombre=<%=usuarios[usuario].getNombre()%>"
                                           class="btn btn-danger bi bi-trash-fill"
                                           data-bs-toggle="tooltip" data-bs-placement="bottom" title="Borrar"
                                           onclick="return confirm('¿Esta seguro que desea borrar al usuario \''+
                                                       '<%=usuarios[usuario].getNombre()%>?\'')"></a>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        <% }
                            }%>
                    </tbody>
                </table>
            </div>

            
        </div>

        <!--
            Canvas o menu que emerge de la izquierda
        -->
        <div
            class="offcanvas offcanvas-start"
            tabindex="-1"
            id="offcanvasWithBackdrop"
            aria-labelledby="offcanvasWithBackdropLabel"
            >
            <div class="offcanvas-header barra-card-preg">
                <h5
                    class="offcanvas-title fs-3 font-titulo-enc fw-bold"
                    id="offcanvasWithBackdropLabel"
                    >
                    Ayuda
                </h5>
                <button
                    type="button"
                    class="btn-close text-reset"
                    data-bs-dismiss="offcanvas"
                    aria-label="Close"
                    ></button>
            </div>
            <div class="offcanvas-body body-card-preg">
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc">Ver detalles: </span>Permite
                    ver las preguntas y respuestas de la encuesta, así como también su
                    descripción, instrucciones, despedida, etc.
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc">Graficas de las respuestas: </span>Muestra 
                    las graficas de las respuestas de una encuesta.
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc">Editar: </span>Permite modificar los valores
                    de una encuesta, como introducción, instrucciones, preguntas, respuestas, etc.
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Deshabilitar: </span
                    >Desactiva una encuesta para que ya no se pueda contestar, sin
                    borrarla.
                </p>

                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Habilitar:</span> Cambia
                    el estado de una encuesta, para que ahora pueda ser contestada.
                </p>

                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Borrar</span> Elimina la
                    encuesta.
                    <span class="text-danger fw-bold"
                          >ATENCIÓN: Una vez borrada la encuesta, también se borraran las
                        respuestas que existan de ella, así como también sus
                        estadísticas.</span
                    >
                </p>
            </div>

        </div>
        <!-- Optional JavaScript; choose one of the two! -->

        <!-- Option 1: Bootstrap Bundle with Popper -->
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"
        ></script>

        <!-- Option 2: Separate Popper and Bootstrap JS -->

        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"
        ></script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"
            integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT"
            crossorigin="anonymous"
        ></script>
        <script>
            var offcanvasElementList = [].slice.call(
                    document.querySelectorAll(".offcanvas")
                    );
            var offcanvasList = offcanvasElementList.map(function (offcanvasEl) {
                return new bootstrap.Offcanvas(offcanvasEl);
            });

            var tooltipTriggerList = [].slice.call(
                    document.querySelectorAll('[data-bs-toggle="tooltip"]')
                    );
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl);
            });
        </script>

    </body>
</html>
<%
         }
                 else//Si no, no tiene permiso
                 {
                    request.setAttribute("NOMBRE_MENSAJE", "Error");
                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                    request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                    request.setAttribute("MENSAJEBOTON", "Volver");
                    request.setAttribute("DIRECCIONBOTON", "index.jsp");
                    request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                 }
                     
             }
             else//Iniicio sesión otra persona
             {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "index.jsp");
                request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
             }
         } else//no hay una sesión iniciada
         {
             //Redirigir al login
             response.sendRedirect(request.getContextPath() + "/login.jsp");
         }
%>