package controlador.alumno;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controlador.Conexion_bd;
import controlador.ConvertirUTF8;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hbdye
 */
public class EditarAlumno extends HttpServlet {

    PrintWriter out;
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        response.setContentType("text/html");
        out = response.getWriter();

        //Objeto para convertir una cadena a UTF-8
        ConvertirUTF8 convert = new ConvertirUTF8();

        //Recibir los parametros desde un JSP (Datos de la encuesta)
        String matricula = (String) request.getParameter("matricula");
        if (matricula != null)//Si si se recibieron valores, entonces proceder
        {
            //Obtener los valores
            matricula = convert.convertToUTF8(matricula);
            String nombre = convert.convertToUTF8((String) request.getParameter("name"));
            String app = convert.convertToUTF8((String) request.getParameter("app"));//No se pregunta si es nulo o vacio ya que es un campo obligatorio
            String apm = (String) request.getParameter("apm");//Se pregunta si es nulo o vacio ya que no es un campo obligatorio
            if (apm != null && apm.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                apm = convert.convertToUTF8(apm);
            }
            String fechaTemp = (String) request.getParameter("fechaNac");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date =null;
            try
            {
                 date=sdf1.parse(fechaTemp);
            } catch (ParseException ex) {
            //Codigo de exception
            }
            java.sql.Date fechaNac = new java.sql.Date(date.getTime());

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
            String correo = (String) request.getParameter("email");
            if (correo != null && correo.compareTo("") != 0)//Si lo recibido es diferente de nulo o diferente de vacio, encontes convertir
            {
                correo = convert.convertToUTF8(correo);
            }
            String telefono = (String) request.getParameter("telefono");
            //Ahora que se tienen los datos del alumno, hay que insertarlos.
            try {
                //Conexion a la BD
                Class.forName("org.postgresql.Driver");
                //Direccion, puerto, nombre de BD, usuario y contraseña
                Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

                
                //Query para conectar
                String query = "UPDATE alumnos "
                        + "SET "
                        + "nombre=?, "
                        + "app=?, "
                        + "apm=?, "
                        + "status=?, "
                        + "carrera_nom=?, "
                        + "planest=?, "
                        + "generacion=?, "
                        + "grupo=?, "
                        + "semestre=?, "
                        + "fecha_nac=?, "
                        + "curp=?, "
                        + "sexo=?, "
                        + "estado=?, "
                        + "municipio=?, "
                        + "cp=?, "
                        + "email=?, "
                        + "telefono=? "
                        + "WHERE num_control="+matricula;
                //Ejecutar el Query
                PreparedStatement st = null;
                st=conexion.prepareStatement(query);
                //Agregar los datos que se actualizaran
                st.setString(1,nombre.toUpperCase());
                st.setString(2,app.toUpperCase());
                st.setString(3,apm.toUpperCase());
                st.setString(4,estatus);
                st.setString(5,carrera.toUpperCase());
                st.setString(6,plan.toUpperCase());
                st.setString(7,generacion.toUpperCase());
                st.setString(8,grupo.toUpperCase());
                st.setString(9,semestre);
                st.setDate(10,fechaNac);
                st.setString(11,curp.toUpperCase());
                st.setString(12,sexo);
                st.setString(13,estado);
                st.setString(14,municipio.toUpperCase());
                st.setString(15,cp);
                st.setString(16,correo);
                st.setString(17,telefono);
                
                st.executeUpdate();
                      
                //Cerrar conexion
                conexion.close();
                st.close();
                successful(request,response,matricula);
            } catch (ClassNotFoundException | SQLException ex) {
                error(request, response, ex.getMessage());
            }
        }
        else
        {
            error(request, response, "no  se recibio algún dato.");
        }
        

    }
    void error(HttpServletRequest request, HttpServletResponse response, String ex) throws ServletException, IOException
    {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "Error al modificar en la base de datos: \n" + ex);
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "AdministrarAlumno");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }
    void successful(HttpServletRequest request, HttpServletResponse response, String matricula) throws ServletException, IOException
    {
        /*request.setAttribute("NOMBRE_MENSAJE", "Hecho");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Alumno modificado.");
                request.setAttribute("DESCRIPCION", "Se ha modificado correctamente al alumno: <br>"+nombre+" "+app+" "+" "+apm);
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "AdministrarAlumno");*/
                request.setAttribute("matricula", matricula);
                request.getRequestDispatcher("VerAlumno").forward(request, response);
    }
}
