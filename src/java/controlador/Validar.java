/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hbdye Esta clase va a servir para validar que las sesiones si esten
 * correctamente iniciadas
 */
public class Validar {

    //TIPO
    private static final String TIPO_ALUMNO = "1";
    private static final String TIPO_ADMINISTRADOR = "2";
    private static final String TIPO_EMPLEADOR = "3";

    //ESTATUS - alumnos
    private static final String STATUS_ALUMNO_INSCRITO = "INSCRITO";
    private static final String STATUS_ALUMNO_EGRESADO = "EGRESADO";
    private static final String STATUS_ALUMNO_BAJA_TEMPORAL = "BAJA TEMPORAL";
    private static final String STATUS_ALUMNO_BAJA_DEFINITIVA = "BAJA DEFINITIVA";

    //ROL - administradores
    private static final String ADMINISTRADOR_ROOT = "0";
    private static final String ADMINISTRADOR_ADMINISTRADOR = "1";
    private static final String ADMINISTRADOR_SEGUIMIENTO_EGRESADOS = "2";
    private static final String ADMINISTRADOR_TALLERES = "3";
    private static final String ADMINISTRADOR_CAPTURADOR = "4";

    //NOMBRE DE ATRIBUTOS
    private static final String TIPO = "TIPO";
    private static final String ESTATUS = "ESTATUS";
    private static final String ROL = "ROL";

    public static void validaSesion(HttpServletRequest request, HttpServletResponse response,
            HttpSession session) throws IOException {
        if (session.getAttribute(TIPO) == null) {//Si es igual a nulo, no hay una sesion activa
            //Como no hay una sesion activa, mandar al login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    public static void compararTipo(HttpServletRequest request, HttpServletResponse response,
            String tipo, String tipoSession) throws IOException, ServletException
    {
        if(!tipo.equals(tipoSession))
        {
            Mensaje.error(request, response,
                    "No tiene permiso para ver esta información.",
                    "Ir a inicio",
                    "index.jsp");
        }
    }
    
    public static void mensajeError(HttpServletRequest request, HttpServletResponse response,
            String descripcion, String mensajeBoton, String direccionBoton)
            throws IOException, ServletException {
        request.setAttribute("NOMBRE_MENSAJE", "Error");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", descripcion);
        request.setAttribute("MENSAJEBOTON", mensajeBoton);
        request.setAttribute("DIRECCIONBOTON", direccionBoton);
        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }

    public static void mensaje(HttpServletRequest request, HttpServletResponse response,
            String nombreMensaje, String subNombreMensaje,
            String descripcion, String mensajeBoton, String direccionBoton)
            throws IOException, ServletException {
        request.setAttribute("NOMBRE_MENSAJE", nombreMensaje);
        request.setAttribute("SUB_NOMBRE_MENSAJE", subNombreMensaje);
        request.setAttribute("DESCRIPCION", descripcion);
        request.setAttribute("MENSAJEBOTON", mensajeBoton);
        request.setAttribute("DIRECCIONBOTON", direccionBoton);
        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }

}
