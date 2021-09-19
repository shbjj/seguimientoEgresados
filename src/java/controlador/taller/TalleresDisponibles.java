/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.taller;

import controlador.Conexion_bd;
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class TalleresDisponibles extends HttpServlet {

    //PrintWriter out;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //out = response.getWriter();
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            //out.print("<br>Se inicio sesión");
            if (tipoS.compareTo("1") == 0) //Inicia sesión un alumno
            {
                //out.print("<br>Alumno");
                String estatus = (String) session.getAttribute("ESTATUS");//Obtener el tipo de alumno, egresado o inscrito
                String matricula = (String) session.getAttribute("MATRICULA");//Obtener el tipo de alumno, egresado o inscrito
                
                if (estatus.equalsIgnoreCase("inscrito"))//Si alumno esta inscrito
                {
                    //out.print("<br>Inscrito");
                    try {
                        Class.forName("org.postgresql.Driver");

                        if(aunPuedeIscribirse(matricula))//Si el alumno aun puede inscribirse a mas talleres
                        {
                            //obtener los talleres disponibles para poder inscribirse
                            obtenerTalleres(request, response, matricula);
                            //out.print("<br>Salio");

                            //Mandar al JSP
                            request.getRequestDispatcher("/Taller/disponibles.jsp").forward(request, response);
                        }
                        else
                        {
                            error("Ya alcanzaste él limite de talleres inscritos", request, response);
                        }
                        
                    } catch (ClassNotFoundException | SQLException ex) {
                        error(ex.toString(), request, response);
                    }
                }
                else
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

    void obtenerTalleres(HttpServletRequest request, HttpServletResponse response, String matricula) throws SQLException{
        //out.print("<br>Entro metodo");
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());
        //Query de consulta, de los talleres disponibles y que no se estan CURSANDO
        String query = "SELECT idtaller, nombre, descripcion, ubicacion, instructor, fechaini, fechafin, cupo, inscritos  "
                + "FROM talleres "
                + "WHERE estatus='Abierto' and fechaini>=? and cupo>inscritos and idtaller not in("
                                                                    + "select idtaller " +
                                                                        "from boletas " +
                                                                        "where num_control="+matricula+" and estatus='Cursando');";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        stmt = conexion.prepareStatement(query);
        //obtener fecha actual
        //out.print("<br>Obtener fecha");
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        //out.print("<br>Fecha generada"+sqlDate);
        stmt.setDate(1, sqlDate);
        //out.print("<br>Fecha guardada");
        //Ejecutar query
        rs = stmt.executeQuery();
        //out.print("<br>Query ejecutado");
        //Obtener los datos del taller
        ArrayList<Taller> talleres = new ArrayList<Taller>();

        Taller taller = null;//Taller temporal

        while (rs.next()) {
            taller = new Taller();
            taller.setIdTaller(rs.getInt(1));
            taller.setNombre(rs.getString(2).trim());
            taller.setDescripcion(rs.getString(3).trim());
            taller.setUbicacion(rs.getString(4).trim());
            taller.setInstructor(rs.getString(5).trim());
            taller.setFechaIni(rs.getString(6));
            taller.setFechaFin(rs.getString(7));
            taller.setCupo(rs.getInt(8));
            taller.setInscritos(rs.getInt(9));
            
            //Obtener los dias del taller
            taller.dias=obtenerDias(taller.getIdTaller());
            //Agregar el taller al Array
            talleres.add(taller);
            //out.print("<br>Se encontro taller");
        }
        //Cerrar rs y stmt
        rs.close();
        stmt.close();
        //Declarar un arreglo de talleres con el tamaño del ArrayList
        Taller[] arregloTaller = new Taller[talleres.size()];
        //igualar el arreglo a el valor retornado del metodo .toArray enviando como parametro el arreglo.
        arregloTaller = talleres.toArray(arregloTaller);
        
        request.setAttribute("TALLERES", arregloTaller);
    }
    
    Dia[] obtenerDias(int idTaller) throws SQLException
    {
        //out.print("<br>Entro metodo de obtener dias");
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());
        //Query de consulta
        String query = "SELECT iddia, horaini, horafin "
                + "FROM dias "
                + "WHERE idtaller=?;";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        stmt = conexion.prepareStatement(query);
        //obtener fecha actual
        
        stmt.setInt(1, idTaller);
        //Ejecutar query
        rs = stmt.executeQuery();
        //Obtener los datos del dia
        ArrayList<Dia> dias = new ArrayList<Dia>();

        Dia dia = null;//Dia temporal

        while (rs.next()) {
            //Agregarle valores al objeto temporal de tipo dia
            dia = new Dia(rs.getInt(1));
            dia.setHoraIni_S(rs.getString(2).trim());
            dia.setHoraFin_S(rs.getString(3).trim());
            
            //Agregar el dia al Array
            dias.add(dia);
            
            //out.print("<br>Se encontro dia: "+dia.getdDia());
        }
        
        //Crear un arreglo temporal
        Dia[] temp= new Dia[dias.size()];
        temp=dias.toArray(temp);
        //Cerrar rs y stmt
        rs.close();
        stmt.close();
        conexion.close();
        
        return temp;
    }
    boolean aunPuedeIscribirse(String matricula) throws SQLException
    {
         //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());
        //Query de consulta
        String query = "SELECT count(*)<3 "
                + "FROM boletas "
                + "WHERE num_control=? and estatus='Cursando';";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        stmt = conexion.prepareStatement(query);
        //obtener fecha actual
        
        stmt.setInt(1, Integer.parseInt(matricula));
        //Ejecutar query
        rs = stmt.executeQuery();
        //Obtener el dato
       
        if (rs.next()) {
            return rs.getBoolean(1);
        }
        else
        {
            return false;
        }
        
    }
}
