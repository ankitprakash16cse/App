package com.erp.collection;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import com.google.gson.GsonBuilder;


import java.util.HashMap;
import java.util.Iterator;

public class DownloadingIntent extends IntentService 
{	
	Globals g;
	DataBaseOP db;
	Context context;
	public static final int STATUS_FINISHED = 1;
	public DownloadingIntent()
	{		
		super(DownloadingIntent.class.getName());	
	}
		
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		//downloading data from server
		g=Globals.getInstance(getApplicationContext());
		db=DataBaseOP.getInstance(getApplicationContext());
		context=getApplicationContext();
		
	//	setRetainer();
	//	setAgency();

		this.stopSelf();
	}
	
	
	private boolean isNetworkAvailable()
	{
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
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
	
	
	
	
	
	}

	

	


	

	

	
	

