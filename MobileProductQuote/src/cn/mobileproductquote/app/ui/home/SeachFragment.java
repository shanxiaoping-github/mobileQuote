package cn.mobileproductquote.app.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import cn.mobileproductquote.app.R;
import cn.mobileproductquote.app.ui.base.BaseFragment;
import cn.mobileproductquote.app.util.ShowUtil;
import cn.mobileproductquote.app.util.StringUtil;
/**
 * 搜索
 * @author Administrator
 *
 */
public class SeachFragment extends BaseFragment implements OnClickListener{
   private EditText content;//内容
   private ImageButton icon;//图标
	@Override
	public View layout(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View contentView = inflater.inflate(R.layout.seach,null);
		content=(EditText)contentView.findViewById(R.id.seach_content);
		icon=(ImageButton)contentView.findViewById(R.id.seach_icon);
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
			ShowUtil.showShortToast(getContext(),"对不起，没有搜索到你所需要的项目");
		}
	}

}
