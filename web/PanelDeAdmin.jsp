<%-- 
    Document   : PanelDeAdmin
    Created on : 2/04/2021, 02:23:35 PM
    Author     : hbdye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    //Obtener los arreglos del Servlet
        String[][] encuestas_alumnos=(String[][])request.getAttribute("ENCUESTAS_ALUMNOS");
        String[][] encuestas_total=(String[][])request.getAttribute("ENCUESTAS_TODOS");
%>

<!DOCTYPE html>
<html lang="es">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
      crossorigin="anonymous"
    />
    <link rel="stylesheet" href="css/estilos.css" />
    <title>Panel de Aministración</title>
  </head>
  <body>
    <div class="container mt-4">
      <div class="menuContainer mb-4"></div>

      <h1 class="mb-3">Opciones del Administrador</h1>
      <div class="row">
        <div class="col-sm-12 col-md-6 col-lg-3 mb-3">
          <div class="card">
            <div class="contenedor"></div>
            <div class="position-relative mb-5">
              <div class="position-absolute top-50 start-50 translate-middle">
                <img
                  src="img/nueva_encuesta.png"
                  class="imagen-tamanio card-img-top"
                  alt="..."
                />
              </div>
            </div>
            <div class="card-body mt-3">
              <h5 class="card-title">Crear encuesta</h5>
              <p class="card-text">Crear una nueva encuesta desde cero.</p>
              <a
                href="crearEncuesta.jsp"
                class="btn btn-primary boton"
                data-bs-toggle="tooltip"
                data-bs-placement="bottom"
                title="Haga clic aquí para crear nuevas encuestas"
                >Crear encuesta</a
              >
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6 col-lg-3 mb-3">
          <div class="card">
            <div class="contenedor"></div>
            <div class="position-relative mb-5">
              <div class="position-absolute top-50 start-50 translate-middle">
                <img
                  src="img/ver_encuesta.png"
                  class="imagen-tamanio card-img-top"
                  alt="..."
                />
              </div>
            </div>
            <div class="card-body mt-3">
              <h5 class="card-title">Administrar encuestas</h5>
              <p class="card-text">
                Despliega una lista con las encuestas existentes,
                y permite ver los detalles de esta, editarla,
                deshabilitarla/habilitarla y borrarla.
              </p>

              <a href="<%=request.getContextPath()%>/AdministrarEncuesta" class="btn btn-primary boton mt-3"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Haga clic aquí para administar las encuestas">Administrar encuestas</a>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6 col-lg-3 mb-3">
          <div class="card">
            <div class="contenedor"></div>
            <div class="position-relative mb-5">
              <div class="position-absolute top-50 start-50 translate-middle">
                <img
                  src="img/editar_encuesta.png"
                  class="imagen-tamanio card-img-top"
                  alt="..."
                />
              </div>
            </div>
            <div class="card-body mt-3">
              <h5 class="card-title">Modificar encuesta</h5>
              <p class="card-text">
                Modifique algunas partes de una encuesta, como la introducción,
                instrucciones, preguntas, respuestas o despedida.
              </p>

              <form action="<%=request.getContextPath()%>/CargarPreguntasRespuestas" method="POST">
                <p>Seleccione la encuesta a modificar.</p>
                <select
                  class="form-select"
                  aria-label="Default select example"
                  id="modificarEncuesta"
                  required
                  name="modificarEncuesta"
                >
                    
                  <%
                for(int c=0; c<encuestas_total.length; c++)
                {
                %>

                <option value="<%=encuestas_total[c][0]%>"><%=encuestas_total[c][1]%></option>
                
                <%
                }
                %>
                  
                  
                </select>
                <button
                  class="btn btn-primary boton mt-3"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Haga clic aquí para modificar una encuesta"
                >
                  Modificar encuesta
                </button>
              </form>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6 col-lg-3 mb-3">
          <div class="card">
            <div class="contenedor"></div>
            <div class="position-relative mb-5">
              <div class="position-absolute top-50 start-50 translate-middle">
                <img
                  src="img/graficas.png"
                  class="imagen-tamanio card-img-top"
                  alt="..."
                />
              </div>
            </div>
            <div class="card-body mt-3">
              <h5 class="card-title">Resultados</h5>
              <p class="card-text">
                Vea los gráficos de los resultados de cada una de las preguntas
                de una encuesta.
              </p>

              <form action="<%=request.getContextPath()%>/ResultadosEncuestas" method="POST">
                <p>
                  Seleccione la encuesta de la que quiere ver la información.
                </p>

                <select
                  class="form-select"
                  aria-label="Default select example"
                  id="resultadosEncuestas"
                  required
                  name="resultadosEncuestas"
                >
                  <%
                for(int c=0; c<encuestas_total.length; c++)
                {
                %>

                <option value="<%=encuestas_total[c][0]%>"><%=encuestas_total[c][1]%></option>
                
                <%
                }
                %>
                  
                  
                </select>

                <button
                  class="btn btn-primary boton mt-3"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Haga clic aquí para ver los resultados"
                >
                  Ver información de la encuesta
                </button>
              </form>

              <form action="<%=request.getContextPath()%>/ResultadosEncuesta" method="post">
                <p class="mt-3">
                  Consultar las respuestas de un alumno en particular.
                </p>
                <p>Ingrese el número de control.</p>
                <input
                  class="form-control"
                  type="text"
                  placeholder="Número de control"
                  id="resultadosEncuesta"
                  name="resultadosEncuesta"
                  pattern="[0-9]+"
                  required
                />
                <button
                  class="btn btn-primary boton mt-3"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Haga clic aquí para ver los resultados"
                >
                  Ver respuestas
                </button>
              </form>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6 col-lg-3 mb-3">
          <div class="card">
            <div class="contenedor"></div>
            <div class="position-relative mb-5">
              <div class="position-absolute top-50 start-50 translate-middle">
                <img
                  src="img/alumnos_no.png"
                  class="imagen-tamanio card-img-top"
                  alt="..."
                />
              </div>
            </div>
            <div class="card-body mt-3">
              <h5 class="card-title">Alumnos que no han respondido</h5>
              <p class="card-text">
                Vea una lista de los alumnos que no han respondido cierta
                encuesta.
              </p>

              <form action="<%=request.getContextPath()%>/AlumnosNoRes" method="post">
                <p>
                  Seleccione la encuesta de la que quiere ver la información.
                </p>

                <select
                  class="form-select"
                  aria-label="Default select example"
                  id="alumnosNoRes"
                  name="alumnoNoRes"
                  required
                >
                <%
                for(int c=0; c<encuestas_alumnos.length; c++)
                {
                %>

                <option value="<%=encuestas_alumnos[c][0]%>"><%=encuestas_alumnos[c][1]%></option>
                
                <%
                }
                %>
                  
                  
                </select>

                <button
                  class="btn btn-primary boton mt-3"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Haga clic aquí para ver los resultados"
                >
                  Ver alumnos
                </button>
              </form>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6 col-lg-3 mb-3">
          <div class="card">
            <div class="contenedor"></div>
            <div class="position-relative mb-5">
              <div class="position-absolute top-50 start-50 translate-middle">
                <img
                  src="img/alumnos_si.png"
                  class="imagen-tamanio card-img-top"
                  alt="..."
                />
              </div>
            </div>
            <div class="card-body mt-3">
              <h5 class="card-title">Alumnos que si han respondido</h5>
              <p class="card-text">
                Vea una lista de los alumnos que si han respondido cierta
                encuesta.
              </p>

              <form action="<%=request.getContextPath()%>/AlumnosSiRes" method="post">
                <p>
                  Seleccione la encuesta de la que quiere ver la información.
                </p>

                <select
                  class="form-select"
                  aria-label="Default select example"
                  id="alumnosSiRes"
                  name="alumnosSiRes"
                  required
                >
                    
                <%
                for(int c=0; c<encuestas_alumnos.length; c++)
                {
                %>

                <option value="<%=encuestas_alumnos[c][0]%>"><%=encuestas_alumnos[c][1]%></option>
                
                <%
                }
                %>
                  
                  
                </select>

                <button
                  class="btn btn-primary boton mt-3"
                  data-bs-toggle="tooltip"
                  data-bs-placement="bottom"
                  title="Haga clic aquí para ver los resultados"
                >
                  Ver alumnos
                </button>
              </form>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6 col-lg-3 mb-3">
          <div class="card">
            <div class="contenedor"></div>
            <div class="position-relative mb-5">
              <div class="position-absolute top-50 start-50 translate-middle">
                <img
                  src="img/cuentas.png"
                  class="imagen-tamanio card-img-top"
                  alt="..."
                />
              </div>
            </div>
            <div class="card-body mt-3">
              <h5 class="card-title">Cuentas</h5>
              <p class="card-text">
                Administre las cuentas de los usuarios administradores existentes.
              </p>
              <p>Seleccione una opción</p>

              <a href="#" class="btn btn-primary boton" data-bs-toggle="tooltip"
              data-bs-placement="bottom"
              title="Borre una cuenta existente">Borrar cuenta</a><br />
              <a href="#" class="btn btn-primary boton mt-3" data-bs-toggle="tooltip"
              data-bs-placement="bottom"
              title="Cree una nueva cuenta">Crear cuenta</a><br />
              <a href="#" class="btn btn-primary boton mt-3" data-bs-toggle="tooltip"
              data-bs-placement="bottom"
              title="Cambie la contraseña de su cuenta">Cambiar contraseña</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
      crossorigin="anonymous"
    ></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->

    <script
      src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
      integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
      integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc"
      crossorigin="anonymous"
    ></script>
  </body>
  <script>
    var tooltipTriggerList = [].slice.call(
      document.querySelectorAll('[data-bs-toggle="tooltip"]')
    );
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new bootstrap.Tooltip(tooltipTriggerEl);
    });
  </script>
</html>

