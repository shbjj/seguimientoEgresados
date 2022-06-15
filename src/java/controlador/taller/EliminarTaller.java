package controlador.taller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controlador.Conexion_bd;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hbdye
 */
public class EliminarTaller extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rol != 2)//Si es SuperAdministrador, Administrador o tallerista,
                //entonces puede eliminar talleres 
                {
                    String idTaller = (String) request.getParameter("idTaller");
                    if(idTaller!=null)//Si si hay un valor recibido
                    {
                        try {
                            Class.forName("org.postgresql.Driver");
                            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                            Connection conexion = DriverManager.getConnection(
                                    datos_conexion.getDireccion(), 
                                    datos_conexion.getUsuario(), 
                                    datos_conexion.getContrasenia());
                            Statement inst = conexion.createStatement(); //Crea la sentencia o instruccion sobre la que se ejecutara el query
                            String query = "DELETE FROM talleres where idtaller =" + idTaller;//Declarando el query que se va ejecutar para la consulta
                            inst.executeUpdate(query);
                            //Cerrar conexion
                            conexion.close();
                            inst.close();
                            response.sendRedirect(request.getContextPath() + "/AdministrarTaller");
                        } catch (ClassNotFoundException | SQLException ex) {
                            error(ex.toString(), request, response);
                        }
                        
                    }
                    else
                    {
                        error("No se recibio ningún valor.", request, response);
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
}
