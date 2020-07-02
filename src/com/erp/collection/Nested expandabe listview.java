package com.erp.collection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erp.TaskSheet;
import com.google.api.client.http.HttpResponse;
import com.google.gson.GsonBuilder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class StartOfShift extends BaseActivity {


    Context context;
    Globals g=Globals.getInstance(context);
    Activity activity;
    DataBaseOP db = DataBaseOP.getInstance(context);

    Fragment selected_fragment = null;
    Typeface face;
    RelativeLayout ll, _header, _center;
    //================================================================
    int edittextheight, edittextwidth, capTextSize, _headerHeight, _textWidth,dropdownwidth, subTextWeidth,AcTextWeidth;
    int fontsize1 = 14;
    int year, day, month, _height, _width;
    //This is our viewPager
    private ViewPager viewPager;

    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;



    private FragmentManager mFragmentManager;


    private ExpandableListView expandableListView;



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
        //  setContentView(R.layout.activity_start_of_shift);
        getLayoutInflater().inflate(R.layout.activity_start_of_shift, frameLayout);

        activity = StartOfShift.this;
        context = StartOfShift.this;

        //   g.setOTP_ATTEMPTS(0);
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

        expandableListView = (ExpandableListView) findViewById(R.id.mainList);
        expandableListView.setAdapter(new ParentLevel(this ,g.TaskSheetData));

    }



    public class ParentLevel extends BaseExpandableListAdapter {

        private final ArrayList<HashMap> tasksheetdata;
        private Context context;

        public ParentLevel(Context context, ArrayList<HashMap> taskSheetData) {
            this.context = context;
            this.tasksheetdata=taskSheetData;
        }

        @Override
        public Object getChild(int arg0, int arg1) {
            return arg1;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            g.activity_code=tasksheetdata.get(groupPosition).get("ACTIVITY_ID").toString();
            db.getGroupName(g.activity_code);
            g.ch_pos=groupPosition;

            SecondLevelExpandableListView secondLevelELV = new SecondLevelExpandableListView(StartOfShift.this);
            secondLevelELV.setAdapter(new SecondLevelAdapter(context,g.TaskSheetData1));
            notifyDataSetChanged();
            secondLevelELV.setGroupIndicator(null);



            return secondLevelELV;
            // return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount() {
            return tasksheetdata.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_first, null);






            }


            TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText);
            text.setText(tasksheetdata.get(groupPosition).get("ACTIVITY_NAME").toString());
            g.activity_pos=groupPosition;

            if (isExpanded) {
                text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.svg_arrow_down_filled, 0);
            } else {
                // If group is not expanded then change the text back into normal
                // and change the icon

                text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.svg_arrow_right_filled, 0);
            }

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class SecondLevelExpandableListView extends ExpandableListView {

        public SecondLevelExpandableListView(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public class SecondLevelAdapter extends BaseExpandableListAdapter {

        private final ArrayList<HashMap> tasksheetdata;
        private Context context;

        public SecondLevelAdapter(Context context, ArrayList<HashMap> taskSheetData) {
            this.context = context;
            this.tasksheetdata=taskSheetData;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {


//                new TaskSheetdata().execute();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_second, null);


            }



            TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText2);
            text.setText(tasksheetdata.get(groupPosition).get("GROUP_NAME").toString());

            convertView.setOnClickListener(new View.OnClickListener(){
                                               @Override
                                               public void onClick(View v)
                                               {
                                                   g.check_pos=groupPosition;


                                                   //db.getTaskName(activity_id,groupid);
                                                   startActivity(new Intent(StartOfShift.this,Task_Name.class));
                                               }
                                           }

            );




            return convertView;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupPosition;
        }

        @Override
        public int getGroupCount() {
            return tasksheetdata.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }



        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                //db.getTaskName("ACT0000001","GRP0000001");
////                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////                convertView = inflater.inflate(R.layout.row_third, null);
//
//
////                String activity_id=g.TaskSheetData.get(childPosition).get("ACTIVITY_ID").toString();
////                String groupid=g.TaskSheetData1.get(groupPosition).get("GROUPID").toString();
//
//
//            }





          /*  TextView text = (TextView) convertView.findViewById(R.id.eventsListEventRowText);
            text.setText(g.TaskSheetData2.get(childPosition).get("TASK_NAME").toString());*/
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }



}