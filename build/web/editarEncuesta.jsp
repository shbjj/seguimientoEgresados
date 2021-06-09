<%-- 
    Document   : editarEncuesta
    Created on : 19/05/2021, 02:32:41 PM
    Author     : hbdye
--%>

<%@page import="modelo.Encuesta"%>
<%@page import="java.util.Date"%>
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
        <title>Editar encuesta</title>
    </head>
    <body>
        <%
            //Objeto para obtener el numero de caracteres que puede tener una pregunta
            Respuesta tamRespuesta= new Respuesta();
            
            //Objetos para obtener los valores de tamaños de campos
            Encuesta tamanio = new Encuesta();
            //Obtener los Atributos enviados desde el Servlet
            int idEncuesta = (int) request.getAttribute("ID");
            String nombreEnc = (String) request.getAttribute("NOMBRE");
            String descripcionEnc = (String) request.getAttribute("DESCRIPCION");
            String instruccionesEnc = (String) request.getAttribute("INSTRUCCIONES");
            String despedidaEnc = (String) request.getAttribute("DESPEDIDA");
            String fechaEnc = (String) request.getAttribute("FECHA");
            String claveEnc = (String) request.getAttribute("CLAVE");
            Pregunta[] preguntas = (Pregunta[]) request.getAttribute("PREGUNTAS");
            Respuesta[] respuestas = (Respuesta[]) request.getAttribute("RESPUESTAS");

            //Con base a estos, hay que mostrar la encuesta para contestar
%>
        <div class="container">
            <div class="menuContainer mb-4"></div>
            <h1>Editar encuesta</h1>
            <h2>Por favor agrega los siguientes datos</h2>
            <!--
            Datos de la encuesta
            -->
            <div class="card border-dark my-3">
                <div class="card-header barra-card-1 text-white">Agregar preguntas a la encuesta</div>
                <div class="card-body text-dark">
                    <h5 class="card-title font-titulo"><%=idEncuesta%> <%=nombreEnc%></h5>
                    <p class="card-text">
                    <div class="row">
                        <h3>Datos de la encuesta</h3>
                        <form action="<%=request.getContextPath()%>/EditarEncuesta" method="POST">
                            <!--
                                Enviar datos importantes acerca de la encuesta
                            -->
                            <input type="hidden" value="<%=idEncuesta%>" name="idEncuesta">
                            <input type="hidden" value="<%=preguntas.length%>" name="cantPreguntas" id="cantPreguntas">
                            
                            <div class="mb-3 form-floating">
                                <input type="text" class="form-control" name="nombre" id="exampleFormControlInput1" 
                                       placeholder="Nombre de la encusta" maxlength="<%=tamanio.tamNombre%>" 
                                       value="<%=nombreEnc%>"
                                       required>
                                <label for="exampleFormControlInput1" class="form-label">Nombre de la encuesta</label>
                            </div>

                            <div class="mb-3 form-floating">
                                <textarea class="form-control" name="descripcion" id="exampleFormControlTextarea1" style="height: 100px"  
                                          placeholder="Descripción" maxlength="<%=tamanio.tamOtros%>" required><%=descripcionEnc%></textarea>
                                <label for="exampleFormControlTextarea1" class="form-label">Descripción</label>
                            </div>
                            <div class="mb-3 form-floating">
                                <textarea class="form-control" name="instrucciones" id="exampleFormControlTextarea1" style="height: 100px"  
                                          placeholder="Instrucciones" maxlength="<%=tamanio.tamOtros%>" required><%=instruccionesEnc%></textarea>
                                <label for="exampleFormControlTextarea1" class="form-label">Instrucciones</label>
                            </div>
                            <div class="mb-3 form-floating">
                                <textarea class="form-control" name="despedida" id="exampleFormControlTextarea1" style="height: 100px"  
                                          placeholder="Despedida" maxlength="<%=tamanio.tamOtros%>" required ><%=despedidaEnc%></textarea>
                                <label for="exampleFormControlTextarea1" class="form-label">Despedida</label>
                            </div>
                            <div class="form-group row">
                                <label for="example-date-input" class="col-2 col-form-label">Fecha</label>
                                <div class="col-10">
                                    <input class="form-control" type="date" value="<%=fechaEnc%>" id="example-date-input" name="fecha" >
                                </div>
                            </div>
                    </div>
                    </p>
                </div>
            </div>
            <!--
            Preguntas y respuestas
            -->
            <h3 class="mb-3">Preguntas y respuestas</h3>
            <%
            for(int numPregunta=0; numPregunta<preguntas.length; numPregunta++)
            { 
                switch(preguntas[numPregunta].getTipo())
                {
                    case "Abierta"://Si la pregunta es abierta
                        %>
                        <div class='card border-dark mb-3' > 
                            <div class='card-header barra-card-2 text-white'>Agregue los datos necesarios</div> 
                            <div class='card-body text-dark'> 
                                <h5 class='card-title font-subtitulo'>Pregunta no. <%=(numPregunta+1)%></h5> 
                                <input type='hidden' value='<%=(numPregunta+1)%>' name='formIdPregunta<%=(numPregunta+1)%>'> 
                                <div class='mb-3 form-floating'> 
                                    <input type='text' class='form-control' name='formPregunta<%=(numPregunta+1)%>' 
                                           id='exampleFormControlInputPregunta<%=(numPregunta+1)%>'  
                                           placeholder='Pregunta' 
                                           maxlength='<%=preguntas[numPregunta].tamPregunta%>' 
                                           value="<%=preguntas[numPregunta].getPregunta()%>"
                                           required> 
                                    <label for='exampleFormControlInputPregunta<%=(numPregunta+1)%>' class='form-label'>Pregunta</label> 
                                </div> 
                                <input type='hidden' value='<%=preguntas[numPregunta].getTipo()%>' name='formTipoPregunta<%=(numPregunta+1)%>'> 
                                <div class='row'> 
                                    <div class='col-md-5'> 
                                        <p class='card-text'> 
                                            Esta es una pregunta de tipo:  
                                        </p> 
                                        <p class='fw-bold font-subtitulo'><%=preguntas[numPregunta].getTipo()%></p> 
                                    </div> 
                                    <div class='col-md-5'> 
                                        <p class='card-text'> 
                                            ¿La pregunta es obligatoria?  </p> 
                                        <select class='form-select' aria-label='Default select example' name='formObligatoria<%=(numPregunta+1)%>'  > 
                                            <%
                                            if(preguntas[numPregunta].getObligatoria().compareToIgnoreCase("si")==0)
                                            {
                                            %>
                                            <option selected value='si'>Si</option> 
                                            <option value='no'>No</option> 
                                            <%
                                            }
                                            else
                                            { %>
                                            <option value='si'>Si</option> 
                                            <option selected value='no'>No</option> 
                                            <% }
                                            %>
                                        </select> 
                                    </div> 
                                </div> 
                            </div> 
                        </div>
                        <%
                        break;
                    default://Si la pregunta no es abierta
                        %>
                        <div class='card border-dark mb-3' > 
                            <div class='card-header barra-card-2 text-white'>Agregue los datos necesarios</div> 
                            <div class='card-body text-dark'> 
                                <h5 class='card-title font-subtitulo'>Pregunta no. <%=(numPregunta+1)%></h5> 
                                <input type='hidden' value='<%=(numPregunta+1)%>' name='formIdPregunta<%=(numPregunta+1)%>'> 
                                <div class='mb-3 form-floating'> 
                                    <input type='text' class='form-control' name='formPregunta<%=(numPregunta+1)%>' 
                                           id='exampleFormControlInputPregunta<%=(numPregunta+1)%>'  
                                           placeholder='Pregunta' maxlength='<%=preguntas[numPregunta].tamPregunta%>' 
                                           value="<%=preguntas[numPregunta].getPregunta()%>"
                                           required> 
                                    <label for='exampleFormControlInputPregunta<%=(numPregunta+1)%>' class='form-label'>Pregunta</label> 
                                </div> 
                                <input type='hidden' value='<%=preguntas[numPregunta].getTipo()%>' name='formTipoPregunta<%=(numPregunta+1)%>'> 
                                <div class='row'> 
                                    <div class='col-md-5'> 
                                        <p class='card-text'> 
                                            Esta es una pregunta de tipo:  
                                        </p> 
                                        <p class='fw-bold font-subtitulo'><%=preguntas[numPregunta].getTipo()%></p> 
                                    </div> 
                                    <div class='col-md-5'> 
                                        <p class='card-text'> 
                                            ¿La pregunta es obligatoria?  </p> 
                                        <select class='form-select' aria-label='Default select example' name='formObligatoria<%=(numPregunta+1)%>'  > 
                                            <%
                                            if(preguntas[numPregunta].getObligatoria().compareToIgnoreCase("si")==0)
                                            {
                                            %>
                                            <option selected value='si'>Si</option> 
                                            <option value='no'>No</option> 
                                            <%
                                            }
                                            else
                                            { %>
                                            <option value='si'>Si</option> 
                                            <option selected value='no'>No</option> 
                                            <% }
                                            %>
                                        </select> 
                                        <input type='hidden' value='<%=preguntas[numPregunta].getNum_respuestas()%>' name='formNumResp<%=(numPregunta+1)%>'> 
                                    </div> 
                                    <!--Respuestas unu-->
                                    <%
                                    for(int respuesta=0; respuesta<preguntas[numPregunta].getNum_respuestas(); respuesta++)
                                    { %>
                                    <div class='mt-3 form-floating'> 
                                        <input type='text' class='form-control' name='formRespuesta<%=(numPregunta+1)%><%=(respuesta+1)%>' 
                                               id='exampleFormControlInputPregunta<%=(numPregunta+1)%><%=(respuesta+1)%>'  
                                               placeholder='Pregunta' maxlength='<%=preguntas[numPregunta].opc%>' 
                                               value="<%=preguntas[numPregunta].respuestas[respuesta]%>"
                                               required> 
                                        <label for='exampleFormControlInputPregunta<%=(numPregunta+1)%><%=(respuesta+1)%>' 
                                               class='form-label'>Respuesta <%=(respuesta+1)%></label> 
                                    </div> 
                                    <% }
                                    %>
                                </div> 
                            </div> 
                        </div> 
                        
                        <%
                        break;
                        
                }
            }
            %>

            <div class="row">
                <div class="col-12">
                    <button class="btn btn-primary boton mb-3"
                    data-bs-toggle="tooltip"
                    data-bs-placement="bottom"
                    title="Guardar cambios">Guardar cambios</button>
                </div>
            </div>
            
        </form>
    </div><!-- Fin del container -->






    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js" integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js" integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>

</body>
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

    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl)
    })
</script>
</html>
