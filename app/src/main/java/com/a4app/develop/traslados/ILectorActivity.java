package com.a4app.develop.traslados;

import com.a4app.develop.traslados.modelo.Lote;

import java.util.ArrayList;

public interface ILectorActivity {
    void onLoteCreated(Lote lote);
    void  onLotesActuales(ArrayList<Lote> lotes);
}
