package cn.mobileproductquote4.app.http;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import cn.mobileproductquote4.app.data.Project;
import cn.mobileproductquote4.app.util.HttpConstants;
/**
 * 询比价项目
 * @author Administrator
 *
 */
public class HttpComparisonProject extends BaseAsynHttpClient{

	private int status = HttpConstants.FAIL;
	private ArrayList<Project> list = new ArrayList<Project>();

	@Override
	protected void parerAsynHcResponse(String content) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo = new JSONObject(content);
			status = jo.getInt("status");
			if (status == HttpConstants.SUCCESS){
				String projectInfo = jo.getString("projectInfo");
				list = Project.getArray(projectInfo);
			}else if(status == HttpConstants.EMPTY){
				setEmpty(true);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ArrayList<Project> getList() {
		return list;
	}

	public void setList(ArrayList<Project> list) {
		this.list = list;
	}

}
