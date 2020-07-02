package com.erp.collection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class AlarmReceiverInput extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent Intent1=new Intent(Intent.ACTION_SYNC,null,context,GoogleServices.class);
        Intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(intent);
        intent.putExtra("KEY1","value to be used by the service");
        //context.startService(Intent1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent1);
       } else {
            context.startService(Intent1);
       }

    }
}



   // startForgroundService()