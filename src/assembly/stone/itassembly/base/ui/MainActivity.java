package assembly.stone.itassembly.base.ui;

import java.util.ArrayList;
import java.util.List;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.animetaste.ui.AnimetasteFragment;
import assembly.stone.itassembly.baiduvarious.ui.VariousFragment;
import assembly.stone.itassembly.base.intf.OnDoubleTouchListenter;
import assembly.stone.itassembly.broadcast.ConnectionChangeReceiver;
import assembly.stone.itassembly.guokr.ui.GuoKrFragment;
import assembly.stone.itassembly.huxiu.ui.HuXiuFragment;
import assembly.stone.itassembly.leiphone.ui.LeiPhoneFragment;
import assembly.stone.itassembly.setting.ui.SettingFragment;
import assembly.stone.itassembly.util.CategoryTabStrip;
import assembly.stone.itassembly.util.ConstantUtils;
import assembly.stone.itassembly.util.NetworkUtil;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnDoubleTouchListenter {
	private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
	private MyPagerAdapter adapter;
	public CategoryTabStrip tabs;
	public ViewPager pager;
	public FrameLayout framelayout, framelayout_center;

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ConstantUtils.ERROR_NETWORK:
				framelayout.setVisibility(View.VISIBLE);
				break;
			case ConstantUtils.SUCC_NETWORK:
				framelayout.setVisibility(View.GONE);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getData();
		initView();
		initData();
		tabs.setOnDoubleTouchListenter(this);
		UmengUpdateAgent.forceUpdate(this);
	}

	private void initData() {
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
	}

	private void initView() {
		tabs = (CategoryTabStrip) findViewById(R.id.tab_strip);
		pager = (ViewPager) findViewById(R.id.view_pager);
		framelayout = (FrameLayout) findViewById(R.id.framelayout_err_connet);
		ReplaceFragmentMethod(R.id.framelayout_err_connet, new ErrorConnetFragment());
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {
		private final List<String> listContainer = new ArrayList<String>();

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			listContainer.add(getString(R.string.baidu_various_text));
			listContainer.add(getString(R.string.title_news_category_sport));
			listContainer.add(getString(R.string.title_news_category_huxiu));
			listContainer.add(getString(R.string.title_news_category_guokr));
			listContainer.add(getString(R.string.title_news_category_animetaster));
			listContainer.add(getString(R.string.title_news_category_setting));
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return listContainer.get(position);
		}

		@Override
		public int getCount() {
			return listContainer.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentsList.get(position);
		}

	}

	public void getData() {
		fragmentsList.add(new VariousFragment());
		fragmentsList.add(new LeiPhoneFragment());
		fragmentsList.add(new HuXiuFragment());
		fragmentsList.add(new GuoKrFragment());
		fragmentsList.add(new AnimetasteFragment());
		fragmentsList.add(new SettingFragment());
	}

	@Override
	public void onDoubleTouch(int position) {
		((BaseHomeFragment) fragmentsList.get(position)).onDoubleTouch();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		ConnectionChangeReceiver.setHandler(handler);
		if (!NetworkUtil.checkNetworkState(this)) {
			framelayout.setVisibility(View.VISIBLE);
		} else {
			framelayout.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 加载初始进入Fragment的方法
	 */
	private void ReplaceFragmentMethod(int viewId, Fragment fragment) {
		FragmentTransaction tration = getSupportFragmentManager().beginTransaction();
		tration.replace(viewId, fragment);
		tration.commitAllowingStateLoss();
	}

}
