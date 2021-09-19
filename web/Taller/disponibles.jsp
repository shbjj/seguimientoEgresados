<%-- 
    Document   : disponibles
    Created on : 24/08/2021, 08:34:48 PM
    Author     : hbdye
--%>
<%@page import="modelo.Taller"%>
<%
    String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

    if (tipoS != null)//Si se inicio sesión
    {
        //out.print("<br>Se inicio sesión");
        if (tipoS.compareTo("1") == 0) //Inicia sesión un alumno
        {
            //out.print("<br>Alumno");
            String estatus = (String) session.getAttribute("ESTATUS");//Obtener el tipo de alumno, egresado o inscrito
            if (estatus.equalsIgnoreCase("inscrito"))//Si alumno esta inscrito
            {
                //Obtener datos de la sesión
                String nombre = (String) session.getAttribute("NOMBRE");
                String matricula = (String) session.getAttribute("MATRICULA");
                //Obtener los datos del Servlet
                Taller[] talleres = (Taller[]) request.getAttribute("TALLERES");

%>

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
        <title>Talleres disponibles</title>
    </head>
    <body>
        <div class="container">
            <%@ include file = "/Alumno/navbar.jsp" %>
            <div class="row mt-3">
                <div class="col-md-8">
                    <p class="fs-3 alu-titulo ">Talleres disponibles</p>
                    <!--Tabla de las encuestas-->
                    <div style="height:225px;overflow:auto;">
                        <div class="table-responsive-md">
                            <table class="table table-striped table-hover">
                                <thead class="alu-header text-white">
                                    <tr>
                                        <th scope="col" class="col-10">Nombre</th>
                                        <th scope="col" class="col-1">Detalles</th> 
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                    if (talleres.length==0) //Si no hay talleres disponibles
                                    { %>
                                    <tr>
                                        <th scope="row" colspan="2" class="text-center">
                                            No hay ningún taller disponible.
                                        </th>
                                    </tr>
                                    <% }
                                    else//Hay talleres disponibles
                                    {
                                        for(int taller=0; taller<talleres.length; taller++)
                                        { %>
                                            <tr>
                                                <th class="">
                                                    <%=talleres[taller].getNombre()%> 
                                                </th>
                                                <th class="text-center">
                                                    <button 
                                                           onclick="mostrarInformacion('<%=talleres[taller].getIdTaller()%>')"
                                                           class="btn btn-primary bi bi-eye-fill text-white" 
                                                           data-bs-toggle="tooltip" 
                                                           data-bs-placement="bottom" 
                                                           title="Ver detalles">
                                                    </button>
                                                </th>
                                            </tr>
<%   
                                        }

                                    }%>
                                </tbody>
                                
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card border-light mb-3 " id="infoTaller" tabindex="0">
                        <div class="card-header" >Información del taller</div>
                        <div class="card-body">
                            <h5 class="card-title" id="idNombreTaller">
                                Seleccione un taller para ver su información
                            </h5>
                            <p class="card-text">

                            <div class="fw-bold bi bi-blockquote-left" >
                                Descripción
                            </div>
                            <div style="height:100px;overflow:auto;">
                                <div id="idDescripcionTaller">
                                    ---
                                </div>
                            </div>
                            <div class="fw-bold bi bi-geo-alt-fill">
                                Ubicación
                            </div>
                            <div id="idUbicacionTaller">
                                ---
                            </div>
                            <div class="fw-bold bi bi-person-fill">
                                Instructor
                            </div>
                            <div id="idInstructorTaller">
                                ---
                            </div>
                            <div class="fw-bold bi bi-people-fill">
                                Cupo disponible
                            </div>
                            <div id="idCupoTaller">
                                ---
                            </div>
                            <div class="fw-bold bi bi-calendar-week">
                                Fechas de inicio y fin
                            </div>
                            <div id="idFechasTaller">
                                ---
                            </div>
                            <div class="fw-bold bi bi-clock-fill">
                                Horarios
                            </div>
                            <div id="idHorariosTaller">
                                
                                    
                                  
                            </div>
                            <form action="<%=request.getContextPath()%>/InscribirTaller" method="POST">
                                    <input type="hidden" id="idTallerInscribir" value="" name="idTaller">
                                    <div class='d-grid gap-2 mt-1'>
                                        <button class="btn btn-primary boton" 
                                        type="submit" 
                                        data-bs-toggle="tooltip" data-bs-placement="bottom" title="Inscribirse al taller"
                                        onclick="return inscribirse()"
                                        >Inscribirme</button>
                                    </div>   
                                </form> 
                            </p>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!-- Optional JavaScript; choose one of the two! -->
        <script>
            
            //Guardar un arreglo con la información de los talleres disponibles
            const talleres=[
                <%  //Variables temporales
                    int cupo;
                    String horario="";
                    for(int taller=0; taller<talleres.length; taller++)
                { 
                    //Obtener el cupo disponible del taller
                    cupo=talleres[taller].getCupo()-talleres[taller].getInscritos();
                    //Obtener los dias del taller
                    for(int dia=0; dia<talleres[taller].dias.length; dia++)
                    {
                        horario+="<div class=\"row\">"
                                    + "<div class=\"fw-bold col-4\">"
                                    +talleres[taller].dias[dia].getDiaS()
                                    +"</div> "
                                    +"<div class=\"col-4\">"
                                    +talleres[taller].dias[dia].getHoraIni()
                                    +"</div> "
                                    +"<div class=\"col-4\">"
                                    +talleres[taller].dias[dia].getHoraFin()
                                    +"</div> "
                                +"</div>";
                    }
                    
                %>
                        
                        {
                            idtaller:   '<%=talleres[taller].getIdTaller()%>',
                            nombre:     '<%=talleres[taller].getNombre()%>',
                            descripcion:'<%=talleres[taller].getDescripcion()%>',
                            ubicacion:  '<%=talleres[taller].getUbicacion()%>',
                            instructor: '<%=talleres[taller].getInstructor()%>',
                            fechas:     '<div class="row"><div class="col-2">De</div><div class="col-4"><%=talleres[taller].getFechaIni()%></div><div class="col-2">a</div><div class="col-4"><%=talleres[taller].getFechaFin()%></div></div>',
                            cupo:       '<%=cupo%>',
                            horario:    '<%=horario%>'
                        },
    <%
                horario="";}                
                %>
            ];
            
            function mostrarInformacion(idTallerFuncion)
            {
                //Buscar el taller que tiene el Id que se recibe
                let taller=talleres.find(taller =>taller.idtaller===''+idTallerFuncion);
                document.getElementById('idNombreTaller').innerHTML=taller.nombre;
                document.getElementById('idDescripcionTaller').innerHTML=taller.descripcion;
                document.getElementById('idUbicacionTaller').innerHTML=taller.ubicacion;
                document.getElementById('idInstructorTaller').innerHTML=taller.instructor;
                document.getElementById('idFechasTaller').innerHTML=taller.fechas;
                document.getElementById('idCupoTaller').innerHTML=taller.cupo;
                document.getElementById('idHorariosTaller').innerHTML=taller.horario;
                document.getElementById('idTallerInscribir').value=taller.idtaller;
                console.log(document.getElementById('idTallerInscribir').value);
                document.getElementById('infoTaller').focus();
            }
            //
            function inscribirse()
            {
                if(document.getElementById('idTallerInscribir').value!="")
                {
                    return confirm("¿Seguro que desea inscribirse al taller '"+document.getElementById('idNombreTaller').innerHTML+"'?");
                }
                else
                {
                    alert("No ha seleccionado un taller :c");
                    return false
                }
            }
            
            
        </script>
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




<%            } else {
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
