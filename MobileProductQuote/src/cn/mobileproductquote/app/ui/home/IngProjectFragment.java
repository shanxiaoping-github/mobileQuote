package cn.mobileproductquote.app.ui.home;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import cn.mobileproductquote.app.R;
import cn.mobileproductquote.app.adapter.ProjectAdapter;
import cn.mobileproductquote.app.data.Project;
import cn.mobileproductquote.app.intrface.AdapterItemListener;
import cn.mobileproductquote.app.ui.base.BaseActivity;
import cn.mobileproductquote.app.ui.base.BaseFragment;
import cn.mobileproductquote.app.ui.project.ProjectDeatailActivity;
import cn.mobileproductquote.app.util.MathUtil;
/**
 * 进行的报价项目
 * @author Administrator
 *
 */
public class IngProjectFragment extends BaseFragment implements
		AdapterItemListener {
	private ListView listView;//列表视图
	private ProjectAdapter projectAdapter;
	private ArrayList<Project> list;

	@Override
	public View layout(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View contenView = inflater.inflate(R.layout.ing_project, null);
		// 初始化视图
		listView = (ListView) contenView
				.findViewById(R.id.ing_project_listview);
		// 初始数据
		projectAdapter = new ProjectAdapter();
	
		list = new ArrayList<Project>();
		projectAdapter.setList(list);
		projectAdapter.setContext(getContext());
		projectAdapter.setListener(this);
		// 设置适配器
		listView.setAdapter(projectAdapter);
		getProjects();
		return contenView;
	}

	/**
	 * 获得项目
	 */
	private String[] name =new String[]{
			"钢材招标采购",
			"生产原料招标采购",
			"工程招标采购",
	};
	private String code[]=new String[]{
			"0000",
			"0001",
			"0002"
	};
	private String time[]=new String[]{
			"2014-6-18",
			"2014-6-20",
			"2014-6-28"
	};
	private void getProjects() {
//		for (int i = 0; i < 3; i++) {
//			Project project = new Project();
//			
//			project.setCurrentNumber(1);
//			project.setName(name[i]);
//			project.setEndTime(time[i]);
//			project.setSerialNumber(code[i]);
//			project.setMoneytype("人民币");
//			project.setRate(17);
//			project.setCurrentQuote(true);
//			project.setCurrentNumber(MathUtil.getRamdom(1, 2,true));
//			list.add(project);
//		}
//		projectAdapter.notifyDataSetChanged();

	}

	@Override
	public boolean onAdapterItemListener(Object... objects) {
		// TODO Auto-generated method stub
		Project project = (Project) objects[0];
		Bundle bundle = new Bundle();
		bundle.putInt("state",0);
		bundle.putSerializable("project", project);
		((BaseActivity) getContext()).openActivity(
				ProjectDeatailActivity.class, bundle);
		return false;
	}

}
