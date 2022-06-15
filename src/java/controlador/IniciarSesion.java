/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hbdye
 */
public class IniciarSesion extends HttpServlet {
    //PrintWriter out;
     public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         //out = response.getWriter();
         //ConvertirUTF8 convert=new ConvertirUTF8();
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //PrintWriter out = response.getWriter();
        //Verificar que no haya una sesion activa
        HttpSession session=request.getSession(true);
        String tipo=(String)session.getAttribute("TIPO");
        //out.print("<br>"+"iniico");
        if(tipo==null)//Si no hay una sesion activa
        {
            tipo = (String) request.getParameter("tipo");//Obtener el tipo de sesión desde el JSP
            //out.print(tipo);
            //Strings temporales
            String st1="", st2="", query="";
            switch(tipo)//Dependiendo del tipo, realizar accion
            {
                case "1"://Egresado
                    //Obtener datos
                    st1 =(String) request.getParameter("matricula");//Obtener la matricula
                    //st1=convert.convertToUTF8(st1);//Covertir a UTF-8
                    query="SELECT num_control, nombre, app, apm, status "
                            + "FROM alumnos "
                            + "WHERE num_control=? or curp=?";
                    break;
                case "2"://Administrador
                    //Obtener datos
                    st1 =(String) request.getParameter("usuario");//Obtener el usuario
                    st2 =(String) request.getParameter("password");//obtener contraseña
                    //st1=convert.convertToUTF8(st1);//Covertir a UTF-8
                    //st2=convert.convertToUTF8(st2);//Covertir a UTF-8
                    query="SELECT nombre, rol "
                            + "FROM administradores "
                            + "WHERE nombre=? and contrasenia=md5(?)";
                    //out.print(query);
                    break;
                case "3"://Empleador
                    //Obtener datos
                    st1=(String) request.getParameter("clave");
                    //st1=convert.convertToUTF8(st1);//Covertir a UTF-8
                    
                    query="SELECT id_encuestas "
                            + "FROM encuestas "
                            + "WHERE clave=? and habilitada='s'";
                    
                    break;
            }
             try {
                 consulta(query, st1, st2, tipo, request, response);
            } catch (ClassNotFoundException |SQLException ex) {
                //out.print(ex);
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "Error al iniciar sesión, intente nuevamente<br>" + ex);
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "index.jsp");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }
        }
        else//Si si existe una sesión activa
        {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
        
        
        
     }
     private void consulta(String query, String st1, String st2, String tipo, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, ServletException
     {
        Class.forName("org.postgresql.Driver");
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
        PreparedStatement stmt = null;
        ResultSet rs = null;
        stmt = conexion.prepareStatement(query);
        //out.print("<br>"+"Consuta");
        //out.print("<br>"+query);
        switch(tipo)
        {
            case "1"://Egresado
                    //Agregar datos al query
                    if(st1.length()>8)
                    {
                        stmt.setInt(1,-1);//Matricula
                        stmt.setString(2,st1);//o curp
                    }
                    else
                    {
                        stmt.setInt(1,Integer.parseInt(st1));//Matricula
                        stmt.setString(2,"");//o curp
                    }
                    //out.print("<br>"+"Agregado");
                    break;
                case "2"://Administrador
                    stmt.setString(1,st1);//Usuario
                    stmt.setString(2,st2);//Contraseña
                    break;
                case "3"://Empleador
                    //Agregar datos al query
                    stmt.setString(1,st1);//Clave de la encuesta
                    break;
        }
        rs=stmt.executeQuery();//Ejecutar la consulta y guardar datos en rs
        //out.print("<br>"+"Ejecutado");
         while (rs.next())
         {
            HttpSession session=request.getSession(true);
            //out.print("<br>"+"Encontrado");
            
             switch(tipo)
            {
                case "1"://ALumno
                    //Obtener los valores
                    st1 = rs.getString(1);//Matricula
                    //Obtener y concatenar nombre en forma App Apm Nombre
                    //Agregar atributos de sesión
                    st2= rs.getString(3).trim()+" "+rs.getString(4)+" "+rs.getString(2).trim();
                    session.setAttribute("TIPO",String.valueOf(tipo));
                    session.setAttribute("MATRICULA",st1);
                    session.setAttribute("NOMBRE",st2);
                    session.setAttribute("ESTATUS",rs.getString(5).trim());
                    //out.print("<br>"+rs.getString(5).trim());
                    //Redireccionar al index
                    //response.sendRedirect(request.getContextPath() + "/index.jsp");
                        break;
                case "2"://Administrador
                    //Obtener los valores
                    st1 = rs.getString(1).trim();//Nombre o usuario
                    //Obtener y concatenar nombre en forma App Apm Nombre
                    st2= rs.getString(2).trim();//Rol de administrador
                    //Agregar atributos de sesión
                    session.setAttribute("TIPO",String.valueOf(tipo));
                    session.setAttribute("USUARIO",st1);
                    session.setAttribute("ROL",st2);
                    //Redireccionar al index
                    //response.sendRedirect(request.getContextPath() + "/index.jsp");
                        break;
                case "3"://Empleador
                    //Obtener los valores
                    st1 = String.valueOf(rs.getInt(1));//id_encuestas
                    //Agregar atributos de sesión
                    //Probablemente solo redireccionar a responder la encuesta unu
                    session.setAttribute("TIPO",String.valueOf(tipo));
                    session.setAttribute("ID_ENCUESTA",st1);
                    //out.print("<br>Redicreccionar");
                    //out.print("<br>"+st1);
                    /*request.setAttribute("modificarEncuesta", st1);
                    request.getRequestDispatcher("CargarPreguntasRespuestas").forward(request, response);*/
                    //response.sendRedirect(request.getContextPath() + "/Alumno/agregar.jsp");
                        break;
            }
         }
         //Cerrar conexion
          conexion.close();
          rs.close();
          stmt.close();
         
         //Si no encuentra nada
         response.sendRedirect(request.getContextPath() + "/index.jsp");
         
     }
     
}