package cn.mobileproductquote.app.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 基本用户信息
 * 
 * @author Administrator
 * 
 */
public class BaseUser implements BaseData {
	private int id;// 用户id
	private String userName;// 用户姓名
	private String realname;// 用户真实姓名

	@Override
	public void parser(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			id = jo.getInt("id");
			userName = jo.getString("userName");
			realname = jo.getString("realName");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}



	@Override
	public JSONObject page() {
		// TODO Auto-generated method stub
		return null;
	}

}
