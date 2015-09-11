package assembly.stone.itassembly.huxiu.ui;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.ui.BaseDetailActivity;
import assembly.stone.itassembly.huxiu.entity.HuXiDetailuResult;
import assembly.stone.itassembly.huxiu.entity.HuXiuDetailModel;
import assembly.stone.itassembly.util.DateUtils;
import assembly.stone.itassembly.util.HttpContacts;
import assembly.stone.itassembly.util.JsonBinder;

public class HuXiuDetailActivity extends BaseDetailActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		tag = "httpForHuXiuDetail";
		url = HttpContacts.EXTEXT_HUXIU_DETAIL;
		super.onCreate(savedInstanceState);
		titleBanner = R.string.huxiu;

		try {
			shareContent = getIntent().getExtras().getString("share_content");
			shareTitle = getIntent().getExtras().getString("share_title");
			shareUrl = url;
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
	protected boolean fillData(String response) {
		HuXiDetailuResult request = JsonBinder.fromJson(response, HuXiDetailuResult.class);
		progress_bar.setVisibility(View.GONE);
		if (request == null) {
			return false;
		}
		HuXiuDetailModel huXiuDetailModel = request.getContent();
		if (huXiuDetailModel == null) {
			return false;
		}
		String str = huXiuDetailModel.getContent();
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		str = str.replace("<img", "<img style=\"width:100%\" ");
		str = str.replace("<div class=\"nr-other-box nr-relevant-article\"",
				"<div class=\"nr-other-box nr-relevant-article\" style=\"display:none\"");
		str = "<p style=\"font-size:10px;color:green;margin:5px\">" + request.getContent().getAuthor() + "   "
				+ DateUtils.getStrTime(request.getContent().getDateline(), "yyyy-MM-dd HH:mm") + "</p><div />" + str;
		str = "<p style=\"margin:5px\">" + request.getContent().getTitle() + "</p>" + str;

		wView.setLoadDataWithBaseURL(str);
		return true;
	}

}
