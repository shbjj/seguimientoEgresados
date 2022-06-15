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
import modelo.Alumno;
import modelo.Dia;
import modelo.Taller;

/**
 *
 * @author hbdye
 */
public class CalificarTaller extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //Para la salida de mendajes en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        //response.setContentType("text/html");
        //out = response.getWriter();
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                if (tipoS.compareTo("2") == 0)//Si es un administrador, revisar que tenga permiso
                {
                    int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                    if (rol == 2)//Si es de seguimiento de egresados, no tiene permiso de ver la info del taller 
                    {
                        error("No tiene permiso para acceder a este contenido", request, response);
                    } else//Si no, si tiene permiso
                    {
                        try {
                            //Cargar el driver en la clase
                            Class.forName("org.postgresql.Driver");
                            String idTemp = (String) request.getParameter("idTaller");
                            if (idTemp == null)//Si no se recibio ningun valor, mandar error
                            {
                                error("No se recibio un valor válido.", request, response);
                            } else//Si si se recibio un valor valido, buscar la informacion del taller
                            {
                                //Convertir el id en entero
                                int idEncuesta = Integer.parseInt(idTemp);
                                //Obtener la informacion del taller
                                buscar(idEncuesta, request, response);

                                //Enviar al JSP
                                request.getRequestDispatcher("/Taller/calificar.jsp").forward(request, response);
                            }

                        } catch (ClassNotFoundException | SQLException ex) {
                            error(ex.toString(), request, response);
                        }
                    }
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
     void buscar(int idTaller, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());
        
        
        String query = "select b.idtaller, a.num_control, a.nombre, a.app, a.apm " +
                        "from boletas b, alumnos a " +
                        "where b.num_control=a.num_control and b.idtaller=" +idTaller+
                        " order by a.app;;";
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Guardar datos
        ArrayList<Alumno> alumnos = new ArrayList<Alumno> ();
        Alumno alumno = null;//Taller temporal
        while (rs.next()) {
            //Instanciar un nuevo taller
            alumno = new Alumno();
            //Agregarle los valores
            alumno.setMatricula(rs.getInt(2)+"");
            alumno.setNombre(rs.getString(3).trim());
            alumno.setApp(rs.getString(4).trim());
            if(rs.getString(5)!= null && rs.getString(5)!="")
            {
                alumno.setApm(rs.getString(5).trim());
            }
            alumnos.add(alumno);
        }
        //Cerrar objetos
        rs.close();
        rs = null;
        st.close();
        st = null;
        //Enviar el taller como parametro
        request.setAttribute("alumnos", alumnos);
        //alumnos.clear();
        //Obtener los datos del taller
        query = "SELECT * "
                + "FROM talleres "
                + "WHERE idtaller=" + idTaller + ";";
        //Ejecutar el Query
        st = conexion.createStatement();
        rs = st.executeQuery(query);

        Taller taller= new Taller();
        while(rs.next())
        {
            taller.setIdTaller(rs.getInt(1));
              taller.setNombre(rs.getString(2).trim());
              taller.setDescripcion(rs.getString(3).trim());
              taller.setUbicacion(rs.getString(4).trim());
              taller.setClave(rs.getString(5).trim());
              taller.setInstructor(rs.getString(6).trim());
              taller.setPeriodo(rs.getString(7).trim());
              taller.setFechaIni(rs.getString(8));
              taller.setFechaFin(rs.getString(9));
              taller.setCupo(rs.getInt(10));
              taller.setEstatus(rs.getString(11).trim());
              taller.setInscritos(rs.getInt(12));
        }
        //Enviar el taller como parametro
        request.setAttribute("taller", taller);
        //Cerrar conexion
        conexion.close();
        rs.close();
        st.close();
        

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

}
