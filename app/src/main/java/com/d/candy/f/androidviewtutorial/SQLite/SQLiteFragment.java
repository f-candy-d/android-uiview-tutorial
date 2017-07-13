package com.d.candy.f.androidviewtutorial.SQLite;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.d.candy.f.androidviewtutorial.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SQLiteFragment extends Fragment implements View.OnClickListener {

    private EditText mEditText;
    private NumberPicker mNumberPicker;

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
        Button showAllButton = (Button) root.findViewById(R.id.button_sqlite_show_all);
        insertButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        showAllButton.setOnClickListener(this);

        mEditText = (EditText) root.findViewById(R.id.edit_text_sqlite);

        mNumberPicker = (NumberPicker) root.findViewById(R.id.num_picker_sqlite);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(100);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_sqlite_insert: {
                if(!TextUtils.isEmpty(mEditText.getText().toString())) {
                    ContentValues values = new ContentValues();
                    values.put(MyDBContract.FirstTable.COLUMN_NAME_TITLE, mEditText.getText().toString());
                    values.put(MyDBContract.FirstTable.COLUMN_NAME_QUANTITY, mNumberPicker.getValue());
                    MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(getActivity());
                    dbHelper.insert(values);
                }
                break;
            }

            case R.id.button_sqlite_show_all: {
                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(getActivity());
                dbHelper.showAll();
                break;
            }

            case R.id.button_sqlite_delete: {
                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(getActivity());
                dbHelper.delete_latest_entry();
                break;
            }
        }
    }
}
