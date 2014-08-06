package cn.mobileproductquote.app.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import cn.mobileproductquote.app.manager.ActivityManager;

/**
 * ui基类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseActivity extends FragmentActivity {
	private static final String TAG = "BaseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		ActivityManager.getInstance().push(this);

		layout();// 对ui进行模板布局,以及一些ui的界面初始化化，不包含数据
		if (savedInstanceState == null) {// 没有保存数据和重建的情况下
			dataInit();
		} else {// 数据恢复
			dataRestore(savedInstanceState);
		}
		eventDispose();
	}

	/**
	 * 数据恢复
	 */
	protected void dataRestore(Bundle savedInstanceState){}

	/**
	 * 数据初始化
	 */
	protected void dataInit(){};

	/**
	 * 对ui进行模板布局,以及一些ui的界面初始化化，不包含数据
	 */
	protected abstract void layout();

	/**
	 * 事件执行
	 */
	protected void eventDispose() {
	};

	/**
	 * 界面刷新
	 * 
	 * @param param
	 */
	protected void refesh(Object... param) {}

	/******************************** 【跳转到其他界面】 *******************************************/
	public void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	public void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	public void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	public void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}
	
	public void openActivityResult(Class<?> pClass,
			int requestCode) {
		openActivityResult(pClass, null, requestCode);
	}
	/******************************** 【跳转到子界面】 *******************************************/
	public void openActivityResult(Class<?> pClass) {
		openActivityResult(pClass, null);
	}

	public void openActivityResult(Class<?> pClass, Bundle pBundle) {
		openActivityResult(pClass, pBundle, 0);
	}

	public void openActivityResult(Class<?> pClass, Bundle pBundle,
			int requestCode) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
	}

	/******************************** 【界面提示】 *******************************************/

	public void showShortToast(int pResId) {
		showShortToast(getString(pResId));
	}

	public void showLongToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
	}

	public void showShortToast(String pMsg) {
		Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();

	}

	/* 自定义清除 */
	public void finishBase() {

		ActivityManager.getInstance().peek(getClass().getName(),true);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			finishBase();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
