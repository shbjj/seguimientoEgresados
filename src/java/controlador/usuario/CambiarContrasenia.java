 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import controlador.Conexion_bd;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hbdye
 */
public class CambiarContrasenia extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

         if (tipoS != null)//Si se inicio sesión
         {
             if(tipoS.compareTo("2")==0) //Inicia sesión un admin
             {
                 int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                 String userSession=(String)session.getAttribute("USUARIO");//Obtener el usuario de la sesión
                 String userForm=(String)request.getParameter("user");//Obtener el usuario del form
                 
                 //Hay que revisar que ambos usuarios sean los mismos, para mayor seguridad
                 if(userSession.compareTo(userForm)==0)//Si ambos usuarios son iguales, se puede cambiar la contraseña
                 {
                     String contrasenia=(String)request.getParameter("password1");//Obtener la contraseña
                     //Convertir a UTF8
                     //ConvertirUTF8 convert= new ConvertirUTF8();
                     //contrasenia=convert.convertToUTF8(contrasenia);
                     
                     //Realizar el cambio de contraseña
                     try {
                            //Conexion a la BD
                            Class.forName("org.postgresql.Driver");
                            //Direccion, puerto, nombre de BD, usuario y contraseña
                            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                            Connection conexion = DriverManager.getConnection(
                                    datos_conexion.getDireccion(),
                                    datos_conexion.getUsuario(), 
                                    datos_conexion.getContrasenia());
                            String query = "";
                                //Ejecutar el Query
                            PreparedStatement stmt = null;
                            
                            //Query para conectar
                            query = "UPDATE administradores "
                                    + "SET contrasenia=md5(?) "
                                    + "WHERE nombre=?";
                            //Ejecutar el Query
                            stmt = conexion.prepareStatement(query);
                            stmt.setString(1, contrasenia);//Contraseña nueva
                            stmt.setString(2, userSession);//Nombre de usuario
                            
                            
                            stmt.executeUpdate();
                            
                            //Cerrar conexion
                            conexion.close();
                            stmt.close();
                            //Cerrar sesion
                            session.setAttribute("MATRICULA",null);
                            session.setAttribute("NOMBRE",null);
                            session.setAttribute("USUARIO",null);
                            session.setAttribute("ROL",null);
                            session.setAttribute("ID_ENCUESTA",null);
                            session.setAttribute("TIPO",null);
                            successful(request, response, userSession);
                        } catch (ClassNotFoundException | SQLException ex) {
                            error(request, response, ex.getMessage());
                        }

                     
                 }
                 else//Si no, no tiene permiso
                 {
                    request.setAttribute("NOMBRE_MENSAJE", "Error");
                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                    request.setAttribute("DESCRIPCION", "No tiene permiso para cambiar la contraseña de otro usuario.");
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
    void error(HttpServletRequest request, HttpServletResponse response, String ex) throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", "Error al modificar en la base de datos: <br>" + ex);
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "Usuario/cambiarContrasenia");
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }
    void successful(HttpServletRequest request, HttpServletResponse response, String nombre) throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Hecho");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Contraseña cambiada.");
        request.setAttribute("DESCRIPCION", "Se ha cambiado correctamente la contraseña del usuario: " + nombre
                            +"<br>Se cerrara sesión.");
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "index.jsp");
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }
}
