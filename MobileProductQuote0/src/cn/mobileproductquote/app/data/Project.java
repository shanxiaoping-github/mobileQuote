package cn.mobileproductquote.app.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目
 * 
 * @author Administrator
 * 
 */
public class Project implements BaseData {
	private int type;// 类型 4招投标项目 或 2询比价项目
	private boolean isCurrentQuote = false;// 本轮是否已经报价
	private int currentNumber = 1;// 当前报价次数
	private String serialNumber = "";// 项目编号
	private String name = "";// 项目名称
	private String endTime = "";// 截止时间
	private String moneyType = "";// 币种

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	private int rate;// 基础税率

	public boolean isCurrentQuote() {
		return isCurrentQuote;
	}

	public void setCurrentQuote(boolean isCurrentQuote) {
		this.isCurrentQuote = isCurrentQuote;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void parser(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			type = jo.getInt("type");
			isCurrentQuote = jo.getBoolean("isCurrentQuote");
			currentNumber = jo.getInt("currentNumber");
			serialNumber = jo.getString("serialNumber");
			name = jo.getString("name");
			endTime = jo.getString("endTime");
			moneyType = jo.getString("moneyType");
			rate = jo.getInt("rate");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public JSONObject page() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("finally")
	public static ArrayList<Project> getArray(String content) {
		ArrayList<Project> list = new ArrayList<Project>();
		try {
			JSONArray ja = new JSONArray(content);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Project project = new Project();
				project.parser(jo);
				list.add(project);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return list;
		}
	

	}

}
