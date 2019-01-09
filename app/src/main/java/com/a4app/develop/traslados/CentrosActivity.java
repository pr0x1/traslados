package com.a4app.develop.traslados;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CentrosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centros);
    }
    public void goCentrosActivity(View vista){
        Intent i = new Intent(this, TableCard.class);
        /*if (texto.equals("") || texto.equals(null))
            i.putExtra("texto", "TEXTO VACÍO");
        else
            i.putExtra("texto", texto);*/
        startActivity(i);
    }
}
