package com.erp.collection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.navigation.drawer.adapters.ExpandableListAdapter;
import com.navigation.drawer.adapters.NavigationDrawerListAdapter;
import com.navigation.drawer.models.ExpandableMenuModel;
import com.navigation.drawer.models.Items;
import com.google.gson.GsonBuilder;



public class BaseActivity extends AppCompatActivity
{
	ExpandableListAdapter expandableListAdapter;
	ExpandableListView expandableListView;
	ArrayList<ExpandableMenuModel> headerList=new ArrayList<>();
	HashMap<ExpandableMenuModel, ArrayList<ExpandableMenuModel>> childList = new HashMap<>();

	Context context = BaseActivity.this;
	Globals g = Globals.getInstance(context);
	String DrawFlag = "true";
	String imageFilepath = "";
	ImageView pbrodimgs;
	String name = "";
	String resp = "";
	String flag = "";
	ImageView pbrodimg;
	protected FrameLayout frameLayout;
	protected ExpandableListView mDrawerList;
	protected ArrayList<Items> _items;
	protected static int position;
	private static boolean isLaunch = true;
	private static String TAG_PROFILEIMG;
	protected DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	int mcount=0;
	NavigationDrawerListAdapter ng=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation_drawer_base_layout);
		frameLayout = (FrameLayout) findViewById(R.id.content_frame);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ExpandableListView) findViewById(R.id.expandableListView);

		_items = new ArrayList<Items>();

		for(int x=0;x<g.getMenuItem().length;x++)
		{
			if(g.getMenuItem()[x].equals("Sync Collection"))
			{
				DataBaseOP dbBaseOP=DataBaseOP.getInstance(context);
				mcount=dbBaseOP.getCount(com.erp.collection.Config.TBL_AD_RCPTHDR_PROV);
				_items.add(new Items(g.getMenuItem()[x]+" ("+mcount+")", null, null));
			}
			else
			{
				_items.add(new Items(g.getMenuItem()[x], null, null));
			}
		}
		View header = (View) getLayoutInflater().inflate(R.layout.header, null);
		mDrawerList.addHeaderView(header);
		// set up the drawer's list view with items and click listener
		ng=new NavigationDrawerListAdapter(this, _items);
//		mDrawerList.setAdapter(ng);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				position = position - 1;
				openActivity(position);
			}
		});

		TextView ttusername = (TextView) findViewById(R.id.username);
		ttusername.setText("");
		String imgssss="";
		File file;

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.ic_launcher, /* nav drawer image to replace 'Up' caret */
				R.string.open_drawer, /* "open drawer" description for accessibility */
				R.string.close_drawer) /* "close drawer" description for accessibility */
		{
			@SuppressWarnings("deprecation")
			@Override
			public void onDrawerClosed(View drawerView)
			{
				getSupportActionBar().setTitle(g.getMenuItem()[position]);
				invalidateOptionsMenu(); // creates call to
				DrawFlag = "true";
				super.onDrawerClosed(drawerView);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onDrawerOpened(View drawerView)
			{
				getSupportActionBar().setTitle(getString(R.string.app_name));
				invalidateOptionsMenu(); // creates call to
				DrawFlag = "false";
				super.onDrawerOpened(drawerView);
			}
			@SuppressWarnings("deprecation")
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset)
			{
				super.onDrawerSlide(drawerView, slideOffset);
			}
			@SuppressWarnings("deprecation")
			@Override
			public void onDrawerStateChanged(int newState)
			{
				super.onDrawerStateChanged(newState);
			}
		};
		mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

		if (isLaunch)
		{
			isLaunch = false;
			openActivity(0);
		}
	}

	/* @Override
	 public void onNetworkConnectionChanged(boolean isConnected)
	 {
	        if(!isConnected) {

	          //show a No Internet Alert or Dialog

	        }else{

	            // dismiss the dialog or refresh the activity
	        }
	   }*/

	protected void openActivity(int position)
	{
		mDrawerLayout.closeDrawer(mDrawerList);
		BaseActivity.position = position; // Setting currently selected position
											// in this field so that it will be
											// available in our child
											// activities.
		String itemName=_items.get(position).getItemName();
		if(itemName.contains("("))
			itemName=itemName.split(" ")[0].trim();

		if(itemName.equalsIgnoreCase("Advt_Collection"))
		{
			startActivity(new Intent(this, StartOfShift.class));
		}
		else if(itemName.equalsIgnoreCase("Verify SMS"))
		{
			startActivity(new Intent(this, VerifySms.class));
		}
        else if (itemName.equalsIgnoreCase("Report")){
            //startActivity(new Intent(this,ReportActivity.class));
          //  Intent intent= new Intent (getApplicationContext(),ReportActivity.class);
           // startActivity(intent);
        }
//		else if(itemName.equalsIgnoreCase("Sam Report"))
//		{
//			startActivity(new Intent(this, Sam_Report_Web_View.class));
//		}
//		else if(itemName.equalsIgnoreCase("Contact Person"))
//		{
//			startActivity(new Intent(this, Cp_webview.class));//.putExtra("Cp","Contact Person"));
//
//
//		}
//		else if(itemName.equalsIgnoreCase("Raw Data"))
//		{
//			startActivity(new Intent(this,Raw_data.class));//.putExtra("Cp","Contact Person"));
//
//
//		}
//		else if(itemName.equalsIgnoreCase("Attendence Report"))
//		{
//			startActivity(new Intent(this,Attendence.class));//.putExtra("Cp","Contact Person"));
//
//
//		}
//		else if(itemName.equalsIgnoreCase("Month Wise Report"))
//		{
//			startActivity(new Intent(this,Monthwise_report.class));//.putExtra("Cp","Contact Person"));
//		}
//		else if(itemName.equalsIgnoreCase("Client Wise Report"))
//		{
//			startActivity(new Intent(this,Clientwise_report.class));//.putExtra("Cp","Contact Person"));
//		}
//		else if(itemName.equalsIgnoreCase("GPRS Report"))
//		{
//			startActivity(new Intent(this,GPRS.class));//.putExtra("Cp","Contact Person"));
//
//
//		}
//		else if(itemName.equalsIgnoreCase("Attendence2 Report"))
//		{
//			startActivity(new Intent(this,Attendence_second_report.class));//.putExtra("Cp","Contact Person"));
//
//
//		}
//		else if(itemName.equalsIgnoreCase("Synch"))
//		{
//			try
//			{
//				//startNewMainActivity(activity, Survey_Subscription_Activity.class);
//				/*ResultReceiverSynch mReceiver = new ResultReceiverSynch(new Handler());
//				Intent intent = new Intent(Intent.ACTION_SYNC, null, context, SynchSurvey.class);
//				intent.putExtra("receiver",mReceiver);
//				startService(intent);*/
//
//				new getSynkPage().execute();
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
		else if(itemName.equalsIgnoreCase("Exit"))
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			// set title
			alertDialogBuilder.setTitle(R.string.app_name);
			// set dialog message
			alertDialogBuilder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int id)
				{
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			})
			.setNegativeButton("No",new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int id)
				{
					dialog.cancel();
				}
			});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		}
	}

	private static Intent getIntent(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		return intent;
	}

	/* We can override onBackPressed method to toggle navigation drawer */
	@Override
	public void onBackPressed()
	{
		try
		{
			finish();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Bitmap getRoundedCornerBitmap(String imgpath, int pixels)
	{
		Bitmap bitmap = null;
		try
		{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			bitmap = BitmapFactory.decodeFile(imgpath, options);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth()+20, bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	///==================data synch============================================================================================


}
