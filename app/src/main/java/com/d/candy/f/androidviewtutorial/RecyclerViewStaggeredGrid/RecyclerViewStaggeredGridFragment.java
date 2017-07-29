package com.d.candy.f.androidviewtutorial.RecyclerViewStaggeredGrid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.androidviewtutorial.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewStaggeredGridFragment extends Fragment {


    public RecyclerViewStaggeredGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(new StaggeredGridRecyclerViewAdapter(50));
        recyclerView.setAdapter(new TestAdapter(new TestAdapter.Block[]{
                new TestAdapter.Block(0, 1, 0, 1, true, false),
                new TestAdapter.Block(1, 1, 1, 2, true, false),
                new TestAdapter.Block(3, 1, 4, 5, true, false),
                new TestAdapter.Block(4, 3, 5, 8, true, false),
                new TestAdapter.Block(6, 2, 9, 11, true, false),
                new TestAdapter.Block(7, 1, 11, 12, true, false),
                new TestAdapter.Block(8, 3, 12, 15, true, false),

                new TestAdapter.Block(0, 2, 0, 2, false, false),
                new TestAdapter.Block(1, 1, 4, 5, false, false),
                new TestAdapter.Block(2, 2, 5, 7, false, false),
                new TestAdapter.Block(3, 1, 7, 8, false, false),
                new TestAdapter.Block(4, 4, 9, 13, false, false),
                new TestAdapter.Block(5, 1, 13, 14, false, false),
                new TestAdapter.Block(6, 1, 14, 15, false, false),

                new TestAdapter.Block(0, 2, 2, 4, true, true),
                new TestAdapter.Block(1, 1, 8, 9, true, true)
        }));

        return view;
    }

}
