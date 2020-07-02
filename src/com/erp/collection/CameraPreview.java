package com.erp.collection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.androidcustomcam.interfaces.OnFocusListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.PreviewCallback;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;
import android.widget.Toast;
//import com.example.androidcustomcam.interfaces.OnFocusListener;

/**
 * This class assumes the parent layout is RelativeLayout.LayoutParams.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static boolean DEBUGGING = true;
    private static final String LOG_TAG = "CameraPreviewSample";
    private static final String CAMERA_PARAM_ORIENTATION = "orientation";
    private static final String CAMERA_PARAM_LANDSCAPE = "landscape";
    private static final String CAMERA_PARAM_PORTRAIT = "portrait";
    protected Activity mActivity;
    private SurfaceHolder mHolder;
    protected Camera mCamera;
    protected List<Camera.Size> mPreviewSizeList;
    protected List<Camera.Size> mPictureSizeList;
    protected Camera.Size mPreviewSize;
    protected Camera.Size mPictureSize;
    private int mSurfaceChangedCallDepth = 0;
    private int mCameraId;
    private LayoutMode mLayoutMode;
    private int mCenterPosX = -1;
    private int mCenterPosY;
    boolean toughStop=true;
    
    PreviewReadyCallback mPreviewReadyCallback = null;
    private OnFocusListener onFocusListener;
    private boolean needToTakePic = false;
    boolean fixedfocusmode=false;
    Globals g;
    
    
    public static enum LayoutMode 
    {
        FitToParent, // Scale to the size that no side is larger than the parent
        NoBlank // Scale to the size that no side is smaller than the parent
    };
    
    public interface PreviewReadyCallback
    {
        public void onPreviewReady();
    } 
    /**
     * State flag: true when surface's layout size is set and surfaceChanged()
     * process has not been completed.
     */
    protected boolean mSurfaceConfiguring = false;

    @SuppressLint("NewApi")
	public CameraPreview(Activity activity, int cameraId, LayoutMode mode,Camera mcamera,Context context) 
    {
        super(activity); // Always necessary
        mActivity = activity;
        mLayoutMode = mode;
        mHolder = getHolder();
        mHolder.addCallback(this);
        g=Globals.getInstance(context);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            if (Camera.getNumberOfCameras() > cameraId) 
            {
                mCameraId = cameraId;
            } 
            else
            {
                mCameraId = 0;
            }
        }
        else
        {
            mCameraId = 0;
        }*/                      
        this.mCamera=mcamera;
        if(mCamera==null)
        	mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
       
        if (mCamera.getParameters()!=null)
        {
        	 Camera.Parameters cameraParams = mCamera.getParameters();
             mPreviewSizeList = cameraParams.getSupportedPreviewSizes();
             mPictureSizeList = cameraParams.getSupportedPictureSizes();
        }
       
        this.onFocusListener = (OnFocusListener) context;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        try 
        {
            mCamera.setPreviewDisplay(mHolder);
        } 
        catch (IOException e)
        {
        	mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        mSurfaceChangedCallDepth++;
        doSurfaceChanged(width, height);
        mSurfaceChangedCallDepth--;
    }

    private void doSurfaceChanged(int width, int height)
    {
        mCamera.stopPreview();
        
        Camera.Parameters cameraParams = mCamera.getParameters();
        boolean portrait = isPortrait();
        // The code in this if-statement is prevented from executed again when surfaceChanged is
        // called again due to the change of the layout size in this if-statement.
        if (!mSurfaceConfiguring)
        {
            Camera.Size previewSize = determinePreviewSize(portrait, width, height);
            Camera.Size pictureSize = determinePictureSize(previewSize);
            if (DEBUGGING) 
            { 
            	Log.v(LOG_TAG, "Desired Preview Size - w: " + width + ", h: " + height); 
            }
            mPreviewSize = previewSize;
            mPictureSize = pictureSize;
            mSurfaceConfiguring = adjustSurfaceLayoutSize(previewSize, portrait, width, height);
            // Continue executing this method if this method is called recursively.
            // Recursive call of surfaceChanged is very special case, which is a path from
            // the catch clause at the end of this method.
            // The later part of this method should be executed as well in the recursive
            // invocation of this method, because the layout change made in this recursive
            // call will not trigger another invocation of this method.
            if (mSurfaceConfiguring && (mSurfaceChangedCallDepth <= 1))
            {
                return;
            }
        }

        configureCameraParameters(cameraParams, portrait);
        mSurfaceConfiguring = false;

        try
        {
            mCamera.startPreview();
        }
        catch (Exception e)
        {
            Log.w(LOG_TAG, "Failed to start preview: " + e.getMessage());
            // Remove failed size
            mPreviewSizeList.remove(mPreviewSize);
            mPreviewSize = null;
            // Reconfigure
            if (mPreviewSizeList.size() > 0)
            { // prevent infinite loop
                surfaceChanged(null, 0, width, height);
            } 
            else
            {
                Toast.makeText(mActivity, "Can't start preview", Toast.LENGTH_LONG).show();
                Log.w(LOG_TAG, "Gave up starting preview");
            }
        }        
        if (null != mPreviewReadyCallback)
        {
            mPreviewReadyCallback.onPreviewReady();
        }
    }
    
    /**
     * @param cameraParams
     * @param portrait
     * @param reqWidth must be the value of the parameter passed in surfaceChanged
     * @param reqHeight must be the value of the parameter passed in surfaceChanged
     * @return Camera.Size object that is an element of the list returned from Camera.Parameters.getSupportedPreviewSizes.
     */
    protected Camera.Size determinePreviewSize(boolean portrait, int reqWidth, int reqHeight) 
    {
        // Meaning of width and height is switched for preview when portrait,
        // while it is the same as user's view for surface and metrics.
        // That is, width must always be larger than height for setPreviewSize.
        int reqPreviewWidth; // requested width in terms of camera hardware
        int reqPreviewHeight; // requested height in terms of camera hardware
        if (portrait) {
            reqPreviewWidth = reqHeight;
            reqPreviewHeight = reqWidth;
        } else {
            reqPreviewWidth = reqWidth;
            reqPreviewHeight = reqHeight;
        }

        if (DEBUGGING)
        {
            Log.v(LOG_TAG, "Listing all supported preview sizes");
            for (Camera.Size size : mPreviewSizeList) {
                Log.v(LOG_TAG, "  w: " + size.width + ", h: " + size.height);   // vishal
            }
            Log.v(LOG_TAG, "Listing all supported picture sizes");
            for (Camera.Size size : mPictureSizeList) {
                Log.v(LOG_TAG, "  w: " + size.width + ", h: " + size.height);
            }
        }
        // Adjust surface size with the closest aspect-ratio
        float reqRatio = ((float) reqPreviewWidth) / reqPreviewHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : mPreviewSizeList) 
        {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin)
            {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }
        return retSize;
    }

    protected Camera.Size determinePictureSize(Camera.Size previewSize) 
    {
        Camera.Size retSize = null;
        int i=0;               
        for (Camera.Size size : mPictureSizeList) 
        {
        	if(i==0)
        	{
        		retSize= size;
        		i++;
        	}        		
            if (size.height>retSize.height) 
            {
            	retSize= size;
            }
        }        
        return retSize;
    }
    
    protected boolean adjustSurfaceLayoutSize(Camera.Size previewSize, boolean portrait,int availableWidth, int availableHeight)
    {
        float tmpLayoutHeight, tmpLayoutWidth;
        if (portrait)
        {
            tmpLayoutHeight = previewSize.width;
            tmpLayoutWidth = previewSize.height;
        }
        else 
        {
            tmpLayoutHeight = previewSize.height;
            tmpLayoutWidth = previewSize.width;
        }

        float factH, factW, fact;
        factH = availableHeight / tmpLayoutHeight;
        factW = availableWidth / tmpLayoutWidth;
        if (mLayoutMode == LayoutMode.FitToParent)        
        {
            // Select smaller factor, because the surface cannot be set to the size larger than display metrics.
            if (factH < factW) 
            {
                fact = factH;
            } 
            else
            {
                fact = factW;
            }
        } 
        else
        {
            if (factH < factW) {
                fact = factW;
            } else {
                fact = factH;
            }
        }

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this.getLayoutParams();

        int layoutHeight = (int) (tmpLayoutHeight * fact);
        int layoutWidth = (int) (tmpLayoutWidth * fact);
        if (DEBUGGING) {
            Log.v(LOG_TAG, "Preview Layout Size - w: " + layoutWidth + ", h: " + layoutHeight);
            Log.v(LOG_TAG, "Scale factor: " + fact);
        }

        boolean layoutChanged;
        if ((layoutWidth != this.getWidth()) || (layoutHeight != this.getHeight())) {
            layoutParams.height = layoutHeight;
            layoutParams.width = layoutWidth;
            if (mCenterPosX >= 0) {
                layoutParams.topMargin = mCenterPosY - (layoutHeight / 2);
                layoutParams.leftMargin = mCenterPosX - (layoutWidth / 2);
            }
            this.setLayoutParams(layoutParams); // this will trigger another surfaceChanged invocation.
            layoutChanged = true;
        } else {
            layoutChanged = false;
        }

        return layoutChanged;
    }
    /**
     * @param x X coordinate of center position on the screen. Set to negative value to unset.
     * @param y Y coordinate of center position on the screen.
     */
    public void setCenterPosition(int x, int y) 
    {
        mCenterPosX = x;
        mCenterPosY = y;
    }
    
    @SuppressWarnings("deprecation")
	@SuppressLint("NewApi") 
    protected void configureCameraParameters(Camera.Parameters cameraParams, boolean portrait) 
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO)
        { 
        	// for 2.1 and before
            if(portrait) 
            {
                cameraParams.set(CAMERA_PARAM_ORIENTATION, CAMERA_PARAM_PORTRAIT);
            }
            else
            {
                cameraParams.set(CAMERA_PARAM_ORIENTATION, CAMERA_PARAM_LANDSCAPE);
            }
        }
        else
        {
        	// for 2.2 and later
            int angle;
            Display display = mActivity.getWindowManager().getDefaultDisplay();
            switch (display.getRotation())
            {
                case Surface.ROTATION_0: // This is display orientation
                    angle = 90; // This is camera orientation
                    break;
                case Surface.ROTATION_90:
                    angle = 0;
                    break;
                case Surface.ROTATION_180:
                    angle = 270;
                    break;
                case Surface.ROTATION_270:
                    angle = 180;
                    break;
                default:
                    angle = 90;
                    break;
            }
            //Log.v(LOG_TAG, "angle: " + angle);
            /*if(g.appfor.equalsIgnoreCase("TIE")&& isTablet(mActivity.getApplicationContext()) && getDeviceName().equalsIgnoreCase("LENOVO Lenovo TAB 2 A10-70L"))
            	mCamera.setDisplayOrientation((int)(360)-(display.getRotation()* 90));
            else   */        	       	
            	mCamera.setDisplayOrientation(angle);
        }
        cameraParams.set("orientation", "portrait");
        cameraParams.setRotation(90);
        cameraParams.setJpegQuality(10);                                                         // vishal tyagi 100
        
        List<String> supported_colour_effects_list = cameraParams.getSupportedWhiteBalance();
        if (supported_colour_effects_list.contains(cameraParams.WHITE_BALANCE_AUTO))
        	cameraParams.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
                
        List<String> FLASH_MODE = cameraParams.getSupportedFlashModes();
        try
        {
	        if (FLASH_MODE.contains(cameraParams.FLASH_MODE_AUTO))  	    
	        	cameraParams.setFlashMode(cameraParams.FLASH_MODE_AUTO);
        }
        catch (Exception e) 
        {
			// TODO: handle exception
		}

        @SuppressWarnings("deprecation")
		List<String> focusModes = cameraParams.getSupportedFocusModes();   
        try
        {
	        if (focusModes.contains(cameraParams.FOCUS_MODE_MACRO))
	        {
	        	cameraParams.setFocusMode(cameraParams.FOCUS_MODE_MACRO);
	        }
	        else if (focusModes.contains(cameraParams.FOCUS_MODE_AUTO))
	        {
	        	cameraParams.setFocusMode(cameraParams.FOCUS_MODE_AUTO);
	        }
	        else if (focusModes.contains(cameraParams.FOCUS_MODE_FIXED))
	        {
	        	fixedfocusmode=true;
	        	cameraParams.setFocusMode(cameraParams.FOCUS_MODE_FIXED);
	        }
        }
        catch (Exception e)
        {
			// TODO: handle exception
		}
        cameraParams.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        cameraParams.setPictureSize(mPictureSize.width, mPictureSize.height); 

      //  cameraParams.setPictureSize(4128,3096);
        if (DEBUGGING) 
        {
            Log.v(LOG_TAG, "Preview Actual Size - w: " + mPreviewSize.width + ", h: " + mPreviewSize.height);
            Log.v(LOG_TAG, "Picture Actual Size - w: " + mPictureSize.width + ", h: " + mPictureSize.height);
        }

        mCamera.setParameters(cameraParams);
    }
                         
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }
    
    public void stop() 
    {
        if (null == mCamera) {
            return;
        }
        mCamera.stopPreview();
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;
    }

    public boolean isPortrait() {
        return (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }
    
    public void setOneShotPreviewCallback(PreviewCallback callback) {
        if (null == mCamera) {
            return;
        }
        mCamera.setOneShotPreviewCallback(callback);
    }
    
    public void setPreviewCallback(PreviewCallback callback) {
        if (null == mCamera) {
            return;
        }
        mCamera.setPreviewCallback(callback);
    }
    
    public Camera.Size getPreviewSize() {
        return mPreviewSize;
    }
    
    public void setOnPreviewReady(PreviewReadyCallback cb) 
    {
        mPreviewReadyCallback = cb;
    }
    
    
    //================================================================================
       
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
    	if(toughStop)
    	{
    		if (event.getAction() == MotionEvent.ACTION_DOWN)   		
    			focusOnTouch(event);         
    	}
         return true;    	   
    }
    @SuppressLint("NewApi") 
    private void focusOnTouch(MotionEvent event) 
    {
    	 if (mCamera != null)
 	     {
    		    int pointerId = event.getPointerId(0);
    	        int pointerIndex = event.findPointerIndex(pointerId);
    	        // Get the pointer's current position
    	        float x = event.getX(pointerIndex);
    	        float y = event.getY(pointerIndex);
    		     		
    		 	Camera.Parameters parameters = mCamera.getParameters();
    		 	 mCamera.cancelAutoFocus();
                 Rect focusRect = calculateTapArea(x,y, 1f);
                 if(!fixedfocusmode)
                 {                 
	                 if (parameters.getFocusMode() != Camera.Parameters.FOCUS_MODE_AUTO) 
	                 {
	                     parameters.setFocusMode(parameters.FOCUS_MODE_AUTO);
	                 }
                 }
                 else
                 {
                	 parameters.setFocusMode(parameters.FOCUS_MODE_INFINITY);
                 }
                 if (parameters.getMaxNumFocusAreas() > 0 ||fixedfocusmode) 
                 {
                     List<Area> mylist = new ArrayList<Area>();
                     mylist.add(new Camera.Area(focusRect, 1000));
                     parameters.setFocusAreas(mylist);
                 }
                 try 
                 {
                	 mCamera.cancelAutoFocus();
                	 mCamera.setParameters(parameters);
                	 mCamera.startPreview();
                	 setNeedToTakePic(true);
                     mCamera.autoFocus(mAutoFocusTakePictureCallback);                	
                 } 
                 catch (Exception e) 
                 {
                     e.printStackTrace();
                 }
             }                 
    }

  
    private Rect calculateTapArea(float x, float y, float coefficient)
    {
    	Matrix matrix=new Matrix();
    	int focusAreaSize=300;                                                 // vishal 300
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();

        int left = clamp((int) x - areaSize / 2, 0, mPreviewSize.width - areaSize);
        int top = clamp((int) y - areaSize / 2, 0, mPreviewSize.height - areaSize);

        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        matrix.mapRect(rectF);

        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }
    
    
    public boolean isNeedToTakePic() 
    {
        return needToTakePic;
    }

    public void setNeedToTakePic(boolean needToTakePic) 
    {
        this.needToTakePic = needToTakePic;
    }
    
    @SuppressWarnings("deprecation")
   	private Camera.AutoFocusCallback mAutoFocusTakePictureCallback = new Camera.AutoFocusCallback() {
           @Override
           public void onAutoFocus(boolean success, Camera camera) 
           {        	        
               if (success) 
               {    
               	 if (isNeedToTakePic())
                    {
               		 toughStop=false;
                        onFocusListener.onFocused();
                    }                 
               } 
               else
               {     
               	setNeedToTakePic(false);
               	camera.cancelAutoFocus();
               	/* if (mCamera != null )
                    {          
            			Camera.Parameters parameters = mCamera.getParameters();
            			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    }*/
               	
               	camera.autoFocus(mAutoFocusTakePictureCallback);
               	
                   Log.i("tap_to_focus","fail!");
               }
           }
       };
       
       
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
/*import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.androidcustomcam.interfaces.OnFocusListener;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
  
	private Camera mCamera;
    private OnFocusListener onFocusListener;

    private boolean needToTakePic = false;
    List<Camera.Size>  mSupportedPreviewSizes;
    Camera.Size mPreviewSize;
  //  Activity activity;
  

    // Constructor that obtains context and camera
    public CameraPreview(Context context, Camera camera)
    {
        super(context);
        this.mCamera = camera;
        this.mSurfaceHolder = this.getHolder();
        this.mSurfaceHolder.addCallback(this);
        this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.onFocusListener = (OnFocusListener) context;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) 
    {
		try
		{
			mCamera.setPreviewDisplay(surfaceHolder);			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}      
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
    	 try 
    	 {
             if (mCamera != null) 
             {
            	 mCamera.setPreviewCallback(null);
            	 mCamera.stopPreview();  
            	 mCamera.release();
             }
         } 
    	 catch (Exception e)
    	 {            
         }    
    }

    @SuppressWarnings("deprecation")
	@Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) 
    {       
        try
        {        	
        	mCamera.setDisplayOrientation(90);	       	 
    	    Camera.Parameters parameters = mCamera.getParameters();
    	    Camera.Parameters parameters1 = parameters;
    	    try
    	    {    	    
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
				mCamera.setParameters(parameters);
				parameters1=parameters;
			}
    	    catch (Exception e1) 
			{				
				e1.printStackTrace();
				parameters=mCamera.getParameters();
			}
    	    try
    	    {    	    	
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
				mCamera.setParameters(parameters);
				parameters1=parameters;
			} 
    	    catch (Exception e) 
    	    {				
				parameters=mCamera.getParameters();
				parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				mCamera.setParameters(parameters);
				parameters1=parameters;
				e.printStackTrace();
			}    	  
			try 
			{
				List<String> supported_colour_effects_list = parameters.getSupportedWhiteBalance();
				parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
				mCamera.setParameters(parameters);
				parameters1 = parameters;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				parameters = mCamera.getParameters();
			} 	    	        
    	    try
    	    {
				parameters.set("orientation", "portrait");
				mCamera.setParameters(parameters);
				parameters1=parameters;
			}
    	    catch (Exception e)
    	    {				
				e.printStackTrace();
				parameters=mCamera.getParameters();
			}    	  
    	    try 
    	    {
				parameters.setPictureFormat(ImageFormat.JPEG);
				mCamera.setParameters(parameters);
				parameters1=parameters;				
			} 
    	    catch (Exception e) 
    	    {
				e.printStackTrace();
				parameters=mCamera.getParameters();
			}
    	    try 
    	    {
				parameters.setJpegQuality(100);
				mCamera.setParameters(parameters);
				parameters1=parameters;
			}
    	    catch (Exception e) 
    	    {
				e.printStackTrace();
				parameters=mCamera.getParameters();
			}
    	    try
    	    {
				parameters.setRotation(90);
				mCamera.setParameters(parameters);
				parameters1=parameters;
			} 
    	    catch (Exception e) 
    	    {
				e.printStackTrace();
				parameters=mCamera.getParameters();
			}    	        	   
    	    try 
    	    {
				parameters.setSceneMode(Camera.Parameters.SCENE_MODE_BARCODE);
				mCamera.setParameters(parameters);
				parameters1=parameters;
			} 
    	    catch (Exception e1) 
    	    {			
				e1.printStackTrace();
				parameters=mCamera.getParameters();
			}    	        	   
    	    try 
    	    {
				Camera.Size size = getOptimalPicSize(height,width,parameters);
				parameters.setPreviewSize(size.width, size.height);				
				mCamera.setParameters(parameters);				
			} 
    	    catch (Exception e) 
			{
				parameters=mCamera.getParameters();
				e.printStackTrace();				
			} 
    	    if (parameters.getAntibanding() != null)
    	    {
    	    	parameters.setAntibanding(Camera.Parameters.ANTIBANDING_AUTO);
    	    }    	   
    	    if (parameters.isZoomSupported()) 
    	    {
    	    	parameters.setZoom(1);
    	    }    	 
            mCamera.startPreview();
          //  mCamera.autoFocus(mAutoFocusTakePictureCallback); 
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void doTouchFocus(final Rect tfocusRect)
    {
        try 
        {
            @SuppressWarnings("deprecation")
			List<Camera.Area> focusList = new ArrayList<Camera.Area>();
            Camera.Area focusArea = new Camera.Area(tfocusRect, 1000);
            focusList.add(focusArea);

            Camera.Parameters param = mCamera.getParameters();
            param.setFocusAreas(focusList);
            param.setMeteringAreas(focusList);
            mCamera.setParameters(param);

          //  mCamera.autoFocus(myAutoFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isNeedToTakePic())
        {
            onFocusListener.onFocused();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
    	 if (event.getAction() == MotionEvent.ACTION_DOWN)
    	 {
             focusOnTouch(event);
         }
         return true;
    	    
    }
    @SuppressWarnings("deprecation")
    private void focusOnTouch(MotionEvent event) 
    {
        if (mCamera != null )
        {          
			Camera.Parameters parameters = mCamera.getParameters();
            Rect rect = calculateFocusArea(event.getX(), event.getY());
            if (parameters.getMaxNumMeteringAreas() > 0)
            {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();
                meteringAreas.add(new Camera.Area(rect, 800));
                parameters.setFocusAreas(meteringAreas);

                mCamera.setParameters(parameters);
                setNeedToTakePic(true);
                mCamera.autoFocus(mAutoFocusTakePictureCallback);
            }
            else if (parameters.getMaxNumFocusAreas() > 0) 
            {
            	 parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            	List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
            	focusAreas.add(new Camera.Area(rect, 1000));
            
            	parameters.setFocusAreas(focusAreas);
            	//setNeedToTakePic(true);
            	mCamera.autoFocus(mAutoFocusTakePictureCallback);
            }
            
            else
            {

        		setNeedToTakePic(true);
                mCamera.autoFocus(mAutoFocusTakePictureCallback);
            }                     
           
        } 
    }

    private Rect calculateFocusArea(float x, float y) 
    {
    	int FOCUS_AREA_SIZE=300;
        int left = clamp(Float.valueOf((x / this.getWidth()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);
        int top = clamp(Float.valueOf((y / this.getHeight()) * 2000 - 1000).intValue(), FOCUS_AREA_SIZE);

        return new Rect(left, top, left + FOCUS_AREA_SIZE, top + FOCUS_AREA_SIZE);
    }

    private int clamp(int touchCoordinateInCameraReper, int focusAreaSize) 
    {
        int result;
        if (Math.abs(touchCoordinateInCameraReper)+focusAreaSize/2>1000)
        {
            if (touchCoordinateInCameraReper>0)
            {
                result = 1000 - focusAreaSize/2;
            } 
            else 
            {
                result = -1000 + focusAreaSize/2;
            }
        } 
        else
        {
             result = touchCoordinateInCameraReper - focusAreaSize/2;
        }
        return result;
    }
    
    @SuppressWarnings("deprecation")
	private Camera.AutoFocusCallback mAutoFocusTakePictureCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) 
        {        	        
            if (success) 
            {    

            	 if (isNeedToTakePic())
                 {
                     onFocusListener.onFocused();
                 }                 
            } 
            else
            {     
            	setNeedToTakePic(false);
            	camera.cancelAutoFocus();
            	 if (mCamera != null )
                 {          
         			Camera.Parameters parameters = mCamera.getParameters();
         			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                 }
            	
            	camera.autoFocus(mAutoFocusTakePictureCallback);
            	
                Log.i("tap_to_focus","fail!");
            }
        }
    };
    
    public boolean isNeedToTakePic() 
    {
        return needToTakePic;
    }

    public void setNeedToTakePic(boolean needToTakePic) 
    {
        this.needToTakePic = needToTakePic;
    }

    //preview size
    
    
    private Camera.Size getOptimalPreviewSize(int w, int h,Camera.Parameters parameters)
    {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;
        Camera.Size optimalSize = null;
        
        
        List<Camera.Size> sizes=parameters.getSupportedPreviewSizes();

        if (sizes == null)
            return null;

        
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) 
        {
            double ratio = (double) size.height / size.width;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize == null) 
        {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) 
            {
            	if(size!=null)
            	{
	                if (Math.abs(size.height - targetHeight) < minDiff) 
	                {                	
	                    optimalSize = size;
	                    minDiff = Math.abs(size.height - targetHeight);
	                    //return size;
	                }
            	}
            }
        }
        return optimalSize;
    }
    
    private Camera.Size getOptimalPicSize(int w, int h,Camera.Parameters parameters)
    {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;
        Camera.Size optimalSize = null;
        
        
        List<Camera.Size> sizes=parameters.getSupportedPictureSizes();

        if (sizes == null)
            return null;

        
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) 
        {
            double ratio = (double) size.height / size.width;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;

            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize == null) 
        {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) 
            {
            	if(size!=null)
            	{
	                if (Math.abs(size.height - targetHeight) < minDiff) 
	                {                	
	                    optimalSize = size;
	                    minDiff = Math.abs(size.height - targetHeight);
	                    //return size;
	                }
            	}
            }
        }
        return optimalSize;
    }
    
    
///========================================================================================================
    
    
    public boolean isPortrait()
    {
        return (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }
	
    
    
    
    
    
}*/