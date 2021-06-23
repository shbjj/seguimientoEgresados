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
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hbdye
 */
public class EliminarAlumno extends HttpServlet {
public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String matricula = (String) request.getParameter("matricula");
        if(matricula!=null & matricula.compareTo("")!=0)//Si si hay un valor recibido
        {
            try {
            Class.forName("org.postgresql.Driver");
            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
            Statement inst = conexion.createStatement(); //Crea la sentencia o instruccion sobre la que se ejecutara el query
            String query = "DELETE FROM alumnos where num_control =" + matricula;//Declarando el query que se va ejecutar para la consulta
            inst.executeUpdate(query);
            conexion.close();//Se cierra la conexion
        } catch (ClassNotFoundException | SQLException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "Error al eliminar al alumno:\n" + ex);
                request.setAttribute("DIRECCIONBOTON","AdministrarAlumnos");
                request.setAttribute("MENSAJEBOTON","Volver");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        }
        //Direccion, puerto, nombre de BD, usuario y contraseña

        //RequestDispatcher vista = getServletContext().getRequestDispatcher("/AdministrarAlumno");
        //vista.forward(request, response);
        response.sendRedirect(request.getContextPath() + "/AdministrarAlumno");
        }
        else
        {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "Error al eliminar al alumno, no se recibieron valores\n");
                request.setAttribute("DIRECCIONBOTON","AdministrarAlumno");
                request.setAttribute("MENSAJEBOTON","Volver");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        }
        
    }
}