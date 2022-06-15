package controlador.taller;

import controlador.Conexion_bd;
import java.io.IOException;
import java.io.PrintWriter;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hbdye
 */
public class SalirTaller extends HttpServlet {

    PrintWriter out;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        out = response.getWriter();
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            out.print("<br>Se inicio sesión");
            if (tipoS.compareTo("1") == 0) //Inicia sesión un alumno
            {
                out.print("<br>Alumno");
                String estatus = (String) session.getAttribute("ESTATUS");//Obtener el tipo de alumno, egresado o inscrito
                if (estatus.equalsIgnoreCase("inscrito"))//Si alumno esta inscrito
                {
                    //Obtener el ID del taller al que se va a inscribir
                    String idTaller = (String) request.getParameter("id");
                    if (idTaller == null)//Si no se recibio un valor valido, mandar error
                    {
                        error("No se recibieron valores válidos.", request, response);
                    }
                    //Obtener la matricula del alumno desde la sesion
                    String matricula = (String) session.getAttribute("MATRICULA");

                    out.println("MAtricula: " + matricula + " idTaller :" + idTaller);
                    try {
                        Class.forName("org.postgresql.Driver");

                        if(borrar(matricula, idTaller))
                        {
                            response.sendRedirect(request.getContextPath() + "/IndexAlumno");
                        }
                        else
                        {
                            error("No se realizo la acción", request, response);
                        }

                        //Mandar al JSP
                        out.print("<br>Sin erroor");
                        //request.getRequestDispatcher("/Taller/disponibles.jsp").forward(request, response);
                    } catch (ClassNotFoundException ex) {
                        //error(ex.toString(), request, response);
                        out.print(ex.toString());
                    }

                } else {
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
        request.setAttribute("DIRECCIONBOTON", "TalleresDisponibles");
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }

    boolean borrar(String matricula, String idTaller) {
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = null;
        PreparedStatement st = null;
        boolean seBorro=false;
        try {
            conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());
            //Definir que se ocuparan transacciones
            conexion.setAutoCommit(false);

            //Borrar la boleta
            String query = "DELETE FROM boletas "
                    + "WHERE num_control=? and idtaller=?";
            //Ejecutar el Query
            //Declarar el objeto PreparedStatement
            //Asignarle la conexion y el query
            st = conexion.prepareStatement(query);
            //Asignarle los parametros
            st.setInt(1, Integer.parseInt(matricula));
            st.setInt(2, Integer.parseInt(idTaller));
            //ejecutar
            st.executeUpdate();
            //Cerrar el St
            st.close();
            st = null;
            //Cambiar el numero de inscritos en el taller
            query = "UPDATE talleres "
                    + "SET inscritos=(inscritos-1) "
                    + "WHERE idtaller=?;";
            //Asignarle la conexion y el query
            st = conexion.prepareStatement(query);
            //Agregarle los valores al query
            st.setInt(1, Integer.parseInt(idTaller));
            //ejecutar
            st.executeUpdate();

            //Hacer commit
            conexion.commit();

            st.close();
            conexion.close();
            seBorro=true;
        } catch (SQLException ex) {
            try {
                st.close();
                //Si hay falla, hacer rollback
                conexion.rollback();
                conexion.close();
                seBorro=false;
            } catch (SQLException ex1) {
                Logger.getLogger(SalirTaller.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        return seBorro;
    }
}
