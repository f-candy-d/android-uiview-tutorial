package com.d.candy.f.androidviewtutorial.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by daichi on 7/13/17.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySQLiteOpenHelper";

    /**
     * SQL grammars
     */
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String PRIMARY_KEY_OPTION =" PRIMARY KEY";
    private static final String COMMA_SEP = ",";
    // Create a table
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + MyDBContract.FirstTable.TABLE_NAME + " (" +
                    MyDBContract.FirstTable.COLUMN_NAME_ID + INT_TYPE + PRIMARY_KEY_OPTION + COMMA_SEP +
                    MyDBContract.FirstTable.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
                    MyDBContract.FirstTable.COLUMN_NAME_QUANTITY + INT_TYPE + " )";

    public MySQLiteOpenHelper(Context context) {
        super(context, MyDBContract.DATABASE_NAME, null, MyDBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    /**
     * Delete a latest inserted data
     */
    public boolean deleteLatestEntry() {
        // count entries
        String select = "SELECT * FROM "+MyDBContract.FirstTable.TABLE_NAME+";";
        Cursor cursor = getReadableDatabase().rawQuery(select, null);
        int count = cursor.getCount();
        cursor.close();
        if(0<count) {
            // Delete a row
            final String SQL_DELETE_ROW =
                    "DELETE FROM "+MyDBContract.FirstTable.TABLE_NAME +
                            " WHERE "+MyDBContract.FirstTable.COLUMN_NAME_ID+" = "+String.valueOf(count);
            getWritableDatabase().execSQL(SQL_DELETE_ROW);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Insert an entry
     * @param entity Data of an entry which will be inserted
     * @return ID of the inserted entry
     */
    public long insert(ContentValues entity) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(MyDBContract.FirstTable.TABLE_NAME, null, entity);
        db.close();
        return id;
    }

    /**
     * Show all data in the database
     */
    public ArrayList<ContentValues> getAll() {
        ArrayList<ContentValues> values = new ArrayList<>();
        // Select all rows in the database
        String select = "SELECT * FROM "+MyDBContract.FirstTable.TABLE_NAME+";";
        Cursor cursor = getReadableDatabase().rawQuery(select, null);

        boolean isEOF = cursor.moveToFirst();
        while (isEOF) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MyDBContract.FirstTable.COLUMN_NAME_TEXT));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDBContract.FirstTable.COLUMN_NAME_ID));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(MyDBContract.FirstTable.COLUMN_NAME_QUANTITY));

            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDBContract.FirstTable.COLUMN_NAME_TEXT, title);
            contentValues.put(MyDBContract.FirstTable.COLUMN_NAME_QUANTITY, quantity);
            contentValues.put(MyDBContract.FirstTable.COLUMN_NAME_ID, id);
            values.add(contentValues);

            isEOF = cursor.moveToNext();
        }
        cursor.close();
        return values;
    }
}
