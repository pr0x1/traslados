package com.a4app.develop.traslados.modelo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.a4app.develop.traslados.R;

import java.util.List;

public class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    private List<Contact> mContacts;

    // Pass in the contact array into the constructor
    public ContactsAdapter(List<Contact> contacts) {
        setmContacts(contacts);
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(context,contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Contact contact = getmContacts().get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());
        Button button = viewHolder.messageButton;
        button.setText(contact.isOnline() ? "Message" : "Offline");
        button.setEnabled(contact.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return getmContacts().size();
    }

    public List<Contact> getmContacts() {
        return mContacts;
    }

    public void setmContacts(List<Contact> mContacts) {
        this.mContacts = mContacts;
    }
    public void removeItem(int position) {
        mContacts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mContacts.size());
    }

    public void restoreItem(Contact item, int position) {
        mContacts.add(position, item);
        notifyItemInserted(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button messageButton;
        public TextView tvName;
        public TextView tvHometown;
        private Context context;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
        public ViewHolder(Context context, View itemView) {
            super(itemView);
            this.nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            this.messageButton = (Button) itemView.findViewById(R.id.message_button);
            // Store the context
            this.context = context;

            // Attach a click listener to the entire row view
            //itemView.setOnClickListener(this);
        }
    /*    @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Contact contact = getmContacts().get(position) ;
                // We can access the data within the views
           //     Toast.makeText(context, nameTextView.getText(), Toast.LENGTH_SHORT).show();
            }
        }*/

    }

}
