package cn.mobileproductquote.app.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.mobileproductquote.app.R;
import cn.mobileproductquote.app.data.Product;
import cn.mobileproductquote.app.util.MathUtil;
import cn.mobileproductquote.app.util.ShowUtil;

/**
 * 改变的产品适配器
 * 
 * @author Administrator
 * 
 */
public class ChangeProductAdapter extends BaseAdapter<Product> {

	@Override
	public View view(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Holder holder = null;
		final Product product = getData(arg0);
		if (arg1 == null) {
			arg1 = ShowUtil.LoadXmlView(getContext(),
					R.layout.change_product_item);
			holder = new Holder();
			holder.name = (TextView) arg1.findViewById(R.id.change_product_item_name);
			holder.number = (TextView) arg1.findViewById(R.id.change_product_item_number);
			holder.currentTotal = (TextView) arg1.findViewById(R.id.change_product_item_current_total);
			holder.currentPrice = (TextView) arg1.findViewById(R.id.change_product_item_current_price);
			holder.lastTotal = (TextView) arg1.findViewById(R.id.change_product_item_last_total);
			holder.lastPrice = (TextView) arg1.findViewById(R.id.change_product_item_last_price);
			arg1.setTag(holder);
		} else {
			holder = (Holder) arg1.getTag();
		}

		holder.name.setText("产品名称:" + product.getName());
		holder.number.setText(product.getNumber() + product.getUnit());
		double ctotal = product.getCurrentPrice() * product.getNumber();
		String ctotalStr = ctotal == 0 ? "暂无小计" : MathUtil
				.getAmoutExpress(ctotal) + "元";
		holder.currentTotal.setText("当前小计:" + ctotalStr);
		holder.currentPrice.setText(
				 product.getCurrentPrice() == 0 ? "暂无报价" : "("+MathUtil
						.getAmoutExpress(product.getCurrentPrice()) + "元/"+product.getUnit()+")");
		double ltotal = product.getLastPrice() * product.getNumber();
		String ltotalStr = ltotal == 0 ? "暂无小计" : MathUtil
				.getAmoutExpress(ltotal) + "元";

		holder.lastTotal.setText("上轮小计:" + ltotalStr);

		holder.lastPrice.setText(
				product.getLastPrice() == 0 ? "暂无报价" : "("+MathUtil
						.getAmoutExpress(product.getLastPrice()) + "元/"+product.getUnit()+")");

		
		arg1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onItem(new Object[]{0,product});
			}
		});
		return arg1;
	}

	private class Holder {
		TextView name;
		TextView number;
		TextView currentTotal;
		TextView currentPrice;
		TextView lastTotal;
		TextView lastPrice;

	}
}
