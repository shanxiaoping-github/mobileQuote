package cn.mobileproductquote.app.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.mobileproductquote.app.R;
import cn.mobileproductquote.app.data.Product;
import cn.mobileproductquote.app.data.Project;
import cn.mobileproductquote.app.util.MathUtil;
import cn.mobileproductquote.app.util.ShowUtil;

/**
 * 产品适配器
 * 
 * @author Administrator
 * 
 */
public class ProductAdapter extends BaseAdapter<Product> {
	public ProductAdapter(Project project,int state){
		setProject(project);
		setState(state);
	}
	private Project project;//所在的项目
	
	private int state = 0;// 0报价，1截止查看，2询价

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public View view(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final Product product = getData(arg0);
		Holder holder = null;
		if (arg1 == null) {
			arg1 = ShowUtil.LoadXmlView(getContext(), R.layout.product_item);
			holder = new Holder();
			holder.productRate = (TextView) arg1
					.findViewById(R.id.product_item_rate);
			holder.productName = (TextView) arg1
					.findViewById(R.id.product_item_name);
			holder.productUnit = (TextView) arg1
					.findViewById(R.id.product_item_unit);

			holder.productSerialNumber = (TextView) arg1
					.findViewById(R.id.product_item_serialNumber);

			holder.productLastPrice = (TextView) arg1
					.findViewById(R.id.product_item_last_price);
			holder.productCurrentPrice = (TextView) arg1
					.findViewById(R.id.product_item_current_price);
			holder.productCurrentTotal = (TextView) arg1
					.findViewById(R.id.product_item_product_total);
			holder.productLastTotal = (TextView) arg1
					.findViewById(R.id.product_item_last_total);
			arg1.setTag(holder);
		} else {
			holder = (Holder) arg1.getTag();
		}

		holder.productRate.setText("含税" + product.getRate() + "%");
		holder.productName.setText(arg0 + "/" + dataSize() + " "
				+ product.getName());// 产品名称
		holder.productUnit
				.setText(MathUtil.getAmoutExpress(product.getNumber())
						+ product.getUnit());// 产品单位
		holder.productSerialNumber.setText("产品编号:" + product.getSerialNumber());// 产品编码

		if (state == 1||project.getCurrentNumber()==1) {//如果是截止或第一次报价

			// holder.productCurrentTotal.setVisibility(View.GONE);
			holder.productLastPrice.setVisibility(View.GONE);
			// holder.productCurrentPrice.setVisibility(View.GONE);
			holder.productLastTotal.setVisibility(View.GONE);
		} else {
			holder.productLastTotal.setVisibility(View.VISIBLE);
			// holder.productCurrentTotal.setVisibility(View.VISIBLE);
			holder.productLastPrice.setVisibility(View.VISIBLE);
			// holder.productCurrentPrice.setVisibility(View.VISIBLE);

			holder.productLastTotal
					.setText((state == 0 ? "上轮投标小计:" : "上轮询标小计:")
							+ MathUtil.getAmoutExpress(product.getLastPrice()
									* product.getNumber()) + "元");// 上轮报价
			holder.productLastPrice
					.setText((state == 0 ? "上轮投标单价:" : "上轮询标单价:")
							+ MathUtil.getAmoutExpress(product
									.getLastPrice()) + "元");// 上轮报价

		}
		holder.productCurrentPrice.setText((state == 0 ? "当前投标单价:" : "当前询标单价:")
				+ MathUtil.getAmoutExpress(product.getCurrentPrice()) + "元");// 当前报价
		double total = product.getCurrentPrice() * product.getNumber();
		holder.productCurrentTotal.setText((state == 0 ? "当前投标小计:" : "当前询标小计:")
				+ MathUtil.getAmoutExpress(total) + "元");

		holder.productCurrentPrice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				onItem(new Object[] { 1, product });
			}
		});
		arg1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onItem(new Object[] { 0, product });
			}
		});
		return arg1;
	}

	private class Holder {
		TextView productCurrentTotal;// 产品小计
		TextView productLastTotal;// 上轮产品小计
		TextView productName;// 产品名称
		TextView productUnit;// 产品单位
		TextView productRate;// 产品税率
		TextView productSerialNumber;// 产品编码

		TextView productLastPrice;// 上轮报价
		TextView productCurrentPrice;// 当前报价
	}

}
