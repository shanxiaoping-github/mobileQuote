package cn.mobileproductquote1.app.util;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * 网络工具类
 * 
 * @author Administrator
 * 
 */
public class NetUtil {
	private static NetUtil instance=null;
	public static NetUtil getInstance(){
		if(instance==null){
			instance=new NetUtil();
		}
		return instance;
	}

	/**
	 * 对网络连接状态进行判断
	 * 
	 * @return true, 可用； false，不可用
	 */
	public boolean isOpenNetwork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;
	}
	/**
	 * 设置网络
	 */
	public void setNet(Context context){
		Intent intent = null;

		try {
			String sdkVersion = android.os.Build.VERSION.SDK;
			if (Integer.valueOf(sdkVersion) > 10) {
				intent = new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS);
			} else {
				intent = new Intent();
				ComponentName comp = new ComponentName(
						"com.android.settings",
						"com.android.settings.WirelessSettings");
				intent.setComponent(comp);
				intent.setAction("android.intent.action.VIEW");
			}
			context.startActivity(intent);
			// startActivity(intent);
		} catch (Exception e) {
			// Log.w(TAG,
			// "open network settings failed, please check...");
			e.printStackTrace();
		}
	}

	/**
	 * 网络可用就调用下一步需要进行的方法， 网络不可用则需设置
	 */
	public boolean initIntener(final Context context) {

		// 判断网络是否可用
		if (isOpenNetwork(context)) {// 网络可用
			return true;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("没有可用的网络").setMessage("是否对网络进行设置?");

		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setNet(context);
			}
		}).setNegativeButton("否", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();

			}
		}).show();
		return false;

	}

}
