package com.d.candy.f.androidviewtutorial.RecyclerViewStaggeredGrid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d.candy.f.androidviewtutorial.R;

import java.util.Arrays;

/**
 * Created by daichi on 7/28/17.
 */

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

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

    public static class Block implements Comparable<Block> {
        private final int mPosInColumn;
        private final int mLength;
        private final int mStart;
        private final int mEnd;
        // If this is false, this block is in the right column
        private final boolean mIsLeft;
        private final boolean mIsFullSpan;

        public Block(final int posInColumn, final int length, final int start,
                     final int end, final boolean isLeft, final boolean isFullSpan) {
            mPosInColumn = posInColumn;
            mLength = length;
            mStart = start;
            mEnd = end;
            mIsLeft = isLeft;
            mIsFullSpan = isFullSpan;
        }

        public int getPosInColumn() {
            return mPosInColumn;
        }

        public int getLength() {
            return mLength;
        }

        public int getStart() {
            return mStart;
        }

        public int getEnd() {
            return mEnd;
        }

        public boolean isLeft() {
            return mIsLeft;
        }

        public boolean isFullSpan() {
            return mIsFullSpan;
        }

        @Override
        public int compareTo(@NonNull Block o) {
            final int diff = this.getStart() - o.getStart();
            if (diff == 0) {
                return ((!this.isLeft() && o.isLeft())
                        ? 1
                        : (this.isLeft() && !o.isLeft()) ? -1 : 0);
            }
            return diff;
        }
    }

    /**
     * View types
     */
    private enum ViewType {
        FULL_SPAN_VIEW(0, "FullSpanView"),
        DEFAULT_SPAN_VIEW(1, "DefaultSpanView");

        private final int mID;
        private final String mText;

        ViewType(int id, String text) {
            mID = id;
            mText = text;
        }

        public int toInt() {
            return mID;
        }

        @Override
        public String toString() {
            return mText;
        }

        public static TestAdapter.ViewType from(final int id) {
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

    private ViewGroup mParent = null;
    private int mDefaultHeight;
    private boolean mFlag = false;
    private Block[] mBlocks;

    public TestAdapter(final Block[] blocks) {
        mBlocks = blocks;
        applyRule(mBlocks);
    }

    @Override
    public int getItemCount() {
        return mBlocks.length;
    }

    @Override
    public int getItemViewType(int position) {
        return (mBlocks[position].isFullSpan())
                ? ViewType.FULL_SPAN_VIEW.toInt()
                : ViewType.DEFAULT_SPAN_VIEW.toInt();
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mParent == null) {
            mParent = parent;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, int position) {
        ViewType viewType = ViewType.from(getItemViewType(position));
        manageItemViewSize(viewType, holder.getItemView(), position);
        final String text
                = "Pos=" + String.valueOf(position)
                + ((mBlocks[position].isFullSpan()) ? "::F" : (mBlocks[position].isLeft()) ? "::L" : "::R")
                + String.valueOf(mBlocks[position].getPosInColumn())
                + "::S=" + String.valueOf(mBlocks[position].getStart());
        holder.bind(text);
    }

    private void manageItemViewSize(final ViewType viewType, final View itemView, final int position) {
        if (!mFlag) {
            mDefaultHeight = itemView.getLayoutParams().height;
            mFlag = true;
        }

        StaggeredGridLayoutManager.LayoutParams params =
                (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        switch (viewType) {
            case FULL_SPAN_VIEW: {
                params.setFullSpan(true);
                break;
            }

            case DEFAULT_SPAN_VIEW: {
                params.setFullSpan(false);
                break;
            }
        }

        params.height = mDefaultHeight * mBlocks[position].getLength();

//        int mod3 = position % 3;
//        if (mod3 == 0) {
//            params.height = mDefaultHeight;
//        } else if (mod3 == 1) {
//            params.height = mDefaultHeight + mDefaultHeight / 2;
//        } else {
//            // mod3 == 2
//            params.height = mDefaultHeight / 2;
//        }

        itemView.setLayoutParams(params);
        StaggeredGridLayoutManager manager =
                (StaggeredGridLayoutManager) ((RecyclerView) mParent).getLayoutManager();
        manager.invalidateSpanAssignments();
    }

    private void applyRule(final Block[] blocks) {
        // Check prerequisite 1
        int sumLengthL = 0;
        int sumLengthR = 0;

        for (Block block : blocks) {
            if (block.isFullSpan()) {
                continue;
            }
            if (block.isLeft()) {
                ++sumLengthL;
            } else {
                ++sumLengthR;
            }
        }

        if (sumLengthL != sumLengthR) {
            throw new IllegalArgumentException();
        }

        // Apply order rules
        Arrays.sort(blocks);

        // Check prerequisite 2
        int prevEndL = -1;
        int prevEndR = -1;
        boolean isError = false;

        for (Block block : blocks) {
            if (block.isFullSpan()) {
                if (0 <= prevEndL && 0 <= prevEndR) {
                    isError = !(prevEndL == block.getStart()
                                    && prevEndR == block.getStart());
                }
                prevEndL = prevEndR = block.getEnd();

            } else if (block.isLeft()) {
                if (0 <= prevEndL) {
                    isError = !(prevEndL == block.getStart());
                }
                prevEndL = block.getEnd();

            } else {
                if (0 <= prevEndR) {
                    isError = !(prevEndR == block.getStart());
                }
                prevEndR = block.getEnd();
            }

            if (isError) break;
        }

        if (isError) {
            throw new IllegalArgumentException();
        }
    }
}
