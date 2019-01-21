package com.a4app.develop.traslados.bdatos;

import com.a4app.develop.traslados.modelo.CentrosAlmacen;

//@Dao
public interface BdaoCentroAlmacen {

   // @Query("SELECT * FROM centrosAlmacen")
    public CentrosAlmacen getCentroAlmacen();

  //  @Insert
    public void addCentroAlmacen(CentrosAlmacen centro);

  //  @Delete
    public void deleteLote(CentrosAlmacen centrosAlmacen);




}
