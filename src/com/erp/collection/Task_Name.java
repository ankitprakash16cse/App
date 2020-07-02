package com.erp.collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.erp.collection.BaseActivity.position;

public class Task_Name extends BaseActivity {
    RecyclerView recycler;
    Context context=Task_Name.this;
    Globals g=Globals.getInstance(context);
    Activity activity;
    DataBaseOP db = DataBaseOP.getInstance(context);

    public static FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    ViewPager viewPager;
    private TabLayout tabLayout;
    boolean _areLecturesLoaded = false;


    ArrayList<HashMap> _items;
    LinearLayout sliderDotspanel;
    //Fragment_Adapter fragment_adapter;



    Fragment selected_fragment = null;
    Typeface face;
    RelativeLayout ll, _header, _center;
    //================================================================
    int edittextheight, edittextwidth, capTextSize, _headerHeight, _textWidth,dropdownwidth, subTextWeidth,AcTextWeidth;
    int fontsize1 = 14;
    int year, day, month, _height, _width;
    //This is our viewPager



    public void displayConfgh() {
        Display _display;
        _display = getWindowManager().getDefaultDisplay();
        _height = _display.getHeight();
        _width = _display.getWidth();
    }

    public void basicSettings()
    {
        switch (getResources().getDisplayMetrics().densityDpi)
        {
            case DisplayMetrics.DENSITY_MEDIUM:
                edittextwidth = (int) (_width * .7);
                edittextheight = (int) (_height * .055);
                capTextSize = (int) (_height * .016);
                _headerHeight = (int) (_height * 0.05);
                _textWidth = (int) (_width * 0.15);
                dropdownwidth = (int) (_width * .9);
                subTextWeidth = (int) (_width * .4);
                break;
            case DisplayMetrics.DENSITY_HIGH:
                edittextwidth = (int) (_width * .7);
                edittextheight = (int) (_height * .055);
                capTextSize = (int) (_height * .013);
                _headerHeight = (int) (_height * 0.05);
                _textWidth = (int) (_width * 0.15);
                dropdownwidth = (int) (_width * .9);
                subTextWeidth = (int) (_width * .3);
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                edittextwidth = (int) (_width * .7);
                edittextheight = (int) (_height * .055);
                capTextSize = (int) (_height * .013);
                _headerHeight = (int) (_height * 0.05);
                _textWidth = (int) (_width * 0.35);
                dropdownwidth = (int) (_width * .9);
                subTextWeidth = (int) (_width * .3);
                break;
            case DisplayMetrics.DENSITY_420:
            case 440:
            case DisplayMetrics.DENSITY_XXHIGH:
                edittextwidth = (int) (_width * .5);
                edittextheight = (int) (_height * .055);
                capTextSize = (int) (_height * .009);
                _headerHeight = (int) (_height * 0.04);
                _textWidth = (int) (_width * 0.3);
                dropdownwidth = (int) (_width * .9);
                subTextWeidth = (int) (_width * .2);
                break;
            case DisplayMetrics.DENSITY_560:
            case 540:
            case DisplayMetrics.DENSITY_XXXHIGH:
                edittextwidth = (int) (_width * .7);
                edittextheight = (int) (_height * .055);
                capTextSize = (int) (_height * .009);
                _headerHeight = (int) (_height * 0.05);
                _textWidth = (int) (_width * 0.15);
                dropdownwidth = (int) (_width * .9);
                subTextWeidth = (int) (_width * .3);
                break;
        }
        dropdownwidth = (int) (_width * .8);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_task__name);
        getLayoutInflater().inflate(R.layout.activity_task__name,frameLayout);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);


        //sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        viewPager = (ViewPager) findViewById(R.id.viewPager1);

        boolean _areLecturesLoaded = false;




        // db.getTaskName(activity_id,groupid);
//        db.getTaskName("ACT0000001","GRP0000001");



//        viewPager.setAdapter(fragment_adapter);



        activity = Task_Name.this;
        context = Task_Name.this;

        g.setOTP_ATTEMPTS(0);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Display dis = getWindowManager().getDefaultDisplay();
        displayConfgh();
        basicSettings();

        AcTextWeidth=(int) (_width * .5);
//        GeoLocation myloc=new GeoLocation(context);


        ll = new RelativeLayout(context);
        ll.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setBackgroundColor(Color.parseColor("#1C92DC"));

        ImageView moreimg = new ImageView(this);
        moreimg.setId(100);
        moreimg.setImageResource(R.drawable.menuico);
        RelativeLayout.LayoutParams paramsmenu = new RelativeLayout.LayoutParams((int) (_height * 0.05), (int) (_height * 0.05));
        paramsmenu.setMargins((int) Math.ceil(5 * (metrics.density)), 0, 0, 0);
        paramsmenu.addRule(RelativeLayout.CENTER_VERTICAL);
        moreimg.setLayoutParams(paramsmenu);

        moreimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(mDrawerList))
                {
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
                else
                {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
            }
        });

        ImageView img_logo = new ImageView(this);
        RelativeLayout.LayoutParams menuparam1 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100);
        menuparam1.setMargins(0, 22, 0, 5);
        menuparam1.addRule(RelativeLayout.CENTER_HORIZONTAL, moreimg.getId());
//img_logo.setLayoutParams(menuparam1);
//img_logo.setImageResource(R.drawable.fourclogo);
//ll.addView(img_logo);

        img_logo.setLayoutParams(menuparam1);
        img_logo.setImageResource(R.drawable.sa);
        ll.addView(img_logo);

        ll.addView(moreimg);
        getSupportActionBar().setCustomView(ll);


        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(false); // show or hide the default home
// button
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true); // enable overriding the default
// toolbar layout
        ab.setDisplayShowTitleEnabled(false); // d

        Toolbar parent = (Toolbar) ll.getParent();
        parent.setContentInsetsAbsolute(0, 0);


        //db.getTaskName("ACT0000001","GRP0000001");

        MyPagerAdapter viewPagerAdapter = new MyPagerAdapter(context,g.TaskSheetData1);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager,true);
//        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
//                                              @Override
//                                              public void onPageSelected(int position)
//                                              {
//                                                  String activity_id=g.TaskSheetData.get(g.ch_pos).get("ACTIVITY_ID").toString();
//                                                  String groupid=g.TaskSheetData1.get(position).get("GROUPID").toString();
//
//
//
//                                              }
//                                          }
//
//        );
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        viewPager.setCurrentItem(g.check_pos);





        //pagerIndicator.setViewPager(pager);

    }


    public void onBackPressed() {

// Below code calls on back button clicked so define anything in this will run on
// back button.

//        Button btn_back = (Button) findViewById(R.id.back_1);
//
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        AlertDialog.Builder BackAlertDialog = new AlertDialog.Builder(Task_Name.this);


        BackAlertDialog.setTitle("Activity Exit Alert");

        BackAlertDialog.setMessage("Are you sure want to exit ?");


        BackAlertDialog.setPositiveButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //Cancel alert dialog box .
                        dialog.cancel();
                    }
                });

        BackAlertDialog.setNegativeButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                        //Exit from activity.
                        finish();
                    }
                });

        BackAlertDialog.show();

        return;
    }





}
