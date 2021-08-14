/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.encuesta;

import controlador.Conexion_bd;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hbdye
 */
public class AdministrarEncuesta extends HttpServlet {
public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Para la salida de mendajes en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        //response.setContentType("text/html");
        //PrintWriter out = response.getWriter();

        try {
            //Cargar el druver en la clase
            Class.forName("org.postgresql.Driver");
            
            //Obtener las encuestas enfocadas a los alumnos
                int c=getNumEncuestas("select count(*) from encuestas where clave='';");

                //Arreglo donde se guardara la info
                String [][] arreglo= new String[c][4];

                //Obtener la informacion de las encuestas para egresados (no tienen clave), y almacenarla en el arreglo
                getEncuestas(arreglo,"select id_encuestas, nombre, fecha, habilitada "
                        + "from encuestas "
                        + "where clave='' "
                        + "order by habilitada desc;");
                
            //Obtener TODAS las encuestas
                int c2=getNumEncuestas("select count(*) from encuestas where clave !='';");

                //Arreglo donde se guardara la info
                String [][] arreglo2= new String[c2][4];

                //Obtener la informacion de las encuestas para empleadores (tienen clave), y almacenarla en el arreglo
                getEncuestas(arreglo2,"select id_encuestas, nombre, fecha, habilitada "
                        + "from encuestas "
                        + "where clave!='' "
                        + "order by habilitada desc;");
            //Enviar los arreglos al JSP de PanelDeAdmin
            //request.setAttribute("NUM_ENCUESTAS_EGRESADOS", c);//Enviar el numero de encuestas de alumnos
            request.setAttribute("ENCUESTAS_EGRESADOS", arreglo);
            //request.setAttribute("NUM_ENCUESTAS_EMPLEADORES", c2);//Enviar el numero de encuestas totales
            request.setAttribute("ENCUESTAS_EMPLEADORES", arreglo2);
            request.getRequestDispatcher("Encuesta/administrar.jsp").forward(request, response);
            
        } catch (ClassNotFoundException | SQLException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al obtener información de la base de datos:\n" + ex);
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        } 

    }
    private int getNumEncuestas(String query) throws SQLException
    {//Este metodo retornara el numero de encuestas, se debe de recibir el query
            //Direccion, puerto, nombre de BD, usuario y contraseña
          Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
       Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
        //Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");
        
            //Ejecutar el Query
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            //Saber cuantos resultados hay
            rs.next();
            int c = rs.getInt(1);
            //Cerrar conexion
            conexion.close();
            rs.close();
            st.close();
            return c;
    }
    private String[][] getEncuestas( String [][] arreglo, String query) throws SQLException
    {//Este metodo retornara el id_encuesta y el nombre, se debe de recibir el arreglo donde se guardara la
        //informacion y el query
            //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
       Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
        //Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");

            //Ejecutar el Query
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            int temp=0;
            //Saber cuantos resultados hay
           while(rs.next())
           {
               //Obtener los valores
               arreglo[temp][0]=rs.getString(1);//ID
               arreglo[temp][1]=rs.getString(2).trim();//NOMBRE
               arreglo[temp][2]=rs.getString(3).trim();//FECHA
               arreglo[temp][3]=rs.getString(4).trim();//HABILITADA
               //Aumentar contador temp
               temp++;
           }
            //Cerrar conexion
            conexion.close();
            rs.close();
            st.close();
            return arreglo;
    }
}
