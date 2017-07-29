package com.d.candy.f.androidviewtutorial.SQLite;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.d.candy.f.androidviewtutorial.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputEntityDialogFragment extends DialogFragment {

    public interface OnDataInputListener {
        void onDataInput(final int integer, final String text);
    }

    private EditText mEditText;
    private NumberPicker mPicker;
    private OnDataInputListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_input_entity_dialog, null);

        mEditText = (EditText) view.findViewById(R.id.edit_text_sqlite);

        mPicker = (NumberPicker) view.findViewById(R.id.num_picker_sqlite);
        mPicker.setMinValue(0);
        mPicker.setMaxValue(100);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Add New Entry")
                .setMessage("Input new entry data!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null && !TextUtils.isEmpty(mEditText.getText().toString())) {
                            mListener.onDataInput(mPicker.getValue(), mEditText.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .setView(view)
                .create();
    }

    public void setListener(final OnDataInputListener listener) {
        mListener = listener;
    }
}
