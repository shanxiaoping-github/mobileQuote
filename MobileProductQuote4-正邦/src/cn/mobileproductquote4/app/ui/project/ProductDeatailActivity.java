package cn.mobileproductquote4.app.ui.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.mobileproductquote3.app.R;
import cn.mobileproductquote4.app.data.Product;
import cn.mobileproductquote4.app.intrface.BaseListener;
import cn.mobileproductquote4.app.ui.base.BaseActivity;
import cn.mobileproductquote4.app.util.MathUtil;

/**
 * 产品详情
 * 
 * @author Administrator
 * 
 */
public class ProductDeatailActivity extends BaseActivity implements
		OnClickListener {

	private TextView currentPrice;
	private TextView lastPrice;
	private TextView rate;
	private TextView serialNumber;

	private TextView number;
	private TextView describe;

	private TextView title;

	private TextView currenTotal;
	private TextView lastTotal;
	private TextView faileView;
	private static BaseListener listener = null;
	private static Product product = null;
	private static boolean modify = true;

	


	public static void setModify(boolean modify) {
		ProductDeatailActivity.modify = modify;
	}

	public static void setListener(BaseListener listener) {
		ProductDeatailActivity.listener = listener;
	}

	public static void setProduct(Product product) {
		ProductDeatailActivity.product = product;
	}

	@Override
	protected void layout() {
		// TODO Auto-generated method stub

		setContentView(R.layout.product_deatail);
		findViewById(R.id.back_item_back).setOnClickListener(this);

		findViewById(R.id.product_deatail_rate_root).setOnClickListener(this);

		currenTotal = (TextView) findViewById(R.id.product_deatail_current_total);
		lastTotal = (TextView) findViewById(R.id.product_deatail_last_total);

		title = (TextView) findViewById(R.id.product_deatail_title);
		currentPrice = (TextView) findViewById(R.id.product_deatail_current_price);
		lastPrice = (TextView) findViewById(R.id.product_deatail_last_price);
		rate = (TextView) findViewById(R.id.product_deatail_rate);
		serialNumber = (TextView) findViewById(R.id.product_deatail_serialnumber);

		number = (TextView) findViewById(R.id.product_deatail_number);
		describe = (TextView) findViewById(R.id.product_deatail_describe);
		faileView = (TextView)findViewById(R.id.product_deatail_faile);
		View prompt = findViewById(R.id.product_deatail_prompt);
		if (!modify) {
			prompt.setVisibility(View.GONE);
		}

		if (product != null) {
			faileView.setVisibility(View.GONE);
			title.setText(product.getName());
			currenTotal.setText("当前小计:"
					+ MathUtil.getAmoutExpress(product.getCurrentPrice()
							* product.getNumber()) + "元");
			lastTotal.setText("上轮小计:"
					+ MathUtil.getAmoutExpress(product.getLastPrice()
							* product.getNumber()) + "元");

			currentPrice.setText("(" + product.getCurrentPrice() + "元/"
					+ product.getUnit() + ")");
			lastPrice.setText("(" + product.getLastPrice() + "元/"
					+ product.getUnit() + ")");
			rate.setText("税率:" + product.getRate() + "%");
			serialNumber.setText("编号:" + product.getSerialNumber());

			number.setText("数量:" + product.getNumber());
			describe.setText(product.getDescribe());
		}else{
			title.setText("未知");
			faileView.setVisibility(View.VISIBLE);
			
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back_item_back:
			finishBase();
			break;
		case R.id.product_deatail_rate_root:
			if (modify) {
				modifyRate();
			}
			break;

		}
	}

	/**
	 * 修改税率
	 */
	private void modifyRate() {
		final EditText editText = new EditText(this);
		editText.setSingleLine(true);
		
		editText.setText(String.valueOf(product.getRate()));
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("修改税率")
				.setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						String rate = editText.getText().toString();
						if (!MathUtil.isDoubleNumber(rate, 0, 100, false, false)) {// 正确的数值
							showShortToast("请输入正确的数值");

						} else if (Double.valueOf(rate) - product.getRate() == 0) {
							showShortToast("请输入不同的税率");
						} else {
							product.setRate(Double.parseDouble(rate));
							ProductDeatailActivity.this.rate.setText("税率:"
									+ product.getRate() + "%");
							if (listener != null) {
								listener.onListener();
								listener = null;
							}
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

}
