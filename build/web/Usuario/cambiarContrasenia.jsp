<%-- 
    Document   : cambiarContrasenia
    Created on : 21/07/2021, 01:48:35 PM
    Author     : hbdye
--%>


<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
     String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

         if (tipoS != null)//Si se inicio sesión
         {
             if(tipoS.compareTo("2")==0) //Inicia sesión un admin
             {
                 int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                 String usuario=(String)session.getAttribute("USUARIO");//Obtener nombre de usuario
%>

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
            src="../jquery/jquery-3.6.0.min.js"
        ></script>
        <link rel="stylesheet" href="../css/estilos.css" />
        <title>Cambiar contraseña</title>
    </head>
  <body>
    <div class="container">
      <%@ include file = "../navbar.jsp" %>
            <br>
      <div class="card">
        <h5 class="card-header font-white barra-card-enc">Cambiar contraseña</h5>
        <div class="card-body body-card-preg">
            <div class="row">
                <div class="col-md-10">
                    <h5 class="card-title font-titulo-enc">
                        Ingrese los siguientes datos, por favor.
                    </h5>
                </div>
            </div>
          
          <form class="row g-3 needs-validation" novalidate action="<%=request.getContextPath()%>/CambiarContrasenia" method="POST">
            <div class="col-md-6">
                <input type="hidden" value="<%=usuario%>" name="user"><!--Enviar el nombre de usuario-->
              <label for="validationNombre" class="form-label">Nombre de Usuario</label>
              <input
                type="text"
                class="form-control"
                id="validationNombre"
                value="<%=usuario%>"
                pattern="[a-zA-Zá-úÁ-Ú 0-9_]+"
                maxlength="20" 
                readonly
              />
              <div class="valid-feedback">Parece bien.</div>
              <div class="invalid-feedback">Escriba un nombre válido. Puede usar letras, números y guión bajo.</div>
            </div>
            
            <div class="col-md-6">
              <label for="validationCustom04" class="form-label">Rol</label>
              <select class="form-select" id="validationCustom04" name="rol" required disabled>
                  <%
                  switch(rol)
                  {
                      case 0://Administrador
                          %>
                        <option value="1" selected>Administrador (Root)</option>
                        <%
                        break;
                      case 1://Administrador
                          %>
                        <option value="1" selected>Administrador</option>
                  <%
                          break;
                      case 2://Seguimiento de egresados
                          %>
                        <option value="2" selected>Seguimiento de egresados</option>
                  <%
                          break;
                      case 3://Talleres
                          %>
                        <option value="3" selected>Talleres</option>
                  <%
                          break;
                  }
                  %>
                
              </select>
              <div class="invalid-feedback">
                Seleccione un rol válido.
              </div>
            </div>
            <div class="col-md-12">
              <label for="validationApp" class="form-label"
                >Contraseña</label
              >
              <input
                type="password"
                class="form-control"
                name="password1"
                id="validationPass1"
                value=""
                pattern="[a-zA-Zá-úÁ-Ú 0-9!?#$%&/().,_]+"
                maxlength="15"
                minlength="6"
                required
              />
              <div class="valid-feedback">Parece bien.</div>
              <div class="invalid-feedback">Minimo seis caracteres. Puede escribir letras, números, y los siguientes caracteres !?#$%&/().,_</div>
            </div>
            <div class="col-md-12">
              <label for="validationApm" class="form-label"
                >Repita la contraseña</label
              >
              <input
                type="password"
                class="form-control"
                name="password2"
                id="validationPass2"
                value=""
                pattern="[a-zA-Zá-úÁ-Ú 0-9!?#$%&/().,_]+"
                maxlength="15"
                minlength="6"
                required
              />
              <div class="valid-feedback">Parece bien.</div>
              <div class="invalid-feedback">Minimo seis caracteres. Puede escribir letras, números, y los siguientes caracteres !?#$%&/().,_</div>
            </div>
            
            
            <div class="col-12">
                <div class='d-grid gap-2 mb-3'>
                    <button class="btn btn-primary boton mt-3" 
                    type="submit" 
                    data-bs-toggle="tooltip" data-bs-placement="bottom" title="Salir y guardar cambios" 
                    onclick="return pass()"
                    >Guardar cambios</button>
                </div>
                
            </div>
          </form>
        </div>
      </div>
    </div>

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
        <script>
            //Validaciones
          (function () {
            "use strict";
    
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.querySelectorAll(".needs-validation");
    
            // Loop over them and prevent submission
            Array.prototype.slice.call(forms).forEach(function (form) {
              form.addEventListener(
                "submit",
                function (event) {
                  if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                  }
    
                  form.classList.add("was-validated");
                },
                false
              );
            });
          })();
          
          function pass()
          {
              var pass1=document.getElementById("validationPass1").value;
              var pass2=document.getElementById("validationPass2").value;
              if(pass1===pass2)
              {
                  return true;
              }
              else
              {
                  alert("Las contraseñas no son iguales");
                  return false;
              }
              
          }
        </script>

  </body>
</html>

<%
        
                     
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