package assembly.stone.itassembly.base.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * @author zengzt
 * @date: Jul 30, 2014 3:31:36 PM
 * @Description: TODO(新闻详情图片游览Viewpager控件)
 */
public class HackyViewPager extends ViewPager {

	public static final String TAG = "HackyViewPager";

	public HackyViewPager(Context context) {
		super(context);
	}

	public HackyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			// 不理会
//			Log.e(TAG, "hacky viewpager error1");
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			// 不理会
//			Log.e(TAG, "hacky viewpager error2");
			return false;
		}
	}

}
