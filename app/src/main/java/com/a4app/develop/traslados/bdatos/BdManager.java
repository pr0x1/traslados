package com.a4app.develop.traslados.bdatos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.a4app.develop.traslados.modelo.CentrosAlmacen;
import com.a4app.develop.traslados.modelo.Lote;
import com.a4app.develop.traslados.modelo.Transportador;

@Database(entities = {Lote.class, CentrosAlmacen.class, Transportador.class}, version = 1)
public abstract class BdManager extends RoomDatabase {
    public abstract BdaoCentroAlmacen bdaoCentroAlmacen();
    public abstract BdaoLote bdaoLote();
    public abstract BdaoTransportador bdaoTransportador();


    private static volatile BdManager INSTANCE;

    public static BdManager getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BdManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BdManager.class, "bdatos")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}

