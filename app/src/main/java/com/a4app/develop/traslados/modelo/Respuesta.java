package com.a4app.develop.traslados.modelo;

public class Respuesta {
    private String tipo;
    private String mensaje;


    public Respuesta (String tipo, String mensaje){
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        String a = this.tipo + ":" +this.mensaje;
        return a;
    }
}
