package com.erp.collection;
public class Config 
{

	//private static String baseurl="http://117.247.84.140:8082/addcollection/api/";
	private static String baseurl="http://103.115.194.178/adapi/api/";
	//private static String baseurl="http://203.101.89.216/samapi/api/";//"http://103.115.194.75/AdvtCollectionNVAPI/api/";//"http://advtcollectionapi.punyanagari.in/api/";  	/
    //private static String baseurl="http://103.115.194.138/publishcode/api/";//"http://203.101.89.216/samapi/api/";//"http://103.115.194.138/publishcode/api/";//"http://203.101.89.216/samapi/api/";//"http://103.115.194.138/publishcode/api/"; //"http://203.101.89.216/samapi/api/";//"http://103.115.194.138/publishcode/api/";//"http://203.101.89.216/samapi/api/";//   //"http://103.115.194.138/publishcode/api/";//"http://203.101.89.216/samapi/api/";//"http://103.115.194.75/AdvtCollectionNVAPI/api/";//"http://advtcollectionapi.punyanagari.in/api/";  	//
	public static String TAG_LOGIN="Table";
	public static String url_PrintingCenter=baseurl+"PrintingCenter";
	public static String url_Branch=baseurl+"BranchBind";
	public static String url_login=baseurl+"Employee";
	public static String url_agency=baseurl+"Agency";
	public static String url_TaskSheet=baseurl+"TaskSheet";
	public static String url_Retainer=baseurl+"Retainer";
	public static String url_Bank=baseurl+"CrmBankBind";
	public static String url_paymode=baseurl+"Paymode";
	public static String url_OTP=baseurl+"OtpAdvt";
	public static String url_AdvtSubmit=baseurl+"AdvtColl";
	public static String url_samleadfunnalsummary=baseurl+"samleadfunnalsummary";
    public static String url_sambindagencycode=baseurl+"sambindagencycodescode";
	public static String url_samclintname=baseurl+"samclintname";
	public static String url_samsubmit=baseurl+"samsubmit";
	public static String url_contactpersonsamsubmit=baseurl+"SamContactPerson";
	public static String url_samleadplan=baseurl+"SamLeadPlan";
    public static String url_samcontactpersonbind=baseurl+"samcontactpersonbind";
	public static String upLoadServerUri=baseurl+"UploadImage/UploadImage";
	public static String OTP_MSG="we received your details and confirmation OTP is";
	public static String url_SamLeadDetail=baseurl+"SamLeadDetail";
	public static String url_SamLeadClose=baseurl+"SamLeadClose";
	public static String url_SamLeadClientComplete=baseurl+"SamLeadClientComplete";
	public static String TBL_LOGIN="UserDetails";
	public static String TBL_AGENCY="AgencyMaster";
	public static String TBL_RETAINER="RetainerMast";
	public static String TBL_CITY="CityMast";
	public static String TBL_PTINTINGCENTER="Print_Center";
	public static String TBL_BRANCH="BranchMast";
	public static String TBL_AD_RCPTHDR_PROV="AD_RCPTHDR_PROV";
	public static String TBL_BANK="BankMast";
	public static String TBL_PAYMENTMODE="Tbl_Payment_Mode";
	public static String TBL_DATESTATUS="tblDateStatus";
	//public static String TBL_DATESTATUS="tblDateStatus";
	public static String TBL_Client="ClientMaster";
	public static String TBL_SAMAGENCY="SamAgencyMaster";
	public static String TBL_TaskSheet="TaskSheet";
	public static String TBL_SAMCONTACTPERSON="SamContactPersonMaster";
	public static boolean DROPDOWN=false;
	public static String PHONENO="";
	public static int pend_id =1;
	public static int ALARM_REPEAT=20;
	public static String TBL_AGENCY_BOOKING="AgencyMaster_booking";
}
