<%-- 
    Document   : editarAlumno
    Created on : 21/06/2021, 04:32:04 PM
    Author     : hbdye
--%>

<%@page import="modelo.Alumno"%>
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
        <title>Editar alumno</title>
    </head>
  <body>
      <%
      //Obtener los valores desde el Servlet
      Alumno alumno = (Alumno) request.getAttribute("ALUMNO");
        String tipoS=(String)session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo
        
        if(tipoS!=null)//Si se inicio sesión
        {
            switch(tipoS)
		{
			case "1"://Inicia sesión un egresado
			case "2"://Inicia sesión un admin
                 %>           
                            
                 
                 
                 

    <div class="container">
        <%if(tipoS.compareTo("1")==0)//Si la sesion es de un egresado
        { String nombre=(String)session.getAttribute("NOMBRE");//Obtener el nombre
        %>
        <%@ include file = "navbar.jsp" %>
        <% }
        else
        { %>
        <%@ include file = "../navbar.jsp" %>
        <% }%>
        
      <div class="card mt-3">
        <h5 class="card-header barra-card-enc text-white">Editar alumno</h5>
        <div class="card-body body-card-preg">
          <h5 class="card-title font-titulo-enc">
            Ingrese los siguientes datos, por favor.
          </h5>

          <form class="row g-3 needs-validation mt-3" novalidate action="<%=request.getContextPath()%>/EditarAlumno" method="POST">
            <div class="col-md-4">
              <label for="validationNombre" class="form-label">Nombre</label>
              <input
                type="text"
                class="form-control"
                name="name" 
                id="validationNombre"
                value="<%=alumno.getNombre()%>"
                pattern="[a-zA-Zá-úÁ-Ú ]+"
                maxlength="145"
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
                required
              />
              <div class="valid-feedback">Parece bien.</div>
              <div class="invalid-feedback">Escriba un nombre válido.</div>
            </div>
            <div class="col-md-4">
              <label for="validationApp" class="form-label"
                >Apellido paterno</label
              >
              <input
                type="text"
                class="form-control"
                name="app"
                id="validationApp"
                value="<%=alumno.getApp()%>"
                pattern="[a-zA-Zá-úÁ-Ú ]+"
                maxlength="145"
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
                required
              />
              <div class="valid-feedback">Parece bien.</div>
              <div class="invalid-feedback">Escriba un apellido válido.</div>
            </div>
            <div class="col-md-4">
              <label for="validationApm" class="form-label"
                >Apellido materno</label
              >
              <input
                type="text"
                class="form-control"
                name="apm"
                id="validationApm"
                value="<%=alumno.getApm()%>"
                pattern="[a-zA-Zá-úÁ-Ú ]+"
                maxlength="145"
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
              />
              <div class="valid-feedback">Parece bien.</div>
            </div>
            <div class="col-md-4">
              <label for="validationFnac" class="form-label"
                >Fecha de nacimiento</label
              >
              <input
                type="date"
                class="form-control"
                name="fechaNac" 
                id="validationFnac"
                value="<%=alumno.getFechaNac()%>"
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
                required
              />
              <div class="valid-feedback">Parece bien.</div>
              <div class="invalid-feedback">Seleccione una fecha válida.</div>
            </div>
            <div class="col-md-6">
              <label for="validationCURP" class="form-label">CURP</label>
              <input
                type="text"
                class="form-control"
                name="curp"
                id="validationCURP"
                value="<%=alumno.getCurp()%>"
                pattern="[a-zA-Z0-9]{18}"
                maxlength="18"
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
                required
              />
              <div class="valid-feedback">Parece bien.</div>
              <div class="invalid-feedback">Ingrese un CURP válido.</div>
            </div>
            <div class="col-md-2">
              <label for="validationSexo" class="form-label">Sexo</label>
              <select class="form-select" 
                      id="validationSexo" 
                      name="sexo" 
                      <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                    { %>
                        disabled
                    <% } %>
                      required>
                <option disabled value="">Eliga una opción...</option>
                <%if(alumno.getSexo().compareToIgnoreCase("F")==0)//Si es mujer
                { %>
                    <option value="F" selected>Mujer</option>
                    <option value="M">Hombre</option>
                <% }
                else//Si es hombre
                { %>
                    <option value="F" >Mujer</option>
                    <option value="M"selected>Hombre</option>
                <% }
                %>
                
              </select>
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                <input type="hidden" name="sexo" value="<%=alumno.getSexo()%>">
                <% } %>
              <div class="invalid-feedback">Seleccione una opción válida.</div>
            </div>
            <div class="col-md-2">
              <label for="validationCustom04" class="form-label">Estado</label>
              <select class="form-select" id="validationCustom04" name="estado">
                <option value="Aguascalientes">Aguascalientes</option>
                <option value="Baja California">Baja California</option>
                <option value="Baja California Sur">Baja California Sur</option>
                <option value="Campeche">Campeche</option>
                <option value="Chiapas">Chiapas</option>
                <option value="Chihuahua">Chihuahua</option>
                <option value="Coahuila">Coahuila</option>
                <option value="Colima">Colima</option>
                <option value="Ciudad de México">Ciudad de México</option>
                <option value="Durango">Durango</option>
                <option value="Estado de México">Estado de México</option>
                <option value="Guanajuato">Guanajuato</option>
                <option value="Guerrero">Guerrero</option>
                <option value="Hidalgo">Hidalgo</option>
                <option value="Jalisco">Jalisco</option>
                <option value="Michoacán">Michoacán</option>
                <option value="Morelos" selected>Morelos</option>
                <option value="Nayarit">Nayarit</option>
                <option value="Nuevo León">Nuevo León</option>
                <option value="Oaxaca">Oaxaca</option>
                <option value="Puebla">Puebla</option>
                <option value="Querétaro">Querétaro</option>
                <option value="Quintana Roo">Quintana Roo</option>
                <option value="San Luis Potosí">San Luis Potosí</option>
                <option value="Sinaloa">Sinaloa</option>
                <option value="Sonora">Sonora</option>
                <option value="Tabasco">Tabasco</option>
                <option value="Tamaulipas">Tamaulipas</option>
                <option value="Tlaxcala">Tlaxcala</option>
                <option value="Veracruz">Veracruz</option>
                <option value="Yucatán">Yucatán</option>
                <option value="Zacatecas">Zacatecas</option>
              </select>
              <div class="invalid-feedback">
                Seleccione un estado válido.
              </div>
            </div>
            <div class="col-md-2">
              <label for="validationMun" class="form-label">Municipio</label>
              <input type="text" class="form-control" name="municipio" id="validationMun" pattern="[a-zA-Zá-úÁ-Ú ]+"
              maxlength="39" 
              value='<%=alumno.getMunicipio()%>'/>
              <div class="invalid-feedback">
                Ingrese un municipio válido.
              </div>
            </div>
            <div class="col-md-2">
              <label for="validationCP" class="form-label">C.P.</label>
              <input type="text" class="form-control" name="cp" id="validationCP" 
              pattern="[0-9]{5}" maxlength="5" value='<%=alumno.getCp()%>'/>
              <div class="invalid-feedback">
                Ingrese un código postal válido.
              </div>
            </div>
            <div class="col-md-2">
              <label for="validationTel" class="form-label">Teléfono</label>
              <input
                type="text"
                class="form-control"
                name="telefono" 
                id="validationTel"
                pattern="[0-9]{10}"
                maxlength="10"
                value="<%if(alumno.getTelefono()!=null){%><%=alumno.getTelefono()%><% } %>"
              />
              <div class="invalid-feedback">
                Ingrese un número telefónico a diez digitos.
              </div>
            </div>
            
            <div class="col-md-4">
              <label for="validationCorreo" class="form-label">Correo electrónico</label>
              <input
                type="email"
                class="form-control"
                name="email" 
                id="validationCorreo"
                value="<%if(alumno.getCorreo()!=null){%><%=alumno.getCorreo()%><% } %>"
              />
              <div class="invalid-feedback">
                Ingrese un correo válido.
              </div>
            </div>
            <div class="col-md-4">
              <label for="validationMat" class="form-label">Matricula</label>
              <input
                type="text"
                class="form-control"
                name="matricula" 
                id="validationMat"
                pattern="[0-9]{8}"
                maxlength="8" 
                value='<%=alumno.getMatricula()%>'
                readonly
                required
              />
              <div class="invalid-feedback">
                Ingrese una matricula válido.
              </div>
            </div>
            <div class="col-md-4">
              <label for="validationStatus" class="form-label">Estatus</label>
              <select class="form-select" 
                      name="estatus" 
                      id="validationStatus" 
                      <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    disabled
                <% } %>
                      required>
                  <%if(alumno.getEstatus().compareToIgnoreCase("INSCRITO")==0)//Si el alumno esta inscrito
                  { %>
                      <option selected value="INSCRITO">Inscrito</option>
                      <option value="EGRESADO">Egresado</option>
                  <% }
                    else//Si no esta inscrito
                    { %>
                      <option value="INSCRITO">Inscrito</option>
                      <option selected value="EGRESADO">Egresado</option>
                    <% } %>
                
              </select>
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                <input type="hidden" name="estatus" value="<%=alumno.getEstatus()%>">
                <% } %>
              <div class="invalid-feedback">
                Por favor, seleccione un estatus válido.
              </div>
            </div>
            <div class="col-md-2">
              <label for="validationSem" class="form-label">Semestre</label>
              <input
                type="text"
                class="form-control" 
                name="semestre" 
                id="validationSem"
                pattern="[0-9]+"
                maxlength="2"
                value='<%=alumno.getSemestre()%>' 
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
                required
              />
              <div class="invalid-feedback">
                Por favor, ingrese un semestre válido.
              </div>
            </div>
            <div class="col-md-2">
              <label for="validationGrupo" class="form-label">Grupo</label>
              <input type="text" class="form-control" name="grupo" id="validationGrupo" pattern="[A-Z]"
              maxlength="1" value='<%=alumno.getGrupo()%>'
              <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>/>
              <div class="invalid-feedback">
                Por favor, ingrese un grupo válido (Mayúsculas).
              </div>
            </div>

            <div class="col-md-8">
              <label for="validationNameCarrera" class="form-label"
                >Carrera</label
              >
              <input
                type="text"
                class="form-control"
                name="carrera" 
                id="validationNameCarrera" pattern="[a-zA-Zá-úÁ-Ú ]+"
                maxlength="145" 
                value='<%=alumno.getCarrera()%>' 
                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
              />
              <div class="invalid-feedback">
                Por favor, ingrese un nombre válido.
              </div>
            </div>
            <div class="col-md-2">
              <label for="validationPlanest" class="form-label"
                >Plan estudiantil</label
              >
              <input type="text" name="plan" class="form-control" id="validationPlanest" pattern="[a-zA-Zá-úÁ-Ú0-9 -]+"
              maxlength="9" value='<%=alumno.getPlan()%>' 
              <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
                />
              <div class="invalid-feedback" >
                Por favor, ingrese un plan válido.
              </div>
            </div>

            <div class="col-md-2">
              <label for="validationGeneracion" class="form-label"
                >Generación</label
              >
              <input
                type="text"
                class="form-control"
                name="generacion" 
                id="validationGeneracion"
                pattern="[a-zA-Zá-úÁ-Ú0-9 ]+"
              maxlength="9" 
              value='<%=alumno.getGeneracion()%>' 
              <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                { %>
                    readonly
                <% } %>
              />
              <div class="invalid-feedback">
                Por favor, ingrese una generación válida.
              </div>
            </div>
              
              <div class="row">
                <div class="col-6">
                    <div class="d-grid gap-2 mb-3">
                        <a class="btn btn-danger boton mt-3"
                                data-bs-toggle="tooltip"
                                data-bs-placement="bottom"
                                onclick="return confirm('¿Esta seguro que desea salir y descartar los cambios? Ningun cambio se guardara.')"
                                
                                <%if(tipoS.compareTo("1")==0)//Si la sesión es de egresado
                                { %>
                                href="<%=request.getContextPath()%>/index.jsp"
                                <% } 
                                else
                                { %>
                                href="<%=request.getContextPath()%>/AdministrarAlumno"
                                <% }%>
                                title="Descartar todos los cambios y salir">Descartar cambios</a>
                    </div> 
                </div>
                <div class="col-6">
                    <div class='d-grid gap-2 mb-3'>
                        <button class="btn btn-primary boton mt-3" 
                        type="submit" 
                        data-bs-toggle="tooltip" data-bs-placement="bottom" title="Salir y guardar alumno"
                        >Guardar cambios</button>
                    </div>   
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
        </script>

  </body>
</html>

                 <%           
			break;
			
			default:
			request.setAttribute("NOMBRE_MENSAJE", "Error");
	                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        	        request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                	request.setAttribute("MENSAJEBOTON", "Volver");
                	request.setAttribute("DIRECCIONBOTON", "index.jsp");
                	request.getRequestDispatcher("mensaje.jsp").forward(request, response);
		}
	}
	else//no hay una sesión iniciada
	{
	//Redirigir al login
        response.sendRedirect(request.getContextPath() + "/login.jsp");
	}


      %>