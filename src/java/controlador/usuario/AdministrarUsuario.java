/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.usuario;

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
import javax.servlet.http.HttpSession;
//import modelo.Alumno;
import modelo.Usuario;

/**
 *
 * @author hbdye
 */
public class AdministrarUsuario extends HttpServlet {

    Usuario[] usuarios;

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) //Inicia sesión un admin
            {
                int rol = Integer.parseInt((String) session.getAttribute("ROL"));//Obtener el rol del usuario
                if (rol == 0 || rol == 1)//Si es SuperAdministrador o Administrador, entonces puede administrar usuarios 
                {

                    try {
                        //Cargar el druver en la clase
                        Class.forName("org.postgresql.Driver");

                        //Obtener numero de los administradores, menos el admin root 
                        int c = getNumUsuarios("select count(*) from administradores where rol!='0';");

                        //Arreglo donde se guardara la info
                        usuarios = new Usuario[c];

                        //Obtener la informacion de los usuarios y almacenarla en el arreglo
                        getUsuarios("select nombre, rol "
                                + "from administradores "
                                + "where rol!='0' "
                                + "order by rol;");
                        //Enviar los arreglos al JSP de PanelDeAdmin

                        request.setAttribute("USUARIOS", usuarios);
                        request.getRequestDispatcher("Usuario/administrar.jsp").forward(request, response);

                    } catch (ClassNotFoundException | SQLException ex) {
                        request.setAttribute("NOMBRE_MENSAJE", "Error");
                        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                        request.setAttribute("DESCRIPCION", "Error al obtener información de la base de datos:\n" + ex);
                        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                    }

                } else//Si no, no tiene permiso
                {
                    request.setAttribute("NOMBRE_MENSAJE", "Error");
                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                    request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                    request.setAttribute("MENSAJEBOTON", "Volver");
                    request.setAttribute("DIRECCIONBOTON", "index.jsp");
                    request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
                }

            } else//Iniicio sesión otra persona
            {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "index.jsp");
                request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
            }
        } else//no hay una sesión iniciada
        {
            //Redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    private int getNumUsuarios(String query) throws SQLException {//Este metodo retornara el numero de encuestas, se debe de recibir el query
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

        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
        return c;
    }

    private void getUsuarios(String query) throws SQLException {//Este metodo retornara el id_encuesta y el nombre, se debe de recibir el arreglo donde se guardara la
        //informacion y el query
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());

        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        int temp = 0;
        while (rs.next()) {
            //Obtener los valores
            usuarios[temp] = new Usuario();
            usuarios[temp].setNombre(rs.getString(1).trim());
            usuarios[temp].setRol(rs.getString(2).trim());

            //Aumentar contador temp
            temp++;
        }
        
        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
    }
}
