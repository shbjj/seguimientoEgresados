/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.alumno;

import controlador.Conexion_bd;
import controlador.ConvertirUTF8;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hugo Blancas Dominguez
 */
public class AgregarAlumno extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Objeto para convertir las cadenas recibidas a UTF-8
        ConvertirUTF8 convert = new ConvertirUTF8();

        //Obtener los valores
        String nombre = (String) request.getParameter("name");
        //Ver si hay valores obtenidos, si no mandar a mensaje de error
        if (nombre != null && nombre.compareTo("") != 0) {
            //Obtener los valores
            nombre = convert.convertToUTF8(nombre);
            String app = convert.convertToUTF8((String) request.getParameter("app"));//No se pregunta si es nulo o vacio ya que es un campo obligatorio
            String apm = (String) request.getParameter("apm");//Se pregunta si es nulo o vacio ya que no es un campo obligatorio
            if (apm != null && apm.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                apm = convert.convertToUTF8(apm);
            }
            String fechaNac = (String) request.getParameter("fechaNac");
            String curp = convert.convertToUTF8((String) request.getParameter("curp"));
            String sexo = (String) request.getParameter("sexo");//no es necesario convertir
            String estado = (String) request.getParameter("estado");
            if (estado != null && estado.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                estado = convert.convertToUTF8(estado);
            }
            String municipio = (String) request.getParameter("municipio");
            if (municipio != null && municipio.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                municipio = convert.convertToUTF8(municipio);
            }
            String cp = (String) request.getParameter("cp");//Aunque son numeros, se puede guardar en un string
            String matricula = (String) request.getParameter("matricula");//Aunque son numeros, se puede guardar en un string
            String estatus = (String) request.getParameter("estatus");
            String semestre = (String) request.getParameter("semestre");
            String grupo = (String) request.getParameter("grupo");
            String carrera = (String) request.getParameter("carrera");
            if (carrera != null && carrera.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                carrera = convert.convertToUTF8(carrera);
            }
            String plan = (String) request.getParameter("plan");
            if (plan != null && plan.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                plan = convert.convertToUTF8(plan);
            }
            String generacion = (String) request.getParameter("generacion");
            if (generacion != null && generacion.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                generacion = convert.convertToUTF8(generacion);
            }

            //Ahora que se tienen los datos del alumno, hay que insertarlos.
            try {
                //Conexion a la BD
                Class.forName("org.postgresql.Driver");
                //Direccion, puerto, nombre de BD, usuario y contraseña
                Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

                //Query para conectar
                String query = "INSERT INTO alumnos"
                        + "(num_control,nombre,app,apm,status,carrera_nom,planest,grupo,"
                        + "semestre,fecha_nac,curp,sexo,estado,municipio,cp) "
                        + "VALUES "
                        + "("+matricula+","
                        + "'"+nombre+"',"
                        + "'"+app+"',"
                        + "'"+apm+"',"
                        + "'"+estatus+"',"
                        + "'"+carrera+"',"
                        + "'"+plan+"',"
                        + "'"+grupo+"',"
                        + "'"+semestre+"',"
                        + "'"+fechaNac+"',"
                        + "'"+curp+"',"
                        + "'"+sexo+"',"
                        + "'"+estado+"',"
                        + "'"+municipio+"',"
                        + "'"+cp+"')";
                //Ejecutar el Query
                Statement st = conexion.createStatement();
                st.executeUpdate(query);
                successful(request,response,nombre,app,apm);
            } catch (ClassNotFoundException | SQLException ex) {
                error(request, response, ex.getMessage());
            }

        }
        else
        {
            error(request, response, "No se recibieron valores");
        }
    }
    
    void error(HttpServletRequest request, HttpServletResponse response, String ex) throws ServletException, IOException
    {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos: \n" + ex);
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "Alumno/agregar.jsp");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }
    void successful(HttpServletRequest request, HttpServletResponse response, String nombre, String app, String apm) throws ServletException, IOException
    {
        request.setAttribute("NOMBRE_MENSAJE", "Hecho");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Alumno agregado.");
                request.setAttribute("DESCRIPCION", "Se ha agregado correctamente al alumno: <br>"+nombre+" "+app+" "+" "+apm);
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "AdministrarAlumno");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }

}
