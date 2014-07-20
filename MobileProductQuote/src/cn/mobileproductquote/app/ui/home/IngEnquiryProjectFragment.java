package cn.mobileproductquote.app.ui.home;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.mobileproductquote.app.R;
import cn.mobileproductquote.app.adapter.ProjectAdapter;
import cn.mobileproductquote.app.data.Project;
import cn.mobileproductquote.app.http.BaseAsynHttpClient.AsynHcResponseListener;
import cn.mobileproductquote.app.http.BaseAsynHttpClient;
import cn.mobileproductquote.app.http.HttpComparisonProject;
import cn.mobileproductquote.app.http.HttpMethod;
import cn.mobileproductquote.app.intrface.AdapterItemListener;
import cn.mobileproductquote.app.main.MyApplication;
import cn.mobileproductquote.app.ui.base.BaseActivity;
import cn.mobileproductquote.app.ui.base.BaseFragment;
import cn.mobileproductquote.app.ui.project.ProjectDeatailActivity;
import cn.mobileproductquote.app.util.HttpConstants;
import cn.mobileproductquote.app.util.MathUtil;
import cn.mobileproductquote.app.util.ShowUtil;

/**
 * 进行的询价项目
 * 
 * @author Administrator
 * 
 */
public class IngEnquiryProjectFragment extends BaseFragment implements
		AdapterItemListener,OnClickListener {
	private ListView listView;// 列表视图
	private ProjectAdapter projectAdapter;
	private ArrayList<Project> list;
	private TextView empty;
	private ImageButton error;

	@Override
	public View layout(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View contenView = inflater.inflate(R.layout.ing_project, null);
		// 初始化视图
		listView = (ListView) contenView
				.findViewById(R.id.ing_project_listview);
		empty = (TextView)contenView.findViewById(R.id.ing_project_empty);
		empty.setText("暂无询比价项目");
		error = (ImageButton)contenView.findViewById(R.id.ing_project_error);
		error.setOnClickListener(this);
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
	private void getProjects() {

		AsynHcResponseListener listener = new AsynHcResponseListener() {
			
			@Override
			public boolean onTimeout() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				error.setVisibility(View.VISIBLE);
				empty.setVisibility(View.GONE);
				ShowUtil.showShortToast(getContext(),getResources().getString(R.string.loading_prompt1));
				return false;
			}
			
			@Override
			public boolean onSuccess(BaseAsynHttpClient asynHttpClient) {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				error.setVisibility(View.GONE);
				empty.setVisibility(View.GONE);
				
				HttpComparisonProject ob = (HttpComparisonProject)asynHttpClient; 
				list.addAll(ob.getList());
				projectAdapter.notifyDataSetChanged();
				return false;
			}
			
			@Override
			public boolean onEmpty() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				empty.setVisibility(View.VISIBLE);
				error.setVisibility(View.GONE);
				return false;
			}
		};
		ShowUtil.openHttpDialog(getResources().getString(R.string.loading_prompt0));
		HttpMethod.getInstance().getCurrentComparisonProject(MyApplication.getInstance().getUser().getId(), HttpConstants.COMPARISON, listener);

	}

	@Override
	public boolean onAdapterItemListener(Object... objects) {
		// TODO Auto-generated method stub
		Project project = (Project) objects[0];
		Bundle bundle = new Bundle();
		bundle.putInt("state", 2);
		bundle.putSerializable("project", project);
		ProjectDeatailActivity.ctProject=project;
		((BaseActivity) getContext()).openActivity(
				ProjectDeatailActivity.class, bundle);
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ing_project_error:
			getProjects();
			break;

		}
	}

}
