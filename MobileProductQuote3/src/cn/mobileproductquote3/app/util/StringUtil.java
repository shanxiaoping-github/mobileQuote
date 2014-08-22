package cn.mobileproductquote3.app.util;

/**
 * 字符串工具类
 * 
 * @author Administrator
 * 
 */
public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * @param content
	 * @return
	 */
	public static boolean stringIsEmpty(String content) {
		if (null == content || "".equals(content) || " ".equals(content)) {
			return true;
		}
		return false;
	}
}
