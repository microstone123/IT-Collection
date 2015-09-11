package assembly.stone.itassembly.setting.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.ui.BaseDetailActivity.SimpleBaseActivity;
import assembly.stone.itassembly.share.ShareOperating;

import com.umeng.socialize.sso.UMSsoHandler;

public class ShareSoftwareActivity extends SimpleBaseActivity {
	public ShareOperating shareOperating;
	private LinearLayout sina, wechat_circle, wechat, qq, qzone;

	@Override
	protected void onCreate(Bundle bundle) {
		setContentView(R.layout.share_software);
		super.onCreate(bundle);
		titleBanner = R.string.share_tx;
		shareOperating = new ShareOperating(this);
		setView();
	}

	@Override
	public void setView() {
		sina = (LinearLayout) findViewById(R.id.sina);
		wechat_circle = (LinearLayout) findViewById(R.id.wechat_circle);
		wechat = (LinearLayout) findViewById(R.id.wechat);
		qq = (LinearLayout) findViewById(R.id.qq);
		qzone = (LinearLayout) findViewById(R.id.qzone);
		sina.setOnClickListener(this);
		wechat_circle.setOnClickListener(this);
		wechat.setOnClickListener(this);
		qq.setOnClickListener(this);
		qzone.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		textsize_btn.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.sina:
			shareOperating.shareToSina(getResources().getString(R.string.share_content),
					getResources().getString(R.string.share_title));
			// initData(getResources().getString(R.string.share_sina));
			break;
		case R.id.wechat_circle:
			shareOperating.shareToCircle(getResources().getString(R.string.share_content),
					getResources().getString(R.string.share_title));
			break;
		case R.id.wechat:
			shareOperating.shareToWeixin(getResources().getString(R.string.share_content),
					getResources().getString(R.string.share_title));
			// initData(getResources().getString(R.string.share_weixin));
			break;
		case R.id.qq:
			shareOperating.shareToQQ(getResources().getString(R.string.share_content),
					getResources().getString(R.string.share_title));
			// initData(getResources().getString(R.string.share_qq));
			break;
		case R.id.qzone:
			shareOperating.shareToQZone(getResources().getString(R.string.share_content),
					getResources().getString(R.string.share_title));
			// initData(getResources().getString(R.string.share_qq_zone));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = shareOperating.mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

}
