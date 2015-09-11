package assembly.stone.itassembly.baiduvarious.ui;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.baiduvarious.entity.VariousModel;
import assembly.stone.itassembly.baiduvarious.request.VariousRequest;
import assembly.stone.itassembly.baiduvarious.response.VariousReponse;
import assembly.stone.itassembly.base.ui.BaseDetailActivity;
import assembly.stone.itassembly.jsouphttp.BaseResponse;
import assembly.stone.itassembly.jsouphttp.HttpCallback;
import assembly.stone.itassembly.jsouphttp.HttpTask;

public class VariousDetailActivity extends BaseDetailActivity implements HttpCallback {
	private VariousReponse variousReponse = null;
	protected String detailUrl;
	private VariousModel variousModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		detailUrl = getIntent().getExtras().getString("detail_url");
		super.onCreate(savedInstanceState);
		titleBanner = R.string.baidu_various;
		try {
			variousModel = (VariousModel) getIntent().getExtras().getSerializable("various_Model");
			baseDetailUrl = detailUrl;
			shareContent = variousModel.getM_summary();
			shareTitle = variousModel.getM_title();
			shareUrl = variousModel.getM_display_url();
		} catch (Exception e) {
			if (StringUtils.isNotEmpty(mAcache.getAsString(baseDetailUrl))) {
				setLoadErr(mAcache.getAsString(baseDetailUrl));
			} else {
				progress_bar.setVisibility(View.GONE);
				load_err_layout.setVisibility(View.VISIBLE);
				share_lay.setVisibility(View.GONE);
			}
		}

	}

	@Override
	protected void getData() {
		progress_bar.setVisibility(View.VISIBLE);
		VariousRequest variousRequest = new VariousRequest(detailUrl, new VariousReponse(this));
		HttpTask tsk = new HttpTask(this, variousRequest, this, GAIN_START_PIC_URL_TASK_ID);
		taskDispatcher.execute(tsk);
	}

	@Override
	public void onRequestSuccess(int id, BaseResponse result) {
		try {
			share_lay.setVisibility(View.VISIBLE);
			share_lay.startAnimation(AnimationUtils.loadAnimation(VariousDetailActivity.this, R.anim.down_up));
			variousReponse = (VariousReponse) result;
			if (variousReponse != null) {
				setLoadErr(variousReponse.webStr);
			} else {
				setLoadErr(null);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected boolean fillData(String response) {
		if (response == null) {
			return false;
		}
		response = "<p style=\"font-size:10px;color:green;margin:5px\">" + variousModel.getM_writer_name() + "   "
				+ variousModel.getM_create_time() + "</p><div />" + response;
		response = "<p style=\"margin:5px\">" + variousModel.getM_title() + "</p>" + response;
		wView.setLoadDataWithBaseURL(response);
		if (variousReponse != null) {
			wView.setUrls(variousReponse.imgUrls);
		}
		return true;
	}

	@Override
	public void onRequestFail(int id, String reason) {
		if (StringUtils.isNotEmpty(mAcache.getAsString(baseDetailUrl))) {
			setLoadErr(mAcache.getAsString(baseDetailUrl));
		} else {
			progress_bar.setVisibility(View.GONE);
			load_err_layout.setVisibility(View.VISIBLE);
			share_lay.setVisibility(View.GONE);
		}
	}

	@Override
	public void onRequestCancel(int id) {
		if (StringUtils.isNotEmpty(mAcache.getAsString(baseDetailUrl))) {
			setLoadErr(mAcache.getAsString(baseDetailUrl));
		} else {
			progress_bar.setVisibility(View.GONE);
			load_err_layout.setVisibility(View.VISIBLE);
			share_lay.setVisibility(View.GONE);
		}
	}

	@Override
	public void webProgressChanged(int newProgress) {
	}

}
