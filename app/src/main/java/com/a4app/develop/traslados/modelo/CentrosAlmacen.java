package com.a4app.develop.traslados.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class CentrosAlmacen implements Parcelable {
    //private static String CREATOR;
    private String centroOringen;
    private String almacenOrigen;
    private String centroDestino;
    private String almacenDestino;
    public static final Parcelable.Creator<CentrosAlmacen> CREATOR
            = new Parcelable.Creator<CentrosAlmacen>() {
        public CentrosAlmacen createFromParcel(Parcel in) {
            return new CentrosAlmacen(in);
        }

        public CentrosAlmacen[] newArray(int size) {
            return new CentrosAlmacen[size];
        }
    };
    private CentrosAlmacen(Parcel in) {
        centroOringen = in.readString();
        almacenOrigen  = in.readString();
        centroDestino  = in.readString();
        almacenDestino = in.readString();

    }
    public CentrosAlmacen(String centroOringen, String almacenOrigen, String centroDestino, String almacenDestino){
        this.centroOringen = centroOringen;
        this.almacenOrigen = almacenOrigen;
        this.centroDestino = centroDestino;
        this.almacenDestino = almacenDestino;

    }
    public CentrosAlmacen(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.centroOringen);
        parcel.writeString(this.almacenOrigen);
        parcel.writeString(this.centroDestino);
        parcel.writeString(this.almacenDestino);

    }

    public String getCentroOringen() {
        return centroOringen;
    }

    public void setCentroOringen(String centroOringen) {
        this.centroOringen = centroOringen;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getCentroDestino() {
        return centroDestino;
    }

    public void setCentroDestino(String centroDestino) {
        this.centroDestino = centroDestino;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
    }
}
