package cn.mobileproductquote1.app.ui.base;

import android.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 碎片基类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseFragment extends Fragment {

	private OnHeadlineSelectedListener listener;// 监听器
	private Context context;// 上下文
	

	public OnHeadlineSelectedListener getListener() {
		return listener;
	}

	public void setListener(OnHeadlineSelectedListener listener) {
		this.listener = listener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		return layout(inflater);// 布局;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		context = activity;
		listener = (OnHeadlineSelectedListener) activity;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (savedInstanceState == null) {// 数据初始化
			dataInit();
		} else {// 数据恢复
			dataRestore(savedInstanceState);
		}
		eventDispose();// 事件
	}

	/* 布局 */
	public abstract View layout(LayoutInflater inflater);

	/* 选中 */
	public void onSelect() {
	};

	/* 未选中 */
	public void onUnSelect() {
	};

	/* 数据初始化 */
	public void dataInit() {
	}

	/* 数据恢复 */
	public void dataRestore(Bundle savedInstanceState) {
	}

	/* 处理 */
	public void eventDispose() {
	}

	/* activity返回 */
	public void onActivityResult(int arg0, int arg1, Intent arg2) {}

	/* 获得上下文环境 */
	public Context getContext() {
		return context;
	}

	/**
	 * 管理类事件触发
	 * 
	 * @author Administrator
	 * 
	 */
	public interface OnHeadlineSelectedListener {
		public void onArticleSelected(Object[] param);

		public void back();
	}

	/**
	 * 按键处理
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (listener != null && keyCode == event.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			listener.back();// 返回
		}
		return true;
	}

}
