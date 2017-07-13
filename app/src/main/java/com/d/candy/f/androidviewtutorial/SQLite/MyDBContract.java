package com.d.candy.f.androidviewtutorial.SQLite;

/**
 * Created by daichi on 7/13/17.
 */

public class MyDBContract {

    // If you change the database scheme, you must increase the database version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDatabase.db";

    private MyDBContract() {}

    /**
     * Inner class that defines the table contents
     */
    public static class FirstTable {
        public static final String TABLE_NAME = "first_table";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
    }
}
