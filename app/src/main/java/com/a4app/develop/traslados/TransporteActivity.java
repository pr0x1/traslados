package com.a4app.develop.traslados;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TransporteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);
    }

    public void goLecturaActivity(View vista){
        Intent i = new Intent(this, LecturaActivity.class);
        /*if (texto.equals("") || texto.equals(null))
            i.putExtra("texto", "TEXTO VACÍO");
        else
            i.putExtra("texto", texto);*/
        startActivity(i);
    }
    public void goCentrosActivity(View vista){
        Intent i = new Intent(this, CentrosActivity.class);
        /*if (texto.equals("") || texto.equals(null))
            i.putExtra("texto", "TEXTO VACÍO");
        else
            i.putExtra("texto", texto);*/
        startActivity(i);
    }
}
