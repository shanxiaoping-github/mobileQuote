package cn.mobileproductquote.app.http;

import cn.mobileproductquote.app.configuration.Configuration;



/**
 * http请求地址类
 * 
 * @author Administrator
 * 
 */
public class HttpAddress {
	
	public static final String LOGIN="LaoginAction.action?action=login";//登录地址
	
	public static final String BIDDING_PROJECT="";//招投标项目
	public static final String COMPARISON_PROJECT="getCurrentComparisonProject.action?action=getCurrentComparisonProject";//询比价项目
	
	
	
	public static final String ENDTIME_PROJECT="";//结束项目
	public static final String SEACH_PROECT="";//搜索项目
	public static final String USERINFOMATION="";//用户详情
	
	public static final String PROJECT_QUOTE="";//项目报价
	public static final String PROJECT_COMPARISON="comparisonQuote.action?action=comparisonQuote";//项目询价操作
	public static final String BIDDING_PROJECT_DEATAIL="";//招投标项目详情
	public static final String COMPARISON_PROJECT_DEATAIL="getComparisonProduct.action?action=getComparisonProduct";//询比价项目详情
	public static final String END_PROJECT_DEATAIL="getComparisonProduct.action?action=getComparisonProduct";//结束项目详情
	
	
	/****************************************** 【获得请求地址】 ****************************************/
	public static String getUrl(String path) {
		return getUrl(Configuration.getInstance().getProperty(Configuration.SERVICE_ADRESS),path);
	}

	public static String getUrl(String url, String path) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append(path);
		return buffer.toString();
	}

}
