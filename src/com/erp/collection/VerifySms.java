package com.erp.collection;

import com.erp.collection.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class VerifySms extends BaseActivity implements OnClickListener{

	int edittextheight,edittextwidth,capTextSize,_headerHeight,_textWidth,dropdownwidth,dropdownheight,subTextWeidth;
	int headtextSize=14;
	int fontsize1=14;
	int _height,_width;
	Activity activity;
	Context context;
	Typeface face;
	RelativeLayout ll,_header,_center;
	EditText otpedit;
    TextView otptxt;
    TextView btndetails,btnprofile,btnhabits,btnlater,btnpayment,btnverify,btnresen,btnsubmit,btncancel;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1 ;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS1 =2;   

	
	public void displayConfgh()	
	{
		Display _display;
		_display=getWindowManager().getDefaultDisplay();
		_height=_display.getHeight();
		_width=_display.getWidth();				
	}
	public void basicSettings() 
	{
		headtextSize=(int)(_height*0.015);
		switch (getResources().getDisplayMetrics().densityDpi) 
		{
		
			case DisplayMetrics.DENSITY_MEDIUM:
				edittextwidth=(int)(_width*.7);
				edittextheight=(int)(_height*.05);
				capTextSize=(int)(_height*.011);
				_headerHeight=(int)(_height*0.05);	
				_textWidth=(int)(_width*0.2);	
				dropdownwidth=(int)(_width*.5);
				dropdownheight=(int)(_height*0.03);	
				subTextWeidth=(int)(_width*.45);				
				break;
			case DisplayMetrics.DENSITY_HIGH:	
				edittextwidth=(int)(_width*.60);
				edittextheight=(int)(_height*.05);
				capTextSize=(int)(_height*.011);
				_headerHeight=(int)(_height*0.05);	
				_textWidth=(int)(_width*0.23);	
				dropdownwidth=(int)(_width*.5);
				dropdownheight=(int)(_height*0.03);	
				subTextWeidth=(int)(_width*.45);
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				edittextwidth=(int)(_width*.60);
				edittextheight=(int)(_height*.05);
				capTextSize=(int)(_height*.011);
				_headerHeight=(int)(_height*0.05);	
				_textWidth=(int)(_width*0.23);	
				dropdownwidth=(int)(_width*.5);
				dropdownheight=(int)(_height*0.03);	
				subTextWeidth=(int)(_width*.45);
				break;
			case DisplayMetrics.DENSITY_XXHIGH:				
				edittextwidth=(int)(_width*.5);
				edittextheight=(int)(_height*.05);
				capTextSize=(int)(_height*.008);
				_headerHeight=(int)(_height*0.04);	
				_textWidth=(int)(_width*0.14);	
				dropdownwidth=(int)(_width*.3);
				subTextWeidth=(int)(_width*.2);
				dropdownheight=(int)(_height*0.03);
				headtextSize=(int)(_height*0.011);
				break;
			case DisplayMetrics.DENSITY_XXXHIGH:	
				edittextwidth=(int)(_width*.60);
				edittextheight=(int)(_height*.05);
				capTextSize=(int)(_height*.011);
				_textWidth=(int)(_width*0.23);	
				dropdownwidth=(int)(_width*.5);
				dropdownheight=(int)(_height*0.03);	
				subTextWeidth=(int)(_width*.45);
				break;
		}			
	}	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.advt_verify);
		getLayoutInflater().inflate(R.layout.advt_verify, frameLayout);
		  activity=VerifySms.this;	
	        context=VerifySms.this;	
	             
	    	DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			Display dis = getWindowManager().getDefaultDisplay();	
			displayConfgh();
			basicSettings();
			
			 //--------------------------------------------------------
			ll = new RelativeLayout(context);
			ll.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			ll.setBackgroundColor(Color.parseColor("#1C92DC"));
					
			ImageView moreimg = new ImageView(this);
			moreimg.setId(100);
			moreimg.setImageResource(R.drawable.menuico);
			RelativeLayout.LayoutParams paramsmenu = new RelativeLayout.LayoutParams((int) (_height * 0.05), (int) (_height * 0.05));
			paramsmenu.setMargins((int) Math.ceil(5 * (metrics.density)), 0, 0, 0);
			paramsmenu.addRule(RelativeLayout.CENTER_VERTICAL);
			moreimg.setLayoutParams(paramsmenu);

			moreimg.setOnClickListener(new OnClickListener() 
			{			
				@Override
				public void onClick(View v)
				{
					if (mDrawerLayout.isDrawerOpen(mDrawerList))
					{
						mDrawerLayout.closeDrawer(mDrawerList);
					}
					else 
					{
						mDrawerLayout.openDrawer(mDrawerList);
					}
				}
			});

			ImageView img_logo = new ImageView(this);
			RelativeLayout.LayoutParams menuparam1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			menuparam1.setMargins(0, 5, 0, 5);		
			menuparam1.addRule(RelativeLayout.CENTER_HORIZONTAL,moreimg.getId());	
			img_logo.setLayoutParams(menuparam1);
			//img_logo.setImageResource(R.drawable.fourclogo);
			//ll.addView(img_logo);
			
			ll.addView(moreimg);
			getSupportActionBar().setCustomView(ll);
			  
		 //===========================================================================================		  
			    ActionBar ab = getSupportActionBar();	     
		        ab.setDisplayShowHomeEnabled(false); // show or hide the default home button
		        ab.setDisplayHomeAsUpEnabled(false);
		        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
		        ab.setDisplayShowTitleEnabled(false); // d
		        
		        Toolbar parent =(Toolbar) ll.getParent();
		        parent.setContentInsetsAbsolute(0,0);
			  //===========================================================================================	 	        	       
		        face=Typeface.createFromAsset(getAssets(),"font/"+g.getFontName());	
		      //===========================================================================================
		        _header=(RelativeLayout)findViewById(R.id._header);
				_header.setBackgroundColor(Color.parseColor("#252525"));
				_header.setId(80001);				
				RelativeLayout.LayoutParams _headerparams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,_headerHeight);
				_headerparams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
							
				TextView _caption=new TextView(this);
				RelativeLayout.LayoutParams _captionRule = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,(int)(_height*0.05));
				_captionRule.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				_caption.setLayoutParams(_captionRule);		
				_caption.setText(" Collection-SMS-VERIFICATION ");	
				_caption.setGravity(Gravity.CENTER_VERTICAL);
				_caption.setPadding(10, 0, 0, 0);
				_caption.setTextColor(Color.WHITE);
				_caption.setTypeface(face,Typeface.NORMAL);
				_caption.setTextSize(capTextSize);				
				_header.addView(_caption);		
				
				TextView _fontsizeCaption=new TextView(this);
				RelativeLayout.LayoutParams _fontsizeCaptionRule = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,(int)(_height*0.05));
				_fontsizeCaptionRule.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				_fontsizeCaption.setLayoutParams(_fontsizeCaptionRule);		
				//_fontsizeCaption.setText(" Aa ");	
				_fontsizeCaption.setGravity(Gravity.CENTER_VERTICAL);
				_fontsizeCaption.setPadding(10, 0, 0, 0);
				_fontsizeCaption.setTextColor(Color.WHITE);
				_fontsizeCaption.setTypeface(face,Typeface.NORMAL);
				_fontsizeCaption.setTextSize(capTextSize);		
				_header.addView(_fontsizeCaption);		
				
				
			//===================================================================		

		        _center=(RelativeLayout)findViewById(R.id._center);
		        RelativeLayout.LayoutParams _centerparams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		        _centerparams.setMargins(1, _headerHeight, 0, 0);
		        _center.setLayoutParams(_centerparams);
		        
		        TextView otp = (TextView) findViewById(R.id.txtotph);
		        otp.setTypeface(face, Typeface.BOLD);
		        otp.setTextSize(headtextSize);

		        otptxt = (TextView) findViewById(R.id.txtotp);
		        LinearLayout.LayoutParams nameCapParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, edittextheight);
		        nameCapParams.setMargins(20, 0, 0, 0);
		        otptxt.setLayoutParams(nameCapParams);
		        otptxt.setTypeface(face, Typeface.NORMAL);
		        otptxt.setTextSize(capTextSize);

		        otpedit = (EditText) findViewById(R.id.editotp);
		        LinearLayout.LayoutParams nameCapParams1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, edittextheight);
		        nameCapParams1.setMargins(20, 0, 20, 0);
		        otpedit.setPadding(0, 10, 0, 0);
		        otpedit.setLayoutParams(nameCapParams1);
		        otpedit.setTextSize(fontsize1);
		        otpedit.setSingleLine(true);
		        otpedit.setTypeface(face, Typeface.NORMAL);

		        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		        buttonParams.setMargins(10, 0, 10, 0);		
		        	       	      
		        btnsubmit = (TextView) findViewById(R.id.btnsub);
		       // btnsubmit.setId(2);
		        btnsubmit.setTextColor(Color.WHITE);
		        btnsubmit.setBackgroundResource(R.drawable.buttonboxbordergray);
		        btnsubmit.setLayoutParams(buttonParams);
		        btnsubmit.setOnClickListener(this);	        	        
		        
		        btncancel = (TextView) findViewById(R.id.btnCancel);
		      //  btncancel.setId(3);
		        btncancel.setTextColor(Color.WHITE);
		        btncancel.setBackgroundResource(R.drawable.buttonboxbordergray);
		        btncancel.setLayoutParams(buttonParams);
		        btncancel.setOnClickListener(this);
		       
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
		case R.id.btnsub:
			getSecrateKeyGeneration();
			break;
		case R.id.btnCancel:			
			break;
		default:
			break;
		}		
	}
	
	private void getSecrateKeyGeneration()
	{
		String result="";
		String first="";
		String last="";
		String arr_data="";
		try
		{
			arr_data=otpedit.getText().toString();	
			if(arr_data.contains("/"))
			{
				first=arr_data.split("/")[0];
				last=arr_data.split("/")[1];
				arr_data=first;
			}
			else
			{
				Toast.makeText(context, "Invalid Data.", Toast.LENGTH_LONG).show();
			}
			int len=arr_data.length();					
			int count=0;
			for(int i=1;i<len;i++)
			{				
				if(i%4==0)
				{
					result=result+arr_data.charAt(i-1);
					count=count+1;
				}
				if(count==6)
					break;
				if(!((len-i)>4))
				{
					arr_data=arr_data+arr_data;
					len=arr_data.length();	
				}
			}
			if(result.equals(last))
			{
				Toast.makeText(context, "SMS generated by app.", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(context, "SMS not generated by app.", Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) 
		{			
		}		
	}


}
