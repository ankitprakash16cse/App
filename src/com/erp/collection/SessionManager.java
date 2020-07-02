package com.erp.collection;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager 
{
	SharedPreferences pref;
	Editor editor;
	Context _context;
	int PRIVATE_MODE = 0;
	private static final String PREF_NAME = "AndroidHivePref";
	private static final String IS_LOGIN = "IsLoggedIn";
	public static final String Email = "email_id";
	public static final String PASS = "pwd";
	public static final String UID = "uid";
	public static final String USER_NAME = "username";

	public SessionManager(Context context) 
	{
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
		editor = pref.edit();
	}

	public void createLoginSession(String email, String pwd,String uid,String username) 
	{
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(Email, email);
		editor.putString(PASS,pwd );
		editor.putString(UID,uid );
		editor.putString(USER_NAME, username);		
		editor.commit();
	}

	public boolean checkLogin() 
	{
		if (!this.isLoggedIn()) 
		{
			Intent i = new Intent(_context, Login.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(i);
		}
		return pref.getBoolean(IS_LOGIN, false);
	}

	public HashMap<String, String> getUserDetails()
	{
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(Email, pref.getString(Email, null));
		user.put(PASS, pref.getString(PASS, null));
		user.put(UID, pref.getString(UID, null));
		user.put(USER_NAME, pref.getString(USER_NAME, null));

		return user;
	}

	public void logoutUser() 
	{
		editor.clear();
		editor.commit();
		Intent i = new Intent(_context, Login.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		_context.startActivity(i);
	}

	public boolean isLoggedIn() 
	{
		return pref.getBoolean(IS_LOGIN, false);
	}
	
	public HashMap<String, String> getLoginDetails(String email,String password)
	{
		HashMap<String, String> user = new HashMap<String, String>();
		if(isLoggedIn())
		{
			user=getUserDetails();
			if(email.equals(user.get("Email")) && password.equals(user.get("PASS")))				
			{
				return user;
			}
		}
		return null;
	}
	
	
}
