package cn.mobileproductquote.app.ui.base;


import android.content.Intent;

import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

/**
 * 碎片管理类
 * 
 * @author Administrator
 * 
 */
public class BaseFragmentActivity extends BaseActivity implements
		BaseFragment.OnHeadlineSelectedListener{
	private BaseFragment currentFragment;// 当前fragment

	public BaseFragment getCurrentFragment(){
		return currentFragment;
	}

	public void setCurrentFragment(BaseFragment currentFragment) {
		this.currentFragment = currentFragment;
	}

	

	/**
	 * 设置当前BaseFragment
	 * 
	 * @param fragment
	 */
	public void setCurrentFragment(BaseFragment fragment,int id){
		if(getCurrentFragment()!=null){
			getCurrentFragment().onUnSelect();
		}
		setCurrentFragment(fragment);
		
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(id,fragment);
		
		transaction.commit();
		fragment.onSelect();
	}

	/**
	 * 删除指定Fragment
	 */
	public void delectFragment(BaseFragment fragment){
		getSupportFragmentManager()
		.beginTransaction().remove(fragment)
				.commit();
	}

	/**
	 * 选中fragment处理
	 * 
	 * @param param
	 */
	@Override
	public void onArticleSelected(Object[] param) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		BaseFragment fragment = getCurrentFragment();
		if (fragment != null) {
			return fragment.onKeyDown(keyCode,event);
		} else {
			return super.onKeyDown(keyCode,event);
		}
	}

	@Override
	public void layout(){
		// TODO Auto-generated method stub
	}

	@Override
	public void back() {
		// TODO Auto-generated method stub
		finishBase();
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if(currentFragment!=null){
			currentFragment.onActivityResult(arg0, arg1, arg2);
		}
	}

}
