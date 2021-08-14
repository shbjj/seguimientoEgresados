/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.alumno;

import controlador.Conexion_bd;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Alumno;

/**
 *
 * @author hbdye
 */
public class AdministrarAlumno extends HttpServlet {

    Alumno [] alumnos;
    Alumno [] egresados;
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Cargar el druver en la clase
            Class.forName("org.postgresql.Driver");
            
            //Obtener los alumnos 
                int c=getNumAlumnos("select count(*) from alumnos where status='INSCRITO';");

                //Arreglo donde se guardara la info
                alumnos= new Alumno[c];

                //Obtener la informacion de las encuestas para egresados (no tienen clave), y almacenarla en el arreglo
                getAlumnos(alumnos,"select num_control, nombre, app, apm, semestre, grupo "
                        + "from alumnos "
                        + "where status='INSCRITO' "
                        + "order by app;");
                
            //Obtener TODAS las encuestas
                int c2=getNumAlumnos("select count(*) from alumnos where status !='INSCRITO';");

                //Arreglo donde se guardara la info
                egresados= new Alumno[c2];

                //Obtener la informacion de las encuestas para empleadores (tienen clave), y almacenarla en el arreglo
                getAlumnos(egresados,"select num_control, nombre, app, apm, semestre, grupo "
                        + "from alumnos "
                        + "where status!='INSCRITO' "
                        + "order by app;");
                
            //Obtener la fecha en la que se hizo el ultimo avance de semestre
            String fechaAvance=getFechaAvanceSemestre();
            request.setAttribute("FECHA_AVANCE_SEMESTRE", fechaAvance);//Enviar la fecha
            
            //Enviar los arreglos al JSP de PanelDeAdmin
            
            request.setAttribute("ALUMNOS", alumnos);
            request.setAttribute("EGRESADOS", egresados);
            request.getRequestDispatcher("Alumno/administrar.jsp").forward(request, response);
            
        } catch (ClassNotFoundException | SQLException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al obtener información de la base de datos:\n" + ex);
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        } 

        
        
    }

    private int getNumAlumnos(String query) throws SQLException {//Este metodo retornara el numero de encuestas, se debe de recibir el query
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
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
    
    private Alumno[] getAlumnos(Alumno[] alumn, String query) throws SQLException
    {//Este metodo retornara el id_encuesta y el nombre, se debe de recibir el arreglo donde se guardara la
        //informacion y el query
            //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
       Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
       
            //Ejecutar el Query
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            int temp=0;
            //Saber cuantos resultados hay
            String tempString="";
           while(rs.next())
           {
               //Obtener los valores
               alumn[temp]= new Alumno();
               alumn[temp].setMatricula(rs.getString(1).trim());
               alumn[temp].setNombre(rs.getString(2).trim());
               alumn[temp].setApp(rs.getString(3).trim());
               tempString=rs.getString(4);
               if(tempString==null)//Si el valor es nulo
               {
                   tempString="";
               }
               alumn[temp].setApm(tempString.trim());//Puede ser nulo, por eso no se ocupa trim
               alumn[temp].setSemestre(rs.getString(5).trim());
               alumn[temp].setGrupo(rs.getString(6).trim());
                       
               //Aumentar contador temp
               temp++;
           }
           //Cerrar conexion
            conexion.close();
            rs.close();
            st.close(); 
           
            return alumn;
    }
    
    private String getFechaAvanceSemestre() throws SQLException
    {   //Obtener la fecha en la que se selecciono por ultima vez la opcion de avanzar semestre
        String query="SELECT fecha "
                + "from avance_semestre "
                + "where fecha="
                + "(select max(fecha) from avance_semestre)";
         //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(), 
                datos_conexion.getContrasenia());
        Statement stmt=null;
        ResultSet rs=null;
        //Agregar la informacion de conexion al stmt
        stmt=conexion.createStatement();
        //Ejecutar el query
        rs=stmt.executeQuery(query);
        //Editar la variable Query, para poner ahi la fecha
        query="NUNCA";
        while(rs.next())
        {
            query=rs.getString(1).trim();
        }
        conexion.close();
        stmt.close();
        rs.close();
        return query;
      
    }
}
