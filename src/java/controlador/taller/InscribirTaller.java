/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.taller;

import controlador.Conexion_bd;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Dia;
import modelo.Taller;

/**
 *
 * @author hbdye
 */
public class InscribirTaller extends HttpServlet {

    PrintWriter out;
    //Talleres que ya 
    ArrayList<Dia> horariosDeTalleres = new ArrayList<Dia>();
    Taller tallerAInscribir;
    String err="";
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        out = response.getWriter();
        HttpSession session = request.getSession(true);
        String tipoS = (String) session.getAttribute("TIPO");//Obtener el tipo de sesion que hay activo

        if (tipoS != null)//Si se inicio sesión
        {
            //out.print("<br>Se inicio sesión");
            if (tipoS.compareTo("1") == 0) //Inicia sesión un alumno
            {
                //out.print("<br>Alumno");
                String estatus = (String) session.getAttribute("ESTATUS");//Obtener el tipo de alumno, egresado o inscrito
                if (estatus.equalsIgnoreCase("inscrito"))//Si alumno esta inscrito
                {
                    //Obtener el ID del taller al que se va a inscribir
                    String idTaller = (String) request.getParameter("idTaller");
                    if (idTaller == null)//Si no se recibio un valor valido, mandar error
                    {
                        error("No se recibieron valores válidos.", request, response);
                    }
                    //Obtener la matricula del alumno desde la sesion
                    String matricula = (String) session.getAttribute("MATRICULA");

                    try {
                        Class.forName("org.postgresql.Driver");

                        //Obtener la información del taller al que se quiere inscribir
                        buscarTaller(idTaller);
                        //Obtener todos los talleres a los cuales ya esta inscrito
                        buscarTalleresIncritos(matricula);
                        //Comparar si hay choque de horarios
                        if (!horariosDeTalleres.isEmpty())//Si esta inscrito en algun taller,
                        //hay que ver que no hayan choques de horario
                        {
                            boolean sinChoque = false;
                            sinChoque = revisarSiHayChoque();
                            //If,
                            if (sinChoque) {//Si no chocan los horarios hay que inscribir
                                if(inscribir(matricula))//Inscirbir
                                {//Si si se inscribio, regresar a index
                                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                                }
                                else
                                {//Si no se inscribio, mandar mensaje de error
                                    error("No se ha podido inscribir, hay un error BOOLEAN sin choque:"+err, request, response);
                                }
                            } else {//Si chocan hay que mandar mensaje de error
                                error("No se ha podido inscribir, estas inscrito a un taller que choca con el horario del nuevo taller.", request, response);
                            }

                            //Vaciar ArrayList
                            horariosDeTalleres.clear();
                        } else//Si no esta inscrito en algun taller, entonces hay que inscribir directamente
                        {
                            if(inscribir(matricula))//Inscirbir
                                {//Si si se inscribio, regresar a index
                                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                                }
                                else
                                {//Si no se inscribio, mandar mensaje de error
                                    error("No se ha podido inscribir, hay un error BOOLEAN sin talleres:"+err, request, response);
                                }
                        }

                        //Mandar al JSP
                        out.print("<br>Sin erroor");
                        //request.getRequestDispatcher("/Taller/disponibles.jsp").forward(request, response);
                    } catch (ClassNotFoundException | SQLException ex) {
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

    boolean inscribir(String matricula) throws SQLException {
        
        boolean seInscribio=false;//Variable para saber si se incribio o no
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = null;
        PreparedStatement st=null;
        ResultSet rs=null;
        try {
            conexion = DriverManager.getConnection(
                    datos_conexion.getDireccion(),
                    datos_conexion.getUsuario(),
                    datos_conexion.getContrasenia());
            //Definir que se ocuparan transacciones
            conexion.setAutoCommit(false);
            //Incribir al taller
            //Para inscribir primero hay que consultar si hay cupo en el taller a inscribir
            //Si el cupo es mayor a los incritos, aun hay lugar
            String query = "SELECT (cupo>inscritos) "
                    + "FROM talleres "
                    + "WHERE idtaller="+tallerAInscribir.getIdTaller()+";";
            //Asignarle la conexion y el query
            st = conexion.prepareStatement(query);
            
            //Ejecutar query
             rs = st.executeQuery();
             
             
            //Se tiene el resultado
            if (rs.next())//Si hay un resultado
            {
                if (rs.getBoolean(1))//Obtener el resultado de si hay cupo o no
                {//Si hay cupo, inscribir
                    //Insertar la boleta
                        //Query para conectar
                        query = "INSERT INTO boletas "
                                + "VALUES(?,?,?,?); ";
                        //Ejecutar el Query
                        //Declarar el objeto PreparedStatement
                        st.close(); st = null;
                        //Asignarle la conexion y el query
                        st = conexion.prepareStatement(query);
                        //Agregarle los valores al query
                        st.setInt(1, Integer.parseInt(matricula));
                        st.setInt(2, tallerAInscribir.getIdTaller());
                        st.setString(3, "Cursando");
                        st.setDouble(4, 0);//Calificacion
                        //ejecutar
                        st.executeUpdate();
                        //Cerrar el St
                        st.close();
                        st = null;
                    //Cambiar el numero de inscritos en el taller
                        query = "UPDATE talleres "
                                + "SET inscritos=(inscritos+1) "
                                + "WHERE idtaller=?;";
                        //Asignarle la conexion y el query
                        st = conexion.prepareStatement(query);
                        //Agregarle los valores al query
                        st.setInt(1, tallerAInscribir.getIdTaller());
                        //ejecutar
                        st.executeUpdate();
                        
                    //Hacer commit
                    conexion.commit();
                    //
                    seInscribio=true;
                }
                else
                {//No hay cupo en el taller
                    seInscribio=false;   
                    //Hacer rollback
                    conexion.rollback();
                }
            }

            //Cerrar
            st.close();
            rs.close();
            conexion.close();
        } catch (SQLException ex) {
            err+=ex;
            if(st!=null && rs!=null)
            {
                st.close();
                rs.close();
            }
            //Si hay falla, hacer rollback
            conexion.rollback();
            conexion.close();
            return false;
        }
        return seInscribio;
    }

    boolean compara(Dia d1, Dia d2) {

        //CASO 1 El horario nuevo esta DESPUES del horario viejo
        //Hora inicial de D1 es mayor a hora inicial de D2
        boolean caso1A = d1.getHoraIni().compareTo(d2.getHoraIni()) > 0;
        //out.print("Hora inicial de D1 es mayor a hora inicial de D2: " + caso1A);
        //Hora ini D1 mayor o igual que hora fin D2
        boolean caso1B = d1.getHoraIni().compareTo(d2.getHoraFin()) >= 0;
        //out.print("<br>Hora ini D1 mayor o igual que hora fin D2: " + caso1B);
        //Caso 2 El horario nuevo esta antes del horaro viejo
        //Hora final de D1 es igual o menor que la hora inicial de D2
        boolean caso2A = d1.getHoraFin().compareTo(d2.getHoraIni()) <= 0;
        //out.print("<br>Hora final de D1 es igual o menor que la hora inicial de D2: " + caso2A);
        //Hora final de D1 es menor que la hora final de D2
        boolean caso2B = d1.getHoraFin().compareTo(d2.getHoraFin()) < 0;
        //out.print("<br>Hora final de D1 es menor que la hora final de D2: " + caso2B);

        boolean sinChoque = (caso1A & caso1B) || (caso2A & caso2B);
        //out.print("<br> ¿sin choque? " + sinChoque);
        return sinChoque;

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

    void buscarTaller(String idTaller) throws SQLException {
        out.print("<br>Taller a inscribir");
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());
        //BUSCAR INFORMACION DEL TALLER
        String query = "SELECT nombre "
                + "FROM talleres "
                + "WHERE idtaller=" + idTaller + ";";
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Guardar datos
        tallerAInscribir = new Taller();

        while (rs.next()) {

            tallerAInscribir.setIdTaller(Integer.parseInt(idTaller));
            tallerAInscribir.setNombre(rs.getString(1).trim());
            out.print("<br>Id" + Integer.parseInt(idTaller));
            out.print("<br>Nombre" + rs.getString(1).trim());

        }

        //BUSCAR LOS DIAS DEL TALLER
        tallerAInscribir.dias = buscarDiasTaller(conexion, Integer.parseInt(idTaller));
        //Cerrar conexion
        conexion.close();
        rs.close();
        st.close();
    }

    Dia[] buscarDiasTaller(Connection conexion, int idTaller) throws SQLException {
        out.print("<br>Dias del taller");
        String query = "SELECT iddia, horaini, horafin "
                + "FROM dias "
                + "WHERE idtaller=" + idTaller + ";";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Obtener los datos del horario
        ArrayList<Dia> dias = new ArrayList<Dia>();
        Dia dia = null;//Dia temporal

        while (rs.next()) {
            //Agregarle valores al objeto temporal de tipo dia
            dia = new Dia(rs.getInt(1));
            dia.setHoraIni_S(rs.getString(2).trim());
            out.print("<br>HORA:" + rs.getString(2).trim());
            dia.setHoraFin_S(rs.getString(3).trim());
            //Agregar el dia al Array
            out.print("<br>Dia: " + dia.getDiaS());
            out.print("<br>" + dia.getHoraIni_S());
            out.print("<br>" + dia.getHoraFin_S());
            dias.add(dia);
        }
        out.print("<br>");
        //Crear un arreglo temporal de dias
        Dia[] diasTemp = new Dia[dias.size()];
        diasTemp = dias.toArray(diasTemp);
        //Cerrar St y rs
        rs.close();
        st.close();

        return diasTemp;
    }

    void buscarDias(Connection conexion, int idTaller) throws SQLException {
        //out.print("<br>Dias del taller");
        String query = "SELECT iddia, horaini, horafin "
                + "FROM dias "
                + "WHERE idtaller=" + idTaller + ";";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        Dia dia = null;//Dia temporal

        while (rs.next()) {
            //Agregarle valores al objeto temporal de tipo dia
            dia = new Dia(rs.getInt(1));
            dia.setHoraIni_S(rs.getString(2).trim());
            dia.setHoraFin_S(rs.getString(3).trim());
            dia.setIdTaller(idTaller);
            //Agregar el dia al Array
            out.print("<br>Dia: " + dia.getDiaS());
            out.print("<br>" + dia.getHoraIni_S());
            out.print("<br>" + dia.getHoraFin_S());
            horariosDeTalleres.add(dia);
        }
        out.print("<br>");

        //Cerrar St y rs
        rs.close();
        st.close();
    }

    void buscarTalleresIncritos(String matricula) throws SQLException {
        out.print("<br>Talleres inscritos ya_____");
        //Direccion, puerto, nombre de BD, usuario y contraseña
        Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
        Connection conexion = DriverManager.getConnection(
                datos_conexion.getDireccion(),
                datos_conexion.getUsuario(),
                datos_conexion.getContrasenia());
        //BUSCAR INFORMACION DEL TALLER
        String query = "select t.idtaller "
                + "from boletas b, talleres t "
                + "where b.idtaller=t.idtaller and b.estatus='Cursando' and b.num_control=" + matricula + ";";
        //Ejecutar el Query
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);

        //Guardar datos
        //Taller temp;
        //horariosDeTalleres
        Dia dia;
        while (rs.next()) {
            /*//Instanciar un nuevo taller
                  temp= new Taller();
                  //Agregarle los datos
                  temp.setIdTaller(rs.getInt(1));
                  //BUSCAR LOS HORARIOS DEL TALLER
                  temp.dias=buscarDiasTaller(conexion, rs.getInt(1));
                  out.print("<br>"+temp.getIdTaller());
                  out.print("<br>"+temp.getNombre());
                  //Agregar el taller temporal al Array
                  talleresInscritos.add(temp);*/
            buscarDias(conexion, rs.getInt(1));//Buscar los dias de ese taller
        }
        //Cerrar conexion
        conexion.close();
        rs.close();
        st.close();
    }

    boolean revisarSiHayChoque() {
        //Este metodo revisara si el taller al que se quiere inscribir choca con
        //algun taller que se este cursando
        out.print("<br>Revisar si hay choque, entro a metodo");
        //Bandera que marca si hay choque o no
        boolean sinChoque = true;
        //Convertir el array de dias a Iterator
        Iterator<Dia> diasOcupados;
        Dia diaTemp;
        //Recorer el horario del taller a que se quiere inscribir
        for (int c = 0; c < tallerAInscribir.dias.length && sinChoque; c++) {
            out.print("<br>Recorrer el horario del taller");
            diasOcupados = horariosDeTalleres.iterator();

            //Recorer el Iterator y comparar las horas 
            while (diasOcupados.hasNext() && sinChoque) {
                diaTemp = diasOcupados.next();

                if (tallerAInscribir.dias[c].getdDia() == diaTemp.getdDia())//Si el dia de ambos es igual, ejempli lunes y lunes
                {
                    out.print("<br>Los dias son iguales Dnew" + tallerAInscribir.dias[c].getdDia() + "  Dold" + diaTemp.getdDia());
                    //Comparar
                    sinChoque = compara(tallerAInscribir.dias[c], diaTemp);
                }

            }

            diasOcupados = null;
        }
        diasOcupados = null;
        return sinChoque;
    }

}
