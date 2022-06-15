/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.encuesta;

import controlador.Conexion_bd;
import java.io.IOException;
import java.io.PrintWriter;
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
import modelo.Pregunta;
import modelo.Respuesta;

/**
 *
 * @author hbdye
 */
public class GraficosEncuesta extends HttpServlet {

    HttpServletResponse response;
    Pregunta[] preguntas;
    Respuesta[] respuestas;
    PrintWriter out;
    int tipoEncuesta = 0;

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        out=response.getWriter();
        this.response = response;
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        
        HttpSession session=request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            if (tipoS.compareTo("2") == 0) {//Si la sesion es de un administrador
                //Recibir ei ID de la encuesta
                String temp = (String) request.getParameter("idEncuesta");
                String tempTipoS = (String) request.getParameter("tipo_enc");
                int t;
                if (temp == null || temp.compareTo("") == 0 || tempTipoS == null)//Si no se recibe un valor de id 
                {
                    error(request, response, "Error al buscar en la base de datos<br>"
                            + "No se ha recibido ningun valor de referencia a alguna encuesta");//Enviar a mensaje de error
                } else {
                    int idEncuesta = Integer.parseInt(temp);//Convertir el ID de la encuesta a int
                    tipoEncuesta = Integer.parseInt(tempTipoS);//Si es de egresado=1, de empleador=2
                    try {
                        //Conexion a la BD
                        Class.forName("org.postgresql.Driver");

                        //Obtener los valores de Nombre, quienes contestaran y fecha de la encuesta
                        //Obtener información de la encuesta
                        getEncuesta(request, idEncuesta);
                        //Obtener id_preguntas, pregunta, tipo, obligatoria, num_respuestas, opc1-opc9 de las preguntas
                        //Obtener las preguntas de la encuesta
                        getPreguntas(request, idEncuesta);
                        //Obtener los valores numericos de las respuestas y almacenarlo en un objeto
                        //De tipo respuesta
                        respuestas = new Respuesta[preguntas.length];//Declarar el tamaño del arreglo con el mismo tamaño que el de preguntas
                        for (int respuesta = 0; respuesta < preguntas.length; respuesta++) {
                            respuestas[respuesta] = new Respuesta();//Declarar el objeto de respuesta
                            //respuestas[respuesta].setId_preguntas(preguntas[respuesta].getId_preguntas());//Ponerle el id de la pregunta
                            //obtener ls valores de las respuestas en base a la pregunta correspondiente

                            if (preguntas[respuesta].getTipo().compareTo("Abierta") == 0)//Si el tipo de pregunta es abierta....-
                            {
                                //Saber la cantidad de respuestas que hay de esta pregunta, ocupando
                                //la funcion getNumRespuestas a la cual se le envia el ID de la encuesta y pregunta
                                t = getNumRespuestas(idEncuesta, preguntas[respuesta].getId_preguntas());
                                respuestas[respuesta].respuestasS = new String[t];
                                //obtener las respuestas
                                respuestas[respuesta].respuestasS = getRespuestasAbiertas(idEncuesta, preguntas[respuesta].getId_preguntas(), t);

                            } else {//Si no es abierta...
                                //Obtener todos los posibles valores de las respuestas
                                for (int cont = 1; cont <= preguntas[respuesta].getNum_respuestas(); cont++) {
                                    /*
                            Consultar en la BD la suma total de las respuestas, esto con ayuda de la funcion consultaSum, se envia el 
                            id de la encuesta, id de la pregunta, y la columna de la que se quiere ibtener el dato (opc). el resultado 
                            se guardara en el objeto respuestas, en su vector que tiene destinado a esto
                                     */

                                    //respuestas[respuesta].respuestas[cont]=
                                    t = consultaSum(idEncuesta, preguntas[respuesta].getId_preguntas(), cont);
                                    respuestas[respuesta].respuestas[cont - 1] = t;
                                }

                            }

                        }
                        //Enviar el arreglo de respuestas
                        request.setAttribute("RESPUESTAS", respuestas);

                        //Mandar a llamar al JSP que mostrara los graficos
                        request.getRequestDispatcher("Encuesta/graficos_1.jsp").forward(request, response);
                    } catch (ClassNotFoundException | SQLException ex) {

                        error(request, response, "Error al buscar en la base de datos:<br>" + ex);//Enviar a mensaje de error
                    }

                }

            } else {
                request.setAttribute("NOMBRE_MENSAJE", "Error");
                request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
                request.setAttribute("DESCRIPCION", "No tiene permiso para acceder a este contenido");
                request.setAttribute("MENSAJEBOTON", "Volver");
                request.setAttribute("DIRECCIONBOTON", "index.jsp");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }
        } else//no hay una sesión iniciada
        {
            //Redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

    }

    private void getEncuesta(HttpServletRequest request, int idEncuesta) throws SQLException, ServletException, IOException {
        //Este metodo obtendra la información de la encuesta y la mandara al JSP

        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

        //Ejecutar el Query Nombre, quienes contestaran y fecha
        String query = "SELECT "
                + "nombre, clave, fecha "
                + "FROM encuestas "
                + "WHERE id_encuestas=" + idEncuesta;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        boolean encuestaExiste = false;
        //Obtener datos, y enviarlos como parametros
        while (rs.next()) {
            //Obtener los valores
            String nombre = rs.getString(1).trim();
            String clave = rs.getString(2).trim();
            String fecha = rs.getString(3).trim();
            //Enviar los valores
            request.setAttribute("ID", idEncuesta);
            request.setAttribute("NOMBRE", nombre);
            request.setAttribute("FECHA", fecha);
            request.setAttribute("CLAVE", clave);
            encuestaExiste = true;
        }
        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
        if (!encuestaExiste)//Si la encuesta no existe, enviar a error
        {
            error(request, response, "Error al buscar en la base de datos<br>"
                    + "No se ha encontrado la encuesta");//Enviar a mensaje de error
        }
    }

    private void getPreguntas(HttpServletRequest request, int idEncuesta) throws SQLException {
        //Obtener las preguntas correspondientes a la encuesta, 
        //almacenarlas en un vector y enviarlo como parametro

        //Declaracion del vector donde se guardaran las preguntas
        preguntas = new Pregunta[getNumPreguntas(idEncuesta)];
        //Query id_preguntas, pregunta, tipo, obligatoria, num_respuestas, opc1-opc9 de las preguntas
        String query = "SELECT * "
                + "FROM preguntas "
                + "WHERE id_encuestas=" + idEncuesta;
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

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

        return temp;
    }

    private int getNumRespuestas(int idEncuesta, int idPregunta) throws SQLException {
        //Obtener la cantidad de preguntas que tiene una encuesta
        String query;
        if (tipoEncuesta == 1)//Si la encuesta es de egresado
        {
            query = "SELECT count(*) "
                    + "FROM respuestas "
                    + "WHERE id_encuestas=" + idEncuesta + " and id_preguntas=" + idPregunta;
        } else//Si la encuesta es de empleador
        {
            query = "SELECT count(*) "
                    + "FROM respuestas_emp "
                    + "WHERE id_encuestas=" + idEncuesta + " and id_preguntas=" + idPregunta;
        }
        out.print("<br>"+"Query "+query);
        //Variable temporal donde se guardara el valor 
        int temp = 0;

        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Obtener los valores
        while (rs.next()) {
            //Obtener los valores
            temp = rs.getInt(1);
        }
        
        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
        //out.print("<br>"+"Núm resp."+temp);
        return temp;
    }

    private String[] getRespuestasAbiertas(int idEncuesta, int idPregunta, int tam) throws SQLException {
        //Obtener la cantidad de preguntas que tiene una encuesta
        String query;
        if (tipoEncuesta == 1)//Si la encuesta es de egresado
        {
            query = "SELECT opcabierta "
                    + "FROM respuestas "
                    + "WHERE id_encuestas=" + idEncuesta + " and id_preguntas=" + idPregunta;
        } else//Si la encuesta es de empleador
        {
            query = "SELECT opcabierta "
                    + "FROM respuestas_emp "
                    + "WHERE id_encuestas=" + idEncuesta + " and id_preguntas=" + idPregunta;
        }
        //Variables temporales
        String[] temp = new String[tam];
        int cont = 0;

        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Obtener los valores
        while (rs.next()) {
            //Obtener los valores
            temp[cont] = rs.getString(1).trim();
            cont++;
        }

        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
        return temp;
    }

    private int consultaSum(int idEncuesta, int idPregunta, int opc) throws SQLException {
        //Obtener la cantidad de preguntas que tiene una encuesta
        String query;
        if (tipoEncuesta == 1)//Si la encuesta es de egresado
        {
            query = "SELECT SUM(opc" + opc + ") "
                    + "FROM respuestas "
                    + "WHERE id_encuestas=" + idEncuesta + " and id_preguntas=" + idPregunta;
        } else//Si la encuesta es de empleador
        {
            query = "SELECT SUM(opc" + opc + ") "
                    + "FROM respuestas_emp "
                    + "WHERE id_encuestas=" + idEncuesta + " and id_preguntas=" + idPregunta;
        }

        //Variable temporal donde se guardara el valor 
        int temp = 0;

        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

        //Ejecutar el Query
        //out.print("<br> "+query);
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Obtener los valores
        while (rs.next()) {
            //Obtener los valores
            temp = rs.getInt(1);
        }
        //Cerrar sesión
        conexion.close();
        st.close();
        rs.close();
        return temp;
    }

    void error(HttpServletRequest request, HttpServletResponse response, String descripcion) throws ServletException, IOException {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", descripcion);
        request.setAttribute("DIRECCIONBOTON", "AdministrarEncuesta");
        request.setAttribute("MENSAJEBOTON", "Regresar");
        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }
}
