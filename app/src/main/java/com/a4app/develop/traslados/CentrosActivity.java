package com.a4app.develop.traslados;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a4app.develop.traslados.bdatos.BdManager;
import com.a4app.develop.traslados.modelo.CentrosAlmacen;

import java.util.StringTokenizer;


/**
 * Clase para  el manejo de la actividad Seleccionar centro y almacenes destino mediante el código QR
 * The type Centros activity.
 * @author Yamit Huertas
 * @version 1.0
 *
 *
 */
public class CentrosActivity extends AppCompatActivity {
    /**
     *Atributo que hace referencia a la clase  {@link CentrosAlmacen} en donde se almacenan
     * los datos de la etiqueta inicial, centro origen, almacen origen, centro destino y almacen destino.
     */
    private CentrosAlmacen centrosAlmacen;
    /**
     * Objeto contexto de la actividad.
     */
    private Context contexto;
    /**
     * Campo de texto mediante el cual se obtiene la información de la etiqueta.
     */
    private EditText textCentros;
    /**
     * contador para saber la cantidad de palabras o tokens leidas en la etiqueta.
     */
    private int contador;
    /**
     *Boton para rediccionar a la actividad {@link LecturaActivity} cuando se haya pausado un transporte.
     */
    private Button otroTrans;

    /**
     * Se inicializan todas las variables y demás atributos, cuando se crea la ventana de la actividad centrosActivity.
     * aquí se inicializa el  listener para el evento {@link TextWatcher#onTextChanged(CharSequence, int, int, int)} que se encarga
     * de manejar el cambio de texto cuando se lee la etiqueta y así proceder con la información leida.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        contador = 0;
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

            /**Este evento se lanza cuando el campo textCentros se modifica al recibir la lectura del código QR
             * si el texto cumple la validación de los campos, se llama a la siguiente actividad {@link TransporteActivity}
             * si no cumple se muestra en el textView centroMensajes un error
             *
             * @param s
             */

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed

                String textoLeido  = s.toString();
                if(!textoLeido.equals("") && !textoLeido.isEmpty() && textoLeido != null) {
                centrosAlmacen =  procesaLecturaCentros(textoLeido);
                goCentrosActivity(centrosAlmacen);
                   CharSequence text = "Etiqueta Leida";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(contexto, text, duration);
                    toast.show();
                    textCentros.setText("");
                }


            }
        });


            otroTrans =  findViewById(R.id.btnOtroTransporte);

            otroTrans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(contexto,LecturaActivity.class);
                    intent.putExtra("centrosAlmacen","centroActividad");
                    startActivity(intent);
                }
            });
            validadInfoBd();



    }

    /**
     * Go centros activity.
     *Este metodo recibe un objeto centrosAlmacen  y lo envia en un Intent para la siguiente actividad, llamada
     * transporteActivity
     * @param centrosAlmacen the centros almacen
     */
    public void goCentrosActivity(CentrosAlmacen centrosAlmacen){
        Intent i = new Intent(contexto, TransporteActivity.class);

        if (validaCampos(centrosAlmacen)) {
            i.putExtra("centrosAlm", centrosAlmacen);
            startActivity(i);
        } else{
            TextView centroMensaje = findViewById(R.id.tvCentroMensajes);
            centroMensaje.setText("Error al Leer etiqueta");
        }


    }

    /**
     * Procesa lectura centros centros almacen.
     *Este metodo recibe el texto leido de la etiqueta QR y lo empieza a dividir en tokes mediante
     * la utilizdad StringTokenizer, cada palabra esta divida con el separador # cada palabra es
     * guardada en un objeto centrosAlmacen y al final retorna dicho objeto
     *
     * @param lectura the lectura
     * @return the centros almacen
     */
    public CentrosAlmacen procesaLecturaCentros(String lectura){
        if (lectura != null) {
            StringTokenizer token = new StringTokenizer(lectura, "#");
            centrosAlmacen = new CentrosAlmacen();
            String fragmento = "";
            contador = 0;
            while (token.hasMoreTokens()) {
                // Lee centro origen;
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    centrosAlmacen.setCentroOringen(fragmento);
                    contador++;
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    centrosAlmacen.setDesCentroOrigen(fragmento);
                    contador++;
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    centrosAlmacen.setAlmacenOrigen(fragmento);
                    contador++;
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    centrosAlmacen.setDesAlmacenOrigen(fragmento);
                    contador++;
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    centrosAlmacen.setCentroDestino(fragmento);
                    contador++;
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    centrosAlmacen.setDesCentroDestino(fragmento);
                    contador++;
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    centrosAlmacen.setAlmacenDestino(fragmento);
                    contador++;
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    centrosAlmacen.setDesAlmacenDestino(fragmento);
                    contador++;
                }


            }
        }
        return centrosAlmacen;

    }

    /**Valida Campos
     * Valida que la etiqueta leida contiene los campos necesarios
     * @param centrosAlmacen
     * @return true si cumple false en caso contrario
     */
    private boolean validaCampos(CentrosAlmacen centrosAlmacen){
        if(centrosAlmacen!=null) {
            if(contador<8){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    /**Valida informaci&oacute;n de la BD.
     * Se crea un objeto AsyncTaskRunner que se encarga de consultar en la base de datos si hay informaci&oacute;n
     * sobre un transporte suspendido si hay informaci&oacute;n activa un boton que se encarga de llevar el flujo
     * de la app hacia la ultima activdad llamada LecturaActivity
     *
     */
    private void validadInfoBd(){
        AsyncTaskRunner tarea = new AsyncTaskRunner();
        tarea.execute();

    }

    /**
     * Clase interna para la conexión con la Base de datos, se utiliza  Room para darle manejo a la Base de datos
     */
    private class AsyncTaskRunner extends AsyncTask<Void, Void, Boolean> {
        /**
         * almacena si la base de datos tiene información o no tiene.
         */
        boolean tieneDAtos = true;

        /**
         * Valida si hay información en la base de datos, si hay retorna true, caso contrario false
         * @param params
         * @return Boolean, Si hay información en la Base de datos retorna true, caso contrario false
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            BdManager db = BdManager.getDatabase(contexto);
            if(db.bdaoLote().getLotes().size() <=0){
                tieneDAtos = false;
                return tieneDAtos;

            }
            if(db.bdaoTransportador().getTransportador() == null){
                tieneDAtos = false;
                return tieneDAtos;

            }
            if(db.bdaoCentroAlmacen().getCentroAlmacen() == null){
                tieneDAtos = false;
                return tieneDAtos;
            }
           return tieneDAtos;
        }

        /**
         * Cuando termina la validación del la información este metodo se encarga de hacer visible
         * el boton otroTrans de la actividad, cuando haya información en la base de datos.
         * @param result
         */
        @Override
        protected void onPostExecute(Boolean result) {
            // execution of result of Long time consuming operation
            Log.i("HiloCentros",result.toString());
            if(result.booleanValue()){
                otroTrans.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... text) {



        }
    }

    /**
     * Este metodo es llamado cuando la actividad o ventana es detenida, esto pasa cuando la actividad ya no es visible para el usuario
     * en este caso se cierra la conexión a la base de datos.
     */
    @Override
    protected void onStop() {
        super.onStop();

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                BdManager db = BdManager.getDatabase(contexto);
                db.close();
                return null;
            }
        };
    }
}
