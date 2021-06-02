/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
public class PanelDeAdmin extends HttpServlet {

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
                String [][] arreglo= new String[c][2];

                //Obtener la informacion de las encuestas, y almacenarla en el arreglo
                getEncuestas(arreglo,"select id_encuestas, nombre from encuestas where clave='';");
                for(int temp=0; temp<arreglo.length; temp++)
                {
          //         out.println(arreglo[temp][0]+"  "+arreglo[temp][1]+"<br>"); 
                }
            //Obtener TODAS las encuestas
                int c2=getNumEncuestas("select count(*) from encuestas;");

                //Arreglo donde se guardara la info
                String [][] arreglo2= new String[c2][2];

                //Obtener la informacion de las encuestas, y almacenarla en el arreglo
                getEncuestas(arreglo2,"select id_encuestas, nombre from encuestas;");
                for(int temp=0; temp<arreglo2.length; temp++)
                {
            //       out.println(arreglo2[temp][0]+"  "+arreglo2[temp][1]+"<br>"); 
                }
            
            //Enviar los arreglos al JSP de PanelDeAdmin
            request.setAttribute("NUM_ENCUESTAS_ALUMNOS", c);//Enviar el numero de encuestas de alumnos
            request.setAttribute("ENCUESTAS_ALUMNOS", arreglo);
            request.setAttribute("NUM_ENCUESTAS_TODOS", c2);//Enviar el numero de encuestas totales
            request.setAttribute("ENCUESTAS_TODOS", arreglo2);
            request.getRequestDispatcher("PanelDeAdmin.jsp").forward(request, response);
            
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
               arreglo[temp][0]=rs.getString(1);
               arreglo[temp][1]=rs.getString(2).trim();
               
                         
               //Aumentar contador temp
               temp++;
           }
            
            return arreglo;
    }

}
