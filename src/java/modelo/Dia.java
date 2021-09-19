/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 *
 * @author hbdye
 */
public class Dia {

    private int dDia;
    private int idTaller;
    private Time horaIni;
    private Time horaFin;
    private String horaIni_S;
    private String horaFin_S;

    public Dia(String dia) {
        switch (dia) {
            case "Lunes":
                this.dDia = 1;
                break;
            case "Martes":
                this.dDia = 2;
                break;
            case "Miercoles":
                this.dDia = 3;
                break;
            case "Jueves":
                this.dDia = 4;
                break;
            case "Viernes":
                this.dDia = 5;
                break;
            case "Sabado":
                this.dDia = 6;
                break;
            case "Domingo":
                this.dDia = 7;
                break;
        }
    }
    public String getDiaS()
    {   String temp="";
        switch (dDia) {
            case 1:
                temp= "Lunes";
                break;
            case 2:
                temp= "Martes";
                break;
            case 3:
                temp= "Miércoles";
                break;
            case 4:
                temp= "Jueves";
                break;
            case 5:
                temp= "Viernes";
                break;
            case 6:
                temp= "Sábado";
                break;
            case 7:
                temp= "Domingo";
                break;
        }
        return temp;
    }
    public Dia(int dia)
    {
        this.dDia=dia;
    }

    public int getdDia() {
        return dDia;
    }

    public void setdDia(int dDia) {
        this.dDia = dDia;
    }

    public int getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    public String getHoraIni_S() {
        return horaIni_S;
    }

    public void setHoraIni_S(String horaIni_S) {
        this.horaIni_S = horaIni_S;
        try {

            SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format

            java.util.Date d1 = (java.util.Date) format.parse(horaIni_S);

            java.sql.Time temp = new java.sql.Time(d1.getTime());
            this.horaIni = temp;

        } catch (Exception e) {

        }
    }

    public String getHoraFin_S() {
        return horaFin_S;
    }

    public void setHoraFin_S(String horaFin_S) {
        this.horaFin_S = horaFin_S;
        try {

            SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format

            java.util.Date d1 = (java.util.Date) format.parse(horaFin_S);

            java.sql.Time temp = new java.sql.Time(d1.getTime());
            this.horaFin = temp;

        } catch (Exception e) {

        }
    }

    public Time getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(Time horaIni) {
        this.horaIni = horaIni;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
}
