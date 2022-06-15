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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Alumno;

/**
 *
 * @author hbdye
 */
public class VerAlumno extends HttpServlet {
 
        public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String matricula = (String) request.getParameter("matricula");
        if(matricula!=null)//Si si hay un valor recibido
        {
            try {
            Class.forName("org.postgresql.Driver");
            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());
            
            Statement st = conexion.createStatement(); //Crea la sentencia o instruccion sobre la que se ejecutara el query
            //Declarando el query que se va ejecutar para la consulta
            String query = "SELECT num_control,nombre,app,apm,status,carrera_nom,planest,grupo,"
                        + "semestre,fecha_nac,curp,sexo,estado,municipio,cp,generacion,email,telefono "
                    + "FROM alumnos "
                    + "where num_control =" + matricula;
            //Ejecutar el query
            ResultSet rs = st.executeQuery(query);
            //Preparae el Statement
            rs.next();
            
            //Obtener valores
                //Variable de tipo alumno que guardara los valores
                Alumno alumno=new Alumno();
                
                //String temporal, en caso de que el valor recibido sea nulo
                    String temp="";
                for(int cont=1; cont<=18; cont++)
                {
                    temp=rs.getString(cont);
                    if(temp==null)//Si el valor almacenado es nulo
                    {
                        temp="";//Para evitar problemas de NullPointerException
                    }
                    switch(cont)
                    {//num_control,nombre,app,apm,status,carrera_nom,planest,grupo,"
                        //+ "semestre,fecha_nac,curp,sexo,estado,municipio,cp
                        case 1: alumno.setMatricula(temp.trim());    break;
                        case 2: alumno.setNombre(temp.trim());    break;
                        case 3: alumno.setApp(temp.trim());    break;
                        case 4: alumno.setApm(temp.trim());    break;
                        case 5: alumno.setEstatus(temp.trim());    break;
                        case 6: alumno.setCarrera(temp.trim());    break;
                        case 7: alumno.setPlan(temp.trim());    break;
                        case 8: alumno.setGrupo(temp.trim());    break;
                        case 9: alumno.setSemestre(temp.trim());    break;
                        case 10:alumno.setFechaNac(temp.trim());     break;
                        case 11:alumno.setCurp(temp.trim());     break;
                        case 12:alumno.setSexo(temp.trim());     break;
                        case 13:alumno.setEstado(temp.trim());     break;
                        case 14:alumno.setMunicipio(temp.trim());     break;
                        case 15:alumno.setCp(temp.trim());     break;
                        case 16:alumno.setGeneracion(temp.trim());     break;
                        case 17:alumno.setCorreo(temp.trim());  break;
                        case 18:alumno.setTelefono(temp.trim());    break;
                    }
                }
                //Cerrar conexion
                    conexion.close();
                    rs.close();
                    st.close();
                request.setAttribute("ALUMNO", alumno);//Enviar el objeto de tipo alumno con la informaciónn
                request.getRequestDispatcher("Alumno/ver.jsp").forward(request, response);
            
            } catch (ClassNotFoundException | SQLException ex) {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                    request.setAttribute("DESCRIPCION", "Error al cargar datos del alumno con matricula: " + matricula+
                            " verifique que si exista el alumno.");
                    request.setAttribute("DIRECCIONBOTON","AdministrarAlumno");
                    request.setAttribute("MENSAJEBOTON","Volver");
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }
           
        response.sendRedirect(request.getContextPath() + "/AdministrarAlumno");
        }
        else//Si no se recibieron valores 
        {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "Error al cargar los datos del alumno, no se recibio ninguna matricula\n");
                request.setAttribute("DIRECCIONBOTON","AdministrarAlumno");
                request.setAttribute("MENSAJEBOTON","Volver");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        }
        
    }

}

