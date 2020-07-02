package com.erp.collection;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.GsonBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

@SuppressLint("TrulyRandom") public class Globals
{
	public int check_pos=0;
	public int activity_pos;
	public int ch_pos=0;
	public String activity_code="";
	private String defDateFormate="dd/mm/yyyy";
	private static Globals instance;
	private Context mContext = null;

	private String menuItem[] = { "Classified Booking","Circulation Receipt", "My Booking", "My Survey Booking","Sync Collection","Verify SMS","Contact Person","Raw Data","Report","Sam Report","Attendence Report","Month Wise Report","Client Wise Report","GPRS Report","Attendence2 Report","Exit"};

	//public ArrayList<HashMap> genderItem = new ArrayList<HashMap>();
	private String FontName = "Montserrat-Regular.otf";
	public ArrayList<HashMap> PaymodeType = new ArrayList<HashMap>();
	HashMap<String, String> Login_parameters = new HashMap<String, String>();
	public ArrayList<HashMap> PrintingCenterList = new ArrayList<HashMap>();
	public ArrayList<HashMap> BranchList = new ArrayList<HashMap>();
	public ArrayList<HashMap> UserList = new ArrayList<HashMap>();

	public ArrayList<HashMap> CityList = new ArrayList<HashMap>();
	public ArrayList<HashMap> AgencyList = new ArrayList<HashMap>();
	public String AgencyList_hds_bh[];
	public ArrayList<String> AgencyList_hds_name = new ArrayList<String>();
	public ArrayList<String> AgencyList_hds_code = new ArrayList<String>();
    public ArrayList<String> AgencyList_hds_subcode = new ArrayList<String>();
    public ArrayList<String> AgencyList_hds_combind = new ArrayList<String>();


    public ArrayList<String> ClientList_hds_name = new ArrayList<String>();
	public ArrayList<String> ClientList_hds_code = new ArrayList<String>();
	public ArrayList<String> ClientList_hds_combind = new ArrayList<String>();
	public ArrayList<String> ClientList_vkt_combind = new ArrayList<String>();

	//public ArrayList<HashMap> RetainList = new ArrayList<HashMap>();
	public ArrayList<HashMap> BankList = new ArrayList<HashMap>();
	public ArrayList<HashMap> PaymodeList = new ArrayList<HashMap>();
    public ArrayList<HashMap> Logindata = new ArrayList<HashMap>();
	public ArrayList<HashMap> TaskSheetData = new ArrayList<HashMap>();
    public ArrayList<HashMap> TaskSheetData1 = new ArrayList<HashMap>();
    public ArrayList<HashMap> TaskSheetData2 = new ArrayList<HashMap>();

	//for sam=========
	public ArrayList<LinkedHashMap> SamLeadFinalSumarryList = new ArrayList<LinkedHashMap>();
	private ArrayList<HashMap> SAMCallType = new ArrayList<HashMap>();
	public ArrayList<LinkedHashMap> SamLeadList = new ArrayList<LinkedHashMap>();
	public ArrayList<LinkedHashMap> TodayList = new ArrayList<LinkedHashMap>();
	public ArrayList<LinkedHashMap> MonthList = new ArrayList<LinkedHashMap>();
	public ArrayList<LinkedHashMap> WeekList = new ArrayList<LinkedHashMap>();
	public ArrayList<LinkedHashMap> MeetingList = new ArrayList<LinkedHashMap>();
	public ArrayList<HashMap> AgencyListAutoComplete = new ArrayList<HashMap>();
	public ArrayList<HashMap> leadclose =new ArrayList<HashMap>();
	public ArrayList<HashMap> clientregister=new ArrayList<HashMap>();
	public ArrayList<HashMap> lead_submit=new ArrayList<HashMap>();
	public ArrayList<HashMap> contact_person_submit=new ArrayList<HashMap>();
	public ArrayList<LinkedHashMap> DetailsList = new ArrayList<LinkedHashMap>();


	//========================================================================================by vishal tyagi
	 public int c_day;
	 public int c_month;
	 public int c_year;
	 public String status;
	 public String date="09/09/2019";


	//List<Lead> LeadList;
	//======================
	public String uniqueno="";
	//collection variables====================================================================
	private String PrintingCenterCode;
	private String BranchCode;
	private String payment_type="";
	private String payment_code="";
	private String payment_name="";
	private String MAIN_AG_CODE="";
	private String SUB_AG_CODE="";
	private String AG_CODE="";
	private String RETAINER_NAME="";
	private String RETAINER_CODE="";
	private String BANK_NAME="";
	private String CHQ_NUMBER="";
	private String CHQ_DATE="";
	private String AMOUNT="";
	private String REMARK="";
	private String CHQ_IMG="";
	private String PAYEE_IMG="";
	private String PYM_ADV_IMG="";
	private String PAYEE_MOB="";	
	
	private int OTP_ATTEMPTS=0;
	//========================================================================================
	public double latitude;
	public double longitude;
	public String fileName;
	public String dirn;
	public Uri urlm;
	public boolean _boolBUttonCapture=false;
	String RetainerMobile="";
	
	//========================================================================================
	
	String retDate="";
	String AgencyDate="";
	String dbtype="oracle";
	//====Sam variables =======================================================================
	private String Ctype;
	private String Smobileno;
	private String semail;
	private String contactPersonCode;

	private String Agency_code_SAM="";
	private String Agency_subcode_SAM="";
	private String Client_code_SAM="";
	public void setPaytype() {
		
		HashMap cs = new HashMap();
		cs.put("ptype", "--Select--");
		cs.put("code", "0");
		PaymodeType.add(cs);
		
		HashMap ca = new HashMap();
		ca.put("ptype", "Normal");
		ca.put("code", "1");
		PaymodeType.add(ca);

		HashMap ch = new HashMap();
		ch.put("ptype", "Advance");
		ch.put("code", "2");
		PaymodeType.add(ch);
	}

	public void setCallType() {
		//SAMCallType.clear();
		HashMap cs = new HashMap();
		cs.put("ctype", "--Select--");
		cs.put("code", "0");
		getSAMCallType().add(cs);

		HashMap cg = new HashMap();     // add by vishal kumar tyagi
		cg.put("ctype", "In House");
		cg.put("code", "In House");
		getSAMCallType().add(cg);

		HashMap cb = new HashMap();
		cb.put("ctype", "Proposal Submit");
		cb.put("code", "Proposal Submit");
		getSAMCallType().add(cb);

		HashMap cbp = new HashMap();
		cbp.put("ctype", "Presentation");
		cbp.put("code", "Presentation");
		getSAMCallType().add(cbp);

		HashMap ce = new HashMap();
		ce.put("ctype", "Outstanding");
		ce.put("code", "Outstanding");
		getSAMCallType().add(ce);

        HashMap cf = new HashMap();
        cf.put("ctype", "Following Up");
        cf.put("code", "Following Up");
        getSAMCallType().add(cf);

		HashMap co = new HashMap();
		co.put("ctype", "Support Call option");
		co.put("code", "Support Call option");
		getSAMCallType().add(co);

        HashMap pr = new HashMap();
        pr.put("ctype", "PR Call option");
        pr.put("code", "PR Call option");
        getSAMCallType().add(pr);
	}

	private Globals(Context context) {
		setPaytype();
		mContext = context;
	}

	public static synchronized Globals getInstance(Context context) {
		if (instance == null) {
			return instance = new Globals(context);
		} else
			return instance;

	}

	public ArrayList<HashMap> getModeList() {
		return PaymodeType;
	}

	public String[] getMenuItem() {
		return menuItem;
	}



	public String getFontName() {
		return FontName;
	}

	
//Collection==============================================================	

	public void setBranchCode(String branchCode) {
		BranchCode = branchCode;
	}

	public void setPrintingCenterCode(String printingCenterCode) {
		PrintingCenterCode = printingCenterCode;
	}


	public String getAegenyCode_sam() {
		return Agency_code_SAM;
	}

	public void setAegenyCode_sam(String Ag_cd_sam) {
		Agency_code_SAM = Ag_cd_sam;
	}

	public String getClientCode_sam() {
		return Client_code_SAM;
	}

	public void setClientCode_sam(String Cl_cd_sam) {
		Client_code_SAM = Cl_cd_sam;
	}
	// ============= add by vishal kumar tyagi =============//

//	public String getAegenySubCode_sam() {
	//	return Agency_subcode_SAM;
//	}

//	public void setAegenySubCode_sam(String Ag_sb_cd_sam) {
//		Agency_subcode_SAM = Ag_sb_cd_sam;
//	}


	public String getDate(int dy) {
		String curdate = "";
		try {

			Calendar nnow = Calendar.getInstance();
			// nnow.before(20);
			nnow.add(Calendar.DATE, dy);
			String day = String.valueOf(nnow.get(Calendar.DATE));
			String month = String.valueOf(nnow.get(Calendar.MONTH) + 1);
			String year = String.valueOf(nnow.get(Calendar.YEAR));
			String hh = String.valueOf(nnow.getTime().getHours());
			String mm = String.valueOf(nnow.getTime().getMinutes());
			String ss = String.valueOf(nnow.getTime().getSeconds());
			if (month.length() == 2) {
			} else {
				month = "0" + month;
			}
			if (day.length() == 2) {
			} 
			else {
				day = "0" + day;
			}
			curdate = day + "/" + month + "/" + year + " " + hh + ":" + mm + ":" + ss;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return curdate;
	}

	public String getDate_sam(String dates)
	{
		String curdate = "";
		try {
			String amPm;
			Calendar nnow = Calendar.getInstance();
			// nnow.before(20);
			nnow.add(Calendar.DATE, 0);
//			String day = String.valueOf(nnow.get(Calendar.DATE));
//			String month = String.valueOf(nnow.get(Calendar.MONTH) + 1);
//			String year = String.valueOf(nnow.get(Calendar.YEAR));

			String day = String.valueOf(c_day);
			String month = String.valueOf(c_month + 1);
			String year = String.valueOf(c_year);


			String hh = "";//String.valueOf(nnow.getTime().getHours());
			String mm = "";//String.valueOf(nnow.getTime().getMinutes());
			String ss = String.valueOf(nnow.getTime().getSeconds());
			String[] separated = dates.split(":");

			hh=separated[0];
			mm=separated[1];

			if (month.length() == 2) {
			} else {
				month = "0" + month;
			}
			if (day.length() == 2) {
			} else {
				day = "0" + day;
			}

			if (Integer.parseInt(hh) >= 12) {
				amPm = "PM";
			} else {
				amPm = "AM";
			}


			if (hh.equals("13")) {
				   hh= "1"; }
           else if (hh.equals("14")) {
                hh= "2";
            }
           else if (hh.equals("15")) {
                hh= "3";
            }
            else if (hh.equals("16")) {
                hh= "4";
            }
	        else  if (hh.equals("17")) {
                hh= "5";
            }
	       else if (hh.equals("18")) {
                hh= "6";
            }
           else if (hh.equals("19")) {
                hh= "7";
            }
          else  if (hh.equals("20")) {
                hh= "8";
            }
           else if (hh.equals("21")) {
                hh= "9";
            }
           else if (hh.equals("22")) {
                hh= "10";
            }
           else if (hh.equals("23")) {
                hh= "11";
            }
            else if (hh.equals("24")) {
                hh= "12";
            }
            else {
                hh = hh ;
			}


			if (mm.length() == 2) {
			} else {
				mm = "0" + mm;
			}

		/*	if (Integer.parseInt(hh) >= 12) {
				amPm = "PM";
			} else {
				amPm = "AM";
			}*/

			curdate = month + "-" + day + "-" + year+" "+hh+":"+mm+":"+ss+ " "+amPm;


		} catch (Exception e) {
			// TODO: handle exception
		}
		return curdate;
	}


	public String getDate() 
	{
		String curdate = "";
		try {

			Calendar nnow = Calendar.getInstance();
			// nnow.before(20);
			nnow.add(Calendar.DATE, 0);
			String day = String.valueOf(nnow.get(Calendar.DATE));
			String month = String.valueOf(nnow.get(Calendar.MONTH) + 1);
			String year = String.valueOf(nnow.get(Calendar.YEAR));
			String hh = String.valueOf(nnow.getTime().getHours());
			String mm = String.valueOf(nnow.getTime().getMinutes());
			String ss = String.valueOf(nnow.getTime().getSeconds());
			if (month.length() == 2) {
			} else {
				month = "0" + month;
			}
			if (day.length() == 2) {
			} else {
				day = "0" + day;
			}

			curdate = day + "-" + month + "-" + year ;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return curdate;
	}


	public String getDateSlash()
	{
		String curdate = "";
		try {

			Calendar nnow = Calendar.getInstance();
			// nnow.before(20);
			nnow.add(Calendar.DATE, 0);
			String day = String.valueOf(nnow.get(Calendar.DATE));
			String month = String.valueOf(nnow.get(Calendar.MONTH) + 1);
			String year = String.valueOf(nnow.get(Calendar.YEAR));
			String hh = String.valueOf(nnow.getTime().getHours());
			String mm = String.valueOf(nnow.getTime().getMinutes());
			String ss = String.valueOf(nnow.getTime().getSeconds());
			if (month.length() == 2) {
			} else {
				month = "0" + month;
			}
			if (day.length() == 2) {
			} else {
				day = "0" + day;
			}

			curdate = day + "/" + month + "/" + year ;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return curdate;
	}

	public String getDateSlash_new()
	{
		String curdate = "";
		try {

			Calendar nnow = Calendar.getInstance();
			// nnow.before(20);
			nnow.add(Calendar.DATE, 0);
			String day = String.valueOf(nnow.get(Calendar.DATE));
			String month = String.valueOf(nnow.get(Calendar.MONTH) + 1);
			String year = String.valueOf(nnow.get(Calendar.YEAR));
			String hh = String.valueOf(nnow.getTime().getHours());
			String mm = String.valueOf(nnow.getTime().getMinutes());
			String ss = String.valueOf(nnow.getTime().getSeconds());
			if (month.length() == 2) {
			} else {
				month = "0" + month;
			}
			if (day.length() == 2) {
			} else {
				day = "0" + day;
			}

			curdate = month + "-" + day + "-" + year ;
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return curdate;
	}
	public String getOracleDate() 
	{
		String curdate = "";
		try {

			Calendar nnow = Calendar.getInstance();
			// nnow.before(20);
			nnow.add(Calendar.DATE, 0);
			String day = String.valueOf(nnow.get(Calendar.DATE));
			String month = String.valueOf(nnow.get(Calendar.MONTH) + 1);
			String year = String.valueOf(nnow.get(Calendar.YEAR));
		
			if (month.length() == 2) {
			} else {
				month = "0" + month;
			}
			if (day.length() == 2) {
			} else {
				day = "0" + day;
			}

			curdate = year+ "/" + month + "/"+ day   ;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return curdate;
	}
	
	
	public String getDatefolder(int dy) 
	{
		String curdate = "";
		try {

			Calendar nnow = Calendar.getInstance();
			// nnow.before(20);
			nnow.add(Calendar.DATE, dy);
			String day = String.valueOf(nnow.get(Calendar.DATE));
			String month = String.valueOf(nnow.get(Calendar.MONTH) + 1);
			String year = String.valueOf(nnow.get(Calendar.YEAR));			
			if (month.length() == 2) {
			} else {
				month = "0" + month;
			}
			if (day.length() == 2) {
			} 
			else {
				day = "0" + day;
			}
			
			curdate = day+ "" + month+ "" +year ;
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		return curdate;
	}

	int range = 9; // to generate a single number with this range, by default
					// its 0..9
	int length = 4; // by default length is 4

	public int generateRandomNumber() 
	{
		int randomNumber;

		SecureRandom secureRandom = new SecureRandom();
		String s = "";
		for (int i = 0; i < length; i++) {
			int number = secureRandom.nextInt(range);
			if (number == 0 && i == 0) { 
				i = -1;
				continue;
			}
			s = s + number;
		}
		randomNumber = Integer.parseInt(s);

		return randomNumber;
	}

	
	

	
	

			
	public boolean isNetworkAvailable() {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
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

	
	
	public String getMAIN_AG_CODE() {
		return MAIN_AG_CODE;
	}

	public void setMAIN_AG_CODE(String mAIN_AG_CODE) {
		MAIN_AG_CODE = mAIN_AG_CODE;
	}

	public String getSUB_AG_CODE() {
		return SUB_AG_CODE;
	}

	public void setSUB_AG_CODE(String sUB_AG_CODE) {
		SUB_AG_CODE = sUB_AG_CODE;
	}

	public String getAG_CODE() {
		return AG_CODE;
	}

	public void setAG_CODE(String aG_CODE) {
		AG_CODE = aG_CODE;
	}

	public String getRETAINER_NAME() {
		return RETAINER_NAME;
	}

	public void setRETAINER_NAME(String rETAINER_NAME) {
		RETAINER_NAME = rETAINER_NAME;
	}

	public String getRETAINER_CODE() {
		return RETAINER_CODE;
	}

	public void setRETAINER_CODE(String rETAINER_CODE) {
		RETAINER_CODE = rETAINER_CODE;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getPayment_code() {
		return payment_code;
	}

	public void setPayment_code(String payment_code) {
		this.payment_code = payment_code;
	}

	public String getPayment_name() {
		return payment_name;
	}

	public void setPayment_name(String payment_name) {
		this.payment_name = payment_name;
	}

	public String getBANK_NAME() {
		return BANK_NAME;
	}

	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}
	public int getOTP_ATTEMPTS() {
		return OTP_ATTEMPTS;
	}

	public void setOTP_ATTEMPTS(int oTP_ATTEMPTS) {
		OTP_ATTEMPTS = oTP_ATTEMPTS;
	}

	public String getRetDate() {
		return retDate;
	}

	public void setRetDate(String retDate) {
		this.retDate = retDate;
	}

	public String getAgencyDate() {
		return AgencyDate;
	}

	public void setAgencyDate(String agencyDate) {
		AgencyDate = agencyDate;
	}
	
	public String getRetainerMobile()
	{
		String res="";
		try
		{
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return res;
	}


    public String getDefDateFormate() {
        return defDateFormate;
    }

	public String getCtype() {
		return Ctype;
	}

	public void setCtype(String ctype) {
		Ctype = ctype;
	}

	public String getSmobileno() {
		return Smobileno;
	}

	public void setSmobileno(String smobileno) {
		Smobileno = smobileno;
	}

	public String getSemail() {
		return semail;
	}

	public void setSemail(String semail) {
		this.semail = semail;
	}

	public String getContactPersonCode() {
		return contactPersonCode;
	}

	public void setContactPersonCode(String contactPersonCode) {
		this.contactPersonCode = contactPersonCode;
	}

	public ArrayList<HashMap> getSAMCallType() {
	//	SAMCallType.clear();
		return SAMCallType;
	}
}
