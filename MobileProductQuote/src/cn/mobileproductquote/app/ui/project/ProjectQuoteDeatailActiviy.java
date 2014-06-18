package cn.mobileproductquote.app.ui.project;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.mobileproductquote.app.R;
import cn.mobileproductquote.app.adapter.ChangeProductAdapter;
import cn.mobileproductquote.app.data.Product;
import cn.mobileproductquote.app.intrface.AdapterItemListener;
import cn.mobileproductquote.app.intrface.BaseListener;
import cn.mobileproductquote.app.ui.base.BaseActivity;

/**
 * 产品报价调动预览界面
 * 
 * @author Administrator
 * 
 */
public class ProjectQuoteDeatailActiviy extends BaseActivity implements
		OnClickListener, AdapterItemListener,Serializable {
	private ArrayList<Product> list;
	private ListView listView;
	private ChangeProductAdapter adapter;
	private TextView title;// 标题
	private TextView describe;// 描述
	private int currentQuote;//当前轮次
	private int manxProductNumber;//产品总数量
	private int state;
	private static BaseListener listener=null;
	

	
	public static void setListener(BaseListener listener) {
		ProjectQuoteDeatailActiviy.listener = listener;
	}

	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.project_quote_deatail);
		list = (ArrayList<Product>) getIntent().getExtras().getSerializable(
				"list");
		
		state=getIntent().getExtras().getInt("state");
		currentQuote = getIntent().getExtras().getInt("currentQuote");
		manxProductNumber = getIntent().getExtras().getInt("manxProductNumber");
		listView = (ListView) findViewById(R.id.project_quote_deatail_listview);
		title = (TextView) findViewById(R.id.project_quote_deatail_title);
		describe = (TextView) findViewById(R.id.project_quote_deatail_describe);
		
		title.setText(state==0?"你正在提交第"+currentQuote+"轮报价":"你正在修改第"+currentQuote+"轮询价");
		int size = list.size();
		describe.setText("本采购项目共包含"+manxProductNumber+"个采购产品，本轮你调整了"+size+"个产品的"+(state==0?"报价":"询价")+"调整产品如下:");
		
		findViewById(R.id.project_quote_deatail_back).setOnClickListener(this);
		findViewById(R.id.project_quote_deatail_sure).setOnClickListener(this);
		// 设置数据
		adapter = new ChangeProductAdapter();
		adapter.setContext(this);
		adapter.setList(list);
		adapter.setListener(this);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.project_quote_deatail_back:// 返回
			finishBase();
			break;

		case R.id.project_quote_deatail_sure:// 确认报价
			if(listener!=null){
				listener.onListener(null);
				listener=null;
			}
			finishBase();
			break;
		}
	}

	@Override
	public boolean onAdapterItemListener(Object... objects) {
		// TODO Auto-generated method stub
		Product product = (Product) objects[1];
		Bundle bundle = new Bundle();
		bundle.putSerializable("product", product);
		openActivity(ProductDeatailActivity.class, bundle);
		return false;
	}

}
