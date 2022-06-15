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
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hbdye
 */
public class EliminarUsuario extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

         if (tipoS != null)//Si se inicio sesión
         {
             if(tipoS.compareTo("2")==0) //Inicia sesión un admin
             {
                 int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                 if(rol==0 || rol==1)//Si es SuperAdministrador o Administrador, entonces puede eliminar usuarios 
                 {
                     String nombre = (String) request.getParameter("nombre");
                    if(nombre!=null)//Si si hay un valor recibido
                    {
                        try {
                        Class.forName("org.postgresql.Driver");
                        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(),
                                datos_conexion.getUsuario(),
                                datos_conexion.getContrasenia());
                        Statement inst = conexion.createStatement(); //Crea la sentencia o instruccion sobre la que se ejecutara el query
                        String query = "DELETE FROM administradores where nombre ='" + nombre+"'";//Declarando el query que se va ejecutar para la consulta
                        inst.executeUpdate(query);
                        //Cerrar conexión
                                conexion.close();
                                inst.close();
                    } catch (ClassNotFoundException | SQLException ex) {
                        request.setAttribute("NOMBRE_MENSAJE", "Error");
                            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                            request.setAttribute("DESCRIPCION", "Error al eliminar al usuario:<br>" + ex);
                            request.setAttribute("DIRECCIONBOTON","AdministrarUsuarios");
                            request.setAttribute("MENSAJEBOTON","Volver");
                            request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                    }
                    //Direccion, puerto, nombre de BD, usuario y contraseña

                    //RequestDispatcher vista = getServletContext().getRequestDispatcher("/AdministrarAlumno");
                    //vista.forward(request, response);
                    response.sendRedirect(request.getContextPath() + "/AdministrarUsuario");
                    }
                    else
                    {
                        request.setAttribute("NOMBRE_MENSAJE", "Error");
                            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                            request.setAttribute("DESCRIPCION", "Error al eliminar al usario, no se recibieron valores\n");
                            request.setAttribute("DIRECCIONBOTON","AdministrarUsuario");
                            request.setAttribute("MENSAJEBOTON","Volver");
                            request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
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
}