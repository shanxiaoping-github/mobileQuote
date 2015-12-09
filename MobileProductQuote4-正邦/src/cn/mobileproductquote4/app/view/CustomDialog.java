package cn.mobileproductquote4.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import cn.mobileproductquote3.app.R;

/**
 * 自定义dialog
 * 
 * @author Administrator
 * 
 */
public class CustomDialog extends Dialog {
	private int type = 0;// 0按键取消，1按键不取消
	private View cView;// 布局的View

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public View getcView() {
		return cView;
	}

	public void setcView(View cView) {
		this.cView = cView;
	}

	public CustomDialog(Context context, View contentView) {
		super(context, R.style.Dialog);
		setcView(contentView);
		// TODO Auto-generated constructor stub
	}

	public CustomDialog(Context context, View contentView, int theme) {
		super(context, theme);
		setcView(contentView);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		setContentView(getcView());

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		

		if (keyCode == event.KEYCODE_BACK) {
			switch (type){
				case 0:
					return super.onKeyDown(keyCode, event);
	
				case 1:
					return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}
