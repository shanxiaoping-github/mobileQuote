package cn.mobileproductquote0.app.http;

import org.json.JSONException;
import org.json.JSONObject;

import cn.mobileproductquote0.app.data.BaseUser;
import cn.mobileproductquote0.app.main.MyApplication;
import cn.mobileproductquote0.app.util.HttpConstants;

/**
 * 登录http
 * 
 * @author Administrator
 * 
 */
public class HttpLogin extends BaseAsynHttpClient {
    private int status=HttpConstants.FAIL;
    private BaseUser user=null;//基本用户信息
	@Override
	protected void parerAsynHcResponse(String content) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo = new JSONObject(content);
			status=jo.getInt("status");
			if(status==HttpConstants.SUCCESS){//成功
				user = new BaseUser();
				String userInfo = jo.getString("userInfo");
				user.parser(new JSONObject(userInfo));
				MyApplication.getInstance().setUser(user);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BaseUser getUser() {
		return user;
	}
	public void setUser(BaseUser user) {
		this.user = user;
	}
	
	

}
