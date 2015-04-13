package com.fangyu.demos.common;

import com.fangyu.demos.R;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	CommonUtils.setImmerseTheme(this, R.color.actionbar_bg);
    }
}
