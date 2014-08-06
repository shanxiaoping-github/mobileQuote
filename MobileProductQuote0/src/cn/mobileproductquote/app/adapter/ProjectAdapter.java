package cn.mobileproductquote.app.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.mobileproductquote.app.R;
import cn.mobileproductquote.app.data.Project;
import cn.mobileproductquote.app.util.ShowUtil;
import cn.mobileproductquote.app.util.Time;
/**
 * 项目适配器
 * @author Administrator
 *
 */
public class ProjectAdapter extends BaseAdapter<Project>{
	

	@Override
	public View view(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder=null;
		final Project project = getData(arg0);
		if(arg1==null){
			arg1 = ShowUtil.LoadXmlView(getContext(),R.layout.project_item);
			holder = new Holder();
			holder.proName = (TextView)arg1.findViewById(R.id.project_item_name);
			holder.endTime = (TextView)arg1.findViewById(R.id.project_item_end_time);
			holder.serialNumber = (TextView)arg1.findViewById(R.id.project_item_serialNumber);
			holder.moneyType = (TextView)arg1.findViewById(R.id.project_item_moneytype);
			arg1.setTag(holder);
		}else{
			holder=(Holder)arg1.getTag();
		}
		holder.proName.setText(project.getName());
		boolean abort = Time.getInstance().dataIsAbort(project.getEndTime(), Time.DATE_PATTERN_6);
		holder.endTime.setText("截止时间:"+project.getEndTime()+(abort?" 已截止":""));
		holder.serialNumber.setText("项目编号:"+project.getSerialNumber());
		holder.moneyType.setText("币种:"+project.getMoneyType());
		arg1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onItem(new Object[]{project});
			}
		});
		return arg1;
	}
	private class Holder{
		TextView proName;//项目名称
		TextView endTime;//截止时间
		TextView serialNumber;//项目编号
		TextView moneyType;//币种
		
	}

}
