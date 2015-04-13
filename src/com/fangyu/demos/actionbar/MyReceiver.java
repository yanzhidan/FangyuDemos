package com.fangyu.demos.actionbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
	Toast.makeText(context, "呵呵", 0).show();
	Log.e("View", "**********************onReceive  " + " process: " + Process.myPid());
    }
}
