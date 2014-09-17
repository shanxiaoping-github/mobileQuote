package cn.mobileproductquote1.app.http;

import org.json.JSONException;
import org.json.JSONObject;

import cn.mobileproductquote1.app.util.HttpConstants;

public class HttpComparisonQuote extends BaseAsynHttpClient{
	private int status=HttpConstants.FAIL;

	@Override
	protected void parerAsynHcResponse(String content) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo = new JSONObject(content);
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
