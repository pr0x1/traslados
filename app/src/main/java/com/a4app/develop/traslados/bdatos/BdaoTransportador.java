package com.a4app.develop.traslados.bdatos;

import com.a4app.develop.traslados.modelo.Transportador;

//@Dao
public interface BdaoTransportador {

  //  @Query("SELECT * FROM transportador")
    public Transportador getTransportador();

    //@Insert
    public void addTransportador(Transportador transportador);

    //@Delete
    public void deleteLote(Transportador transportador);




}
