package com.erp.collection;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by admin on 11/6/2017.
 */

public class DownloadResultReceiver extends ResultReceiver
{
    private Receiver mReceiver;
    public DownloadResultReceiver(Handler handler)
    {
        super(handler);
    }
    public void setReceiver(Receiver receiver)
    {
        mReceiver = receiver;
    }
    public interface Receiver
    {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData)
    {
        if (mReceiver != null)
        {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
