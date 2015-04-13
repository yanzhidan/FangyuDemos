package com.fangyu.demos.actionbar;

import java.io.File;
import java.lang.reflect.Method;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.Window;
import android.widget.Toast;

import com.fangyu.demos.R;

public class MainActivity extends ActionBarActivity implements TabListener
{
    private long currentDownloadId;
    private DownloadManager manager;
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	getSupportActionBar().setDisplayUseLogoEnabled(true);
	getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	if (savedInstanceState == null)
	{
	    getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment("original")).commit();
	}

	for (int i = 0; i < 4; i++)
	{
	    ActionBar.Tab tab = getSupportActionBar().newTab();
	    tab.setText("Tab--" + i);
	    tab.setTabListener(this);
	    getSupportActionBar().addTab(tab);
	}
	manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
	registerReceiver(receiver = new MyReceiver(), new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
	System.out.println("--- " + getComponentName().getClassName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	getMenuInflater().inflate(R.menu.main, menu);
	ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action3));
	actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
	actionProvider.setShareIntent(createShareIntent());

	SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action2));
	searchView.setOnQueryTextListener(mOnQueryTextListener);

	SubMenu subMenu1 = menu.addSubMenu(0, 12, 100, "action item");
	subMenu1.add(0, 100, 100, "Sample");
	subMenu1.add(0, 101, 100, "Menu");
	subMenu1.add(0, 102, 100, "Items");

	MenuItem subMenu1Item = subMenu1.getItem();
	subMenu1Item.setIcon(R.drawable.menu_lol_hero);
	subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
	System.out.println("onWindowFocusChanged");
	super.onWindowFocusChanged(hasFocus);
    }

    // 显示菜单图标
    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
	if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
	{
	    if (menu.getClass().getSimpleName().equals("MenuBuilder"))
	    {
		try
		{
		    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
		    m.setAccessible(true);
		    m.invoke(menu, true);
		}
		catch (Exception e)
		{
		}
	    }
	}
	return super.onMenuOpened(featureId, menu);
    }

    @Override
    public ActionMode onWindowStartingActionMode(Callback callback)
    {
	System.out.println("onWindowStartingActionMode");
	return super.onWindowStartingActionMode(callback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	int id = item.getItemId();
	if (id == android.R.id.home)
	{
	    Intent intent = new Intent(this, SecondActivity.class);
	    startActivity(intent);
	    return true;
	}
	else if (id == 100)
	{
	    excuteDownload();
	    Log.e("View", "100");
	}
	else if (id == 101)
	{
	    Log.e("View", "101");
	    manager.remove(currentDownloadId);
	}
	return super.onOptionsItemSelected(item);
    }

    private void excuteDownload()
    {
	String url = "http://172.16.16.149:8088/dota_update.apk";
	String path = "gamelol";
	File file = new File(path);
	if (!file.exists())
	{
	    file.mkdirs();
	}
	Request request = new Request(Uri.parse(url));
	request.setDestinationInExternalPublicDir(path, "talktvgame.apk");
	request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
	request.setMimeType("download_test");
	currentDownloadId = manager.enqueue(request);
    }

    private Intent createShareIntent()
    {
	Intent shareIntent = new Intent(Intent.ACTION_SEND);
	shareIntent.setType("image/*");
	Uri uri = Uri.fromFile(getFileStreamPath("shared.png"));
	shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
	return shareIntent;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft)
    {}

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft)
    {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft)
    {
	FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
	// ft1.remove(getSupportFragmentManager().findFragmentById(R.id.container));
	ft1.replace(R.id.container, new MyFragment("你妹"));
	// ft1.addToBackStack(null);
	ft1.commit();
    }

    @Override
    protected void onDestroy()
    {
	super.onDestroy();
	unregisterReceiver(receiver);
    }

    private final SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener()
    {
	@Override
	public boolean onQueryTextChange(String newText)
	{
	    newText = TextUtils.isEmpty(newText) ? "" : "Query so far: " + newText;
	    return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
	    Toast.makeText(MainActivity.this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
	    return true;
	}
    };

}
