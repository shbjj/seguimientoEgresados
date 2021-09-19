/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author hbdye
 */
public class Encuesta {
    public short tamNombre=150, tamClave=6, tamOtros=500;
    private String id_encuestas,
            nombre,
            descripcion,
            instrucciones,
            despedida,
            fecha,
            clave,
            habilitada;
    public Encuesta()
    {
        
    }
    public int getTamNombre() {
        return tamNombre;
    }

    public void setTamNombre(short tamNombre) {
        this.tamNombre = tamNombre;
    }

    public int getTamClave() {
        return tamClave;
    }

    public void setTamClave(short tamClave) {
        this.tamClave = tamClave;
    }

    public int getTamOtros() {
        return tamOtros;
    }

    public void setTamOtros(short tamOtros) {
        this.tamOtros = tamOtros;
    }

    public String getId_encuestas() {
        return id_encuestas;
    }

    public void setId_encuestas(String id_encuestas) {
        this.id_encuestas = id_encuestas;
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

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getDespedida() {
        return despedida;
    }

    public void setDespedida(String despedida) {
        this.despedida = despedida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(String habilitada) {
        this.habilitada = habilitada;
    }
}
