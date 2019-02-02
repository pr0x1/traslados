package com.a4app.develop.traslados;

import com.a4app.develop.traslados.modelo.Lote;

import java.util.ArrayList;

/**
 * Interface que provee  los metodos para la comunicación entre los fragments y  la actividad
 * principal y viceversa
 */
public interface ILectorActivity {
    /**
     * Se llama cuando se crea un Lote y se le hace saber al fragment {@link EnvioFragment}
     * @param lote
     */
    void onLoteCreated(Lote lote);

    /**
     * Notifica a la actividad que contiene los fragments {@link LecturaActivity} el listado de lotes
     * creados
     * @param lotes
     */
    void  onLotesActuales(ArrayList<Lote> lotes);
}
