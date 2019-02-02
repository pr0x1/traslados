package com.a4app.develop.traslados.modelo;

import android.os.Parcel;
import android.os.Parcelable;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

/**
 * Clase que hace referencia al transportador
 * @author  Yamit Huertas.
 * @version 1.0
 */
@Entity(tableName="transportador",primaryKeys = {"codigo","placa"})
public class Transportador implements Parcelable {
    /**
     * Código de la empresa transportadora
     */
    @NonNull
    private String codigo;
    /**
     * Nombre de la empresa transportadora
     */
    @NonNull
    private String nombre;
    /**
     * Placa del vehiculo
     */
    @NonNull
    private String placa;

    /**
     * Constructor
     */
    public Transportador() {
        // TODO Auto-generated constructor stub

    }

    /**
     * Objeto Parceable
     */
    @Ignore
    public static final Parcelable.Creator<Transportador> CREATOR
            = new Parcelable.Creator<Transportador>() {
        public Transportador createFromParcel(Parcel in) {
            return new Transportador(in);
        }

        public Transportador[] newArray(int size) {
            return new Transportador[size];
        }
    };

    /**
     * Constructor Parceable
     * @param in
     */
    private Transportador(Parcel in){
        codigo = in.readString();
        nombre = in.readString();
        placa = in.readString();
    }

    /**
     * Constructor con parametros de entrada
     * @param codigo
     * @param nombre
     * @param placa
     */
    public Transportador(String codigo, String nombre, String placa) {
        // TODO Auto-generated constructor stub
        this.codigo = codigo;
        this.nombre = nombre;
        this.placa = placa;

    }

    /**
     * Retorna el código del transportador
     * @return
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Cambia el código del transportador por el código pasado por parametro
     * @param codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna el nombre del transportador
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambio el nombre del transportador
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna la placa del vehiculo
     * @return
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Cambia la placa del vehiculo
     * @param placa
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.codigo);
        parcel.writeString(this.nombre);
        parcel.writeString(this.placa);

    }
}
