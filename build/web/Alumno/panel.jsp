<%-- 
    Document   : Index
    Created on : 21/06/2021, 04:32:04 PM
    Author     : hbdye
--%>

<%@page import="modelo.Encuesta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
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
        <title>Index</title>
    </head>
    <body>
        <%
            Encuesta[] encuestasNoContestadas = (Encuesta[]) request.getAttribute("ENCUESTASNOCONTESTADAS");
            Encuesta[] encuestasContestadas = (Encuesta[]) request.getAttribute("ENCUESTASCONTESTADAS");

            String tipo = (String) session.getAttribute("TIPO");//Obtener el tipo de usuario (1 es alumno)
            if (tipo != null)//Si el tipo de usuario es diferente de Nulo (o sea, si se inicio sesion)
            {
                if (tipo.compareTo("1") == 0)//Si el tipo de usuario es Alumno
                {
                    //Obtener datos de la sesión
                    String nombre = (String) session.getAttribute("NOMBRE");
                    String matricula = (String) session.getAttribute("MATRICULA");
        %>



        <div class="container">
            <%@ include file = "navbar.jsp" %>
            <div class="row">
                <div class="col-sm-10">
                    <p class="fs-3 alu-nombre mt-3"><%=nombre%></p>
                </div>
                <div class="d-grid gap-2 col-sm-2 mx-auto py-3">
                    <a
                        class="btn btn-primary bi bi-pencil-fill"
                        data-bs-toggle="tooltip"
                        data-bs-placement="bottom"
                        title="Editar datos personales" 
                        href='<%=request.getContextPath()%>/CargarDatosAlumno?matricula=<%=matricula%>'
                        >
                        Editar datos
                    </a>
                </div>
            </div>
            <p class="fs-3 alu-titulo md-5">Talleres cursando</p>

            <!--
                Aqui ira todo lo de talleres unu
            -->
            <p class="fs-3 alu-titulo mt-md-5">Encuestas</p>
            <!--Tabla de las encuestas-->
            <div style="height:225px;overflow:auto;">
                <div class="table-responsive-md">
                    <table class="table table-striped table-hover">
                        <thead class="alu-header text-white">
                            <tr>
                                <th scope="col" class="col-7">Nombre</th>
                                <th scope="col" class="col-1 text-center">Fecha</th>
                                <th scope="col" class="col-1 text-center">Contestada</th>
                                <th scope="col" class="col-1 text-center">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            if (encuestasNoContestadas == null && encuestasContestadas == null) //Si no hay encuestas
                            { %>
                            <tr>
                                <th scope="row" colspan="4" class="text-center">
                                    No hay ninguna encuesta disponible.
                                </th>
                            </tr>
                            <% } else {
                                if (encuestasNoContestadas != null)//Si si hay encuestas
                                {

                                    for (int encuesta = 0; encuesta < encuestasNoContestadas.length; encuesta++)//Recorrer el arreglo de encuestas no contestadas
                                    {%>
                            <tr>
                                <th scope="row">
                                    <%=encuestasNoContestadas[encuesta].getNombre()%>
                                </th>
                                <td class="text-center">
                                    <%=encuestasNoContestadas[encuesta].getFecha()%>
                                </td>
                                <td class="text-center"><span class="text-danger fw-bold">No</span>
                                </td>
                                <td class="">
                                    <div class="row text-center">

                                        <!--Boton editar-->
                                        <div class="position-relative">
                                            <a href="<%=request.getContextPath()%>/CargarPreguntasRespuestas?modificarEncuesta=<%=encuestasNoContestadas[encuesta].getId_encuestas()%>"
                                               class="btn btn-primary bi bi-pencil-fill "
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Contestar"></a>
                                        </div>

                                    </div>
                                </td>
                            </tr>
                            <% }
                                }//Fin del if
                                if (encuestasContestadas != null)//Si si hay encuestas
                                {

                                    for (int encuesta = 0; encuesta < encuestasContestadas.length; encuesta++)//Recorrer el arreglo de encuestas no contestadas
                                        {%>
                            <tr>
                                <th scope="row">
                                    <%=encuestasContestadas[encuesta].getNombre()%>
                                </th>
                                <td class="text-center">
                                    <%=encuestasContestadas[encuesta].getFecha()%>
                                </td>
                                <td class="text-center"><span class="text-primary fw-bold">Si</span>
                                </td>
                                <td class="">
                                    <div class="row text-center">

                                        <!--Boton editar-->
                                        <div class="position-relative">
                                            <a href="<%=request.getContextPath()%>/CargarPreguntasRespuestas?modificarEncuesta=<%=encuestasContestadas[encuesta].getId_encuestas()%>"
                                               class="btn btn-primary bi bi-pencil-fill "
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Contestar"></a>
                                        </div>

                                    </div>
                                </td>
                            </tr>
                            <% }
                                    }//Fin del if

                                }
                            %>





                        </tbody>
                    </table>
                </div>
            </div>


        </div>





        <%
                } else//La sesión activa no es de un alumno
                {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");//Redireccionar
                }
            } else//No se ha iniciado sesión
            {
                response.sendRedirect(request.getContextPath() + "/login.jsp");//Redireccionar
            }
        %>
        <!-- Scrips -->


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