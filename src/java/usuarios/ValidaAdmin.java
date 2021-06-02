/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 *
 * @author hbdye
 */
public class ValidaAdmin extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1"; //Se indica la localizacion de la BD
        Connection con = null;//Se declara el objeto Connection "con" un valor nulo

        //Para mensajes
        response.setContentType("text/html"); //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida) 
        PrintWriter out = response.getWriter();

        try {
            //Obtener los valores desde el JSP
            String correo = request.getParameter("email");
            String password = request.getParameter("password");

            //Convertir los String en UTF-8
            byte[] tempBytes = correo.getBytes();
            correo = new String(tempBytes, StandardCharsets.UTF_8);
            tempBytes = password.getBytes();
            password = new String(tempBytes, StandardCharsets.UTF_8);

            //Preparar el Query de consulta
            String query = "Select * from administradores  WHERE correo='" + correo + "' and contrasenia='" + password + "'";
            out.println(query);

            //Cargar el driver para establecer la conexion desde el jar correspondiente
            Class.forName("org.postgresql.Driver");
            //Se crea la conexion usando el "url" y los datos del usuario
            con = DriverManager.getConnection(url, "postgres", "Adgjmptw1797@1");
            //Crea la sentencia o instruccion sobre la que se ejecutara el query
            Statement inst = con.createStatement();
            //Se ejecuta el query y se almacena la consulta en rs
            ResultSet rs = inst.executeQuery(query);
            //out.println("Query ejecutado");

            if (rs.next()) {//Si hace match
                //Guardar los valores de nombre y tipo de usuario
                String nombre = rs.getString(1).trim();
                String tipo = rs.getString(4).trim();

                //Crear las varianbles de sesion
                HttpSession session = request.getSession(true);
                session.setAttribute("NOMBRE", nombre);
                session.setAttribute("TIPO", tipo);
                //out.println("Encontrado");

                //Redirigir al index
            request.getRequestDispatcher("index.jsp").forward(request, response);
                //(se invoca el JSP "index" enviando los atributos que se han creado)
            } else {//Si no hay coincidencias

                //out.println("No encontrado");
                //Modificar los atributos
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "No se ha iniciado sesión");
                request.setAttribute("DESCRIPCION", "Ha ocurrido un error, el correo o la contraseña no son válidos.");
                //Redireccionar al JSP de mensajes
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                //(se invoca el JSP "mensaje" enviando los atributos que se han creado)
            }

            con.close();//Se cierra la conexion
            inst.close();//Se cierra la instruccion o el Statement
            //(se invoca el JSP "DespliegaConsultaGeneral"enviando los atributos que se han creado, en este caso "PLATILLOS")
        } catch (Exception exc) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error");
            request.setAttribute("DESCRIPCION", exc);
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                //(se invoca el JSP "mensaje" enviando los atributos que se han creado)
        }
    }

}
