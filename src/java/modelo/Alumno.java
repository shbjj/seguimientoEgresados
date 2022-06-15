/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.alumno.AgregarAlumno;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hbdye
 */
public class Alumno {

    private String matricula, nombre,
            app, apm, estatus, carrera,
            plan, grupo, semestre,
            fechaNac, curp, sexo,
            estado, municipio, cp, generacion, telefono, correo;
    Date fechaNacDate;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGeneracion() {
        return generacion;
    }

    public void setGeneracion(String generacion) {
        this.generacion = generacion;
    }

    public Alumno() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getApm() {
        return apm;
    }

    public void setApm(String apm) {
        this.apm = apm;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        
        this.fechaNac = fechaNac;
    }
    public void setFechaNacDate(String fechaNac) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = null;
        try {
            date = sdf1.parse(fechaNac);
        } catch (ParseException ex) {
            Logger.getLogger(AgregarAlumno.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.sql.Date fecha = new java.sql.Date(date.getTime());

        this.fechaNacDate = fecha;
        this.fechaNac = fechaNac;
    }
    
    

    public Date getFechaDate() {
        return this.fechaNacDate;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "Alumno{" + "matricula=" + matricula + ", nombre=" + nombre + ", app=" + app + ", apm=" + apm + ", estatus=" + estatus + ", carrera=" + carrera + ", plan=" + plan + ", grupo=" + grupo + ", semestre=" + semestre + ", fechaNac=" + fechaNac + ", curp=" + curp + ", sexo=" + sexo + ", estado=" + estado + ", municipio=" + municipio + ", cp=" + cp + ", generacion=" + generacion + ", telefono=" + telefono + ", correo=" + correo + ", fechaNacDate=" + fechaNacDate + '}';
    }

}
