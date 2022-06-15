/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.alumno;

import controlador.Conexion_bd;
import java.io.IOException;
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
import modelo.Encuesta;
import modelo.Taller;

/**
 *
 * @author hbdye
 */
public class IndexAlumno extends HttpServlet {
    
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession(true);
        String tipo=(String)session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo
        
        if(tipo!=null)//Si se inicio sesión
        {
            if(tipo.compareTo("1")==0)//Si se inicio sesión de un alumno
            {
                String matricula=(String)session.getAttribute("MATRICULA");//Obtener el numero de matricula del alumno
                try {
                    //Obtener datos para mostrar en el panel del alumno
                    Class.forName("org.postgresql.Driver");
                    //Obtener el numero de las encuestas disponibles no contestadas
                    String query="select count(*) " +
                                                                    "from encuestas as e " +
                                                                    "where not exists " +
                                                                    "( " +
                                                                    "select * " +
                                                                    "from respuestas as r " +
                                                                    "where e.id_encuestas=r.id_encuestas and num_control=" +matricula+
                                                                    ") and habilitada='s' and clave=''";
                    
                    int temp=getNumEncuestas(query);
                    if(temp>0)//Si si hay encuestas
                    {  //Obtener las encuestas disponibles no contestadas y enviarlas como parametro
                        Encuesta[] encuestasNoContestadas=getEncuestas(temp,"select e.id_encuestas, e.nombre, e.fecha " +
                                                                "from encuestas as e " +
                                                                "where not exists " +
                                                                "(" +
                                                                "select * " +
                                                                "from respuestas as r " +
                                                                "where e.id_encuestas=r.id_encuestas and num_control="+matricula+
                                                                ") and habilitada='s' and clave=''");
                        request.setAttribute("ENCUESTASNOCONTESTADAS", encuestasNoContestadas);
                    }
                    
                     //Obtener el numero de las encuestas contestadas
                     query="select count(*) " +
                                        "from encuestas as e " +
                                        "where exists " +
                                        "(" +
                                        "select * " +
                                        "from respuestas as r " +
                                        "where e.id_encuestas=r.id_encuestas and num_control="+matricula+
                                        ") and habilitada='s' and clave=''";
                    temp=getNumEncuestas(query); 
                    if(temp>0)//Si si hay encuestas
                    {  //Obtener las encuestas disponibles no contestadas y enviarlas como parametro
                        Encuesta[] encuestasContestadas=getEncuestas(temp,"select e.id_encuestas, e.nombre, e.fecha " +
                                                                        "from encuestas as e " +
                                                                        "where exists " +
                                                                        "( " +
                                                                        "select * " +
                                                                        "from respuestas as r  " +
                                                                        "where e.id_encuestas=r.id_encuestas and num_control="+matricula+
                                                                        ") and habilitada='s' and clave=''");
                        request.setAttribute("ENCUESTASCONTESTADAS", encuestasContestadas);
                        
                    }
                    
                    query="select t.idtaller, t.nombre, t.instructor, t.ubicacion " +
                            "from talleres t, boletas b " +
                            "where t.idtaller=b.idtaller and b.num_control="+matricula;
                    request.setAttribute("TALLERESCURSANDO", getTalleres(query));
                    //Redireccionar al panel del alumno
                    request.getRequestDispatcher("Alumno/panel.jsp").forward(request, response);
                    
                    
                
                
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(IndexAlumno.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            else//Si se inicio sesion pero es diferente de alumno
            {
                //Redirigir al login
                request.setAttribute("NOMBRE_MENSAJE", "Error");
	                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        	        request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                	request.setAttribute("MENSAJEBOTON", "Volver");
                	request.setAttribute("DIRECCIONBOTON", "index.jsp");
                	request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }
        }
        else//Si no se ha iniciado sesion
        {
            //Redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
    private ArrayList<Taller> getTalleres(String query) throws SQLException
    {//Este metodo retornara el numero de encuestas, se debe de recibir el query
            //Direccion, puerto, nombre de BD, usuario y contraseña
            Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
        
            //Ejecutar el Query
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            ArrayList<Taller> t= new ArrayList();
            Taller temp=null;
            while(rs.next())
            {
                temp= new Taller();
                temp.setIdTaller(rs.getInt(1));
                temp.setNombre(rs.getString(2));
                temp.setInstructor(rs.getString(3));
                temp.setUbicacion(rs.getString(4));
                
                t.add(temp);
            }
            //Cerrar conexion
            conexion.close();
            rs.close();
            st.close();
            return t;
    }
    private int getNumEncuestas(String query) throws SQLException
    {//Este metodo retornara el numero de encuestas, se debe de recibir el query
            //Direccion, puerto, nombre de BD, usuario y contraseña
            Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
        
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
    private Encuesta[] getEncuestas(int cantEncuestas, String query) throws SQLException
    {//Este metodo retornara el id_encuesta y el nombre, se debe de recibir el arreglo donde se guardara la
        //informacion 
            //Direccion, puerto, nombre de BD, usuario y contraseña
            Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
            
            //Arreglo de encuestas
            Encuesta [] encTemp= new Encuesta[cantEncuestas];
            //Ejecutar el Query
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            int temp=0;
            //Saber cuantos resultados hay
           while(rs.next())
           {
               encTemp[temp]= new Encuesta();
               //Obtener los valores e.id_encuestas, e.nombre, e.fecha
               encTemp[temp].setId_encuestas(String.valueOf(rs.getInt(1)));//ID
               encTemp[temp].setNombre(rs.getString(2).trim());//nombre
               encTemp[temp].setFecha(rs.getString(3).trim());//Fecha
               //Aumentar contador temp
               temp++;
           }
           //Cerrar conexion
            conexion.close();
            rs.close();
            st.close();
           return encTemp;//Retornar el arreglo
    }
    
}