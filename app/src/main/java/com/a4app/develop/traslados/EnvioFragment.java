package com.a4app.develop.traslados;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.a4app.develop.traslados.modelo.Contact;
import com.a4app.develop.traslados.modelo.ContactsAdapter;
import com.a4app.develop.traslados.modelo.Lote;
import com.a4app.develop.traslados.modelo.LoteAdapter;
import com.a4app.develop.traslados.modelo.SwipeToDeleteCallback;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnvioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnvioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnvioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TableLayout tableLayout;
    private View vista;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



   private ArrayList<Lote> lotes;
   private RecyclerView rvRollos;
   private RecyclerView mAdapter;
   private LoteAdapter adapter;
   private LinearLayout rolloLayout;
   private Context context;

    public EnvioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnvioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnvioFragment newInstance(String param1, String param2) {
        EnvioFragment fragment = new EnvioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_envio, container, false);
        //tableLayout = (TableLayout) vista.findViewById(R.id.tablaRollo);
        rvRollos = (RecyclerView) vista.findViewById(R.id.rvTablaRollos);
        rolloLayout = (LinearLayout) vista.findViewById(R.id.tablaRollosLayaout);
        lotes = new ArrayList<Lote>();
        adapter = new LoteAdapter(lotes);
        // Attach the adapter to the recyclerview to populate items
        rvRollos.setAdapter(adapter);
        // Set layout manager to position the items
        context = vista.getContext();
        rvRollos.setLayoutManager(new LinearLayoutManager(context));
        enableSwipeToDeleteAndUndo();
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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
    public void PasaLote(Lote lote){
        Lote a = lote;
        populateTable(lote);

    }

    public void populateTable(Lote lote){

        // Initialize contacts
           // Create adapter passing in the sample user data
        adapter.addItem(lote,0);
        
        // Attach the adapter to the recyclerview to populate items
      //  rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
       // rvContacts.setLayoutManager(new LinearLayoutManager(this));


        /*final TableLayout tableLayout = (TableLayout) vista.findViewById(R.id.tablaRollo);
        TableRow row = new TableRow(vista.getContext());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
        row.setLayoutParams(lp);
        TableRow.LayoutParams vp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT,1.0f);
        TextView textView = new TextView(vista.getContext());
        textView.setLayoutParams(vp);
        textView.setText(lote.getNumLote());
        row.addView(textView);
        TextView textView2 = new TextView(vista.getContext());
        textView2.setLayoutParams(vp);
        textView2.setText(lote.getMaterial());
        row.addView(textView2);
        TextView textView3 = new TextView(vista.getContext());
        textView3.setLayoutParams(vp);
        textView3.setText(String.valueOf(lote.getCantidad()));
        row.addView(textView3);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow tr1=(TableRow)v;
                for (int i = 0; i < tableLayout.getChildCount(); i++)
                {
                    View row = tableLayout.getChildAt(i);
                    if (row == v)
                    {
                        row.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                    else
                    {
                        //Change this to your normal background color.
                        row.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    }
                }

            }
        });
        tableLayout.addView(row);

*/


    }
    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(context) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Lote item = adapter.getLotes().get(position);

                adapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(rolloLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(item, position);
                        rvRollos.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rvRollos);
    }

}
