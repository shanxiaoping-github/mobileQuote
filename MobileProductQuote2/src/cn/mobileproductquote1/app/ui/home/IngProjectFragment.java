package cn.mobileproductquote1.app.ui.home;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.mobileproductquote1.app.R;
import cn.mobileproductquote1.app.adapter.ProjectAdapter;
import cn.mobileproductquote1.app.data.Project;
import cn.mobileproductquote1.app.http.BaseAsynHttpClient;
import cn.mobileproductquote1.app.http.HttpBiddingProject;
import cn.mobileproductquote1.app.http.HttpMethod;
import cn.mobileproductquote1.app.http.BaseAsynHttpClient.AsynHcResponseListener;
import cn.mobileproductquote1.app.intrface.AdapterItemListener;
import cn.mobileproductquote1.app.main.MyApplication;
import cn.mobileproductquote1.app.ui.base.BaseActivity;
import cn.mobileproductquote1.app.ui.base.BaseFragment;
import cn.mobileproductquote1.app.ui.project.ProjectDeatailActivity;
import cn.mobileproductquote1.app.util.HttpConstants;
import cn.mobileproductquote1.app.util.MathUtil;
import cn.mobileproductquote1.app.util.ShowUtil;

/**
 * 进行的报价项目
 * 
 * @author Administrator
 * 
 */
public class IngProjectFragment extends BaseFragment implements
		AdapterItemListener,OnClickListener{
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
		empty=(TextView)contenView.findViewById(R.id.ing_project_empty);
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

	

	private void getProjects() {
		empty.setVisibility(View.GONE);
		error.setVisibility(View.GONE);
		
		AsynHcResponseListener listener = new AsynHcResponseListener() {

			@Override
			public boolean onTimeout() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				error.setVisibility(View.VISIBLE);
				return false;
			}

			@Override
			public boolean onSuccess(BaseAsynHttpClient asynHttpClient) {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				HttpBiddingProject ob  = (HttpBiddingProject)asynHttpClient;
				list.addAll(ob.getList());
				projectAdapter.notifyDataSetChanged();
		
				return false;
			}

			@Override
			public boolean onEmpty() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				empty.setVisibility(View.VISIBLE);
				return false;
			}
		};
		ShowUtil.openHttpDialog(getResources().getString(
				R.string.loading_prompt0));
		HttpMethod.getInstance().getCurrentBiddingProject(
				MyApplication.getInstance().getUser().getId(),
				HttpConstants.BIDDING, listener);

	}

	@Override
	public boolean onAdapterItemListener(Object... objects) {
		// TODO Auto-generated method stub
		Project project = (Project) objects[0];
		Bundle bundle = new Bundle();
		bundle.putInt("state", 0);
		bundle.putSerializable("project", project);
		ProjectDeatailActivity.ctProject = project;
		((BaseActivity) getContext()).openActivity(
				ProjectDeatailActivity.class, bundle);
		return false;
	}



	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ing_project_error://加载错误
			getProjects();
			break;

		
		}
	}


}
