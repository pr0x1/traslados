package com.a4app.develop.traslados.modelo;

import com.a4app.develop.traslados.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RollosService {

    String API_ROUTE = "tras";
    @POST(API_ROUTE)
    Call<List<Respuesta>> enviaLotes(@Body List<Lote> lotes);

}