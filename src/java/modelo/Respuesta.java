/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Hugo Blancas Dominguez
 */
public class Respuesta {

    //Tamaño de los campos de las respuestas
    public int tamOpcAbierta=600;
    //Datos unicos de las Respuestas
    private int id_encuestas, id_preguntas, num_control;
    private String date;
    //Opcion seleccionada
    public int[] respuestas;
    public String[] respuestasS;
    //En caso de ser una pregunta abierta
    private String opcAbierta;
    //Tipo
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Respuesta() {
respuestas = new int[9];
    }

    public Respuesta(int id_encuestas, int id_preguntas, int num_control, String date) {
        this.id_encuestas = id_encuestas;
        this.id_preguntas = id_preguntas;
        this.num_control = num_control;
        this.date = date;
        respuestas = new int[9];
    }
    public Respuesta(int id_encuestas, int id_preguntas, String date) {
        this.id_encuestas = id_encuestas;
        this.id_preguntas = id_preguntas;
        this.date = date;
        respuestas = new int[9];
    }

    public int getId_encuestas() {
        return id_encuestas;
    }

    public void setId_encuestas(int id_encuestas) {
        this.id_encuestas = id_encuestas;
    }

    public int getId_preguntas() {
        return id_preguntas;
    }

    public void setId_preguntas(int id_preguntas) {
        this.id_preguntas = id_preguntas;
    }

    public int getNum_control() {
        return num_control;
    }

    public void setNum_control(int num_control) {
        this.num_control = num_control;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpcAbierta() {
        return opcAbierta;
    }

    public void setOpcAbierta(String opcAbierta) {
        this.opcAbierta = opcAbierta;
    }

    @Override
    public String toString() {
        String tempo = "<br>Respuesta " + id_encuestas + id_preguntas + ""
                + "<br>No. Control: " + num_control
                + "<br>Tipo: " + tipo
                + "<br>Fecha: " + date;

        if (tipo.compareTo("Abierta") == 0) {
            tempo += "<br>Resp: " + opcAbierta;
        } else {
            for (int c = 0; c < respuestas.length; c++) {
                tempo += "<br>Resp" + (c + 1) + " :" + respuestas[c];
            }

        }

        return tempo;

        /*
        id_encuestas, id_preguntas, num_control;
    private String date;
    //Opcion seleccionada
    public int []respuestas;
    //En caso de ser una pregunta abierta
    private String opcAbierta;
    //Tipo
    private String tipo;
         */
    }

}
