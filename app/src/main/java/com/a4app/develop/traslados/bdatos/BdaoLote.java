package com.a4app.develop.traslados.bdatos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;


import com.a4app.develop.traslados.modelo.Lote;

import java.util.List;


@Dao
public interface BdaoLote {
   @Query("SELECT * FROM lote")
    public List<Lote> getLotes();

   @Insert
    public void addLotes(List<Lote> lotes);

   @Delete
    public void deleteUsers(Lote lote);

   @Delete
    public void deleteUsers(List<Lote> lotes);


}
