package com.erp.collection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
/**
 * Simple database access helper class.
 * 
 * @author Dan Breslau
 */
public class AutoCompleteDbAdapter {
    
	public static final int DATABASE_VERSION = 1;
	public static final String dbpath ="/data/data/com.erp.collection/databases/"; //
	public static final String DATABASE_NAME = "booking.db";						
	public static final String TABLE_CRM_APP_SUBSCRIBER = "CRM_APP_SUBSCRIBER";	
	

    private class DatabaseHelper extends SQLiteOpenHelper     
    {
    	private Context myContext;
 
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            myContext=context;
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) 
        {           
        	createDataBase();
        }
 
        public void onUpgrade(SQLiteDatabase db, int old, int c) 
        {           
        }
        public void createDataBase()
    	{
    		boolean dbExist = checkDataBase();
    		if (!dbExist) 
    		{
    			try 
    			{
    				copyDataBase();
    			} 
    			catch (Exception e) 
    			{
    				e.toString();
    			}
    		}
    	}

    	private boolean checkDataBase() 
    	{
    		SQLiteDatabase checkDB = null;
    		try 
    		{
    			String dbpathabs = dbpath + DATABASE_NAME;
    			checkDB = SQLiteDatabase.openDatabase(dbpathabs, null,SQLiteDatabase.OPEN_READONLY);
    		}
    		catch (SQLiteException e) 
    		{
    		}
    		if (checkDB != null) 
    		{
    			checkDB.close();
    		}
    		return checkDB != null ? true : false;
    	}

    	private void CreateDirectory(String DB_PATH) 
    	{
    		boolean flag = false;
    		try 
    		{
    			File fileObj = new File(dbpath);
    			if (!fileObj.exists()) 
    			{
    				flag = fileObj.mkdirs();
    			}
    		} catch (Exception e) {
    			// TODO: handle exception
    		}
    	}

    	private void createFile(String outFileName) {
    		File ff = new File(outFileName);
    		try {
    			if (!ff.exists()) {
    				ff.createNewFile();
    			}
    		} catch (Exception e) {
    			// TODO: handle exception
    		}
    	}

    	private void copyDataBase() {
    		try {
    			// Open your local db as the input stream
    			AssetManager am = myContext.getAssets();
    			InputStream myInput = am.open(DATABASE_NAME);
    			CreateDirectory(dbpath);
    			String outFileName = dbpath + DATABASE_NAME;
    			createFile(outFileName);
    			OutputStream myOutput = new FileOutputStream(outFileName);
    			copyFile(myInput, myOutput);

    			myOutput.flush();
    			myOutput.close();
    			myInput.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    			// TODO: handle exception
    		}
    	}

    	private void copyFile(InputStream in, OutputStream out) throws IOException {
    		byte[] buffer = new byte[1024];
    		int read;
    		while ((read = in.read(buffer)) != -1) {
    			out.write(buffer, 0, read);
    		}
    	}

    	/*public SQLiteDatabase openDatabase() throws SQLException 
    	{
    		SQLiteDatabase SQ=null;
    		try 
    		{
    			String dbpathabs = dbpath + DATABASE_NAME;			
    			SQ = SQLiteDatabase.openDatabase(dbpathabs, null,SQLiteDatabase.OPEN_READWRITE);
    		} 
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}
    		return SQ;
    	}   */                  
    }
 
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Activity mActivity;
 
    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param activity
     *            the Activity that is using the database
     */
    public AutoCompleteDbAdapter(Activity activity) 
    {
        this.mActivity = activity;
        mDbHelper = this.new DatabaseHelper(activity);
        mDb = mDbHelper.getWritableDatabase();
    }
 
    /**
     * Closes the database.
     */
    public void close() {
       // mDbHelper.close();
    }
 
   
    public Cursor getMatchingStates(String optparam,String Tablename,String SearchFilterValue,String SearchFilterName) throws SQLException {
 
        String queryString ="SELECT * FROM " + Tablename;
		Cursor cursor=null;
 
        if (SearchFilterValue != null) {          
        	SearchFilterValue = SearchFilterValue.trim() + "%";
            queryString += " WHERE "+SearchFilterName+" LIKE ?";
        }
        String params[] = { SearchFilterValue };
 
        if (SearchFilterValue == null) {          
            params = null;
        }
        try {
          cursor = mDb.rawQuery(queryString, params);
            if (cursor != null) {
                this.mActivity.startManagingCursor(cursor);
                cursor.moveToFirst();
                return cursor;
            }
        }
        catch (SQLException e) {
            Log.e("AutoCompleteDbAdapter", e.toString());
            throw e;
        }
        finally {
        	this.mActivity.stopManagingCursor(cursor);
		}
 
        return null;
    }
}
