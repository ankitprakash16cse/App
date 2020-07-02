package com.erp.collection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.erp.TaskSheet;
import com.google.api.client.http.HttpResponse;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import static com.erp.collection.Config.TAG_LOGIN;

public class Landing_Page extends BaseActivity {

    GridView gv1;
    Context context;
     Globals g;
    DataBaseOP db = DataBaseOP.getInstance(context);

    RelativeLayout ll;
    int edittextheight, edittextwidth, capTextSize, _headerHeight, _textWidth,dropdownwidth, subTextWeidth,AcTextWeidth;
    int fontsize1 = 14;
    int year, day, month, _height, _width;
    Activity activity;

    Spinner printingCenter,branch;
    int dropdownheight;
    CustomSpinerLogin adapter1;

    Boolean  Permission_flag=false;

    public void displayConfgh()
    {
        Display _display;
        _display=getWindowManager().getDefaultDisplay();
        _height=_display.getHeight();
        _width=_display.getWidth();
    }
    public void basicSettings()
    {
        switch (getResources().getDisplayMetrics().densityDpi)
        {
            case DisplayMetrics.DENSITY_MEDIUM:
                dropdownheight=(int)(_height*0.05);
                capTextSize=(int)(_height*.01);
                break;
            case DisplayMetrics.DENSITY_HIGH:
                dropdownheight=(int)(_height*0.05);
                capTextSize=(int)(_height*.01);
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                dropdownheight=(int)(_height*0.05);
                capTextSize=(int)(_height*.012);
                break;
            case DisplayMetrics.DENSITY_420:
            case 440:
            case DisplayMetrics.DENSITY_XXHIGH:
                dropdownheight=(int)(_height*0.05);
                capTextSize=(int)(_height*.01);
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                dropdownheight=(int)(_height*0.05);
                capTextSize=(int)(_height*.01);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        g = Globals.getInstance(context);
        gv1=(GridView) findViewById(R.id.gview);
        gv1.setAdapter(new ImageAdapter(getApplicationContext()));
     //   getSupportActionBar().hide();



        getLayoutInflater().inflate(R.layout.activity_booking_main, frameLayout);



        activity = Landing_Page.this;
        context = Landing_Page.this;

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
        //moreimg.setImageResource(R.drawable.menuico);
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

        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                new TaskSheetdata().execute();
                // start Intent
            }
        });
    }

    public class TaskSheetdata extends AsyncTask<String, Void, Void> {
        private ProgressDialog dia;
        private String City_code;
        boolean ststus;
        private String dir;
        private String jsonfile;
        private HttpResponse httpResponse;
        int respcode;
        Globals g = Globals.getInstance(context);

        @Override
        protected void onPreExecute() {

            //dia = ProgressDialog.show(context, "Please wait....", "Loading");

            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... arg) {

            setTasksheetdata();


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {

                db.getTasksheetdata();
                Intent intent = new Intent(Landing_Page.this, StartOfShift.class);
//                intent.putExtra("image", logos[position]); // put image data in Intent
                startActivity(intent);
               // dia.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
             //   dia.dismiss();
            }



            super.onPostExecute(result);
        }
    }

    private void setTasksheetdata() {

        org.apache.http.HttpResponse httpResponse;
        int respcode;
        JSONObject c;
        DataBaseOP db = DataBaseOP.getInstance(getApplicationContext());
        boolean ststus = isNetworkAvailable();
        if (ststus) {
            try {
                TaskSheet obj = new TaskSheet(g.UserList.get(0).get("COM_CODE").toString(),"","", "","",
                        "","","","", "","","","",
                        "","", "","","");
                String ss = new GsonBuilder().create().toJson(obj);
                HttpPost postRequest = new HttpPost(Config.url_TaskSheet);
                StringEntity input = new StringEntity(ss);
                input.setContentType("application/json; charset=UTF-8");
                input.setContentEncoding("UTF-8");
                postRequest.setHeader("Content-Type", "application/json; charset=UTF-8");
                postRequest.setEntity(input);
                HttpClient client = new DefaultHttpClient();
                org.apache.http.HttpResponse responsedata = client.execute(postRequest);
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(responsedata);
                respcode = responsedata.getStatusLine().getStatusCode();


                if (respcode == 200)
                {
                    JSONObject json = new JSONObject(body);
                   // HashMap relist;
                    if (json != null) {
                        JSONArray result = json.getJSONArray("Table");
                        int countdata = result.length();
                     //   g.TaskSheetData.clear();
                        db.putDeleteValues( Config.TBL_TaskSheet);

                        for (int i = 0; i < countdata; i++) {
                            c = result.getJSONObject(i);
                            String statusxdaa = " ";
                       //     relist = new HashMap();
                            Iterator iter = c.keys();
                            ContentValues cv=new ContentValues();
                            cv.putNull("_id");
                            while (iter.hasNext()) {
                                String key = (String) iter.next();
                                String value = " ";
                                try {
                                    value = c.getString(key);
                                } catch (Exception e) {
                                    value = " ";
                                }
                                cv.put(key,value);
                              //  relist.put(key, value);
                            }


                            db.putDropValues(cv, Config.TBL_TaskSheet);
                            //g.TaskSheetData.add(relist);
                        }

                       /* if(countdata>0)
                        {
                            db.dateSetting("",g.getOracleDate(), Config.TBL_DATESTATUS,2);
                        }
*/
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//
//        MenuModel selectedItem = (MenuModel)_item.get(pos);
//
//        if (selectedItem.getMenuCaption().equalsIgnoreCase("TOUR PLAN")) {
//
//            new TaskSheetdata().execute();
//            Intent home = new Intent(context, StartOfShift.class);
//            finish();
//            startActivity(home);
//
//        }
//    }

    private boolean isNetworkAvailable() {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            return false;
        } else {
            if (info.isConnected()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
