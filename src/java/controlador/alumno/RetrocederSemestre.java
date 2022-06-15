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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class RetrocederSemestre extends HttpServlet {
    
    PrintWriter out;
    //Alumno [] alumnos;//Arreglo donde se guardaran los alumnos
    ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
    
    @Override
     public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        response.setContentType("text/html");
        out = response.getWriter();
        HttpSession session=request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

         if (tipoS != null)//Si se inicio sesión
         {
             if(tipoS.compareTo("2")==0) //Inicia sesión un admin
             {
                 int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                 if(rol==0 || rol==1)//Si es SuperAdministrador o Administrador, entonces puede modificar alumnos
                 {
                     Connection conexion = null;
                     try {
                        Class.forName("org.postgresql.Driver");
                        //Direccion, puerto, nombre de BD, usuario y contraseña
                        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                        
                        conexion = DriverManager.getConnection(
                                    datos_conexion.getDireccion(),
                                    datos_conexion.getUsuario(),
                                    datos_conexion.getContrasenia());
                        conexion.setAutoCommit(false);//Definir que se ocuparan transacciones
                        //Disminuir el nivel de semestre de todos los alumnos de 2do semestre en adelante
                        actualizar(conexion, "UPDATE alumnos "
                                    + "SET semestre=(semestre-1) "
                                    + "WHERE semestre>1 and status='INSCRITO'");
                         
                         //Borrar la ultima fecha en la que se avanzo el semestre, ya que con esta accion se borrara lo que se hizo al avanzar
                         borrarFecha(conexion);
                         //Hacer commit
                            conexion.commit();
                            //Cerrar conexion
                            conexion.close();
                         //Redireccionar a AdministrarAlumno
                        response.sendRedirect(request.getContextPath() + "/AdministrarAlumno");
                         
                     } catch (ClassNotFoundException | SQLException ex) {
                         //Logger.getLogger(AvanzarSemestre.class.getName()).log(Level.SEVERE, null, ex);
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
                         request.setAttribute("NOMBRE_MENSAJE", "Error");
                        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                        request.setAttribute("DESCRIPCION", ex.toString());
                        request.setAttribute("MENSAJEBOTON", "Volver");
                        request.setAttribute("DIRECCIONBOTON", "index.jsp");
                        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                     }
                     //Logger.getLogger(AvanzarSemestre.class.getName()).log(Level.SEVERE, null, ex);
                     
                 }
                 else//Si no, no tiene permiso
                 {
                    request.setAttribute("NOMBRE_MENSAJE", "Error");
                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                    request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                    request.setAttribute("MENSAJEBOTON", "Volver");
                    request.setAttribute("DIRECCIONBOTON", "index.jsp");
                    request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                 }
                     
             }
             else//Iniicio sesión otra persona
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
        //Cerrar Statement
        st.close();
    }
     void borrarFecha(Connection conexion) throws ClassNotFoundException, SQLException
     {
         Statement st = null;
         //Borrar la ultima fecha en la que se avanzo el semestre
         String query = "delete from avance_semestre where fecha=(select max(fecha) from avance_semestre);";
         st = conexion.createStatement();
         //Ejecutar accion
         st.executeUpdate(query);
         //Cerrar Statement
         st.close();
         
     }
}
