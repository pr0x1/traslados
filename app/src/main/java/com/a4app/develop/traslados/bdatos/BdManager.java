package com.a4app.develop.traslados.bdatos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.a4app.develop.traslados.modelo.CentrosAlmacen;
import com.a4app.develop.traslados.modelo.Lote;
import com.a4app.develop.traslados.modelo.Transportador;

/**
 * Clase que gestiona la conexión a la base de datos, utiliza el meoto getDatabase para generar una sola instancia
 * de esta clase
 */
@Database(entities = {Lote.class, CentrosAlmacen.class, Transportador.class}, version = 1)
public abstract class BdManager extends RoomDatabase {
    /**
     * Tabla centroAlmacen
     * @return
     */
    public abstract BdaoCentroAlmacen bdaoCentroAlmacen();

    /**
     * Tabla lote
     * @return
     */
    public abstract BdaoLote bdaoLote();

    /**
     * tabla transportador
     * @return
     */
    public abstract BdaoTransportador bdaoTransportador();

    /**
     * instancia de la base de datos
     */
    private static volatile BdManager INSTANCE;

    /**
     * Metodo estatico que retorna una instancia de la base de datos.
     * @param context
     * @return
     */
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

