<%-- 
    Document   : navbar
    Created on : 1/07/2021, 10:10:33 PM
    Author     : hbdye
--%>

<%-- 
    Document   : navbar
    Created on : 23/06/2021, 09:33:17 PM
    Author     : hbdye
--%>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp">Inicio</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-expanded="false">
                        Talleres
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Talleres disponibles</a></li>
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/#">Talleres cursados</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/#">Historial</a></li>
                    </ul>
                </li>
                <li >
                    <a class="nav-link active" href="<%=request.getContextPath()%>/index.jsp">Encuestas</a></li>
                        
                </li>

            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item">
                    <span class="nav-link fw-bold" ><%=nombre%></span>
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