package assembly.stone.itassembly.leiphone.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.intf.OnItemLoadListenter;
import assembly.stone.itassembly.base.ui.BaseHomeFragment;
import assembly.stone.itassembly.leiphone.adapter.LeiPhoneAdapter;
import assembly.stone.itassembly.leiphone.entity.LeiPhoneResult;
import assembly.stone.itassembly.leiphone.entity.LeiphoneModel;
import assembly.stone.itassembly.util.HttpContacts;
import assembly.stone.itassembly.util.JsonBinder;
import assembly.stone.itassembly.wedget.BaseListView;
import assembly.stone.itassembly.wedget.swiptlistview.BaseSwipeRefreshLayout;

public class LeiPhoneFragment extends BaseHomeFragment implements OnItemLoadListenter {

	private List<LeiphoneModel> list = new ArrayList<LeiphoneModel>();
	private LeiPhoneAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setOnItemLoadListenter(this);
		layoutResourceId = R.layout.fragment_leiphone;
		tag = "httpForLeiPhone";
	}

	@Override
	protected void initView() {
		swipeLayout = (BaseSwipeRefreshLayout) mHoseView.findViewById(R.id.lswipe_container);
		mListView = (BaseListView) mHoseView.findViewById(R.id.llistview);
		loading_progress = (ProgressBar) mHoseView.findViewById(R.id.loading_progress);
		adapter = new LeiPhoneAdapter(mActivity, list);
		mListView.setAdapter(adapter);
		page = 1;
		super.initView();
	}

	@Override
	protected void fillData(String response) {
		LeiPhoneResult request = JsonBinder.fromJson(response, LeiPhoneResult.class);
		loading_progress.setVisibility(View.GONE);
		swipeLayout.setRefreshing(false);
		swipeLayout.setLoading(false);
		if (request.getResult() == 200) {
			if (page == 1) {
				adapter.setData(request.getContent().getArtlist());
			} else {
				for (LeiphoneModel leiphoneModel : request.getContent().getArtlist()) {
					Log.e("LeiphoneModel", "LeiphoneModel");
					adapter.addLeiphoneModel(leiphoneModel);
				}
			}
		} else {

		}
	}

	@Override
	public void onRefresh() {
		page = 1;
		super.onRefresh();
	}

	@Override
	public void onItemClick(int position, long id) {
		Intent intent = new Intent();
		intent.putExtra("detail_tid", adapter.getTid((int) id));
		intent.setClass(mActivity, LeiPhoneDetailActivity.class);
		intent.putExtra("share_content", adapter.getData((int) id).getSummary());
		intent.putExtra("share_title", adapter.getData((int) id).getTitle());
		startActivity(intent);
	}

	@Override
	protected String getPageUrl() {
		return HttpContacts.EXTEXT_LEIPHONE + page;
	}

	@Override
	public void onLoad() {
		try {
			page++;
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
