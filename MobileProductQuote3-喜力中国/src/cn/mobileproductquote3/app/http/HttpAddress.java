package cn.mobileproductquote3.app.http;

import cn.mobileproductquote3.app.configuration.Configuration;

/**
 * http请求地址类
 * 
 * @author Administrator
 * 
 */
public class HttpAddress {

	public static final String LOGIN = "LaoginAction.action?action=login";// 登录地址

	public static final String BIDDING_PROJECT = "getCurrentBiddingProject.action?action=getCurrentBiddingProject";// 招投标项目
	public static final String COMPARISON_PROJECT = "getCurrentComparisonProject.action?action=getCurrentComparisonProject";// 询比价项目

	public static final String ENDTIME_COMPARISON_PROJECT = "getEndComparisonProject.action?action=getEndComparisonProject";// 结束询比价项目
	public static final String ENDTIME_BIDDING_PROJECT = "getEndTenderProject.action?action=getEndTenderProject";// 结束招投标项目

	public static final String SEACH_PROECT = "seachProject.action?action=seachProject";// 搜索项目

	public static final String PROJECT_BIDDING_MODIFICATION = "editTenderProjectQuote.action?action=editTenderProjectQuote";// 招投标项目修改报价
	public static final String PROJECT_BIDDING_QUOTE = "tenderProjectQuote.action?action=tenderProjectQuote";// 招投标项目报价
	public static final String PROJECT_COMPARISON_QUOTE = "comparisonQuote.action?action=comparisonQuote";// 询比价报价
	public static final String PROJECT_COMPARISON_OPERATE = "projectComparisonOperate.action?action=projectComparisonOperate";// 询比价操作
	public static final String BIDDING_PROJECT_DEATAIL = "getTenderProjectProduct.action?action=getTenderProjectProduct";// 招投标项目详情
	public static final String COMPARISON_PROJECT_DEATAIL = "getComparisonProduct.action?action=getComparisonProduct";// 询比价项目详情

	public static final String END_COMPARISON_PROJECT_DEATAIL = "getEndComparisonProduct.action?action=getEndComparisonProduct";// 结束的询比价项目详情
	public static final String END_BIDDING_PROJECT_DEATAIL = "getEndTenderProjectProduct.action?action=getEndTenderProjectProduct";// 结束的招投标项目详情

	/****************************************** 【获得请求地址】 ****************************************/
	public static String getUrl(String path) {
		return getUrl(
				Configuration.getInstance().getProperty(
						Configuration.SERVICE_ADRESS), path);
	}

	public static String getUrl(String url, String path) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append(path);
		return buffer.toString();
	}

}
