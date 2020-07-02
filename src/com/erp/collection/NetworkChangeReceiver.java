package com.erp.collection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NetworkChangeReceiver extends BroadcastReceiver {

	public static ConnectionReceiverListener connectionReceiverListener;
	
	@Override
	public void onReceive(final Context context, final Intent intent) {

		boolean isConnected = NetworkUtil.getConnectivityStatusString(context);

		//Toast.makeText(context, status, Toast.LENGTH_LONG).show();
		
		if (connectionReceiverListener != null)
		{
            connectionReceiverListener.onNetworkConnectionChanged(isConnected);
        }
	}
	
	
	public interface ConnectionReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}