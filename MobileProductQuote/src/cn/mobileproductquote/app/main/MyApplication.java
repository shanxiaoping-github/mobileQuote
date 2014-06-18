package cn.mobileproductquote.app.main;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;
import cn.mobileproductquote.app.configuration.Configuration;
import cn.mobileproductquote.app.data.BaseUser;
import cn.mobileproductquote.app.manager.ActivityManager;
import cn.mobileproductquote.app.sqlite.SharedPreferencesSava;
import cn.mobileproductquote.app.util.SpSaveContants;

/**
 * 程序类
 * 
 * @author Administrator
 * 
 */
public class MyApplication extends Application {

	private static MyApplication instance;// 实例
	private static Context context;// 上下文
	private static WindowManager wm = null;// 窗口管理类
	private BaseUser user=null;//基本用户
	
    
	public BaseUser getUser() {
		if(user==null){
			user=(BaseUser)SharedPreferencesSava.getInstance().getObject(SpSaveContants.OBJ,"baseUser");
		}
		return user;
	}

	public void setUser(BaseUser user) {
		this.user = user;
		SharedPreferencesSava.getInstance().savaObject(SpSaveContants.OBJ, "baseUser",user);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		super.onCreate();
		instance = this;//获得实例
		context = getApplicationContext();// 获得上下文
	    Configuration.getInstance();//加载配置文件

	}

	/**
	 * 返回程序单列
	 * 
	 * @return
	 */
	public static MyApplication getInstance() {
		return instance;
	}

	/**
	 * 获得程序上下文
	 * 
	 * @return
	 */
	public static Context getAppContext() {
		return MyApplication.context;
	}

	/**
	 * 获得窗口管理
	 * 
	 * @param context
	 * @return
	 */
	public static WindowManager getWindowManager(Context context) {
		if (wm == null) {
			wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
		}
		return wm;
	}

	/**
	 * 退出程序
	 */
	public static void outApp(boolean isSafe) {
		ActivityManager.getInstance().clear();

		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
