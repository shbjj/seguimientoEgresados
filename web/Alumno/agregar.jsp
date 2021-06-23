<%-- 
    Document   : agregarAlumno
    Created on : 21/06/2021, 04:32:04 PM
    Author     : hbdye
--%>

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
            src="../jquery/jquery-3.6.0.min.js"
        ></script>
        <link rel="stylesheet" href="../css/estilos.css" />
        <title>Agregar alumno</title>
    </head>
  <body>
    <div class="container">
      <div class="card mt-3">
        <h5 class="card-header font-white barra-card-preg">Agregar alumno</h5>
        <div class="card-body body-card-preg">
          <h5 class="card-title font-titulo-enc">
            Ingrese los siguientes datos, por favor.
          </h5>

          <form class="row g-3 needs-validation mt-3" novalidate action="<%=request.getContextPath()%>/AgregarAlumno" method="POST">
            <div class="col-md-4">
              <label for="validationNombre" class="form-label">Nombre</label>
              <input
                type="text"
                class="form-control"
                name="name" 
                id="validationNombre"
                value=""
                pattern="[a-zA-Zá-úÁ-Ú ]+"
                maxlength="145"
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
                value=""
                pattern="[a-zA-Zá-úÁ-Ú ]+"
                maxlength="145"
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
                value=""
                pattern="[a-zA-Zá-úÁ-Ú ]+"
                maxlength="145"
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
                value=""
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
                value=""
                pattern="[a-zA-Z0-9]{18}"
                maxlength="18"
                required
              />
              <div class="valid-feedback">Parece bien.</div>
              <div class="invalid-feedback">Ingrese un CURP válido.</div>
            </div>
            <div class="col-md-2">
              <label for="validationSexo" class="form-label">Sexo</label>
              <select class="form-select" id="validationSexo" name="sexo" required>
                <option selected disabled value="">Eliga una opción...</option>
                <option value="m" >Mujer</option>
                <option value="h">Hombre</option>
              </select>
              <div class="invalid-feedback">Seleccione una opción válida.</div>
            </div>
            <div class="col-md-3">
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
            <div class="col-md-6">
              <label for="validationMun" class="form-label">Municipio</label>
              <input type="text" class="form-control" name="municipio" id="validationMun" pattern="[a-zA-Zá-úÁ-Ú ]+"
              maxlength="39"/>
              <div class="invalid-feedback">
                Ingrese un municipio válido.
              </div>
            </div>
            <div class="col-md-3">
              <label for="validationCP" class="form-label">C.P.</label>
              <input type="text" class="form-control" name="cp" id="validationCP" 
              pattern="[0-9]{5}" maxlength="5"/>
              <div class="invalid-feedback">
                Ingrese un código postal válido.
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
                required
              />
              <div class="invalid-feedback">
                Ingrese una matricula válido.
              </div>
            </div>
            <div class="col-md-4">
              <label for="validationStatus" class="form-label">Estatus</label>
              <select class="form-select" name="estatus" id="validationStatus" required>
                <option selected value="INSCRITO">Inscrito</option>
                <option value="EGRESADO">Egresado</option>
              </select>
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
                required
              />
              <div class="invalid-feedback">
                Por favor, ingrese un semestre válido.
              </div>
            </div>
            <div class="col-md-2">
              <label for="validationGrupo" class="form-label">Grupo</label>
              <input type="text" class="form-control" name="grupo" id="validationGrupo" pattern="[A-Z]"
              maxlength="1"/>
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
              />
              <div class="invalid-feedback">
                Por favor, ingrese un nombre válido.
              </div>
            </div>
            <div class="col-md-2">
              <label for="validationPlanest" class="form-label"
                >Plan estudiantil</label
              >
              <input type="text" name="plan" class="form-control" id="validationPlanest" pattern="[a-zA-Zá-úÁ-Ú0-9 ]+"
              maxlength="9"/>
              <div class="invalid-feedback">
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
              />
              <div class="invalid-feedback">
                Por favor, ingrese una generación válida.
              </div>
            </div>

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

