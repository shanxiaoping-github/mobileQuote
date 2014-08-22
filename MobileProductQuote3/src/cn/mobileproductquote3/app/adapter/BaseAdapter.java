package cn.mobileproductquote3.app.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import cn.mobileproductquote3.app.data.BaseData;
import cn.mobileproductquote3.app.intrface.AdapterItemListener;

/**
 * 自定义基类适配器
 * @author Administrator
 * 
 */
public abstract class BaseAdapter<T extends BaseData> extends android.widget.BaseAdapter {

	private AdapterItemListener listener;

	public AdapterItemListener getListener() {
		return listener;
	}

	public void setListener(AdapterItemListener listener) {
		this.listener = listener;
	}

	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	private ArrayList<T> list = new ArrayList();

	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		arg1 = view(arg0, arg1, arg2);
		
		
		return arg1;
	}

	/* 视图view */
	public abstract View view(int arg0, View arg1, ViewGroup arg2);

	/**
	 * 点击处理
	 * 
	 * @param objects
	 */
	public void onItem(Object... objects) {
		if (listener != null) {
			listener.onAdapterItemListener(objects);
		}
	}

	/* 获得当前选中T */
	public T getData(int index) {
		return list.get(index);
	}
	/**
	 * 返回数据大小
	 * @return
	 */
	public int dataSize(){
		return list.size();
		
	}

}
