/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.alumno;

import controlador.Conexion_bd;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Alumno;

/**
 *
 * @author hbdye
 */
public class AvanzarSemestre extends HttpServlet {

    PrintWriter out;
    //Alumno [] alumnos;//Arreglo donde se guardaran los alumnos
    ArrayList<Alumno> alumnos = new ArrayList<Alumno>();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        response.setContentType("text/html");
        out = response.getWriter();
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rol == 0 || rol == 1)//Si es SuperAdministrador o Administrador, entonces puede modificar alumnos
                {
                    try {
                        Class.forName("org.postgresql.Driver");
                        //Direccion, puerto, nombre de BD, usuario y contraseña
                        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                        Connection conexion = null;
                        try {
                            conexion = DriverManager.getConnection(
                                    datos_conexion.getDireccion(),
                                    datos_conexion.getUsuario(),
                                    datos_conexion.getContrasenia());
                            conexion.setAutoCommit(false);//Definir que se ocuparan transacciones
                            //Cambiar estado a EGRESADO a los alumnos que estan en 6to semestre
                            actualizar(conexion, "UPDATE alumnos "
                                    + "SET status='EGRESADO' "
                                    + "WHERE semestre=6");
                            //Sumar 1 a todos los alumnos que estan entre los semestres 1 a 5
                            actualizar(conexion, "UPDATE alumnos "
                                    + "SET semestre=(semestre+1) "
                                    + "WHERE status='INSCRITO'");
                            
                            //Agregar la fecha de cambio a la BD
                            agregarFecha(conexion);
                            
                            //Hacer commit
                            conexion.commit();
                            //Cerrar conexion
                            conexion.close();

                        } catch (SQLException ex) {
                            //Si hay error
                            if (conexion != null) {
                                try {
                                    //Deshacer los errores
                                    conexion.rollback();
                                    conexion.close();
                                } catch (SQLException ex2) {
                                    request.setAttribute("NOMBRE_MENSAJE", "Error");
                                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                                    request.setAttribute("DESCRIPCION", ex2.toString());
                                    request.setAttribute("MENSAJEBOTON", "Volver");
                                    request.setAttribute("DIRECCIONBOTON", "index.jsp");
                                    request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                                }
                            }
                        }
                        

                        //Redireccionar a AdministrarAlumno
                        response.sendRedirect(request.getContextPath() + "/AdministrarAlumno");

                    } catch (ClassNotFoundException ex) {
                        //Logger.getLogger(AvanzarSemestre.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("NOMBRE_MENSAJE", "Error");
                        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                        request.setAttribute("DESCRIPCION", ex.toString());
                        request.setAttribute("MENSAJEBOTON", "Volver");
                        request.setAttribute("DIRECCIONBOTON", "index.jsp");
                        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                    } 
                } else//Si no, no tiene permiso
                {
                    request.setAttribute("NOMBRE_MENSAJE", "Error");
                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                    request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                    request.setAttribute("MENSAJEBOTON", "Volver");
                    request.setAttribute("DIRECCIONBOTON", "index.jsp");
                    request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                }

            } else//Iniicio sesión otra persona
            {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "index.jsp");
                request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
            }
        } else//no hay una sesión iniciada
        {
            //Redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
    

    void actualizar(Connection conexion, String query) throws SQLException {
        PreparedStatement st = null;
        
        st = conexion.prepareStatement(query);
        
        //Ejecutar accion
        st.executeUpdate();
        //Cerrar conexion
        st.close();
    }

    void agregarFecha(Connection conexion) throws SQLException {
        PreparedStatement st = null;
        String query = "INSERT INTO avance_semestre(fecha) VALUES "
                + "(?);";
        st = conexion.prepareStatement(query);

        //Obtener fecha
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        st.setDate(1, sqlDate);//Fecha

        //Ejecutar accion
        st.executeUpdate();
        //Cerrar conexion
        st.close();
    }
}
