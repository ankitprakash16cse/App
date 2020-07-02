package com.erp.collection;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Timer;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
//import com.erp.sam.Main2Activity;
import com.google.gson.GsonBuilder;


public class GoogleServices extends Service implements LocationListener
{
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude,longitude;
    LocationManager locationManager;
    Location location;
    Globals g;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    public static String str_receiver = "com.erp.booking.AlaramReceiver";
    Intent mintent;
    Context mContext=GoogleServices.this;
  //  public static String url_LatLang="http://prabhatapp.prabhatkhabar.com/api/LatLang";
    public static String url_LatLang="http://203.101.89.216/samapi/api/LatLang";

    public String test;
    String btry_levl;

    public static String user_id,user_name,mobile_number,remarks,trn_type,trn_id,p_battery_status,p_pextra1,p_pextra2,p_pextra3,p_pextra4,p_pextra5;

    public GoogleServices()
    {
        //fn_getlocation();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
           // btry_level=String.valueOf(level)+"%";
            btry_levl=String.valueOf(level)+"%";
        }
    };

   // @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    public void onCreate() {
        g= Globals.getInstance(getApplicationContext());
        super.onCreate();

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

              NotificationManager mNotificationManager =
                      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                  NotificationChannel channel = new NotificationChannel("default",
                          "Location",
                          NotificationManager.IMPORTANCE_DEFAULT);
                  channel.setDescription("Location_Default");
                  mNotificationManager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(this, "default").build();
         startForeground(1,notification);

        }
    }
    @Override                              //by vishal
    public void onDestroy() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true); //true will remove notification
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        try
        {
            mintent =intent;
            user_id=g.UserList.get(0).get("USERID").toString();
            trn_id="0";//g.getDate(0).replace(":", "").replace("/", "").replace(" ","");
            mobile_number="";
            trn_type="I";
            p_pextra1="";
            p_pextra2="";
            p_pextra3="";
            p_pextra4 ="";
            p_pextra5 ="";
            remarks="";
            user_name="";
//            fn_getlocation();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return Service.START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location)
    {
      /*     String msg = "New Latitude: " + location.getLatitude()
                   + "New Longitude: " + location.getLongitude();
           Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();*/
              fn_getlocation();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
    }

    @Override
    public void onProviderEnabled(String provider)
    {
    }
    @Override
    public void onProviderDisabled(String provider)
    {
    }

    private void fn_getlocation()
    {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable)
        {
        }
        else
        {
            if (isGPSEnable)
            {
                location = null;
                try
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000*60, 0, this); //15
                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                           if (location != null)
                        {
                            Log.e("latitude", location.getLatitude() + "");
                            Log.e("longitude", location.getLongitude() + "");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            fn_update(location);

                          //  Toast.makeText(getApplicationContext(),btry_levl,Toast.LENGTH_LONG).show();

                           // Toast.makeText(getApplicationContext(),"latitude"+latitude+"longitude"+longitude+"Btry"+btry_levl,Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (SecurityException s)
                {
                    s.printStackTrace();
                }
            }
            else if (isNetworkEnable)
            {
                location = null;
                try
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000*60, 0, this); // 15
                    if (locationManager != null)
                    {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null)
                        {
                            Log.e("latitude", location.getLatitude() + "");
                            Log.e("longitude", location.getLongitude() + "");
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            //    Intent intent = new Intent(mContext,Main2Activity.class);
                            //    String lat1=String.valueOf(latitude);
                            //  intent.putExtra("movie_id_key",lat1);
                            //mContext.startActivity(intent);


//                            fn_update(location);
                      //      Toast.makeText(getApplicationContext(),"latitude"+latitude+"longitude"+longitude+"Btry"+btry_levl,Toast.LENGTH_LONG).show();
                           // Toast.makeText(getApplicationContext(),btry_levl,Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch (SecurityException s)
                {
                    s.printStackTrace();
                }
            }
        }
        stopSelf();
    }

    private void fn_update(Location location)
    {
        try
        {
            //retrivePrefrence();
            LATLANG obj=new LATLANG(user_id, user_name, mobile_number,String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()), remarks, trn_type,trn_id,btry_levl,"","","","","");
//            this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            new uploadData(obj).execute();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"error:"+String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
        }
        sendBroadcast(mintent);
    }

    public class uploadData extends AsyncTask<String, Void, Void>
    {
        LATLANG obj;
        uploadData(LATLANG obj1)
        {
            obj=obj1;
        }
        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                String ss=new GsonBuilder().create().toJson(obj);
                StringEntity stringEntity = new StringEntity(ss,HTTP.UTF_8);
                //ServerSync synkObj=new ServerSync(mContext);
                HttpResponse httpResponse=post(stringEntity,url_LatLang);
                HttpEntity httpEntity = httpResponse.getEntity();
                int respcode=httpResponse.getStatusLine().getStatusCode();
                String responsedata=EntityUtils.toString(httpEntity);
                String vkt="200";
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public HttpResponse post( StringEntity stringEntity,String ctrl)throws IOException
    {
        String response="";
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            //Checking http request method type
            HttpPost httpPost = new HttpPost(ctrl);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept-Encoding", "gzip");

            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return httpResponse;
    }

    private int retrivePrefrence()
    {
        int SPS=0;
        SharedPreferences mysharepre=getSharedPreferences("MyPref", mContext.MODE_PRIVATE);
        boolean exists = mysharepre.contains("user_id");
        if(exists)
        {
             /* editor.putString("user_id",g.UserList.get(0).get("USERID").toString());
                    editor.putString("username",g.UserList.get(0).get("USERNAME").toString());
                    editor.putString("comp_code",g.UserList.get(0).get("COMP_CODE").toString());*/
          //  user_id=mysharepre.getString("user_id", "");
            //user_name=mysharepre.getString("username", "");
        }
        return SPS;
    }

    public String getDate(int dy)
    {
        String curdate = "";
        try {

            Calendar nnow = Calendar.getInstance();
            // nnow.before(20);
            nnow.add(Calendar.DATE, dy);
            String day = String.valueOf(nnow.get(Calendar.DATE));
            String month = String.valueOf(nnow.get(Calendar.MONTH) + 1);
            String year = String.valueOf(nnow.get(Calendar.YEAR));
            String hh=String.valueOf(nnow.getTime().getHours());
            String mm=String.valueOf(nnow.getTime().getMinutes());
            String ss=String.valueOf(nnow.getTime().getSeconds());
            if (month.length() == 2) {
            } else {
                month = "0" + month;
            }
            if (day.length() == 2) {
            }
            else {
                day = "0" + day;
            }

            curdate = day+ "/" + month+ "/" +year  + " "+hh+":"+mm+":"+ss;
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return curdate;
    }

    public class LATLANG
    {
        private String p_user_id;
        private String p_user_name;
        private String p_mobile_number;
        private String p_longitude;
        private String p_latitude;
        private String p_remarks;
        private String p_trn_type;
        private String p_trn_id;
        private String p_battery_status;
        private String p_pextra1;
        private String p_pextra2;
        private String p_pextra3;
        private String p_pextra4;
        private String p_pextra5;

        public LATLANG(String p_user_id, String p_user_name, String p_mobile_number, String p_longitude, String p_latitude, String p_remarks, String p_trn_type, String p_trn_id, String p_battery_status, String p_pextra1, String p_pextra2, String p_pextra3, String p_pextra4, String p_pextra5) {
            this.p_user_id = p_user_id;
            this.p_user_name = p_user_name;
            this.p_mobile_number = p_mobile_number;
            this.p_longitude = p_longitude;
            this.p_latitude = p_latitude;
            this.p_remarks = p_remarks;
            this.p_trn_type = p_trn_type;
            this.p_trn_id = p_trn_id;
            this.p_battery_status = p_battery_status;
            this.p_pextra1 = p_pextra1;
            this.p_pextra2 = p_pextra2;
            this.p_pextra3 = p_pextra3;
            this.p_pextra4 = p_pextra4;
            this.p_pextra5 = p_pextra5;
        }
    }
}