/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Alumno;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author hbdye
 */
public class LeerExcel {

    /**
     * Esta es una Clase que servira para poder leer un archivo XLSX
     */
    private  String ruta = "D:\\Archivos\\Descargas\\INSCRITOS PREPA TLALTIZAPAN  al 27 mayo 2021.xls";

    public static void main(String args[]) throws InvalidFormatException {
        try {
            agregarAlumnos(leerArchivo());
        } catch (IOException ex) {
            Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static ArrayList<Alumno> leerArchivo() throws IOException, InvalidFormatException {
        //Array donde se guardaran los alumnos
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        Alumno temp=null;//Alumno temporal
        
        //Crear un Workbook con la ruta del archivo
        Workbook workbook = WorkbookFactory.create(new File("D:/Archivos/Descargas/hugo.xls"));
        //Objeto para formatear
        DataFormatter formatter = new DataFormatter();
        //Hoja del archivo
        Sheet s = workbook.getSheetAt(0);
        //Recorrer los renglones
        for (Row r : s) {
            temp=new Alumno();
            temp.setMatricula(formatter.formatCellValue(r.getCell(0)));
            temp.setNombre(formatter.formatCellValue(r.getCell(1)));
            temp.setApp(formatter.formatCellValue(r.getCell(2)));
            temp.setApm(formatter.formatCellValue(r.getCell(3)));
            temp.setEstatus(formatter.formatCellValue(r.getCell(4)));
            temp.setCarrera(formatter.formatCellValue(r.getCell(5)));
            temp.setPlan(formatter.formatCellValue(r.getCell(6)));
            temp.setGeneracion(formatter.formatCellValue(r.getCell(7)));
            temp.setGrupo(formatter.formatCellValue(r.getCell(8)));
            temp.setSemestre(formatter.formatCellValue(r.getCell(9)));
            temp.setFechaNacDate(formatter.formatCellValue(r.getCell(10)));
            temp.setCurp(formatter.formatCellValue(r.getCell(11)));
            temp.setSexo(formatter.formatCellValue(r.getCell(12)));
            temp.setEstado(formatter.formatCellValue(r.getCell(13)));
            temp.setMunicipio(formatter.formatCellValue(r.getCell(14)));
            temp.setCp(formatter.formatCellValue(r.getCell(15)));
            
            
            //Agregar Alumno a ArrayList
            alumnos.add(temp);
            
            //Recorrer las celdas
//            for (Cell c : r) {
//                System.out.println(formatter.formatCellValue(c));
//            }
        }
//        
         alumnos.forEach(alumno->{
             System.out.println(alumno);
         });
        return alumnos;
    }
    
    public static void agregarAlumnos(ArrayList<Alumno> alumnos)
    {
        
                        try {
                            //Conexion a la BD
                            Class.forName("org.postgresql.Driver");
                            //Direccion, puerto, nombre de BD, usuario y contraseña
                            Conexion_bd datos_conexion = new Conexion_bd();//Aqui se guardan los datos de la conexion
                            Connection conexion = DriverManager.getConnection(datos_conexion.getDireccion(), datos_conexion.getUsuario(), datos_conexion.getContrasenia());

                            alumnos.forEach(alumno->{
                                agregarAlumno(alumno, conexion);
                            });
                            
                            //Cerrar sesión
                            conexion.close();
                            } catch (ClassNotFoundException | SQLException ex) {
                            ex.printStackTrace();
                        }
    }
    
    private static void agregarAlumno(Alumno a, Connection conexion)
    {          
        try {//Query para conectar
        String query = "INSERT INTO alumnos "
                + "(num_control,"
                + "nombre,"
                + "app,"
                + "apm,"
                + "status,"
                + "carrera_nom,"
                + "planest,"
                + "generacion,"
                + "grupo,"
                + "semestre,"
                + "fecha_nac,"
                + "curp,"
                + "sexo,"
                + "estado,"
                + "municipio,"
                + "cp"
                + ")"
                + "VALUES "
                + "(?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?,"
                + "?)";
        
        //Ejecutar el Query
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement(query);
        stmt.setInt(1, Integer.parseInt(a.getMatricula()));
        stmt.setString(2, a.getNombre());
        stmt.setString(3, a.getApp());
        stmt.setString(4, a.getApm());
        stmt.setString(5, a.getEstatus());
        stmt.setString(6, a.getCarrera());
        stmt.setString(7, a.getPlan());
        stmt.setString(8, a.getGeneracion());
        stmt.setString(9, a.getGrupo());
        stmt.setInt(10, Integer.parseInt(a.getSemestre()));
        stmt.setDate(11, a.getFechaDate());
        stmt.setString(12, a.getCurp());
        stmt.setString(13, a.getSexo());
        stmt.setString(14, a.getEstado());
        stmt.setString(15, a.getMunicipio());
        stmt.setString(16, a.getCp());
        stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            
        }
    }
}
