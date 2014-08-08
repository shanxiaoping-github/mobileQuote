package cn.mobileproductquote0.app.manager;

import java.util.HashMap;
import java.util.Stack;

import cn.mobileproductquote0.app.ui.base.BaseActivity;

/**
 * ui界面管理
 * 
 * @author Administrator
 * 
 */
public class ActivityManager {
	private static ActivityManager instance = null;

	public static ActivityManager getInstance() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}

	private Stack<BaseActivity> stack = new Stack<BaseActivity>();// activity栈
	private HashMap<String, BaseActivity> activityMap = new HashMap<String, BaseActivity>();// 键值activity

	/**
	 * 返回最顶端的activity,并弹出删除
	 * 
	 * @return
	 */
	public BaseActivity pop() {

		return activityMap.remove(stack.pop().getClass().getName());

	}

	/* 添加activity */
	public void push(BaseActivity activity) {
		stack.push(activity);

		activityMap.put(activity.getClass().getName(), activity);

	}

	/* 查看当前activity */
	public BaseActivity peek() {
		
		return stack.peek();
	}

	/* 查看指定的activity */
	public BaseActivity peek(String activityName, boolean delect) {
		if (delect)// 查看并删除
		{
			BaseActivity activity = activityMap.remove(activityName);
			stack.remove(activity);
			return activity;
			
		} else {// 查看不删除
			return activityMap.get(activityName);
		}

	}
	/* 查看指定的activity,不删除*/
	public BaseActivity peek(String activityName){
		return peek(activityName, false);

	}
	/*清空*/
	public void clear(){
		stack.clear();
		activityMap.clear();
		
	}

}
