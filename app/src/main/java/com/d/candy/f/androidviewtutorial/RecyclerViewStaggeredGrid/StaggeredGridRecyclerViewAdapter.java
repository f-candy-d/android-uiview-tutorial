package com.d.candy.f.androidviewtutorial.RecyclerViewStaggeredGrid;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.candy.f.androidviewtutorial.R;

/**
 * Created by daichi on 7/27/17.
 */

public class StaggeredGridRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredGridRecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View mRoot;
        private TextView mTextView;

        public ViewHolder(final View view) {
            super(view);

            mRoot = view;
            mTextView = (TextView) view.findViewById(R.id.demo_card_item_text);
        }

        public void bind(final String string) {
            mTextView.setText(string);
        }

        public View getItemView() {
            return mRoot;
        }
    }

    /**
     * View types
     */
    private enum ViewType {
        SPACER_VIEW(0),
        SMALL_FULL_SPAN_VIEW(1),
        FULL_SPAN_VIEW(2),
        SMALL_HALF_SPAN_VIEW(3),
        HALF_SPAN_VIEW(4);

        private final int mID;

        ViewType(int id) {
            mID = id;
        }

        public int toInt() {
            return mID;
        }

        public static ViewType from(final int id) {
            ViewType[] values = ViewType.values();
            for (ViewType val : values) {
                if (val.toInt() == id) {
                    return val;
                }
            }

            throw new IllegalArgumentException(
                    "id=" + String.valueOf(id) + " is not supported");
        }
    }

    private final int mDataNum;
    private ViewGroup mParent = null;
    private int mDefaultHeight;
    private boolean mFlag = false;

    public StaggeredGridRecyclerViewAdapter(final int dataNum) {
        mDataNum = dataNum;
    }

    @Override
    public int getItemCount() {
        return mDataNum;
    }

    @Override
    public int getItemViewType(int position) {
        final int mod5 = position % 5;
        if (mod5 == 0) {
            if (position % 10 == 0) {
                return ViewType.SMALL_FULL_SPAN_VIEW.toInt();
            }
            return ViewType.FULL_SPAN_VIEW.toInt();
        } else if (mod5 == 4) {
            return ViewType.SPACER_VIEW.toInt();
        } else if (mod5 == 3 || mod5 == 2) {
            return ViewType.HALF_SPAN_VIEW.toInt();
        } else {
            // mod5 == 1
            return ViewType.SMALL_HALF_SPAN_VIEW.toInt();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mParent == null) {
            mParent = parent;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewType viewType = ViewType.from(getItemViewType(position));
        manageItemViewSize(viewType, holder.getItemView());
        holder.bind(String.valueOf(position) + " #" + viewType.toString());
    }

    private void manageItemViewSize(final ViewType viewType, final View itemView) {
        if (!mFlag) {
            mDefaultHeight = itemView.getLayoutParams().height;
            mFlag = true;
        }

        StaggeredGridLayoutManager.LayoutParams params =
                (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        switch (viewType) {
            case SMALL_FULL_SPAN_VIEW: {
                params.height = mDefaultHeight / 2;
            }

            case FULL_SPAN_VIEW: {
                itemView.setVisibility(View.VISIBLE);
                params.setFullSpan(true);
                break;
            }

            case SPACER_VIEW: {
                itemView.setVisibility(View.INVISIBLE);
                params.setFullSpan(false);
                params.height = mDefaultHeight / 2;
                break;
            }

            case HALF_SPAN_VIEW: {
                itemView.setVisibility(View.VISIBLE);
                params.setFullSpan(false);
                params.height = mDefaultHeight + mDefaultHeight / 2;
                break;
            }

            case SMALL_HALF_SPAN_VIEW: {
                itemView.setVisibility(View.VISIBLE);
                params.setFullSpan(false);
                params.height = mDefaultHeight / 2;
                break;
            }
        }

        itemView.setLayoutParams(params);
        StaggeredGridLayoutManager manager =
                (StaggeredGridLayoutManager) ((RecyclerView) mParent).getLayoutManager();
        manager.invalidateSpanAssignments();
    }
}
