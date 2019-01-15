package com.a4app.develop.traslados;

import com.a4app.develop.traslados.modelo.Profile;
import com.a4app.develop.traslados.modelo.Respuesta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostService {

    String API_ROUTE = "/posts";
    String API_ROUTE2 = "users";

    @GET(API_ROUTE)
    Call< List<Post> > getPost();

    @POST(API_ROUTE2)
    Call<Respuesta> enviaLotes(@Body List<Profile> profiles);

}