package com.danny_jiang.algorithm;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.LinkedList;

import cdc.sed.yff.AdManager;
import cdc.sed.yff.nm.cm.ErrorCode;
import cdc.sed.yff.nm.sp.SplashViewSettings;
import cdc.sed.yff.nm.sp.SpotListener;
import cdc.sed.yff.nm.sp.SpotManager;
import cdc.sed.yff.nm.sp.SpotRequestListener;

/**
 * 开屏广告演示窗口
 *
 * @author Alian Lee
 * @since 2016-11-25
 */
public class SplashActivity extends BaseActivity {

	private static final String TAG = SplashActivity.class.getSimpleName();

	private PermissionHelper mPermissionHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		mContext = this;
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 移除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		// 当系统为6.0以上时，需要申请权限
		mPermissionHelper = new PermissionHelper(this);
		mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
			@Override
			public void onAfterApplyAllPermission() {
				Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
				runApp();
			}
		});
		if (Build.VERSION.SDK_INT < 23) {
			// 如果系统版本低于23，直接跑应用的逻辑
			Log.d(TAG, "The api level of system is lower than 23, so run app logic directly.");
			runApp();
		} else {
			// 如果权限全部申请了，那就直接跑应用逻辑
			if (mPermissionHelper.isAllRequestedPermissionGranted()) {
				Log.d(TAG, "All of requested permissions has been granted, so run app logic directly.");
				runApp();
			} else {
				// 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
				Log.i(TAG, "Some of requested permissions hasn't been granted, so apply permissions first.");
				mPermissionHelper.applyPermissions();
			}
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mPermissionHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 跑应用的逻辑
	 */
	private void runApp() {
		//初始化SDK
		AdManager.getInstance(mContext).init("70f1a3abd7d63f3f", "3b3e1e460ceea249", true);
		preloadAd();
		setupSplashAd(); // 如果需要首次展示开屏，请注释掉本句代码
	}
	
	/**
	 * 预加载广告
	 */
	private void preloadAd() {
		// 注意：不必每次展示插播广告前都请求，只需在应用启动时请求一次
		SpotManager.getInstance(this).requestSpot(new SpotRequestListener() {
			@Override
			public void onRequestSuccess() {
				logInfo("请求插屏广告成功");
				//				// 应用安装后首次展示开屏会因为本地没有数据而跳过
				//              // 如果开发者需要在首次也能展示开屏，可以在请求广告成功之前展示应用的logo，请求成功后再加载开屏
				//				setupSplashAd();
			}
			
			@Override
			public void onRequestFailed(int errorCode) {
				logError("请求插屏广告失败，errorCode: %s", errorCode);
				switch (errorCode) {
				case ErrorCode.NON_NETWORK:
					showShortToast("网络异常");
					break;
				case ErrorCode.NON_AD:
					showShortToast("暂无插屏广告");
					break;
				default:
					showShortToast("请稍后再试");
					break;
				}
			}
		});
	}
	
	/**
	 * 设置开屏广告
	 */
	private void setupSplashAd() {
		// 创建开屏容器
		final RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.rl_splash);
		RelativeLayout.LayoutParams params =
				new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.ABOVE, R.id.view_divider);
		
		// 对开屏进行设置
		SplashViewSettings splashViewSettings = new SplashViewSettings();
		//		// 设置是否展示失败自动跳转，默认自动跳转
		//		splashViewSettings.setAutoJumpToTargetWhenShowFailed(false);
		// 设置跳转的窗口类
		splashViewSettings.setTargetClass(MainActivity.class);
		// 设置开屏的容器
		splashViewSettings.setSplashViewContainer(splashLayout);
		
		// 展示开屏广告
		SpotManager.getInstance(mContext)
		                    .showSplash(mContext, splashViewSettings, new SpotListener() {
			
			                    @Override
			                    public void onShowSuccess() {
				                    logInfo("开屏展示成功");
			                    }
			
			                    @Override
			                    public void onShowFailed(int errorCode) {
				                    logError("开屏展示失败");
				                    switch (errorCode) {
				                    case ErrorCode.NON_NETWORK:
					                    logError("网络异常");
					                    break;
				                    case ErrorCode.NON_AD:
					                    logError("暂无开屏广告");
					                    break;
				                    case ErrorCode.RESOURCE_NOT_READY:
					                    logError("开屏资源还没准备好");
					                    break;
				                    case ErrorCode.SHOW_INTERVAL_LIMITED:
					                    logError("开屏展示间隔限制");
					                    break;
				                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
					                    logError("开屏控件处在不可见状态");
					                    break;
				                    default:
					                    logError("errorCode: %d", errorCode);
					                    break;
				                    }
			                    }
			
			                    @Override
			                    public void onSpotClosed() {
				                    logDebug("开屏被关闭");
			                    }
			
			                    @Override
			                    public void onSpotClicked(boolean isWebPage) {
				                    logDebug("开屏被点击");
				                    logInfo("是否是网页广告？%s", isWebPage ? "是" : "不是");
			                    }
		                    });
	}

	@Override
	protected void onDestroy() {
		LinkedList
		super.onDestroy();
		// 开屏展示界面的 onDestroy() 回调方法中调用
		SpotManager.getInstance(mContext).onDestroy();
	}
}
