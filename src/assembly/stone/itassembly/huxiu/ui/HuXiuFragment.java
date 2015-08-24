package assembly.stone.itassembly.huxiu.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.intf.OnItemLoadListenter;
import assembly.stone.itassembly.base.ui.BaseHomeFragment;
import assembly.stone.itassembly.huxiu.adapter.HuXiuAdapter;
import assembly.stone.itassembly.huxiu.entity.HuXiuModel;
import assembly.stone.itassembly.huxiu.entity.HuXiuResult;
import assembly.stone.itassembly.util.HttpContacts;
import assembly.stone.itassembly.util.JsonBinder;
import assembly.stone.itassembly.wedget.BaseListView;
import assembly.stone.itassembly.wedget.swiptlistview.BaseSwipeRefreshLayout;

public class HuXiuFragment extends BaseHomeFragment implements OnItemLoadListenter {

	private List<HuXiuModel> list = new ArrayList<HuXiuModel>();
	private HuXiuAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setOnItemLoadListenter(this);
		layoutResourceId = R.layout.fragment_huxiu;
		tag = "httpForhuxiu";
	}

	@Override
	protected void initView() {
		swipeLayout = (BaseSwipeRefreshLayout) mHoseView.findViewById(R.id.hswipe_container);
		mListView = (BaseListView) mHoseView.findViewById(R.id.hlistview);
		loading_progress = (ProgressBar) mHoseView.findViewById(R.id.loading_progress);
		adapter = new HuXiuAdapter(mActivity, list);
		mListView.setAdapter(adapter);
		super.initView();

	}

	@Override
	public void onRefresh() {
		page = 1;
		super.onRefresh();
	}

	@Override
	protected void fillData(String response) {
		HuXiuResult request = JsonBinder.fromJson(response, HuXiuResult.class);
		loading_progress.setVisibility(View.GONE);
		swipeLayout.setRefreshing(false);
		swipeLayout.setLoading(false);
		if (request.getResult() == 0) {
			if (page == 1) {
				adapter.setData(request.getContent());
			} else {
				for (HuXiuModel huXiuModel : request.getContent()) {
					adapter.addHuXiuModel(huXiuModel);
				}
			}
		} else {

		}
	}

	@Override
	public void onItemClick(int position, long id) {
		Intent intent = new Intent();
		intent.putExtra("detail_tid", adapter.getAid((int) id));
		intent.setClass(mActivity, HuXiuDetailActivity.class);
		intent.putExtra("share_content", adapter.getData((int)id).getSummary());
		intent.putExtra("share_title", adapter.getData((int)id).getTitle());
//		intent.putExtra("share_url", adapter.getData((int)id).getLink());
		startActivity(intent);
	}

	@Override
	protected String getPageUrl() {
		return HttpContacts.EXTEXT_HUXIU + page;
	}

	@Override
	public void onLoad() {
		try {
			page++;
			getData();
		} catch (Exception e) {
			// TODO: handle exception
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
