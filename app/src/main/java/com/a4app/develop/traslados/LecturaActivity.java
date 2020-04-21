package com.a4app.develop.traslados;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.a4app.develop.traslados.bdatos.BdManager;
import com.a4app.develop.traslados.modelo.CentrosAlmacen;
import com.a4app.develop.traslados.modelo.Lote;
import com.a4app.develop.traslados.modelo.TabDetails;
import com.a4app.develop.traslados.modelo.Transportador;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase es la clase pricipal de la ventana de lectura, la ventana de lectura está dividad en dos Tabs, lectura y envio
 * cada uno de estos tabs está representado por un {@link Fragment}, estos Fragments tienen comportamientos independientes de su clase Controladora
 * el fragment para el tab Lectura, se representa mediante la clase {@link LectorFragment} y la clase para el tab envio se llama {@link EnvioFragment}
 *
 * Está clase implementa la interfaz {@link ILectorActivity} que a su vez tiene los metodos onLoteCreate y onLotesActuales, con estos métodos
 * la clase controladora LecturaActivity se encarga de comunicarse con cada unos de los fragments y viceversa.
 *
 * Aquí tambien se define la barra de tareas de la ventana de lectura la cual contiene un boton para regresar al inicio de la aplicación
 * y un menú en donde se encuentra la opción para suspender el transporte e iniciar otro. Esta funcionalidad se require cuando
 * es necesario  pausar el transporte actual para poder ir a realizar otro transporte que saldrá más rápido, estos casos
 * se dan más cuando se trata de transportes a Corame y se necesita luego cargar un Litofan, Solo se puede tener un transporte suspendido
 *
 *
 *
 * @author Yamit Huertas.
 * @version 1.0
 */
public class LecturaActivity extends AppCompatActivity implements ILectorActivity {

    /**
     * EL {@link android.support.v4.view.PagerAdapter} provee
     * fragmentos para cada una de las secciones. Se  usa un herencia del
     * {@link FragmentPagerAdapter} , el cual permite tener las secciones
     * en memoria sabiendo que esto es muy pesado, pero se hace de la mejor formar
     * utilizando   {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     *  {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    /**
     * Layou para la gestión de los Tabs
     */

    private TabLayout tabLayout;
    /**
     * Referencia al objeto centroAlmacen que contiene la información de la primera lectura de QR
     */
    private CentrosAlmacen centrosAlmacen;
    /**
     * Referencia al objeto transportador que se creó despues de la lectura del segundo QR contiene la información
     * del transportador y su placa
     */
    private Transportador transportador;
    /**
     * Colección de los lotes o rollos leidos.
     */
    private ArrayList<Lote> lotess;
    /**
     * variable para saber si se pauso un transporte
     */
    private boolean otroTransporte;
    public static final String LOTE_KEY = "lote_key";
    /**
     * Contiene el contexto de la aplicación.
     */
    private Context contexto;

    /**
     * Inicializa los  atributos de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);
        Intent intent = getIntent();
        centrosAlmacen  = intent.getParcelableExtra("centrosAlm");
        transportador = intent.getParcelableExtra("transportador");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
       // mViewPager.setAdapter(mSectionsPagerAdapter);

       //populateViewPager();

        contexto = getApplicationContext();
        revisaBd();
       //mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      // tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    /**
     * Carga el layout del Menú utilizado para pausar el transporte actual
     * @param menu
     * @return true en todos los casos
     */
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lectura, menu);
        return true;
    }

    /**
     * Listener de la barra de herramientas que permite saber cuando se hizo clic en el boton de regreso o en el boton de pausar
     * el transporte.
     * @param item  item del menu seleccionado
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, CentrosActivity.class);
                startActivity(i);
                return true;
            case R.id.action_settings:
                AsyncTaskOnSaveInstace  task = new AsyncTaskOnSaveInstace();
                task.execute();
                otroTransporte = true;
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    /**
     * Crea los fragmentos y los ingresa dentro del viewPagerAdapter y este a su vez se registra dentro del Tablayout
     */
    private void populateViewPager() {

        TabDetails tab;
        tab = new TabDetails("Lectura", LectorFragment.newInstance(centrosAlmacen,transportador));
        mSectionsPagerAdapter.addFragment(tab);
        tab = new TabDetails("Traslado",new EnvioFragment());
        mSectionsPagerAdapter.addFragment(tab);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);


    }

    /**
     * Metodo para comunicar el fragment de lectura con el fragment de envio, aqui se recibe  cada lote que se lee
     * dentro del fragment lectura y se le notifica al {@link EnvioFragment} para que lo resgistre el el listado de lotes
     * disponibles
     * @param lote
     */
    @Override
    public void onLoteCreated(Lote lote) {
       // The user selected the headline of an article from the HeadlinesFragment
        // Capture the article fragment from the activity layout
        EnvioFragment envioFragment = (EnvioFragment) mSectionsPagerAdapter.getItem(1);
        if(!lote.getCentro_destino().equalsIgnoreCase(centrosAlmacen.getCentroDestino())){
            if(!centrosAlmacen.getCentroDestino().equals("1000") && !centrosAlmacen.getCentroDestino().equals("1111") && !centrosAlmacen.getCentroDestino().equals("2222")
                    && !centrosAlmacen.getCentroDestino().equals("8888")
                    && !centrosAlmacen.getCentroDestino().equals("8889")) {
                Snackbar.make(mViewPager, "Este lote se dirige a otra planta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                if (!envioFragment.PasaLote(lote)) {
                    Snackbar.make(mViewPager, "Lote ya cargado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

        }else {
            if (!envioFragment.PasaLote(lote)) {
                Snackbar.make(mViewPager, "Lote ya cargado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        //private static final String ARG_LAYOUT = "layout";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int layout) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, layout);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Retorna la vista que contiene el fragment
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(getArguments().getInt(ARG_SECTION_NUMBER), container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        /**
         * Lista de Objetos {@link TabDetails} que contiene la información de cada Tab
         */
        private final List<TabDetails> tabs = new ArrayList<>();

        /**
         * Contrusctor
         * @param fm
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Devuelve el fragment para la posición recibida como parámetro
         * @param position
         * @return {@link Fragment}
         */
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return  tabs.get(position).getFragment();
        }

        /**
         * Retorna la cantidad de tabs  cargados
         * @return int Cantidad de tabs
         */
        @Override
        public int getCount() {
            // Show 3 total pages.room vs sqlite
            return tabs.size();
        }

        /**
         * Adiciona un objeto {@link TabDetails} dentro de la colección de tabs
         * @param tab
         */
        private void addFragment(TabDetails tab) {
            tabs.add(tab);
        }

        /**
         * Retorna el texto del tab que se encuentra en la posición enviada como parámetro
         * @param position
         * @return {@link CharSequence} Nombre del tab
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position).getTabName();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       // SharedPreferences preferences = getPreferences(MODE_PRIVATE);
      //  BdManager  db = BdManager.getDatabase(this);
        //BdaoLote bdaoLote = db.bdaoLote();
      if(otroTransporte) {


      }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Intent intent = getIntent();
        String actividadCentros = intent.getStringExtra("centrosAlmacen");
        if (actividadCentros != null){
            if(actividadCentros.equalsIgnoreCase("centroActividad")){

               AsyncTaskOnRestoreInstace taskOnRestoreInstace = new AsyncTaskOnRestoreInstace();
               taskOnRestoreInstace.execute();




            }
        }



    }

    @Override
    public void onLotesActuales(ArrayList<Lote> lotes) {

        lotess = lotes;


    }
    private class AsyncTaskOnSaveInstace extends AsyncTask<Void, Boolean, Boolean> {

        private String resp;
        ProgressDialog progressDialog;
        boolean tieneDAtos = true;

        @Override
        protected Boolean doInBackground(Void... params) {
           BdManager db = BdManager.getDatabase(contexto);
           // db.clearAllTables();
            boolean mismoCentro = false;
            boolean vacio = false;

            ArrayList<Lote> lotesBd = (ArrayList<Lote>) db.bdaoLote().getLotes();
            if(lotesBd.isEmpty()){
                vacio = true;
                for (Lote lot:lotess) {
                        db.bdaoLote().addLotes(lot);
                }
                db.bdaoCentroAlmacen().addCentroAlmacen(centrosAlmacen);
                db.bdaoTransportador().addTransportador(transportador);
                mismoCentro = true;
            }else{
                for (Lote lot:lotess
                ) {
                    mismoCentro = false;
                    for(Lote lotbd: lotesBd){
                        if( lot.getCentro_destino().equalsIgnoreCase(lotbd.getCentro_destino())){
                            int a = db.bdaoLote().exiteLote(lot.getNumLote());
                            if(db.bdaoLote().exiteLote(lot.getNumLote())==0){
                                db.bdaoLote().addLotes(lot);
                            }
                            mismoCentro = true;
                            break;
                        }
                    }


                }
            }
            return mismoCentro;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            // execution of result of Long time consuming operation
            if(result) {
                Log.i("HiloCentros", "Ok");
                Intent a = new Intent(contexto, CentrosActivity.class);
                startActivity(a);
            }else{
                Toast toast = Toast.makeText(contexto, "No se puede pausar este transporte de otra planta", Toast.LENGTH_LONG);
                toast.show();
            }

        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(Boolean... text) {



        }
    }

    private class AsyncTaskOnRestoreInstace extends AsyncTask<Void, Void, Void> {

        private String resp;
        ProgressDialog progressDialog;
        boolean tieneDAtos = true;

        @Override
        protected Void doInBackground(Void... params) {

            BdManager db = BdManager.getDatabase(contexto);
            lotess = (ArrayList<Lote>)db.bdaoLote().getLotes();
            centrosAlmacen = db.bdaoCentroAlmacen().getCentroAlmacen();
            transportador = db.bdaoTransportador().getTransportador();
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // execution of result of Long time consuming operation
            Log.i("HiloCentros", "Ok");
            TabDetails tab;
            tab = new TabDetails("Lectura", LectorFragment.newInstance(centrosAlmacen,transportador));
            mSectionsPagerAdapter.addFragment(tab);
            tab = new TabDetails("Traslado", EnvioFragment.newInstance(lotess,"CentrosActivity.class"));
            mSectionsPagerAdapter.addFragment(tab);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout.setupWithViewPager(mViewPager);

        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(Void... text) {


        }
    }
    public void revisaBd(){
        Intent intent = getIntent();
        String actividadCentros = intent.getStringExtra("centrosAlmacen");
        if (actividadCentros != null){
            if(actividadCentros.equalsIgnoreCase("centroActividad")){
                AsyncTaskOnRestoreInstace taskOnRestoreInstace = new AsyncTaskOnRestoreInstace();
                taskOnRestoreInstace.execute();
            }else{
                populateViewPager();
            }
        }else{
            populateViewPager();
        }
    }




}
