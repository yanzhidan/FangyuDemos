package com.fangyu.demos.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.util.Log;

/**
 * 
 * @description Test Messenger service
 * 
 */
public class MessengerService extends Service {
	private Messenger messenger = new Messenger(new InHandler());

	class InHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			servicePrint();
		}
	}

	@Override
	public void onCreate() {
		Log.e("View", "Service****" + getPackageName() + " pid: " + Process.myPid());
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.e("View", "*onBind");
		return messenger.getBinder();
	}

	class MyBinder extends Binder {
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.e("View", "*onUnbind");
		return true;
	}

	@Override
	public void onRebind(Intent intent) {
		Log.e("View", "*onRebind");
		super.onRebind(intent);
	}

	public void servicePrint() {
		Log.e("View", "this is from service");
	}
}
