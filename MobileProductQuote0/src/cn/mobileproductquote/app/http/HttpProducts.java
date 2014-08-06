package cn.mobileproductquote.app.http;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import cn.mobileproductquote.app.data.Product;
import cn.mobileproductquote.app.util.HttpConstants;

/**
 * 获得项目产品列表
 * 
 * @author Administrator
 * 
 */
public class HttpProducts extends BaseAsynHttpClient {
	private int status = HttpConstants.FAIL;
	private String productsInfo = "";
	private ArrayList<Product> list = new ArrayList<Product>();

	@Override
	protected void parerAsynHcResponse(String content) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo = new JSONObject(content);
			status = jo.getInt("status");
			if (status == HttpConstants.SUCCESS) {
				productsInfo = jo.getString("productsInfo");
				if (!(productsInfo.equals("0") || productsInfo.equals("1"))){
					list = Product.getArray(productsInfo);
				}

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

	public ArrayList<Product> getList() {
		return list;
	}

	public void setList(ArrayList<Product> list) {
		this.list = list;
	}

}
