package cn.mobileproductquote.app.ui.project;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.mobileproductquote.app.R;
import cn.mobileproductquote.app.adapter.ProductAdapter;
import cn.mobileproductquote.app.data.Product;
import cn.mobileproductquote.app.data.Product.QuoteProduct;
import cn.mobileproductquote.app.data.Project;
import cn.mobileproductquote.app.http.BaseAsynHttpClient;
import cn.mobileproductquote.app.http.BaseAsynHttpClient.AsynHcResponseListener;
import cn.mobileproductquote.app.http.HttpComparisonOprate;
import cn.mobileproductquote.app.http.HttpComparisonQuote;
import cn.mobileproductquote.app.http.HttpMethod;
import cn.mobileproductquote.app.http.HttpProducts;
import cn.mobileproductquote.app.intrface.AdapterItemListener;
import cn.mobileproductquote.app.intrface.BaseListener;
import cn.mobileproductquote.app.main.MyApplication;
import cn.mobileproductquote.app.ui.base.BaseActivity;
import cn.mobileproductquote.app.util.HttpConstants;
import cn.mobileproductquote.app.util.MathUtil;
import cn.mobileproductquote.app.util.ShowUtil;
import cn.mobileproductquote.app.util.Time;

/**
 * 项目详情
 * 
 * @author Administrator
 * 
 */
public class ProjectDeatailActivity extends BaseActivity implements
		AdapterItemListener, OnClickListener {
	public static Project ctProject = null;// 当前项目
	private int state = 0;// 0可报价，1已截止,2询价
	private Project project;// 项目对象
	private ListView listView;// 产品视图
	private ArrayList<Product> list;// 产品数据数组
	private ProductAdapter productAdapter;// 适配器
	private TextView title;// 标题
	private TextView totalLastPrice;// 上一轮报价总和
	private TextView totalCurrentPrice;// 当前报价总和
	// private Button qutoButton;// 报价
	private TextView projectRate;// 项目税率
	private TextView projectRemainQuoteNumber;// 项目剩余报价轮次
	private LinearLayout bottomGroup;// 底部容器
	private TextView type;
	private ImageButton more;
	private ImageButton error;// 错误
	private TextView empty;// 空

	@Override
	protected void layout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.project_deatail);
		state = getIntent().getExtras().getInt("state");
		project = (Project) getIntent().getExtras().getSerializable("project");
		type = (TextView) findViewById(R.id.project_deatail_type);
		more = (ImageButton) findViewById(R.id.project_deatail_more);
		more.setOnClickListener(this);
		error = (ImageButton) findViewById(R.id.project_deatail_error);
		error.setOnClickListener(this);
		empty = (TextView) findViewById(R.id.project_deatail_empty);
		switch (state) {
		case 0:
			type.setText("投");
			if (project.getCurrentNumber() > 1) {
				more.setVisibility(View.VISIBLE);
			} else {
				more.setVisibility(View.GONE);
			}
			break;

		case 1:
			type.setText("止");
			more.setVisibility(View.GONE);
			break;
		case 2:
			type.setText("询");
			if (project.getCurrentNumber() > 1) {
				more.setVisibility(View.VISIBLE);
			} else {
				more.setVisibility(View.GONE);
			}
			break;
		}
		// 初始顶部
		findViewById(R.id.back_item_back).setOnClickListener(this);

		// 初始标题栏和视图
		listView = (ListView) findViewById(R.id.project_deatail_listview);
		title = (TextView) findViewById(R.id.project_deatail_title);
		title.setText(project.getName());

		// 设置数据
		productAdapter = new ProductAdapter(project, state);

		list = new ArrayList<Product>();
		productAdapter.setContext(this);
		productAdapter.setList(list);
		productAdapter.setListener(this);
		listView.setAdapter(productAdapter);
		getProducts();

	}

	/**
	 * 初始底部
	 */
	private View quote;// 报价按钮
	private View refused;// 拒绝
	private View modify;// 修改

	private void initbottom() {
		bottomGroup = (LinearLayout) findViewById(R.id.project_deatail_bottom);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		switch (state) {
		case 0:// 报价

			bottomGroup.removeAllViews();
			View view0 = ShowUtil.LoadXmlView(this,
					R.layout.project_deatail_bottom_item);
			projectRate = (TextView) view0
					.findViewById(R.id.project_deatail_rate);
			projectRemainQuoteNumber = (TextView) view0
					.findViewById(R.id.project_deatail_current_quotenumber);
			totalLastPrice = (TextView) view0
					.findViewById(R.id.project_deatail_last_price_total);
			totalCurrentPrice = (TextView) view0
					.findViewById(R.id.project_deatail_current_price_total);
			quote = view0.findViewById(R.id.project_deatail_quote);
			quote.setOnClickListener(this);
			refused = view0.findViewById(R.id.project_deatail_refused);
			if (project.getCurrentNumber() != 1) {
				refused.setVisibility(View.VISIBLE);
				refused.setOnClickListener(this);
			} else {
				int hSize = MathUtil.diptopx(this, 10);
				int wSize = MathUtil.diptopx(this, 20);
				quote.setPadding(wSize, hSize, wSize, hSize);
				refused.setVisibility(View.GONE);
			}
			totalCurrentPrice();
			totalLastPrice();
			projectRate.setText("基础税率:" + project.getRate() + "%");
			projectRemainQuoteNumber.setText("第" + project.getCurrentNumber()
					+ "轮");
			bottomGroup.addView(view0, params);
			break;
		case 1:// 截止
			bottomGroup.setVisibility(View.GONE);
			break;
		case 2:// 询价
			bottomGroup.removeAllViews();
			View view1 = ShowUtil.LoadXmlView(this,
					R.layout.project_deatail_bottom_item1);

			projectRate = (TextView) view1
					.findViewById(R.id.project_deatail_rate);
			projectRemainQuoteNumber = (TextView) view1
					.findViewById(R.id.project_deatail_current_quotenumber);
			totalLastPrice = (TextView) view1
					.findViewById(R.id.project_deatail_last_price_total);
			totalCurrentPrice = (TextView) view1
					.findViewById(R.id.project_deatail_current_price_total);

			refused = view1.findViewById(R.id.project_deatail_refuse);
			refused.setOnClickListener(this);
			modify = view1.findViewById(R.id.project_deatail_modification);
			modify.setOnClickListener(this);
			quote = view1.findViewById(R.id.project_deatail_quote);
			quote.setOnClickListener(this);
			View comparisonOperate = view1
					.findViewById(R.id.project_deatail_comparison);
			if (project.getCurrentNumber() > 1) {
				comparisonOperate.setVisibility(View.VISIBLE);
				if (project.isCurrentQuote()) {// 已经操作过了

					refused.setBackgroundColor(getResources().getColor(
							R.color.hintColor));
					modify.setBackgroundColor(getResources().getColor(
							R.color.hintColor));
				}
				quote.setVisibility(View.GONE);
			} else {
				comparisonOperate.setVisibility(View.GONE);
				quote.setVisibility(View.VISIBLE);
				if (project.isCurrentQuote()) {// 已经报价了
					quote.setBackgroundColor(getResources().getColor(
							R.color.hintColor));
				} else {// 未报价

					quote.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.blue_gray_select));

				}
			}
			totalCurrentPrice();
			totalLastPrice();
			projectRate.setText("基础税率:" + project.getRate() + "%");
			projectRemainQuoteNumber.setText("第" + project.getCurrentNumber()
					+ "轮");
			bottomGroup.addView(view1, params);
			break;
		}
	}

	private void getProducts() {
		error.setVisibility(View.GONE);
		empty.setVisibility(View.GONE);
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
				HttpProducts ob = (HttpProducts) asynHttpClient;
				if (ob.getStatus() == HttpConstants.SUCCESS) {
					list.addAll(ob.getList());
					productAdapter.notifyDataSetChanged();

					// 初始底部

					initbottom();

				}
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
		HttpMethod.getInstance().getProjectProducts(
				MyApplication.getInstance().getUser().getId(),
				project.getSerialNumber(),
				project.getCurrentNumber(),
				state == 0 ? HttpConstants.BIDDING
						: (state == 2 ? HttpConstants.COMPARISON : -100),
				listener);
	}

	@Override
	public boolean onAdapterItemListener(Object... objects) {
		// TODO Auto-generated method stub
		int type = (Integer) objects[0];
		Product product = (Product) objects[1];
		switch (type) {
		case 0:// 产品详情
			BaseListener listener = new BaseListener() {

				@Override
				public boolean onListener(Object... objects) {
					// TODO Auto-generated method stub
					productAdapter.notifyDataSetChanged();
					return false;
				}
			};
			ProductDeatailActivity.setListener(listener);
			ProductDeatailActivity.setProduct(product);
			if (state == 1
					|| (project.isCurrentQuote() && project.getCurrentNumber() > 1)) {
				ProductDeatailActivity.setModify(false);
			} else {
				ProductDeatailActivity.setModify(true);
			}
			openActivity(ProductDeatailActivity.class);
			break;

		case 1:// 报价修改
			if (this.state != 1) {
				// if (project.getCurrentNumber() != 1 &&
				// project.isCurrentQuote()) {
				// showShortToast("非首轮不能多次修改报价");
				// } else {
				modifyPrice(product);
				// }
			}
			break;
		}
		return false;
	}

	/**
	 * 修改报价
	 */
	private void modifyPrice(final Product product) {
		final EditText editText = new EditText(this);
		editText.setSingleLine(true);
		editText.setText(String.valueOf(product.getCurrentPrice()));
		Dialog dialog = new AlertDialog.Builder(this)
				.setTitle("修改报价")
				.setView(editText)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						String price = editText.getText().toString();

						if (!MathUtil.isFloatNumber(price, 0, Float.MAX_VALUE,
								true, true)) {// 非法的数值
							showShortToast("请输入正确的数值");
						} else if (Float.valueOf(price)
								- product.getCurrentPrice() == 0) {
							showShortToast("请输入不同的报价");
						} else {

							product.setCurrentPrice(Float.parseFloat(price));
							productAdapter.notifyDataSetChanged();
							totalCurrentPrice();// 每次修改后统计总的当前报价
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.project_deatail_more:// 更多轮次
			moreRounds();
			break;
		case R.id.project_deatail_refused:// 拒绝报价
			refusedPrice();
			break;
		case R.id.project_deatail_quote:// 招投标报价或询比价报价
			quotePrice();
			break;
		case R.id.back_item_back:// 返回
			finishBase();
			break;

		case R.id.project_deatail_refuse:// 拒绝询价或拒绝报价
			refusePrice();
			break;
		case R.id.project_deatail_modification:// 修改询价并向对方询价
			modificationPrice();
			break;
		case R.id.project_deatail_error:// 连接错误
			getProducts();
			break;

		}
	}

	/**
	 * 更多轮次
	 */
	private void moreRounds() {
		int leng = project.getCurrentNumber() - 1;
		String[] rounds = new String[leng];
		for (int i = 0; i < leng; i++) {
			int index = i + 1;
			rounds[i] = "第" + index + "轮" + (state == 0 ? "报价" : "询价") + "记录";
		}

		new AlertDialog.Builder(this)
				.setItems(rounds, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						item(arg1 + 1);
					}
				}).create().show();
	}

	/**
	 * 进入记录轮次
	 * 
	 * @param round
	 */
	private void item(int round) {
		switch (state) {
		case 0:// 报价轮次

			break;

		case 2:// 询价轮次
			break;
		}
	}

	/**
	 * 拒绝报价
	 */
	private void refusedPrice() {
		showShortToast("拒绝报价");
	}

	/**
	 * 同意询价
	 */
	private void agreePrice() {
		showShortToast("同意询价");
	}

	/**
	 * 拒绝询价
	 */
	private void refusePrice() {
		if (Time.getInstance().dataIsAbort(project.getEndTime(),
				Time.DATE_PATTERN_6)) {// 项目时间截止
			showShortToast("项目已经截止,无法操作");
			return;
		}
		if (project.isCurrentQuote()) {// 已经操作过了
			return;
		}

		AsynHcResponseListener listener = new AsynHcResponseListener() {

			@Override
			public boolean onTimeout() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				showShortToast(R.string.loading_prompt1);
				return false;
			}

			@Override
			public boolean onSuccess(BaseAsynHttpClient asynHttpClient) {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();

				HttpComparisonOprate ob = (HttpComparisonOprate) asynHttpClient;
				switch (ob.getStatus()) {
				case HttpConstants.SUCCESS:
					project.setCurrentQuote(true);
					if (ctProject != null) {
						ctProject.setCurrentQuote(true);
					}
					refused.setBackgroundColor(getResources().getColor(
							R.color.hintColor));
					modify.setBackgroundColor(getResources().getColor(
							R.color.hintColor));
					showShortToast("操作成功");
					break;

				case HttpConstants.FAIL:
					showShortToast("操作失败");
					break;
				}
				return false;
			}

			@Override
			public boolean onEmpty() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				return false;
			}
		};
		ShowUtil.openHttpDialog(getResources().getString(
				R.string.loading_prompt2));
		HttpMethod.getInstance().projectComparisonOperate(
				MyApplication.getInstance().getUser().getId(),
				project.getCurrentNumber(), HttpConstants.REFUSE,
				project.getSerialNumber(), listener);

	}

	/**
	 * 修改询价
	 */
	private void modificationPrice() {
		if (Time.getInstance().dataIsAbort(project.getEndTime(),
				Time.DATE_PATTERN_6)) {// 项目时间截止
			showShortToast("项目已经截止,无法操作");
			return;
		}
		if (project.isCurrentQuote()) {
			return;
		}

		if (!isChange()) {
			showLongToast(state == 0 ? "无法报价，没有修改任何产品询价" : "无法询价,没有修改任何产品询价");
		} else {
			final ArrayList<Product> changList = getChangeProducts();
			if (changList.size() > 0) {

				BaseListener listener = new BaseListener() {

					@Override
					public boolean onListener(Object... objects) {
						// TODO Auto-generated method stub
						// showShortToast("向服务器询价");
						AsynHcResponseListener listener = new AsynHcResponseListener() {

							@Override
							public boolean onTimeout() {
								// TODO Auto-generated method stub
								ShowUtil.closeHttpDialog();
								showShortToast(R.string.loading_prompt1);
								return false;
							}

							@Override
							public boolean onSuccess(
									BaseAsynHttpClient asynHttpClient) {
								// TODO Auto-generated method stub
								ShowUtil.closeHttpDialog();

								HttpComparisonOprate ob = (HttpComparisonOprate) asynHttpClient;
								switch (ob.getStatus()) {
								case HttpConstants.SUCCESS:
									showShortToast("询价成功");

									project.setCurrentQuote(true);
									if (ctProject != null) {
										ctProject.setCurrentQuote(true);
									}
									refused.setBackgroundColor(getResources()
											.getColor(R.color.hintColor));
									modify.setBackgroundColor(getResources()
											.getColor(R.color.hintColor));
									break;

								case HttpConstants.FAIL:
									showShortToast("询价失败");
									break;
								}

								return false;
							}

							@Override
							public boolean onEmpty() {
								// TODO Auto-generated method stub
								ShowUtil.closeHttpDialog();
								return false;
							}
						};
						ShowUtil.openHttpDialog(getResources().getString(
								R.string.loading_prompt2));
						HttpMethod.getInstance().projectComparisonOperate(
								MyApplication.getInstance().getUser().getId(),
								project.getCurrentNumber(),
								project.getSerialNumber(),
								getQuoteProduct(changList), listener);
						return true;
					}
				};
				ProjectQuoteDeatailActiviy.setListener(listener);
				Bundle bundle = new Bundle();
				bundle.putInt("state", state);
				bundle.putSerializable("list", changList);
				bundle.putSerializable("project", project);
				bundle.putInt("manxProductNumber", list.size());
				openActivity(ProjectQuoteDeatailActiviy.class, bundle);
			}
		}
	}

	/**
	 * 判断能否报价，只要有一个产品没有填写报价，则不能进行报价
	 * 
	 * @return
	 */
	private boolean isCantQuotPrice() {
		for (int i = 0; i < list.size(); i++) {
			Product product = list.get(i);
			if (product.getCurrentPrice() <= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 报价
	 */
	private void quotePrice() {
		switch (state) {
		case 0:// 招投标报价
			biddingQuotePrice();
			break;

		case 2:// 询比价报价
			comparisonQuotePrice();
			break;
		}

	}

	/**
	 * 招投标报价
	 */
	private void biddingQuotePrice() {
		if (project.isCurrentQuote() && project.getCurrentNumber() != 1) {
			showLongToast("非首轮不能进行多次报价");
		} else if (isCantQuotPrice() || !isChange()) {// 第一次没有报价或者没有改动报价
			showLongToast("无法报价,请修改产品报价");
		} else {
			ArrayList<Product> changList = getChangeProducts();
			if (changList.size() > 0) {

				BaseListener listener = new BaseListener() {

					@Override
					public boolean onListener(Object... objects) {
						// TODO Auto-generated method stub
						showShortToast("向服务器报价");

						return false;
					}
				};
				ProjectQuoteDeatailActiviy.setListener(listener);
				Bundle bundle = new Bundle();
				bundle.putInt("state", state);
				bundle.putSerializable("list", changList);
				bundle.putSerializable("project", project);
				bundle.putInt("manxProductNumber", list.size());
				openActivity(ProjectQuoteDeatailActiviy.class, bundle);
			}
		}

	}

	/**
	 * 询比价报价
	 */
	private void comparisonQuotePrice() {
		if (project.isCurrentQuote()&&project.getCurrentNumber()!=1) {// 已经报价，返回
			return;
		} else if (Time.getInstance().dataIsAbort(project.getEndTime(),
				Time.DATE_PATTERN_6)) {// 项目报价时间截止
			showShortToast("项目已经截止,无法操作");
		}

		else {
			final ArrayList<Product> changList = getChangeProducts();
			if (changList.size() > 0) {

				BaseListener listener = new BaseListener() {

					@Override
					public boolean onListener(Object... objects) {
						// TODO Auto-generated method stub
						// showShortToast("向服务器报价");
						httpComparisonQuotePrice(getQuoteProduct(changList));

						return false;
					}
				};
				ProjectQuoteDeatailActiviy.setListener(listener);
				Bundle bundle = new Bundle();
				bundle.putInt("state", 0);
				bundle.putSerializable("list", changList);
				bundle.putSerializable("project", project);
				bundle.putInt("manxProductNumber", list.size());
				openActivity(ProjectQuoteDeatailActiviy.class, bundle);
			}

		}

	}

	/**
	 * 发送询比价报价请求
	 */
	private void httpComparisonQuotePrice(ArrayList<QuoteProduct> list) {
		AsynHcResponseListener listener = new AsynHcResponseListener() {

			@Override
			public boolean onTimeout() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				showShortToast("报价超时");
				return false;
			}

			@Override
			public boolean onSuccess(BaseAsynHttpClient asynHttpClient) {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				HttpComparisonQuote ob = (HttpComparisonQuote) asynHttpClient;
				if (ob.getStatus() == HttpConstants.FAIL) {
					showShortToast("报价失败");
				} else {

					project.setCurrentQuote(true);
					if (ctProject != null) {
						ctProject.setCurrentQuote(true);
					}
					quote.setBackgroundColor(getResources().getColor(
							R.color.hintColor));
					showShortToast("报价成功");
				}
				return false;
			}

			@Override
			public boolean onEmpty() {
				// TODO Auto-generated method stub
				ShowUtil.closeHttpDialog();
				return false;
			}
		};
		ShowUtil.openHttpDialog("报价中...");
		HttpMethod.getInstance().projectComparisonQuote(
				project.getCurrentNumber(), project.getSerialNumber(), list,
				listener);
	}

	/**
	 * 获得改变的产品列表
	 * 
	 * @return
	 */
	private ArrayList<Product> getChangeProducts() {
		ArrayList<Product> changList = new ArrayList<Product>();
		for (int i = 0; i < list.size(); i++) {
			Product product = list.get(i);
			if (product.isChange(project.getCurrentNumber())) {
				changList.add(product);
			}
		}
		return changList;
	}

	private ArrayList<QuoteProduct> getQuoteProduct(ArrayList<Product> pl) {
		ArrayList<QuoteProduct> list = new ArrayList<Product.QuoteProduct>();
		for (int i = 0; i < pl.size(); i++) {
			Product product = pl.get(i);
			QuoteProduct quoteProduct = product.getQuoteProduct();
			list.add(quoteProduct);
		}
		return list;

	}

	/**
	 * 是否改变了价格
	 * 
	 * @return
	 */
	private boolean isChange() {
		for (int i = 0; i < list.size(); i++) {
			Product product = list.get(i);
			if (product.isChange(project.getCurrentNumber())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 统计报价结果
	 * 
	 * @param type
	 *            0，上一次报价 ；1，当前报价
	 * @return
	 */
	private float totalPrice(int type) {
		float total = 0;

		for (int i = 0; i < list.size(); i++) {
			Product product = list.get(i);
			switch (type) {
			case 0:
				total += product.getLastPrice() * product.getNumber();
				break;

			case 1:
				total += product.getCurrentPrice() * product.getNumber();
				break;
			}
		}
		return total;

	}

	/**
	 * 统计并显示当前总报价
	 */
	private void totalCurrentPrice() {
		float total = totalPrice(1);
		totalCurrentPrice.setText("当前总价:" + MathUtil.getAmoutExpress(total)
				+ "元");
	}

	private void totalLastPrice() {
		if (project.getCurrentNumber() == 1) {// 第一次报价
			totalLastPrice.setVisibility(View.GONE);
		} else {
			totalLastPrice.setVisibility(View.VISIBLE);
			float lastTotal = totalPrice(0);
			totalLastPrice.setText("上一轮总价:"
					+ MathUtil.getAmoutExpress(lastTotal) + "元");
		}

	}

}
