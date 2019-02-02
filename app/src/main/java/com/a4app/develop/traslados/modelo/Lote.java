package com.a4app.develop.traslados.modelo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Clase que hace referencia al objeto Lote implmenta {@link Parcelable} para poder enviarla entre
 * los Intents
 * @author Yamit Huertas
 * @version 1.0
 */
//Entity(tableName = "lote",primaryKeys = {"numLote","centro","almacen","centro_destino","almacen_destino"})
@Entity(tableName = "lote",primaryKeys = {"numLote","centro","almacen","centro_destino","almacen_destino"})
public class Lote implements Parcelable {
    /**
     * Número de rollo o lote.
     */
    @NonNull
    private String numLote;
    /**
     * Número de pedido.
     */
    private String numPedido;
    /**
     * Posición del pedido.
     */
    private String posPedido;

    /**
     * Centro origen.
     */
    @NonNull
    private String centro;
    /**
     * Centro destino.
     */
    @NonNull
    private String centro_destino;
    /**
     * Almacén origen.
     */
    @NonNull
    private String almacen;
    /**
     * Almacén destino.
     */
    @NonNull
    private String almacen_destino;
    /**
     * Código del material
     */
    private String material;
    /**
     * Peso del lote o rollo.
     */
    private double cantidad;
    /**
     * Unidad de medida del peso.
     */
    private String unidad_medida;
    /**
     * Cliente del lote o rollo.
     */
    private String cliente;
    /**
     * Fecha de salida del rollo.
     */
    private String fecha_salida;
    /**
     * Número de entrega en donde está el rollo.
     */
    private String entrega;
    /**
     * Orden de compra en donde está rollo o lote .
     */
    private String ordenCompra;
    /**
     * Número del transporte en donde se despacho el rollo.
     */
    private String transporte;
    /**
     * Estado del rollo
     */
    private String estado;
    /**
     * Placa del  vehiculo en donde va el lote o rollo.
     */
    private String placa;
    /**
     * fecha en la que se hizo el registro de la salida.
     */
    private String fecha_registro;
    /**
     * Indica si fue despachado.
     */
    private String despa;
    /**
     * Indica si fue recibido
     */
    private String recep;
    /**
     * Número empresa transportadora
     */
    private String transportador;
    @Ignore
    public static final Parcelable.Creator<Lote> CREATOR
            = new Parcelable.Creator<Lote>() {
        public Lote createFromParcel(Parcel in) {
            return new Lote(in);
        }

        public Lote[] newArray(int size) {
            return new Lote[size];
        }
    };
    private Lote (Parcel in){
          numLote = in.readString();
          numPedido = in.readString();
          posPedido = in.readString();
          centro = in.readString();
          centro_destino = in.readString();
          almacen = in.readString();
          almacen_destino = in.readString();
          material = in.readString();
          cantidad  = in.readDouble();
          unidad_medida = in.readString();
          cliente = in.readString();
          fecha_salida = in.readString();
          entrega = in.readString();
          ordenCompra = in.readString();
          transporte = in.readString();
          estado = in.readString();
          placa = in.readString();
          fecha_registro = in.readString();
          despa = in.readString();
          recep = in.readString();
          transportador = in.readString();
    }
    public Lote(){

    }
    public Lote(String  numLote, String numPedido,String posPedido, String centro, String centro_destino,
                String almacen,  String almacen_destino, String material, double cantidad,
                String unidad_medida,  String cliente, String fecha_salida, String entrega,
                String ordenCompra, String transporte, String estado, String placa, String fecha_registro,
                String despa, String recep, String transportador){
        this.numLote = numLote;
        this.numPedido = numPedido;
        this.posPedido = posPedido;
        this.centro = centro;
        this.centro_destino = centro_destino;
        this.almacen = almacen;
        this.almacen_destino = almacen_destino;
        this.material = material;
        this.cantidad = cantidad;
        this.unidad_medida = unidad_medida;
        this.cliente = cliente;
        this.fecha_salida = fecha_salida;
        this.entrega = entrega;
        this.ordenCompra = ordenCompra;
        this.transporte = transporte;
        this.estado = estado;
        this.placa = placa;
        this.fecha_registro = fecha_registro;
        this.despa = despa;
        this.recep = recep;
        this.transportador = transportador;


    }

    public String getNumLote() {
        return numLote;
    }

    public void setNumLote(String numLote) {
        this.numLote = numLote;
    }

    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public String getPosPedido() {
        return posPedido;
    }

    public void setPosPedido(String posPedido) {
        this.posPedido = posPedido;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getCentro_destino() {
        return centro_destino;
    }

    public void setCentro_destino(String centro_destino) {
        this.centro_destino = centro_destino;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getAlmacen_destino() {
        return almacen_destino;
    }

    public void setAlmacen_destino(String almacen_destino) {
        this.almacen_destino = almacen_destino;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(String ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getDespa() {
        return despa;
    }

    public void setDespa(String despa) {
        this.despa = despa;
    }

    public String getRecep() {
        return recep;
    }

    public void setRecep(String recep) {
        this.recep = recep;
    }

    public String getTransportador() {
        return transportador;
    }

    public void setTransportador(String transportador) {
        this.transportador = transportador;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.numLote);
        parcel.writeString(this.numPedido);
        parcel.writeString(this.posPedido);
        parcel.writeString(this.centro);
        parcel.writeString(this.centro_destino);
        parcel.writeString(this.almacen);
        parcel.writeString(this.almacen_destino);
        parcel.writeString(this.material);
        parcel.writeDouble(this.cantidad);
        parcel.writeString(this.unidad_medida);
        parcel.writeString(this.cliente);
        parcel.writeString(this.fecha_salida);
        parcel.writeString(this.entrega);
        parcel.writeString(this.ordenCompra);
        parcel.writeString(this.transporte);
        parcel.writeString(this.estado);
        parcel.writeString(this.placa);
        parcel.writeString(this.fecha_registro);
        parcel.writeString(this.despa);
        parcel.writeString(this.recep);
        parcel.writeString(this.transportador);
    }


}
