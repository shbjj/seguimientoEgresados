/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author hbdye
 */
public class Conexion_bd {
    private String direccion="jdbc:postgresql://localhost:5432/prepa_seis_v1";
    private String usuario="postgres";
    private String contrasenia="root";

    public Conexion_bd() {
    }

    public String getDireccion() {
        return direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
            
            //Connection conexion = DriverManager.getConnection("jdbc:postgresql://45.33.125.66:5432/prepa_seis_v1", "postgres", "Adgjmptw1797@1");
}
