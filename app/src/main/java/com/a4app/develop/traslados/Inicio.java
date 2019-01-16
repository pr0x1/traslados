package com.a4app.develop.traslados;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.a4app.develop.traslados.modelo.Profile;
import com.a4app.develop.traslados.modelo.Respuesta;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inicio extends AppCompatActivity {
    ListView list;
    ArrayList<String> titles = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,titles);
        list = findViewById(R.id.list);
        //getPosts();
        getPostsLotes();
        list.setAdapter(arrayAdapter);
    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<Post>> call = postService.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                for(Post post : response.body()) {
                    titles.add(post.getTitle());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            }
        });
    }
    private void getPostsLotes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.1.2.102:8080/apiTraslados/apiTraslados/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        Profile a = new Profile("14609695", "Yamit Alejandro");
        Profile b = new Profile("1130606725", "Diego Alexander Soler");
        profiles.add(a);
        profiles.add(b);
        Call<Respuesta> call = postService.enviaLotes(profiles);

        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                Respuesta a = response.body();
                Log.i("ApiRestfull", a.getTipo());
                Log.i("ApiRestfull", a.getMensaje());

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
            }
        });
    }
}
