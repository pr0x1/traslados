package com.a4app.develop.traslados.bdatos;

import com.a4app.develop.traslados.modelo.Transportador;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;

/**
 * Interface para gestinar la tabla Transportador
 */
@Dao
public interface BdaoTransportador {

    /**
     * Consulta todos lo registros de la tabla transportador
     * @return
     */
    @Query("SELECT * FROM transportador")
    public Transportador getTransportador();

    /**
     * Adiciona un registro a la tabla transportador
     * @param transportador
     */
    @Insert
    public void addTransportador(Transportador transportador);

    /**
     * Borra un registro de la tabla transportador
     * @param transportador
     */
    @Delete
    public void deleteLote(Transportador transportador);




}
