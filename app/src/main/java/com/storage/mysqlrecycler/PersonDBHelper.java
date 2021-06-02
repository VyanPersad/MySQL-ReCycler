package com.storage.mysqlrecycler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.storage.mysqlrecycler.PersonContract.*;
//This class helps to define all the specific things of the database that will be saved.
//Effectively it defines the database table, its name and the columns.
public class PersonDBHelper extends SQLiteOpenHelper  {
    public static final String DATABASE_NAME = "PersonList";
    public static final int DATABASE_VERSION = 1;

    public PersonDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final  String SQL_CREATE_TABLE = "CREATE TABLE " +
                PersonEntry.TABLE_NAME + " (" +
                PersonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PersonEntry.FIRST_NAME + " TEXT, " +
                PersonEntry.SURNAME + " TEXT, " +
                PersonEntry.ADDRESS + " TEXT, " +
                PersonEntry.EMAIL + " TEXT, " +
                PersonEntry.DATE + " TEXT" +
                ");";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + PersonEntry.TABLE_NAME);
        onCreate(db);
    }
}
