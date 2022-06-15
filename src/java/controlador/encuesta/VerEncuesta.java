/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.encuesta;

import controlador.Conexion_bd;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import modelo.Pregunta;

/**
 *
 * @author hbdye
 */
public class VerEncuesta extends HttpServlet {

 //Variable que almacenara el num de preguntas, se modificara cuando se ocupe el metodo getNumPreguntas
    int cantPreguntas;

    //Para hacer pruebas (sirve para imprimir desde el Servlet
    PrintWriter out;
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        out = response.getWriter();

        //Obtener el ID de la encuesta a contestar desde el JSP
        String temp=(String) request.getParameter("idEncuesta");
        if(temp!="")//Si si existe un valor como id
        {
           int idEncuesta = Integer.parseInt(temp); 
           //Inicializar cosas necesarias pata la conexion a BD
            try {
                //Conexion a la BD
                Class.forName("org.postgresql.Driver");

                //Obtener información de la encuesta
                getEncuesta(request, idEncuesta);

                //Obtener las preguntas de la encuesta
                getPreguntas(request, idEncuesta);

                //Mandar a llamar al JSP que mostrara las preguntas y/o respuestas
                request.getRequestDispatcher("Encuesta/ver.jsp").forward(request, response);
            } catch (ClassNotFoundException | SQLException  ex) {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "Error al buscar la encuesta:\n" + ex);
                request.setAttribute("DIRECCIONBOTON","AdministrarEncuesta");
                request.setAttribute("MENSAJEBOTON","Volver");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }
            
        }
        request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al buscar la encuesta\n");
            request.setAttribute("DIRECCIONBOTON","AdministrarEncuesta");
            request.setAttribute("MENSAJEBOTON","Volver");
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        
        
    }
 private void getEncuesta(HttpServletRequest request, int idEncuesta) throws  SQLException {
        //Este metodo obtendra la información de la encuesta y la mandara al JSP

        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

    //Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");

        //Ejecutar el Query
        String query = "SELECT * FROM encuestas WHERE id_encuestas=" + idEncuesta;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Obtener datos, y enviarlos como parametros
        while (rs.next()) {
            //Obtener los valores
            String nombre = rs.getString(2).trim();
            String descripcion = rs.getString(3).trim();
            String instrucciones = rs.getString(4).trim();
            String despedida = rs.getString(5).trim();
            String fecha = rs.getString(6).trim();
            String clave = rs.getString(7).trim();
            String habilitada = rs.getString(8).trim();

            request.setAttribute("ID", idEncuesta);
            request.setAttribute("NOMBRE", nombre);
            request.setAttribute("DESCRIPCION", descripcion);
            request.setAttribute("INSTRUCCIONES", instrucciones);
            request.setAttribute("DESPEDIDA", despedida);
            request.setAttribute("FECHA", fecha);
            request.setAttribute("CLAVE", clave);
            request.setAttribute("HABILITADA", habilitada);
        }
        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
    }

    private void getPreguntas(HttpServletRequest request, int idEncuesta) throws SQLException {
        //Obtener las preguntas correspondientes a la encuesta, 
        //almacenarlas en un vector y enviarlo como parametro

        //Declaracion del vector donde se guardaran las preguntas
        Pregunta[] preguntas;
        preguntas = new Pregunta[getNumPreguntas(idEncuesta)];
        //Query
        String query = "SELECT * "
                + "FROM preguntas "
                + "WHERE id_encuestas=" + idEncuesta;
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(), 
                datos_conexion.getUsuario(), 
                datos_conexion.getContrasenia());

//Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");

        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Obtener los valores e ir almacenando en el arreglo
        //Declarar un contador para recorrer el arreglo de preguntass
        int c = 0;
        
        //Variables que se van a ocupar
        int id_encuestas ,//id_encuestas
                    id_preguntas ;//id_preguntas
        String pregunta ,//Pregunta
                    tipo ,//Tipo
                    obligatoria;
        
        while(rs.next()) {
            //Obtener los valores

            //Crear un nuevo objeto de tipo pregunta
            id_encuestas = rs.getInt(1);//id_encuestas
                    id_preguntas = rs.getInt(2);//id_preguntas
            pregunta = rs.getString(3).trim();//Pregunta
                    tipo = rs.getString(4).trim();//Tipo
                    obligatoria = rs.getString(5).trim();//Obligatoria

            //Guardar los datos en el objeto
            preguntas[c] = new Pregunta(id_encuestas, id_preguntas, pregunta, tipo, obligatoria);
            
            //Si la pregunta es abierta o carrada/multiple...
            if(tipo.compareTo("Abierta") == 0)
            {//Si es abierta, no hacer nada, ya que no hay respuestas probables
                
            }
            else
            {//Obtener y guardar las respuestas
                 //obtener el numero de respuestas
                int num_respuestas=rs.getInt(6);
                preguntas[c].setNum_respuestas(num_respuestas);
                //out.print("<br> Numero de respuestas: "+num_respuestas);
                
                for(int cont=0; cont<num_respuestas; cont++)
                {
                    preguntas[c].respuestas[cont]=rs.getString((cont+7)).trim();
                }
            }
           
            //out.print("<br> Objeto pregunta: ++++++++++"        + "<br>"+preguntas[c].toString());
            
            //Incrementar contador
            c++;
        }
        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
        //Enviamos el arreglo al JSP
        request.setAttribute("PREGUNTAS", preguntas);

    }

     private int getNumPreguntas(int idEncuesta) throws SQLException {
        //Obtener la cantidad de preguntas que tiene una encuesta
        String query = "SELECT count(*) "
                + "FROM preguntas "
                + "WHERE id_encuestas=" + idEncuesta;

        //out.print("<br> QUERY getNumPreguntas" + query);
        //Variable temporal donde se guardara el valor 
        int temp = 0;

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
            temp = rs.getInt(1);
        }
        
        cantPreguntas=temp;//variable global para el num de preguntas
        
        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
        return temp;
    }
    
    

}
