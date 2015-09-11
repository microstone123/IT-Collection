package assembly.stone.itassembly.base.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import assembly.stone.itassembly.base.manager.RequestManager;
import assembly.stone.itassembly.jsouphttp.RequestIdGenFactory;
import assembly.stone.itassembly.util.ACache;

import com.android.volley.Request;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseFragment extends Fragment {

	protected Activity mActivity;
	protected Object tag;
	protected ACache mACache;
	protected View mHoseView;
	protected int layoutResourceId;
	protected static final int FRONT_PAGE_TASK_ID = RequestIdGenFactory.gen();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
		mACache = ACache.get(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mHoseView == null) {
			mHoseView = inflater.inflate(layoutResourceId, null);
			initView();
		}
		// 缓存的mHoseView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个mHoseView已经有parent的错误。
		ViewGroup parent = (ViewGroup) mHoseView.getParent();
		if (parent != null) {
			parent.removeView(mHoseView);
		}
		return mHoseView;
	}

	protected void executeRequest(Object tag, Request<?> request) {
		RequestManager.addRequest(tag, request);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		RequestManager.cancelAll(tag);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClass().getSimpleName()); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
		MobclickAgent.onResume(mActivity); // 统计时长
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getClass().getSimpleName());
		MobclickAgent.onPause(mActivity);
	}

	protected abstract void initView();

	protected abstract void getData();

}
