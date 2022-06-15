<%-- 
    Document   : administrar
    Created on : 16/08/2021, 03:02:49 PM
    Author     : hbdye
--%>
<%@page import="modelo.Taller"%>
<%
    String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

    if (tipoS != null)//Si se inicio sesión
    {
        if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
        {
            int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
            if (rol == 0 || rol == 1 || rol == 3)//Si es SuperAdministrador, Administrador o tallerista,
            //entonces puede administrar talleres 
            { %>

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
        <title>Talleres</title>
    </head>
    <body>
        <%

            //Obtener los Atributos enviados desde el Servlet
            Object[] talleresAbiertos = (Object[]) request.getAttribute("LISTA_Abierto");
            Object[] talleresCerrados = (Object[]) request.getAttribute("LISTA_Cerrado");

        %>
        <div class="container">
            <%@ include file = "../navbar.jsp" %>

            <div class="row">
                <div class="col-sm-7">
                    <p class="fs-3 font-titulo-enc">Talleres abiertos</p>
                </div>
                <div class="d-grid gap-2 col-sm-3 mx-auto py-2">
                    <a
                        class="btn btn-primary bi bi-plus-circle"
                        type="button"
                        data-bs-toggle="tooltip"
                        data-bs-placement="bottom"
                        title="Agregar una nueva taller" 
                        href="<%=request.getContextPath()%>/Taller/agregar.jsp"
                        >
                        Agregar taller
                    </a>
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
            <div style="height:225px;overflow:auto;"> 
                <div class="table-responsive-md">
                    <table class="table table-striped table-hover">
                        <thead class="barra-card-enc text-white">
                            <tr>
                                <th scope="col" class="col-3">Nombre</th>
                                <th scope="col" class="col-1">Clave</th>
                                <th scope="col" class="col-3">Instructor</th>
                                <th scope="col" class="col-2">Periodo</th>
                                <th scope="col" class="col-3">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (talleresAbiertos.length == 0) { %>
                            <tr>
                                <th scope="row" colspan="5" class="text-center">
                                    No hay ningun taller de este tipo.
                                </th>
                            </tr>
                            <% } else {
                                for (int taller = 0; taller < talleresAbiertos.length; taller++) {
                            %>   
                            <tr>
                                <th scope="row">
                                    <%=((Taller) talleresAbiertos[taller]).getNombre()%>
                                </th>
                                <td class="">
                                    <%=((Taller) talleresAbiertos[taller]).getClave()%>
                                </td>
                                <td class="">
                                    <%=((Taller) talleresAbiertos[taller]).getInstructor()%>
                                </td>
                                <td class="">
                                    <%=((Taller) talleresAbiertos[taller]).getPeriodo()%>
                                </td>
                                <td class="">
                                    <div class="row">
                                        <!--Boton ver taller-->
                                        <div class="col-lg-1 me-lg-4">
                                            <a href="<%=request.getContextPath()%>/VerTaller?idTaller=<%=((Taller) talleresAbiertos[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-eye-fill text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Ver detalles"></a>
                                        </div>
                                        <!--Boton lista de alumnos-->
                                        <div class="col-lg-1 me-lg-4">
                                            <a href="<%=request.getContextPath()%>/ListaAsistencia?idTaller=<%=((Taller) talleresAbiertos[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-card-list text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Lista de alumnos"></a>
                                        </div>
                                        <!--Boton calificar-->
                                        <div class="col-lg-1 me-lg-4">
                                            <a href="<%=request.getContextPath()%>/CalificarTaller?idTaller=<%=((Taller) talleresAbiertos[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-check2-square text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Calificar y cerrar taller"></a>
                                        </div>
                                        <!--Boton editar-->
                                        <div class="col-lg-1 me-lg-4">
                                            <a href="<%=request.getContextPath()%>/CargarDetallesTaller?idTaller=<%=((Taller) talleresAbiertos[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-pencil-fill"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Editar"></a>
                                        </div>
                                        <!--Boton borrar-->
                                        <div class="col-lg-1 me-lg-4">
                                            <a href="<%=request.getContextPath()%>/EliminarTaller?idTaller=<%=((Taller) talleresAbiertos[taller]).getIdTaller()%>"
                                               class="btn btn-danger bi bi-trash-fill"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Borrar"
                                               onclick="return confirm('¿Esta seguro que desea borrar el taller \'' +
                                                               '<%=((Taller) talleresAbiertos[taller]).getNombre()%>?\'')"></a>
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
            Talleres cerrados
            -->
            <div class="row mt-2">
                <div class="col-sm-7">
                    <p class="fs-3 font-titulo-enc">Talleres cerrados</p>
                </div>
            </div>
            <div style="height:225px;overflow:auto;"> 
                <div class="table-responsive-md">
                    <table class="table table-striped table-hover">
                        <thead class="barra-card-enc text-white">
                            <tr>
                                <th scope="col" class="col-3">Nombre</th>
                                <th scope="col" class="col-1">Clave</th>
                                <th scope="col" class="col-3">Instructor</th>
                                <th scope="col" class="col-2">Periodo</th>
                                <th scope="col" class="col-3">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (talleresCerrados.length == 0) { %>
                            <tr>
                                <th scope="row" colspan="5" class="text-center">
                                    No hay ningun taller de este tipo.
                                </th>
                            </tr>
                            <% } else {
                                for (int taller = 0; taller < talleresCerrados.length; taller++) {
                            %>   
                            <tr>
                                <th scope="row">
                                    <%=((Taller) talleresCerrados[taller]).getNombre()%>
                                </th>
                                <td class="">
                                    <%=((Taller) talleresCerrados[taller]).getClave()%>
                                </td>
                                <td class="">
                                    <%=((Taller) talleresCerrados[taller]).getInstructor()%>
                                </td>
                                <td class="">
                                    <%=((Taller) talleresCerrados[taller]).getPeriodo()%>
                                </td>
                                <td class="">
                                    <div class="row">
                                        <!--Boton ver taller-->
                                        <div class="col-lg-1 me-lg-3">
                                            <a href="<%=request.getContextPath()%>/VerTaller?idTaller=<%=((Taller) talleresCerrados[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-eye-fill text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Ver detalles"></a>
                                        </div>
                                        <!--Boton lista de alumnos-->
                                        <div class="col-lg-1 me-lg-3">
                                            <a href="<%=request.getContextPath()%>/ListaAlumnos?idTaller=<%=((Taller) talleresCerrados[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-card-list text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Lista de alumnos"></a>
                                        </div>
                                        <!--Boton calificar-->
                                        <div class="col-lg-1 me-lg-3">
                                            <a href="<%=request.getContextPath()%>/CalificarTaller?idTaller=<%=((Taller) talleresCerrados[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-check2-square text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Cambiar calificaciones"></a>
                                        </div>
                                        <!--Boton generar boletas-->
                                        <div class="col-lg-1 me-lg-3">
                                            <a href="<%=request.getContextPath()%>/GenerarBoletas?idTaller=<%=((Taller) talleresCerrados[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-collection text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Generar boletas"></a>
                                        </div>
                                        <!--Boton editar-->
                                        <div class="col-lg-1 me-lg-3">
                                            <a href="<%=request.getContextPath()%>/CargarDetallesTaller?idTaller=<%=((Taller) talleresCerrados[taller]).getIdTaller()%>"
                                               class="btn btn-primary bi bi-pencil-fill"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Editar"></a>
                                        </div>
                                        <!--Boton borrar-->
                                        <div class="col-lg-1">
                                            <a href="<%=request.getContextPath()%>/EliminarTaller?idTaller=<%=((Taller) talleresCerrados[taller]).getIdTaller()%>"
                                               class="btn btn-danger bi bi-trash-fill"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Borrar"
                                               onclick="return confirm('¿Esta seguro que desea borrar el taller \'' +
                                                               '<%=((Taller) talleresCerrados[taller]).getNombre()%>?\'')"></a>
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
                    ver las preguntas y respuestas de la taller, así como también su
                    descripción, instrucciones, despedida, etc.
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc">Graficas de las respuestas: </span>Muestra 
                    las graficas de las respuestas de una taller.
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc">Editar: </span>Permite modificar los valores
                    de una taller, como introducción, instrucciones, preguntas, respuestas, etc.
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Deshabilitar: </span
                    >Desactiva una taller para que ya no se pueda contestar, sin
                    borrarla.
                </p>

                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Habilitar:</span> Cambia
                    el estado de una taller, para que ahora pueda ser contestada.
                </p>

                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Borrar</span> Elimina la
                    taller.
                    <span class="text-danger fw-bold"
                          >ATENCIÓN: Una vez borrada la taller, también se borraran las
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

<% } else//Si no, no tiene permiso
            {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "index.jsp");
                request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
            }

        } else//Iniicio sesión otra persona
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

