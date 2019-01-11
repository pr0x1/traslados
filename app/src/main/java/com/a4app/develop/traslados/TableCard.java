package com.a4app.develop.traslados;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.a4app.develop.traslados.modelo.Contact;
import com.a4app.develop.traslados.modelo.ContactsAdapter;
import com.a4app.develop.traslados.modelo.SwipeToDeleteCallback;
import com.a4app.develop.traslados.modelo.SwipeDismissRecyclerViewTouchListener;


import java.util.ArrayList;

public class TableCard extends AppCompatActivity {
    ArrayList<Contact> contacts;
    RecyclerView rvContacts;
    RecyclerView mAdapter;
    ContactsAdapter adapter;
   // ArrayList<Contact> stringArrayList = new ArrayList<>();
   ConstraintLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_card);
        // ...
        // Lookup the recyclerview in activity layout
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        coordinatorLayout = (ConstraintLayout) findViewById(R.id.coordinatorLayout);

        // Initialize contacts
        contacts = Contact.createContactsList(20);
        // Create adapter passing in the sample user data
        adapter = new ContactsAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        enableSwipeToDeleteAndUndo();
       /* RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rvContacts.setItemAnimator(itemAnimator);*/
    }
       private void enableSwipeToDeleteAndUndo() {
            SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                    final int position = viewHolder.getAdapterPosition();
                    final Contact item = adapter.getmContacts().get(position);

                    adapter.removeItem(position);


                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            adapter.restoreItem(item, position);
                            rvContacts.scrollToPosition(position);
                        }
                    });

                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();

                }
            };

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
            itemTouchhelper.attachToRecyclerView(rvContacts);
        }




    private void showDialog(String msg){
        AlertDialog alert = new AlertDialog.Builder(TableCard.this)
                .setTitle("alert")
                .setMessage(msg)
                .setCancelable(false)
                .create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
}
