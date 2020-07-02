package com.erp.collection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseOP extends SQLiteOpenHelper
{
	public static final int database_version = 1;

	public static final String dbpath = "/data/data/com.erp.collection/databases/"; // 																		///
	public static final String DATABASE_NAME = "booking.db";
	public static final String TABLE_CRM_APP_SUBSCRIBER = "CRM_APP_SUBSCRIBER";

	SQLiteDatabase SQProductLoader = null;
	private final Context myContext;
	private static DataBaseOP _DataBaseOP;

	Globals g = null;

	public DataBaseOP(Context context) 
	{
		super(context, dbpath + DATABASE_NAME, null, database_version);
		this.myContext = context;
		try 
		{
			createDataBase();
			g = Globals.getInstance(context);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static DataBaseOP getInstance(Context context) {
		if (_DataBaseOP == null) {
			_DataBaseOP = new DataBaseOP(context);
		}
		return _DataBaseOP;
	}

	@Override
	public void onCreate(SQLiteDatabase sdb) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase sdb, int version_old,int current_version) {

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
		else
		{

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
			e.printStackTrace();
		}
		if (checkDB != null) 
		{
			checkDB.close();
            //return myContext.deleteDatabase(DATABASE_NAME);
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
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createFile(String outFileName)
	{
		File ff = new File(outFileName);
		try 
		{
			if (!ff.exists()) 
			{
				ff.createNewFile();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void copyDataBase() 
	{
		try 
		{
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
		}
		catch (Exception e) 
		{
			e.printStackTrace();			
		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException 
	{
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) 
		{
			out.write(buffer, 0, read);
		}
	}

	public SQLiteDatabase openDatabase() throws SQLException 
	{
		SQLiteDatabase SQ = null;
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
	}

	public void closeDatabase(SQLiteDatabase SQ) throws SQLException {
		if (SQ != null && SQ.isOpen())
			SQ.close();
	}

	public void putAdvtCollection(String UNIT,String AGCODE,String DOCTYPE,String ROIDNUM,String RECPTNO ,String RECPTDT,String ACRECPTNO,String ACRECPTDT,String CHNO ,String CHDT,String BANK ,String AMOUNT ,String OTH_AMOUNT,
			String REASON,String VOUCHERNO,String CASHBANK,String REVENUE,String REPCODE,String STATION,String REMARK,String REF_RECPTNO,String REF_RECPTDT,String REF_AMOUNT,String REF_DOCTYPE,
			String USERID,String USERDATE,String USERIP,String COMP_CODE,String CREATION_DATETIME,String RECEIVED_REGION,String VOUCHERDATE,String AG_MAIN_CODE,String AG_SUB_CODE,
			String FLAG,String BKUP_SNO ,String RECEIVED_FROM,String CHEQUE_DEPOSIT_DATE,String CONS_AMOUNT ,String CONS_RCPT_NO,String CONS_REST_AMT ,String CONS_TYPE,String trn_type,
			String date_formate,String LOCAL_IMAGEPATH ,String IMAGE_NAME ,String LATITUDE,String LONGITUDE,String USER_IMAGE_PATH,String PAYMENT_ADVICE_IMAGE,String MOBILENO,String RECORD_STATUS,
			String RECEIPT_TYPE,String RETCODE,String PIMAGE_NAME_STATUS,String PUSER_IMAGE_STATUS,String PPAYMENT_ADVICE_STATUS,String EXTRA1,String EXTRA2,String EXTRA3,String EXTRA4,String EXTRA6,String EXTRA7,String EXTRA8,String EXTRA9,String EXTRA10)
	{
		ContentValues cv = null;
		SQLiteDatabase SQ = null;
		try {
			SQ = openDatabase();
			cv = new ContentValues();
			cv.put("PUNIT",UNIT);
    		cv.put("PAGCODE",AGCODE);
    		cv.put("PDOCTYPE",DOCTYPE);
    		cv.put("PROIDNUM",ROIDNUM);
    		cv.put("PRECPTNO",RECPTNO);
    		cv.put("PRECPTDT",RECPTDT);
    		cv.put("PACRECPTNO",ACRECPTNO);
    		cv.put("PACRECPTDT",ACRECPTDT);
    		cv.put("PCHNO",CHNO);
    		cv.put("PCHDT",CHDT);
    		cv.put("PBANK",BANK);
    		cv.put("PAMOUNT",AMOUNT);
    		cv.put("POTH_AMOUNT",OTH_AMOUNT);
    		cv.put("PREASON",REASON);
    		cv.put("PVOUCHERNO",VOUCHERNO);
    		cv.put("PCASHBANK",CASHBANK);
    		cv.put("PREVENUE",REVENUE);
    		cv.put("PREPCODE",REPCODE);
    		cv.put("PSTATION",STATION);
    		cv.put("PREMARK",REMARK);
    		cv.put("PREF_RECPTNO",REF_RECPTNO);
    		cv.put("PREF_RECPTDT",REF_RECPTDT);
    		cv.put("PREF_AMOUNT",REF_AMOUNT);
    		cv.put("PREF_DOCTYPE",REF_DOCTYPE);
    		cv.put("PUSERID",USERID);
    		cv.put("PUSERDATE",USERDATE);
    		cv.put("PUSERIP",USERIP);
    		cv.put("PCOMP_CODE",COMP_CODE);
    		cv.put("PCREATION_DATETIME",CREATION_DATETIME);
    		cv.put("PRECEIVED_REGION",RECEIVED_REGION);
    		cv.put("PVOUCHERDATE",VOUCHERDATE);
    		cv.put("PAG_MAIN_CODE",AG_MAIN_CODE);
    		cv.put("PAG_SUB_CODE",AG_SUB_CODE);
    		cv.put("PFLAG",FLAG);
    		cv.put("PBKUP_SNO",BKUP_SNO);
    		cv.put("PRECEIVED_FROM",RECEIVED_FROM);
    		cv.put("PCHEQUE_DEPOSIT_DATE",CHEQUE_DEPOSIT_DATE);
    		cv.put("PCONS_AMOUNT",CONS_AMOUNT);
    		cv.put("PCONS_RCPT_NO",CONS_RCPT_NO);
    		cv.put("PCONS_REST_AMT",CONS_REST_AMT);
    		cv.put("PCONS_TYPE",CONS_TYPE);
    		cv.put("ptrn_type",trn_type); // 'I' For Insert, 'U' For Update & 'D' For Delete
    		cv.put("pdate_formate",date_formate);
    		cv.put("PLOCAL_IMAGEPATH",LOCAL_IMAGEPATH);
    		cv.put("PIMAGE_NAME",IMAGE_NAME);
    		cv.put("PLATITUDE",LATITUDE);
    		cv.put("PLONGITUDE",LONGITUDE);
    		cv.put("PUSER_IMAGE_PATH",USER_IMAGE_PATH);
    		cv.put("PPAYMENT_ADVICE_IMAGE",PAYMENT_ADVICE_IMAGE);
    		cv.put("PMOBILENO",MOBILENO);
    		cv.put("PRECORD_STATUS",RECORD_STATUS);    		    	
    		cv.put("PRECEIPT_TYPE",RECEIPT_TYPE);
    		cv.put("PRETCODE",RETCODE);    		
    		cv.put("PIMAGE_NAME_STATUS",PIMAGE_NAME_STATUS);    		    	
    		cv.put("PUSER_IMAGE_STATUS",PUSER_IMAGE_STATUS);
    		cv.put("PPAYMENT_ADVICE_STATUS",PPAYMENT_ADVICE_STATUS);
    		cv.put("PEXTRA1",EXTRA1);
    		cv.put("PEXTRA2",EXTRA2);
    		cv.put("PEXTRA3",EXTRA3);
    		cv.put("PEXTRA4",EXTRA4);
    		cv.put("PEXTRA6",EXTRA6);
    		cv.put("PEXTRA7",EXTRA7);
    		cv.put("PEXTRA8",EXTRA8);
    		cv.put("PEXTRA9",EXTRA9);
    		cv.put("PEXTRA10",EXTRA10);
			long k = SQ.insert(Config.TBL_AD_RCPTHDR_PROV, null, cv);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cv.clear();
			} catch (Exception e) {
			}
			closeDatabase(SQ);
		}
	}

	public void putSurveyInfo(String PCOMP_CODE, String PUNIT_CODE,
			String PORDER_FORM, String PORDER_DATE, String PPAYMENT_MODE,
			String PCHEQUE_NUMBER, String PCHEQUE_BANK, String PCHEQUE_DATE,
			String PNAME, String PADD1, String PADD2, String PAREA,
			String PCITY, String PPHONE_HOUSE, String PMOBILE, String PAGE,
			String PHEAD_HOME, String PEDUCATION, String PHEAD_HOME_OCC,
			String PENTRY_DATE, String PSURVEY_FORM_NO, String PCREATED_BY,
			String PCREATED_DT, String PUPDATED_BY, String PUPDATED_DT,
			String PSCHEME_NAME, String PSCHEME_CODE, String PSCHEME_AMOUNT,
			String PGENDER, String PEMAIL, String PTOWN, String PPIN,
			String PORGANIZATION_TYPE, String PORGANIZATION,
			String PDESIGNATION, String PINCOMEGROUP, String PVEH_CODE,
			String PDUR_CODE, String PNEWS_CODE, String PADDR_PROOF_CODE,
			String PADDR_PROOF_PATH, String PCARD_TYPE, String PCARD_NO,
			String PCARD_EXPIRY_DATE, String PCARD_HOLDER_NAME,
			String PPAYMENT_FLAG, String PWING_COLONY, String POCCUPATION,
			String PEXTRA1, String PEXTRA2, String PEXTRA3, String PEXTRA4,
			String PEXTRA5, String PEXTRA6, String PEXTRA7, String PEXTRA8,
			String PEXTRA9, String PEXTRA10, String PEXTRA11, String PEXTRA12,
			String PEXTRA13, String PEXTRA14, String PEXTRA15, String PEXTRA16,
			String PEXTRA17, String PEXTRA18, String PEXTRA19, String PEXTRA20) {
		ContentValues cv = null;
		SQLiteDatabase SQ = null;
		try {
			SQ = openDatabase();
			cv = new ContentValues();
			// cv.putNull("Sno");

			// PCOMP_CODE,PUNIT_CODE,PORDER_FORM,PORDER_DATE,PPAYMENT_MODE,PCHEQUE_NUMBER,PCHEQUE_BANK,PCHEQUE_DATE,
			cv.put("PCOMP_CODE", PCOMP_CODE);
			cv.put("PUNIT_CODE", PUNIT_CODE);
			cv.put("PORDER_FORM", PORDER_FORM);
			cv.put("PORDER_DATE", PORDER_DATE);
			cv.put("PPAYMENT_MODE", PPAYMENT_MODE);
			cv.put("PCHEQUE_NUMBER", PCHEQUE_NUMBER);
			cv.put("PCHEQUE_BANK", PCHEQUE_BANK);
			cv.put("PCHEQUE_DATE", PCHEQUE_DATE);
			cv.put("PNAME", PNAME);
			cv.put("PADD1", PADD1);
			cv.put("PADD2", PADD2);
			cv.put("PAREA", PAREA);
			cv.put("PCITY", PCITY);
			cv.put("PPHONE_HOUSE", PPHONE_HOUSE);
			cv.put("PMOBILE", PMOBILE);
			cv.put("PAGE", PAGE);
			cv.put("PHEAD_HOME", PHEAD_HOME);
			cv.put("PEDUCATION", PEDUCATION);
			cv.put("PHEAD_HOME_OCC", PHEAD_HOME_OCC);
			cv.put("PENTRY_DATE", PENTRY_DATE);
			cv.put("PSURVEY_FORM_NO", PSURVEY_FORM_NO);
			cv.put("PCREATED_BY", PCREATED_BY);
			cv.put("PCREATED_DT", PCREATED_DT);
			cv.put("PUPDATED_BY", PUPDATED_BY);
			cv.put("PUPDATED_DT", PUPDATED_DT);
			cv.put("PSCHEME_NAME", PSCHEME_NAME);
			cv.put("PSCHEME_CODE", PSCHEME_CODE);
			cv.put("PSCHEME_AMOUNT", PSCHEME_AMOUNT);
			cv.put("PGENDER", PGENDER);
			cv.put("PEMAIL", PEMAIL);
			cv.put("PTOWN", PTOWN);
			cv.put("PPIN", PPIN);
			cv.put("PORGANIZATION_TYPE", PORGANIZATION_TYPE);
			cv.put("PORGANIZATION", PORGANIZATION);
			cv.put("PDESIGNATION", PDESIGNATION);
			cv.put("PINCOMEGROUP", PINCOMEGROUP);
			cv.put("PVEH_CODE", PVEH_CODE);
			cv.put("PDUR_CODE", PDUR_CODE);
			cv.put("PNEWS_CODE", PNEWS_CODE);
			cv.put("PADDR_PROOF_CODE", PADDR_PROOF_CODE);
			cv.put("PADDR_PROOF_PATH", PADDR_PROOF_PATH);
			cv.put("PCARD_TYPE", PCARD_TYPE);
			cv.put("PCARD_NO", PCARD_NO);
			cv.put("PCARD_EXPIRY_DATE", PCARD_EXPIRY_DATE);
			cv.put("PCARD_HOLDER_NAME", PCARD_HOLDER_NAME);
			cv.put("PPAYMENT_FLAG", PPAYMENT_FLAG);
			cv.put("PWING_COLONY", PWING_COLONY);
			cv.put("POCCUPATION", POCCUPATION);
			cv.put("PEXTRA1", PEXTRA1);
			cv.put("PEXTRA2", PEXTRA2);
			cv.put("PEXTRA3", PEXTRA3);
			cv.put("PEXTRA4", PEXTRA4);
			cv.put("PEXTRA5", PEXTRA5);
			cv.put("PEXTRA6", PEXTRA6);
			cv.put("PEXTRA7", PEXTRA7);
			cv.put("PEXTRA8", PEXTRA8);
			cv.put("PEXTRA9", PEXTRA9);
			cv.put("PEXTRA10", PEXTRA10);
			cv.put("PEXTRA11", PEXTRA11);
			cv.put("PEXTRA12", PEXTRA12);
			cv.put("PEXTRA13", PEXTRA13);
			cv.put("PEXTRA14", PEXTRA14);
			cv.put("PEXTRA15", PEXTRA15);
			cv.put("PEXTRA16", PEXTRA16);
			cv.put("PEXTRA17", PEXTRA17);
			cv.put("PEXTRA18", PEXTRA18);
			cv.put("PEXTRA19", PEXTRA19);
			cv.put("PEXTRA20", PEXTRA20);

			long k = SQ.insert("CRM_APP_SUBSCRIBER", null, cv);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cv.clear();
			} catch (Exception e) {
			}
			closeDatabase(SQ);
		}
	}

	public void updateInfo(String PRODCD, String LIKE_UNLIKE, String CMNTS) {
		ContentValues cv = null;
		SQLiteDatabase SQ = null;
		try {
			SQ = openDatabase();
			cv = new ContentValues();
			if (!LIKE_UNLIKE.equals("0"))
				cv.put("LIKE_UNLIKE", LIKE_UNLIKE);
			if (!CMNTS.equals("0"))
				cv.put("CMNTS", CMNTS);
			SQ.update(TABLE_CRM_APP_SUBSCRIBER, cv, "PRODCD=?",
					new String[] { PRODCD });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				cv.clear();
			} catch (Exception e) {
			}
			closeDatabase(SQ);
		}
	}




	private void tablecheck()
    {
        SQLiteDatabase SQ = null;
        try
        {
            SQ = openDatabase();
            Cursor c = SQ.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            if (c.moveToFirst()) {
                while ( !c.isAfterLast() ) {
     //               Toast.makeText(myContext, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                    c.moveToNext();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
			try {
			} catch (Exception e) {
			}
			closeDatabase(SQ);
		}



    }


	public void putDropValues(ContentValues cv, String tablename) 
	{
		SQLiteDatabase SQ = null;
		try 
		{
			SQ = openDatabase();
			long k = SQ.insert(tablename, null, cv);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{					
			closeDatabase(SQ);
		}
        tablecheck();
	}

	public void putDeleteValues(String tablename) 
	{
		SQLiteDatabase SQ = null;
		try 
		{
			SQ = openDatabase();
			SQ.delete(tablename, null, null);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			closeDatabase(SQ);
		}
	}

	public void getinfo(String optparam,String Tablename) 
	{
		Cursor cur = null;
		SQLiteDatabase SQ = null;
		try 
		{
			SQ = openDatabase();
			String query = "select * from " + Tablename;
			cur = SQ.rawQuery(query, null);
			if (cur != null) 
			{				
				clearList(optparam)	;
				while (cur.moveToNext()) 
				{
					HashMap row = new HashMap(cur.getColumnCount());
					for (int i = 0; i < cur.getColumnCount(); i++) {
						row.put(cur.getColumnName(i), cur.getString(i));
					}					
					appandList(optparam,row);
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally
		{
			cur.close();
			closeDatabase(SQ);
		}
	}
	
//	public void getLogin(String Tablename,String username,String Password,String branchcode)
//	{
//		SQLiteDatabase SQ=null;
//		Cursor cur=null;
//		try
//		{
//			SQ=openDatabase();
//			String query = "select * from " + Tablename +" where username='"+username+"' and password='"+Password+"' and RETAINER='"+branchcode+"'";
//			cur = SQ.rawQuery(query, null);
//			if (cur != null)
//			{
//				int count=cur.getCount();
//				if(count>0)
//				{
//					g.UserList.clear();
//					while (cur.moveToNext())
//					{
//						HashMap row = new HashMap(cur.getColumnCount());
//						for (int i = 0; i < cur.getColumnCount(); i++)
//						{
//							row.put(cur.getColumnName(i).toUpperCase(), cur.getString(i));
//							if(cur.getColumnName(i).toUpperCase().equalsIgnoreCase("RETAINER"))
//								g.setBranchCode(cur.getString(i).toString());
//							if(cur.getColumnName(i).toUpperCase().equalsIgnoreCase("PUB_CENT"))
//								g.setPrintingCenterCode(cur.getString(i).toString());
//						}
//						g.UserList.add(row);
//					}
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			cur.close();
//			closeDatabase(SQ);
//		}
//	}
		
	private void appandList(String optparam,HashMap list)
	{
		if(optparam.equalsIgnoreCase("AGENCY"))
		{   
			g.AgencyList.add(list);		
		}		
		else if(optparam.equalsIgnoreCase("CITY"))
		{
			g.CityList.add(list);		
		} 
		else if(optparam.equalsIgnoreCase("PRNCTR"))
		{
			g.PrintingCenterList.add(list);	
		}
		else if(optparam.equalsIgnoreCase("BANK"))
		{
			g.BankList.add(list);	
		}
		else if(optparam.equalsIgnoreCase("PAYMENT"))
		{
			g.PaymodeList.add(list);
		}
	}
		
	private void clearList(String optparam)
	{
		if(optparam.equalsIgnoreCase("AGENCY"))
		{   g.AgencyList.clear();		
		}else if(optparam.equalsIgnoreCase("CITY")){
			g.CityList.clear();
		}else if(optparam.equalsIgnoreCase("PRNCTR")){
			g.PrintingCenterList.clear();
		}else if(optparam.equalsIgnoreCase("BANK")){
			g.BankList.clear();
		}else if(optparam.equalsIgnoreCase("PAYMENT")){
			g.PaymodeList.clear();
		}
	}
	
	public void getBranchData(String Tablename,String pcentercode)
	{		
		SQLiteDatabase SQ=null;
		Cursor cur=null;
		try 
		{				
			SQ=openDatabase();				
			String query = "select * from " + Tablename +" where pub_center='"+pcentercode+"'";			
			cur = SQ.rawQuery(query, null);
			if (cur != null) 
			{	
				int count=cur.getCount();
				if(count>0)
				{
					g.BranchList.clear();
					while (cur.moveToNext()) 
					{											
						HashMap row = new HashMap(cur.getColumnCount());
						for (int i = 0; i < cur.getColumnCount(); i++) 						
							row.put(cur.getColumnName(i).toUpperCase(), cur.getString(i));						
						g.BranchList.add(row);      
					}
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{	
			cur.close();
			closeDatabase(SQ);
		}
	}
	

	public void getAgencyDetails(String agencycode)
	{		
		SQLiteDatabase SQ=null;
		Cursor cur=null;
		try 
		{				
			SQ=openDatabase();				
			String query = "select * from " + Config.TBL_AGENCY +" where MAIN_AG_CODE='"+agencycode+"'";			
			cur = SQ.rawQuery(query, null);
			if (cur != null) 
			{	
				int count=cur.getCount();
				if(count>0)
				{
					cur.moveToFirst();
					g.setAG_CODE(cur.getString(cur.getColumnIndex("AG_CODE")));
					g.setMAIN_AG_CODE(cur.getString(cur.getColumnIndex("MAIN_AG_CODE")));
					g.setSUB_AG_CODE(cur.getString(cur.getColumnIndex("SUB_AG_CODE")));
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{	
			cur.close();
			closeDatabase(SQ);
		}
	}


	public void getAgencyDetails_booking(String agencycode)
	{
		SQLiteDatabase SQ=null;
		Cursor cur=null;
		try
		{
			SQ=openDatabase();
			String query = "select * from " + Config.TBL_AGENCY_BOOKING +" where code_subcode='"+agencycode+"'";
			//String query = "select * from " + Config.TBL_AGENCY_BOOKING;
			cur = SQ.rawQuery(query, null);
			if (cur != null)
			{
				int count=cur.getCount();
				if(count>0)
				{
					cur.moveToFirst();
					g.setAG_CODE(cur.getString(cur.getColumnIndex("Agency_Code")));
					//g.setMAIN_AG_CODE(cur.getString(cur.getColumnIndex("MAIN_AG_CODE")));
					g.setSUB_AG_CODE(cur.getString(cur.getColumnIndex("SUB_Agency_Code")));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cur.close();
			closeDatabase(SQ);
		}
	}


	public void getAgencyDetails_Sam(String agencycode)
	{
		SQLiteDatabase SQ=null;
		Cursor cur=null;
		try
		{
			SQ=openDatabase();
			/*String query = "select * from " + Config.TBL_SAMAGENCY +" where CODE='"+agencycode+"'";*/
			String query = "select * from " + Config.TBL_SAMAGENCY;
			cur = SQ.rawQuery(query, null);
			if (cur != null)
			{
				int count=cur.getCount();
				if(count>0)
				{
					cur.moveToFirst();
					//g.setAG_CODE(cur.getString(cur.getColumnIndex("AG_CODE")));
					g.setMAIN_AG_CODE(cur.getString(cur.getColumnIndex("CODE")));
					g.setSUB_AG_CODE(cur.getString(cur.getColumnIndex("SUBCODE")));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cur.close();
			closeDatabase(SQ);
		}
	}




	public void getContactPersonDetails(String code)
	{
		SQLiteDatabase SQ=null;
		Cursor cur=null;
		try
		{
			SQ=openDatabase();
			String query = "select * from " + Config.TBL_SAMCONTACTPERSON +" where CODE='"+code+"'";
			cur = SQ.rawQuery(query, null);
			if (cur != null)
			{
				int count=cur.getCount();
				if(count>0)
				{
					cur.moveToFirst();
					g.setSmobileno(cur.getString(cur.getColumnIndex("MOBILE")));
					g.setSemail(cur.getString(cur.getColumnIndex("EMAIL")));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cur.close();
			closeDatabase(SQ);
		}
	}

	public void updateImage(String PORDER_FORM,String status,String Fieldname)
	{
		ContentValues cv = null;
		SQLiteDatabase SQ=null;
		try 
		{
			SQ=openDatabase();
			cv = new ContentValues();			
			cv.put(Fieldname,status);					
			SQ.update(Config.TBL_AD_RCPTHDR_PROV, cv, "PRECPTNO=?", new String[]{PORDER_FORM});			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{			
			cv.clear();
			closeDatabase(SQ);
		}
	}


	public void getTaskName(String activity_id,String group_id) {

		SQLiteDatabase SQ=null;
		SQ=openDatabase();
		Cursor cur=null;

		try
		{

			String query="select comp_code, TASKID, TASK_NAME, TASK_FEEDING_TYPE, task_order, NO_OF_COLUMN from " + Config.TBL_TaskSheet +"  WHERE COMP_CODE = 'HT001' AND ACTIVITY_ID = '"+activity_id+"' AND GROUPID='"+group_id+"'";
			//String query = "select * from " + Config.TBL_TaskSheet +" where ACTIVITY_ID='"+activity_id+"'";
			cur = SQ.rawQuery(query, null);
			if (cur != null)
			{
				g.TaskSheetData2.clear();

				cur.moveToFirst();
				int count=cur.getCount();
				for(int i=0;i<count;i++)
				{
					cur.moveToPosition(i);
					HashMap map = new HashMap();

					for(int j=0;j<cur.getColumnCount();j++)
					{
						map.put(cur.getColumnName(j),cur.getString(j));     //cur.getString(j)

					}
					g.TaskSheetData2.add(map);

				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		//cur.close();
		closeDatabase(SQ);
	}

	public void getGroupName(String activity_id) {

		SQLiteDatabase SQ=null;
		SQ=openDatabase();
		Cursor cur=null;

		try
		{
			String query="select comp_code,	GROUPID, GROUP_NAME, group_order from " + Config.TBL_TaskSheet +"  WHERE COMP_CODE = 'HT001' AND ACTIVITY_ID ='"+activity_id+"'" + "GROUP BY comp_code, GROUPID, GROUP_NAME, group_order";
			//String query = "select * from " + Config.TBL_TaskSheet +" where ACTIVITY_ID='"+activity_id+"'";
			cur = SQ.rawQuery(query, null);
			if (cur != null)
			{
				g.TaskSheetData1.clear();

				cur.moveToFirst();
				int count=cur.getCount();
				for(int i=0;i<count;i++)
				{
					cur.moveToPosition(i);
					HashMap map = new HashMap();

					for(int j=0;j<cur.getColumnCount();j++)
					{
						map.put(cur.getColumnName(j),cur.getString(j));     //cur.getString(j)

					}
					g.TaskSheetData1.add(map);

				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		//cur.close();
		closeDatabase(SQ);
	}

//	public ArrayList<AdvtCollectionSubmit> getSurveyCollectionInfo(String tablename)
//	{
//		SQLiteDatabase SQ=null;
//		Cursor cur=null;
//		ArrayList<AdvtCollectionSubmit> NeedSynchList=null;
//		try
//		{
//			SQ=openDatabase();
//			String sql="select * from "+tablename+" where PEXTRA10='N'";
//			cur = SQ.rawQuery(sql, null);
//			if (cur != null)
//			{
//				int count=cur.getCount();
//				if(count>0)
//				{
//					//g.NeedSynchList.clear();
//					NeedSynchList=new ArrayList<AdvtCollectionSubmit>();
//					while (cur.moveToNext())
//					{
//						AdvtCollectionSubmit row ;
//						String PUNIT=cur.getString(cur.getColumnIndex("PUNIT"));
//			    		String PAGCODE=cur.getString(cur.getColumnIndex("PAGCODE"));
//			    		String PDOCTYPE=cur.getString(cur.getColumnIndex("PDOCTYPE"));
//			    		String PROIDNUM=cur.getString(cur.getColumnIndex("PROIDNUM"));
//			    		String PRECPTNO=cur.getString(cur.getColumnIndex("PRECPTNO"));
//			    		String PRECPTDT=cur.getString(cur.getColumnIndex("PRECPTDT"));
//			    		String PACRECPTNO=cur.getString(cur.getColumnIndex("PACRECPTNO"));
//			    		String PACRECPTDT=cur.getString(cur.getColumnIndex("PACRECPTDT"));
//			    		String PCHNO=cur.getString(cur.getColumnIndex("PCHNO"));
//			    		String PCHDT=cur.getString(cur.getColumnIndex("PCHDT"));
//			    		String PBANK=cur.getString(cur.getColumnIndex("PBANK"));
//			    		String PAMOUNT=cur.getString(cur.getColumnIndex("PAMOUNT"));
//			    		String POTH_AMOUNT=cur.getString(cur.getColumnIndex("POTH_AMOUNT"));
//			    		String PREASON=cur.getString(cur.getColumnIndex("PREASON"));
//			    		String PVOUCHERNO=cur.getString(cur.getColumnIndex("PVOUCHERNO"));
//			    		String PCASHBANK=cur.getString(cur.getColumnIndex("PCASHBANK"));
//			    		String PREVENUE=cur.getString(cur.getColumnIndex("PREVENUE"));
//			    		String PREPCODE=cur.getString(cur.getColumnIndex("PREPCODE"));
//			    		String PSTATION=cur.getString(cur.getColumnIndex("PSTATION"));
//			    		String PREMARK=cur.getString(cur.getColumnIndex("PREMARK"));
//			    		String PREF_RECPTNO=cur.getString(cur.getColumnIndex("PREF_RECPTNO"));
//			    		String PREF_RECPTDT=cur.getString(cur.getColumnIndex("PREF_RECPTDT"));
//			    		String PREF_AMOUNT=cur.getString(cur.getColumnIndex("PREF_AMOUNT"));
//			    		String PREF_DOCTYPE=cur.getString(cur.getColumnIndex("PREF_DOCTYPE"));
//			    		String PUSERID=cur.getString(cur.getColumnIndex("PUSERID"));
//			    		String PUSERDATE=cur.getString(cur.getColumnIndex("PUSERDATE"));
//			    		String PUSERIP=cur.getString(cur.getColumnIndex("PUSERIP"));
//			    		String PCOMP_CODE=cur.getString(cur.getColumnIndex("PCOMP_CODE"));
//			    		String PCREATION_DATETIME=cur.getString(cur.getColumnIndex("PCREATION_DATETIME"));
//			    		String PRECEIVED_REGION=cur.getString(cur.getColumnIndex("PRECEIVED_REGION"));
//			    		String PVOUCHERDATE=cur.getString(cur.getColumnIndex("PVOUCHERDATE"));
//			    		String PAG_MAIN_CODE=cur.getString(cur.getColumnIndex("PAG_MAIN_CODE"));
//			    		String PAG_SUB_CODE=cur.getString(cur.getColumnIndex("PAG_SUB_CODE"));
//			    		String PFLAG=cur.getString(cur.getColumnIndex("PFLAG"));
//			    		String PBKUP_SNO=cur.getString(cur.getColumnIndex("PBKUP_SNO"));
//			    		String PRECEIVED_FROM=cur.getString(cur.getColumnIndex("PRECEIVED_FROM"));
//			    		String PCHEQUE_DEPOSIT_DATE=cur.getString(cur.getColumnIndex("PCHEQUE_DEPOSIT_DATE"));
//			    		String PCONS_AMOUNT=cur.getString(cur.getColumnIndex("PCONS_AMOUNT"));
//			    		String PCONS_RCPT_NO=cur.getString(cur.getColumnIndex("PCONS_RCPT_NO"));
//			    		String PCONS_REST_AMT=cur.getString(cur.getColumnIndex("PCONS_REST_AMT"));
//			    		String PCONS_TYPE=cur.getString(cur.getColumnIndex("PCONS_TYPE"));
//			    		String ptrn_type=cur.getString(cur.getColumnIndex("ptrn_type")); // 'I' For Insert, 'U' For Update & 'D' For Delete
//			    		String pdate_formate=cur.getString(cur.getColumnIndex("pdate_formate"));
//			    		String PLOCAL_IMAGEPATH=cur.getString(cur.getColumnIndex("PLOCAL_IMAGEPATH"));
//			    		String PIMAGE_NAME=cur.getString(cur.getColumnIndex("PIMAGE_NAME"));
//			    		String PLATITUDE=cur.getString(cur.getColumnIndex("PLATITUDE"));
//			    		String PLONGITUDE=cur.getString(cur.getColumnIndex("PLONGITUDE"));
//			    		String PUSER_IMAGE_PATH=cur.getString(cur.getColumnIndex("PUSER_IMAGE_PATH"));
//			    		String PPAYMENT_ADVICE_IMAGE=cur.getString(cur.getColumnIndex("PPAYMENT_ADVICE_IMAGE"));
//			    		String PMOBILENO=cur.getString(cur.getColumnIndex("PMOBILENO"));
//			    		String PRECORD_STATUS=cur.getString(cur.getColumnIndex("PRECORD_STATUS"));
//			    		String PRECEIPT_TYPE=cur.getString(cur.getColumnIndex("PRECEIPT_TYPE"));
//			    		String PRETCODE=cur.getString(cur.getColumnIndex("PRETCODE"));
//			    		String PIMAGE_NAME_STATUS=cur.getString(cur.getColumnIndex("PIMAGE_NAME_STATUS"));
//			    		String PUSER_IMAGE_STATUS=cur.getString(cur.getColumnIndex("PUSER_IMAGE_STATUS"));
//			    		String PPAYMENT_ADVICE_STATUS=cur.getString(cur.getColumnIndex("PPAYMENT_ADVICE_STATUS"));
//			    		String PEXTRA1=cur.getString(cur.getColumnIndex("PEXTRA1"));
//			    		String PEXTRA2=cur.getString(cur.getColumnIndex("PEXTRA2"));
//			    		String PEXTRA3=cur.getString(cur.getColumnIndex("PEXTRA3"));
//			    		String PEXTRA4=cur.getString(cur.getColumnIndex("PEXTRA4"));
//			    		String PEXTRA6=cur.getString(cur.getColumnIndex("PEXTRA6"));
//			    		String PEXTRA7=cur.getString(cur.getColumnIndex("PEXTRA7"));
//			    		String PEXTRA8=cur.getString(cur.getColumnIndex("PEXTRA8"));
//			    		String PEXTRA9=cur.getString(cur.getColumnIndex("PEXTRA9"));
//			    		String PEXTRA10=cur.getString(cur.getColumnIndex("PEXTRA10"));
//						row=new AdvtCollectionSubmit(PUNIT,PAGCODE,PDOCTYPE,PROIDNUM,PRECPTNO ,PRECPTDT,PACRECPTNO,PACRECPTDT,
//				    			PCHNO ,PCHDT,PBANK ,PAMOUNT ,POTH_AMOUNT,PREASON,PVOUCHERNO,PCASHBANK,
//				    			PREVENUE,PREPCODE,PSTATION,PREMARK,PREF_RECPTNO,PREF_RECPTDT,PREF_AMOUNT,
//				    			PREF_DOCTYPE,PUSERID,PUSERDATE,PUSERIP,PCOMP_CODE,PCREATION_DATETIME,
//				    			PRECEIVED_REGION,PVOUCHERDATE,PAG_MAIN_CODE,PAG_SUB_CODE,
//				    			PFLAG,PBKUP_SNO ,PRECEIVED_FROM,PCHEQUE_DEPOSIT_DATE,PCONS_AMOUNT ,PCONS_RCPT_NO,PCONS_REST_AMT ,PCONS_TYPE,
//				    			 ptrn_type,pdate_formate,PLOCAL_IMAGEPATH ,PIMAGE_NAME ,PLATITUDE,PLONGITUDE,
//				    			PUSER_IMAGE_PATH,PPAYMENT_ADVICE_IMAGE,PMOBILENO,PRECORD_STATUS,PRECEIPT_TYPE,PRETCODE,PIMAGE_NAME_STATUS,PUSER_IMAGE_STATUS,PPAYMENT_ADVICE_STATUS,
//				    			PEXTRA1,PEXTRA2,PEXTRA3,PEXTRA4,PEXTRA6,PEXTRA7,PEXTRA8,PEXTRA9,PEXTRA10);
//						NeedSynchList.add(row);
//					}
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			cur.close();
//			closeDatabase(SQ);
//		}
//		return NeedSynchList;
//	}
	public int getCount(String Tablename)
	{	
		Cursor cur = null;
		SQLiteDatabase SQ=null;	
		int count=0;
		try
		{
			SQ=openDatabase();
			String sql="select * from "+Tablename+" where PEXTRA10='N'";
			cur = SQ.rawQuery(sql, null);
			if (cur != null)
			{
				 count=cur.getCount();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 


		//	cur.close();
			closeDatabase(SQ);

		return count;
	}
	
	public int getLastUpdate(String Tablename)
	{	
		Cursor cur = null;
		SQLiteDatabase SQ=null;	
		int count=0;
		try
		{
			SQ=openDatabase();
			String sql="select * from "+Tablename;
			cur = SQ.rawQuery(sql, null);
			if (cur != null) 
			{	
				 count=cur.getCount();	
				 if(count>0)
				 {
					 cur.moveToFirst();
					 String agdate=cur.getString(cur.getColumnIndex("AgencyDate"));
					 String rtdate=cur.getString(cur.getColumnIndex("RetainerDate"));
					 if(agdate.equals("1"))
						 g.setAgencyDate(" ");
					 else
						 g.setAgencyDate(agdate);
						 
					 if(rtdate.equals("1"))
						 g.setRetDate(" ");
					 else
						 g.setRetDate(rtdate);
						 
				 }
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{			
			cur.close();
			closeDatabase(SQ);
		}
		return count;
	}
	
	
	public void dateSetting(String ad,String rd, String tablename,int type) 
	{
		SQLiteDatabase SQ = null;
		ContentValues cv = null;
		try 
		{
			SQ = openDatabase();
			cv=new ContentValues();		
			
			if(type==1)
				cv.put("AgencyDate", ad);
			else if(type==2)
				cv.put("RetainerDate", rd);
						
				SQ.update(tablename, cv, null, null);
				
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{					
			closeDatabase(SQ);
		}
	}
	
//	public ArrayList<MenuModel> getMenu()
//	{
//		SQLiteDatabase SQ=null;
//		ArrayList<MenuModel> menuItem= null;
//		Cursor cur;
//		try
//		{
//			SQ=openDatabase();
//			String query = "select * from MenuData where flag='A'";
//			cur = SQ.rawQuery(query, null);
//			if (cur != null)
//			{
//				int count=cur.getCount();
//				if(count>0)
//				{
//					cur.moveToFirst();
//					menuItem=new ArrayList<MenuModel>();
//					for(int i=0;i<count;i++)
//					{
//						MenuModel obj=new MenuModel();
//						obj.setMenuid(cur.getInt(cur.getColumnIndex("Sno")));
//						obj.setMenuCaption(cur.getString(cur.getColumnIndex("MenuName")));
//						obj.setMenuImage(getImage(cur.getString(cur.getColumnIndex("MenuIcon"))));
//						menuItem.add(obj);
//						cur.moveToNext();
//					}
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}finally {
//			closeDatabase(SQ);
//		}
//		return menuItem;
//	}
	
	public  int getImage(String name)
	{
	     //return myContext.getResources().getDrawable(myContext.getResources().getIdentifier(name, "drawable", myContext.getPackageName()));
		return  myContext.getResources().getIdentifier(name, "drawable", myContext.getPackageName());//(myContext.getPackageName()+":drawable/"+name, null, null);
	}

	public void getLogin(String Tablename,String username,String Password,String branchcode)
	{
		SQLiteDatabase SQ=null;
		Cursor cur=null;
		try
		{
			SQ=openDatabase();
			String query = "select * from " + Tablename +" where username='"+username+"' and password='"+Password+"' and RETAINER='"+branchcode+"'";
			cur = SQ.rawQuery(query, null);
			if (cur != null)
			{
				int count=cur.getCount();
				if(count>0)
				{
					g.UserList.clear();
					while (cur.moveToNext())
					{
						HashMap row = new HashMap(cur.getColumnCount());
						for (int i = 0; i < cur.getColumnCount(); i++)
						{
							row.put(cur.getColumnName(i).toUpperCase(), cur.getString(i));
							if(cur.getColumnName(i).toUpperCase().equalsIgnoreCase("RETAINER"))
								g.setBranchCode(cur.getString(i).toString());
							if(cur.getColumnName(i).toUpperCase().equalsIgnoreCase("PUB_CENT"))
								g.setPrintingCenterCode(cur.getString(i).toString());
						}
						g.UserList.add(row);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cur.close();
			closeDatabase(SQ);
		}
	}



	public void getTasksheetdata() {

		SQLiteDatabase SQ=null;
		SQ=openDatabase();
		Cursor cur=null;

		try
		{

			String query = "select comp_code, ACTIVITY_ID, ACTIVITY_NAME, ACTIVITY_ORDER from TASKSHEET WHERE COMP_CODE = 'HT001' GROUP BY comp_code, ACTIVITY_ID, ACTIVITY_NAME, ACTIVITY_ORDER" ;
			cur = SQ.rawQuery(query, null);
			if (cur != null)
			{
				g.TaskSheetData.clear();

				cur.moveToFirst();
				int count=cur.getCount();
				for(int i=0;i<count;i++)
				{
					cur.moveToPosition(i);
					HashMap map = new HashMap();

					for(int j=0;j<cur.getColumnCount();j++)
					{
						map.put(cur.getColumnName(j),cur.getString(j));     //cur.getString(j)

					}
					g.TaskSheetData.add(map);

				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		cur.close();
		closeDatabase(SQ);
	}
}
