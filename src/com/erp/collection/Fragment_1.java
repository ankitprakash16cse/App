//package com.erp.collection;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class Fragment_1 extends Fragment {
//
//    RecyclerView recycler;
//    Context context;
//    DataBaseOP db = DataBaseOP.getInstance(context);
//    HashMap map;
//    Globals g = Globals.getInstance(context);
//    private RecyclerView.LayoutManager layoutManager;
//
//    public static Fragment_1 newInstance(int page, String title) {
//        Fragment_1 fragmentFirst = new Fragment_1();
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        fragmentFirst.setArguments(args);
//        return fragmentFirst;
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView =inflater.inflate(R.layout.fragment_fragment_1, container, false);
//        recycler = (RecyclerView) rootView.findViewById(R.id.rv_child);
//        recycler.setHasFixedSize(true);
////        recycler.addItemDecoration(new
////                DividerItemDecoration(context,
////                DividerItemDecoration.VERTICAL));
//        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//
//        layoutManager = new LinearLayoutManager(context);
//        recycler.setLayoutManager(layoutManager);
//        recycler.setNestedScrollingEnabled(false);
//        recycler.setLayoutManager(new LinearLayoutManager(context));
//        db.getTaskName("ACT0000001","GRP0000001");
//        ShiftAdapter adapter = new ShiftAdapter(getContext(), g.TaskSheetData2);
//        recycler.setAdapter(adapter);
//
//
////        ArrayList<HashMap> TaskSheetData = new ArrayList<HashMap>();
////        db.getTaskName("ACT0000001","GRP0000001");
//
//        //ShiftAdapter adapter=new ShiftAdapter(this,g.TaskSheetData2);
//        //recycler.setAdapter(adapter);
//
//        return rootView;
//    }
////    @Override
////    public void onCreate(@Nullable Bundle savedInstanceState){
////        super.onCreate(savedInstanceState);
////
////        db.getTaskName("ACT0000001","GRP0000001");
////    }
//
//}
