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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        out = response.getWriter();
        
        //Objeto para convertir una cadena a UTF-8
        //ConvertirUTF8 convert = new ConvertirUTF8();
        
        //Obtener Datos

            //Recibir los parametros desde un JSP (Datos de la encuesta)
            String nombre, descripcion, instrucciones, despedida, fecha;
//            nombre = convert.convertToUTF8((String) request.getParameter("nombre"));
//            descripcion = convert.convertToUTF8((String) request.getParameter("descripcion"));
//            instrucciones = convert.convertToUTF8((String) request.getParameter("instrucciones"));
//            despedida = convert.convertToUTF8((String) request.getParameter("despedida"));
//            fecha = convert.convertToUTF8((String) request.getParameter("fecha"));
            nombre = (String) request.getParameter("nombre");
            descripcion = (String) request.getParameter("descripcion");
            instrucciones = (String) request.getParameter("instrucciones");
            despedida = (String) request.getParameter("despedida");
            fecha = (String) request.getParameter("fecha");
            
            int idEncuesta = Integer.parseInt((String) request.getParameter("idEncuesta"));
            int cantPreguntas = Integer.parseInt((String) request.getParameter("cantPreguntas"));

            //Las preguntas se guardaran en un vector
            preguntas = new Pregunta[cantPreguntas];

            //Variables temporales para las preguntas
            String pregunta_s, tipo_s, obligatoria_s;

            for (int numPreg = 1; numPreg <= cantPreguntas; numPreg++) {
                //Obtener los datos de la pregunta
//                pregunta_s = convert.convertToUTF8((String) request.getParameter("formPregunta" + numPreg)); //pregunta
//                tipo_s = convert.convertToUTF8((String) request.getParameter("formTipoPregunta" + numPreg));//tipo
//                obligatoria_s = convert.convertToUTF8((String) request.getParameter("formObligatoria" + numPreg));//obligatoria
                pregunta_s = (String) request.getParameter("formPregunta" + numPreg); //pregunta
                tipo_s = (String) request.getParameter("formTipoPregunta" + numPreg);//tipo
                obligatoria_s = (String) request.getParameter("formObligatoria" + numPreg);//obligatoria

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
//                        preguntas[(numPreg - 1)].respuestas[(numResp - 1)] = convert.convertToUTF8((String) request.getParameter("formRespuesta" + numPreg + numResp));
                        preguntas[(numPreg - 1)].respuestas[(numResp - 1)] = (String) request.getParameter("formRespuesta" + numPreg + numResp);
                    }
                }

            }
        //Ahora que ya se tienen los datos tanto de la encuesta como de las preguntas, hay que guardalas en la BD
        
        //Inicializar cosas necesarias pata la conexion a BD
        try {
            //Conexion a la BD
            Class.forName("org.postgresql.Driver");
            
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
        //Convertir String de la fecha a SqlDate
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date =null;
            try
            {
                 date=sdf1.parse(fecha);
            } catch (ParseException ex) {
            //Codigo de exception
            }
            java.sql.Date fechaSQL = new java.sql.Date(date.getTime());


        //Abrir conexion
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());
        String query="UPDATE encuestas "
                    + "SET nombre=?, "
                    + "descripcion=?, "
                    + "instrucciones=?, "
                    + "despedida=?, "
                    + "fecha=? "
                    + "WHERE id_encuestas=?";
        
        //Ejecutar el Query
        PreparedStatement st = null;
        st=conexion.prepareStatement(query);
        
        //Agregar datos a st
        st.setString(1, nombre);
        st.setString(2, descripcion);
        st.setString(3, instrucciones);
        st.setString(4, despedida);
        st.setDate(5, fechaSQL);
        st.setInt(6, idEncuesta);
        /*
             + "SET nombre='"+nombre+"', "
                + "descripcion='"+descripcion+"', "
                + "instrucciones='"+instrucciones+"', "
                + "despedida='"+despedida+"', "
                + "fecha='"+fecha+"' 
        idEncuesta";
        */
        
        st.executeUpdate();
        //Cerrar sesión
        conexion.close();
        st.close();
    }
    void update(Pregunta preg) throws SQLException {
        //Abrir conexion
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());
        String query="UPDATE preguntas "
                    + "SET "
                    + "pregunta=?, "
                    + "obligatoria=? ";
        //Dependiendo del tipo de pregunta, asi dependera el QUERY
        if (preg.getTipo().compareTo("Abierta") != 0) {//Si la pregunta no abierta
            
                    for (int c=0; c<9; c++)//Recordemos que hay maximo 9 respuestas, por eso c>9
                    {
                     if(c<preg.getNum_respuestas())//Si se cumple, entonces poner ? en el QUERY
                     {
                         query+=", opc"+(c+1)+"=? ";
                         //query+=", opc"+(c+1)+"='"+preg.respuestas[c]+"' ";
                     }
                    }
        }
        query+="WHERE id_encuestas=? and id_preguntas=?";
        //query+="WHERE id_encuestas="+preg.getId_encuestas()+" and id_preguntas="+preg.getId_preguntas();
        //Ejecutar el Query
        PreparedStatement st = null;
        st=conexion.prepareStatement(query);
        //Agregar datos al st
        //Datos que todas las preguntas tienen
        st.setString(1, preg.getPregunta());
        st.setString(2, preg.getObligatoria());
        int t=2;//En caso de ser preg. abierta el valor se queda en 2, si no, se cambia en el if
        //Si la pregunta es diferente de abierta
        if (preg.getTipo().compareTo("Abierta") != 0) {//Si la pregunta no abierta
            for (int c=0; c<9; c++)//Recordemos que hay maximo 9 respuestas, por eso c>9
            {
                if(c<preg.getNum_respuestas())//Si se cumple, entonces obtener el valor de la respuesta y guardarla en el st
                {
                    t=c+3;//Posicion en la que se guardara el valor
                    st.setString((t), preg.respuestas[c]);
                }
            }        
        }
        st.setInt((t+1),preg.getId_encuestas());
        st.setInt((t+2),preg.getId_preguntas());
        
        
        st.executeUpdate();
        
        //Cerrar sesión
        conexion.close();
        st.close();
    }

}
