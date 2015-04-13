package com.fangyu.demos.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fangyu.demos.R;

public class RemoteClient extends Activity
{
    IRemoteService remoteService;

    ServiceConnection serviceConnection = new ServiceConnection()
    {

	@Override
	public void onServiceDisconnected(ComponentName name)
	{
	    Log.e("View", RemoteClient.class.getSimpleName() + "*onServiceDisconnected");
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service)
	{
	    Log.e("View", RemoteClient.class.getSimpleName() + "*onServiceConnected");
	    remoteService = IRemoteService.Stub.asInterface(service);
	}
    };

    IRemoteServiceCallback callback = new IRemoteServiceCallback.Stub()
    {

	@Override
	public void valueChanged(int value) throws RemoteException
	{
	    Log.e("View", "RemoteClient * value: " + value);
	}

	@Override
	public void doAnything() throws RemoteException
	{

	}

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	Log.e("View", "onCreate " + Process.myUid() + " p: " + Process.myPid());
	setContentView(R.layout.activity_client);
	Button button = (Button) findViewById(R.id.button);
	button.setText("remoteSend");
	Intent intent = new Intent(this, RemoteService.class);
	bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    boolean clicked = false;

    public void remoteAction(View view)
    {
	try
	{
	    if (!clicked)
	    {
		remoteService.registerCallback(callback);
		clicked = true;
	    }
	    else
	    {
		IRemoteData data = remoteService.getRemoteData(new IRemoteData());
		Log.e("View", "remoteData: " + data.name);
	    }
	}
	catch (RemoteException e)
	{
	    e.printStackTrace();
	}
    }

    @Override
    protected void onDestroy()
    {
	super.onDestroy();
	try
	{
	    remoteService.unregisterCallback(callback);
	}
	catch (RemoteException e)
	{
	    e.printStackTrace();
	}
	unbindService(serviceConnection);
    }

}
