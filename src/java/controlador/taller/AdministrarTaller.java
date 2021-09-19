/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.taller;

import controlador.Conexion_bd;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Taller;

/**
 *
 * @author hbdye
 */
public class AdministrarTaller extends HttpServlet {
    //PrintWriter out;
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Para la salida de mendajes en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        //response.setContentType("text/html");
        //out = response.getWriter();
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if(rol!=2)//Si es SuperAdministrador, Administrador o tallerista,
                                                //entonces puede administrar talleres 
                {

                     try {
                        //Cargar el driver en la clase
                        Class.forName("org.postgresql.Driver");
                        //Obtener una lista de los talleres abiertos
                        buscar("Abierto", request, response);
                        //Obtener una lista de los talleres cerrados
                        buscar("Cerrado", request, response);
                        
                        //Enviar al JSP
                        request.getRequestDispatcher("/Taller/administrar.jsp").forward(request, response);
                        
                     } catch (ClassNotFoundException | SQLException ex) {
                        error(ex.toString(), request, response);
                    }
                } else//Si no, no tiene permiso
                {
                    error("No tiene permiso para acceder a este contenido", request, response);
                }

            } else//Iniicio sesión otra persona
            {
                error("No tiene permiso para acceder a este contenido", request, response);
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
    
    void buscar(String estatus,HttpServletRequest request, HttpServletResponse response) throws SQLException
    {         
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(), 
                datos_conexion.getUsuario(), 
                datos_conexion.getContrasenia());
        String query="SELECT idtaller, nombre, clave, instructor, periodo "
                + "FROM talleres "
                + "WHERE estatus='"+estatus+"';";
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        //Guardar datos
        //Se almacenaran aqui
        ArrayList<Taller> talleres = new ArrayList<Taller>();
        Taller taller=null;//Taller temporal
          while (rs.next()) {
              //Instanciar un nuevo taller
              taller=new Taller();
              //Agregarle los valores
              taller.setIdTaller(rs.getInt(1));
              taller.setNombre(rs.getString(2).trim());
              taller.setClave(rs.getString(3).trim());
              taller.setInstructor(rs.getString(4).trim());
              taller.setPeriodo(rs.getString(5).trim());
              
              //Agregar el taller al Array
              talleres.add(taller);
          }
          request.setAttribute("LISTA_"+estatus, talleres.toArray());
          talleres.clear();
          //Cerrar conexion
          conexion.close();
          rs.close();
          st.close();
    }
}
