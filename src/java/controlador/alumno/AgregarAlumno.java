/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.alumno;

import controlador.Conexion_bd;
//import controlador.ConvertirUTF8;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hugo Blancas Dominguez
 */
public class AgregarAlumno extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //Objeto para convertir las cadenas recibidas a UTF-8
        //ConvertirUTF8 convert = new ConvertirUTF8();

        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rol == 0 || rol == 1 || rol == 4)//Si es SuperAdministrador, Administrador o capturador,
                //entonces puede agregar alumnos nuevos
                {

                    //Obtener los valores
                    String nombre = (String) request.getParameter("name");
                    //Ver si hay valores obtenidos, si no mandar a mensaje de error
                    if (nombre != null) {
                        //Obtener los valores
                        //nombre = convert.convertToUTF8(nombre);
                        //Variables que se ocuparan;
                        String app = "", apm = "", fechaTemp = "", curp = "", sexo = "", estado = "", municipio = "", cp = "", matricula = "", estatus = "",
                                semestre = "", grupo = "", carrera = "", plan = "", generacion = "", email = "", telefono = "";
                        //app = convert.convertToUTF8((String) request.getParameter("app"));//No se pregunta si es nulo o vacio ya que es un campo obligatorio
                        app = (String) request.getParameter("app");//No se pregunta si es nulo o vacio ya que es un campo obligatorio
                        apm = (String) request.getParameter("apm");//Se pregunta si es nulo o vacio ya que no es un campo obligatorio
                        /*if (apm != null && apm.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                apm = convert.convertToUTF8(apm);
            }*/
                        fechaTemp = (String) request.getParameter("fechaNac");
                        //Convertir fecha a SQLDate
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = null;
                        try {
                            date = sdf1.parse(fechaTemp);
                        } catch (ParseException ex) {
                            Logger.getLogger(AgregarAlumno.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        java.sql.Date fechaNac = new java.sql.Date(date.getTime());
                        //curp = convert.convertToUTF8((String) request.getParameter("curp"));
                        curp = (String) request.getParameter("curp");
                        sexo = (String) request.getParameter("sexo");//no es necesario convertir
                        estado = (String) request.getParameter("estado");
                        /*if (estado != null && estado.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                estado = convert.convertToUTF8(estado);
            }*/
                        municipio = (String) request.getParameter("municipio");
                        /*if (municipio != null && municipio.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                municipio = convert.convertToUTF8(municipio);
            }*/
                        cp = (String) request.getParameter("cp");//Aunque son numeros, se puede guardar en un string
                        matricula = (String) request.getParameter("matricula");//Aunque son numeros, se puede guardar en un string
                        estatus = (String) request.getParameter("estatus");
                        semestre = (String) request.getParameter("semestre");
                        grupo = (String) request.getParameter("grupo");
                        carrera = (String) request.getParameter("carrera");
                        /*if (carrera != null && carrera.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                carrera = convert.convertToUTF8(carrera);
            }*/
                        plan = (String) request.getParameter("plan");
                        /*if (plan != null && plan.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                plan = convert.convertToUTF8(plan);
            }*/
                        generacion = (String) request.getParameter("generacion");

                        /*if (generacion != null && generacion.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                generacion = convert.convertToUTF8(generacion);
            }*/
                        telefono = (String) request.getParameter("telefono");
                        email = (String) request.getParameter("email");
                        /*if (email != null && email.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                email = convert.convertToUTF8(email);
            }*/
                        //Ahora que se tienen los datos del alumno, hay que insertarlos.
                        try {
                            //Conexion a la BD
                            Class.forName("org.postgresql.Driver");
                            //Direccion, puerto, nombre de BD, usuario y contraseña
                            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

                            //Query para conectar
                            String query = "INSERT INTO alumnos"
                                    + "(num_control,nombre,app,apm,status,carrera_nom,planest,generacion,grupo,"
                                    + "semestre,fecha_nac,curp,sexo,estado,municipio,cp,email,telefono) "
                                    + "VALUES "
                                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            //Ejecutar el Query
                            //Dleclarar el objeto PReparedStatement
                            PreparedStatement st = null;
                            //Asignarle la conexion y el query
                            st = conexion.prepareStatement(query);
                            //Agregarle los valores recibidos del JSP
                            st.setInt(1, Integer.parseInt(matricula));
                            st.setString(2, nombre.toUpperCase());
                            st.setString(3, app.toUpperCase());
                            st.setString(4, apm.toUpperCase());
                            st.setString(5, estatus);
                            st.setString(6, carrera.toUpperCase());
                            st.setString(7, plan.toUpperCase());
                            st.setString(8, generacion.toUpperCase());
                            st.setString(9, grupo.toUpperCase());
                            st.setInt(10, Integer.parseInt(semestre));
                            st.setDate(11, fechaNac);
                            st.setString(12, curp.toUpperCase());
                            st.setString(13, sexo);
                            st.setString(14, estado);
                            st.setString(15, municipio.toUpperCase());
                            st.setString(16, cp);
                            st.setString(17, email);
                            st.setString(18, telefono);

                            st.executeUpdate();

                            //Cerrar conexion
                            conexion.close();
                            st.close();
                            successful(request, response, nombre, app, apm);
                        } catch (ClassNotFoundException | SQLException ex) {
                            error(request, response, ex.getMessage());
                        }

                    } else {
                        error(request, response, "No se recibieron valores");
                    }

                } else//Si no, no tiene permiso
                {
                    error("No tiene permiso para acceder a este contenido", request, response);
                }

            } else//Iniicio sesión otra persona
            {
                error("No tiene permiso para acceder a este contenido", request, response);
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

    void error(HttpServletRequest request, HttpServletResponse response, String ex) throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos: \n" + ex);
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "Alumno/agregar.jsp");
        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }

    void successful(HttpServletRequest request, HttpServletResponse response, String nombre, String app, String apm) throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Hecho");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Alumno agregado.");
        request.setAttribute("DESCRIPCION", "Se ha agregado correctamente al alumno: <br>" + nombre + " " + app + " " + " " + apm);
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "AdministrarAlumno");
        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }

}
