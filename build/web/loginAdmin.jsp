<%-- Document : loginAdmin Created on : 1/04/2021, 06:42:02 PM Author : hbdye
--%> <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
      crossorigin="anonymous"
    />
    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="css/estilos.css" />
    <title>Login</title>
  </head>
  <body>
    <div class="container">
      <h1 class="mb-5">Iniciar sesión</h1>
      <div class="row">
        <div class="col-md-4">
          <img src="img/admin.png" class="card-img-top" />
        </div>
        <div class="col-md-8">
          <form
            action="<%=request.getContextPath()%>/ValidaAdmin"
            method="POST"
          >
            <div class="mb-3 form-floating">
              
              <input
                type="email"
                id="floatingInput"
                class="form-control"
                name="email"
                aria-describedby="emailHelp"
                placeholder="Correo electronico"
                required
                maxlength="150"
              />
              <label for="floatingInput">Correo electronico</label>
            </div>
            <div class="mb-3 form-floating">
              
              <input
                type="password"
                class="form-control"
                id="floatingPassword"
                name="password"
                placeholder="Contraseña"
                required
                maxlength="15"
              />
              <label for="floatingPassword">Contraseña</label>
            </div>
            <button
              type="submit"
              class="btn btn-primary"
              data-bs-toggle="tooltip"
              data-bs-placement="bottom"
              title="Iniciar sesión"
            >
              Entrar
            </button>
          </form>
          <div class="mt-4">
            <a
              class="form-text enlaces"
              data-bs-toggle="tooltip"
              data-bs-placement="bottom"
              title="Recuperar contraseña"
              >¿Se te olvido tu contraseña?</a
            >
          </div>
        </div>
      </div>
    </div>

    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf"
      crossorigin="anonymous"
    ></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->
    <script
      src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"
      integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js"
      integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc"
      crossorigin="anonymous"
    ></script>
  </body>
  <script>
    var tooltipTriggerList = [].slice.call(
      document.querySelectorAll('[data-bs-toggle="tooltip"]')
    );
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
      return new bootstrap.Tooltip(tooltipTriggerEl);
    });
  </script>
</html>
