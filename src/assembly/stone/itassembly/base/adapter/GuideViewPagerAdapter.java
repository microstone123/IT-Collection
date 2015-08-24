package assembly.stone.itassembly.base.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.ui.MainActivity;
import assembly.stone.itassembly.util.ConstantUtils;

/**
 * 第一次使用指引适配器
 * 
 * @author: linhs
 * @date: 2014-7-15 下午7:25:53
 * @Description: TODO(用一句话描述该文件做什么)
 */
public class GuideViewPagerAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;
	private Activity activity;

	public GuideViewPagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}

	// 销毁view位置的界面
	@Override
	public void destroyItem(View view, int index, Object arg2) {
		((ViewPager) view).removeView(views.get(index));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	// 获得当前界面数
	@Override
	public int getCount() {
		if (views != null)
			return views.size();
		return 0;
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	// 初始化view位置的界面
	@Override
	public Object instantiateItem(View view, int index) {
		((ViewPager) view).addView(views.get(index), 0);
		if (index == views.size() - 1) {
			Button startBtn = (Button) view.findViewById(R.id.startBtn);
			startBtn = (Button) view.findViewById(R.id.startBtn);
			startBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					// 设置已经引导
					SharedPreferences sp = activity.getSharedPreferences("CreateShortcut", 0);
					sp.edit().putBoolean(ConstantUtils.EXTRA_START_FIRST, false).commit();

					// 进入主页面
					Intent intent = new Intent();
					intent.setClass(activity, MainActivity.class);
					intent.putExtra("isPic", false);
					activity.startActivity(intent);
//					activity.overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
					activity.finish();
				}

			});
		}
		return views.get(index);
	}
}
