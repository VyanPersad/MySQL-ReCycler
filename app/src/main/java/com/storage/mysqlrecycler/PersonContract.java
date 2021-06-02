package com.storage.mysqlrecycler;

import android.provider.BaseColumns;

public class PersonContract {

    public static final class PersonEntry implements BaseColumns{
        public static final String TABLE_NAME = "PersonList";
        public static final String FIRST_NAME = "FirstName";
        public static final String SURNAME = "Surname";
        public static final String ADDRESS = "Address";
        public static final String EMAIL = "Email";
        public static final String DATE = "Date";
    }

    private PersonContract() {
    }
}
