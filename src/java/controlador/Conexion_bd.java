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
    /*private String direccion="jdbc:postgresql://localhost:5432/prepa_seis_v1";
    private String usuario="postgres";
    private String contrasenia="root";*/
    
    
    private String direccion="jdbc:postgresql://ec2-52-7-159-155.compute-1.amazonaws.com:5432/d66ne6mj0n3oeq";
    private String usuario="kyjosbgcoxkffm";
    private String contrasenia="4a1b7cc4dcc9a5be00fdbf26a0bfa29d6985dfc5059dae7b76416e9816b4b6b5";

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
