package com.erp.collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.erp.model.User;
import com.google.gson.GsonBuilder;

import android.support.v7.app.AppCompatActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Login extends AppCompatActivity
{

	TextView signup, skip, new_user;
	EditText email_id, pass;
	Button sign;
	Context context;
	SessionManager session;
	AlertDialogManager alert = new AlertDialogManager();
	private Globals g=Globals.getInstance(context);
	private static final int PERMISSION_REQUEST_CODE = 200;

	Spinner printingCenter,branch;
	int _height,_width;
	int edittextheight,edittextwidth,capTextSize,_headerHeight,_textWidth,dropdownwidth,subTextWeidth,dropdownheight;
	CustomSpinerLogin adapter1;
	DataBaseOP db;
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
	protected void onCreate(Bundle savedInstanceState)	
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getSupportActionBar().hide();
		context = Login.this;
		session = new SessionManager(getApplicationContext());
	
	//	g = Globals.getInstance(context);
		db=DataBaseOP.getInstance(context);
		displayConfgh();
		basicSettings();
				
		printingCenter=(Spinner)findViewById(R.id.printing_center);	
		printingCenter.setVisibility(View.GONE);
        printingCenter.setBackgroundResource(R.drawable.boxspinner);
        
        branch=(Spinner)findViewById(R.id.branch);   
        branch.setVisibility(View.GONE);
        branch.setBackgroundResource(R.drawable.boxspinner);	
		
        //db.getLastUpdate(Config.TBL_DATESTATUS);
        
		/*CustomSpinerLogin adapter=new CustomSpinerLogin(context, g.PrintingCenterList,capTextSize,"center");
		printingCenter.setAdapter(adapter);				
		
		printingCenter.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> arg, View arg1,int pos, long arg3) 
			{	
				HashMap mapprintingcenter=(HashMap)arg.getItemAtPosition(pos);
				g.setPrintingCenterCode(mapprintingcenter.get("Pub_cent_Code").toString());
				Branch obj = new Branch(g.getPrintingCenterCode());
				obj.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{					
			}			
		});							        
			
		branch.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> arg, View arg1,int pos, long arg3) 
			{		
				HashMap mapbranch=(HashMap)arg.getItemAtPosition(pos);
				g.setBranchCode(mapbranch.get("BRANCH_CODE").toString());
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{					
			}			
		});	*/
		
		signup = (TextView) findViewById(R.id.signup);		

		
		// -------------Sign in code----------------------------------
		email_id = (EditText) findViewById(R.id.email);
		pass = (EditText) findViewById(R.id.pass);
		sign = (Button) findViewById(R.id.signin);	
				
		pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_DONE) 
		        {
		        	login();
		            return true;
		        }
		        return false;
		    }		
		});
				
		sign.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				if (checkPermission()) {
					login();
				} else {
					requestPermission();
					//  Toast.makeText(getApplicationContext()," Permission requested",Toast.LENGTH_LONG).show();
					if (Permission_flag==true)
					{
						login();
					}
				}

			}
		});
	}

	private void requestPermission() {
		ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
	}

	private boolean checkPermission() {

		int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

		return  result == PackageManager.PERMISSION_GRANTED;

				//&& result1 == PackageManager.PERMISSION_GRANTED

	}
	@Override
	public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults)
	{
		switch (requestCode)
		{
			case PERMISSION_REQUEST_CODE:
				if (grantResults.length > 0) {
					boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
					//boolean cameraAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
					//boolean  read_external_storage= grantResults[3] == PackageManager.PERMISSION_GRANTED;
					//boolean  write_external_storage= grantResults[3] == PackageManager.PERMISSION_GRANTED;

					if (locationAccepted )
						//&& cameraAccepted && read_external_storage && write_external_storage)
						//    Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
						//Toast.makeText(this, "Permission Granted, Now you can access location data and camera.", Toast.LENGTH_SHORT).show();
					{
						Permission_flag = true;
					}
					else {

						Toast.makeText(this, "Permission Denied, You cannot access location data and camera", Toast.LENGTH_SHORT).show();
						//   Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
							if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
								showMessageOKCancel("You need to allow access to both the permissions",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
													requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
															PERMISSION_REQUEST_CODE);
												}
											}
										});
								return;
							}
						}
					}
				}
				break;
		}
	}
	private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
		new android.support.v7.app.AlertDialog.Builder(Login.this)
				.setMessage(message)
				.setPositiveButton("OK", okListener)
				.setNegativeButton("Cancel", null)
				.create()
				.show();
	}
	private void login()
	{
		String username,password;
		 username = email_id.getText().toString().trim();
		 password = pass.getText().toString().trim();
		 
		// g.setBranchCode("");
			 /*if(g.getPrintingCenterCode().equals(""))
			 {
				 Toast.makeText(getApplicationContext(),"Please select your Printing Center!", Toast.LENGTH_LONG).show();
			 }
			 else if(g.getBranchCode().equals(""))
			 {
				 Toast.makeText(getApplicationContext(),"Please select your Branch!", Toast.LENGTH_LONG).show();
			 }					 
			 else*/ 
		 	 if (!username.isEmpty() && !password.isEmpty())
			 {
				 User usrobj=new User();
				 //usrobj.setUser(g.getBranchCode(), username, password);
				 usrobj.setUser("", username, password);
				 new getLoginPage(usrobj).execute();
			 }
			 else
			 {
				Toast.makeText(getApplicationContext(),"Please enter the credentials!", Toast.LENGTH_LONG).show();
			 }
	}
	/*@Override
	public boolean dispatchTouchEvent(MotionEvent ev) 
	{
	    View v = getCurrentFocus();
	    if (v != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && v instanceof EditText && !v.getClass().getName().startsWith("android.webkit."))
	    {
	        int scrcoords[] = new int[2];
	        v.getLocationOnScreen(scrcoords);
	        float x = ev.getRawX() + v.getLeft() - scrcoords[0];
	        float y = ev.getRawY() + v.getTop() - scrcoords[1];
	        if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
	            hideKeyboard(this);
	    }
	    return super.dispatchTouchEvent(ev);
	}

	public static void hideKeyboard(Activity activity)
	{
	    if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) 
	    {
	        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
	    }
	}*/
	
	private boolean isNetworkAvailable()
	{
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (info == null) 
		{
			return false;
		} 
		else 
		{
			if (info.isConnected()) 
			{
				return true;
			} 
			else 
			{
				return false;
			}
		}
	}
	
	private class getLoginPage extends AsyncTask<String, Void, Void>
	{
		private ProgressDialog dia;
		User userCre;
		HttpResponse httpResponse;
		int respcode;
		getLoginPage(User objuser)
		{
			userCre=new User();
			userCre=objuser;
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			dia = ProgressDialog.show(context, "Please wait....","Loading");
		}

		@SuppressWarnings("unused")
		@Override
		protected Void doInBackground(String... arg)
		{
			try
			{
				 if(isNetworkAvailable())
				 {
					String ss=new GsonBuilder().create().toJson(userCre);

					 HttpPost postRequest = new HttpPost(Config.url_login);
					 StringEntity input = new StringEntity(ss);
					 input.setContentType("application/json; charset=UTF-8");
					 input.setContentEncoding("UTF-8");
					 postRequest.setHeader("Content-Type","application/json; charset=UTF-8");
					 postRequest.setEntity(input);
					 HttpClient client = new DefaultHttpClient();
					 HttpResponse responsedata = client.execute(postRequest);
					 ResponseHandler<String> handler = new BasicResponseHandler();
					 String body = handler.handleResponse(responsedata);
					JSONObject json=new JSONObject(body);

					if(json!=null)
					{
						JSONArray result = json.getJSONArray(Config.TAG_LOGIN);
						HashMap usrList;
						g.UserList.clear();
						for (int i = 0; i < result.length(); i++)
						{
							usrList= new HashMap();
							JSONObject c = result.getJSONObject(i);

							//g.encrypt_pass=c.getString("password");
							Iterator iter = c.keys();
							ContentValues cv=new ContentValues();
							while(iter.hasNext())
							{
								String key=(String)iter.next();
								String value="";
								try
								{
									value=c.getString(key);
								}
								catch (Exception e)
								{
									value="";
								}
								if(key.equalsIgnoreCase("RETAINER"))
									g.setBranchCode(value);
								if(key.equalsIgnoreCase("PUB_CENT"))
									g.setPrintingCenterCode(value);
								cv.put(key.toLowerCase(),value);

								usrList.put(key.toUpperCase(),value);
							}
							db.putDropValues(cv, Config.TBL_LOGIN);
							g.UserList.add(usrList);
						}

						/*HashMap map=g.UserList.get(0);
						g.user_name=map.get("USERNAME").toString();

						if (map.get("RETAIN_NAME")!=null) {
							g.retainer_book = map.get("RETAIN_NAME").toString();
						}
						if (map.get("EXEC_NAME")!=null) {
							g.executive_book = map.get("EXEC_NAME").toString();
						}
						if (map.get("MOBILENO")!=null) {
							g.mobile_book = map.get("MOBILENO").toString();
						}
						if (map.get("AGENCY_NAME")!=null) {
							g.agency_data = map.get("AGENCY_NAME").toString();
						}if (map.get("COM_CODE")!=null) {
						g.com_code = map.get("COM_CODE").toString();
					}if (map.get("USERID")!=null) {
						g.user_id = map.get("USERID").toString();
					}
						if (map.get("BRANCH_NM")!=null) {
							g.branch_name = map.get("BRANCH_NM").toString();
						}
						if (map.get("CODE_SUBCODE")!=null) {
							g.agency_code_subcode = map.get("CODE_SUBCODE").toString();
						}
						if (map.get("AGENCY_CODE")!=null) {
							g.agency_code = map.get("AGENCY_CODE").toString();
						}
						if (map.get("ALTERNATE_MOBILE")!=null) {
							g.alternate_mobile = map.get("ALTERNATE_MOBILE").toString();
						}
*/

					}
					else
						db.getLogin(Config.TBL_LOGIN, userCre.getUsername(), userCre.getPassword(), userCre.getBranchCode());
				 }
				 else
					db.getLogin(Config.TBL_LOGIN, userCre.getUsername(), userCre.getPassword(), userCre.getBranchCode());

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result)
		{
			dia.dismiss();
			if(g.UserList.size()>0)
			{
				//setAlarm(context);
				Intent home = new Intent(context, Landing_Page.class);
		        finish();
		        startActivity(home);
			}
			else
			{
				Toast.makeText(getApplicationContext(),"User not available.", Toast.LENGTH_LONG).show();
			}
			super.onPostExecute(result);
		}
	}

	private void setAlarm(Context context) {

		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiverInput.class);
		//startActivity(intent);

		//set alarm at user request time
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		PendingIntent pi = PendingIntent.getBroadcast(context, Config.pend_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//set alarm repeat time
		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),(Config.ALARM_REPEAT*60*1000), pi);

	}

	/*
	private class Branch extends AsyncTask<String, Void, Void> 
	{
		private ProgressDialog dia;		
		private String center;
		boolean ststus;		
		private String dir;
		private String jsonfile;
		private HttpResponse httpResponse;
		int respcode;		
		ContentValues cv;
		
		public Branch(String center)
		{
			this.center=center;
		}
		
		@Override
		protected Void doInBackground(String... arg) 
		{					      
			ststus=isNetworkAvailable();
			if(ststus)
			{				
				try
				{
					ServerSync synkObj=new ServerSync(context);
					httpResponse=synkObj.get(Config.url_Branch+"?Pub_cent_Code="+center);
					HttpEntity httpEntity = httpResponse.getEntity();
					respcode=httpResponse.getStatusLine().getStatusCode();
					String responsedata=EntityUtils.toString(httpEntity);
					JSONObject json=new JSONObject(responsedata);
					if(json!=null)
					{									
						JSONArray result = json.getJSONArray(Config.TAG_LOGIN);
						HashMap branchList;
						g.BranchList.clear();
						for (int i = 0; i < result.length(); i++)
						{
							branchList= new HashMap();
							JSONObject c = result.getJSONObject(i);
							Iterator iter = c.keys();
							cv=new ContentValues();
							while(iter.hasNext())
							{
								String key=(String)iter.next();									
								branchList.put(key.toUpperCase(), c.getString(key));		
								cv.put(key.toUpperCase(), c.getString(key));	
							}											
							g.BranchList.add(branchList);
							db.putDropValues(cv,Config.TBL_BRANCH);
						}
					}					
				}
				catch (Exception e) 
				{					
				}				
			}	
			else
			{
				db.getBranchData(Config.TBL_BRANCH,center);
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) 
		{				
				if(g.BranchList.size()>0)
				{	
					adapter1=new CustomSpinerLogin(context, g.BranchList,capTextSize,"Branch_Name".toUpperCase());
					branch.setAdapter(adapter1);
				}
				else 
				{
					Toast.makeText(getApplicationContext(),"Branch not available.", Toast.LENGTH_LONG).show();					
				}	
			
			super.onPostExecute(result);
		}
		@Override
		protected void onPreExecute() 
		{
		}
	}
		
	

	
	public void setAgency()
	{
		String compcode="";
		boolean ststus=isNetworkAvailable();
		if(ststus)
		{				
			try
			{		
				if(g.UserList.get(0).get("COM_CODE")!=null)
					compcode=g.UserList.get(0).get("COM_CODE").toString();
				else
					compcode="SP001";
				
				Agency obj=new Agency(compcode,g.getPrintingCenterCode(),g.getBranchCode(),"",g.UserList.get(0).get("USERID".toLowerCase()).toString(),"",g.getAgencyDate());
				String ss=new GsonBuilder().create().toJson(obj);
				
				StringEntity stringEntity = new StringEntity(ss,HTTP.UTF_8);
				ServerSync synkObj=new ServerSync(context);
				HttpResponse httpResponse=synkObj.post(stringEntity,Config.url_agency);
				HttpEntity httpEntity = httpResponse.getEntity();
				int respcode=httpResponse.getStatusLine().getStatusCode();
				String responsedata=EntityUtils.toString(httpEntity);
				if(respcode==200)
				{
					if(Config.DROPDOWN)
						getAgencyDataParsingDropDown(responsedata);
					else
						getAgencyDataParsing(responsedata);							
				}							
			}
			catch (Exception e) 
			{	
				e.printStackTrace();
			}				
		}				
	}

	
	private void getAgencyDataParsingDropDown(String responsedata)
	{
		HashMap agencyList=null;
		boolean flag=false;
		Iterator iter=null;
		ContentValues cv=null;
		try
		{			
			JSONObject json=new JSONObject(responsedata);	
			if(json==null)
				flag=true;				
			else
			{									
				JSONArray result = json.getJSONArray(Config.TAG_LOGIN);
				if(result.length()<=0)
					flag=true;		
				else
				{
					g.AgencyList.clear();
					db.putDeleteValues(Config.TBL_AGENCY);
					for (int i = 0; i < result.length(); i++)
					{							
						JSONObject c = result.getJSONObject(i);															
						if(i==0)
						{
							agencyList= new HashMap();
							iter = c.keys();	
							cv=new ContentValues();
							cv.putNull("_id");
							while(iter.hasNext())
							{								
								String key=(String)iter.next();									
								agencyList.put(key,"--Select--");
								cv.put(key,"--Select--");
							}
							db.putDropValues(cv, Config.TBL_AGENCY);
							g.AgencyList.add(agencyList);
						}
						
						agencyList= new HashMap();
						iter = c.keys();
						cv=new ContentValues();
						cv.putNull("_id");
						while(iter.hasNext())
						{								
							String key=(String)iter.next();									
							agencyList.put(key, c.getString(key));									
							cv.put(key, c.getString(key));
						}
						db.putDropValues(cv,Config.TBL_AGENCY);
						g.AgencyList.add(agencyList);
					}
				}					
			}				
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		if(flag)
		{
			db.getinfo("AGENCY",Config.TBL_AGENCY);
		}
	}
	
	private void getAgencyDataParsing(String responsedata)
	{			
		try
		{			
			JSONObject json=new JSONObject(responsedata);							
			if(json!=null)
			{									
				JSONArray result = json.getJSONArray(Config.TAG_LOGIN);
				if(result.length()>0)
				{						
					db.putDeleteValues(Config.TBL_AGENCY);
					for (int i = 0; i < result.length(); i++)
					{							
						JSONObject c = result.getJSONObject(i);																												
						Iterator iter = c.keys();
						ContentValues cv=new ContentValues();//MAIN_AG_CODE //AgencyMaster
						cv.putNull("_id");
						while(iter.hasNext())
						{								
							String key=(String)iter.next();																								
							cv.put(key, c.getString(key));
						}
						db.putDropValues(cv,Config.TBL_AGENCY);						
					}
				}					
			}				
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	*/
	
	

}
