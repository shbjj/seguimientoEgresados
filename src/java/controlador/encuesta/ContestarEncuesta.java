/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.encuesta;

import controlador.Conexion_bd;
//import controlador.ConvertirUTF8;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Respuesta;
import java.util.Date;
//import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hbdye
 */
public class ContestarEncuesta extends HttpServlet {

    Respuesta trespuestas;
    PrintWriter out;
    Date x = new Date();
    String fecha = new SimpleDateFormat("dd-MM-yyyy").format(x);
    
    java.util.Date d = new java.util.Date();
    java.sql.Date sd = new java.sql.Date(d.getTime());
    
    
    HttpSession session;

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //Sesion
        session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo
        boolean contestada = false;
        if (tipoS != null)//Si se inicio sesión
        {
            //Objeto para convertir cadenas a UTF8
            //ConvertirUTF8 convertir = new ConvertirUTF8();

            //para que la salida sea en html (no es tan correcto hacerlo ya que los servlets no deber tener salida)
            //response.setContentType("text/html");
            out = response.getWriter();
            
            //Declaración de las variables donde se guardara la información
            int idEncuesta = Integer.parseInt((String) request.getParameter("idEncuesta"));
            int cantPreguntas = Integer.parseInt((String) request.getParameter("cantPreguntas"));
            switch(tipoS)
		{
			case "1"://Inicia sesión un egresado
                            contestada = Boolean.parseBoolean((String) request.getParameter("contestada"));
                            String numControl = (String) session.getAttribute("MATRICULA");//Obtener la matricula
                            egresado(request, response, idEncuesta, cantPreguntas, contestada, Integer.parseInt(numControl));
                            break;
			case "3"://Inicia sesión un empleador
                            empleador(request, response, idEncuesta, cantPreguntas);
                            break;
			
			default:
			mensaje(request, response, 
                                "Error", 
                                "Ha ocurrido un error.", 
                                "No tiene permiso para acceder a este contenido",
                                "Volver",  
                                "index.jsp");
		}
        }
        else//no hay una sesión iniciada
	{
	//Redirigir al login
        response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

    }
    void mensaje(HttpServletRequest request, HttpServletResponse response, 
            String nombre, String sub_mens, String descripcion, 
            String mens_boton, String direccion_mens) throws ServletException, IOException
    {
        request.setAttribute("NOMBRE_MENSAJE", nombre);
        request.setAttribute("SUB_NOMBRE_MENSAJE", sub_mens);
        request.setAttribute("DESCRIPCION", descripcion);
        request.setAttribute("MENSAJEBOTON", mens_boton);
        request.setAttribute("DIRECCIONBOTON", direccion_mens);
        request.getRequestDispatcher("/mensaje.jsp").forward(request, response);
    }
    void egresado(HttpServletRequest request, HttpServletResponse response, int idEncuesta, int cantPreguntas, boolean contestada, int numControl) throws ServletException, IOException {
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

            out.print("<br>Cantidad de preguntas: " + cantPreguntas);
            //Obtener los valores de las respuestas
            //Variables necesarias
            String tipo;//Tipo de pregunta
            //respuestas= new Respuesta[cantPreguntas];//Aqui se guardaran las respuestas
            String[] valoresSeleccionados;//Respuestas seleccionadas tipo CheckBox
            int valResp;//Guardara el valor seleccionado en opcion cerrada
            String stringTemporal;//Variable temporal

            //Guardar valores de las respuestas
            for (int pregunta = 1; pregunta <= cantPreguntas; pregunta++) {
                //Crear nuevo objeto de respuesta
                trespuestas = new Respuesta(
                        idEncuesta,
                        pregunta,//IdPregunta
                        numControl,
                        fecha);

                tipo = (String) request.getParameter("tipo" + pregunta);//Obtener el tipo de la pregunta en turno
                //Asignar el tipo de respuesta al objeto
                trespuestas.setTipo(tipo);
                out.print("<br>------------------------------------------<br>");
                out.print("<br>Pregunta no: " + pregunta + ", Tipo: " + tipo);

                switch (tipo) {
                    case "Abierta"://Si la pregunta es abierta
                        out.print("<br>------Entre al switch en caso: " + tipo);
                        //Obtener su valor y asignarlo al objeto temporal de respuestas
                        trespuestas.setOpcAbierta((String) request.getParameter("respuesta" + pregunta + "1"));
                        out.print("<br>Valor de respuesta abierta: " + trespuestas.getOpcAbierta());
                        break;

                    case "Cerrada":
                        out.print("<br>------Entre al switch en caso: " + tipo);
                        //Obtener el valor seleccionado y guardarlo en la variable temporal
                        stringTemporal = (String) request.getParameter("respuesta" + pregunta + "1");
                        //Revisar que la respuesta obtenida no sea NULO, ya que si lo es, puede causar ERRORES
                        if (stringTemporal != null) {
                            //Convertir la respuesta a entero(rerdemos que los inputs type=radios devuelven un numero
                            valResp = Integer.parseInt(stringTemporal);
                            //Asignar el valor de la respuesta al objeto
                            trespuestas.respuestas[valResp - 1] = 1;
                        }
                        break;
                    default:
                        out.print("<br>------Entre al switch en caso: " + tipo);
                        //Obtener los valores seleccionados en los CheckBoxes
                        valoresSeleccionados = request.getParameterValues("respuesta" + pregunta + "1");
                        //Veririfcar que la variable valoresSeleccionados no sea Nula, ya que si no, generara errores
                        if (valoresSeleccionados != null) {
                            //Ponerle los valores al objeto tipo Respuesta
                            for (int conVal = 0; conVal < valoresSeleccionados.length; conVal++) {
                                trespuestas.respuestas[Integer.parseInt(valoresSeleccionados[conVal]) - 1] = 1;
                            }
                        }
                        break;
                }

                if (contestada)//Si la encuesta esta contestada, hay que realizar un UPDATE en la base de datos
                {
                    actualizarRespEgre(conexion, trespuestas);
                } else//Si la encuesta NO esta contestada, hay que realizar un INSERT
                {
                    //Mandar a insertar la respuesta
                    insertarRespEgre(conexion, trespuestas);
                }

            }
            //Cerrar conexion
            conexion.close();
            //Enviar datos al JSP de mensaje
            mensaje(request, response,
                    "Hecho",
                    "¡Encuesta guardada con exito!",
                    "Sus respuestas han sido guardadas, gracias por su tiempo.",
                    "Ir a Inicio",
                    "IndexAlumno");

        } catch (ClassNotFoundException | SQLException ex) {
            mensaje(request, response,
                    "Error",
                    "Ha ocurrido un error.",
                    "Error al insertar en la base de datos:\n" + ex,
                    "Ir a Inicio",
                    "IndexAlumno");
            
            out.print("<br>----Error al insertar en la base de datos:" + ex);
        }
    }

    void insertarRespEgre(Connection conexion, Respuesta respuesta) throws SQLException {
        
        //15
        String query="INSERT INTO respuestas VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        //Ejecutar el Query
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement(query);
        stmt.setInt(1, respuesta.getId_encuestas());
        stmt.setInt(2, respuesta.getId_preguntas());
        stmt.setInt(3, respuesta.getNum_control());
        stmt.setDate(4, sd);
        for (int temp = 0; temp < respuesta.respuestas.length; temp++) {
            if (respuesta.respuestas[temp] == 1) {//Si se selecciono esa respuesta
                stmt.setInt((temp+5), 1);
            } else {//Si no
                stmt.setInt((temp+5), 0);
            }
        }
        stmt.setString(14, respuesta.getTipo());
        stmt.setString(15, respuesta.getOpcAbierta());
        stmt.executeUpdate();

        //Cerrar conexion
          stmt.close();
        out.println("<br>INSERTADO " + respuesta.getTipo());

    }

    void actualizarRespEgre(Connection conexion, Respuesta respuesta) throws SQLException {
        //Query de la actualizacion
        String query = "UPDATE respuestas "
                + "SET "
                + "opc1=?, "
                + "opc2=?, "
                + "opc3=?, "
                + "opc4=?, "
                + "opc5=?, "
                + "opc6=?, "
                + "opc7=?, "
                + "opc8=?, "
                + "opc9=?, "
                + "opcabierta=?, "
                + "fecha=? "
                + "WHERE id_encuestas=?" 
                + " AND id_preguntas=?" 
                + " AND num_control=?" ;
        out.print("<br>" + query);
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement(query);
        //Agregar los valores al stmt
            //Agregar los valores de opc1, opc2...
            for (int temp = 0; temp < respuesta.respuestas.length; temp++) {
                if (respuesta.respuestas[temp] == 1) {//Si se selecciono esa respuesta
                    stmt.setInt((temp+1), 1);
                } else {//Si no
                    stmt.setInt((temp+1), 0);
                }
            }
            //Otros valores
            stmt.setString(10, respuesta.getOpcAbierta());
            stmt.setDate(11, sd);
            stmt.setInt(12, respuesta.getId_encuestas());
            stmt.setInt(13, respuesta.getId_preguntas());
            stmt.setInt(14, respuesta.getNum_control());
        //Ejecutar el Query
        stmt.executeUpdate();

        //Cerrar conexion
          stmt.close();
        out.println("<br>ACTUALIZADO " + respuesta.getTipo());

    }
    
    void empleador(HttpServletRequest request, HttpServletResponse response, int idEncuesta, int cantPreguntas) throws ServletException, IOException {
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

            out.print("<br>Cantidad de preguntas: " + cantPreguntas);
            //Obtener los valores de las respuestas
            //Variables necesarias
            String tipo;//Tipo de pregunta
            //respuestas= new Respuesta[cantPreguntas];//Aqui se guardaran las respuestas
            String[] valoresSeleccionados;//Respuestas seleccionadas tipo CheckBox
            int valResp;//Guardara el valor seleccionado en opcion cerrada
            String stringTemporal;//Variable temporal

            //Guardar valores de las respuestas
            for (int pregunta = 1; pregunta <= cantPreguntas; pregunta++) {
                //Crear nuevo objeto de respuesta
                trespuestas = new Respuesta(
                        idEncuesta,
                        pregunta,//IdPregunta
                        fecha);

                tipo = (String) request.getParameter("tipo" + pregunta);//Obtener el tipo de la pregunta en turno
                //Asignar el tipo de respuesta al objeto
                trespuestas.setTipo(tipo);
                out.print("<br>------------------------------------------<br>");
                out.print("<br>Pregunta no: " + pregunta + ", Tipo: " + tipo);

                switch (tipo) {
                    case "Abierta"://Si la pregunta es abierta
                        out.print("<br>------Entre al switch en caso: " + tipo);
                        //Obtener su valor y asignarlo al objeto temporal de respuestas
                        trespuestas.setOpcAbierta((String) request.getParameter("respuesta" + pregunta + "1"));
                        out.print("<br>Valor de respuesta abierta: " + trespuestas.getOpcAbierta());
                        break;

                    case "Cerrada":
                        out.print("<br>------Entre al switch en caso: " + tipo);
                        //Obtener el valor seleccionado y guardarlo en la variable temporal
                        stringTemporal = (String) request.getParameter("respuesta" + pregunta + "1");
                        //Revisar que la respuesta obtenida no sea NULO, ya que si lo es, puede causar ERRORES
                        if (stringTemporal != null) {
                            //Convertir la respuesta a entero(rerdemos que los inputs type=radios devuelven un numero
                            valResp = Integer.parseInt(stringTemporal);
                            //Asignar el valor de la respuesta al objeto
                            trespuestas.respuestas[valResp - 1] = 1;
                        }
                        break;
                    default:
                        out.print("<br>------Entre al switch en caso: " + tipo);
                        //Obtener los valores seleccionados en los CheckBoxes
                        valoresSeleccionados = request.getParameterValues("respuesta" + pregunta + "1");
                        //Veririfcar que la variable valoresSeleccionados no sea Nula, ya que si no, generara errores
                        if (valoresSeleccionados != null) {
                            //Ponerle los valores al objeto tipo Respuesta
                            for (int conVal = 0; conVal < valoresSeleccionados.length; conVal++) {
                                trespuestas.respuestas[Integer.parseInt(valoresSeleccionados[conVal]) - 1] = 1;
                            }
                        }
                        break;
                }

                insertarRespEmp(conexion, trespuestas);
                

            }
            //cerrar la sesión del empleador
            session.setAttribute("TIPO", null);
            session.setAttribute("ID_ENCUESTA", null);
            out.print("<br>"+"Sesión Tipo="+session.getAttribute("TIPO"));
            out.print("<br>"+"Sesión ID_ENCUESTA="+session.getAttribute("ID_ENCUESTA"));
            //Cerrar conexion
            conexion.close();
            //Enviar datos al JSP de mensaje
            mensaje(request, response,
                    "Hecho",
                    "¡Encuesta guardada con exito!",
                    "Sus respuestas han sido guardadas, gracias por su tiempo.",
                    "Ir a Inicio",
                    "login.jsp");

        } catch (ClassNotFoundException | SQLException ex) {
            mensaje(request, response,
                    "Error",
                    "Ha ocurrido un error.",
                    "Error al insertar en la base de datos:\n" + ex,
                    "Ir a Inicio",
                    "IndexAlumno");
            
            out.print("<br>----Error al insertar en la base de datos:" + ex);
        }
    }
      void insertarRespEmp(Connection conexion, Respuesta respuesta) throws SQLException {
        
        //15
        String query="INSERT INTO respuestas_emp"
                + "(id_encuestas,id_preguntas,fecha,opc1,opc2,opc3,opc4,opc5,opc6,opc7,opc8,opc9,tipo,opcabierta) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        /*
        insert into respuestas_emp
        (id_encuestas,id_preguntas,fecha,opc1,opc2,opc3,opc4,opc5,opc6,opc7,opc8,opc9,tipo,opcabierta) 
                values (23,1,'2021-07-05',1,0,0,0,0,0,0,0,0,'','');
        */
        //Ejecutar el Query
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement(query);
        stmt.setInt(1, respuesta.getId_encuestas());
        stmt.setInt(2, respuesta.getId_preguntas());
        stmt.setDate(3, sd);
        for (int temp = 0; temp < respuesta.respuestas.length; temp++) {
            if (respuesta.respuestas[temp] == 1) {//Si se selecciono esa respuesta
                stmt.setInt((temp+4), 1);
            } else {//Si no
                stmt.setInt((temp+4), 0);
            }
        }
        stmt.setString(13, respuesta.getTipo());
        stmt.setString(14, respuesta.getOpcAbierta());
        stmt.executeUpdate();

        out.println("<br>INSERTADO " + respuesta.getTipo());
        //Cerrar conexion
          stmt.close();
    }

    
}








/*
    void actualizar(Connection conexion, Respuesta respuesta) throws SQLException {
        //Query de la actualizacion
        String query = "UPDATE respuestas "
                + "SET ";
        //Vamos a asignar el valor a las opc de respuestas (util para opciones multiples y cerradas)
        for (int temp = 0; temp < respuesta.respuestas.length; temp++) {
            //Si la respuesta fue seleccionada
            if (respuesta.respuestas[temp] == 1) {
                //Poner esa opcion con valor 1
                query += "opc" + (temp + 1) + "=1 ,";
            } else//Si no fue seleccionada
            {
                //Ponerle valor NULL, para que se sobreescriba la respuesta que antes tenia
                query += "opc" + (temp + 1) + "=null ,";
            }
        }
        //Obtener el valor de la respuesta abierta
        query += "opcabierta= '" + respuesta.getOpcAbierta() + "',"
                + "fecha='" + fecha + "' "
                + "WHERE id_encuestas=" + respuesta.getId_encuestas()
                + " AND id_preguntas=" + respuesta.getId_preguntas()
                + " AND num_control=" + respuesta.getNum_control();
        out.print("<br>" + query);
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        st.executeUpdate(query);

        out.println("<br>ACTUALIZADO " + respuesta.getTipo());

    }
    */
    /*void insertar(Connection conexion, Respuesta respuesta) throws SQLException {
        String query = "INSERT INTO respuestas VALUES ("
                + respuesta.getId_encuestas() + ","
                + respuesta.getId_preguntas() + ","
                + respuesta.getNum_control() + ", '"
                + fecha + "',";

        for (int temp = 0; temp < respuesta.respuestas.length; temp++) {
            if (respuesta.respuestas[temp] == 1) {
                query += "1,";
            } else {
                query += "NULL,";
            }
        }
        query += "'" + respuesta.getTipo() + "','" + respuesta.getOpcAbierta() + "')";
        out.print("<br>" + query);
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        st.executeUpdate(query);

        out.println("<br>INSERTADO " + respuesta.getTipo());

    }*/