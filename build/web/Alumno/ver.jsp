<%-- 
    Document   : verAlumno
    Created on : 23/06/2021, 09:06:35 PM
    Author     : hbdye
--%>

<%@page import="modelo.Alumno"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    //Sesion
             String tipoS=(String)session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo
      //Obtener los valores desde el Servlet
      Alumno alumno = (Alumno) request.getAttribute("ALUMNO");
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
    <title><%=alumno.getApp()%> <%=alumno.getApm()%> <%=alumno.getNombre()%></title>
  </head>
  <body>
    <div class="container">
        <%if(tipoS.compareTo("1")==0)//Si el tipo de sesión es de alumno, mostrar su barra
            { 
            String nombre=(String)session.getAttribute("NOMBRE");//Obtener el nombre
            %>
                <%@ include file = "/Alumno/navbar.jsp" %>
            <% }
            else
            { %>
            <%@ include file = "/navbar.jsp" %>
            <% }%>
        
      <div class="row">
        <div class="col-sm-10">
          <p class="fs-3 font-titulo-enc mt-2"><%=alumno.getApp()%> <%=alumno.getApm()%> <%=alumno.getNombre()%></p>
        </div>
        <div class="d-grid gap-2 col-sm-2 mx-auto py-3">
          <a
            class="btn btn-primary bi bi-pencil-fill"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            title="Editar la Información del alumno" 
            href='<%=request.getContextPath()%>/CargarDatosAlumno?matricula=<%=alumno.getMatricula()%>'
          >
            Editar
          </a>
        </div>
      </div>
        <div class="row">
            <div class="col-12">
                <p class="fs-4 barra-card-enc text-white text-center">
                    Información personal
            </p>
            </div>
            
        </div>
        <div class="row">
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Apellido Paterno</div>
                <div class=""><%=alumno.getApp()%></div>
            </div>
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Apellido Materno</div>
                <div class=""><%=alumno.getApm()%></div>
            </div>
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Nombre</div>
                <div class=""><%=alumno.getNombre()%></div>
            </div>
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Fecha de nacimiento</div>
                <div class=""><%=alumno.getFechaNac()%></div>
            </div>
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">CURP</div>
                <div class=""><%=alumno.getCurp()%></div>
            </div>
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Sexo</div>
                <div class=""><%if(alumno.getSexo().compareToIgnoreCase("F")==0)
                { %>
                    FEMENINO
                <% }
                else
                { %>
                    MASCULINO
                <% }%></div>  
            </div>
        </div>
        <div class="row mt-5">
            <div class="col-12">
               <p class="fs-4 barra-card-enc text-white text-center">
                    Información de contacto
                </p> 
            </div>
            
        </div>
        <div class="row">
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Estado</div>
                <div class=""><%=alumno.getEstado()%></div>
            </div>
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Municipio</div>
                <div class=""><%=alumno.getMunicipio()%></div>
            </div>
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">C.P.</div>
                <div class=""><%=alumno.getCp()%></div>
            </div>
            
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Correo</div>
                <div class="">
                    <%if(alumno.getCorreo()==null){ %> No agregado <%}
                      else { %> <%=alumno.getCorreo()%> <% }%>
                </div>
            </div>
            <div class="col-sm-2 ">
                <div class="fw-bold font-titulo-enc">Teléfono</div>
                <div class="">
                    <%if(alumno.getTelefono()==null){ %> No agregado <%}
                      else { %> <%=alumno.getTelefono()%> <% }%>
                </div>
            </div>
        </div>
        <div class="row mt-5">
            <div class="col-12">
                <p class="fs-4 barra-card-enc text-white text-center">
                    Información escolar
                </p>
            </div>
            
        </div>
        <div class="row">
            <div class="col-sm-1 ">
                <div class="fw-bold font-titulo-enc">Matricula</div>
                <div class=""><%=alumno.getMatricula()%></div>
            </div>
            <div class="col-sm-1 ">
                <div class="fw-bold font-titulo-enc">Estatus</div>
                <div class=""><%=alumno.getEstatus()%></div>
            </div>
            <div class="col-sm-1 ">
                <div class="fw-bold font-titulo-enc">Semestre</div>
                <div class=""><%=alumno.getSemestre()%></div>
            </div>
            <div class="col-sm-1 ">
                <div class="fw-bold font-titulo-enc">Grupo</div>
                <div class=""><%=alumno.getGrupo()%></div>
            </div>
            <div class="col-sm-6 ">
                <div class="fw-bold font-titulo-enc">Carrera</div>
                <div class=""><%=alumno.getCarrera()%></div>
            </div>
            <div class="col-sm-1 ">
                <div class="fw-bold font-titulo-enc">Plan</div>
                <div class=""><%=alumno.getPlan()%></div>
            </div>
            <div class="col-sm-1 ">
                <div class="fw-bold font-titulo-enc">Generación</div>
                <div class=""><%=alumno.getGeneracion()%></div>
            </div>
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

