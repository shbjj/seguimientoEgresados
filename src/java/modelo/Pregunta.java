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
public class Pregunta {
    //Tamaño de campos de las preguntas
    public int tamPregunta=400, opc=150;
    //Datos unicos de las preguntas
    private int id_encuestas, id_preguntas;
    private String pregunta, tipo, obligatoria;
    
    //Respuestas
    private int num_respuestas;
    public String [] respuestas;

    public Pregunta()
    {
        
    }
    public Pregunta(int id_encuestas, int id_preguntas, String pregunta, String tipo, String obligatoria) {
        this.id_encuestas = id_encuestas;
        this.id_preguntas = id_preguntas;
        this.pregunta = pregunta;
        this.tipo = tipo;
        this.obligatoria = obligatoria;
        respuestas= new String[10];
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

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(String obligatoria) {
        this.obligatoria = obligatoria;
    }

    public int getNum_respuestas() {
        return num_respuestas;
    }

    public void setNum_respuestas(int num_respuestas) {
        this.num_respuestas = num_respuestas;
    }

    @Override
    public String toString(){
        String tempo="<br>IdEncuesta "+id_encuestas+" Id_pregunta: "+id_preguntas
                +"<br>Pregunta: "+pregunta
                +"<br>Tipo: "+tipo
                +"<br>Obligatoria: "+obligatoria;
        
        if(tipo.compareTo("Abierta") == 0)
        {
            
        }
        else
        {
            for(int c=0; c<respuestas.length; c++)
            {
               tempo+="<br>Resp: "+(c+1)+" :"+respuestas[c];  
            }
           
        }
        
        return tempo;
        
        /*
        //Datos unicos de las preguntas
    private int id_encuestas, id_preguntas;
    private String pregunta, tipo, obligatoria;
    
    //Respuestas
    private int num_respuestas;
    public String [] respuestas;
        */
    }
}
