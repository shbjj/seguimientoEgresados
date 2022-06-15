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

/**
 *
 * @author hbdye
 */
public class Mensaje {
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
    public static void error(HttpServletRequest request, HttpServletResponse response,
            String descripcion, String mensajeBoton, String direccionBoton)
            throws IOException, ServletException {
        request.setAttribute("NOMBRE_MENSAJE", "Error :c");
        request.setAttribute("SUB_NOMBRE_MENSAJE", "Ha ocurrido un error.");
        request.setAttribute("DESCRIPCION", descripcion);
        request.setAttribute("MENSAJEBOTON", mensajeBoton);
        request.setAttribute("DIRECCIONBOTON", direccionBoton);
        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }
}
