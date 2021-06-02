package com.storage.mysqlrecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //The SQL database to be used
    SQLiteDatabase PersonDatabase;
    //The adapter to be used
    PersonAdapter PAdapter;
    //All inputs
    EditText firstname, surname, addln1, emailin, datein;
    Button add;
    //Initialize the inputs
    public void inputs(){
        firstname = findViewById(R.id.firstname);
        surname = findViewById(R.id.surname);
        addln1 = findViewById(R.id.addln1);
        emailin = findViewById(R.id.emailin);
        datein = findViewById(R.id.datein);

        add = findViewById(R.id.add);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //The inputs
        inputs();
        //Connects the database to everything, also gets ready for being written into.
        PersonDBHelper dbHelper = new PersonDBHelper(this);
        PersonDatabase = dbHelper.getWritableDatabase();
        //Gets te recyclerview and connects it to the adapter.
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //This triggers getAllPersons function (defined below). It allows for the all the
        //database values to be loaded on the use of the function.
        PAdapter = new PersonAdapter(this, getAllPersons());
        recyclerView.setAdapter(PAdapter);
        //A gesture to handle a swipe feature, where by swiping the recycler view you can delete
        //the entry.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            //This handles the specific deleting of the view and the data contained within.
            //This does it by getting the entry id and setting it to the tag. If you didn't have
            //the swipe then enclosing this in an onClick for a button would give you a delete
            //button.
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deletePerson((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);
        inputs();
    }
    //This is the deletePerson function. This deletes the entry by the id.
    private void deletePerson(long id) {
        PersonDatabase.delete(PersonContract.PersonEntry.TABLE_NAME,
        PersonContract.PersonEntry._ID + "=" + id, null);
        PAdapter.updateCursor(getAllPersons());
    }
    //This gets the inputs and adds them to the database.
    public void add (View view){
        inputs();

        if (firstname.getText().toString().trim().equals("")||
            surname.getText().toString().trim().equals("")||
            addln1.getText().toString().trim().equals("")||
            emailin.getText().toString().trim().equals("")||
            datein.getText().toString().trim().equals("")){
            return;
        }
        //The inputs
        String fname = firstname.getText().toString();
        String lname = surname.getText().toString();
        String add = addln1.getText().toString();
        String email = emailin.getText().toString();
        String date = datein.getText().toString();
        //Matching the inputs to the contract and the table.
        ContentValues cV = new ContentValues();
        cV.put(PersonContract.PersonEntry.FIRST_NAME, fname);
        cV.put(PersonContract.PersonEntry.SURNAME, lname);
        cV.put(PersonContract.PersonEntry.ADDRESS, add);
        cV.put(PersonContract.PersonEntry.EMAIL, email);
        cV.put(PersonContract.PersonEntry.DATE, date);

        PersonDatabase.insert(PersonContract.PersonEntry.TABLE_NAME, null, cV);
        PAdapter.updateCursor(getAllPersons());
        //This clears the text after getting the previous inputs, thus clearing it for new inputs.
        firstname.setText("");
        surname.setText("");
        addln1.setText("");
        emailin.setText("");
        datein.setText("");
    }
    //This is the function that gets all the database entries.
    private Cursor getAllPersons(){
        return PersonDatabase.query(
                PersonContract.PersonEntry.TABLE_NAME,
                null, null, null, null,null,
                null );
    }
}