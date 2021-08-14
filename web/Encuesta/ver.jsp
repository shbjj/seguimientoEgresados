<%-- 
    Document   : verEncuesta
    Created on : 2/06/2021, 10:31:49 PM
    Author     : hbdye
--%>
<%@page import="modelo.Respuesta"%>
<%@page import="modelo.Pregunta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script language="JavaScript" type="text/javascript" src="jquery/jquery-3.6.0.min.js"></script>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estilos.css">
        <title>Ver encuesta</title>
    </head>
    <body>


        <%
            //Obtener los Atributos enviados desde el Servlet
            int idEncuesta = (int) request.getAttribute("ID");
            String nombreEnc = (String) request.getAttribute("NOMBRE");
            String descripcionEnc = (String) request.getAttribute("DESCRIPCION");
            String instruccionesEnc = (String) request.getAttribute("INSTRUCCIONES");
            String despedidaEnc = (String) request.getAttribute("DESPEDIDA");
            String fechaEnc = (String) request.getAttribute("FECHA");
            String claveEnc = (String) request.getAttribute("CLAVE");
            String habilitada = (String) request.getAttribute("HABILITADA");
            Pregunta[] preguntas = (Pregunta[]) request.getAttribute("PREGUNTAS");
            Respuesta[] respuestas = (Respuesta[]) request.getAttribute("RESPUESTAS");

            //Con base a estos, hay que mostrar la encuesta para visualizar
        %>


        <!--
          Tarjeta que contiene la información de la encuesta
        -->
        <div class="container">
            <%@ include file = "../navbar.jsp" %>
            
            <div class="card mt-3">
                <h5 class="card-header barra-card-enc text-white">
                    Detalles de encuesta
                </h5>
                <div class="card-body">
                    <h5 class="card-title font-titulo-enc"><%=nombreEnc%></h5>
                    <div class="row">
                        <div class="col-md-2 fw-bold font-subtitulo-enc">
                            Descripción
                        </div>
                        <div class="col-md-10">
                            <%=descripcionEnc%>
                        </div>
                        <div class="col-md-2 fw-bold font-subtitulo-enc">
                            Instrucciones
                        </div>
                        <div class="col-md-10">
                             <%=instruccionesEnc%>
                        </div>
                        <div class="col-md-2 fw-bold font-subtitulo-enc">
                            Despedida
                        </div>
                        <div class="col-md-10">
                            <%=despedidaEnc%>
                        </div>
                        <div class="col-md-2 fw-bold font-subtitulo-enc">
                            Fecha
                        </div>
                        <div class="col-md-10">
                            <%=fechaEnc%>
                        </div>
                        <%
                            if (claveEnc.compareTo("")!=0) {%>
                        <div class="col-md-2 fw-bold font-subtitulo-enc">
                            Clave
                        </div>
                        <div class="col-md-10">
                            <%=claveEnc%>
                        </div>
                        <% } %>
                        <div class="col-md-2 fw-bold font-subtitulo-enc">
                            Habilitada
                        </div>
                        <div class="col-md-10">
                            <%if (habilitada != null) {
                                        if (habilitada.compareToIgnoreCase("s") == 0) {%>
                                Si  
                                <%} else {%>
                                No
                                <%}
                                    }
                                %>
                        </div>
                    </div>
                      


                    <%
                        //Empezar a recorrer el arreglo de preguntas
                        for (int numPregunta = 0; numPregunta < preguntas.length; numPregunta++) {
                            //Este ciclo va a recorrer cada pregunta
%>
                    <input type="hidden" value="<%=preguntas[numPregunta].getTipo()%>" name="tipo<%=(numPregunta + 1)%>"><!-- Enviar el tipo de pregunta -->
                    <%

                        switch (preguntas[numPregunta].getTipo()) {
                            case "Abierta"://Si la pregunta es abierta%>
                    <div class="card mt-2">
                        <div class="card-header barra-card-preg font-white">
                            <div class="row">
                                <div class="col-md-10 fw-bold">   Pregunta no. <%=(numPregunta + 1)%>
                                </div>

                                <%
                                    if (preguntas[numPregunta].getObligatoria().compareToIgnoreCase("si") == 0)//Si la pregunta es obligatoria
                                    {%>
                                <div class="col-md-2 text-danger fw-bold">
                                    Obligatoria
                                </div>
                                <%} else {%>
                                <div class="col-md-2">
                                    No obligatoria
                                </div>
                                <%}%>
                            </div>
                        </div>
                        <div class="card-body body-card-preg">
                            <h5 class="card-title"><%=preguntas[numPregunta].getPregunta()%></h5>
                            <div class="form-floating">

                                <textarea class="form-control" placeholder="Escribe tu respuesta por favor" 
                                          name="respuesta<%=(numPregunta + 1)%>1" 
                                          id="respuesta<%=(numPregunta + 1)%>1" style="height: 100px" 
                                          <%if (preguntas[numPregunta].getObligatoria().compareToIgnoreCase("si") == 0)//Si la pregunta es obligatoria
                                              {//Si la pregunta es obligatoria, hay que agregar el campo 'required'
                                          %>
                                          required 
                                          <%}%> 
                                          ></textarea>
                                <label for="respuesta<%=(numPregunta + 1)%>1">Escribe tu respuesta por favor</label>
                            </div>
                        </div>
                    </div>

                    <%
                            break;
                        case "Opción multiple":%>
                    <!--
                      Tarjeta de pregunta Opcion multiple
                    -->

                    <div class="card mt-2">
                        <div class="card-header barra-card-preg font-white">
                            <div class="row">
                                <div class="col-md-10">
                                    Pregunta no. <%=(numPregunta + 1)%>
                                </div>
                                <% if (preguntas[numPregunta].getObligatoria().compareToIgnoreCase("si") == 0)//Si la pregunta es obligatoria
                                    {%>
                                <div class="col-md-2 text-danger fw-bold">
                                    Obligatoria
                                </div>
                                <%} else {%>
                                <div class="col-md-2">
                                    No obligatoria
                                </div>
                                <%}%>
                            </div>
                        </div>
                        <div class="card-body body-card-preg">
                            <h5 class="card-title"><%=preguntas[numPregunta].getPregunta()%></h5>
                            <p class="card-text">Selecciona una o más opciones.</p>

                            <!--
                              Checks
                            -->
                            <div 
                                <%if (preguntas[numPregunta].getObligatoria().compareToIgnoreCase("si") == 0)//Si la pregunta es obligatoria
                                    {//Si la pregunta es obligatoria, hay que agregar el campo 'required'
%>
                                onclick="verificarCheck('respuesta<%=(numPregunta + 1)%>')"
                                <%}%>  
                                >
                                <%
                                    for (int numRespuesta = 0; numRespuesta < preguntas[numPregunta].getNum_respuestas(); numRespuesta++) {//Se recorrera cada respuesta posible del arreglo de respuestas que tiene la pregunta
%>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="<%=(numRespuesta + 1)%>"  
                                           name="respuesta<%=(numPregunta + 1)%>1" 
                                           id="respuesta<%=(numPregunta + 1)%><%=(numRespuesta + 1)%>" 
                                           >
                                    <label class="form-check-label" for="respuesta<%=(numPregunta + 1)%><%=(numRespuesta + 1)%>">
                                        <%=preguntas[numPregunta].respuestas[numRespuesta]%>
                                    </label>
                                </div> 
                                <%}%>                  
                            </div>
                        </div>
                    </div>
                    <%                  break;
                        case "Cerrada":%>
                    <!--
                      Tarjeta de pregunta Cerrada
                    -->
                    <input type="hidden" value="<%=preguntas[numPregunta].getNum_respuestas()%>" name="numRespuestas<%=(numPregunta + 1)%>"><!-- Enviar la cantidad de respuestas -->
                    <div class="card mt-2">
                        <div class="card-header barra-card-preg font-white">
                            <div class="row">
                                <div class="col-md-10">
                                    Pregunta no. <%=(numPregunta + 1)%>
                                </div>
                                <% if (preguntas[numPregunta].getObligatoria().compareToIgnoreCase("si") == 0)//Si la pregunta es obligatoria
                                    {%>
                                <div class="col-md-2 text-danger fw-bold">
                                    Obligatoria
                                </div>
                                <%} else {%>
                                <div class="col-md-2">
                                    No obligatoria
                                </div>
                                <%}%>
                            </div>
                        </div>
                        <div class="card-body body-card-preg">
                            <h5 class="card-title"><%=preguntas[numPregunta].getPregunta()%></h5>
                            <p class="card-text">Selecciona solo una opción por favor.</p>

                            <!-- Radios -->
                            <%
                                for (int numRespuesta = 0; numRespuesta < preguntas[numPregunta].getNum_respuestas(); numRespuesta++) {//Se recorrera cada respuesta posible del arreglo de respuestas que tiene la pregunta
%>

                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="respuesta<%=(numPregunta + 1)%>1" id="respuesta<%=(numPregunta + 1)%><%=(numRespuesta + 1)%>" value="<%=(numRespuesta + 1)%>"
                                       <%if (preguntas[numPregunta].getObligatoria().compareToIgnoreCase("si") == 0)//Si la pregunta es obligatoria
                                           {//Si la pregunta es obligatoria, hay que agregar el campo 'required'
                                       %>
                                       required 
                                       <%}%> 
                                       >
                                <label class="form-check-label" for="respuesta<%=(numPregunta + 1)%><%=(numRespuesta + 1)%>">
                                    <%=preguntas[numPregunta].respuestas[numRespuesta]%>
                                </label>
                            </div>


                            <% }   %>
                        </div>
                    </div>

                    <%    break;

                            }

                        }


                    %>










                </div>
                <div class="card-footer text-muted text-center">

                </div>
            </div>

        </div><!-- Fin del container -->
        <!-- Optional JavaScript; choose one of the two! -->

        <!-- Option 1: Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

        <!-- Option 2: Separate Popper and Bootstrap JS -->

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js" integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js" integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>

        <script>

                                    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
                                    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                                        return new bootstrap.Tooltip(tooltipTriggerEl)
                                    })
        </script>
    </body>
</html>
