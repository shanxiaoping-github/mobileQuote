package cn.mobileproductquote4.app.intrface;

import java.io.Serializable;
/**
 * 基础监听接口
 * 
 * @author Administrator
 * 
 */
public interface BaseListener extends Serializable {
	/**
	 * 处理函数
	 * 
	 * @param objects
	 * @return
	 */
	public boolean onListener(Object... objects);
}
