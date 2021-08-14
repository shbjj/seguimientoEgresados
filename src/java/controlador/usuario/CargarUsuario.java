/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

import controlador.Conexion_bd;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

/**
 *
 * @author hbdye
 */
public class CargarUsuario extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

         if (tipoS != null)//Si se inicio sesión
         {
             if(tipoS.compareTo("2")==0) //Inicia sesión un admin
             {
                 int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                 if(rol==0 || rol==1)//Si es SuperAdministrador o Administrador, entonces puede agregar usuarios nuevos
                 {
                 String nombre = (String) request.getParameter("nombre");//Obtener el nombre del usuario
                        if(nombre!=null)//Si si hay un valor recibido
                        {
                            try {
                            Class.forName("org.postgresql.Driver");
                            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(),
                                    datos_conexion.getUsuario(),
                                    datos_conexion.getContrasenia());

                            Statement st = conexion.createStatement(); //Crea la sentencia o instruccion sobre la que se ejecutara el query
                            //Declarando el query que se va ejecutar para la consulta
                            String query = "SELECT nombre, rol "
                                    + "FROM administradores "
                                    + "where nombre ='" + nombre+"'";
                            //Ejecutar el query
                            ResultSet rs = st.executeQuery(query);
                            //Preparae el Statement
                            rs.next();

                            //Obtener valores
                                //Variable de tipo alumno que guardara los valores
                                Usuario usuario=new Usuario();
                                //Ponerle al usuario los valores
                                usuario.setNombre(rs.getString(1).trim());
                                usuario.setRol(rs.getString(2).trim());
                                
                                //Cerrar conexión
                                conexion.close();
                                st.close();
                                rs.close();
                                
                                request.setAttribute("USUARIO", usuario);//Enviar el objeto de tipo alumno con la informaciónn
                                request.getRequestDispatcher("Usuario/editar.jsp").forward(request, response);

                            } catch (ClassNotFoundException | SQLException ex) {
                                request.setAttribute("NOMBRE_MENSAJE", "Error");
                                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                                    request.setAttribute("DESCRIPCION", "Error al cargar datos del usuario: " + nombre+
                                            " verifique que si exista el usuario.<br>"+ex);
                                    request.setAttribute("DIRECCIONBOTON","AdministrarUsuario");
                                    request.setAttribute("MENSAJEBOTON","Volver");
                                    request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                            }
                        
                        }
                        else//Si no se recibieron valores 
                        {
                            request.setAttribute("NOMBRE_MENSAJE", "Error");
                                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                                request.setAttribute("DESCRIPCION", "Error al cargar los datos del usuario, no se recibio ningun valor\n");
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
