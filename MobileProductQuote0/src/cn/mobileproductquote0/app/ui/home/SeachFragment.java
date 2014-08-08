package cn.mobileproductquote0.app.ui.home;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import cn.mobileproductquote0.app.R;
import cn.mobileproductquote0.app.adapter.ProjectAdapter;
import cn.mobileproductquote0.app.data.Project;
import cn.mobileproductquote0.app.http.BaseAsynHttpClient;
import cn.mobileproductquote0.app.http.HttpMethod;
import cn.mobileproductquote0.app.http.HttpSeachProject;
import cn.mobileproductquote0.app.http.BaseAsynHttpClient.AsynHcResponseListener;
import cn.mobileproductquote0.app.intrface.AdapterItemListener;
import cn.mobileproductquote0.app.main.MyApplication;
import cn.mobileproductquote0.app.ui.base.BaseActivity;
import cn.mobileproductquote0.app.ui.base.BaseFragment;
import cn.mobileproductquote0.app.ui.project.ProjectDeatailActivity;
import cn.mobileproductquote0.app.util.HttpConstants;
import cn.mobileproductquote0.app.util.ShowUtil;
import cn.mobileproductquote0.app.util.StringUtil;
import cn.mobileproductquote0.app.util.Time;
/**
 * 搜索
 * @author Administrator
 *
 */
public class SeachFragment extends BaseFragment implements OnClickListener,AdapterItemListener{
   private EditText content;//内容
   private Button icon;//图标
   private ListView listView;
   private ProjectAdapter adapter;
   private ArrayList<Project> list;
   
   
	@Override
	public View layout(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View contentView = inflater.inflate(R.layout.seach,null);
		content=(EditText)contentView.findViewById(R.id.seach_content);
		listView = (ListView)contentView.findViewById(R.id.seach_listview);
		adapter=new ProjectAdapter();
		adapter.setContext(getContext());
		list = new ArrayList<Project>();
		adapter.setList(list);
		
		adapter.setListener(this);
		listView.setAdapter(adapter);
		
		icon=(Button)contentView.findViewById(R.id.seach_icon);
		icon.setOnClickListener(this);
		return contentView;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.seach_icon:
			seachProject();
			break;
		}
	}
	/**
	 * 搜索项目
	 */
	private void seachProject(){
		String contentStr = content.getText().toString();
		if(StringUtil.stringIsEmpty(contentStr)){
			ShowUtil.showShortToast(getContext(),"请输入关键字");
		}else{
			AsynHcResponseListener listener = new AsynHcResponseListener() {
				
				@Override
				public boolean onTimeout() {
					// TODO Auto-generated method stub
					ShowUtil.closeHttpDialog();
					ShowUtil.showShortToast(getContext(),"搜索超时...");
					return false;
				}
				
				@Override
				public boolean onSuccess(BaseAsynHttpClient asynHttpClient) {
					// TODO Auto-generated method stub
					ShowUtil.closeHttpDialog();
					HttpSeachProject ob = (HttpSeachProject)asynHttpClient;
					list.addAll(ob.getList());
					adapter.notifyDataSetChanged();
					return false;
				}
				
				@Override
				public boolean onEmpty() {
					// TODO Auto-generated method stub
					ShowUtil.closeHttpDialog();
					ShowUtil.showShortToast(getContext(),"未搜索到相关项目");
					return false;
				}
			};
			ShowUtil.openHttpDialog("搜索中...");
			HttpMethod.getInstance().seachProject(MyApplication.getInstance().getUser().getId(), contentStr, listener);
			
		}
	}
	@Override
	public boolean onAdapterItemListener(Object... objects) {
		// TODO Auto-generated method stub
		Project project = (Project) objects[0];
		Bundle bundle = new Bundle();
		int state=0;
		if(Time.getInstance().dataIsAbort(project.getEndTime(),Time.DATE_PATTERN_6)){
			if(project.getType()==HttpConstants.COMPARISON){
				state = 1;
			}else{
				state=3;
			}
		}else{
			if(project.getType()==HttpConstants.COMPARISON){
				state = 2;
			}else{
				state=0;
			}
		}
		
		bundle.putInt("state",state);
		bundle.putSerializable("project", project);
		ProjectDeatailActivity.ctProject = project;
		((BaseActivity) getContext()).openActivity(
				ProjectDeatailActivity.class, bundle);
		return false;
	}

}
