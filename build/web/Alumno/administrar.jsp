<%-- 
    Document   : administraralumnos
    Created on : 4/06/2021, 12:39:09 PM
    Author     : hbdye
--%>

<%@page import="modelo.Alumno"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />

        <!-- Bootstrap CSS -->
        <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css"
            />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
            crossorigin="anonymous"
            />
        <script
            language="JavaScript"
            type="text/javascript"
            src="jquery/jquery-3.6.0.min.js"
        ></script>
        <link rel="stylesheet" href="css/estilos.css" />
        <title>Alumnos</title>
    </head>
    <body>
        <%
            //Obtener los Atributos enviados desde el Servlet
            Alumno[] alumnos = (Alumno[]) request.getAttribute("ALUMNOS");
            Alumno[] egresados = (Alumno[]) request.getAttribute("EGRESADOS");
            String fechaAvanceSemestre=(String) request.getAttribute("FECHA_AVANCE_SEMESTRE");
            
        %>
        <div class="container">
            <%@ include file = "../navbar.jsp" %>
            <div class="row">
                <div class="col-sm-8">
                    <p class="fs-3 font-titulo-enc">Alumnos (<%=alumnos.length%>)</p>
                </div>
                <div class="d-grid gap-2 col-sm-2 mx-auto py-2">
                    <a
                        class="btn btn-primary bi bi-plus-circle"
                        type="button"
                        data-bs-toggle="tooltip"
                        data-bs-placement="bottom"
                        title="Agregar un nuevo alumno" 
                        href="<%=request.getContextPath()%>/Alumno/agregar.jsp"
                        >
                        Agregar alumno
                    </a>
                </div>
                <div class="d-grid gap-2 col-sm-2 mx-auto py-2">
                    <button
                        class="btn btn-secondary bi bi-question-lg"
                        type="button"
                        data-bs-toggle="offcanvas"
                        data-bs-target="#offcanvasWithBackdrop"
                        aria-controls="offcanvasWithBackdrop"

                        >
                        Ayuda
                    </button>
                </div>
            </div>
            
                <form method="POST" action="<%=request.getContextPath()%>/CambiarEstadoAlumno">
                <div style="height:225px;overflow:auto;"> 

                <div class="table-responsive-md">
                    <table class="table table-striped table-hover ">
                        <thead class="barra-card-enc text-white">
                            <tr>

                                <th scope="col" class="col-1">Matricula</th>
                                <th scope="col" class="col-6">Nombre</th>
                                <th scope="col" class="col-1 text-center">Semestre</th>
                                <th scope="col" class="col-1 text-center">Grupo</th>
                                <th scope="col" class="col-2">Opciones</th>
                            </tr>
                        </thead>                    
                        <tbody id='alumnosTabla'>
                            <% if (alumnos.length == 0) { %>
                            <tr>
                                <th scope="row" colspan="5" class="text-center">
                                    No hay ningun alumno.
                                </th>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                        
                </div>
                <div class="row">
                    <div class="col-md-5">
                        <div class="input-group mb-1">
                            <span class="input-group-text bi bi-search" id="inputAlumnosBuscar">
                                Buscar alumno por matricula
                            </span>
                            <input type="text" class="form-control" aria-label="Buscar" aria-describedby="inputAlumnosBuscar" id="alumnosInput" >
                        </div>

                    </div>
                    <div class="col-md-2">
                        <div class='d-grid gap-2'>
                            <a class="btn btn-danger boton bi bi-arrow-left fw-bold"
                                    href="<%=request.getContextPath()%>/RetrocederSemestre"
                                    data-bs-toggle="tooltip"
                                    data-bs-placement="bottom"
                                    title="Disminuir el nivel de semestre a los alumnos" 
                                    onclick="return confirm('¿Estas seguro que quieres disminuir el semestre a todos los alumnos?')"
                                    >
                                Regresar semestre
                            </a>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <div class='d-grid gap-2'>
                            <a class="btn btn-danger boton bi bi-arrow-right fw-bold"
                                    href="<%=request.getContextPath()%>/AvanzarSemestre"
                                    data-bs-toggle="tooltip"
                                    data-bs-placement="bottom"
                                    title="Aumentar el nivel de semestre a los alumnos" 
                                    onclick="return confirm('¿Estas seguro que quieres aumentar el semestre a todos los alumnos? La ultima vez que se utilizó esta opción fue el <%=fechaAvanceSemestre%>')"
                                    >
                                Avanzar semestre
                            </a>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class='d-grid gap-2'>
                            <button class="btn btn-warning boton bi bi-arrow-clockwise fw-bold"
                                    data-bs-toggle="tooltip"
                                    data-bs-placement="bottom"
                                    title="Cambiar de alumnos a egresados" 
                                    onclick="return confirm('¿Estas seguro que quieres cambiar el estado de estos alumnos a egresados?')"
                                    >
                                Convertir a egresados
                            </button>
                        </div>
                    </div>
                </div>
            </form>
                        
                        
                        <!--Egresados-->
                        
            <div class="row">
                <div class="col-sm-10">
                    <p class="fs-3 font-titulo-enc">Egresados (<%=egresados.length%>)</p>
                </div>
            </div>
            
            <form method="POST" action="<%=request.getContextPath()%>/CambiarEstadoAlumno">
                <div style="height:225px;overflow:auto;"> 

                <div class="table-responsive-md">
                    <table class="table table-striped table-hover ">
                        <thead class="barra-card-enc text-white">
                            <tr>

                                <th scope="col" class="col-1">Matricula</th>
                                <th scope="col" class="col-6">Nombre</th>
                                <th scope="col" class="col-1 text-center">Grupo</th>
                                <th scope="col" class="col-2">Opciones</th>
                            </tr>
                        </thead>                    
                        <tbody id='egresadosTabla'>
                            <% if (egresados.length == 0) { %>
                            <tr>
                                <th scope="row" colspan="5" class="text-center">
                                    No hay ningun egresado.
                                </th>
                            </tr>
                            <% } %>
                            
                        </tbody>
                    </table>
                </div>
                </div>
                        <div class="row">
                            <div class="col-md-9">
                              <div class="input-group mb-1">
                                <span class="input-group-text bi bi-search" id="inputEgresadosBuscar"> Buscar egresado por matricula</span>
                                <input type="text" class="form-control" aria-label="Buscar" aria-describedby="inputEgresadosBuscar" id="egresadosInput">
                              </div>

                            </div>
                            <div class="col-md-3">
                                <div class='d-grid gap-2'>
                                    <button class="btn btn-warning boton bi bi-arrow-counterclockwise fw-bold"
                                    data-bs-toggle="tooltip"
                                    data-bs-placement="bottom"
                                    title="Cambiar de egresados a alumnos" 
                                    onclick="return confirm('¿Estas seguro que quieres cambiar el estado de estos egresados a alumnos?')"
                                    > Convertir a alumnos</button>
                                </div>
                            </div>
                        </div>
            </form>
                        
                      
        </div>

        <!--
            Canvas o menu que emerge de la izquierda
        -->
        <div
            class="offcanvas offcanvas-start"
            tabindex="-1"
            id="offcanvasWithBackdrop"
            aria-labelledby="offcanvasWithBackdropLabel"
            >
            <div class="offcanvas-header barra-card-preg">
                <h5
                    class="offcanvas-title fs-3 font-titulo-enc fw-bold"
                    id="offcanvasWithBackdropLabel"
                    >
                    Ayuda
                </h5>
                <button
                    type="button"
                    class="btn-close text-reset"
                    data-bs-dismiss="offcanvas"
                    aria-label="Close"
                    ></button>
            </div>
            <div class="offcanvas-body body-card-preg">
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc">Ver detalles: </span>Permite ver los datos del alumno.
                </p>
                
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc">Editar: </span>Permite modificar los datos de un alumno, 
                    como nombre, apellidos, fecha de nacimiento, etc. (No se puede editar el número de matrícula, si 
                    desea hacer eso, elimine al alumno y agréguelo nuevamente con la matrícula correcta).
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Regresar semestre: </span
                    >Disminuye el valor del semestre de TODOS los alumnos, excepto de los alumnos que se encuentran en el 1er semestre.
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Avanzar semestre: </span
                    >Aumenta el valor del semestre de TODOS los alumnos. Los alumnos que se encuentran en el 6to semestre se convierten en egresados.
                </p>
                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Convertir a egresados: </span
                    >Cambia el estatus de un alumno ha egresado, para hacerlo seleccione la casilla
                    que está en el renglón del/los alumno(s), y después seleccione 
                    el botón "Convertir a egresados".
                </p>

                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Convertir a alumnos:</span> Cambia el estatus de un
                    egresado ha alumno, para hacerlo seleccione la casilla que está en el renglón del/los
                    egresado(s), y después seleccione el botón "Convertir a alumnos".
                </p>

                <p>
                    <span class="fs-5 fw-bold font-titulo-enc"> Borrar</span> Elimina al alumno o egresado.
                    <span class="text-danger fw-bold"
                          >ATENCIÓN: Una vez borrado, también se borraran las
                        respuestas que existan de el, así como también sus datos y 
                        estadísticas.</span
                    >
                </p>
            </div>

        </div>
        <!-- Optional JavaScript; choose one of the two! -->

        <!-- Option 1: Bootstrap Bundle with Popper -->
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
            crossorigin="anonymous"
        ></script>

        <!-- Option 2: Separate Popper and Bootstrap JS -->

        <script
            src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
            crossorigin="anonymous"
        ></script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"
            integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT"
            crossorigin="anonymous"
        ></script>
        
        <script>//Codigo para busqueda
            //Variable que guardara a todos los alumnos 
            const alumnos=[
                <%for(int alumno=0; alumno<alumnos.length; alumno++)
                { %>
                        {matricula: '<%=alumnos[alumno].getMatricula()%>', 
                            nombre: '<%=alumnos[alumno].getApp()%> <%=alumnos[alumno].getApm()%> <%=alumnos[alumno].getNombre()%>',
                          semestre: '<%=alumnos[alumno].getSemestre()%>',
                             grupo: '<%=alumnos[alumno].getGrupo()%>', 
                            numero: '<%=(alumno+1)%>'
                        },
                <% } %>
            ];
             //Variable que guardara a todos los egresados 
            const egresados=[
                <%for(int egresado=0; egresado<egresados.length; egresado++)
                { %>
                        {matricula: '<%=egresados[egresado].getMatricula()%>', 
                            nombre: '<%=egresados[egresado].getApp()%> <%=egresados[egresado].getApm()%> <%=egresados[egresado].getNombre()%>',
                             grupo: '<%=egresados[egresado].getGrupo()%>', 
                            numero: '<%=(egresado+1)%>'
                        },
                <% } %>
            ];
            //Variables de busqueda
                //Alumnos
                const busquedaAlumnos=document.querySelector('#alumnosInput');
                const resultadoAlumnos=document.querySelector('#alumnosTabla');
                //Egresados
                const busquedaEgresados=document.querySelector('#egresadosInput');
                const resultadoEgresados=document.querySelector('#egresadosTabla');
            //Variables temporales
            let matricula;//no hay que convertir a minusculas
            let nombre;
            let semestre;
            let grupo;
            let numero;
            //Metodo de busqueda para alumnos
            const filtrarAlumnos=()=>{
                //console.log("oa");
                resultadoAlumnos.innerHTML='';
                
                const texto=busquedaAlumnos.value;//Como seran numeros, no hay que cambiar a minusculas .toLowerCase()
                for(let alumno of alumnos)
                {
                    matricula=alumno.matricula;//no hay que convertir a minusculas
                    nombre=alumno.nombre;
                    semestre=alumno.semestre;
                    grupo=alumno.grupo;
                    numero=alumno.numero;
                    if(matricula.indexOf(texto)!==-1)
                    {
                        resultadoAlumnos.innerHTML+=`<tr ><td class='col-1'>`+matricula+`</td>
                        <td class='col-6'>`+nombre+`</td>
                        <td class='col-1 text-center'>`+semestre+`</td>
                        <td class='col-1 text-center'>`+grupo+`</td>
                        <td class='col-2'>
                        <div class="row">
                                        <!--Boton ver alumno-->
                                        <div class="col-lg-2 me-xl-2">
                                            <a href="<%=request.getContextPath()%>/VerAlumno?matricula=`+matricula+`"
                                               class="btn btn-primary bi bi-eye-fill text-white" 
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Ver detalles"></a>
                                        </div>
                                        <!--Boton editar-->
                                        <div class="col-lg-2 me-xl-2">
                                            <a href="<%=request.getContextPath()%>/CargarDatosAlumno?matricula=`+matricula+`"
                                               class="btn btn-primary bi bi-pencil-fill" 
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Editar"></a> 
                                        </div>
                                        <!--Boton borrar-->
                                        <div class="col-lg-2 me-xl-2">
                                            <a href="<%=request.getContextPath()%>/EliminarAlumno?matricula=`+matricula+`"
                                               class="btn btn-danger bi bi-trash-fill" 
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Borrar" 
                                               onclick="return confirm('¿Esta seguro que desea borrar al alumno `+nombre+`')"></a>
                                        </div>
                                        <!--Cambiar estado-->
                                        <div class="col-lg-2 me-xl-2 ms-3 mt-2">
                                            <input class="form-check-input" data-bs-toggle="tooltip"
                                    data-bs-placement="bottom" 
                                    title="Cambiar a alumno"  type="checkbox" value="`+matricula+`" name="cambioEstado" id="cambioEstado">
                                        </div>
                                    </div>
                        </td>
                        </tr >`;
                    }
                }
                if(resultadoAlumnos.innerHTML==='')
                {
                resultadoAlumnos.innerHTML=== `<tr><td span="c5">No encontrado...</td></tr>`;
                }
            }
            //Metodo de busqueda para egresados
            const filtrarEgresados=()=>{
                //console.log("oa");
                resultadoEgresados.innerHTML='';
                
                const texto=busquedaEgresados.value;//Como seran numeros, no hay que cambiar a minusculas .toLowerCase()
                for(let egresado of egresados)
                {
                    matricula=egresado.matricula;//no hay que convertir a minusculas
                    nombre=egresado.nombre;
                    grupo=egresado.grupo;
                    numero=egresado.numero;
                    if(matricula.indexOf(texto)!==-1)
                    {
                        resultadoEgresados.innerHTML+=`<tr ><td class='col-1'>`+matricula+`</td>
                        <td class='col-6'>`+nombre+`</td>
                        <td class='col-1 text-center'>`+grupo+`</td>
                        <td class='col-2'>
                        <div class="row">
                                        <!--Boton ver alumno-->
                                        <div class="col-lg-2 me-xl-2">
                                            <a href="<%=request.getContextPath()%>/VerAlumno?matricula=`+matricula+`"
                                               class="btn btn-primary bi bi-eye-fill text-white"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" 
                                               title="Ver detalles"></a>
                                        </div>
                                        <!--Boton editar-->
                                        <div class="col-lg-2 me-xl-2">
                                            <a href="<%=request.getContextPath()%>/CargarDatosAlumno?matricula=`+matricula+`"
                                               class="btn btn-primary bi bi-pencil-fill"
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Editar"></a>
                                        </div>
                                        <!--Boton borrar-->
                                        <div class="col-lg-2 me-xl-2">
                                            <a href="<%=request.getContextPath()%>/EliminarAlumno?matricula=`+matricula+`"
                                               class="btn btn-danger bi bi-trash-fill" 
                                               data-bs-toggle="tooltip" data-bs-placement="bottom" title="Borrar" 
                                               onclick="return confirm('¿Esta seguro que desea borrar al alumno `+nombre+`')"></a>
                                        </div>
                                        <!--Cambiar estado-->
                                        <div class="col-lg-2 me-xl-2 ms-3 mt-2">
                                            <input class="form-check-input" data-bs-toggle="tooltip" 
                                    data-bs-placement="bottom"
                                    title="Cambiar a alumno"  type="checkbox" value="`+matricula+`" name="cambioEstado" id="cambioEstado">
                                        </div>
                                    </div>
                        </td>
                        </tr >`;
                    }
                }
                if(resultadoEgresados.innerHTML==='')
                {
                    resultadoEgresados.innerHTML=== `<tr><td span="c5">No encontrado...</td></tr>`;
                }
            }
            
            
            busquedaAlumnos.addEventListener('keyup', filtrarAlumnos);
            busquedaEgresados.addEventListener('keyup', filtrarEgresados);
            filtrarAlumnos();
            filtrarEgresados();
            
        </script>
        <script>/*Codigo para boostrap*/
            var offcanvasElementList = [].slice.call(
                    document.querySelectorAll(".offcanvas")
                    );
            var offcanvasList = offcanvasElementList.map(function (offcanvasEl) {
                return new bootstrap.Offcanvas(offcanvasEl);
            });

            var tooltipTriggerList = [].slice.call(
                    document.querySelectorAll('[data-bs-toggle="tooltip"]')
                    );
            var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                return new bootstrap.Tooltip(tooltipTriggerEl);
            });
        </script>
        
    </body>
</html>
