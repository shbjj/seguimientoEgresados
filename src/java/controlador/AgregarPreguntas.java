/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Pregunta;

/**
 *
 * @author hbdye
 */
public class AgregarPreguntas extends HttpServlet {

    //Aqui se guardaran todas las preguntas que se vayan a almacenar en la BD
    Pregunta[] preguntas;

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ConvertirUTF8 convert=new ConvertirUTF8();
        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        /*response.setContentType("text/html");
        PrintWriter out = response.getWriter();*/

        //Declaración de las variables donde se guardara la información
        int idEncuesta = Integer.parseInt((String) request.getParameter("idEncuesta"));
        int cantPreguntas = Integer.parseInt((String) request.getParameter("cantPreguntas"));

        //Las preguntas se guardaran en un vector
        preguntas = new Pregunta[cantPreguntas];
        
        //Variables para convertir cadenas a UTF-8
        String pregunta_s, tipo_s, obligatoria_s;
        byte[] tempBytes;
        
        
        
        for (int numPreg = 1; numPreg <= cantPreguntas; numPreg++) {
            //Obtener los datos
                    pregunta_s=(String) request.getParameter("formPregunta" + numPreg); //pregunta
                    tipo_s=(String) request.getParameter("formTipoPregunta" + numPreg);//tipo
                    obligatoria_s=(String) request.getParameter("formObligatoria" + numPreg);//obligatoria
            
            //Convertir a UTF-8
                    tempBytes= pregunta_s.getBytes();
                    pregunta_s = new String(tempBytes, StandardCharsets.UTF_8);
                    tempBytes= tipo_s.getBytes();
                    tipo_s = new String(tempBytes, StandardCharsets.UTF_8);
                    tempBytes= obligatoria_s.getBytes();
                    obligatoria_s = new String(tempBytes, StandardCharsets.UTF_8);
            
            //Crear un objeto pregunta y guardarlo en el vector
            preguntas[(numPreg - 1)] = new Pregunta(
                    idEncuesta, //id_encuestas
                    numPreg, //id_preguntas
                    pregunta_s, //pregunta
                    tipo_s,//tipo
                    obligatoria_s//obligatoria
            );

            //Imprimir (solo para verificar
            /*out.print(
                    "<br>------------------------------------ <br>"
                    + " No. Pregunta: " + preguntas[(numPreg - 1)].getId_preguntas()
                    + "<br> Pregunta: " + preguntas[(numPreg - 1)].getPregunta()
                    + "<br> Tipo: " + preguntas[(numPreg - 1)].getTipo()
                    + "<br> Obligatoria: " + preguntas[(numPreg - 1)].getObligatoria());*/

            //Ahora, ya que se tienen los datos de la pregunta, hay que ver si es 
            //diferente a abierta (ya que entonces tendra respuestas)
            if (preguntas[(numPreg - 1)].getTipo().compareTo("Abierta") == 0) {//Si la pregunta es abierta, no hacer nada
                //out.print("<br> IF, la preguta es abierta D:");
            } else {//Si la pregunta no es abierta, entonces...
                //out.print("<br> ELSE, la preguta no es abierta D:");
                //Obtener el número de respuestas, y ponerle ese valor a la pregunta
                preguntas[(numPreg - 1)].setNum_respuestas(Integer.parseInt((String) request.getParameter("formNumResp" + numPreg)));

                //Por cada respuesta, obtener sus valores y guardarlos en la pregunta
                for (int numResp = 1; numResp <= preguntas[(numPreg - 1)].getNum_respuestas(); numResp++) {
                    //out.print("<br>Resp. " + numResp + (String) request.getParameter("formRespuesta" + numPreg + numResp));

                    //Guardar los datos en la pregunta
                    preguntas[(numPreg - 1)].respuestas[(numResp - 1)] = convert.convertToUTF8((String) request.getParameter("formRespuesta" + numPreg + numResp));
                }
            }
        }

        //Inicializar cosas necesarias pata la conexion a BD
        try {
            //Conexion a la BD
            Class.forName("org.postgresql.Driver");
            //Direccion, puerto, nombre de BD, usuario y contraseña
              Conexion_bd datos_conexion=new Conexion_bd();//Aqui se guardan los datos de la conexion
       Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

            /*
                *Ahora que ya se tienen las preguntas guardadas en el arreglo, hay que recorrer el arreglo
                *e insertar cada una de las preguntas en la BD
             */
            for (int numPreg = 0; numPreg < cantPreguntas; numPreg++) {
                insertar(conexion, preguntas[numPreg]);
            }
            request.setAttribute("NOMBRE_MENSAJE", "Hecho");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "¡Preguntas guardadas exitosamente!");
            request.setAttribute("DESCRIPCION", "Las preguntas de la encuesta se han guardado, estan listas para poder ser contestadas.");
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            
            
        } catch (ClassNotFoundException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos:\n" + ex);
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            //out.print("<br>----Error al insertar en la base de datos:" + ex);
        } catch (SQLException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos:\n" + ex);
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            //out.print("<br>----Error al insertar en la base de datos:" + ex);
        }

    }

    void insertar(Connection conexion, Pregunta preg) throws SQLException {
        String query;
        //Dependiendo del tipo de pregunta, asi dependera el QUERY
        if (preg.getTipo().compareTo("Abierta") == 0) {//Si la pregunta es abierta
            //Query para conectar
            query = "INSERT INTO preguntas VALUES ("
                    + preg.getId_encuestas() + ","
                    + preg.getId_preguntas() + ", '"
                    + preg.getPregunta() + "','"
                    + preg.getTipo() + "','"
                    + preg.getObligatoria() + "',"
                    + preg.getNum_respuestas() + ","
                    + "NULL,NULL, NULL, NULL, NULL,NULL,NULL,NULL,NULL,NULL)";
        }
        else
        {//Si la pregunta no es abierta
            //Query para conectar
            query = "INSERT INTO preguntas VALUES ("
                    + preg.getId_encuestas() + ","
                    + preg.getId_preguntas() + ", '"
                    + preg.getPregunta() + "','"
                    + preg.getTipo() + "','"
                    + preg.getObligatoria() + "',"
                    + preg.getNum_respuestas() + ",";
                    //"NULL,NULL, NULL, NULL, NULL,NULL,NULL,NULL,NULL,NULL";
                    
                    for (int c=0; c<9; c++)//Recordemos que hay maximo 9 respuestas, por eso c>9
                    {
                     if(c<preg.getNum_respuestas())//Si se cumple, entonces obtener el valor de la respuesta y guardarla en el QUERY
                     {
                         query+="'"+preg.respuestas[c]+"',";
                     }
                     else//Si no se cumple, guardar NULL en el query, ya que no habra respuesta
                     {
                         query+="NULL,";
                     }
                    }
                    query+="NULL)";
        }

        /*
        INSERT INTO preguntas VALUES (1,1,'HOLA','ABIERTA','N',1,NULL,NULL, NULL, NULL, NULL,NULL,NULL,NULL,NULL,NULL);
         */
        //out.println("<br>-----------QUERY-------------<br>" + query);//Imprimir por errores

        //Ejecutar el Query
        Statement st = conexion.createStatement();
        st.executeUpdate(query);
        
        //out.println("<br>INSERTADO");
    }

}
