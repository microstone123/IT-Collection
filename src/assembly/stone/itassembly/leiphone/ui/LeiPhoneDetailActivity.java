package assembly.stone.itassembly.leiphone.ui;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.ui.BaseDetailActivity;
import assembly.stone.itassembly.leiphone.entity.LeiPhoneDetailModel;
import assembly.stone.itassembly.leiphone.entity.LeiPhoneDetailResult;
import assembly.stone.itassembly.util.DateUtils;
import assembly.stone.itassembly.util.HttpContacts;
import assembly.stone.itassembly.util.JsonBinder;

public class LeiPhoneDetailActivity extends BaseDetailActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		tag = "httpForDetail";
		url = HttpContacts.EXTEXT_LEIPHONE_DETAIL;
		super.onCreate(savedInstanceState);
		titleBanner = R.string.lei_phone;
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
		LeiPhoneDetailResult request = JsonBinder.fromJson(response, LeiPhoneDetailResult.class);
		LeiPhoneDetailModel leiPhoneDetailModel = request.getContent();
		if (leiPhoneDetailModel == null) {
			return false;
		}
		String str = leiPhoneDetailModel.getDetail();
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		str = str.replace("<embed", "<embed style=\"display:none\"");
		str = str.replace("<img", "<img style=\"width:100%\" ");
		str = "<p style=\"font-size:10px;color:green;margin:5px\">" + request.getContent().getUname() + " "
				+ DateUtils.getStrTime(request.getContent().getDate(), "yyyy-MM-dd HH:mm") + "</p><div />" + str;
		str = "<p style=\"margin:5px\">" + request.getContent().getTitle() + "</p>" + str;

		String[] urls = new String[request.getContent().getImglist().size()];
		wView.setLoadDataWithBaseURL(str);
		if (request.getContent().getImglist().size() > 0) {
			for (int i = 0; i < request.getContent().getImglist().size(); i++) {
				urls[i] = request.getContent().getImglist().get(i).getPath();
			}
			wView.setUrls(urls);
		}
		return true;
	}
}
