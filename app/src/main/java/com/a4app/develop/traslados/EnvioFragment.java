package com.a4app.develop.traslados;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a4app.develop.traslados.modelo.Lote;
import com.a4app.develop.traslados.modelo.LoteAdapter;
import com.a4app.develop.traslados.modelo.Respuesta;
import com.a4app.develop.traslados.modelo.RollosService;
import com.a4app.develop.traslados.modelo.SwipeToDeleteCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Clase que hereda de {@link Fragment} y representa el frament que envía la etiqueta del rollo.
 * @author Yamit Huertas.
 * @version 1.0
 */
public class EnvioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /**
     * Cadena para obtener los lotes del Inten
     */
    private static final String ARG_PARAM1 = "lotes";
    /**
     * Cadena para saber si fue llamada esta actividade desde la actividad centros
     */
    private static final String ARG_PARAM2 = "CentrosActivity.class";
    /**
     * Table Layout, en donde se muestra el encabezado de la lista, es decir los nombres de las columnas, rollo, material y peso
     */
    private TableLayout tableLayout;
    /**
     * Hace referencia al objeto Vista de la actividad.
     */
  private View vista;
  private OnFragmentInteractionListener mListener;
    /**
     * Hace a la intefaz IlectorActivity utilizada para la comunicación entre Fragments.
     */
  private ILectorActivity mCallback;

    /**
     * Listado de lotes que se estan cargando
     */
   private ArrayList<Lote> lotes;
    /**
     * Recycler View que gestiona la visibilidad de los rollos en en listado.
     */
   private RecyclerView rvRollos;
    /**
     * Hace referencia al adapter que gestiona los objetos lote dentro del listado
     */
   private LoteAdapter adapter;
    /**
     * Hace referencia al LinearLayout que contiene el tab de traslados
     */
   private LinearLayout rolloLayout;
    /**
     * Hace referencia al contexto de la aplicación.
     */
   private Context contexto;
    /**
     * Hace referencia al progressBar que se activa cuando se hace clic en descargar
     */
   private ProgressBar progressBar;

    private Button btonTransportar;

    public EnvioFragment() {
        // Required empty public constructor
    }


    /**
     * Generador de Objetos EnvioFrament.
     */
    public static EnvioFragment newInstance(ArrayList<Lote> lotes, String centros) {
        EnvioFragment fragment = new EnvioFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, lotes);
        args.putString(ARG_PARAM2, centros);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Inicializar el listado de lotes cuando se carga la vista del tab
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lotes = getArguments().getParcelableArrayList(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Inicializar las diferentes variables una vez se carga el fragment.
     * Aquí, se define el listener para el boton transportar, este listener va reaalizar lo siguiente:
     * al presionar el botón de transportar se valida que haya conexión a red.
     * una vez validado lo anterior, se crea un objeto OkHttoClient que controla la conexión HTTP al servicio
     * se definen 3 minutos, de espéra hasta agotar el intento de conexión.
     *
     *Se crea un objeto Retrofit que realiza el llamado al restAPi aquí se define la url de conexión.
     * cuando el restApi responde se utilizan dos metodos del Retrofit para controlar la respuesta que son:
     * onResponse  y onFailure, en el onResponse se maneja la logica para activar la barra de progreso y desactivarla cuando termina de esperar
     * una vez responde se crea un objeto Intent que será el que realiza el llamado a la actividad Respuesta, enviando un listado con las respuestas
     * a mostrar.
     * en el metodo onFailure se muestra un mensaje que indica que hubo problemas de conexión.
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_envio, container, false);
        progressBar = vista.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        //tableLayout = (TableLayout) vista.findViewById(R.id.tablaRollo);
        rvRollos = (RecyclerView) vista.findViewById(R.id.rvTablaRollos);
        rolloLayout = (LinearLayout) vista.findViewById(R.id.tablaRollosLayaout);
        btonTransportar = (Button) vista.findViewById(R.id.btnTransportar);
        btonTransportar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                if(validadConexion()) {

                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(3, TimeUnit.MINUTES)
                            .readTimeout(3, TimeUnit.MINUTES)
                            .writeTimeout(3, TimeUnit.MINUTES)

                            .build();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.36.1.14:8040/apiTraslados/apiTraslados/") //sonda
                            //.baseUrl("http://10.13.2.28:8040/apiTraslados/apiTraslados/") //Nuevo PRD
                            //.baseUrl("http://10.1.2.58:8080/apiTraslados/apiTraslados/") //desarrollo
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RollosService rollosService = retrofit.create(RollosService.class);
                    progressBar.setVisibility(View.VISIBLE);
                    btonTransportar.setEnabled(false);
                   Call<List<Respuesta>> call = rollosService.enviaLotes(lotes);
                   // Tesxprueba
                    call.enqueue(new Callback<List<Respuesta>>() {
                        @Override
                        public void onResponse(Call<List<Respuesta>> call, Response<List<Respuesta>> response) {
                           final ArrayList<Respuesta> respuestas = (ArrayList<Respuesta>) response.body();
                          /*  for (Respuesta a : respuestas
                            ) {
                                Log.i("ApiRestfull", a.getTipo());
                                Log.i("ApiRestfull", a.getMensaje());
                            }*/
                            if(getArguments()!= null) {
                                String texto = getArguments().getString(ARG_PARAM2);
                                if (texto.equalsIgnoreCase("CentrosActivity.class")) {
                                    progressBar.setVisibility(View.GONE);

                                    Intent intent = new Intent(vista.getContext(), MensajesActivity.class);
                                    intent.putParcelableArrayListExtra("respuestas", respuestas);
                                    intent.putExtra("cerrarBdatos",true);
                                    startActivity(intent);

                                }else{
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(vista.getContext(), MensajesActivity.class);
                                    intent.putParcelableArrayListExtra("respuestas", respuestas);
                                    startActivity(intent);
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(vista.getContext(), MensajesActivity.class);
                                intent.putParcelableArrayListExtra("respuestas", respuestas);
                                startActivity(intent);
                            }
                            btonTransportar.setEnabled(true);

                        }

                        @Override
                        public void onFailure(Call<List<Respuesta>> call, Throwable t) {
                            Toast toast = Toast.makeText(contexto, t.toString(), Toast.LENGTH_LONG);
                            toast.show();
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(vista.getContext(), MensajesActivity.class);
                            Respuesta respuesta = new Respuesta("E",t.toString());
                            ArrayList<Respuesta> respuestas = new ArrayList<>();
                            respuestas.add(respuesta);
                            intent.putParcelableArrayListExtra("respuestas", respuestas);
                            startActivity(intent);
                            btonTransportar.setEnabled(true);
                        }
                    });
                }else{
                    Toast toast = Toast.makeText(contexto, "Error de Conexion a red", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
        lotes = new ArrayList<Lote>();
        if (getArguments() != null) {
            lotes = getArguments().getParcelableArrayList(ARG_PARAM1);
            //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
        adapter = new LoteAdapter(lotes);
        // Attach the adapter to the recyclerview to populate items
        rvRollos.setAdapter(adapter);
        // Set layout manager to position the items
        contexto = vista.getContext();
        rvRollos.setLayoutManager(new LinearLayoutManager(contexto));
        enableSwipeToDeleteAndUndo();
        calculaKg();
        cantidadRollos();
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * Este metodo se lanza cuando se adiciona el fragment a la vista principal, al realizar esto
     * se valida que la vista principal haya implmentado la interfaz {@link ILectorActivity}
     * @param context conexto
     */


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILectorActivity) {
            mCallback = (ILectorActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ILectorActivity");
        }
    }

/*
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public boolean PasaLote(Lote lote){

        if(!exiteLote(lote)) {
        populateTable(lote);
            return true;
        }
        return false;

    }

    /**
     * Adiciona el lote que se lee desde la etiqueta al listado de lotes  cargados.
     * @param lote
     */
    public void populateTable(Lote lote){

        // Initialize contacts
           // Create adapter passing in the sample user data

            adapter.addItem(lote, 0);
            calculaKg();
            cantidadRollos();
          mCallback.onLotesActuales(lotes);


    }

    /**
     * Realiza el calculo del total de los Kg cargados
     */
    public void calculaKg(){
        String kg = "";
        double kgd = 0;
        for (Lote lot:lotes
             ) {
            kgd = lot.getCantidad() +kgd;
        }
        kg = String.valueOf(kgd);
        TextView kilosText = (TextView) vista.findViewById(R.id.tvTotalKg);
        kilosText.setText(kg);

    }

    /**
     * Informa la cantidad de rollos en la lista
     */

    public void cantidadRollos(){
        TextView cantiRollos = (TextView) vista.findViewById(R.id.tvCantRollos);
        cantiRollos.setText(String.valueOf(lotes.size()));

    }

    /**
     * se crea un objeto {@link SwipeToDeleteCallback} que controla el gesto de deslizar el dedo de izquierda a derecha sobre la celda a borrar
     * con ellos se borrar el lote al que se le realizó el gesto
     */
    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(contexto) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Lote item = adapter.getLotes().get(position);

                adapter.removeItem(position);
                calculaKg();
                cantidadRollos();
                mCallback.onLotesActuales(lotes);


                Snackbar snackbar = Snackbar
                        .make(rolloLayout, "Lote borrado de la lista.", Snackbar.LENGTH_LONG);
                snackbar.setAction("Deshacer", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(item, position);
                        rvRollos.scrollToPosition(position);
                        calculaKg();
                        cantidadRollos();
                        mCallback.onLotesActuales(lotes);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvRollos);
    }

    /**
     * Valida  si existe el rollo que es pasado por parámetro en la tabla de rollos.
     * @param rollo rollo a evaluar
     * @return True si el rollo a evaluar existe en a tabla, False si no el rollo a evaluar  no está en la tabla  de rollos
     */
    public boolean exiteLote(Lote rollo){
        for (Lote lote : lotes) {
            if( lote.getNumLote().equalsIgnoreCase(rollo.getNumLote())){
                return true;
            }
        }
        return false;
    }

    /**
     * Valida si el lector esta conectado a la red.
     * @return true si está conectado, false si no está conectado
     */
    public boolean validadConexion() {
        ConnectivityManager cm =
                (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
