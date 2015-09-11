package assembly.stone.itassembly.setting.ui;

import android.os.Bundle;
import android.view.View;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.ui.BaseDetailActivity.SimpleBaseActivity;

public class PersonalCustomizateActivity extends SimpleBaseActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		setContentView(R.layout.personal_custom);
		super.onCreate(bundle);
		titleBanner = R.string.personal_customization;
	}

	@Override
	protected void onStart() {
		super.onStart();
		textsize_btn.setVisibility(View.GONE);
	}
}
