package assembly.stone.itassembly.base.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.util.ConstantUtils;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends Activity {

	private static final int sleepTime = 3 * 1000;
//	ShimmerTextView tv;
//	Shimmer shimmer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
//		tv = (ShimmerTextView) findViewById(R.id.collection_tv);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				SharedPreferences sp = getSharedPreferences("CreateShortcut", MODE_PRIVATE);
				boolean firstuse = sp.getBoolean(ConstantUtils.EXTRA_START_FIRST, true);
				if (firstuse) {
					startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				} else {
					startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
				finish();
			}
		}, sleepTime);

	}

	@Override
	protected void onResume() {
		super.onResume();
//		shimmer = new Shimmer();
//		shimmer.start(tv);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		shimmer.cancel();
//		shimmer = null;
	}

}
