/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hbdye
 */
public class CrearEncuesta extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        //response.setContentType("text/html");
        //PrintWriter out = response.getWriter();

        //Recibir los parametros desde un JSP
        String nombre, descripcion, instrucciones, despedida, fecha, clave;
        nombre = (String) request.getParameter("nombre");
        descripcion = (String) request.getParameter("descripcion");
        instrucciones = (String) request.getParameter("instrucciones");
        despedida = (String) request.getParameter("despedida");
        fecha = (String) request.getParameter("fecha");
        clave = (String) request.getParameter("clave");

        //Pasar los datos recibidos a UTF-8
        byte[] tempBytes = nombre.getBytes();
        nombre = new String(tempBytes, StandardCharsets.UTF_8);
        tempBytes = descripcion.getBytes();
        descripcion = new String(tempBytes, StandardCharsets.UTF_8);
        tempBytes = instrucciones.getBytes();
        instrucciones = new String(tempBytes, StandardCharsets.UTF_8);
        tempBytes = despedida.getBytes();
        despedida = new String(tempBytes, StandardCharsets.UTF_8);
        tempBytes = fecha.getBytes();
        fecha = new String(tempBytes, StandardCharsets.UTF_8);
        tempBytes = clave.getBytes();
        clave = new String(tempBytes, StandardCharsets.UTF_8);

        try {
            //Conexion a la BD
            Class.forName("org.postgresql.Driver");
            //Direccion, puerto, nombre de BD, usuario y contraseña
              Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
       Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

            //Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");

            //Query para conectar
            String query = "Insert into encuestas(nombre, descripcion, instrucciones, despedida, fecha, clave, habilitada) values('" + nombre + "','" + descripcion + "','" + instrucciones + "','" + despedida + "','" + fecha + "','" + clave + "','s');";
            //out.println(query);//Imprimir por errores

            //Ejecutar el Query
            /*
            Statement st = conexion.createStatement();
            st.executeUpdate(query);*/
            long id_encuesta = 0;
            int id = 0;
            PreparedStatement ps = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            id_encuesta = ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }

            //Enviar parametros al otro JSP de "salida"
            request.setAttribute("IDENCUESTA", id);
            request.setAttribute("NOMBRE", nombre);
            request.setAttribute("DESCRIPCION", descripcion);
            request.setAttribute("INSTRUCCIONES", instrucciones);
            request.setAttribute("DESPEDIDA", despedida);
            request.setAttribute("FECHA", fecha);
            request.setAttribute("CLAVE", clave);

            //Pasar a otra Pagina
            RequestDispatcher vista = request.getRequestDispatcher("agregarPreguntas.jsp");
            //Enviar al area de preguntas los request y response
            vista.forward(request, response);

        } catch (ClassNotFoundException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos:\n" + ex);
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos:\n" + ex);
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        }
    }

}
