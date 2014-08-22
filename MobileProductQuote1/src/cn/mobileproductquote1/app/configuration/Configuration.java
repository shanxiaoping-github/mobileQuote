package cn.mobileproductquote1.app.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import cn.mobileproductquote1.app.main.MyApplication;

/**
 * 项目基础配置类
 * 
 * @author Administrator
 * 
 */
public class Configuration extends Properties {
    
	public static final String SERVICE_ADRESS="serviceAddress";//服务器地址
	
	private Configuration() {}// 保证私有构造

	private static Configuration instance = null;// 单例

	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
			instance.loadProperties(MyApplication.getAppContext(), "mpqproperties");
		}
		return instance;
	}

	/**
	 * 加载配置地址
	 * 
	 * @param context
	 * @param path
	 */
	public void loadProperties(Context context, String path) {
		try {
			InputStream in = context.getAssets().open(path);
			load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
