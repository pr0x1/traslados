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
import android.support.v4.app.FragmentTransaction;
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
import com.a4app.develop.traslados.modelo.Transportador;

import java.util.ArrayList;
import java.util.List;

public class LecturaActivity extends AppCompatActivity implements ILectorActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private CentrosAlmacen centrosAlmacen;
    private Transportador transportador;
    private ArrayList<Lote> lotess;
    private boolean otroTransporte;
    public static final String LOTE_KEY = "lote_key";
    private Context contexto;
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


   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lectura, menu);
        return true;
    }

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
     * Creates the fragments and sets it to ViewPager
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

    @Override
    public void onLoteCreated(Lote lote) {
       // The user selected the headline of an article from the HeadlinesFragment
        // Capture the article fragment from the activity layout
        EnvioFragment envioFragment = (EnvioFragment) mSectionsPagerAdapter.getItem(1);
       if(!envioFragment.PasaLote(lote)) {
           Snackbar.make(mViewPager, "Lote ya cargado", Snackbar.LENGTH_LONG)
                   .setAction("Action", null).show();
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
        private final List<TabDetails> tabs = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return  tabs.get(position).getFragment();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.room vs sqlite
            return tabs.size();
        }
        private void addFragment(TabDetails tab) {
            tabs.add(tab);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position).getTabName();
        }
    }

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
    private class AsyncTaskOnSaveInstace extends AsyncTask<Void, Void, Void> {

        private String resp;
        ProgressDialog progressDialog;
        boolean tieneDAtos = true;

        @Override
        protected Void doInBackground(Void... params) {
           BdManager db = BdManager.getDatabase(contexto);
            db.clearAllTables();
            for (Lote lot:lotess
            ) {
                db.bdaoLote().addLotes(lot);

            }
            db.bdaoCentroAlmacen().addCentroAlmacen(centrosAlmacen);
            db.bdaoTransportador().addTransportador(transportador);

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // execution of result of Long time consuming operation
            Log.i("HiloCentros","Ok");
            Intent a = new Intent(contexto, CentrosActivity.class);
            startActivity(a);

        }


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onProgressUpdate(Void... text) {



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
            tab = new TabDetails("Traslado", EnvioFragment.newInstance((lotess),"CentrosActivity.class"));
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
