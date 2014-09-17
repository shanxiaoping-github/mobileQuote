package cn.mobileproductquote1.app.data;

import org.json.JSONObject;
/**
 * 记录
 * @author Administrator
 *
 */
public class Record implements BaseData{
	private String time="";//时间
	private boolean isRefused=false;//询价是否被拒绝

	@Override
	public void parser(JSONObject jo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject page() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
