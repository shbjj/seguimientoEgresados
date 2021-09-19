<%-- 
    Document   : lista
    Created on : 5/08/2021, 05:53:40 PM
    Author     : hbdye
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="modelo.Alumno"%>
<%@page import="modelo.Encuesta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

         if (tipoS != null)//Si se inicio sesión
         {
             if(tipoS.compareTo("2")==0) //Inicia sesión un admin
             {
                 int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                 if(rol==0 || rol==1 || rol==3)//Si es SuperAdministrador, Administrador o tallerista, entonces puede ver la lista
                 {  
                 String tipoLista=(String)request.getAttribute("TIPOLISTA");
                 Encuesta encuesta=(Encuesta)request.getAttribute("ENCUESTA");
                 Object[] alumnos =(Object[])request.getAttribute("ALUMNOS");
                 if(tipoLista.compareTo("1")==0)
                 {//Si el tipo de lista es de alumnos que si han respondido la encuesta
                     tipoLista="Alumnos que han respondido";
                 }
                 else
                 {
                     tipoLista="Alumnos que no han respondido";
                 }
%>
  
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
        <title>Lista - <%=tipoLista%></title>
    </head>
    <body>
        
        <div class="container">
            <%@ include file = "../navbar.jsp" %>
            
            <p class="fs-3 font-titulo-enc mt-2"><%=tipoLista%></p>
            <!--
            Dettalles de la encuesta
            -->
            <h5 class="card-title font-titulo-enc">
                <%=encuesta.getNombre()%>
            </h5>
            <div class="row">
                <div class="col-md-2 fw-bold font-subtitulo-enc">
                    Descripción
                </div>
                <div class="col-md-10">
                    <%=encuesta.getDescripcion()%>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 fw-bold font-subtitulo-enc">
                    Fecha
                </div>
                <div class="col-md-10">
                    <%=encuesta.getFecha()%>
                </div>
            </div>
            
                <!--
                Lista
                -->
                <div class="table-responsive-md mt-3">
                    <table class="table table-striped table-hover">
                        <thead class="barra-card-enc text-white">
                            
                            <tr>
                                <th scope="col" class="col-1">Matricula</th>
                                <th scope="col" class="col-5">Nombre</th>
                                <th scope="col" class="col-3">Correo</th>
                                <th scope="col" class="col-2">Telefono</th>
                                <th scope="col" class="col-1">Detalles</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (alumnos.length==0) { %>
                            <tr>
                                <th scope="row" colspan="5" class="text-center">
                                    No hay ningun valor.
                                </th>
                            </tr>
                            <% } else {
                                //Recorrer el ArrayList e ir cambiando los datos
                                
                                Alumno alumno;
                                for(int cont=0; cont<alumnos.length; cont++)
                                {
                                    alumno=(Alumno)alumnos[cont];
                                %>   
                            <tr>
                                <th scope="row"><%=alumno.getMatricula()%>
                                </th>
                                <td class=""><%=alumno.getApp()%> <%if(alumno.getApm()!=null){%><%=alumno.getApm()%><% } %> <%=alumno.getNombre()%></td>
                                <td class="">
                                    <%
                                    if (alumno.getCorreo()==null) { %>
                                    Correo no registrado  
                                    <%} else { %>
                                    <a href="mailto:<%=alumno.getCorreo()%>" 
                                        data-bs-toggle="tooltip" 
                                        data-bs-placement="bottom" 
                                        title="Enviar correo"><%=alumno.getCorreo()%></a>
                                    <% }
                                    %>
                                </td>
                                <td class="">
                                    <%
                                    if (alumno.getTelefono()==null) { %>
                                    Teléfono no registrado  
                                    <%} else { %>
                                    <%=alumno.getTelefono()%>
                                    <% }
                                    %>
                                </td>
                                <td class="">
                                    <div class="row">
                                        <!--Boton ver alumno-->
                                        <div class="col-lg-2 me-xl-2">
                                            <a href="<%=request.getContextPath()%>/VerAlumno?matricula=<%=alumno.getMatricula()%>"
                                               class="btn btn-primary bi bi-eye-fill text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Ver detalles"></a>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                            <% } 
                            
                            }
                                %>
                        </tbody>
                    </table>
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



                 
<%               alumnos=null;
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