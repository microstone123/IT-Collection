package assembly.stone.itassembly.base.ui;

import org.apache.commons.lang.StringUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.intf.OnItemLoadListenter;
import assembly.stone.itassembly.base.manager.RequestManager;
import assembly.stone.itassembly.jsouphttp.HttpTask;
import assembly.stone.itassembly.jsouphttp.RequestIdGenFactory;
import assembly.stone.itassembly.util.ToastUtil;
import assembly.stone.itassembly.wedget.swiptlistview.BaseSwipeRefreshLayout;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public abstract class BaseHomeFragment extends BaseFragment implements BaseSwipeRefreshLayout.OnRefreshListener,
		BaseSwipeRefreshLayout.OnLoadListener, OnClickListener {

	protected ProgressBar loading_progress;
	public HttpTask mTsk;
	protected int page = 1;
	protected static final int FRONT_PAGE_TASK_ID = RequestIdGenFactory.gen();
	protected BaseSwipeRefreshLayout swipeLayout;
	protected ListView mListView;
	protected String url;
	protected LinearLayout errLayout;
	protected Button load_again_btn;
	protected OnItemLoadListenter onItemLoadListenter;
	protected String className;

	protected abstract boolean getListViewData();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		className = getClass().getSimpleName();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InlinedApi")
	public void initBaseSwipeRefreshLayout(BaseSwipeRefreshLayout swipeLayout) {
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		RequestManager.cancelAll(tag);
	}

	@Override
	protected void initView() {
		load_again_btn = (Button) mHoseView.findViewById(R.id.load_again_btn);
		load_again_btn.setOnClickListener(this);
		errLayout = (LinearLayout) mHoseView.findViewById(R.id.load_err_layout);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setOnLoadListener(this);
		initBaseSwipeRefreshLayout(swipeLayout);
		loading_progress.setVisibility(View.GONE);
		swipeLayout.setRefreshing(true);
		getData();
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onItemLoadListenter.onItemClick(position, id);
				mActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});
	}

	protected void setIntent(Class<?> cls) {
		startActivity(new Intent(mActivity, cls));
		mActivity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	protected abstract String getPageUrl();

	protected abstract void fillData(String response);

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.load_again_btn:
			onDoubleTouch();
			break;

		default:
			break;
		}

	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		getData();
	}

	protected void getData() {
		Log.d("getPageUrl()", getPageUrl());
		executeRequest(tag, new StringRequest(Method.GET, getPageUrl(), responseListener(), errorListener()));
	}

	protected Response.Listener<String> responseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					swipeLayout.setRefreshing(false);
					swipeLayout.setLoading(false);
					errLayout.setVisibility(View.GONE);
					if (page == 1) {
						if (StringUtils.isNotEmpty(response)) {
							mACache.remove(className);
							mACache.put(className, response);
						}
					}
					fillData(response);
				} catch (Exception e) {
					Log.e("Exception", e + "");
				}
			}
		};
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				swipeLayout.setRefreshing(false);
				swipeLayout.setLoading(false);
				loading_progress.setVisibility(View.GONE);
				if (!getListViewData()) {
					if (StringUtils.isNotEmpty(mACache.getAsString(className))) {
						fillData(mACache.getAsString(className));
					} else {
						errLayout.setVisibility(View.VISIBLE);
					}
				}
				ToastUtil.show(mActivity, mActivity.getResources().getString(R.string.is_err_network));

			}
		};
	}

	public OnItemLoadListenter getOnItemLoadListenter() {
		return onItemLoadListenter;
	}

	public void setOnItemLoadListenter(OnItemLoadListenter onItemLoadListenter) {
		this.onItemLoadListenter = onItemLoadListenter;
	}

	public void onDoubleTouch() {
		try {
			mListView.setSelection(0);
			swipeLayout.setRefreshing(true);
			onRefresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static class SimpleBaseHomeFragment extends BaseHomeFragment {

		@Override
		public void onLoad() {
			// TODO Auto-generated method stub

		}

		@Override
		protected String getPageUrl() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void fillData(String response) {
			// TODO Auto-generated method stub

		}

		@Override
		protected void initView() {
		}

		@Override
		protected boolean getListViewData() {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
