package com.a4app.develop.traslados.bdatos;

import com.a4app.develop.traslados.modelo.CentrosAlmacen;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
@Dao
public interface BdaoCentroAlmacen {

    @Query("SELECT * FROM centrosAlmacen")
    public CentrosAlmacen getCentroAlmacen();

    @Insert
    public void addCentroAlmacen(CentrosAlmacen centro);

    @Delete
    public void deleteLote(CentrosAlmacen centrosAlmacen);




}
