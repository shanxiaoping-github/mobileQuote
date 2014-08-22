package cn.mobileproductquote1.app.ui.home;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.mobileproductquote1.app.R;
import cn.mobileproductquote1.app.adapter.ProjectAdapter;
import cn.mobileproductquote1.app.data.Project;
import cn.mobileproductquote1.app.http.BaseAsynHttpClient;
import cn.mobileproductquote1.app.http.HttpEndtimeProject;
import cn.mobileproductquote1.app.http.HttpMethod;
import cn.mobileproductquote1.app.http.BaseAsynHttpClient.AsynHcResponseListener;
import cn.mobileproductquote1.app.intrface.AdapterItemListener;
import cn.mobileproductquote1.app.ui.base.BaseActivity;
import cn.mobileproductquote1.app.ui.base.BaseFragment;
import cn.mobileproductquote1.app.ui.project.ProjectDeatailActivity;
import cn.mobileproductquote1.app.util.HttpConstants;
import cn.mobileproductquote1.app.util.ShowUtil;

public class EndProectFragment extends BaseFragment implements AdapterItemListener,OnClickListener{
	private ListView listView;// 列表视图
	private ProjectAdapter projectAdapter;
	private ArrayList<Project> cpList=new ArrayList<Project>();//询比价数据
	private ArrayList<Project> biList=new ArrayList<Project>();//招投标数据
	private Button cp;//招投标
	private Button bi;//询比价
	private TextView empty;
	private ImageButton error;

	@Override
	public View layout(LayoutInflater inflater) {
		View contenView = inflater.inflate(R.layout.end_project, null);
		cp=(Button)contenView.findViewById(R.id.end_project_cp);
		bi =(Button)contenView.findViewById(R.id.end_project_bi);
		empty =(TextView)contenView.findViewById(R.id.end_project_empty);
		error=(ImageButton)contenView.findViewById(R.id.end_project_error);
		cp.setOnClickListener(this);
		bi.setOnClickListener(this);
		error.setOnClickListener(this);
		// 初始化视图
		listView = (ListView) contenView
				.findViewById(R.id.end_project_listview);
		// 初始数据
		projectAdapter = new ProjectAdapter();
		
		projectAdapter.setContext(getContext());
		projectAdapter.setListener(this);
		// 设置适配器
		listView.setAdapter(projectAdapter);
		selectIndex(0);
		return contenView;// TODO Auto-generated method stub

	}
	
	/**
	 * 获得项目
	 */
	private void getProjects(int index) {
		empty.setVisibility(View.GONE);
		error.setVisibility(View.GONE);
		projectAdapter.getList().clear();
		projectAdapter.notifyDataSetChanged();
		AsynHcResponseListener listener = new AsynHcResponseListener() {
			
			@Override
			public boolean onTimeout() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				//ShowUtil.showShortToast(getContext(), R.string.loading_prompt1);
				error.setVisibility(View.VISIBLE);
				return false;
			}
			
			@Override
			public boolean onSuccess(BaseAsynHttpClient asynHttpClient) {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				HttpEndtimeProject ob = (HttpEndtimeProject)asynHttpClient;
				switch (currentIndex) {
				case 0:
					cpList.addAll(ob.getList());
					projectAdapter.setList(cpList);
					break;

				case 1:
					biList.addAll(ob.getList());
					projectAdapter.setList(biList);
					break;
				}
				
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
		ShowUtil.openHttpDialog(getResources().getString(R.string.loading_prompt0));
		HttpMethod.getInstance().getEndtimeProject(index==0?HttpConstants.COMPARISON:HttpConstants.BIDDING, listener);
	}

	@Override
	public boolean onAdapterItemListener(Object... objects) {
		// TODO Auto-generated method stub
		Project project = (Project) objects[0];
		Bundle bundle = new Bundle();
		
		bundle.putInt("state",currentIndex==0?1:3);
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
		case R.id.end_project_cp://询比价截止
			selectIndex(0);
			break;

		case R.id.end_project_bi://招投标截止
			selectIndex(1);
			break;
		case R.id.end_project_error:
			getProjects(currentIndex);
			break;
		}
	}
	private int currentIndex;
	private void selectIndex(int index){
		currentIndex=index;
		cp.setBackgroundColor(getContext().getResources().getColor(R.color.hintColor));
		bi.setBackgroundColor(getContext().getResources().getColor(R.color.hintColor));
		switch (index) {
		case 0://询比价
			cp.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
			if(cpList.size()>0){
				projectAdapter.setList(cpList);
				projectAdapter.notifyDataSetChanged();
				return;
			}
			break;

		case 1://招投标
			bi.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
			if(biList.size()>0){
				projectAdapter.setList(biList);
				projectAdapter.notifyDataSetChanged();
				return;
			}
			break;
		}
	    
		getProjects(index);
	}

}
