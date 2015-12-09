package cn.mobileproductquote4.app.ui.project;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import cn.mobileproductquote3.app.R;
import cn.mobileproductquote4.app.ui.base.BaseActivity;

/**
 * 询价反馈
 * 
 * @author Administrator
 * 
 */
public class EnquiryRecordActivity extends BaseActivity implements
		OnClickListener {
    private ListView listView;
	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.enquiry_record);
		findViewById(R.id.back_item_back).setOnClickListener(this);
		listView = (ListView)findViewById(R.id.enquiry_record_listview);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_item_back:
			finishBase();
			break;
		}
	}

}
