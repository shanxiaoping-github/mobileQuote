package cn.mobileproductquote.app.http;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.mobileproductquote.app.data.Product.QuoteProduct;
import cn.mobileproductquote.app.http.BaseAsynHttpClient.AsynHcResponseListener;
import cn.mobileproductquote.app.util.HttpConstants;

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
	public void getEndtimeProject(int userId, int type,
			AsynHcResponseListener listener) {
		HttpEndtimeProject ob = new HttpEndtimeProject();
		ob.setPramas(new String[] { "userId", "type" }, new Object[] { userId,
				type });
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.ENDTIME_PROJECT);
	}

	/**
	 * 
	 * @param userId
	 * @param projectKey
	 * @param type
	 * @param listener
	 */
	public void seachProject(int userId, String projectKey, int type,
			AsynHcResponseListener listener) {
		HttpSeachProject ob = new HttpSeachProject();
		ob.setPramas(new String[] { "userId", "projectKey", "type" },
				new Object[] { userId, projectKey, type });
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.SEACH_PROECT);
	}

	/**
	 * 获得用户详情
	 * 
	 * @param userId
	 *            用户id
	 * @param listener
	 *            监听器
	 */
	public void getUserInformation(int userId, AsynHcResponseListener listener) {
		HttpUserInformation ob = new HttpUserInformation();
		ob.setPramas(new String[] { "userId" }, new Object[] { userId });
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.USERINFOMATION);
	}

	/**
	 * 获得项目产品详情
	 * 
	 * @param serialNumber
	 *            项目编号
	 * @param currenTurn
	 *            第几轮
	 * @param type
	 *            项目类型
	 * @param listener
	 *            监听器
	 */
	public void getProjectProducts(String serialNumber, int currenTurn,
			int type, AsynHcResponseListener listener) {
		HttpProducts ob = new HttpProducts();
		ob.setPramas(new String[] { "serialNumber", "currenTurn", "type" },
				new Object[] { serialNumber, currenTurn, type });
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.PROJECT_DEATAIL);
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
	private void projectQuote(int operateCode, String productQuoteInfo,
			String serialNumber, int type, AsynHcResponseListener listener) {
		HttpProjectQuote ob = new HttpProjectQuote();
		ob.setPramas(new String[] { "operateCode", "productQuoteInfo",
				"serialNumber", "type" }, new Object[] { operateCode,
				productQuoteInfo, serialNumber, type

		});
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.PROJECT_QUOTE);
	}
	/**
	 * 拒绝报价
	 * @param serialNumber 项目编号
	 * @param type 项目类型
	 * @param listener 监听器
	 */
	public void projectQuoteRefuse(String serialNumber,int type,AsynHcResponseListener listener){
		projectQuote(HttpConstants.REFUSE, "", serialNumber, type, listener);
	}
	public void projuectQuote(String serialNumber,int type,ArrayList<QuoteProduct> list,AsynHcResponseListener listener){
		if(list==null||list.size()==0){
			return;
		}
		JSONArray ja = new JSONArray();
		for(int i=0;i<list.size();i++){
			QuoteProduct quoteProduct = list.get(i);
			JSONObject jo = quoteProduct.page();
			ja.put(jo);
			
		}
		String productQuoteInfo = ja.toString();
		projectQuote(HttpConstants.AGREE,productQuoteInfo,serialNumber,type,listener);
	}
	/**
	 * 项目询价操作
	 * @param operateCode 操作码
	 * @param serialNumber 项目编号
	 * @param type 项目类型
	 * @param productQuoteInfo 产品询价详情
	 */
	private void projectComparisonOperate(int operateCode,String serialNumber,int type
			,String productQuoteInfo,AsynHcResponseListener listener){
		HttpComparisonOprate ob = new HttpComparisonOprate();
		ob.setPramas(new String[]{
				"operateCode",
				"serialNumber",
				"type",
				"productQuoteInfo"
		}, new Object[]{
				operateCode,
				serialNumber,
				type,
				productQuoteInfo
		});
		ob.addAsynHcResponseListenrt(listener);
		ob.getUrl(HttpAddress.PROJECT_COMPARISON);
	}
	/**
	 * 询价操作 同意或拒绝
	 * @param serialNumber 项目编号
	 * @param type 项目类型
	 * @param listener 监听器
	 */
	public void projectComparisonOperate(int operateCode,String serialNumber,int type,AsynHcResponseListener listener){
		projectComparisonOperate(operateCode, serialNumber, type, "", listener);
	}
	/**
	 * 询价操作 价格修改反向询价
	 * @param serialNumber 项目编号
	 * @param type 项目类型
	 * @param list 产品询价详情
	 * @param listener 监听器
	 */
	public void projectComparisonOperate(String serialNumber,int type,ArrayList<QuoteProduct> list,AsynHcResponseListener listener){
		if(list==null||list.size()==0){
			return;
		}
		JSONArray ja = new JSONArray();
		for(int i=0;i<list.size();i++){
			QuoteProduct quoteProduct = list.get(i);
			JSONObject jo = quoteProduct.page();
			ja.put(jo);
			
		}
		String productQuoteInfo = ja.toString();
		projectComparisonOperate(HttpConstants.CHANGE,serialNumber,type,productQuoteInfo,listener);
	}

}
