package com.fangyu.demos.actionbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fangyu.demos.R;

public class PlaceholderFragment extends Fragment
{

    private String str = "";
    private static final String tag = "PlaceholderFragment";

    public PlaceholderFragment()
    {}

    public PlaceholderFragment(String str)
    {
	this.str = str;
    }

    @Override
    public void onAttach(Activity activity)
    {
	super.onAttach(activity);
	Log.e("View", tag + "***** onAttach_" + str);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	Log.e("View", tag + "***** onCreate_" + str);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	Log.e("View", tag + "***** onCreateView_" + str);
	View rootView = inflater.inflate(R.layout.fragment_main, container, false);
	TextView tv = (TextView) rootView.findViewById(R.id.tv);
	tv.setText(str);
	return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	super.onActivityCreated(savedInstanceState);
	Log.e("View", tag + "***** onActivityCreated_" + str);
    }

    @Override
    public void onStart()
    {
	// TODO Auto-generated method stub
	super.onStart();
	Log.e("View", tag + "***** onStart_" + str);
    }

    @Override
    public void onResume()
    {
	// TODO Auto-generated method stub
	super.onResume();
	Log.e("View", tag + "***** onResume_" + str);
    }

    @Override
    public void onPause()
    {
	// TODO Auto-generated method stub
	super.onPause();
	Log.e("View", tag + "***** onPause_" + str);
    }

    @Override
    public void onStop()
    {
	// TODO Auto-generated method stub
	super.onStop();
	Log.e("View", tag + "***** onStop_" + str);
    }

    @Override
    public void onDestroyView()
    {
	// TODO Auto-generated method stub
	super.onDestroyView();
	Log.e("View", tag + "***** onDestroyView_" + str);
    }

    @Override
    public void onDestroy()
    {
	// TODO Auto-generated method stub
	super.onDestroy();
	Log.e("View", tag + "***** onDestroy_" + str);
    }

    @Override
    public void onDetach()
    {
	// TODO Auto-generated method stub
	super.onDetach();
	Log.e("View", tag + "***** onDetach_" + str);
    }
}