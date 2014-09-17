package cn.mobileproductquote3.app.main;

import cn.mobileproductquote3.app.ui.base.BaseActivity;
import cn.mobileproductquote3.app.ui.login.LoginActivity;
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
		finishBase();

	}
	
	
	

}
