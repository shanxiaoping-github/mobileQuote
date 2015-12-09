package cn.mobileproductquote4.app.data;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;
/**
 * 基础数据类
 * @author Administrator
 *
 */
public interface BaseData extends Serializable{
	public void parser(JSONObject jo);//解析数据
	public JSONObject page();//打包数据
	
}
