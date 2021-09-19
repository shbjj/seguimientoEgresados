<%-- 
    Document   : crearEncuesta
    Created on : 1/04/2021, 07:51:06 PM
    Author     : hbdye
--%>
<%@page import="modelo.Encuesta"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <head>
        <!-- Required meta tags -->
        <META http-equiv=contentType=text/html" pageEncoding="UTF-8">
        
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="../css/estilos.css">
        <title>Crear encuesta</title>
    </head>
    <body>
        <%
            //Objetos para obtener los valores de tamaños de campos
            Encuesta tamanio=new Encuesta();
            //Fecha
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = ft.format(dNow);
        %>
        <div class="container">
            <%@ include file = "../navbar.jsp" %>
            
            <div class="card mt-3">
                <h5 class="card-header barra-card-enc text-white">
                    Agregar encuesta
                </h5>
                <div class="card-body body-card-preg">
                    <h5 class="card-title font-titulo-enc">
                        Por favor agrega los siguientes datos
                    </h5>
                    <div class="card-text">
                        <form action="<%=request.getContextPath()%>/CrearEncuesta" method="POST">
                            <div class="mb-2 form-floating">
                                <textarea class="form-control" name="nombre" id="exampleFormControlInput1" 
                                          placeholder="Nombre de la encusta" maxlength="<%=tamanio.tamNombre%> " required></textarea>
                                <label for="exampleFormControlInput1" class="form-label">Nombre de la encuesta</label>
                            </div>

                            <div class="mb-2 form-floating">

                                <textarea class="form-control" name="descripcion" id="exampleFormControlTextarea1" style="height: 75px"  
                                          placeholder="Descripción" maxlength="<%=tamanio.tamOtros%> " required></textarea>
                                <label for="exampleFormControlTextarea1" class="form-label">Descripción</label>
                            </div>
                            <div class="mb-2 form-floating">
                                <textarea class="form-control" name="instrucciones" id="exampleFormControlTextarea1" style="height: 75px"  
                                          placeholder="Instrucciones" maxlength="<%=tamanio.tamOtros%> " required></textarea>
                                <label for="exampleFormControlTextarea1" class="form-label">Instrucciones</label>
                            </div>
                            <div class="mb-2 form-floating">
                                <textarea class="form-control" name="despedida" id="exampleFormControlTextarea1" style="height: 75px"  
                                          placeholder="Despedida" maxlength="<%=tamanio.tamOtros%> " required ></textarea>
                                <label for="exampleFormControlTextarea1" class="form-label">Despedida</label>
                            </div>
                            <div class="form-group row mb-2">
                                <label for="example-date-input" class="col-md-1 col-form-label">Fecha</label>
                                <div class="col-md-2">
                                    <input class="form-control mb-2" type="date" value="<%=fecha%>" id="example-date-input" name="fecha" >
                                </div>
                                <div class="col-md-3">
                                        ¿La encuesta es para empleadores?
                                        <br>
                                        <input class="form-check-input" type="radio" name="tipoEncuesta" id="rEmpleadores"checked onclick="handleClick(this);" value="n">
                                        <label class="form-check-label" for="rEmpleadores" >
                                          No
                                        </label>
                                    
                                    
                                        <input class="form-check-input ms-md-5" type="radio" name="tipoEncuesta" id="rEmpleadores2" onclick="handleClick(this);" value="s">
                                        <label class="form-check-label mb-2" for="rEmpleadores2">
                                          Si
                                        </label>
                                </div>
                                <div id="clave" style="display: none" class="col-md-6">
                                    <div class="form-floating">
                                    <input type="text" class="form-control" name="clave" id="inputClave" 
                                           placeholder="Nombre de la encusta" maxlength="<%=tamanio.tamClave%> " >
                                    <label for="inputClave" class="form-labe2">Clave de la encuesta</label>
                                </div>
                                </div>
                            </div>
                                    
                            <div class='d-grid gap-2'>
                                <button class="btn btn-primary boton"
                                                data-bs-toggle="tooltip"
                                                data-bs-placement="bottom"
                                                title="Crear encuesta">Agregar encuesta</button>
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
                if(myRadio.value=="s")//Si la encuesta es para empleadores
                {
                    document.getElementById("clave").style.display='block';
                    document.getElementById("inputClave").required=true;
                }
                else//Si la encuesta es para egresados
                {
                    document.getElementById("clave").style.display='none';
                    document.getElementById("inputClave").required=false;
                }
            }
        </script>
        <!-- Option 1: Bootstrap Bundle with Popper -->
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