package com.a4app.develop.traslados;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class CentrosActivity extends AppCompatActivity {
    private String centroOringen;
    private String almacenOrigen;
    private String centroDestino;
    private String almacenDestino;
    private Context contexto;
    private EditText textCentros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centros);
        textCentros = (EditText) findViewById(R.id.etCentros);
        textCentros.setInputType(InputType.TYPE_NULL);
        contexto = this.getBaseContext();

        textCentros.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed

                String textoLeido  = s.toString();
                if(!textoLeido.equals("") && !textoLeido.isEmpty() && textoLeido != null) {
                procesaLecturaCentros(textoLeido);
                goCentrosActivity();
                   CharSequence text = "Cambiado!After";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(contexto, text, duration);
                    toast.show();
                    textCentros.setText("");
                }


            }
        });
    }
    public void goCentrosActivity(){
        Intent i = new Intent(contexto, TransporteActivity.class);
        if (validaCampos()) {
            i.putExtra("centroOrigen", getCentroOringen());
            i.putExtra("almacenOrigen", getAlmacenOrigen());
            i.putExtra("centroDestino", getCentroDestino());
            i.putExtra("almacenDestino", getAlmacenDestino());
            startActivity(i);
        } else{
            TextView centroMensaje = findViewById(R.id.tvCentroMensajes);
            centroMensaje.setText("Error al Leer etiqueta");
        }


    }
    public void procesaLecturaCentros(String lectura){
        if (lectura != null) {
            StringTokenizer token = new StringTokenizer(lectura, "#");
            String fragmento = "";
            while (token.hasMoreTokens()) {
                // Lee centro origen;
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    setCentroOringen(fragmento);
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    setAlmacenOrigen(fragmento);
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    setCentroDestino(fragmento);
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    setAlmacenDestino(fragmento);
                }

            }
        }

    }
    private boolean validaCampos(){
        if((getCentroOringen().equals("")|| getAlmacenOrigen() == null)){
            return false;
        }
        if((getCentroDestino().equals("")||getAlmacenDestino() == null)){
            return false;
        }
        return true;

    }

    public String getCentroOringen() {
        return centroOringen;
    }

    public void setCentroOringen(String centroOringen) {
        this.centroOringen = centroOringen;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getCentroDestino() {
        return centroDestino;
    }

    public void setCentroDestino(String centroDestino) {
        this.centroDestino = centroDestino;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
    }
}
