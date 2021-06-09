<%-- 
    Document   : agregarPreguntas
    Created on : 4/04/2021, 04:49:10 PM
    Author     : hbdye
--%>

<%@page import="java.nio.charset.StandardCharsets"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
//Obtener los valores entregados desde el Servlet
    int id_encuesta = (int) request.getAttribute("IDENCUESTA");
    String nombre = (String) request.getAttribute("NOMBRE");
    String descripcion = (String) request.getAttribute("DESCRIPCION");
    String instrucciones = (String) request.getAttribute("INSTRUCCIONES");
    String despedida = (String) request.getAttribute("DESPEDIDA");
    String fecha = (String) request.getAttribute("FECHA");
    String clave = (String) request.getAttribute("CLAVE");


    
//Pasar los valores a UTF-8
      /*  //Nombre
       byte[] tempBytes = nombre.getBytes();
        nombre = new String(tempBytes, StandardCharsets.UTF_8);
        //Descripción
        tempBytes = descripcion.getBytes();
        descripcion = new String(tempBytes, StandardCharsets.UTF_8);
        //Instrucciones
        tempBytes = instrucciones.getBytes();
        instrucciones = new String(tempBytes, StandardCharsets.UTF_8);
        //Despedida
        tempBytes = despedida.getBytes();
        despedida = new String(tempBytes, StandardCharsets.UTF_8);
        //Clave
        tempBytes = clave.getBytes();
        clave = new String(tempBytes, StandardCharsets.UTF_8);*/

%>

<!doctype html>
<html lang="es">

    <head>
        <!-- Required meta tags -->
        <META http-equiv=contentType=text/html" pageEncoding="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estilos.css">
        <script language="JavaScript" type="text/javascript" src="jquery/jquery-3.6.0.min.js"></script>
        <title>Agregar preguntas</title>

        <link rel="icon" type="image/jpg" href="img/img_admin.jpg">
    </head>

    <body>
        <div class="container ">

            <div class="card border-dark my-3">
                <div class="card-header barra-card-1 text-white">Agregar preguntas a la encuesta</div>
                <div class="card-body text-dark">
                    <h5 class="card-title font-titulo"><%=id_encuesta%> <%=nombre%></h5>
                    <p class="card-text">
                    <div class="row">
                        <div class="col-md-2 fw-bold font-subtitulo">
                            Descripción
                        </div>
                        <div class="col-md-10"><%=descripcion%></div>
                        <div class="col-md-2 fw-bold font-subtitulo">
                            Instrucciones
                        </div>
                        <div class="col-md-10">
                            <%=instrucciones%>
                        </div>
                        <div class="col-md-2 fw-bold font-subtitulo">
                            Despedida
                        </div>
                        <div class="col-md-10">
                            <%=despedida%>
                        </div>
                        <div class="col-md-2 fw-bold font-subtitulo">
                            Fecha
                        </div>
                        <div class="col-md-10">
                            <%=fecha%>
                        </div>
                        <div class="col-md-2 fw-bold font-subtitulo">
                            Clave
                        </div>
                        <div class="col-md-10">
                            <%=clave%>
                        </div>
                    </div>
                    </p>
                </div>
            </div>

            <form action="<%=request.getContextPath()%>/AgregarPreguntas" method="POST">
                <!--
                Datos recibidos del Servlet
                -->
                <input type="hidden" value="<%=id_encuesta%>" name="idEncuesta">
                <input type="hidden" value="0" name="cantPreguntas" id="cantPreguntas">
           
                <!-- En este contenedor se guardaran las preguntas-->
                <div id="contenedorPreguntas" class="contenedorPreguntas">
                
                </div>
                <div class="d-grid gap-2">
                <a class="btn btn-primary" data-bs-toggle="modal"
                        data-bs-target="#modalPregunta">
                    Agregar Pregunta
                </a>
                </div>
                <div id="divBotonAgregar" style="display:none">
                    <div class='d-grid gap-2 mb-3'>
                        <button class="btn btn-danger boton mt-3"
                        data-bs-toggle="tooltip"
                        data-bs-placement="bottom"
                        title="Terminar y guardar preguntas" 
                        onclick="return confirm('¿Estas seguro que quieres guardar las preguntas?')"
                        >Guardar las preguntas</button>
                    </div>
                </div>
            </form>
                


            
        </div>


        <!-- Modal ayuda-->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header barra-modal">
                        <h5 class="modal-title font-white" id="exampleModalLabel">Ayuda</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body body-modal">
                        <p>
                        <d class="fw-bold font-subtitulo">Preguntas cerradas:</d> Son preguntas que aunque tengas varias respuestas,
                        solo se puede seleccionar una respuesta a la hora de contestar.
                        </p>
                        <p>
                        <d class="fw-bold font-subtitulo">Preguntas abiertas:</d> Son preguntas que no tienen una respuesta definida,
                        por lo que quien conteste la pregunta tiene que escribir su propia respuesta.
                        </p>
                        <p>
                        <d class="fw-bold font-subtitulo">Pregunta de opción múltiple:</d> Contiene varias respuestas, de las cuales se
                        pueden elegir una o varias.
                        </p>



                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" data-bs-toggle="modal"
                                data-bs-target="#modalPregunta">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal Agregar pregunta-->
        <div class="modal fade" id="modalPregunta" tabindex="-1" aria-labelledby="modalPregunta" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header barra-modal">
                        <h5 class="modal-title font-white" id="modalPregunta">Agregar pregunta</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">

                        <div class="card-body">
                            <h5 class="card-title font-titulo">Por favor, introduzca la información que se pide.</h5>
                            <p class="card-text">Para agregar una pregunta, primero hay que saber un poco acerca de como
                                será esta, por favor, ingrese a continuación el tipo de pregunta que es y cuantas respuestas
                                tendrá.</p>

                            <p>Tipo de pregunta</p>
                            <select class="form-select" aria-label="Default select example" id="tipoPreg"
                                    onchange="tipoPregunta()">
                                <option value="cerrada">Cerrada</option>
                                <option selected value="abierta">Abierta</option>
                                <option value="multiple">Opción multiple</option>
                            </select>
                            <div id="divNumResp">
                                <p class="mt-3">¿Cuantas respuestas tiene la pregunta?</p>
                                <select class="form-select" aria-label="Default select example" id="numResp"
                                        onchange="numeroRespuestas()" disabled >
                                    <option selected value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8">8</option>
                                    <option value="9">9</option>
                                </select>
                            </div>


                            <div class="d-grid gap-2">
                                <button type="button" class="btn btn-primary mt-3"
                                        onclick="nuevaPregunta()" data-bs-dismiss="modal">Agregar</button>
                            </div>
                            <div id="temp">

                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                data-bs-target="#exampleModal" data-bs-dismiss="modal">
                            Ayuda
                        </button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>

        </div>



        <!-- Optional JavaScript; choose one of the two! -->

        <!-- Option 1: Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
        crossorigin="anonymous"></script>

        <!-- Option 2: Separate Popper and Bootstrap JS -->

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
                integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
                integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc"
        crossorigin="anonymous"></script>

    </body>
    <script>
                                        //Para los tooltip
                                        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
                                        var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                                            return new bootstrap.Tooltip(tooltipTriggerEl)
                                        })
                                        //Lo siguiente se hará para poder crear nuevas preguntas
                                        //Variables
                                        var numeroPreguntas = 0;//Almacenara el numero de las preguntas que hay
                                        var tempNumeroRespuestas = 1;//Temporal, almacenara cuantas respuestas tendra la pregunta
                                        var tipoPreguntaVa = 'Abierta';//Guardara el tipo de pregunta que hay

                                        function nuevaPregunta() {
                                            if (numeroPreguntas == 0) {
                                                /*document.getElementById("divBotonAgregar").innerHTML = "<div class='d-grid gap-2'>" +
                                                 "<button type='button' class='btn btn-primary mb-3'>Guardar las preguntas</button>" +
                                                 "</div>";*/
                                              document.getElementById("divBotonAgregar").style.display = "block"; 
                                            }

                                            numeroPreguntas += 1;
                                            switch (tipoPreguntaVa) {
                                                case 'Abierta':
                                                    $(".contenedorPreguntas").append(
                                                            "<div class='card border-dark mb-3' >" +
                                                            "<div class='card-header barra-card-2 text-white'>Agregue los datos necesarios</div>" +
                                                            "<div class='card-body text-dark'>" +
                                                            "<h5 class='card-title font-subtitulo'>Pregunta no. " + numeroPreguntas + "</h5>" +
                                                            "<input type='hidden' value='" + numeroPreguntas + "' name='formIdPregunta" + numeroPreguntas + "'>" +
                                                            "<div class='mb-3 form-floating'>" +
                                                            "<input type='text' class='form-control' name='formPregunta" + numeroPreguntas + "' id='exampleFormControlInputPregunta" + numeroPreguntas + "' " +
                                                            "placeholder='Pregunta' maxlength='400' required>" +
                                                            "<label for='exampleFormControlInputPregunta" + numeroPreguntas + "' class='form-label'>Pregunta</label>" +
                                                            "</div>" +
                                                            "<input type='hidden' value='" + tipoPreguntaVa + "' name='formTipoPregunta" + numeroPreguntas + "'>" +
                                                            "<div class='row'>" +
                                                            "<div class='col-md-5'>" +
                                                            "<p class='card-text'>" +
                                                            "Esta es una pregunta de tipo: " +
                                                            "</p>" +
                                                            "<p class='fw-bold font-subtitulo'>" + tipoPreguntaVa + " </p>" +
                                                            " </div>" +
                                                            "<div class='col-md-5'>" +
                                                            "<p class='card-text'>" +
                                                            "¿La pregunta es obligatoria?  </p>" +
                                                            "<select class='form-select' aria-label='Default select example' name='formObligatoria" + numeroPreguntas + "'  >" +
                                                            "<option selected value='si'>Si</option>" +
                                                            "<option value='no'>No</option>" +
                                                            "</select>" +
                                                            "</div>" +
                                                            "</div>" +
                                                            "</div>" +
                                                            "</div>");

                                                    break;

                                                case 'Cerrada':
                                                case 'Opción multiple':
                                                    var tempe = "";
                                                    for (var c = 1; c <= tempNumeroRespuestas; c++) {
                                                        tempe += "<div class='mt-3 form-floating'>" +
                                                                "<input type='text' class='form-control' name='formRespuesta" + numeroPreguntas + c + "' id='exampleFormControlInputPregunta" + numeroPreguntas + c + "' " +
                                                                "placeholder='Pregunta' maxlength='150' required>" +
                                                                "<label for='exampleFormControlInputPregunta" + numeroPreguntas + c + "' class='form-label'>Respuesta " + c + "</label>" +
                                                                "</div>";
                                                        //console.log(tempe);
                                                    }
                                                    $(".contenedorPreguntas").append( "<div class='card border-dark mb-3' >" +
                                                            "<div class='card-header barra-card-2 text-white'>Agregue los datos necesarios</div>" +
                                                            "<div class='card-body text-dark'>" +
                                                            "<h5 class='card-title font-subtitulo'>Pregunta no. " + numeroPreguntas + "</h5>" +
                                                            "<input type='hidden' value='" + numeroPreguntas + "' name='formIdPregunta" + numeroPreguntas + "'>" +
                                                            "<div class='mb-3 form-floating'>" +
                                                            "<input type='text' class='form-control' name='formPregunta" + numeroPreguntas + "' id='exampleFormControlInputPregunta" + numeroPreguntas + "' " +
                                                            "placeholder='Pregunta' maxlength='400' required>" +
                                                            "<label for='exampleFormControlInputPregunta" + numeroPreguntas + "' class='form-label'>Pregunta</label>" +
                                                            "</div>" +
                                                            "<input type='hidden' value='" + tipoPreguntaVa + "' name='formTipoPregunta" + numeroPreguntas + "'>" +
                                                            "<div class='row'>" +
                                                            "<div class='col-md-5'>" +
                                                            "<p class='card-text'>" +
                                                            "Esta es una pregunta de tipo: " +
                                                            "</p>" +
                                                            "<p class='fw-bold font-subtitulo'>" + tipoPreguntaVa + " </p>" +
                                                            " </div>" +
                                                            "<div class='col-md-5'>" +
                                                            "<p class='card-text'>" +
                                                            "¿La pregunta es obligatoria?  </p>" +
                                                            "<select class='form-select' aria-label='Default select example' name='formObligatoria" + numeroPreguntas + "'  >" +
                                                            "<option selected value='si'>Si</option>" +
                                                            "<option value='no'>No</option>" +
                                                            "</select>" +
                                                            "<input type='hidden' value='" + tempNumeroRespuestas + "' name='formNumResp" + numeroPreguntas + "'>" +
                                                            "</div>" +
                                                            tempe +
                                                            "</div>" +
                                                            "</div>" +
                                                            "</div>" +
                                                            "</div>");
                                                    break;

                                            }

                                            /*Modificar el hidden del numero de preguntas*/
                                            document.getElementById("cantPreguntas").value = numeroPreguntas;
                                            //console.log(document.getElementById("cantPreguntas").value);
                                        }
                                        function tipoPregunta() {
                                            //Obtener el valor de la seleccion del usuario
                                            var temp = document.getElementById("tipoPreg");
                                            var strUser = temp.options[temp.selectedIndex].text;
                                            //Se el tipo de pregunta es diferente de abierta, entonces hay que preguntar cuantas respuestas tendra Uwu
                                            console.log(strUser);

                                            switch (strUser) {
                                                case 'Abierta':
                                                    document.getElementById("numResp").disabled = true;
                                                    tipoPreguntaVa = 'Abierta';
                                                    console.log(strUser);
                                                    break;
                                                case 'Cerrada':
                                                    document.getElementById("numResp").disabled = false;
                                                    tipoPreguntaVa = 'Cerrada';
                                                    console.log(strUser);
                                                    break;
                                                case 'Opción multiple':
                                                    document.getElementById("numResp").disabled = false;
                                                    tipoPreguntaVa = 'Opción multiple';
                                                    console.log(strUser);
                                                    break;
                                            }
                                        }
                                        //Para obtener el numero temporal de respuestas
                                        function numeroRespuestas() {
                                            //Obtener el valor de la seleccion del usuario
                                            var temp = document.getElementById("numResp");
                                            var strUser = temp.options[temp.selectedIndex].text;
                                            //Se el tipo de pregunta es diferente de abierta, entonces hay que preguntar cuantas respuestas tendra Uwu
                                            tempNumeroRespuestas = strUser;
                                        }

    </script>

</html>
