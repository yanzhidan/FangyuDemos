package com.fangyu.demos.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

/**
 * 
 * @description Test AIDL
 * 
 */
public class RemoteService extends Service {
	private int pid;
	IRemoteService.Stub binder = new IRemoteService.Stub() {
		@Override
		public void remoteAction() throws RemoteException {
			Log.e("View", "this is a remoteService " + pid);
		}

		@Override
		public int getPid() throws RemoteException {
			return pid;
		}

	};

	public void onCreate() {
		pid = Process.myPid();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
}
