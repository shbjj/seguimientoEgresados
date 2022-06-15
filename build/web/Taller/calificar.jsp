<%-- 
    Document   : calificar
    Created on : 14/06/2022, 07:51:41 AM
    Author     : hbdye
--%>

<%@page import="modelo.Alumno"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelo.Taller"%>
<%
    String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

    if (tipoS != null)//Si se inicio sesión
    {
        if (tipoS.compareTo("2") == 0 || tipoS.compareTo("1") == 0) //Inicia sesión un admin o un alumno
        {
            if (tipoS.compareTo("2") == 0)//Si es un administrador, revisar que tenga permiso
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rol == 2)//Si es de seguimiento de egresados, no tiene permiso de ver la info del taller 
                {
                    request.setAttribute("NOMBRE_MENSAJE", "Error");
                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                    request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                    request.setAttribute("MENSAJEBOTON", "Volver");
                    request.setAttribute("DIRECCIONBOTON", "index.jsp");
                    request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                }
            }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ArrayList<Alumno> alumnos =(ArrayList<Alumno>) request.getAttribute("alumnos");
    Taller taller = (Taller) request.getAttribute("taller");
%>
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
        <title>Calificar taller | <%=taller.getNombre()%></title>
    </head>
    <body>
        <div class="container">
            
            <%@ include file = "/navbar.jsp"%>

            <div class="row">
                <div class="col-sm-10">
                    <p class="fs-3 font-titulo-enc mt-2"><%=taller.getNombre()%></p>
                </div>
                <%if(!tipoS.equals("1"))//Si el tipo de sesion no es de alumno
                {
                %>
                <div class="d-grid gap-2 col-sm-2 mx-auto py-3">
                    <a
                        class="btn btn-primary bi bi-pencil-fill"
                        data-bs-toggle="tooltip"
                        data-bs-placement="bottom"
                        title="Editar la Información del taller" 
                        href='<%=request.getContextPath()%>/CargarDetallesTaller?idTaller=<%=taller.getIdTaller()%>'
                        >
                        Editar
                    </a>
                </div>
                <% } %>
            </div>
            <div class="row">
                <div class="col-12">
                    <p class="fs-4 barra-card-enc text-white text-center">
                        Información básica
                    </p>
                </div>

            </div>
            <div class="row">
                <div class="col-sm-12 ">
                    <div class="fw-bold font-titulo-enc">Descripción</div>
                    <div class=""><%=taller.getDescripcion()%></div>
                </div>
                <div class="col-sm-12 ">
                    <div class="fw-bold font-titulo-enc">Instructor</div>
                    <div class=""><%=taller.getInstructor()%></div>
                </div>
                <div class="col-sm-4 ">
                    <div class="fw-bold font-titulo-enc">Clave</div>
                    <div class=""><%=taller.getClave()%></div>
                </div>
              </div>
            <div class="row mt-3">
                <div class="col-12">
                    <p class="fs-4 barra-card-enc text-white text-center">
                        Fechas
                    </p> 
                </div>

            </div>
            <div class="row">
                <div class="col-sm-4 ">
                    <div class="fw-bold font-titulo-enc">Período</div>
                    <div class=""><%=taller.getPeriodo()%></div>
                </div>
                <div class="col-sm-4 ">
                    <div class="fw-bold font-titulo-enc">Fecha de inicio</div>
                    <div class=""><%=taller.getFechaIni()%></div>
                </div>
                <div class="col-sm-4 ">
                    <div class="fw-bold font-titulo-enc">Fecha de cierre</div>
                    <div class=""><%=taller.getFechaFin()%></div>
                </div>
            </div>
        <h3>Alumnos</h3>
        <div class="list-group">
            <form class="row g-3 needs-validation mt-3" action="<%=request.getContextPath()%>/GenerarBoletas" method="POST">
                <input type="hidden" name="idTaller" value="<%=taller.getIdTaller()%>">
            <%
                for (Alumno alumno: alumnos){ %>
                    <div class="list-group-item list-group-item-action">
                        <div class="row">
                            <div class="col-md-2">
                                <span class="fw-bold"><%=alumno.getMatricula()%> </span>
                            </div> 
                            <div class="col-md-6">
                                <span><%=alumno.getApp()%> <%=alumno.getApm()%> <%=alumno.getNombre()%></span>
                            </div>
                            <div class="col-md-4">
                                <div class="row">
                                    <div class="col-6">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="calificacion<%=alumno.getMatricula()%>" id="calificacion<%=alumno.getMatricula()%>1" value="1" checked>
                                            <label class="form-check-label" for="calificacion<%=alumno.getMatricula()%>1">
                                              Aprobado
                                            </label>
                                          </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="calificacion<%=alumno.getMatricula()%>" id="calificacion<%=alumno.getMatricula()%>2" value="0" >
                                            <label class="form-check-label" for="calificacion<%=alumno.getMatricula()%>2">
                                              Reprobado
                                            </label>
                                          </div>
                                    </div>
                                </div>
                                 
                                  
                            </div>
                        </div>
                    </div>
                 <%   
                };
            %>
            <div class="col-12">
                <div class='d-grid gap-2 mb-3'>
                    <button class="btn btn-primary boton mt-3" 
                    type="submit" 
                    data-bs-toggle="tooltip" data-bs-placement="bottom" title="Salir y guardar alumno"
                    >Guardar alumno</button>
                </div>
                
            </div>
            </form>
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


    </body>
</html>
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

