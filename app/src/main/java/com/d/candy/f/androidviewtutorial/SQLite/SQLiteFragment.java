package com.d.candy.f.androidviewtutorial.SQLite;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.d.candy.f.androidviewtutorial.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SQLiteFragment extends Fragment implements View.OnClickListener {

    private TextView mLogcat;

    public SQLiteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sqlite, container, false);

        Button insertButton = (Button) root.findViewById(R.id.button_sqlite_insert);
        Button deleteButton = (Button) root.findViewById(R.id.button_sqlite_delete);
        insertButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        mLogcat = (TextView) root.findViewById(R.id.text_sqlite_logcat);
        updateLogcat();

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_sqlite_insert: {
                InputEntityDialogFragment dialogFragment = new InputEntityDialogFragment();
                dialogFragment.setListener(new InputEntityDialogFragment.OnDataInputListener() {
                    @Override
                    public void onDataInput(int integer, String text) {
                        ContentValues values = new ContentValues();
                        values.put(MyDBContract.FirstTable.COLUMN_NAME_TEXT, text);
                        values.put(MyDBContract.FirstTable.COLUMN_NAME_QUANTITY, integer);
                        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(getActivity());
                        dbHelper.insert(values);
                        updateLogcat();
                    }
                });
                dialogFragment.show(getActivity().getSupportFragmentManager(), null);

                break;
            }

            case R.id.button_sqlite_delete: {
                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(getActivity());
                boolean result = dbHelper.deleteLatestEntry();
                if (result) {
                    updateLogcat();
                } else {
                    mLogcat.setText("There is no data in the DB!");
                }
                break;
            }
        }
    }

    private void updateLogcat() {
        mLogcat.setText(null);
        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(getActivity());
        for (ContentValues values : dbHelper.getAll()) {
            final int id = values.getAsInteger(MyDBContract.FirstTable.COLUMN_NAME_ID);
            final String text = values.getAsString(MyDBContract.FirstTable.COLUMN_NAME_TEXT);
            final int quantity = values.getAsInteger(MyDBContract.FirstTable.COLUMN_NAME_QUANTITY);

            mLogcat.append("id:::"+String.valueOf(id)+"   text:::"+text+"   quantity:::"+String.valueOf(quantity)+"\n");
        }
    }
}
