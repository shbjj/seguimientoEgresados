<%-- 
    Document   : login
    Created on : 4/06/2021, 12:39:09 PM
    Author     : hbdye
--%>

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
        <title>Inicio de sesión</title>
    </head>
    
    <body class="barra-card-preg">
        <%
            String tipo=(String)session.getAttribute("TIPO");
            String nombre=(String)session.getAttribute("NOMBRE");
            if(tipo!=null)//Sesion iniciada
            {
                switch(tipo)
                {
                    case "1"://Alumno
                        response.sendRedirect(request.getContextPath() + "/IndexAlumno");
                        break;
                }
                
            }
            else
{
%>
                

        <div class="container ">
            <div class="menuContainer mb-4"></div>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                      <nav>
                        <div class="nav nav-tabs" id="nav-tab" role="tablist">
                          <button class="nav-link active fw-bold" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" type="button" role="tab" aria-controls="nav-home" aria-selected="true">Alumnos</button>
                          <button class="nav-link fw-bold" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button" role="tab" aria-controls="nav-profile" aria-selected="false">Administradores</button>
                          <button class="nav-link fw-bold" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-contact" type="button" role="tab" aria-controls="nav-contact" aria-selected="false">Empleadores</button>
                        </div>
                      </nav>
                      <div class="tab-content" id="nav-tabContent">
                        <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                            <!--
                                Login de alumnos
                            -->
                            <div class="card mb-3 sin-borde" >
                                <div class="card-header barra-card-enc card-title"><h5 class="text-white">Acceso de alumnos</h5></div>
                                <div class="card-body">
                                  <p class="card-text">
                                    <form action="<%=request.getContextPath()%>/IniciarSesion" method="POST">
                                        <input type="hidden" name="tipo" value="1">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" id="floatingInput" placeholder="Matricula o CURP" 
                                                   pattern="([a-zA-Z0-9]{18}|[0-9]{8})"
                                                    maxlength="18"
                                                    name="matricula"
                                                    required
                                                    >
                                            <label for="floatingInput" class="bi bi-person-fill">
                                                Matricula o CURP
                                            </label>
                                        </div>
                                          
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="d-grid gap-2 mb-3">
                                                    <button class="btn btn-primary boton bi bi-box-arrow-in-right"
                                                            data-bs-toggle="tooltip"
                                                            data-bs-placement="bottom"
                                                            title="Iniciar sesión"
                                                            >
                                                            Entrar
                                                    </button>
                                                </div> 
                                            </div>
                                        </div>
                                    </form>
                                  </p>
                                </div>
                            </div>
                            
                            
                        </div>
                        <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                            <!--
                                Login de administradores
                            -->
                            <div class="card mb-3 sin-borde" >
                                <div class="card-header barra-card-enc card-title"><h5 class="text-white">Acceso de administradores</h5></div>
                                <div class="card-body">
                                  <p class="card-text">
                                    <form action="IniciarSesion" method="POST">
                                        <input type="hidden" name="tipo" value="2">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control" id="floatingInput" placeholder="Usuario"
                                                   name="usuario"
                                                   required
                                                   >
                                            <label for="floatingInput" class="bi bi-person-fill">
                                                Usuario
                                            </label>
                                          </div>
                                          <div class="form-floating mb-3">
                                            <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                                                   name="password"
                                                   required>
                                            <label for="floatingPassword" class="bi bi-key-fill">
                                                Contraseña
                                            </label>
                                          </div>
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="d-grid gap-2 mb-3">
                                                    <button class="btn btn-primary boton bi bi-box-arrow-in-right"
                                                            data-bs-toggle="tooltip"
                                                            data-bs-placement="bottom"
                                                            title="Iniciar sesión"
                                                            >
                                                            Entrar
                                                    </button>
                                                </div> 
                                            </div>
                                        </div>
                                    </form>
                                    <a href="#" class="bi bi-question-lg" >
                                        Olvide mi contraseña
                                    </a>
                                  </p>
                                </div>
                            </div>
                            
                        </div>
                        <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                            <!--
                                Empleadores
                            -->
                            <div class="card mb-3 sin-borde" >
                                <div class="card-header barra-card-enc card-title"><h5 class="text-white">Acceso de empleadores</h5></div>
                                <div class="card-body">
                                  <p class="card-text">
                                    <form action="IniciarSesion" method="POST">
                                        <input type="hidden" name="tipo" value="3">
                                        <div class="form-floating mb-3">
                                            <input type="text" class="form-control bi bi-key-fill" id="floatingPassword" placeholder="Password"
                                                   name="clave"
                                                   required>
                                            <label for="floatingPassword" class="bi bi-key-fill">
                                                Clave de encuesta
                                            </label>
                                          </div>
                                          <div class="row">
                                            <div class="col-12">
                                                <div class="d-grid gap-2 mb-3">
                                                    <button class="btn btn-primary boton bi bi-box-arrow-in-right"
                                                            data-bs-toggle="tooltip"
                                                            data-bs-placement="bottom"
                                                            title="Responder encuesta"
                                                            >
                                                            Entrar
                                                    </button>
                                                </div> 
                                            </div>
                                        </div>
                                    </form>
                                  </p>
                                </div>
                            </div>
                        </div>
                      </div>


                </div>
                <div class="col-md-3"></div>

                
                


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
            %>