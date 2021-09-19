/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author hbdye
 */public class Taller {
    
    //Variables
    private int idTaller; 
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String clave;
    private String instructor;
    private String periodo;
    private Date fechaIni;
    private Date fechaFin;
    private int cupo;
    private int inscritos;
    private String estatus;//Abierto, Cerrado, Culminado
    public Dia [] dias;
    public int getInscritos() {
        return inscritos;
    }

    public void setInscritos(int inscritos) {
        this.inscritos = inscritos;
    }
    
    
    //Tamaños
    final private short tamNombre=200;
    final private short tamDescripcion=400;
    final private short tamUbicacion=200;
    final private short tamClave=15;
    final private short tamInstructor=150;
    final private short tamPeriodo=30;
    final private short tamEstatus=10;

    public Taller() {
    }

    public short getTamNombre() {
        return tamNombre;
    }

    public short getTamDescripcion() {
        return tamDescripcion;
    }

    public short getTamUbicacion() {
        return tamUbicacion;
    }

    public short getTamClave() {
        return tamClave;
    }

    public short getTamInstructor() {
        return tamInstructor;
    }

    public short getTamPeriodo() {
        return tamPeriodo;
    }

    public short getTamEstatus() {
        return tamEstatus;
    }

    

    public int getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        //Convertir fecha a SQLDate
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date =null;
            try
            {
                 date=sdf1.parse(fechaIni);
            } catch (ParseException ex) {
                
            }
            java.sql.Date temp = new java.sql.Date(date.getTime()); 
        this.fechaIni = temp;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        //Convertir fecha a SQLDate
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date =null;
            try
            {
                 date=sdf1.parse(fechaFin);
            } catch (ParseException ex) {
                
            }
            java.sql.Date temp = new java.sql.Date(date.getTime()); 
        this.fechaFin = temp;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
