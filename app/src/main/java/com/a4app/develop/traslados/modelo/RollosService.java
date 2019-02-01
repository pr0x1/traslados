package com.a4app.develop.traslados.modelo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Interface que provee los metodos para conectarse al apiRest
 */
public interface RollosService {
    /**
     * ruta relativa para la conexión al apiRest
     */
    String API_ROUTE = "tras";

    /**
     * Metodo que se conecta al sevice
     * @param lotes recibe un listado de lotes
     * @return Listado de respuestas
     */
    @POST(API_ROUTE)
    Call<List<Respuesta>> enviaLotes(@Body List<Lote> lotes);

}