<%-- 
    Document   : mensaje
    Created on : 1/04/2021, 06:57:59 PM
    Author     : hbdye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String nombreMensaje=(String)request.getAttribute("NOMBRE_MENSAJE");
String subNombreMensaje=(String)request.getAttribute("SUB_NOMBRE_MENSAJE");
String descripcion=(String)request.getAttribute("DESCRIPCION");
String direccionBoton=(String)request.getAttribute("DIRECCIONBOTON");
String mensajeBoton=(String)request.getAttribute("MENSAJEBOTON");

%>
<!doctype html>
<html lang="es">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/estilos.css">
    <title><%=nombreMensaje%></title>
  </head>
  <body>
    <div class="container ">
        
            
        <div class="row g-4 mt-md-5">
            <div class="col-md-2"></div>
            
            <div class="col-md-8">
              <div class="card">
                <img src="img/mensaje.jpg" class="card-img-top" alt="Oups, aquí debería de ir una imagen :c">
                <div class="card-body">
                            <h5 ><%=nombreMensaje%></h5>
                  
                  <p class="card-text">
                          <div class="card back-secundary">
                            <div class="card-body">
                                
                                        <h5 class="card-title"><%=subNombreMensaje%></h5>
                              
                            <p class="card-text"><%=descripcion%>
                            </p>
                            </div>
                          </div>
                    </p>
                </div>
                <div class="card-footer">
                  <small>
                      <div class="position-relative">
                        <div class="position-absolute top-0 start-50 translate-middle">
                            <a class="btn btn-primary centrar" data-bs-toggle="tooltip" data-bs-placement="bottom" title="<%=mensajeBoton%>" href="<%=request.getContextPath()%>/<%=direccionBoton%>"><%=mensajeBoton%></a>
                        </div>
                        
                      </div>
                      
                    </small>
                </div>
              </div>
            </div>
            <div class="col-md-2"></div>
          </div>
    </div>


    <!-- Optional JavaScript; choose one of the two! -->
   
    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->

    <scrip src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js" integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG" crossorigin="anonymous"></scrip>
    <scrip src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js" integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></scrip>

  </body>
  <script>
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new bootstrap.Tooltip(tooltipTriggerEl)
    })
    </script>
</html>
