package cn.mobileproductquote0.app.sqlite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Map;

import org.apache.mina.util.Base64;

import android.content.SharedPreferences;
import cn.mobileproductquote0.app.main.MyApplication;

/**
 * SharedPreference存储类
 * 
 * @author Administrator
 * 
 */
public class SharedPreferencesSava {
	private static SharedPreferencesSava instance = null;

	public static SharedPreferencesSava getInstance() {
		if (instance == null) {
			instance = new SharedPreferencesSava();
		}
		return instance;
	}

	/**
	 * 保存对象
	 * 
	 * @param spName
	 * @param key
	 * @param ob
	 */
	public void savaObject(String spName, String key, Object ob) {
		if (ob == null) {
			return;
		}
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		// 创建字节输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			// 将对象写入字节流
			oos.writeObject(ob);
			// 将字节流编码成base64的字符窜
			String oAuth_Base64 = new String(Base64.encodeBase64(baos
					.toByteArray()));

			preferences.edit().putString(key, oAuth_Base64).commit();

		} catch (IOException e) {
			// TODO Auto-generated
		}

	}

	/**
	 * 获取存储的对象
	 * 
	 * @param spName
	 * @param key
	 * @return
	 */
	public Object getObject(String spName, String key) {
		Object ob = null;
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);

		String productBase64 = preferences.getString(key, "");

		// 读取字节
		byte[] base64 = Base64.decodeBase64(productBase64.getBytes());

		// 封装到字节流
		ByteArrayInputStream bais = new ByteArrayInputStream(base64);
		try {
			// 再次封装
			ObjectInputStream bis = new ObjectInputStream(bais);
			try {
				// 读取对象
				ob = bis.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ob;

	}

	/**
	 * 存储int数据
	 * 
	 * @param spName
	 * @param key
	 * @param value
	 */
	public void savaIntValue(String spName, String key, int value) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		preferences.edit().putInt(key, value).commit();

	}

	/**
	 * 获取int数据
	 * 
	 * @param spName
	 * @param key
	 * @return
	 */
	public int getIntValue(String spName, String key) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		return preferences.getInt(key, 0);
	}

	/**
	 * 获取int数据
	 * 
	 * @param spName
	 * @param key
	 * @return
	 */
	public int getIntValue(String spName, String key, int defaultValue) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		return preferences.getInt(key, defaultValue);
	}

	/**
	 * 存储float数据
	 * 
	 * @param spName
	 * @param key
	 * @param value
	 */
	public void savaFloatValue(String spName, String key, float value) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		preferences.edit().putFloat(key, value).commit();

	}

	/**
	 * 获取float数据
	 * 
	 * @param spName
	 * @param key
	 * @return
	 */
	public float getFloatValue(String spName, String key) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		return preferences.getFloat(key, 0);
	}

	/**
	 * 存储boolean数据
	 * 
	 * @param spName
	 * @param key
	 * @param value
	 */
	public void savaBooleanValue(String spName, String key, boolean value) {

		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		preferences.edit().putBoolean(key, value).commit();

	}

	/**
	 * 获取boolean数据
	 * 
	 * @param spName
	 * @param key
	 * @return
	 */
	public boolean getBooleanValue(String spName, String key) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	/**
	 * 存储long数据
	 * 
	 * @param spName
	 * @param key
	 * @param value
	 */
	public void savaLongValue(String spName, String key, long value) {

		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);

		preferences.edit().putLong(key, value).commit();
	}

	/**
	 * 获取long数据
	 * 
	 * @param spName
	 * @param key
	 * @return
	 */
	public long getLongValue(String spName, String key) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		return preferences.getLong(key, 0);
	}

	/**
	 * 存储String数据
	 * 
	 * @param spName
	 * @param key
	 * @param value
	 */
	public void savaStringValue(String spName, String key, String value) {

		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		preferences.edit().putString(key, value).commit();
	}

	/**
	 * 获取String数据
	 * 
	 * @param spName
	 * @param key
	 * @return
	 */
	public String getStringValue(String spName, String key) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);

		return preferences.getString(key, "");
	}

	/**
	 * 删除某个文件的记录
	 * 
	 * @param spName
	 * @param key
	 */
	public void removeKey(String spName, String key) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		preferences.edit().remove(key).commit();

	}

	/* 删除整个文件 */
	public void remove(String spName) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);
		preferences.edit().clear().commit();
	}

	/* 获得文件所有键值 */
	public Map<String, String> getAllString(String spName) {
		SharedPreferences preferences = MyApplication.getAppContext()
				.getSharedPreferences(spName,
						MyApplication.getAppContext().MODE_PRIVATE);

		return (Map<String, String>) preferences.getAll();
	}

	

}
