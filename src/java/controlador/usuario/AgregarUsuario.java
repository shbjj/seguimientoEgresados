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
//import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hbdye
 */
public class AgregarUsuario extends HttpServlet {

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
                int rolS = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rolS == 0 || rolS == 1)//Si es SuperAdministrador o Administrador, entonces puede agregar usuarios nuevos
                {
                    //Objeto para convertir las cadenas recibidas a UTF-8
                    //ConvertirUTF8 convert = new ConvertirUTF8();

                    //Obtener los valores
                    String user = (String) request.getParameter("user");
                    //Ver si hay valores obtenidos, si no mandar a mensaje de error
                    if (user != null) {
                        //Obtener los valores
//                        user = convert.convertToUTF8(user);
//                        String rol = convert.convertToUTF8((String) request.getParameter("rol"));//No se pregunta si es nulo o vacio ya que es un campo obligatorio
//                        String password = convert.convertToUTF8((String) request.getParameter("password1"));//No se pregunta si es nulo o vacio ya que es un campo obligatorio
                        
                        String rol = (String) request.getParameter("rol");//No se pregunta si es nulo o vacio ya que es un campo obligatorio
                        String password = (String) request.getParameter("password1");//No se pregunta si es nulo o vacio ya que es un campo obligatorio

                        //Ahora que se tienen los datos del usuario, hay que insertarlos.
                        try {
                            //Conexion a la BD
                            Class.forName("org.postgresql.Driver");
                            //Direccion, puerto, nombre de BD, usuario y contraseña
                            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

                            //Query para conectar
                            String query = "INSERT INTO administradores "
                                    + "VALUES (?,md5(?),?)";
                            //Ejecutar el Query
                            PreparedStatement stmt = null;
                            stmt = conexion.prepareStatement(query);
                            stmt.setString(1, user);
                            stmt.setString(2, password);
                            stmt.setString(3, rol);
                            stmt.executeUpdate();
                            
                            //Cerrar sesión
                            conexion.close();
                            stmt.close();
                            successful(request, response, user);
                        } catch (ClassNotFoundException | SQLException ex) {
                            error(request, response, ex.getMessage());
                        }

                    } else {
                        error(request, response, "No se recibieron valores");
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

    void error(HttpServletRequest request, HttpServletResponse response, String ex) throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos: \n" + ex);
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "Usuario/agregar.jsp");
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }

    void successful(HttpServletRequest request, HttpServletResponse response, String nombre) throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Hecho");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Usuario agregado.");
        request.setAttribute("DESCRIPCION", "Se ha agregado correctamente al usuario: <br>" + nombre);
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "AdministrarUsuario");
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }

}
