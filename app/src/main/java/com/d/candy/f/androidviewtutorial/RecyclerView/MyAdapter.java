package com.d.candy.f.androidviewtutorial.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.candy.f.androidviewtutorial.R;

/**
 * Created by daichi on 7/11/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mText;

        public ViewHolder(View content_root) {
            super(content_root);

            mText = (TextView) content_root.findViewById(R.id.recycler_view_item_text);
        }
    }

    private final String[] mData;
    private final int mNumData;

    public MyAdapter(String[] data, int numData) {
        mData = data;
        mNumData = numData;
    }

    @Override
    public int getItemCount() {
        return mNumData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View content_root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(content_root);
    }

    /**
     * Set data to an ite in this method
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mText.setText(mData[position%mData.length]);
    }
}
