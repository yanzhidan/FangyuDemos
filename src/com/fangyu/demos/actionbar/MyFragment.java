package com.fangyu.demos.actionbar;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangyu.demos.R;
import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.DownloadManager.Query;
import com.mozillaonline.providers.DownloadManager.Request;

public class MyFragment extends Fragment
{
    private DownloadManager downloadManager;
    private NotificationManager notificationManager;
    private Timer timer;
    private UpdateNotificationTask timerTask;
    private String str = "";
    private long currentDownloadId;
    private static final String tag = "MyFragment";

    public MyFragment()
    {
	str = "haha";
    }

    public MyFragment(String str)
    {
	this.str = str;
    }

    @Override
    public void onAttach(Activity activity)
    {
	super.onAttach(activity);
	Log.e("View", tag + "----> onAttach_" + str);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setHasOptionsMenu(true);
	timer = new Timer();
	downloadManager = new DownloadManager(getActivity().getContentResolver(), getActivity().getPackageName());
	getActivity().registerReceiver(new BroadcastReceiver()
	{

	    @Override
	    public void onReceive(Context context, Intent intent)
	    {
		Log.e("View", tag + "----> ACTION_DOWNLOAD_COMPLETE  " + str + " process: " + Process.myPid());
		updateNotification("gamelol.apk", 100);
		timerTask.cancel();
	    }
	}, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

	// getActivity().registerReceiver(new MyReciever(), new
	// IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	Log.e("View", tag + "----> onCreate_" + str + " process: " + Process.myPid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	Log.e("View", tag + "----> onCreateView_" + str);
	View rootView = inflater.inflate(R.layout.fragment_main, container, false);
	TextView tv = (TextView) rootView.findViewById(R.id.tv);
	tv.setText(str);
	return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
	// TODO Auto-generated method stub
	menu.add(0, 15, 10, "我来自Fragment");
	menu.add(0, 16, 10, "我也来自Fragment");
	Log.e("View", tag + "----> onCreateOptionsMenu_" + str + " *********");
	super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	// TODO Auto-generated method stub
	Log.e("View", tag + "----> onOptionsItemSelected_" + str + " *********");
	if (item.getItemId() == 15)
	{
	    excuteDownload();
	}
	else if (item.getItemId() == 16)
	{
	    updateNotification("", 10);
	}
	return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	super.onActivityCreated(savedInstanceState);
	Log.e("View", tag + "----> onActivityCreated_" + str);
    }

    private void excuteDownload()
    {
	String url = "http://172.16.16.149:8088/dota_update.apk";
	DownloadManager.Request request = new Request(Uri.parse(url));
	String path = "gamelol";
	File file = new File(path);
	if (!file.exists())
	{
	    file.mkdirs();
	}
	request.setDestinationInExternalPublicDir(path, "gamelol1.apk");
	// request.setDescription("Just for test");
	// request.setMimeType("download_test");
	request.setShowRunningNotification(false);
	currentDownloadId = downloadManager.enqueue(request);
	if (timerTask != null)
	{
	    timerTask.cancel();
	}
	timerTask = new UpdateNotificationTask();
	timer.schedule(timerTask, 0, 2000);
    }

    private void updateNotification(String title, int progress)
    {
	notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

	// Intent intent = new Intent(getActivity(), TestActivity.class);
	Intent intent = new Intent();
	intent.setAction(Intent.ACTION_SEND);
	intent.setType("image/*");
	Uri uri = Uri.fromFile(getActivity().getFileStreamPath("shared.png"));
	intent.putExtra(Intent.EXTRA_STREAM, uri);
	PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

	NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
	// RemoteViews views = new RemoteViews(getActivity().getPackageName(),
	// R.layout.notification_layout);
	builder.setContentTitle(title);
	if (progress != 100)
	{
	    builder.setProgress(100, progress, false);
	    builder.setContentText(progress + "%");
	}
	else
	{
	    builder.setContentText("下载完成");
	}
	builder.setSmallIcon(R.drawable.ic_launcher);
	builder.setContentIntent(pendingIntent);
	// builder.setContent(views);
	notificationManager.notify(1001, builder.build());
    }

    class UpdateNotificationTask extends TimerTask
    {
	@Override
	public void run()
	{
	    DownloadManager.Query query = new Query();
	    query.setFilterById(currentDownloadId);
	    Cursor cursor = downloadManager.query(query);
	    if (cursor.moveToFirst())
	    {
		int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
		String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
		String current = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
		String total = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
		Log.e("View", (status == DownloadManager.STATUS_RUNNING) + "  ** " + current + "  " + total);
		int progress = 100 * Integer.valueOf(current) / Integer.valueOf(total);
		if (status == DownloadManager.STATUS_RUNNING)
		{
		    updateNotification(title, progress);
		}
	    }
	}

    }

    @Override
    public void onStart()
    {
	// TODO Auto-generated method stub
	super.onStart();
	Log.e("View", tag + "----> onStart_" + str);
    }

    @Override
    public void onResume()
    {
	// TODO Auto-generated method stub
	super.onResume();
	Log.e("View", tag + "----> onResume_" + str);
    }

    @Override
    public void onPause()
    {
	// TODO Auto-generated method stub
	super.onPause();
	Log.e("View", tag + "----> onPause_" + str);
    }

    @Override
    public void onStop()
    {
	// TODO Auto-generated method stub
	super.onStop();
	Log.e("View", tag + "----> onStop_" + str);
    }

    @Override
    public void onDestroyView()
    {
	// TODO Auto-generated method stub
	super.onDestroyView();
	Log.e("View", tag + "----> onDestroyView_" + str);
    }

    @Override
    public void onDestroy()
    {
	// TODO Auto-generated method stub
	super.onDestroy();
	if (timer != null)
	{
	    timer.cancel();
	    timer = null;
	}
	Log.e("View", tag + "----> onDestroy_" + str);
    }

    @Override
    public void onDetach()
    {
	// TODO Auto-generated method stub
	super.onDetach();
	Log.e("View", tag + "----> onDetach_" + str);
    }
}
