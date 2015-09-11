package assembly.stone.itassembly.guokr.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.intf.OnItemLoadListenter;
import assembly.stone.itassembly.base.ui.BaseHomeFragment;
import assembly.stone.itassembly.guokr.adapter.GuokrAdapter;
import assembly.stone.itassembly.guokr.entity.GuoKrModel;
import assembly.stone.itassembly.guokr.entity.GuoKrResult;
import assembly.stone.itassembly.util.DateUtils;
import assembly.stone.itassembly.util.HttpContacts;
import assembly.stone.itassembly.util.JsonBinder;
import assembly.stone.itassembly.wedget.BaseListView;
import assembly.stone.itassembly.wedget.swiptlistview.BaseSwipeRefreshLayout;

public class GuoKrFragment extends BaseHomeFragment implements OnItemLoadListenter {

	private List<GuoKrModel> list = new ArrayList<GuoKrModel>();
	private String getTime;
	private GuokrAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setOnItemLoadListenter(this);
		layoutResourceId = R.layout.fragment_guokr;
		getTime = DateUtils.getsysTime();
		page = 1;
	}

	@Override
	protected void initView() {
		swipeLayout = (BaseSwipeRefreshLayout) mHoseView.findViewById(R.id.gswipe_container);
		mListView = (BaseListView) mHoseView.findViewById(R.id.glistview);
		loading_progress = (ProgressBar) mHoseView.findViewById(R.id.loading_progress);
		adapter = new GuokrAdapter(mActivity, list);
		mListView.setAdapter(adapter);
		super.initView();
	}

	@Override
	public void onRefresh() {
		getTime = DateUtils.getsysTime();
		page = 1;
		super.onRefresh();
	}

	@Override
	protected void fillData(String response) {
		GuoKrResult request = JsonBinder.fromJson(response, GuoKrResult.class);
		loading_progress.setVisibility(View.GONE);
		swipeLayout.setRefreshing(false);
		swipeLayout.setLoading(false);
		if (request.isOk()) {
			if (page == 1) {
				adapter.setData(request.getResult());
			} else {
				for (GuoKrModel guoKrModel : request.getResult()) {
					adapter.addGuoKrModel(guoKrModel);
				}
			}
		} else {

		}
	}

	@Override
	protected String getPageUrl() {
		return HttpContacts.EXTEXT_GUOKR + getTime;
	}

	@Override
	public void onItemClick(int position, long id) {
		Intent intent = new Intent();
		intent.putExtra("detail_tid", adapter.getAid((int) id));
		intent.putExtra("share_content", adapter.getData((int)id).getSummary());
		intent.putExtra("share_title", adapter.getData((int)id).getTitle());
		intent.putExtra("share_url", adapter.getData((int)id).getLink());
		intent.setClass(mActivity, GuoKrDetailActivity.class);
		startActivity(intent);
	}

	@Override
	public void onLoad() {
		try {
			page++;
			getTime = adapter.getDatePicked();
			getData();
		} catch (Exception e) {
		}
	}


	@Override
	protected boolean getListViewData() {
		if (adapter == null) {
			return false;
		}
		if (adapter.getCount() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
