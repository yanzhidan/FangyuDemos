package com.fangyu.demos.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @description Test AIDL
 * 
 */
public class RemoteService extends Service {
	private int pid;
	private final RemoteCallbackList<IRemoteServiceCallback> mCallbacks = new RemoteCallbackList<IRemoteServiceCallback>();

	IRemoteService.Stub binder = new IRemoteService.Stub() {
		@Override
		public void remoteAction() throws RemoteException {
			Log.e("View", "this is a remoteService " + pid);
		}

		@Override
		public int getPid() throws RemoteException {
			return pid;
		}

		@Override
		public IRemoteData getRemoteData(IRemoteData data) throws RemoteException {
			data.name = "我来自Service";
			return data;
		}

		@Override
		public void registerCallback(IRemoteServiceCallback callback) throws RemoteException {
			if (callback != null)
				mCallbacks.register(callback);
		}

		@Override
		public void unregisterCallback(IRemoteServiceCallback callback) throws RemoteException {
			if(callback != null)
				mCallbacks.unregister(callback);
		}

	};

	public void onCreate() {
		pid = Process.myPid();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		Toast.makeText(this, "Task removed: " + rootIntent, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onDestroy() {
		mCallbacks.kill();
	}
}
