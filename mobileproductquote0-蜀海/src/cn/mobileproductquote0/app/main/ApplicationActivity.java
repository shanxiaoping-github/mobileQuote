package cn.mobileproductquote0.app.main;

import cn.mobileproductquote0.app.ui.base.BaseActivity;
import cn.mobileproductquote0.app.ui.login.LoginActivity;
/**
 * 程序启动入口
 * @author Administrator
 *
 */
public class ApplicationActivity extends BaseActivity {
	

	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		openActivity(LoginActivity.class);
		//finishBase();

	}
	
	
	

}
