package com.a4app.develop.traslados.bdatos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.a4app.develop.traslados.modelo.CentrosAlmacen;

/**
 * Interface que gestina la consultas sobre la tabla centrosAlmacen
 */
@Dao
public interface BdaoCentroAlmacen {
    /**
     * retorna todos los registros de la tabla centrosAlmacen
     * @return
     */

    @Query("SELECT * FROM centrosAlmacen")
    public CentrosAlmacen getCentroAlmacen();

    /**
     * Inserta un registro en la tabla centrosAlmacen
     * @param centro
     */
    @Insert
    public void addCentroAlmacen(CentrosAlmacen centro);

    /**
     * Borra un registro en la tabla centrosAlmacen
     * @param centrosAlmacen
     */
    @Delete
    public void deleteLote(CentrosAlmacen centrosAlmacen);




}
