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
import java.sql.Statement;
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
public class RetrocederSemestre extends HttpServlet {
    PrintWriter out;
    //Alumno [] alumnos;//Arreglo donde se guardaran los alumnos
    ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
    
    @Override
     public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                     try {
                        Class.forName("org.postgresql.Driver");

                        //Obtener todos los alumnos que esten inscritos (no sean egresados)
                         obtenerAlumnos();
                         
                         //Recorrer el ArrayList e ir cambiando los datos
                         Iterator <Alumno> alumnosIterator=alumnos.iterator();
                         Alumno alumno=null;
                         while(alumnosIterator.hasNext())
                         {
                             alumno=alumnosIterator.next();
                             modificarSemestre(alumno);
                         }
                         
                         //Borrar la ultima fecha en la que se avanzo el semestre, ya que con esta accion se borrara lo que se hizo al avanzar
                         borrarFecha();
                         //Redireccionar a AdministrarAlumno
                        response.sendRedirect(request.getContextPath() + "/AdministrarAlumno");
                         
                     } catch (ClassNotFoundException ex) {
                         //Logger.getLogger(AvanzarSemestre.class.getName()).log(Level.SEVERE, null, ex);
                         out.print("<br>ERORR: "+ex);
                     } catch (SQLException ex) {
                         //Logger.getLogger(AvanzarSemestre.class.getName()).log(Level.SEVERE, null, ex);
                         out.print("<br>ERORR: "+ex);
                     }
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
     
     void obtenerAlumnos() throws ClassNotFoundException, SQLException
     {
          
                //Direccion, puerto, nombre de BD, usuario y contraseña
                Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                Connection conexion = DriverManager.getConnection(
                        datos_conexion.getDireccion(), 
                        datos_conexion.getUsuario(), 
                        datos_conexion.getContrasenia());
          //Obtener el num_control y semestre de los alumnos inscritos      
          String query = "SELECT num_control, semestre "
                  + "FROM alumnos "
                  + "WHERE status='INSCRITO'";
          out.print("<br> Query de busqueda: "+query);
          PreparedStatement st = null;
          ResultSet rs = null;
          st = conexion.prepareStatement(query);
          rs = st.executeQuery();
          Alumno alumno=null;//Alumno temporal
          while (rs.next()) {
              //Instanciar un nuevo alumno
              alumno=new Alumno();
              //Agregarle los valores
              alumno.setMatricula(String.valueOf(rs.getInt(1)));//Obtener la matricula
              alumno.setSemestre(rs.getString(2).trim());//Obtener el semestre
              //Agregar el alumno al Array
              alumnos.add(alumno);
          }
          
          //Cerrar conexion
          conexion.close();
          rs.close();
          st.close();
     }
     
     void modificarSemestre(Alumno alumno) throws ClassNotFoundException, SQLException
     {
        //Obtener el valor numerico del semestre del alumno
         int semestre = Integer.parseInt(alumno.getSemestre());
         //AUMENTAR VALOR DEL SEMESTRE
         if (semestre > 1)//Si el semestre es mayor a 1, se disminuye el valor, si no, entonces no hacer nada
         {
             //Direccion, puerto, nombre de BD, usuario y contraseña
             Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
             Connection conexion = DriverManager.getConnection(
                     datos_conexion.getDireccion(),
                     datos_conexion.getUsuario(),
                     datos_conexion.getContrasenia());
             String query;//Query de modificacion
             PreparedStatement st = null;

             semestre--;//Disminuir semestre
             query = "UPDATE alumnos "
                     + "SET semestre=? "
                     + "WHERE num_control=?";
             out.print("<br> Query de actualizacion: " + query);
             st = conexion.prepareStatement(query);
             //Agregar valor del semestre
             st.setString(1, String.valueOf(semestre));//Semestre
             st.setInt(2, Integer.parseInt(alumno.getMatricula()));//Matricula
             //Ejecutar accion
             st.executeUpdate();
             //Cerrar conexion
             st.close();
             conexion.close();
         }
     }
     
     void borrarFecha() throws ClassNotFoundException, SQLException
     {
         //Direccion, puerto, nombre de BD, usuario y contraseña
         Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
         Connection conexion = DriverManager.getConnection(
                 datos_conexion.getDireccion(),
                 datos_conexion.getUsuario(),
                 datos_conexion.getContrasenia());
         Statement st = null;
         //Borrar la ultima fecha en la que se avanzo el semestre
         String query = "delete from avance_semestre where fecha=(select max(fecha) from avance_semestre);";
         st = conexion.createStatement();
         //Ejecutar accion
         st.executeUpdate(query);
         //Cerrar conexion
         st.close();
         conexion.close();
         
     }
}
