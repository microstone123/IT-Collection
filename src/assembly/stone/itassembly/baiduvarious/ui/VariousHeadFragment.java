package assembly.stone.itassembly.baiduvarious.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.animetaste.ui.AnimetasteFragment;
import assembly.stone.itassembly.baiduvarious.adapter.PicTurnsAdapter;
import assembly.stone.itassembly.baiduvarious.entity.VariousHeadModel;
import assembly.stone.itassembly.baiduvarious.request.PicassoVariousRequest;
import assembly.stone.itassembly.baiduvarious.response.PicassoVariousReponse;
import assembly.stone.itassembly.base.ui.BaseHomeFragment;
import assembly.stone.itassembly.jsouphttp.BaseResponse;
import assembly.stone.itassembly.jsouphttp.HttpCallback;
import assembly.stone.itassembly.jsouphttp.HttpTask;
import assembly.stone.itassembly.jsouphttp.TaskDispatcher;
import assembly.stone.itassembly.wedget.viewflow.CircleFlowIndicator;
import assembly.stone.itassembly.wedget.viewflow.TurnsViewFlow;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link AnimetasteFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link VariousHeadFragment#newInstance} factory method to create an instance
 * of this fragment.
 */
public class VariousHeadFragment extends BaseHomeFragment implements HttpCallback {
	private TurnsViewFlow viewFlow;
	public TaskDispatcher taskDispatcher;
	private PicTurnsAdapter picTurnsAdapter;
	private int flowItem = 0;

	private static final int SELECTION_TIMER = 5 * 1000;
	private List<VariousHeadModel> picTurnsInfoList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layoutResourceId = R.layout.pic_turns;
		taskDispatcher = TaskDispatcher.getInstance();
	}

	@Override
	protected void initView() {
		viewFlow = (TurnsViewFlow) mHoseView.findViewById(R.id.viewflow);
		getData();
		fillData();
	}

	@SuppressWarnings("unchecked")
	protected void getData() {
		picTurnsInfoList = (List<VariousHeadModel>) mACache.getAsObject("pic_turns");
		if (picTurnsInfoList == null) {
			picTurnsInfoList = new ArrayList<VariousHeadModel>();
		}
		picTurnsAdapter = new PicTurnsAdapter(mActivity, picTurnsInfoList);
		viewFlow.setAdapter(picTurnsAdapter);
		viewFlow.setmSideBuffer(picTurnsInfoList.size()); // 实际图片张数，
		CircleFlowIndicator indic = (CircleFlowIndicator) mHoseView.findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
		viewFlow.setTimeSpan(SELECTION_TIMER);
		viewFlow.startAutoFlowTimer(); // 启动自动播放
		viewFlow.setSelection(flowItem);
	}

	private void startView(List<VariousHeadModel> picTurnsInfoList) {
		viewFlow.stopAutoFlowTimer();
		picTurnsAdapter = new PicTurnsAdapter(mActivity, picTurnsInfoList);
		viewFlow.setAdapter(picTurnsAdapter);
		viewFlow.setmSideBuffer(picTurnsInfoList.size()); // 实际图片张数，
		viewFlow.setSelection(flowItem);
		viewFlow.startAutoFlowTimer();
	}

	@Override
	public void onResume() {
		super.onResume();
		viewFlow.startAutoFlowTimer(); // 启动自动播放
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		super.onPause();
		flowItem = viewFlow.mCurrentAdapterIndex;
		viewFlow.stopAutoFlowTimer();
	}

	protected void fillData() {
		PicassoVariousRequest clientPosterConfigRequest = new PicassoVariousRequest(
				new PicassoVariousReponse(mActivity));
		mTsk = new HttpTask(mActivity, clientPosterConfigRequest, FRONT_PAGE_TASK_ID);
		mTsk.setCallback(this);
		taskDispatcher.execute(mTsk);
	}

	@Override
	public void onRequestSuccess(int id, BaseResponse result) {
		PicassoVariousReponse clientPosterConfigResult = (PicassoVariousReponse) result;
		picTurnsInfoList = clientPosterConfigResult.picTurnsInfoList;
		startView(picTurnsInfoList);
		if (picTurnsInfoList.size() != 0) {
			mACache.put(getClass().getSimpleName(), (Serializable) picTurnsInfoList);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRequestFail(int id, String reason) {
		if (mACache.getAsObject(getClass().getSimpleName()) != null) {
			picTurnsInfoList = (List<VariousHeadModel>) mACache.getAsObject(getClass().getSimpleName());
			startView(picTurnsInfoList);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onRequestCancel(int id) {
		if (mACache.getAsObject(getClass().getSimpleName()) != null) {
			picTurnsInfoList = (List<VariousHeadModel>) mACache.getAsObject(getClass().getSimpleName());
			startView(picTurnsInfoList);
		}
	}

	@Override
	protected void fillData(String response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getPageUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean getListViewData() {
		// TODO Auto-generated method stub
		return true;
	}
}
