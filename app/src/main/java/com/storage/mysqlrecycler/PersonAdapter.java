package com.storage.mysqlrecycler;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class  PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    Cursor Pcursor;
    Context Pcontext;

    public PersonAdapter(Context context, Cursor cursor){
        Pcursor = cursor;
        Pcontext = context;
    }
    //These components come from the recycled view. These are the ids of the recycled view
    //components.
    public class PersonViewHolder extends RecyclerView.ViewHolder{
        public TextView nameO, surnameO, AddO, emailO, dateO;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            nameO = itemView.findViewById(R.id.nameO);
            surnameO = itemView.findViewById(R.id.surnameO);
            AddO = itemView.findViewById(R.id.AddO);
            emailO = itemView.findViewById(R.id.emailO);
            dateO = itemView.findViewById(R.id.dateO);
        }
    }
    //This inflates the view to be recycled.
    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(Pcontext);
        View view = inflate.inflate(R.layout.cardview, parent, false);
        return new PersonViewHolder(view);
    }
    //This connects the input components to the table/database.
    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        if (!Pcursor.moveToPosition(position)){
            return;
        }
        //These are from the input activity and shows the place in the database/table where they
        //are to go to be stored. This was defined in the Contract.
        String fname = Pcursor.getString(Pcursor.getColumnIndex(PersonContract.PersonEntry.FIRST_NAME));
        String lname = Pcursor.getString(Pcursor.getColumnIndex(PersonContract.PersonEntry.SURNAME));
        String add= Pcursor.getString(Pcursor.getColumnIndex(PersonContract.PersonEntry.ADDRESS));
        String email = Pcursor.getString(Pcursor.getColumnIndex(PersonContract.PersonEntry.EMAIL));
        String date = Pcursor.getString(Pcursor.getColumnIndex(PersonContract.PersonEntry.DATE));
        long id = Pcursor.getLong(Pcursor.getColumnIndex(PersonContract.PersonEntry._ID));
        //This sets, the input values, to the position in the recycled view.
        holder.nameO.setText(fname);
        holder.surnameO.setText(lname);
        holder.AddO.setText(add);
        holder.emailO.setText(email);
        holder.dateO.setText(date);
        //The id won't be visible in the text BUT it can be used to identify the view.
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return Pcursor.getCount();
    }
    //New Cursor to update the database
    public void updateCursor(Cursor newCursor){
        if (Pcursor != null){Pcursor.close();}
        Pcursor = newCursor;
        if (newCursor != null){notifyDataSetChanged();}
    }
}
