package cn.mobileproductquote.app.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 产品
 * 
 * @author Administrator
 * 
 */
public class Product implements BaseData {

	private String serialNumber = "";// 产品编码
	private String name = "";// 产品名称
	private float number = 0;// 数量
	private String unit = "";// 斤
	private float lastPrice = 0;// 上轮价格
	private float currentPrice = 0;// 当前价格
	private float rate = 0;// 税率
	private String describe = "无";

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getNumber() {
		return number;
	}

	public void setNumber(float number) {
		this.number = number;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	@Override
	public void parser(JSONObject jo) {
		// TODO Auto-generated method stub

	}

	/**
	 * 是否改变了价格
	 * 
	 * @return
	 */
	public boolean isChange() {
		return currentPrice - lastPrice != 0;
	}

	@Override
	public JSONObject page() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Product> getArray(String content) {
		ArrayList<Product> list = new ArrayList<Product>();
		try {
			JSONArray ja = new JSONArray(content);
			for (int i = 0; i < ja.length(); i++) {

				JSONObject jo = ja.getJSONObject(i);
				Product product = new Product();
				product.parser(jo);
				list.add(product);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获得报价产品
	 * @return
	 */
    public QuoteProduct getQuoteProduct(){
    	QuoteProduct quoteProduct=new QuoteProduct();
    	quoteProduct.setSerialNumber(serialNumber);
    	quoteProduct.setPrice(currentPrice);
    	quoteProduct.setRate(rate);
    	return quoteProduct;
    }
	public class QuoteProduct implements BaseData {
		private String serialNumber = "";// 产品编码
		private float price;// 当前单价
		private float rate;// 产品税率

		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}

		public float getPrice() {
			return price;
		}

		public void setPrice(float price) {
			this.price = price;
		}

		public float getRate() {
			return rate;
		}

		public void setRate(float rate) {
			this.rate = rate;
		}

		@Override
		public void parser(JSONObject jo) {
			// TODO Auto-generated method stub

		}

		@Override
		public JSONObject page() {
			// TODO Auto-generated method stub
			JSONObject jo = new JSONObject();
			try {
				jo.put("serialNumber",serialNumber);
				jo.put("price", price);
				jo.put("rate", rate);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return jo;
		}

	}
}
