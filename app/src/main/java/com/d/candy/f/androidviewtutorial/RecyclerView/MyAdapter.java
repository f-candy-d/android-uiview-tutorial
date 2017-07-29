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

        private final View mDividerTop;
        private final View mDividerBottom;

        public ViewHolder(View itemView) {
            super(itemView);

            mDividerTop = itemView.findViewById(R.id.item_divider_top);
            mDividerBottom = itemView.findViewById(R.id.item_divider_bottom);
        }

        public void bind(final int position) {
            if (position == 0) {
                mDividerTop.setVisibility(View.INVISIBLE);
                mDividerBottom.setVisibility(View.INVISIBLE);
            } else {
                mDividerTop.setVisibility(View.VISIBLE);
                mDividerBottom.setVisibility(View.INVISIBLE);
            }
        }
    }

    private final int mNumData;

    public MyAdapter(int numData) {
        mNumData = numData;
    }

    @Override
    public int getItemCount() {
        return mNumData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Set data to an ite in this method
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }
}
