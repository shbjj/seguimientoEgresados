/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.taller;

import controlador.Conexion_bd;
import controlador.Excel;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Dia;
import modelo.Taller;

/**
 *
 * @author hbdye
 */
public class ListaAsistencia extends HttpServlet {

    PrintWriter out;

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        //response.setContentType("text/html");

        out = response.getWriter();
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rol == 0 || rol == 1 || rol == 3)//Si es SuperAdministrador, Administrador o tallerista,
                //entonces puede agregar talleres nuevos
                {

                    //Obtener los valores
                    String idTaller = (String) request.getParameter("idTaller");
                    //Ver si hay valores obtenidos, si no mandar a mensaje de error
                    if (idTaller != null) {
                        try {
                            //Si hav valores validos, hay que obtener todos los valores

                            Taller taller=consultarTaller(idTaller);
                            ArrayList <String> alumnos=consultarAlumnos(idTaller);
                            
                            request.setCharacterEncoding("UTF-8");

                        //Set the response header.
//                        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//                        response.setHeader("content-disposition", String.format("attachment; filename=\"%s\"", "Lista_"+taller.getNombre()+".xlsx"));
//                        response.setCharacterEncoding("UTF-8");
                        
                        Excel.modificar(taller.getInstructor(), taller.getNombre(), taller.getPeriodo(), alumnos);
                        
                            
                        } catch (SQLException ex) {
                            error(ex.toString(), request, response);
                        }
                        
                        
                        

                    } else {
                        error("No obtuvieron valores validos", request, response);
                    }

                } else//Si no, no tiene permiso
                {
                    error("No tiene permiso para acceder a este contenido", request, response);
                }

            } else//Iniicio sesión otra persona
            {
                error("No tiene permiso para acceder a este contenido otra", request, response);
            }
        } else//no hay una sesión iniciada
        {
            //Redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    void error(String mensaje, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", mensaje);
        request.setAttribute("MENSAJEBOTON", "Volver");
        request.setAttribute("DIRECCIONBOTON", "index.jsp");
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }
    
    Taller consultarTaller(String idTaller) throws SQLException
    {         
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(), 
                datos_conexion.getUsuario(), 
                datos_conexion.getContrasenia());
        String query="select t.nombre, t.instructor, t.periodo " +
                        "from talleres t " +
                        "where t.idtaller="+idTaller;
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        //Guardar datos
        Taller taller=null;//Taller temporal
          while (rs.next()) {
              //Instanciar un nuevo taller
              taller=new Taller();
              //Agregarle los valores
              taller.setNombre(rs.getString(1).trim());
              taller.setInstructor(rs.getString(2).trim());
              taller.setPeriodo(rs.getString(3).trim());         
          }
          //Cerrar objetos
          rs.close(); 
          st.close(); 
          
          return taller;
          
    }
    
    ArrayList<String> consultarAlumnos(String idTaller) throws SQLException
    {         
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(), 
                datos_conexion.getUsuario(), 
                datos_conexion.getContrasenia());
        String query="select a.nombre, a.app, a.apm " +
                    "from alumnos a, boletas b " +
                    "where b.idtaller="+idTaller+" and a.num_control=b.num_control "
                + "order by a.app;";
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        //Guardar datos
        ArrayList<String> t=new ArrayList();
        String alumno=null;//Taller temporal
          while (rs.next()) {
              //Instanciar un nuevo taller
              alumno=rs.getString(2).trim()+" "+rs.getString(3).trim()+" "+rs.getString(1);  
              t.add(alumno);
          }
          //Cerrar objetos
          rs.close(); 
          st.close(); 
          
          //Cerrar conexion
          conexion.close();
          return t;
          
    }

}
