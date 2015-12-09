package cn.mobileproductquote4.app.http;

import org.json.JSONException;
import org.json.JSONObject;

import cn.mobileproductquote4.app.util.HttpConstants;

/**
 * 项目报价
 * @author Administrator
 *
 */
public class HttpProjectQuote extends BaseAsynHttpClient{
    private int status=HttpConstants.FAIL;
	@Override
	protected void parerAsynHcResponse(String content) {
		// TODO Auto-generated method stub
		JSONObject jo;
		try {
			jo = new JSONObject(content);
			status=jo.getInt("status");
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
	

}
