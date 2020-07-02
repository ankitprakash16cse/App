package com.erp.collection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import com.erp.collection.R.drawable;
import com.example.androidcustomcam.interfaces.OnFocusListener;
import eu.janmuller.android.simplecropimage.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class CameraMainActivity extends AppCompatActivity implements OnFocusListener
{
	private CameraPreview mPreview;
	private RelativeLayout mLayout;
	Camera camera = null;
	boolean prev_state=true;
	static String filePath="";
	private Camera mCamera;
	Context context;
	Activity activity;
	LinearLayout mybuttonlayout;
	Button button_switch_camera,button_capture;
	private int currentCameraId;
	Globals g;
	final int PIC_CROP = 2;
	int resultset=RESULT_OK;
	final int RESULT_BACK=3;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent i2 = getIntent();
		filePath = i2.getStringExtra("filepath");
		// Requires RelativeLayout.
		context=CameraMainActivity.this;
		activity=this;
		g=Globals.getInstance(context);

	/*	getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);*/

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Display dis = getWindowManager().getDefaultDisplay();

		int w = dis.getWidth();
		int h = dis.getHeight();
		float density = metrics.density;

		RelativeLayout ll = new RelativeLayout(context);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ll.setBackgroundColor(Color.parseColor("#1C92DC"));

		/*ImageView img_logo = new ImageView(this);
		RelativeLayout.LayoutParams menuparam1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);	;
		menuparam1.addRule(RelativeLayout.CENTER_HORIZONTAL);
		img_logo.setLayoutParams(menuparam1);
		img_logo.setImageResource(drawable.fourclogo);*/

		ImageView img_logo = new ImageView(this);
		LayoutParams menuparam1 = new LayoutParams((int) (w * 0.4), LayoutParams.MATCH_PARENT);
		menuparam1.setMargins(0, 5, 0, 5);
		menuparam1.addRule(RelativeLayout.CENTER_HORIZONTAL);
		img_logo.setLayoutParams(menuparam1);
		img_logo.setImageResource(drawable.sam_logo_ol);
		ll.addView(img_logo);

		//getActionBar().setCustomView(ll);
		getSupportActionBar().setCustomView(ll);
		// ========================================================================================================================

		ActionBar ab = getSupportActionBar();
		//ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
		ab.setDisplayShowHomeEnabled(false); // show or hide the default home button
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
		ab.setDisplayShowTitleEnabled(false); // d

		Toolbar parent =(Toolbar) ll.getParent();
		parent.setContentInsetsAbsolute(0,0);
		//===========================================================================================
		mLayout = new RelativeLayout(this);
		mLayout.setBackgroundColor(Color.parseColor("#1C92DC"));

		//button on bottom

		LayoutParams _bottomParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		_bottomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

		mybuttonlayout=new LinearLayout(context);
		mybuttonlayout.setLayoutParams(_bottomParams);
		mybuttonlayout.setBackgroundColor(Color.parseColor("#1C92DC"));
		mybuttonlayout.setOrientation(LinearLayout.HORIZONTAL);
		mybuttonlayout.setGravity(Gravity.CENTER_HORIZONTAL);
		if(!g._boolBUttonCapture)
		{
			Typeface faceCaption=Typeface.createFromAsset(getAssets(),"Montserrat-Bold.otf");
			TextView  _marqueeText=new TextView(this);
			_marqueeText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,(int)(h*.05)));
			_marqueeText.setText(Html.fromHtml(" Message:Tap on screen for capture Image."));
			_marqueeText.setTypeface(faceCaption);
			_marqueeText.setEllipsize(TruncateAt.MARQUEE);
			_marqueeText.setHorizontallyScrolling(true);
			_marqueeText.setSingleLine();
			_marqueeText.setMarqueeRepeatLimit(-1);

			_marqueeText.setSelected(true);
			_marqueeText.setGravity(Gravity.CENTER_HORIZONTAL);
			_marqueeText.setPadding(1, 10, 10, 0);

			mybuttonlayout.addView(_marqueeText);
		}
		else
		{
			button_capture=new Button(context);
			button_capture.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,(int)(h*.05)));
			button_capture.setText("Capture");
			mybuttonlayout.addView(button_capture);
			button_capture.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					// Obtain MotionEvent object
					v.setEnabled(false);
					mPreview.setNeedToTakePic(true);
					long downTime = SystemClock.uptimeMillis();
					long eventTime = SystemClock.uptimeMillis() + 100;
					float x = mPreview.getWidth() / 2;
					float y = mPreview.getHeight() / 2;
					// List of meta states found here:     developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
					int metaState = 0;
					MotionEvent motionEvent = MotionEvent.obtain(
							downTime,
							eventTime,
							MotionEvent.ACTION_DOWN,
							x,
							y,
							metaState
					);
					// Dispatch touch event to view
					mPreview.dispatchTouchEvent(motionEvent);
				}
			});
		}

		mLayout.addView(mybuttonlayout);
		setContentView(mLayout);
	}


	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		prev_state=false;
		mCamera.stopPreview();
		setResult(RESULT_FIRST_USER);
		finish();

	}

	@SuppressLint("NewApi")
	private Camera getCameraInstance()
	{
		try
		{
			if(camera==null)
				camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return camera;
	}

	@Override
	protected void onResume()
	{
		try
		{
			super.onResume();
			mCamera = getCameraInstance();
			mPreview = new CameraPreview(this, 0, CameraPreview.LayoutMode.FitToParent,mCamera,context);
			LayoutParams previewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			mLayout.addView(mPreview, 0, previewLayoutParams);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause()
	{
		try
		{
			super.onPause();
			mPreview.stop();
			mLayout.removeView(mPreview);	 // This is necessary.
			mPreview = null;
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Camera.PictureCallback mPicture = new Camera.PictureCallback()
	{
		@Override
		public void onPictureTaken(byte[] data, Camera camera)
		{
			File pictureFile = getOutputMediaFile();
			if (pictureFile == null)
			{
				return;
			}
			try
			{
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				prev_state=false;
				mCamera.stopPreview();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				new cameraimageload().execute();
			}
		}
	};

	ProgressDialog dia;
	private class cameraimageload extends AsyncTask<Void, Void, Void>
	{
		File mediaFile ;
		@Override
		protected void onPreExecute()
		{
			runOnUiThread(new Runnable()
			{
				public void run()
				{
					dia = ProgressDialog.show(context, "","Image Processing..... ");
					mediaFile = new File(filePath);
				}
			});
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... arg0)
		{
			try
			{
				//if(!(g.appfor.equalsIgnoreCase("TIE")&& isTablet(getApplicationContext()) && getDeviceName().equalsIgnoreCase("Lenovo TAB2 A10-70L")))
				getCameraPhotoOrientation(context,g.urlm,filePath);
				performCrop(filePath);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result)
		{
			dia.dismiss();
			Toast.makeText(CameraMainActivity.this, "Image Captured", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
	}

	private void performCrop(String fileName)
	{
		try
		{
			Intent intent = new Intent(this, CropImage.class);
			intent.putExtra(CropImage.IMAGE_PATH, fileName);
			intent.putExtra(CropImage.SCALE, true);
			intent.putExtra(CropImage.ASPECT_X, 0);
			intent.putExtra(CropImage.ASPECT_Y, 0);
			intent.putExtra(CropImage.ORIENTATION_IN_DEGREES, 90);

			startActivityForResult(intent, PIC_CROP);
		}
		catch (Exception anfe)
		{
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			if (requestCode == PIC_CROP)
			{
				resultset=RESULT_OK;
			}
		}
		else if(resultCode==RESULT_CANCELED)
		{
			resultset=3;
		}
		setResult(resultset);
		finish();
		super.onActivityResult(requestCode, resultCode, data);
	}




	public void getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath)
	{
		int rotate = 0;
		try {
			context.getContentResolver().notifyChange(imageUri, null);
			File imageFile = new File(imagePath);

			ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

			switch (orientation)
			{
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
			}

			Log.i("RotateImage", "Exif orientation: " + orientation);
			Log.i("RotateImage", "Rotate value: " + rotate);
			setrotation(imagePath,rotate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void setrotation(String tempphoto,int rotate)
	{
		String photopath = tempphoto;
		Bitmap bmp = BitmapFactory.decodeFile(photopath);

		Matrix matrix = new Matrix();
		matrix.postRotate(rotate);
	//	bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
		bmp = Bitmap.createBitmap(bmp, 0, 0, (int)(bmp.getWidth()*.80), (int)(bmp.getHeight()*.80), matrix, true);    // add by vishal
		//bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
		FileOutputStream fOut;
		try
		{
			fOut = new FileOutputStream(tempphoto);
			bmp.compress(Bitmap.CompressFormat.JPEG,100, fOut);   // by vishal 100 t0 10
			fOut.flush();
			fOut.close();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



	@SuppressLint("NewApi")
	private static File getOutputMediaFile()
	{
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MyCameraApp");

		try
		{
			if (!mediaStorageDir.exists())
			{
				if (!mediaStorageDir.mkdirs())
				{
					Log.d("MyCameraApp", "failed to create directory");
					return null;
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File mediaFile;
		// filePath=mediaStorageDir.getPath() + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg";
		mediaFile = new File(filePath);
		if (mediaFile.exists())
			mediaFile.delete();
		return mediaFile;
	}


	private static void DeleteMediaFile()
	{
		File mediaFile = new File(filePath);
		if (mediaFile.exists())
			mediaFile.delete();
	}

	@Override
	public void onFocused()
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if (mPreview.isNeedToTakePic())
				{
					mPreview.setNeedToTakePic(false);
					mCamera.takePicture(null, null, mPicture);
				}
			}
		}, 500);
	}

	public static boolean isTablet(Context ctx)
	{
		return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	/** Returns the consumer friendly device name */
	public static String getDeviceName()
	{
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer))
		{
			return capitalize(model);
		}
		return capitalize(manufacturer) + " " + model;
	}

	private static String capitalize(String str)
	{
		if (TextUtils.isEmpty(str))
		{
			return str;
		}
		char[] arr = str.toCharArray();
		boolean capitalizeNext = true;

		StringBuilder phrase = new StringBuilder();
		for (char c : arr)
		{
			if (capitalizeNext && Character.isLetter(c))
			{
				phrase.append(Character.toUpperCase(c));
				capitalizeNext = false;
				continue;
			}
			else if (Character.isWhitespace(c))
			{
				capitalizeNext = true;
			}
			phrase.append(c);
		}

		return phrase.toString();
	}


}
