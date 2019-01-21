package com.a4app.develop.traslados.bdatos;

import com.a4app.develop.traslados.modelo.Lote;

import java.util.List;

//@Dao
public interface BdaoLote {
   // @Query("SELECT * FROM lote")
    public List<Lote> getLotes();

   // @Insert
    public void addLote(List<Lote> lotes);

   // @Delete
    public void deleteUsers(Lote lote);

   // @Delete
    public void deleteUsers(List<Lote> lotes);

}
