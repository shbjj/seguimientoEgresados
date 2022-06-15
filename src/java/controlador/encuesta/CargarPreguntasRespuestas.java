/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.encuesta;

import controlador.Conexion_bd;
import java.io.IOException;
import java.io.PrintWriter;
//import java.nio.charset.StandardCharsets;
import java.sql.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Pregunta;
import modelo.Respuesta;

/**
 *
 * @author hbdye
 */
public class CargarPreguntasRespuestas extends HttpServlet {

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
        //Validacion de sesion
        HttpSession session = request.getSession(true);
        String tipo = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo
        if (tipo != null)//Se inicio sesión
        {
            if(tipo.compareTo("2")==0)//SI la sesion es de un administrador, no puede ver el contenido
            {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "index.jsp");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }
            else//Si no es administrador, entonces si
            {
                //Variables necesarias
                int idEncuesta=0;
                String numControl="";
                //Obtener el idEncuesta
                switch (tipo) 
                {
                    case "1":
                        //Obtener el ID de la encuesta a contestar desde el JSP
                        idEncuesta = Integer.parseInt((String) request.getParameter("modificarEncuesta"));
                        //Obtener el numero de control
                        numControl = (String) session.getAttribute("MATRICULA");//Obtener la matricula
                        break;
                    case "3":
                        //Obtener el ID de la encuesta pero desde el atributo de sesion
                        idEncuesta = Integer.parseInt((String)session.getAttribute("ID_ENCUESTA"));
                        break;

                }

                //Inicializar cosas necesarias pata la conexion a BD
                try {
                    //Conexion a la BD
                    Class.forName("org.postgresql.Driver");

                    //Obtener información de la encuesta
                    getEncuesta(request, idEncuesta);

                    //Obtener las preguntas de la encuesta
                    getPreguntas(request, idEncuesta);

                    if(tipo.compareTo("1")==0)//Si es un egresado
                    {
                        //Obtener las respuestas (solo en caso de egresados)
                        getRespuestas(request, idEncuesta, numControl);
                    }

                    //Mandar a llamar al JSP que mostrara las preguntas y/o respuestas
                    request.getRequestDispatcher("Encuesta/contestar.jsp").forward(request, response);
                } catch (ClassNotFoundException | SQLException ex) {
                    request.setAttribute("NOMBRE_MENSAJE", "Error");
                    request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                    request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos:\n" + ex);
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                    //out.print("<br>----Error al insertar en la base de datos:" + ex);
                }
            }
        }
    }

    private void getEncuesta(HttpServletRequest request, int idEncuesta) throws SQLException {
        //Este metodo obtendra la información de la encuesta y la mandara al JSP

        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
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

            request.setAttribute("ID", idEncuesta);
            request.setAttribute("NOMBRE", nombre);
            request.setAttribute("DESCRIPCION", descripcion);
            request.setAttribute("INSTRUCCIONES", instrucciones);
            request.setAttribute("DESPEDIDA", despedida);
            request.setAttribute("FECHA", fecha);
            request.setAttribute("CLAVE", clave);

            /*      out.print("Nombre:" + nombre
                    + "<br>Descripción: " + descripcion
                    + "<br>Instrucciones: " + instrucciones
                    + "<br>Despedida: " + despedida
                    + "<br>Fecha: " + fecha);*/
        }
        
        //Cerrar conexion
          conexion.close();
          rs.close();
          st.close();
    }

    private void getPreguntas(HttpServletRequest request, int idEncuesta) throws SQLException {
        //Obtener las preguntas correspondientes a la encuesta, 
        //almacenarlas en un vector y enviarlo como parametro

        //Declaracion del vector donde se guardaran las preguntas
        Pregunta[] preguntas;
        preguntas = new Pregunta[getNumPreguntas(idEncuesta)];
        //out.print("<br>Numero de preguntas: "+getNumPreguntas(idEncuesta));
        //Query
        String query = "SELECT * "
                + "FROM preguntas "
                + "WHERE id_encuestas=" + idEncuesta;
        //out.print("<br> QUERY getPreguntas " + query);
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

//Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Obtener los valores e ir almacenando en el arreglo
        //Declarar un contador para recorrer el arreglo de preguntass
        int c = 0;

        //Variables que se van a ocupar
        int id_encuestas,//id_encuestas
                id_preguntas;//id_preguntas
        String pregunta,//Pregunta
                tipo,//Tipo
                obligatoria;
        while (rs.next()) {
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
            if (tipo.compareTo("Abierta") == 0) {//Si es abierta, no hacer nada, ya que no hay respuestas probables

            } else {//Obtener y guardar las respuestas
                //obtener el numero de respuestas
                int num_respuestas = rs.getInt(6);
                preguntas[c].setNum_respuestas(num_respuestas);
                //out.print("<br> Numero de respuestas: "+num_respuestas);

                for (int cont = 0; cont < num_respuestas; cont++) {
                    preguntas[c].respuestas[cont] = rs.getString((cont + 7)).trim();
                }
            }

            //out.print("<br> Objeto pregunta: ++++++++++"        + "<br>"+preguntas[c].toString());
            //Incrementar contador
            c++;
        }
        //Cerrar conexion
          conexion.close();
          rs.close();
          st.close();
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
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
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
        //Cerrar conexion
          conexion.close();
          rs.close();
          st.close();
        cantPreguntas = temp;//variable global para el num de preguntas
        return temp;
    }

    private void getRespuestas(HttpServletRequest request, int idEncuesta, String numControl) throws SQLException {
        /*
            Este metodo buscara en la tabla de respuestas, si es que existe un registro de alguna respuesta
            que contenga el identificador de la encuesta y a su vez el numero de control del alumno.
            Si es asi, entonces la encuesta ya ha sido contestada, y hay que obtener sus respuestas para despues
            mostrarlas, y si no, entonces no se tiene que obtener nada y se procedera a mostrar una encuesta en
            blanco.
         */
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

        //Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");
        //Query de busqueda
        String query = "SELECT * "
                + "FROM respuestas "
                + "WHERE id_encuestas=" + idEncuesta + " and num_control=" + numControl;
        out.print(query);
        //Ejecutar el Query
        Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);//DEclarar el Statement para que se 
        //Pueda recorrrer "bidireccionalmente"
        ResultSet rs = st.executeQuery(query);

        //Si hay respuestas
        if (rs.next())//En caso de que si haya respuestas a la encuesta
        {
            rs.beforeFirst();//Mover rs antes del primer resultado
            //Variables que se ocuparan
            int id_encuestas, id_preguntas, num_control;
            String date;

            //Declarar el arreglo de Respuestas (con la misma longuitud que el arreglo de preguntas
            Respuesta[] respuestas;
            respuestas = new Respuesta[cantPreguntas];

            //Contador
            int numRespuesta = 0;
            //Obtener los valores de las respuestas e irlas guardando en un vector
            while (rs.next()) {
                id_encuestas = rs.getInt(1);
                id_preguntas = rs.getInt(2);
                num_control = rs.getInt(3);
                date = rs.getString(4).trim();

                //Mostrar xd (test)
                out.print("<br> ---------------------------<br> RESPUESTA NUM: " + (numRespuesta + 1)
                        + "<br> id_encuestas " + id_encuestas
                        + "<br> id_preguntas " + id_preguntas
                        + "<br> num_control " + num_control
                        + "<br> date " + date);

                //Crear el objeto de tipo Respuesta en el vector
                respuestas[numRespuesta] = new Respuesta(id_encuestas, id_preguntas, num_control, date);

                //out.print("<br>se crea el objeto");
                //Obtener el tipo de respuesta
                respuestas[numRespuesta].setTipo(rs.getString(14).trim());
                //out.print("<br> Tipo: "+respuestas[numRespuesta].getTipo());

                //Dependiendo del tipo de respuesta se obtendran las respuestas
                if (respuestas[numRespuesta].getTipo().compareToIgnoreCase("Abierta") == 0) {//Si la respuesta es de tipo Abierta
                    respuestas[numRespuesta].setOpcAbierta(rs.getString(15).trim());
                } else {//Si no, entonces obtener los demas valores
                    for (int c = 0; c < 9; c++) {
                        respuestas[numRespuesta].respuestas[c] = rs.getInt((c + 5));
                    }
                }

                out.print("<br><br>--------Objeto respuesta-------<br>" + respuestas[numRespuesta].toString());

                numRespuesta++;
            }

            //Enviar el parametro Encuesta contestada
            request.setAttribute("CONTESTADA", true);
            //Si la encuesta esta contestada
            //Enviar el vector como parametro al JSP
            request.setAttribute("RESPUESTAS", respuestas);
            //Obtener valores y enviarlos
        } else {//Si no hay respuestas en la tabla

            //Enviar el parametro Encuesta contestada
            request.setAttribute("CONTESTADA", false);
        }
        //Cerrar conexion
          conexion.close();
          rs.close();
          st.close();
    }
}
