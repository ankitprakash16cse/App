package com.erp.collection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class MyPagerAdapter extends PagerAdapter {

    private Context context;
    Globals g = Globals.getInstance(context);
    ArrayList<HashMap> TaskSheetData2 = new ArrayList<HashMap>();
    private LayoutInflater layoutInflater;
    DataBaseOP db = DataBaseOP.getInstance(context);
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recycler;
    private TabLayout tabLayout;
    ViewPager viewPager1;
    HashMap map;

    public MyPagerAdapter(Context context, ArrayList<HashMap> _items) {
        this.context = context;
        this.TaskSheetData2 = _items;
    }





    @Override
    public int getCount() {
        return TaskSheetData2.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.fragment_fragment_1, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_id);
        Button btn_submit = (Button) view.findViewById(R.id.submit_1);
        Button btn_back = (Button) view.findViewById(R.id.back_1);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Task_Name) {
                    ((Task_Name)context).onBackPressed();
                }

            }
        });


        String activity_id=g.TaskSheetData.get(g.ch_pos).get("ACTIVITY_ID").toString();
        String groupid=g.TaskSheetData1.get(position).get("GROUPID").toString();

//        g.check_pos=groupPosition;
//        g.ch_pos=childPosition;

        db.getTaskName(activity_id,groupid);


        recycler = (RecyclerView) view.findViewById(R.id.rv_child);
        recycler.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(context);
        recycler.setLayoutManager(layoutManager);
        recycler.setNestedScrollingEnabled(false);

        recycler.setLayoutManager(new LinearLayoutManager(context));
        ShiftAdapter adapter = new ShiftAdapter(context, g.TaskSheetData2);
        recycler.setAdapter(adapter);
       // notifyDataSetChanged();


        ViewPager vp = (ViewPager) container;
        vp.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }









}
