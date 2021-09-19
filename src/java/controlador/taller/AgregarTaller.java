/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.taller;

import controlador.Conexion_bd;
import controlador.ConvertirUTF8;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Dia;
import modelo.Taller;

/**
 *
 * @author hbdye
 */
public class AgregarTaller extends HttpServlet {
    PrintWriter out;
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        out=response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rol == 0 || rol == 1 || rol == 3)//Si es SuperAdministrador, Administrador o tallerista,
                //entonces puede agregar talleres nuevos
                {
                    //Objeto para convertir las cadenas recibidas a UTF-8
                    //ConvertirUTF8 convert = new ConvertirUTF8();

                    //Obtener los valores
                    String nombreTaller = (String) request.getParameter("nombreTaller");
                    //Ver si hay valores obtenidos, si no mandar a mensaje de error
                    if (nombreTaller != null) {
                        //Si hav valores validos, hay que obtener todos los valores
                        String descripcion = (String) request.getParameter("descripcion");
                        String instructor = (String) request.getParameter("instructor");
                        String ubicacion = (String) request.getParameter("ubicacion");
                        String periodo = (String) request.getParameter("periodo");
                        String claveTaller = (String) request.getParameter("claveTaller");
                        int cupo = Integer.parseInt((String) request.getParameter("cupo"));
                        String fechaIni = (String) request.getParameter("fechaIni");
                        String fechaFin = (String) request.getParameter("fechaFin");
                        String[] dias = (String[]) request.getParameterValues("dias");

                        //Convertir a UTF-8
                        /* = convert.convertToUTF8(nombreTaller);
                        descripcion = convert.convertToUTF8(descripcion);
                        instructor = convert.convertToUTF8(instructor);
                        ubicacion = convert.convertToUTF8(ubicacion);
                        periodo = convert.convertToUTF8(periodo);
                        claveTaller = convert.convertToUTF8(claveTaller);*/

                        //Obtener los dias y horas que el taller estara abierto
                        Dia[] diasHoras = new Dia[dias.length];

                        for (int dia = 0; dia < dias.length; dia++) {
                            //Crear un nuevo objeto de tipo dia, con el valor del dia
                            diasHoras[dia] = new Dia(dias[dia]);

                            //Obtener la hora inicial
                            diasHoras[dia].setHoraIni_S((String) request.getParameter("inputHoraIni" + dias[dia]));

                            //Obtener la hora final
                            diasHoras[dia].setHoraFin_S((String) request.getParameter("inputHoraFin" + dias[dia]));
                            //out.print("<br>"
                            //        + "DIA: "+dias[dia]+" número del dia: "+diasHoras[dia].getdDia());
                        }

                        //Crear un objeto de tipo Taller
                        Taller taller = new Taller();
                        taller.setNombre(nombreTaller);
                        taller.setDescripcion(descripcion);
                        taller.setInstructor(instructor);
                        taller.setUbicacion(ubicacion);
                        taller.setPeriodo(periodo);
                        taller.setClave(claveTaller);
                        taller.setFechaIni(fechaIni);
                        taller.setFechaFin(fechaFin);
                        taller.setCupo(cupo);
                        taller.setEstatus("Abierto");
                        taller.dias = diasHoras;
                        //Insertar taller
                        try {
                            //Conexion a la BD
                            Class.forName("org.postgresql.Driver");
                            insertar(taller, request, response);
                            
                            //Redireccionar a AdministrarTaller
                        response.sendRedirect(request.getContextPath() + "/AdministrarTaller");
                        } catch (ClassNotFoundException ex) {
                            error(ex.toString(), request, response);
                        }

                    } else {
                        error("No obtuvieron valores validos", request, response);
                    }

                } else//Si no, no tiene permiso
                {
                    error("No tiene permiso para acceder a este contenido", request, response);
                }

            } else//Iniicio sesión otra persona
            {
                error("No tiene permiso para acceder a este contenido otra", request, response);
            }
        } else//no hay una sesión iniciada
        {
            //Redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    void error(String mensaje, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", mensaje);
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "index.jsp");
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }

    void insertar(Taller taller, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());
            conexion.setAutoCommit(false);//Definir que se ocuparan transacciones
            //Query para conectar
            String query = "INSERT INTO talleres "
                    + "(nombre, descripcion, ubicacion, clave, instructor, periodo, fechaini, fechafin, cupo, estatus, inscritos) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?, 0);";
            //Ejecutar el Query
            //Dleclarar el objeto PReparedStatement
            PreparedStatement st = null;
            //Asignarle la conexion y el query
            st = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            //Agregarle los valores recibidos del JSP
            st.setString(1, taller.getNombre());
            st.setString(2, taller.getDescripcion());
            st.setString(3, taller.getUbicacion());
            st.setString(4, taller.getClave());
            st.setString(5, taller.getInstructor());
            st.setString(6, taller.getPeriodo());
            st.setDate(7, taller.getFechaIni());
            st.setDate(8, taller.getFechaFin());
            st.setInt(9, taller.getCupo());
            st.setString(10, taller.getEstatus());

            //ejecutar
            st.executeUpdate();
            //Obtener el ID del taller
            int idTaller = 0;
            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                idTaller = generatedKeys.getInt(1);
            }
            st.close();
            //Insertar los dias en la BD
            st = null;
            query = "INSERT INTO dias "
                    + "VALUES (?,?,?,?);";
            for (int cont = 0; cont < taller.dias.length; cont++) {
                st = conexion.prepareStatement(query);
                st.setInt(1, taller.dias[cont].getdDia());
                st.setInt(2, idTaller);
                st.setTime(3, taller.dias[cont].getHoraIni());
                st.setTime(4, taller.dias[cont].getHoraFin());
                //ejecutar
                st.executeUpdate();
                st.close();
            }
            //Hacer commit
            conexion.commit();
            //Cerrar conexion
            conexion.close();
            st.close();
            generatedKeys.close();

        } catch (SQLException ex) {
            //Si hay error
            if (conexion != null) {
                try {
                    //Deshacer los errores
                    conexion.rollback();
                    conexion.close();
                } catch (SQLException ex2) {
                    error(ex2.toString(), request, response);
                }
            }
        }
    }
}
