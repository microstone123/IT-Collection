package assembly.stone.itassembly.guokr.ui;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.ui.BaseDetailActivity;
import assembly.stone.itassembly.guokr.entity.GuoKrModel;
import assembly.stone.itassembly.guokr.entity.GuoKrResult;
import assembly.stone.itassembly.util.DateUtils;
import assembly.stone.itassembly.util.HttpContacts;
import assembly.stone.itassembly.util.JsonBinder;

public class GuoKrDetailActivity extends BaseDetailActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		tag = "httpForGuoKrDetail";
		url = HttpContacts.EXTEXT_GUOKR_DETAIL;
		super.onCreate(savedInstanceState);
		titleBanner = R.string.guokr;
		
		try {
			shareContent = getIntent().getExtras().getString("share_content");
			shareTitle = getIntent().getExtras().getString("share_title");
			shareUrl = getIntent().getExtras().getString("share_url");
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
		GuoKrResult request = JsonBinder.fromJson(response, GuoKrResult.class);
		List<GuoKrModel> list = request.getResult();
		progress_bar.setVisibility(View.GONE);
		if (list == null) {
			return false;
		}
		if (list.size() == 0) {
			return false;
		}
		GuoKrModel guoKrModel = list.get(0);
		if (guoKrModel == null) {
			return false;
		}
		String str = guoKrModel.getContent();
		str = str.replace("<img", "<img style=\"width:100%\" ");
		str = "<p style=\"font-size:10px;color:green;margin:5px\">" + guoKrModel.getAuthor() + "   "
				+ DateUtils.getStrTime(String.valueOf(guoKrModel.getDate_picked()), "yyyy-MM-dd HH:mm") + "</p><div />"
				+ str;
		str = "<p style=\"margin:5px\">" + guoKrModel.getTitle() + "</p>" + str;
		wView.setLoadDataWithBaseURL(str);
		wView.setUrls(guoKrModel.getImages());
		return true;
	}

}
