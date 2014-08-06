package cn.mobileproductquote1.app.http;

import java.util.ArrayList;

import cn.mobileproductquote1.app.util.StringUtil;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 基础http数据处理类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseAsynHttpClient {
	private boolean isEmpty = false;

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	private boolean isFail = false;

	public void setFail(boolean isFail) {
		this.isFail = isFail;
	}

	protected abstract void parerAsynHcResponse(String content);// 数据解析

	public interface AsynHcResponseListener {// 回调相应接口
		public boolean onSuccess(BaseAsynHttpClient asynHttpClient);// 连接成功

		public boolean onTimeout();// 连接超时

		public boolean onEmpty();// 数据为空
	}

	/**
	 * 相应接口聚集
	 */
	private ArrayList<AsynHcResponseListener> list = new ArrayList<AsynHcResponseListener>();

	public void addAsynHcResponseListenrt(AsynHcResponseListener listener) {
		list.add(listener);
	}

	/* 设置参数 */

	private RequestParams requestParams = null;

	public void setPramas(String[] key, Object... params) {

		requestParams = new RequestParams();
		for (int i = 0; i < key.length; i++) {
			requestParams.put(key[i], String.valueOf(params[i]));
		}

	}

	private int outime = 0;// 超时时间

	public int getOutime() {
		return outime;
	}

	public void setOutime(int outime) {
		this.outime = outime;
	}

	/* 获得请求 */
	public void getUrl(String path) {
		isEmpty = false;
		AsyncHttpClient client = new AsyncHttpClient();

		if (getOutime() > 0) {
			client.setTimeout(getOutime());
		}
		client.get(HttpAddress.getUrl(path), requestParams,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String content) {
						// TODO Auto-generated method stub
						if (StringUtil.stringIsEmpty(content)) {// 数据为空

							reSponse(2);
						} else {
							parerAsynHcResponse(content);
							reSponse(0);
						}
					}

					@Override
					public void onFailure(Throwable error) {
						// TODO Auto-generated method stub
						reSponse(1);
					}

				});

	}

	/**
	 * 成功响应
	 */
	public void reSponse(int state) { // 0成功，1连接超时，2连接被拒
		for (int i = 0; i < list.size(); i++) {
			AsynHcResponseListener listener = list.get(i);
			switch (state) {

			case 0:// 0成功
				if (isEmpty) {
					listener.onEmpty();
					return;
				}
				if (isFail) {
					listener.onTimeout();
					return;
				}

				listener.onSuccess(this);

				break;

			case 1:// 连接超时

				listener.onTimeout();
				break;

			case 2:// 数据为空

				listener.onEmpty();
				break;
			}

		}
	}

}
