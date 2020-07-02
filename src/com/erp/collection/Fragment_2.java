package com.erp.collection;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_2 extends Fragment {
    RecyclerView recycler;
    Context context;
    DataBaseOP db = DataBaseOP.getInstance(context);
    HashMap map;
    Globals g = Globals.getInstance(context);

    public static Fragment_2 newInstance(int page, String title) {
        Fragment_2 fragmentFirst = new Fragment_2();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment_2, container, false);
        recycler = (RecyclerView) rootView.findViewById(R.id.rv_child);
        recycler.setHasFixedSize(true);
//        recycler.addItemDecoration(new
//                DividerItemDecoration(context,
//                DividerItemDecoration.VERTICAL));
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recycler.setNestedScrollingEnabled(false);
        recycler.setLayoutManager(new LinearLayoutManager(context));

        ShiftAdapter adapter = new ShiftAdapter(getContext(), g.TaskSheetData2);
        recycler.setAdapter(adapter);
        db.getTaskName("ACT0000001","GRP0000003");
        return rootView;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//
//        db.getTaskName("ACT0000001","GRP0000003");
//    }
//

}
