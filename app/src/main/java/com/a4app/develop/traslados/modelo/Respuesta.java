package com.a4app.develop.traslados.modelo;

public class Respuesta {
    private String id;
    private String mensaje;


    public Respuesta (String id, String mensaje){
        this.id = id;
        this.mensaje = mensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
