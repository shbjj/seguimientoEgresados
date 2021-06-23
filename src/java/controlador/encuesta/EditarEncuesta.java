/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.encuesta;

import controlador.Conexion_bd;
import controlador.ConvertirUTF8;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Pregunta;

/**
 *
 * @author hbdye
 */
public class EditarEncuesta extends HttpServlet {

    Pregunta[] preguntas;//Aqui se guardaran las preguntas
    PrintWriter out;
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        response.setContentType("text/html");
        out = response.getWriter();
        
        //Objeto para convertir una cadena a UTF-8
        ConvertirUTF8 convert = new ConvertirUTF8();
        
        //Obtener Datos

            //Recibir los parametros desde un JSP (Datos de la encuesta)
            String nombre, descripcion, instrucciones, despedida, fecha;
            nombre = convert.convertToUTF8((String) request.getParameter("nombre"));
            descripcion = convert.convertToUTF8((String) request.getParameter("descripcion"));
            instrucciones = convert.convertToUTF8((String) request.getParameter("instrucciones"));
            despedida = convert.convertToUTF8((String) request.getParameter("despedida"));
            fecha = convert.convertToUTF8((String) request.getParameter("fecha"));
            
            int idEncuesta = Integer.parseInt((String) request.getParameter("idEncuesta"));
            int cantPreguntas = Integer.parseInt((String) request.getParameter("cantPreguntas"));

            //Las preguntas se guardaran en un vector
            preguntas = new Pregunta[cantPreguntas];

            //Variables temporales para las preguntas
            String pregunta_s, tipo_s, obligatoria_s;

            for (int numPreg = 1; numPreg <= cantPreguntas; numPreg++) {
                //Obtener los datos de la pregunta
                pregunta_s = convert.convertToUTF8((String) request.getParameter("formPregunta" + numPreg)); //pregunta
                tipo_s = convert.convertToUTF8((String) request.getParameter("formTipoPregunta" + numPreg));//tipo
                obligatoria_s = convert.convertToUTF8((String) request.getParameter("formObligatoria" + numPreg));//obligatoria

                //Crear un objeto pregunta y guardarlo en el vector
                preguntas[(numPreg - 1)] = new Pregunta(
                        idEncuesta, //id_encuestas
                        numPreg, //id_preguntas
                        pregunta_s, //pregunta
                        tipo_s,//tipo
                        obligatoria_s//obligatoria
                );

                //Ahora, ya que se tienen los datos de la pregunta, hay que ver si es 
                //diferente a abierta (ya que entonces tendra respuestas)
                if (preguntas[(numPreg - 1)].getTipo().compareTo("Abierta") == 0) {//Si la pregunta es abierta, no hacer nada

                } else {//Si la pregunta no es abierta, entonces...//out.print("<br> ELSE, la preguta no es abierta D:");
                    //Obtener el número de respuestas, y ponerle ese valor a la pregunta
                    preguntas[(numPreg - 1)].setNum_respuestas(Integer.parseInt((String) request.getParameter("formNumResp" + numPreg)));

                    //Por cada respuesta, obtener sus valores y guardarlos en la pregunta
                    for (int numResp = 1; numResp <= preguntas[(numPreg - 1)].getNum_respuestas(); numResp++) {

                        //Guardar los datos en la pregunta
                        preguntas[(numPreg - 1)].respuestas[(numResp - 1)] = convert.convertToUTF8((String) request.getParameter("formRespuesta" + numPreg + numResp));
                    }
                }

            }
        //Ahora que ya se tienen los datos tanto de la encuesta como de las preguntas, hay que guardalas en la BD
        
        //Inicializar cosas necesarias pata la conexion a BD
        try {
            //Conexion a la BD
            Class.forName("org.postgresql.Driver");
            //Direccion, puerto, nombre de BD, usuario y contraseña
            Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
            Connection conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(), 
                    datos_conexion.getUsuario(), 
                    datos_conexion.getContrasenia());

            //Actualizar datos de la encuesta
            update(idEncuesta, nombre, descripcion, instrucciones, despedida, fecha);
            /*
                *Ahora que ya se tienen las preguntas guardadas en el arreglo, hay que recorrer el arreglo
                *y actualizar cada una de las preguntas en la BD
             */
            for (int numPreg = 0; numPreg < cantPreguntas; numPreg++) {
                update(preguntas[numPreg]);
            }
            //Redireccionar para visualizar la encuesta
            response.sendRedirect(request.getContextPath() + "/VerEncuesta?idEncuesta="+idEncuesta);
            
            
        } catch (ClassNotFoundException | SQLException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al modificar la encuesta:\n" + ex);
            request.setAttribute("MENSAJEBOTON", "Volver");
            request.setAttribute("DIRECCIONBOTON", "AdministrarEncuesta");
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        }
    }
    void update(int idEncuesta, String nombre, String descripcion, String instrucciones, String despedida, String fecha) throws SQLException {
        //Abrir conexion
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());
        String query="UPDATE encuestas "
                    + "SET nombre='"+nombre+"', "
                + "descripcion='"+descripcion+"', "
                + "instrucciones='"+instrucciones+"', "
                + "despedida='"+despedida+"', "
                + "fecha='"+fecha+"' ";
        
        query+="WHERE id_encuestas="+idEncuesta;
        
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        st.executeUpdate(query);
    }
    void update(Pregunta preg) throws SQLException {
        //Abrir conexion
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());
        String query="UPDATE preguntas "
                    + "SET pregunta='"+preg.getPregunta()+"', "
                    + "obligatoria='"+preg.getObligatoria()+"' ";
        //Dependiendo del tipo de pregunta, asi dependera el QUERY
        if (preg.getTipo().compareTo("Abierta") != 0) {//Si la pregunta no abierta
            
                    for (int c=0; c<9; c++)//Recordemos que hay maximo 9 respuestas, por eso c>9
                    {
                     if(c<preg.getNum_respuestas())//Si se cumple, entonces obtener el valor de la respuesta y guardarla en el QUERY
                     {
                         query+=", opc"+(c+1)+"='"+preg.respuestas[c]+"' ";
                     }
                    }
        }
        query+="WHERE id_encuestas="+preg.getId_encuestas()+" and id_preguntas="+preg.getId_preguntas();
        
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        st.executeUpdate(query);
    }

}