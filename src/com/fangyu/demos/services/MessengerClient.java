package com.fangyu.demos.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fangyu.demos.R;

/**
 * 
 * @description Test Messenger client
 * 
 */
public class MessengerClient extends Activity {
	Messenger messenger;
	Messenger acMessenger = new Messenger(new OutHandler());
	private int pid;

	class OutHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			clientPrint();
		}
	}

	ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e("View", "*onServiceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("View", "*onServiceConnected");
			Toast.makeText(getApplicationContext(), "onServiceConnected", 0).show();
			messenger = new Messenger(service);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);
		Button button = (Button) findViewById(R.id.button);
		button.setText("messengerSend");
		Intent intent = new Intent(this, MessengerService.class);
		bindService(intent, serviceConnection, BIND_AUTO_CREATE);
		pid = Process.myPid();
		Log.e("View", "Activity****" + getPackageName() + " pid: " + pid);
	}

	public void remoteAction(View view) {
		Message message = new Message();
		message.replyTo = acMessenger;
		message.what = 1;
		try {
			messenger.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void clientPrint() {
		Log.e("View", findViewById(R.id.button) + " this is from client " + pid);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("View", "clientDestroy" + pid);
		unbindService(serviceConnection);
	}
}
