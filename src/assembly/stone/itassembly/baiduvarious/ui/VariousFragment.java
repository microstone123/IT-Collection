package assembly.stone.itassembly.baiduvarious.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.animetaste.ui.AnimetasteFragment;
import assembly.stone.itassembly.baiduvarious.adapter.VariousAdapter;
import assembly.stone.itassembly.baiduvarious.entity.VariousModel;
import assembly.stone.itassembly.baiduvarious.entity.VariousResponse;
import assembly.stone.itassembly.baiduvarious.entity.VariousResponse.DataModel;
import assembly.stone.itassembly.base.intf.OnItemLoadListenter;
import assembly.stone.itassembly.base.ui.BaseHomeFragment;
import assembly.stone.itassembly.base.ui.MainActivity;
import assembly.stone.itassembly.util.HttpContacts;
import assembly.stone.itassembly.util.JsonBinder;
import assembly.stone.itassembly.wedget.swiptlistview.BaseSwipeRefreshLayout;
import assembly.stone.itassembly.wedget.swiptlistview.VariousSwipeListView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link AnimetasteFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link VariousFragment#newInstance} factory method to create an instance of
 * this fragment.
 */
public class VariousFragment extends BaseHomeFragment implements OnItemLoadListenter {

	private VariousAdapter adapter;
	private int prevarticalid = 0;
	private List<VariousModel> variousModelList = new ArrayList<VariousModel>();
	private VariousHeadFragment variousHeadFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setOnItemLoadListenter(this);
		layoutResourceId = R.layout.fragment_various;
		tag = "httpForVarious";
		if (page == 1) {
			prevarticalid = 0;
		}
	}

	@Override
	protected void initView() {
		swipeLayout = (BaseSwipeRefreshLayout) mHoseView.findViewById(R.id.swipe_container);
		mListView = (VariousSwipeListView) mHoseView.findViewById(R.id.listview);
		variousHeadFragment = (VariousHeadFragment) ((MainActivity) mActivity).getSupportFragmentManager()
				.findFragmentById(R.id.picfragment);
		loading_progress = (ProgressBar) mHoseView.findViewById(R.id.loading_progress);
		adapter = new VariousAdapter(mActivity, variousModelList);
		mListView.setAdapter(adapter);
		super.initView();
	}

	@Override
	public void onRefresh() {
		page = 1;
		prevarticalid = 0;
		variousHeadFragment.fillData();
		super.onRefresh();
	}

	@Override
	protected void fillData(String response) {
		loading_progress.setVisibility(View.GONE);
		VariousResponse variousResponse = JsonBinder.fromJson(response, VariousResponse.class);
		DataModel dataModel = variousResponse.getData();
		variousModelList = dataModel.getList();
		if (variousModelList != null) {
			if (page == 1) {
				adapter.setData(variousModelList);
			} else {
				for (VariousModel variousModel : variousModelList) {
					adapter.addVariousModel(variousModel);
				}
			}
		}
		swipeLayout.setRefreshing(false);
		swipeLayout.setLoading(false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onItemClick(int position, long id) {
		Intent intent = new Intent();
		String url = adapter.getData((int) id).getM_display_url();
		intent.putExtra("detail_url", url);
		intent.putExtra("various_Model", adapter.getData((int) id));
		intent.setClass(mActivity, VariousDetailActivity.class);
		startActivity(intent);
	}

	@Override
	protected String getPageUrl() {
		return HttpContacts.BAIDU_VARIOUS + "&page=" + page + "&prevarticalid=" + prevarticalid;
	}

	@Override
	public void onLoad() {
		try {
			page++;
			prevarticalid = adapter.getData(adapter.getCount() - 1).getM_id();
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
