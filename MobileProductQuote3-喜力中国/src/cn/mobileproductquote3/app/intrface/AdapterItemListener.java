package cn.mobileproductquote3.app.intrface;

import java.io.Serializable;

/**适配器处理接口
 * 
 * @author Administrator
 *
 */
public interface AdapterItemListener extends Serializable{
	public boolean onAdapterItemListener(Object...objects);
	
}
