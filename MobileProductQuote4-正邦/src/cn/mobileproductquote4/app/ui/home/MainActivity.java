package cn.mobileproductquote4.app.ui.home;

import java.util.Timer;
import java.util.TimerTask;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.mobileproductquote3.app.R;
import cn.mobileproductquote4.app.main.MyApplication;
import cn.mobileproductquote4.app.ui.base.BaseFragmentActivity;
import cn.mobileproductquote4.app.view.munedrawer.MenuDrawer;

/**
 * 登录进去的主界面
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends BaseFragmentActivity implements
		OnClickListener {
	private LayoutInflater inflater;// 加载器
	private MenuDrawer mMenuDrawer;// 菜单控件
	private IngProjectFragment ingProjectFragment;// 进行的项目
	private IngEnquiryProjectFragment ingEnquiryProjectFragment;// 进行的询价项目
	private EndProectFragment endProectFragment;// 结束的项目

	private SeachFragment seachFragment;// 搜索

	private ImageButton menu;// 菜单
	private TextView title;// 标题

	@Override
	public void layout() {

		// TODO Auto-generated method stub
		inflater = LayoutInflater.from(this);
		initMenuDrawer();
		initContent();
		initMenu();
		selectIngProject();
	

	}

	/**
	 * 初始化内容
	 */
	private void initContent() {
		mMenuDrawer.setContentView(R.layout.main_content);
		menu = (ImageButton) findViewById(R.id.main_content_menu);
		title = (TextView) findViewById(R.id.main_content_title);
		menu.setOnClickListener(this);
		ingProjectFragment = new IngProjectFragment();
		ingEnquiryProjectFragment = new IngEnquiryProjectFragment();
		endProectFragment = new EndProectFragment();
		seachFragment = new SeachFragment();// 搜索
	}

	/**
	 * 初始菜单
	 */
	private Button enquiryProject;// 询价项目
	private Button tenderProject; // 招投标项目
	private Button endProject;// 结束的项目
	private Button outApp;// 退出程序
	private TextView userName;// 用户名称
	private ImageView userIcon;// 用户头像

	private void initMenu() {

		View view = inflater.inflate(R.layout.main_menu, null);
		// 项目选择
		enquiryProject = (Button) view
				.findViewById(R.id.main_menu_ing_enquiry_project);
		enquiryProject.setOnClickListener(this);// 询价项目
		tenderProject = (Button) view
				.findViewById(R.id.main_menu_ing_tender_project);
		tenderProject.setOnClickListener(this);// 投标项目
		endProject = (Button) view.findViewById(R.id.main_menu_end_project);
		endProject.setOnClickListener(this);// 结束的项目
		// 用户初始
		view.findViewById(R.id.user_item_out_app).setOnClickListener(this);

		userName = (TextView) view.findViewById(R.id.user_item_name);
		userIcon = (ImageView) view.findViewById(R.id.user_item_icon);
		userName.setText(MyApplication.getInstance().getUser().getRealname());
		// 搜索
		view.findViewById(R.id.seacher_root).setOnClickListener(this);// 搜索
		mMenuDrawer.setMenuView(view);
	}

	/**
	 * 初始控制器
	 */
	private void initMenuDrawer() {
		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_item_out_app:// 退出程序
			MyApplication.outApp(true);
			break;
		case R.id.main_content_menu:
			showMenu();
			break;
		case R.id.main_menu_ing_enquiry_project:// 询价
			selectIngProject1();
			break;
		case R.id.main_menu_ing_tender_project:// 投标
			selectIngProject();
			break;
		case R.id.main_menu_end_project:
			selectEndProject();
			break;
		case R.id.seacher_root:// 搜索
			selectSeach();
			break;

		}
	}

	/**
	 * 操作菜单
	 */
	private void showMenu() {
		if (mMenuDrawer.isMenuVisible()) {
			mMenuDrawer.closeMenu();
		} else {
			mMenuDrawer.openMenu();
		}
	}

	/**
	 * 进行的招投标项目
	 */
	private void selectIngProject() {
		clearButtonbackGround();
		tenderProject
				.setBackgroundColor(getResources().getColor(R.color.slectColor));
		
		tenderProject.setTextColor(getResources().getColor(
				R.color.orange));
	
		setCurrentFragment(ingProjectFragment, R.id.main_content_container);
		title.setText("进行的招投标项目");
		mMenuDrawer.closeMenu();
	}

	/**
	 * 重置选项背景
	 */
	private void clearButtonbackGround() {
		enquiryProject.setBackgroundColor(getResources().getColor(
				R.color.noSlectColor));
		tenderProject.setBackgroundColor(getResources().getColor(
				R.color.noSlectColor));
		endProject.setBackgroundColor(getResources().getColor(
				R.color.noSlectColor));
		
		enquiryProject.setTextColor(getResources().getColor(
				R.color.shallowGray));
		tenderProject.setTextColor(getResources().getColor(
				R.color.shallowGray));
		endProject.setTextColor(getResources().getColor(
				R.color.shallowGray));
	}

	/**
	 * 进行的询价项目
	 */
	private void selectIngProject1() {
		clearButtonbackGround();
		enquiryProject.setBackgroundColor(getResources()
				.getColor(R.color.slectColor));


		enquiryProject.setTextColor(getResources().getColor(
				R.color.orange));
		
		
		setCurrentFragment(ingEnquiryProjectFragment,
				R.id.main_content_container);
		title.setText("进行的询价项目");
		mMenuDrawer.closeMenu();
	}

	/**
	 * 结束的项目
	 */
	private void selectEndProject() {
		clearButtonbackGround();
		endProject.setBackgroundColor(getResources()
				.getColor(R.color.slectColor));
		
		
		endProject.setTextColor(getResources().getColor(
				R.color.orange));
		setCurrentFragment(endProectFragment, R.id.main_content_container);
		title.setText("结束的项目");
		mMenuDrawer.closeMenu();

	}

	/**
	 * 搜索
	 */
	private void selectSeach() {
		clearButtonbackGround();
		setCurrentFragment(seachFragment, R.id.main_content_container);
		title.setText("搜索");
		mMenuDrawer.closeMenu();
	}

	private final long time = 2000;// 间隔时间毫秒
	private boolean isOut = false;// 是否退出

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == event.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			back();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	public void back() {
		// TODO Auto-generated method stub

		if (isOut) {

			MyApplication.outApp(true);// 退出
		} else {
			isOut = true;
			showShortToast("再按一次返回键退出软件");
			// 设置定时任务
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					isOut = false;
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, time);
		}

	}

}
