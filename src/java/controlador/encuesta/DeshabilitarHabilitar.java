/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.encuesta;

import controlador.Conexion_bd;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Respuesta;

/**
 *
 * @author hbdye
 */
public class DeshabilitarHabilitar extends HttpServlet {
    
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Declaración de las variables donde se guardara la información
        int idEncuesta = Integer.parseInt((String) request.getParameter("idEncuesta"));
        //Inicializar cosas necesarias pata la conexion a BD
        try {
            //Conexion a la BD
            Class.forName("org.postgresql.Driver");
            //Direccion, puerto, nombre de BD, usuario y contraseña
            
            String habilitado=getEstado(idEncuesta);
            
            deshabilitarHabilitar(habilitado, idEncuesta);
            
            request.getRequestDispatcher("/AdministrarEncuesta").forward(request, response);
            
        } catch (ClassNotFoundException | SQLException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al modificar la base de datos:\n" + ex);
            request.setAttribute("MENSAJEBOTON", "Volver");
            request.setAttribute("DIRECCIONBOTON", "AdministrarEncuesta");
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            
        } 
    }
    
    private void deshabilitarHabilitar(String habilitado, int idEncuestas) throws SQLException
    {
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());
        //QUERY DE actualizacion
            String query = "UPDATE encuestas ";
            if(habilitado.compareTo("s")==0)
            {
                query+="SET habilitada='n' ";
            }
            else
            {
                query+="SET habilitada='s' ";
            }
            query+="WHERE id_encuestas="+idEncuestas;
            //Ejecutar el Query
            Statement st = conexion.createStatement();
            st.executeUpdate(query);
    }
    
    private String getEstado(int idEncuesta) throws SQLException {
        //Obtener la cantidad de preguntas que tiene una encuesta
        String query = "SELECT habilitada "
                + "FROM encuestas "
                + "WHERE id_encuestas=" + idEncuesta;

        //Variable temporal donde se guardara el valor 
        String temp ="";
        //Direccion, puerto, nombre de BD, usuario y contraseña
          Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
       Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

        //Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");

        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Obtener los valores
        while (rs.next()) {
            //Obtener los valores
            temp = rs.getString(1).trim();
        }
        
        return temp;
    }

}
