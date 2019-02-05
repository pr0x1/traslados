package com.a4app.develop.traslados.bdatos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;


import com.a4app.develop.traslados.modelo.Lote;

import java.util.List;

/**
 * Inteface que gestiona la tabla lote
 */
@Dao
public interface BdaoLote {
    /**
     * Retorna todos los registros de la tabla lote
     * @return
     */
   @Query("SELECT * FROM lote")
    public List<Lote> getLotes();

    @Query("SELECT COUNT(numLote) FROM lote WHERE numLote = :numerolote")
    public int exiteLote(String numerolote);


    /**
     * Inserta un registro en la tabla lote
     * @param lote
     */
   @Insert
    public void addLotes(Lote lote);

    /**
     * Borra el registro recibido por parametro de la tabla lote
     * @param lote
     */
   @Delete
    public void deleteUsers(Lote lote);

    /**
     * Borra una colección de lotes de la tabla lote
     * @param lotes
     */
   @Delete
    public void deleteUsers(List<Lote> lotes);


}
