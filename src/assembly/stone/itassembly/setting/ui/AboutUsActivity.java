package assembly.stone.itassembly.setting.ui;

import android.os.Bundle;
import android.view.View;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.ui.BaseDetailActivity.SimpleBaseActivity;

public class AboutUsActivity extends SimpleBaseActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		setContentView(R.layout.about_us);
		super.onCreate(bundle);
		titleBanner = R.string.about_us;
	}

	@Override
	protected void onStart() {
		super.onStart();
		textsize_btn.setVisibility(View.GONE);
	}
}
