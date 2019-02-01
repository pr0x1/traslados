package com.a4app.develop.traslados;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a4app.develop.traslados.bdatos.BdManager;
import com.a4app.develop.traslados.modelo.CentrosAlmacen;
import com.a4app.develop.traslados.modelo.Transportador;

import java.util.StringTokenizer;

/**
 * Clase para  el manejo de la actividad TransporteActivity, es la segunda actividad o ventana que se llama
 * en el flujo de la aplicación, aquí se obtiene la información que viene del código QR transportador, es decir
 * código y nombre de empresa transportadora, número de placa
 *
 * @author Yamit Huertas
 * @version 1.0
 */
public class TransporteActivity extends AppCompatActivity {
    /**
     * Texto de entrada del QR
     */
    private EditText textoTransportador;
    /**
     * Texto en donde se muestran los errores.
     */
    private TextView textoCentros;
    /**
     * Contexto de la actividad.
     */
    private Context contexto;
    /**
     * código del transportador.
     */
    private String codigoTransportador;
    /**
     * Nombre del transportador.
     */
    private String nombreTransportador;
    /**
     *Placa del camión que transporta
     */
    private String placa;
    /**
     * Objeto de la clase {@link CentrosAlmacen} que viene compartida desde la actividadad {@link CentrosActivity}
     */
    private CentrosAlmacen centroOrigen;

    /**
     * Inicializa los valores de la clase.
     * Carga la información enviada desde la actividad {@link CentrosActivity} en un {@link Intent}
     * Inicializa el  listener para el evento {@link TextWatcher#onTextChanged(CharSequence, int, int, int)} que se encarga
     * de manejar el cambio de texto cuando se lee la etiqueta y así proceder con la información leida.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);
        contexto = getBaseContext();
        textoTransportador = (EditText) findViewById(R.id.etEmpreTransport);
        textoTransportador.setInputType(InputType.TYPE_NULL);
        textoCentros = (TextView) findViewById(R.id.tvTCentrosLeidos);
        Intent intent = getIntent();
        centroOrigen = intent.getParcelableExtra("centrosAlm");
        textoCentros.setText(centroOrigen.toString());
        textoTransportador.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }
            /**Este evento se lanza cuando el campo textoTransportador se modifica al recibir la lectura del código QR
             * si el texto cumple la validación de los campos, se llama a la siguiente actividad {@link LecturaActivity}
             * si no cumple se muestra en el textView centroMensajes un error
             *
             * @param s Texto del editeText textoTransportador
             */
            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed

                String textoLeido  = s.toString();
                if(!textoLeido.equals("") && !textoLeido.isEmpty() && textoLeido != null) {
                    procesaLecturaTransporte(textoLeido);
                    goLecturaActivity();
                    CharSequence text = "Etiqueta Leida";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(contexto, text, duration);
                    toast.show();
                    textoTransportador.setText("");
                }


            }
        });

    }


    /**
     * Aquí se crea el objeto transportador que contiene la información de la etiqueta de transporte leida,
     * es decir el nombre y código del transportador, la placa. posteriormente se crea un Intent en donde se carga
     * este objeto y nuevamente la información de la primera actividad CentrosActivity para que sea leida por la siguiente acitividad
     * del flujo llamada {@link LecturaActivity}
     */
    public void goLecturaActivity(){
        Intent i = new Intent(contexto, LecturaActivity.class);
        if (validaCampos()) {
            Transportador transportador = new Transportador();
            transportador.setCodigo(getCodigoTransportador());
            transportador.setNombre(getNombreTransportador());
            transportador.setPlaca(getPlaca());
            i.putExtra("transportador", transportador);
            i.putExtra("centrosAlm",centroOrigen);
            startActivity(i);
        } else{
            textoCentros = findViewById(R.id.tvTCentrosLeidos);
            textoCentros.setText("Error al Leer etiqueta");
        }
    }

    /**
     * Procesa la información del QR transportador separandola en tokens, y  utilizando como separador el caracter #,
     * la estructura que lee es la siguiente codigo de la empresa transportadora#código de la emrpresa transportadora#número de placa
     *
     *
     * @param lectura  Texto leido del QR Transportador
     */
    public void procesaLecturaTransporte(String lectura){
        if (lectura != null) {
            StringTokenizer token = new StringTokenizer(lectura, "#");
            String fragmento = "";
            while (token.hasMoreTokens()) {
                // Lee nombre código Transportador
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    setCodigoTransportador(fragmento);
                }
                // Lee nombre Transportador
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    setNombreTransportador(fragmento);
                }
                if (token.hasMoreTokens()) {
                    fragmento = token.nextToken();
                    setPlaca(fragmento);
                }


            }
        }

    }

    /**Se encarga de validar que el QR transportador tenga la información correcta, si la tiene
     * retorna true, caso contrario false
     *
     * @return  true si el QR es correcto, falso si no lo es.
     */

    private boolean validaCampos(){
        if((getCodigoTransportador().equals("")|| getNombreTransportador() == null)){
            return false;
        }

        return true;

    }


    /**
     * retorna el codigo transportador.
     *
     * @return the codigo transportador
     */
    public String getCodigoTransportador() {
        return codigoTransportador;
    }

    /**
     * Modifica el codigo transportador.
     *
     * @param codigoTransportador the codigo transportador
     */
    public void setCodigoTransportador(String codigoTransportador) {
        this.codigoTransportador = codigoTransportador;
    }

    /**
     * Devuelve nombre transportador.
     *
     * @return the nombre transportador
     */
    public String getNombreTransportador() {
        return nombreTransportador;
    }

    /**
     * Cambia el nombre transportador.
     *
     * @param nombreTransportador the nombre transportador
     */
    public void setNombreTransportador(String nombreTransportador) {
        this.nombreTransportador = nombreTransportador;
    }

    /**
     * Devuelve el número de placa.
     *
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Cambia el número de placa.
     *
     * @param placa the placa
     */
    public void setPlaca(String placa) {
        this.placa = placa;
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
