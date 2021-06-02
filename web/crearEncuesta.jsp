<%-- 
    Document   : crearEncuesta
    Created on : 1/04/2021, 07:51:06 PM
    Author     : hbdye
--%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <head>
        <!-- Required meta tags -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="css/estilos.css">
        <title>Crear encuesta</title>
    </head>
    <body>
        <%
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = ft.format(dNow);
        %>
        <div class="container mt-4">
            <div class="menuContainer mb-4"></div>
            <h1>Agregar encuesta</h1>
            <br>
            <h2>Por favor agrega los siguientes datos</h2>
            <form action="<%=request.getContextPath()%>/CrearEncuesta" method="POST">
                <div class="mb-3 form-floating">
                    <input type="text" class="form-control" name="nombre" id="exampleFormControlInput1" 
                           placeholder="Nombre de la encusta" maxlength="150" required>
                    <label for="exampleFormControlInput1" class="form-label">Nombre de la encuesta</label>
                </div>

                <div class="mb-3 form-floating">

                    <textarea class="form-control" name="descripcion" id="exampleFormControlTextarea1" rows="4" 
                              placeholder="Descripción" maxlength="500" required></textarea>
                    <label for="exampleFormControlTextarea1" class="form-label">Descripción</label>
                </div>
                <div class="mb-3 form-floating">
                    <textarea class="form-control" name="instrucciones" id="exampleFormControlTextarea1" rows="4" 
                              placeholder="Instrucciones" maxlength="500" required></textarea>
                    <label for="exampleFormControlTextarea1" class="form-label">Instrucciones</label>
                </div>
                <div class="mb-3 form-floating">
                    <textarea class="form-control" name="despedida" id="exampleFormControlTextarea1" rows="4" 
                              placeholder="Despedida" maxlength="500" required ></textarea>
                    <label for="exampleFormControlTextarea1" class="form-label">Despedida</label>
                </div>
                <div class="form-group row">
                    <label for="example-date-input" class="col-2 col-form-label">Fecha</label>
                    <div class="col-10">
                        <input class="form-control" type="date" value="<%=fecha%>" id="example-date-input" name="fecha" >
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-md-4 text-danger">
                        Solo introduzca una clave si la encuesta sera para un empleador, de lo contrario, deje en blanco.
                    </div>
                    <div class="col-md-8 mb-3 form-floating">
                        <input type="text" class="form-control" name="clave" id="exampleFormControlInput2" 
                               placeholder="Nombre de la encusta" maxlength="6" >
                        <label for="exampleFormControlInput1" class="form-labe2">Clave de la encuesta</label>
                    </div>
                </div>
                
                <button class="btn btn-primary boton mt-3"
                        data-bs-toggle="tooltip"
                        data-bs-placement="bottom"
                        title="Crear encuesta">Agregar encuesta</button>
            </form>
        </div>

        <!-- Optional JavaScript; choose one of the two! -->

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