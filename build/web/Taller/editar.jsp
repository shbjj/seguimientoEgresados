<%-- 
    Document   : editar
    Created on : 19/08/2021, 01:47:33 PM
    Author     : hbdye
--%>

<%@page import="modelo.Taller"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

    if (tipoS != null)//Si se inicio sesión
    {
        if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
        {
            int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
            if (rol == 0 || rol == 1 || rol == 3)//Si es SuperAdministrador, Administrador o tallerista,
            //entonces puede editar talleres 
            {
                //Obtener el taller que se editara
                Taller taller = (Taller) request.getAttribute("taller");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Required meta tags -->
        <!--<META http-equiv=contentType=text/html" pageEncoding="UTF-8">-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script language="JavaScript" type="text/javascript" src="jquery/jquery-3.6.0.min.js"></script>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estilos.css">
        <title>Editar taller</title>
    </head>
    <body>                     
        <div class="container">
            <%@ include file = "/navbar.jsp" %>

            <div class="card mt-3">
                <h5 class="card-header barra-card-enc text-white">
                    Editar taller
                </h5>
                <div class="card-body body-card-preg">
                    <h5 class="card-title font-titulo-enc">
                        Por favor agrega los siguientes datos
                    </h5>
                    <div class="card-text">

                        <form action="<%=request.getContextPath()%>/EditarTaller" method="POST">
                            <input type="hidden" value="<%=taller.getIdTaller()%>" name="idTaller">
                            <!--
                            Nombre
                            -->
                            <div class="mb-2 form-floating">
                                <input type="text" class="form-control" name="nombreTaller" id="inputNombre" 
                                       placeholder="Nombre del taller" maxlength="<%=taller.getTamNombre()%>" 
                                       value="<%=taller.getNombre()%>"
                                       required>
                                <label for="inputNombre" class="form-label">Nombre del taller</label>
                            </div>
                            <!--
                            Descripcion
                            -->
                            <div class="mb-2 form-floating">

                                <textarea class="form-control" name="descripcion" id="inputDescripcion" style="height: 75px"  
                                          placeholder="Descripción" maxlength="<%=taller.getTamDescripcion()%>" 
                                          required><%=taller.getDescripcion()%></textarea>
                                <label for="inputDescripcion" class="form-label">Descripción</label>
                            </div>
                            <!--
                            Instructor
                            -->
                            <div class="mb-2 form-floating">
                                <input type="text" class="form-control" name="instructor" id="inputInstructor" 
                                       placeholder="Instructor" maxlength="<%=taller.getTamInstructor()%>" 
                                       value="<%=taller.getInstructor()%>"
                                       required>
                                <label for="inputInstructor" class="form-label">Instructor</label>
                            </div>
                            <div class="row">
                                <div class="col-md-4">
                                    <!--
                                    Ubicación
                                    -->
                                    <div class="mb-2 form-floating">
                                        <input type="text" class="form-control" name="ubicacion" id="inputUbicacion" 
                                               placeholder="Ubicación" maxlength="<%=taller.getTamUbicacion()%>" 
                                               value="<%=taller.getUbicacion()%>"
                                               required>
                                        <label for="inputUbicacion" class="form-label">Ubicación</label>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <!--
                                    Periodo
                                    -->
                                    <div class="mb-2 form-floating">
                                        <input type="text" class="form-control" name="periodo" id="inputPeriodo" 
                                               placeholder="Periodo" maxlength="<%=taller.getTamPeriodo()%>" 
                                               value="<%=taller.getPeriodo()%>"
                                               required>
                                        <label for="inputPeriodo" class="form-label">Período</label>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <!--
                                    Clave
                                    -->
                                    <div class="mb-2 form-floating">
                                        <input type="text" class="form-control" name="claveTaller" id="inputClave" 
                                               placeholder="NClave" maxlength="<%=taller.getTamClave()%>" 
                                               value="<%=taller.getClave()%>"
                                               required>
                                        <label for="inputClave" class="form-label">Clave</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <!--
                                    Cupo
                                    -->
                                    <input type="number" class="form-control col-md-4" name="cupo" id="inputCupo" 
                                           placeholder="Cupo" 
                                           min="1"
                                           value="<%=taller.getCupo()%>"
                                           required>
                                </div>
                                <div class="col-md-9">
                                    <div class="form-group row mb-2">
                                        <!--
                                              Fecha inicial
                                        -->
                                        <label for="inputFechaIni" class="col-md-2 col-form-label">Fecha de inicio</label>
                                        <div class="col-md-4">
                                            <input class="form-control mb-2" type="date" value="<%=taller.getFechaIni()%>" id="inputFechaIni" name="fechaIni" 
                                                   required 
                                                   onchange="limpiarDia(this)">
                                        </div>

                                        <!--
                                              Fecha final
                                        -->
                                        <label for="inputFechaFin" class="col-md-2 col-form-label">Fecha de cierre</label>
                                        <div class="col-md-4">
                                            <input class="form-control mb-2" type="date" value="<%=taller.getFechaFin()%>" id="inputFechaFin" name="fechaFin" 
                                                   value="<%=taller.getFechaFin()%>"
                                                   required
                                                   onchange="validaDia(this)">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--
                            Dias y horas del taller
                            -->
                            <span class="fw-bold">Horario: </span>Seleccione los días y las horas del taller.
                            <div onclick="verificarCheck('inputHora')">
                                <%
                                    String temp = "";
                                    String temp2 = "";//Se ocupa por incomptatibilidad de acentos
                                    boolean coincideDiaSemanaConDiaTaller = false;
                                    int tempInt = 0;
                                    //Recorrer los dias de la semana
                                    for (int diasSemana = 1; diasSemana < 8; diasSemana++) {
                                        //Ponerle el nombre del dia a la variable temp
                                        switch (diasSemana) {
                                            case 1:
                                                temp = "Lunes";
                                                temp2 = "Lunes";

                                                break;
                                            case 2:
                                                temp = "Martes";
                                                temp2 = "Martes";
                                                break;
                                            case 3:
                                                temp = "Miercoles";
                                                temp2 = "Miércoles";
                                                break;
                                            case 4:
                                                temp = "Jueves";
                                                temp2 = "Jueves";
                                                break;
                                            case 5:
                                                temp = "Viernes";
                                                temp2 = "Viernes";
                                                break;
                                            case 6:
                                                temp = "Sabado";
                                                temp2 = "Sábado";
                                                break;
                                            case 7:
                                                temp = "Domingo";
                                                temp2 = "Domingo";
                                                break;
                                        }
                                        for (int diaTaller = 0; diaTaller < taller.dias.length; diaTaller++) {   //Si coincide el dia de la semana con el dia de taller, poner variable booleana como true
                                            if (diasSemana == taller.dias[diaTaller].getdDia()) {
                                                coincideDiaSemanaConDiaTaller = true;
                                                tempInt = diaTaller;
                                            }
                                        }
                                        if (coincideDiaSemanaConDiaTaller)//Mostrar los datos del dia
                                    {%>
                                <div class="row">
                                    <div class="col-md-2">
                                        <div class="form-check form-switch mt-2">
                                            <input class="form-check-input" type="checkbox" id="inputHora<%=temp%>" onchange="horas(this)" value="<%=temp%>" name="dias" checked>
                                            <label class="form-check-label" for="inputHora<%=temp%>"><%=temp2%></label>
                                        </div>
                                    </div>
                                    <div id="horas<%=temp%>" class="col-md-10" style="display: block">
                                        <div class="row">
                                            <label for="inputHoraIni<%=temp%>" class="col-md-2 col-form-label">Hora de inicio</label>
                                            <div class="col-md-4">
                                                <input class="form-control mb-2" type="time" value="<%=taller.dias[tempInt].getHoraIni()%>" id="inputHoraIni<%=temp%>" name="inputHoraIni<%=temp%>" 
                                                       onchange="limpiarHora('<%=temp%>')">
                                            </div>

                                            <label for="inputHoraFin<%=temp%>" class="col-md-2 col-form-label">Hora de cierre</label>
                                            <div class="col-md-4">
                                                <input class="form-control mb-2" type="time" value="<%=taller.dias[tempInt].getHoraFin()%>" id="inputHoraFin<%=temp%>" name="inputHoraFin<%=temp%>" 
                                                       onchange="validarHoras('<%=temp%>')">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <%
                                } else//No mostrar los datos, solo el switch
                                {%>
                                <div class="row">
                                    <div class="col-md-2">
                                        <div class="form-check form-switch mt-2">
                                            <input class="form-check-input" type="checkbox" id="inputHora<%=temp%>" onchange="horas(this)" value="<%=temp%>" name="dias" >
                                            <label class="form-check-label" for="inputHora<%=temp2%>"><%=temp%></label>
                                        </div>
                                    </div>
                                    <div id="horas<%=temp%>" class="col-md-10" style="display: none">
                                        <div class="row">
                                            <label for="inputHoraIni<%=temp%>" class="col-md-2 col-form-label">Hora de inicio</label>
                                            <div class="col-md-4">
                                                <input class="form-control mb-2" type="time" value="" id="inputHoraIni<%=temp%>" name="inputHoraIni<%=temp%>" 
                                                       onchange="limpiarHora('<%=temp%>')">
                                            </div>

                                            <label for="inputHoraFin<%=temp%>" class="col-md-2 col-form-label">Hora de cierre</label>
                                            <div class="col-md-4">
                                                <input class="form-control mb-2" type="time" value="" id="inputHoraFin<%=temp%>" name="inputHoraFin<%=temp%>" 
                                                       onchange="validarHoras('<%=temp%>')">
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <%
                                        }
                                        coincideDiaSemanaConDiaTaller = false;
                                        tempInt = 0;
                                    }
                                %>   

                            </div>
                    </div>
                    <div class='d-grid gap-2 mt-2'>
                        <button class="btn btn-primary boton"
                                data-bs-toggle="tooltip"
                                data-bs-placement="bottom"
                                title="Guardar cambios">Guardar cambios</button>
                    </div>

                    </form>
                </div> 
            </div>
        </div>

    </div>

    <!-- Optional JavaScript; choose one of the two! -->
    <script>
        function handleClick(myRadio) {
            //mostrar o no mostrar la parte de la clave
            if (myRadio.value == "s")//
            {
                document.getElementById("clave").style.display = 'block';
                document.getElementById("inputClave").required = true;
            } else//
            {
                document.getElementById("clave").style.display = 'none';
                document.getElementById("inputClave").required = false;
            }
        }

        function horas(dia)
        {
            //Si se selecciono el checkbox, hay que mostrar las horas de inicio y fin, y ponerlas obligatorias
            if (dia.checked)
            {
                //Mostrar el bloque de las horas
                document.getElementById("horas" + dia.value).style.display = 'block';
                //Hacer obligatorios los campos de hora
                document.getElementById("inputHoraIni" + dia.value).required = true;
                document.getElementById("inputHoraFin" + dia.value).required = true;
            } else
            {
                //Ocultar el bloque de las horas
                document.getElementById("horas" + dia.value).style.display = 'none';
                //Quitar de obligatorios los campos de hora
                document.getElementById("inputHoraIni" + dia.value).required = false;
                document.getElementById("inputHoraFin" + dia.value).required = false;
            }
        }

        function validarHoras(dia)
        {
            var horaIni = document.getElementById("inputHoraIni" + dia);
            var horaFin = document.getElementById("inputHoraFin" + dia);
            if (horaIni.value > horaFin.value)
            {
                alert("Error en las horas del dia " + dia + ", la hora de inicio debe de ser menor a la hora de cierre.");
                horaIni.value = "";
                horaFin.value = "";
            }
        }

        function limpiarHora(dia)
        {
            document.getElementById("inputHoraFin" + dia).value = "";
        }

        function limpiarDia(date)
        {
            document.getElementById("inputFechaFin").value = date.value;
        }
        function validaDia(dia)
        {
            var diaIni = document.getElementById("inputFechaIni");
            if (dia.value < diaIni.value)
            {
                alert("Error en las fechas de inicio y cierre del taller, la fecha de cierre no puede ser después de la hora de inicio.");
                diaIni.value = "";
                dia.value = "";
            }
        }
    </script>
    <script>
        function verificarCheck(p)
        {

            //$cbx_group = $("input:checkbox[name='option[]']");
            $cbx_group = $("input:checkbox[id^='" + p + "']"); // name is not always helpful ;)

            $cbx_group.prop('required', true);
            if ($cbx_group.is(":checked")) {
                $cbx_group.prop('required', false);
            }
        }

    </script>    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js" integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js" integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>

</body>
<script>
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    })
</script>
</html>                 






<% } else//Si no, no tiene permiso
            {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "index.jsp");
                request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
            }

        } else//Inicio sesión otra persona
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
