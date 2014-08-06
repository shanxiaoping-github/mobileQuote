package cn.mobileproductquote.app.http;

import org.json.JSONException;
import org.json.JSONObject;

import cn.mobileproductquote.app.util.HttpConstants;

/**
 * 获得用户详细信息
 * 
 * @author Administrator
 * 
 */
public class HttpUserInformation extends BaseAsynHttpClient {
	private int status;
	private String userInfo;

	@Override
	protected void parerAsynHcResponse(String content) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo = new JSONObject(content);
			status = jo.getInt("status");
			if (status == HttpConstants.SUCCESS) {
				userInfo = jo.getString("userInfo");
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

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

}
