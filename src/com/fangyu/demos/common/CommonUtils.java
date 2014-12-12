package com.fangyu.demos.common;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;


public class CommonUtils {
	/**
	 * 设置沉浸式状态栏 状态栏背景
	 */
	public static void setImmerseTheme(Activity activity, int resId) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window win = activity.getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			winParams.flags |= bits;
			win.setAttributes(winParams);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(resId);
	}
}
