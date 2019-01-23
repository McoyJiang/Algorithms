package com.danny_jiang.algorithm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * 基础窗口
 *
 * @author Alian Lee
 * @since 2016-11-25
 */
public class BaseActivity extends Activity {
	
	protected static final String TAG = "youmi-demo";
	
	protected Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
	}
	
	/**
	 * 打印调试级别日志
	 *
	 * @param format
	 * @param args
	 */
	protected void logDebug(String format, Object... args) {
		logMessage(Log.DEBUG, format, args);
	}
	
	/**
	 * 打印信息级别日志
	 *
	 * @param format
	 * @param args
	 */
	protected void logInfo(String format, Object... args) {
		logMessage(Log.INFO, format, args);
	}
	
	/**
	 * 打印错误级别日志
	 *
	 * @param format
	 * @param args
	 */
	protected void logError(String format, Object... args) {
		logMessage(Log.ERROR, format, args);
	}
	
	/**
	 * 展示短时Toast
	 *
	 * @param format
	 * @param args
	 */
	protected void showShortToast(String format, Object... args) {
		showToast(Toast.LENGTH_SHORT, format, args);
	}
	
	/**
	 * 展示长时Toast
	 *
	 * @param format
	 * @param args
	 */
	protected void showLongToast(String format, Object... args) {
		showToast(Toast.LENGTH_LONG, format, args);
	}
	
	/**
	 * 打印日志
	 *
	 * @param level
	 * @param format
	 * @param args
	 */
	private void logMessage(int level, String format, Object... args) {
		String formattedString = String.format(format, args);
		switch (level) {
		case Log.DEBUG:
			Log.d(TAG, formattedString);
			break;
		case Log.INFO:
			Log.i(TAG, formattedString);
			break;
		case Log.ERROR:
			Log.e(TAG, formattedString);
			break;
		}
	}
	
	/**
	 * 展示Toast
	 *
	 * @param duration
	 * @param format
	 * @param args
	 */
	private void showToast(int duration, String format, Object... args) {
		Toast.makeText(mContext, String.format(format, args), duration).show();
	}
}
