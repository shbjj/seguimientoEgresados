<%-- 
    Document   : navbar
    Created on : 23/06/2021, 09:33:17 PM
    Author     : hbdye
--%>

<%
    String tipoNav=(String)session.getAttribute("ROL");
    String usuarioNav=(String)session.getAttribute("USUARIO");
    if(tipoNav!=null)
    {
%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark bg-light">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/index.jsp">Inicio</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <%if(tipoNav.compareTo("1")==0 ||tipoNav.compareTo("0")==0)
                { %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active fw-bold" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
                        Alumnos
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/AdministrarAlumno">Administrar alumnos</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/Alumno/agregar.jsp">Agregar alumno</a></li>
                    </ul>
                </li>    
                <% } %>
                <%if(tipoNav.compareTo("3")!=0)//Si no es de talleres
                { %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active fw-bold" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
                        Encuestas
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/AdministrarEncuesta">Administrar encuestas</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/Encuesta/agregar.jsp">Agregar encuesta</a></li>
                    </ul>
                </li>
                <% } %>
                
                <%if(tipoNav.compareTo("2")!=0)//Si no es de seguimeinto de egresados
                { %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active fw-bold" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
                        Talleres
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/AdministrarEncuesta">Administrar talleres</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/crearEncuesta.jsp">Agregar taller</a></li>
                    </ul>
                </li>
                <% } %>
                
                <%if(tipoNav.compareTo("1")==0 ||tipoNav.compareTo("0")==0)//Si es superAdmin y administrador
                { %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active fw-bold" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
                        Usuarios
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/AdministrarUsuario">Administrar usuarios</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/Usuario/agregar.jsp">Agregar usuario</a></li>
                    </ul>
                </li>
                <% } %>
                
                

            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item">
                    <span class="nav-link fw-bold " >Usuario [<%=usuarioNav%>]</span>
                </li>
            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item">
                    <span class="" ><a href="<%=request.getContextPath()%>/Usuario/cambiarContrasenia.jsp" class="nav-link fw-bold text-white">Cambiar contraseña</a></span>
                </li>
            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item">
                    <a class="nav-link text-danger fw-bold" href="<%=request.getContextPath()%>/cerrarSesion.jsp"  >Cerrar sesión</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<!-- Optional JavaScript; choose one of the two! -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<% } %>