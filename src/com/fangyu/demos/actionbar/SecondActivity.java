package com.fangyu.demos.actionbar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fangyu.demos.R;

public class SecondActivity extends ActionBarActivity implements TabListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		getSupportActionBar().setTitle("飞得那么远");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment("original")).commit();
		}

		for (int i = 0; i < 4; i++) {
			ActionBar.Tab tab = getSupportActionBar().newTab();
			tab.setText("Tab--" + i);
			tab.setTabListener(this);
			getSupportActionBar().addTab(tab);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_second, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == android.R.id.home)
		{
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
		ft1.replace(R.id.container, new MyFragment(tab.getText().toString()));
//		ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//		ft1.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_right);
		ft1.addToBackStack(null);
		ft1.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
		getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.container)).addToBackStack(null).commit();
System.out.println("11");
	}
}
