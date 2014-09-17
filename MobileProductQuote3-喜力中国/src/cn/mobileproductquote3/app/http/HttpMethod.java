package cn.mobileproductquote3.app.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.mobileproductquote3.app.data.Product.QuoteProduct;
import cn.mobileproductquote3.app.http.BaseAsynHttpClient.AsynHcResponseListener;
import cn.mobileproductquote3.app.main.MyApplication;
import cn.mobileproductquote3.app.util.HttpConstants;

/**
 * http方法集合
 * 
 * @author Administrator
 * 
 */
public class HttpMethod {
	private static HttpMethod instance = null;

	/**
	 * 获得实例
	 * 
	 * @return
	 */
	public static HttpMethod getInstance() {
		if (instance == null) {
			instance = new HttpMethod();
		}
		return instance;
	}

	/**
	 * 登录
	 * 
	 * @param key
	 *            参数键值
	 * @param params
	 *            参数值
	 * @param listener
	 *            监听器
	 */
	public void login(String[] key, Object[] params,
			AsynHcResponseListener listener) {
		HttpLogin login = new HttpLogin();
		login.setPramas(key, params);
		login.addAsynHcResponseListenrt(listener);
		login.getUrl(HttpAddress.LOGIN);
	}

	/**
	 * 获得当前招投标项目
	 * 
	 * @param userId
	 *            用户id
	 * @param type
	 *            项目类型
	 * @param listener
	 *            监听器
	 */
	public void getCurrentBiddingProject(int userId, int type,
			AsynHcResponseListener listener) {
		HttpBiddingProject ob = new HttpBiddingProject();
		ob.setPramas(new String[] { "userId", "type" }, new Object[] { userId,
				type });
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.BIDDING_PROJECT);
	}

	/**
	 * 获得当前询比价项目
	 * 
	 * @param userId
	 *            用户id
	 * @param type
	 *            项目类型
	 * @param listener
	 *            监听器
	 */
	public void getCurrentComparisonProject(int userId, int type,
			AsynHcResponseListener listener) {
		HttpComparisonProject ob = new HttpComparisonProject();
		ob.setPramas(new String[] { "userId", "type" }, new Object[] { userId,
				type });
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.COMPARISON_PROJECT);
	}

	/**
	 * 获得结束的招投标或询比价项目
	 * 
	 * @param userId
	 *            用户id
	 * @param type
	 *            项目类型
	 * @param listener
	 *            监听器
	 */
	public void getEndtimeProject(int type,
			AsynHcResponseListener listener) {
		HttpEndtimeProject ob = new HttpEndtimeProject();
		ob.setPramas(new String[] { "userId", "type" }, new Object[] { MyApplication.getInstance().getUser().getId(),
				type });
		ob.addAsynHcResponseListenrt(listener);
		switch (type) {
		case HttpConstants.COMPARISON://询比价
			ob.getUrl(HttpAddress.ENDTIME_COMPARISON_PROJECT);
			break;

		case HttpConstants.BIDDING://招投标
			ob.getUrl(HttpAddress.ENDTIME_BIDDING_PROJECT);
			break;
		}
		
	}

	/**
	 * 
	 * @param userId
	 * @param projectKey
	 * @param type
	 * @param listener
	 */
	public void seachProject(int userId, String projectKey,
			AsynHcResponseListener listener) {
		HttpSeachProject ob = new HttpSeachProject();
		ob.setPramas(new String[] { "userId", "projectKey"},
				new Object[] { userId, projectKey});
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.SEACH_PROECT);
	}



	/**
	 * 获得项目产品详情
	 * 
	 * @param userId
	 * @param serialNumber
	 *            项目编号
	 * @param currenTurn
	 *            第几轮
	 * @param type
	 *            项目类型
	 * @param listener
	 *            监听器
	 */
	public void getProjectProducts(int userId, String serialNumber,
			int currenTurn, int type, AsynHcResponseListener listener) {
		HttpProducts ob = new HttpProducts();
		ob.setPramas(new String[] { "userId", "serialNumber", "currenTurn",
				"type" },
				new Object[] { userId, serialNumber, currenTurn, type==HttpConstants.COMPARISON||type==HttpConstants.ENDCP?HttpConstants.COMPARISON:HttpConstants.BIDDING });
		ob.addAsynHcResponseListenrt(listener);
		switch (type) {
		case HttpConstants.COMPARISON:// 询比价
			ob.getUrl(HttpAddress.COMPARISON_PROJECT_DEATAIL);
			break;

		case HttpConstants.BIDDING:// 招投标
			ob.getUrl(HttpAddress.BIDDING_PROJECT_DEATAIL);
			break;
		case HttpConstants.ENDCP:// 询比价截止
			ob.getUrl(HttpAddress.END_COMPARISON_PROJECT_DEATAIL);
			break;
		case HttpConstants.ENDIN://招投标截止
			ob.getUrl(HttpAddress.END_BIDDING_PROJECT_DEATAIL);
			break;

		}

	}

	/**
	 * 项目报价
	 * 
	 * @param operateCode
	 *            操作码
	 * @param productQuoteInfo
	 *            修改json数组
	 * @param serialNumber
	 *            项目编码
	 * @param type
	 *            项目类型
	 */
	public void projectQuote(String serialNumber, int currenTurn,
			ArrayList<QuoteProduct> list, AsynHcResponseListener listener) {
		if (list == null || list.size() == 0) {
			return;
		}
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			QuoteProduct quoteProduct = list.get(i);
			JSONObject jo = quoteProduct.page();
			ja.put(jo);

		}
		String productQuoteInfo = ja.toString();
		projectQuote(MyApplication.getInstance().getUser().getId(), 1,
				productQuoteInfo, serialNumber, HttpConstants.BIDDING,
				currenTurn, listener);
	}

	public void projectQuote(int userId, int operateCode,
			String productQuoteInfo, String serialNumber, int type,
			int currenTurn, AsynHcResponseListener listener) {
		HttpProjectQuote ob = new HttpProjectQuote();
		ob.setPramas(new String[] { "operateCode", "userId", "type",
				"serialNumber", "currenTurn", "productQuoteInfo" },
				new Object[] { operateCode, userId, type, serialNumber,
						currenTurn, productQuoteInfo,

				});
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.PROJECT_BIDDING_QUOTE);
	}
 
	public void projectModify(String serialNumber, int currenTurn,
			ArrayList<QuoteProduct> list, AsynHcResponseListener listener) {
		if (list == null || list.size() == 0) {
			return;
		}
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			QuoteProduct quoteProduct = list.get(i);
			JSONObject jo = quoteProduct.page();
			ja.put(jo);

		}
		String productQuoteInfo = ja.toString();
		projectModify(MyApplication.getInstance().getUser().getId(),2,
				productQuoteInfo, serialNumber, HttpConstants.BIDDING,
				currenTurn, listener);
	}
	public void projectModify(int userId, int operateCode,
			String productQuoteInfo, String serialNumber, int type,
			int currenTurn, AsynHcResponseListener listener){
		HttpProjectQuote ob = new HttpProjectQuote();
		ob.setPramas(new String[] { "operateCode", "userId", "type",
				"serialNumber", "currenTurn", "productQuoteInfo" },
				new Object[] { operateCode, userId, type, serialNumber,
						currenTurn, productQuoteInfo,

				});
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.PROJECT_BIDDING_MODIFICATION);
	}

	/**
	 * 询比价项目报价
	 * 
	 * @param currenTurn
	 *            当前轮次
	 * @param serialNumber
	 *            项目编号
	 * @param list
	 *            修改列表
	 * @param listener
	 *            监听器
	 */
	public void projectComparisonQuote(int currenTurn, String serialNumber,
			ArrayList<QuoteProduct> list, AsynHcResponseListener listener) {
		if (list == null || list.size() == 0) {
			return;
		}
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			QuoteProduct quoteProduct = list.get(i);
			JSONObject jo = quoteProduct.page();
			ja.put(jo);

		}
		String productQuoteInfo = ja.toString();
		projectComparisonQuote(MyApplication.getInstance().getUser().getId(),
				serialNumber, currenTurn, HttpConstants.AGREE,
				productQuoteInfo, listener);
	}

	/**
	 * 询比价项目报价
	 * 
	 * @param userId
	 * @param serialNumber
	 * @param currenTurn
	 * @param operateCode
	 * @param productQuoteInfo
	 */
	public void projectComparisonQuote(int userId, String serialNumber,
			int currenTurn, int operateCode, String productQuoteInfo,
			AsynHcResponseListener listener) {
		HttpComparisonQuote ob = new HttpComparisonQuote();

		ob.setPramas(new String[] { "userId", "operateCode", "serialNumber",
				"currenTurn", "productQuoteInfo"

		}, new Object[] { userId, operateCode, serialNumber, currenTurn,
				productQuoteInfo });
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.PROJECT_COMPARISON_QUOTE);
	}

	/**
	 * 项目询价操作
	 * 
	 * @param operateCode
	 *            操作码
	 * @param serialNumber
	 *            项目编号
	 * @param type
	 *            项目类型
	 * @param productQuoteInfo
	 *            产品询价详情
	 */
	private void projectComparisonOperate(int userId, int currenTurn,
			int operateCode, String serialNumber, String productQuoteInfo,
			AsynHcResponseListener listener) {
		HttpComparisonOprate ob = new HttpComparisonOprate();
		ob.setPramas(new String[] { "userId", "operateCode", "serialNumber",
				"currenTurn", "productQuoteInfo"

		}, new Object[] { userId, operateCode, serialNumber, currenTurn,
				productQuoteInfo });

		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.PROJECT_COMPARISON_OPERATE);
	}

	/**
	 * 询价操作 同意或拒绝
	 * 
	 * @param serialNumber
	 *            项目编号
	 * @param type
	 *            项目类型
	 * @param listener
	 *            监听器
	 */
	public void projectComparisonOperate(int userId, int currenTurn,
			int operateCode, String serialNumber,
			AsynHcResponseListener listener) {
		projectComparisonOperate(userId, currenTurn, operateCode, serialNumber,
				"", listener);
	}

	/**
	 * 询价操作 价格修改反向询价
	 * 
	 * @param serialNumber
	 *            项目编号
	 * @param type
	 *            项目类型
	 * @param list
	 *            产品询价详情
	 * @param listener
	 *            监听器
	 */
	public void projectComparisonOperate(int userId, int currenTurn,
			String serialNumber, ArrayList<QuoteProduct> list,
			AsynHcResponseListener listener) {
		if (list == null || list.size() == 0) {
			return;
		}
		JSONArray ja = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			QuoteProduct quoteProduct = list.get(i);
			JSONObject jo = quoteProduct.page();
			ja.put(jo);

		}
		String productQuoteInfo = ja.toString();
		projectComparisonOperate(userId, currenTurn, HttpConstants.CHANGE,
				serialNumber, productQuoteInfo, listener);
	}

}
