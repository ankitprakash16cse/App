package com.erp.collection;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import com.erp.collection.R;

import android.app.Application;

@ReportsCrashes(
		//	http://localhost:5984/acra-mamta/_design/acra-storage/_update/report
			//http://182.74.87.147:5984/acra-mamta/_design/acra-storage/_update/report
		    formUri = "https://mamta4cplus.cloudant.com/acra-epaper/_design/acra-storage/_update/report",
		    reportType = HttpSender.Type.JSON,
		    httpMethod = HttpSender.Method.POST,
		    formUriBasicAuthLogin = "ickelymentrandereemesomm",
		    formUriBasicAuthPassword = "433f16cf81f0ca1da3cfd887463eb714d569b78a",
		    formKey = "", // This is required for backward compatibility but not used
		    customReportContent = {
		            ReportField.APP_VERSION_CODE,
		            ReportField.APP_VERSION_NAME,
		            ReportField.ANDROID_VERSION,
		            ReportField.PACKAGE_NAME,
		            ReportField.REPORT_ID,
		            ReportField.BUILD,
		            ReportField.STACK_TRACE
		    },
		    mode = ReportingInteractionMode.TOAST,
		    resToastText = R.string.app_name
		)


public class Controller extends Application
{
	
    @Override
    public void onCreate() 
	{
        super.onCreate();
        // The following line triggers the initialization of ACRA
		//  ACRA.init(this);
    }	  
}
