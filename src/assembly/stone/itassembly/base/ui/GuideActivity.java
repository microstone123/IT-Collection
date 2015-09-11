package assembly.stone.itassembly.base.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.adapter.GuideViewPagerAdapter;

/**
 * 第一次使用指引
 * 
 * @author Ray
 * 
 */
public class GuideActivity extends Activity implements OnPageChangeListener {

	private ViewPager vp;
	private GuideViewPagerAdapter vpAdapter;
	private List<View> views;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_adapter);
		// 初始化页面
		initViews();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// TestinAgent.onResume(this);//此行必须放在super.onResume后
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// TestinAgent.onStop(this);//此行必须放在super.onResume后
	}

	@SuppressLint("InflateParams")
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.guide_view00, null));
		views.add(inflater.inflate(R.layout.guide_view01, null));
		views.add(inflater.inflate(R.layout.guide_view02, null));
		views.add(inflater.inflate(R.layout.guide_view03, null));
		// 初始化Adapter
		vpAdapter = new GuideViewPagerAdapter(views, this);

		vp = (ViewPager) findViewById(R.id.guideAdapter);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	// 当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 拦截返回键， 回到手机主屏幕
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// ע��
			intent.addCategory(Intent.CATEGORY_HOME);
			this.startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
