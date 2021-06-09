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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Respuesta;
import java.util.Date;

/**
 *
 * @author hbdye
 */
public class ContestarEncuesta extends HttpServlet {
    Respuesta trespuestas;
    PrintWriter out;
    Date x = new Date();
    String fecha = new SimpleDateFormat("dd-MM-yyyy").format(x);

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Objeto para convertir cadenas a UTF8
        ConvertirUTF8 convertir = new ConvertirUTF8();

        //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
        response.setContentType("text/html");
        out = response.getWriter();

        //Declaración de las variables donde se guardara la información
        int idEncuesta = Integer.parseInt((String) request.getParameter("idEncuesta"));
        int cantPreguntas = Integer.parseInt((String) request.getParameter("cantPreguntas"));
        boolean contestada = Boolean.parseBoolean((String) request.getParameter("contestada"));
        int numControl = 17091014;//Para pruebas
//        out.print(contestada);

        //Inicializar cosas necesarias pata la conexion a BD
        try {
            //Conexion a la BD
            Class.forName("org.postgresql.Driver");
            //Direccion, puerto, nombre de BD, usuario y contraseña
            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
            Connection conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());

            out.print("<br>Cantidad de preguntas: "+cantPreguntas);
            //Obtener los valores de las respuestas
                //Variables necesarias
                String tipo;//Tipo de pregunta
                //respuestas= new Respuesta[cantPreguntas];//Aqui se guardaran las respuestas
                String [] valoresSeleccionados;//Respuestas seleccionadas tipo CheckBox
                int valResp;//Guardara el valor seleccionado en opcion cerrada
                String stringTemporal;//Variable temporal
                
            for(int pregunta=1; pregunta<=cantPreguntas; pregunta++)
            {
                //Crear nuevo objeto de respuesta
                trespuestas= new Respuesta(
                                                        idEncuesta,
                                                        pregunta,//IdPregunta
                                                        numControl,
                                                        fecha);
                
                tipo=(String)request.getParameter("tipo"+pregunta);//Obtener el tipo de la pregunta en turno
                //Asignar el tipo de respuesta al objeto
                trespuestas.setTipo(tipo);
                out.print("<br>------------------------------------------<br>");
                out.print("<br>Pregunta no: "+pregunta+", Tipo: "+tipo);
                
                switch(tipo)
                  {
                      case "Abierta"://Si la pregunta es abierta
                          out.print("<br>------Entre al switch en caso: "+tipo);
                          //Obtener su valor y asignarlo al objeto temporal de respuestas
                          trespuestas.setOpcAbierta((String)request.getParameter("respuesta"+pregunta+"1"));
                          out.print("<br>Valor de respuesta abierta: "+trespuestas.getOpcAbierta());
                          break;
                          
                      case "Cerrada":
                          out.print("<br>------Entre al switch en caso: "+tipo);
                          //Obtener el valor seleccionado y guardarlo en la variable temporal
                          stringTemporal=(String)request.getParameter("respuesta"+pregunta+"1");
                          //Revisar que la respuesta obtenida no sea NULO, ya que si lo es, puede causar ERRORES
                          if(stringTemporal!=null)
                          {
                              //Convertir la respuesta a entero(rerdemos que los inputs type=radios devuelven un numero
                              valResp=Integer.parseInt(stringTemporal);
                              //Asignar el valor de la respuesta al objeto
                              trespuestas.respuestas[valResp-1]=1;
                          }
                          break;
                      default:
                          out.print("<br>------Entre al switch en caso: "+tipo);
                          //Obtener los valores seleccionados en los CheckBoxes
                          valoresSeleccionados=request.getParameterValues("respuesta"+pregunta+"1");
                          //Veririfcar que la variable valoresSeleccionados no sea Nula, ya que si no, generara errores
                          if(valoresSeleccionados!=null)
                          {
                            //Ponerle los valores al objeto tipo Respuesta
                            for(int conVal=0; conVal<valoresSeleccionados.length; conVal++)
                            {
                                trespuestas.respuestas[Integer.parseInt(valoresSeleccionados[conVal])-1]=1;
                            } 
                          }
                          break;
                }
                
                if(contestada)//Si la encuesta esta contestada, hay que realizar un UPDATE en la base de datos
                {
                    actualizar(conexion, trespuestas);
                }
                else//Si la encuesta NO esta contestada, hay que realizar un INSERT
                {
                    //Mandar a insertar la respuesta
                    insertar(conexion, trespuestas);
                }
                
            }
            //Enviar datos al JSP de mensaje
            request.setAttribute("NOMBRE_MENSAJE", "Hecho");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "¡Encuesta guardada con exito!");
            request.setAttribute("DESCRIPCION", "Sus respuestas han sido guardadas, gracias por su tiempo.");
            request.setAttribute("MENSAJEBOTON", "Ir a Inicio");
            request.setAttribute("DIRECCIONBOTON", "index.jsp");
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            
            
            
            
        } catch (ClassNotFoundException | SQLException ex) {
            request.setAttribute("NOMBRE_MENSAJE", "Error");
            request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
            request.setAttribute("DESCRIPCION", "Error al insertar en la base de datos:\n" + ex);
            request.setAttribute("MENSAJEBOTON", "Volver a contestar");
            request.setAttribute("DIRECCIONBOTON", "ContestaEncuesta");
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            out.print("<br>----Error al insertar en la base de datos:" + ex);
        } 
    }
    
    void insertar(Connection conexion, Respuesta respuesta) throws SQLException {
        String query = "INSERT INTO respuestas VALUES ("
                    + respuesta.getId_encuestas() + ","
                    + respuesta.getId_preguntas() + ","
                    + respuesta.getNum_control() + ", '"
                    + fecha + "',";
        
        for(int temp=0; temp<respuesta.respuestas.length; temp++)
        {
            if(respuesta.respuestas[temp]==1)
            {
                query+="1,";
            }
            else
            {
                query+="NULL,";
            }
        }
        query+="'"+respuesta.getTipo()+"','"+respuesta.getOpcAbierta()+"')";
        out.print("<br>"+query);
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        st.executeUpdate(query);
        
        out.println("<br>INSERTADO "+respuesta.getTipo());
        
    }
    void actualizar(Connection conexion, Respuesta respuesta) throws SQLException {
        //Query de la actualizacion
        String query = "UPDATE respuestas "
                     + "SET ";
        //Vamos a asignar el valor a las opc de respuestas (util para opciones multiples y cerradas)
        for(int temp=0; temp<respuesta.respuestas.length; temp++)
        {
            //Si la respuesta fue seleccionada
            if(respuesta.respuestas[temp]==1)
            {
                    //Poner esa opcion con valor 1
                    query+="opc"+(temp+1)+"=1 ,";
            }
            else//Si no fue seleccionada
            {   
                //Ponerle valor NULL, para que se sobreescriba la respuesta que antes tenia
                query+="opc"+(temp+1)+"=null ,";
            }
        }
        //Obtener el valor de la respuesta abierta
        query+="opcabierta= '"+respuesta.getOpcAbierta()+"',"
                + "fecha='"+fecha+"' "
                + "WHERE id_encuestas="+respuesta.getId_encuestas()+
                        " AND id_preguntas="+respuesta.getId_preguntas()+
                        " AND num_control="+respuesta.getNum_control();
        out.print("<br>"+query);
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        st.executeUpdate(query);
        
        out.println("<br>ACTUALIZADO "+respuesta.getTipo());
        
    }
}
