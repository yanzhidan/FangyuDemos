package com.fangyu.demos.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fangyu.demos.R;

public class RemoteClient extends Activity {
	IRemoteService remoteService;

	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e("View", RemoteClient.class.getSimpleName() + "*onServiceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("View", RemoteClient.class.getSimpleName() + "*onServiceConnected");
			remoteService = IRemoteService.Stub.asInterface(service);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);
		Button button = (Button) findViewById(R.id.button);
		button.setText("remoteSend");
		Intent intent = new Intent(this, RemoteService.class);
		bindService(intent, serviceConnection, BIND_AUTO_CREATE);
	}

	public void remoteAction(View view) {
		try {
			remoteService.remoteAction();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
