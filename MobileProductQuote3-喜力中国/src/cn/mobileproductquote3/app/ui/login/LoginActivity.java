package cn.mobileproductquote3.app.ui.login;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.mobileproductquote3.app.R;
import cn.mobileproductquote3.app.http.BaseAsynHttpClient;
import cn.mobileproductquote3.app.http.HttpLogin;
import cn.mobileproductquote3.app.http.HttpMethod;
import cn.mobileproductquote3.app.http.BaseAsynHttpClient.AsynHcResponseListener;
import cn.mobileproductquote3.app.main.MyApplication;
import cn.mobileproductquote3.app.ui.base.BaseActivity;
import cn.mobileproductquote3.app.ui.home.MainActivity;
import cn.mobileproductquote3.app.util.HttpConstants;
import cn.mobileproductquote3.app.util.ShowUtil;
import cn.mobileproductquote3.app.util.StringUtil;

/**
 * 登录界面
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText userName;// 用户名
	private EditText userPwd;// 用户密码
	private Button into;// 登入

	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		// adb shell setprop debug.assert 1

		setContentView(R.layout.login);

		userName = (EditText) findViewById(R.id.login_input_item_username);
		userPwd = (EditText) findViewById(R.id.login_input_item_userpwd);
		into = (Button) findViewById(R.id.login_input_item_into);
		into.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_input_item_into:// 登录
			login();
			break;

		}
	}

	/**
	 * 登录
	 */
	private void login() {
		String nameStr = userName.getText().toString();
		String pwdStr = userPwd.getText().toString();
		if (StringUtil.stringIsEmpty(nameStr)) {
			ShowUtil.showShortToast(this, "请输入用户名");
		} else if (StringUtil.stringIsEmpty(pwdStr)) {
			ShowUtil.showShortToast(this, "请输入登录密码");
		} else {// 登入

			ShowUtil.openHttpDialog("登录中...");
			AsynHcResponseListener listener = new AsynHcResponseListener() {

				@Override
				public boolean onTimeout() {
					// TODO Auto-generated method stub

					ShowUtil.closeHttpDialog();
					ShowUtil.showShortToast(MyApplication.getAppContext(),
							"登录超时");
					return false;
				}

				@Override
				public boolean onSuccess(BaseAsynHttpClient asynHttpClient) {
					// TODO Auto-generated method stub
					ShowUtil.closeHttpDialog();
					HttpLogin ob = (HttpLogin) asynHttpClient;
					switch (ob.getStatus()) {
					case HttpConstants.SUCCESS:// 登录成功
						ShowUtil.showShortToast(MyApplication.getAppContext(),
								"登录成功");
						openActivity(MainActivity.class);
						finishBase();
						break;

					case HttpConstants.FAIL:// 登录失败
						ShowUtil.showShortToast(MyApplication.getAppContext(),
								"登录失败");
						break;
					case HttpConstants.HAVE:
						ShowUtil.showShortToast(MyApplication.getAppContext(),
								"用户已登录");
						break;
					case HttpConstants.ERROR:
						ShowUtil.showShortToast(MyApplication.getAppContext(),
								"用户名或密码错误");
						break;
					}

					return false;
				}

				@Override
				public boolean onEmpty() {
					// TODO Auto-generated method stub
					ShowUtil.closeHttpDialog();
					ShowUtil.showShortToast(MyApplication.getAppContext(),
							"服务器无响应");
					return false;
				}

			};

			HttpMethod.getInstance().login(
					new String[] { "userName", "passWord" },
					new String[] { nameStr, pwdStr }, listener);

		}
	}

}
