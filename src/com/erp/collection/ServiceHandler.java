package com.erp.collection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import android.util.Base64;

public class ServiceHandler 
{
	//static String response = null;
	public final static int GET = 1;
	public final static int POST = 2;
	
	public ServiceHandler() 
	{	
	}
		
	public String encoder(String param)
	{
		byte[] data=null;
		try 
		{
			data = param.getBytes("UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{		
			e.printStackTrace();
		}
		String base64 = Base64.encodeToString(data,Base64.NO_WRAP);
		return base64;
	}
	
    public JSONObject makeServiceCall(String url, int method, HashMap<String, String> params) 
    {
    	String charset = "UTF-8";
        HttpURLConnection conn=null;
        DataOutputStream wr;
        StringBuilder result = null;
        URL urlObj;
        JSONObject jObj = null;
        StringBuilder sbParams;
        String paramsString;
    		sbParams = new StringBuilder();
			int i = 0;
			for (String key : params.keySet()) 
			{
				try 
				{
					if (i != 0)
					{
						sbParams.append("&");
					}
					sbParams.append(key).append("=").append(URLEncoder.encode(params.get(key), charset));
	
				} 
				catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
				i++;
			}

			if (method==POST)
			{
				// request method is POST
				try
				{
					urlObj = new URL(url);					
					conn = (HttpURLConnection) urlObj.openConnection();					
					conn.setDoOutput(true);					
					conn.setRequestMethod("POST");					
					conn.setRequestProperty("Accept-Charset", charset);					
					conn.setReadTimeout(10000);
					conn.setConnectTimeout(15000);					
					conn.connect();					
					paramsString = sbParams.toString();					
					wr = new DataOutputStream(conn.getOutputStream());
					wr.writeBytes(paramsString);
					wr.flush();
					wr.close();
					
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else if(method == GET)
			{
				// request method is GET				
				if (sbParams.length() != 0) 
				{
					url += "?" + sbParams.toString();
				}				
				try
				{
					urlObj = new URL(url);					
					conn = (HttpURLConnection) urlObj.openConnection();					
					conn.setDoOutput(false);					
					conn.setRequestMethod("GET");					
					conn.setRequestProperty("Accept-Charset", charset);					
					conn.setConnectTimeout(15000);					
					conn.connect();				
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}				
			}
			try 
			{
				//Receive the response from the server
				InputStream in = new BufferedInputStream(conn.getInputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				result = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null)
				{
					result.append(line);
				}												
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			conn.disconnect();
			// try parse the string to a JSON object
			try
			{
				jObj = new JSONObject(result.toString());
			} 
			catch (JSONException e)
			{
				//Log.e("JSON Parser", "Error parsing data " + e.toString());
			}						
			return jObj;
    	}

    //class ends
	}

	
	
	

