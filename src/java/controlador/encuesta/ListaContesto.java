/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.encuesta;

import controlador.Conexion_bd;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Alumno;
import modelo.Encuesta;

/**
 *
 * @author hbdye
 */
public class ListaContesto extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rol == 0 || rol == 1 || rol == 3)//Si es SuperAdministrador, Administrador o de talleres, entonces puede ver la lista
                {
                    String tipoLista = (String) request.getParameter("tipoLista");
                    String idEncuesta = (String) request.getParameter("idEncuesta");
                    if (tipoLista != null) {
                        try {
                            //Cargar driver
                            Class.forName("org.postgresql.Driver");
                            //Buscar los alumnos de la lista
                            buscar(tipoLista, idEncuesta, request, response);
                            //Obtener datos de la encuesta
                            Encuesta encuesta = datosEncuesta(idEncuesta);

                            //Enviar parametros
                            request.setAttribute("TIPOLISTA", tipoLista);
                            request.setAttribute("ENCUESTA", encuesta);
                            //Redirigir
                            request.getRequestDispatcher("Encuesta/lista.jsp").forward(request, response);

                        } catch (ClassNotFoundException | SQLException ex) {
                            error(request, response, ex.toString());
                        }
                    } else {
                        error(request, response, "No se obtuvieron valores");
                    }
                } else//Si no, no tiene permiso
                {
                    error(request, response, "No tiene permiso para acceder a este contenido");
                }

            } else//Iniicio sesión otra persona
            {
                error(request, response, "No tiene permiso para acceder a este contenido");
            }
        } else//no hay una sesión iniciada
        {
            //Redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    void error(HttpServletRequest request, HttpServletResponse response, String mensaje) throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", mensaje);
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "index.jsp");
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }

    Encuesta datosEncuesta(String idEncuesta) throws ClassNotFoundException, SQLException {
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());
        String query = "SELECT nombre, descripcion, fecha "
                + "FROM encuestas "
                + "WHERE id_encuestas=" + idEncuesta;
        //Ejecutar el Query

        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        Encuesta encuesta = null;//Alumno temporal
        while (rs.next()) {
            //Instanciar un nuevo alumno
            encuesta = new Encuesta();
            //Agregarle los valores
            encuesta.setId_encuestas(idEncuesta);
            encuesta.setNombre(rs.getString(1).trim());
            encuesta.setDescripcion(rs.getString(2).trim());
            encuesta.setFecha(rs.getString(3));
        }

        //Cerrar conexion
        conexion.close();
        rs.close();
        st.close();

        return encuesta;
    }

    void buscar(String tipoLista, String idEncuesta, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());
        String query = "";
        //Ejecutar el Query
        if (tipoLista.compareTo("1") == 0)//Si la lista es de los que SI han respondido la encuesta
        {
            query = "SELECT distinct a.num_control, a.app, a.apm, a.nombre, a.email, a.telefono "
                    + " FROM alumnos a, respuestas r"
                    + " WHERE a.num_control=r.num_control and r.id_encuestas=" + idEncuesta;
        } else//Si no, la lista es de los que NO han contestado la encuesta
        {
            query = "SELECT al.num_control, al.app, al.apm, al.nombre, al.email, al.telefono"
                    + " FROM alumnos al"
                    + " WHERE al.num_control not in ("
                    + " SELECT distinct a.num_control "
                    + " FROM alumnos a, respuestas r"
                    + " WHERE a.num_control=r.num_control and r.id_encuestas=" + idEncuesta + ") and al.status!='INSCRITO'";
        }

        /*
                SELECT count(*)
                FROM alumnos al
                WHERE al.num_control not in (
                SELECT a.num_control 
                FROM alumnos a, respuestas r
                WHERE a.num_control=r.num_control and r.id_encuestas=38)
         */
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        Alumno alumno = null;//Alumno temporal
        while (rs.next()) {
            //Instanciar un nuevo alumno
            alumno = new Alumno();
            //Agregarle los valores
            alumno.setMatricula(String.valueOf(rs.getInt(1)));//Obtener la matricula
            alumno.setApp(rs.getString(2).trim());
            alumno.setApm(rs.getString(3));
            alumno.setNombre(rs.getString(4).trim());
            alumno.setCorreo(rs.getString(5));
            alumno.setTelefono(rs.getString(6));
            //Agregar el alumno al Array
            alumnos.add(alumno);
        }

        //Cerrar conexion
        conexion.close();
        rs.close();
        st.close();

        request.setAttribute("ALUMNOS", alumnos.toArray());
        alumnos.clear();
        alumnos=null;
    }
}
