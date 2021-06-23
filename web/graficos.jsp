<%-- 
    Document   : graficos
    Created on : 4/04/2021, 04:49:10 PM
    Author     : Hugo
--%>

<%@page import="modelo.Respuesta"%>
<%@page import="modelo.Pregunta"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    int id_encuesta = (int) request.getAttribute("ID");
    String nombre = (String) request.getAttribute("NOMBRE");
    String fecha = (String) request.getAttribute("FECHA");
    String clave = (String) request.getAttribute("CLAVE");
    Pregunta [] preguntas = (Pregunta[]) request.getAttribute("PREGUNTAS");
    Respuesta [] respuestas=(Respuesta[])request.getAttribute("RESPUESTAS");
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
        
        <link rel = "preconnect" href = "https://fonts.gstatic.com">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
        <title>Graficos</title>
        <link rel="icon" type="image/jpg" href="img/img_admin.jpg">
    
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        
    </head>
    <body>
        <div class="container">
                        <!--
                Información de la Encuesta
            -->
            <div class="card mt-3">
                <div class="card-header text-center barra-card-enc text-white">
                  Respuestas de la encuesta
                </div>
                <div class="card-body body-card-preg">
                  <h5 class="card-title"><%=nombre%></h5>
                  <p class="card-text font-subtitulo-enc"><%if(clave.compareTo("")==0){%>Egresados<%}else{%>Empleadores<%}%></p>
                </div>
                <div class="card-footer text-muted text-center body-card-preg">
                  Fecha: <%=fecha%>
                </div>
            </div>
                
            <%
            for(int pregunta=0; pregunta<preguntas.length; pregunta++)
            { %>
            <!--
                Respuestas de la pregunta
            -->
            <div class="card mt-3">
                <h5 class="card-header barra-card-enc text-white">
                    
                    <div class="row">
                        <div class="col-lg-9">
                            Pregunta No. <%=(pregunta+1)%>
                        </div>
                        <div class="col-lg-3">
                            <%=preguntas[pregunta].getTipo()%>
                        </div>
                    </div>
                </h5>
                <div class="card-body">
                    <div class="card-title font-titulo-enc fw-bold">
                        <%=preguntas[pregunta].getPregunta()%>
                    </div>
                  <div class="card-text">
                      <%
                      if(preguntas[pregunta].getTipo().compareTo("Abierta")==0)//si la pregunta es abierta
                      {
                      %>
                      <div style="height:200px;overflow:auto;">   
                        <table class="table table-hover">
                             <thead class="body-card-enc">
                               <tr>
                                 <th scope="col">#</th>
                                 <th scope="col">Respuesta</th>
                               </tr>
                             </thead>
                             <tbody>
                                 <%
                                 for(int cont=0; cont<respuestas[pregunta].respuestasS.length; cont++)
                                 { %>
                               <tr>
                                 <th scope="row"><%=(cont+1)%></th>
                                 <td>
                                     <%=respuestas[pregunta].respuestasS[cont]%>
                                 </td>
                               </tr>    
                                 <% }
                                 %>

                             </tbody>
                         
                           </table>
                     </div>
                      <%
                      
                      }
                      else
                        {%>
                      
                      <div class="row">
                          <div class="col-12">
                             <div id="piechart_<%=preguntas[pregunta].getId_preguntas()%>" style="height: 500px;"></div> 
                          </div>
                      </div>
                        <%
                        }
                        %>
                    </div>
                </div>
            </div>
            <% }
            %>
            
            
            
        </div>
        <script type="text/javascript">
            google.charts.load("current", {packages:["corechart"]});
            google.charts.setOnLoadCallback(drawChart);
            function drawChart() {
            <%for(int pregunta=0; pregunta<preguntas.length; pregunta++)
            { 
                if(preguntas[pregunta].getTipo().compareTo("Abierta")!=0)//Si la pregunta no es abierta...
                { %>
              var data = google.visualization.arrayToDataTable([
                  
                ['Opción', 'Respuestas'],
                <%
                for(int cont=0; cont<=preguntas[pregunta].getNum_respuestas(); cont++)
                { %>
                    ['<%=preguntas[pregunta].respuestas[cont]%>',     <%=respuestas[pregunta].respuestas[cont]%>],
                <% }
                %>
                
              ]);
      
              var options = {
                title: '<%=preguntas[pregunta].getPregunta()%>',
                is3D: true,
              };
      
              var chart = new google.visualization.PieChart(document.getElementById('piechart_<%=preguntas[pregunta].getId_preguntas()%>'));
              chart.draw(data, options);
              
              <% }
            } %>
            }
          </script>
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
