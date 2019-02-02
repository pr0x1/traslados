package com.a4app.develop.traslados.modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase que hace referencia a la respuesta del ApiRest que se conecta con SAP
 * @author Yamit Huertas.
 * @version 1.0
 */
public class Respuesta implements Parcelable {
    /**
     * tipo de respuesta,púede ser E Error, S Exito, I información
     */
    private String tipo;
    /**
     * Mensaje de la respuesta
     */
    private String mensaje;
    /**
     * Objeto parceable
     */
    public static final Parcelable.Creator<Respuesta> CREATOR
            = new Parcelable.Creator<Respuesta>() {
        public Respuesta createFromParcel(Parcel in) {
            return new Respuesta(in);
        }

        public Respuesta[] newArray(int size) {
            return new Respuesta[size];
        }
    };

    /**
     * Constructor
     * @param tipo Tipo de respuesta
     * @param mensaje Mensaje de la respuesta
     */
    public Respuesta (String tipo, String mensaje){
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    /**
     * Constructor Parceable
     * @param in
     */
    private Respuesta(Parcel in){
         tipo = in.readString();
         mensaje = in.readString();
    }

    /**
     * Rertorna el tipo del mensaje
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Cambia el tipo de mensaje
     * @param tipo tipo de mensaje
     */

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /**
     * Rertorna el mensaje
     * @return
     */
    public String getMensaje() {
        return mensaje;
    }
    /**
     * Cambia el mensaje de mensaje
     * @param mensaje mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Se sobreescribe el metodo toString
     * @return mensaje
     */
    @Override
    public String toString() {
        String a = this.tipo + ":" +this.mensaje;
        return a;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.tipo);
        parcel.writeString(this.mensaje);
    }
}
