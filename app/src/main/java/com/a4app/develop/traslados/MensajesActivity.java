package com.a4app.develop.traslados;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.a4app.develop.traslados.bdatos.BdManager;
import com.a4app.develop.traslados.modelo.Respuesta;
import com.a4app.develop.traslados.modelo.RespuestaAdapter;

import java.util.ArrayList;


public class MensajesActivity extends AppCompatActivity {
    private RecyclerView rvRespuestas;
    private ArrayList<Respuesta> respuestas;
    private RespuestaAdapter adapter;
    private boolean cerrarBd;
    private Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_mensajes);
        rvRespuestas = (RecyclerView) findViewById(R.id.rvRespuestas);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMensajes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cargaIntent();
        adapter = new RespuestaAdapter(respuestas);
        // Attach the adapter to the recyclerview to populate items
        rvRespuestas.setAdapter(adapter);
        // Set layout manager to position the items
        rvRespuestas.setLayoutManager(new LinearLayoutManager(this));
        contexto = getBaseContext();

    }

    public void cargaIntent(){
        Intent intent = getIntent();
        if(intent != null) {
            respuestas = intent.getParcelableArrayListExtra("respuestas");
            cerrarBd = intent.getBooleanExtra("cerrarBdatos",false);
            if (respuestas == null) {
                respuestas = new ArrayList<Respuesta>();
                Respuesta error = new Respuesta("I", "No se cargaron respuestas");
                respuestas.add(error);
            }
        }else{
            respuestas = new ArrayList<Respuesta>();
            Respuesta error = new Respuesta("E", "No se cargaron respuestas");
            respuestas.add(error);
        }


    }
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                if(cerrarBd){
                    AsyncTaskCloseBd asyncTaskCloseBd = new AsyncTaskCloseBd();
                    asyncTaskCloseBd.execute();
                }else{
                    Intent i = new Intent(contexto, CentrosActivity.class);
                    startActivity(i);
                }


                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, CentrosActivity.class);
        startActivity(i);

    }
    private class AsyncTaskCloseBd extends AsyncTask<Void, Void, Void> {

        private String resp;
        ProgressDialog progressDialog;
        boolean tieneDAtos = true;

        @Override
        protected Void doInBackground(Void... params) {

            BdManager db = BdManager.getDatabase(contexto);
            db.clearAllTables();
            db.close();
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // execution of result of Long time consuming operation
            Intent i = new Intent(contexto, CentrosActivity.class);
            startActivity(i);

        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(Void... text) {


        }
    }




}
