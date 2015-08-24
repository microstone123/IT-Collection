package assembly.stone.itassembly.base.ui;

import mipt.media.MediaPlayerHandler;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.base.manager.RequestManager;
import assembly.stone.itassembly.broadcast.ConnectionChangeReceiver;
import assembly.stone.itassembly.jsouphttp.RequestIdGenFactory;
import assembly.stone.itassembly.jsouphttp.TaskDispatcher;
import assembly.stone.itassembly.util.ACache;
import assembly.stone.itassembly.util.ConstantUtils;
import assembly.stone.itassembly.util.NetworkUtil;

import com.android.volley.Request;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;

@SuppressLint("NewApi")
public abstract class BaseActivity extends FragmentActivity implements OnClickListener {

	private Object tag;
	protected ACache mAcache;
	protected ImageButton back_btn;
	protected TextView secondTitleName;
	protected ProgressBar progress_bar;
	protected String titleName;
	protected ImageButton textsize_btn, textsize_decrease_btn, textsize_add_btn;
	protected LinearLayout textsize_lay;

	protected MediaPlayerHandler mediaPlayer;

	protected abstract void setView();

	protected abstract void getData();

	protected abstract boolean fillData(String response);

	protected int titleBanner;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader;
	protected static final int GAIN_START_PIC_URL_TASK_ID = RequestIdGenFactory.gen();
	public TaskDispatcher taskDispatcher;

	public FrameLayout framelayout, framelayout_center;

	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			sendHandleMessage(msg);
		}
	};

	protected void sendHandleMessage(Message msg) {
		switch (msg.what) {
		case ConstantUtils.ERROR_NETWORK:
			framelayout.setVisibility(View.VISIBLE);
			break;
		case ConstantUtils.SUCC_NETWORK:
			framelayout.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mediaPlayer = MediaPlayerHandler.getInstance();
		mAcache = ACache.get(this);
		taskDispatcher = TaskDispatcher.getInstance();
		setImageLoader();
		setView();
	}

	public void setImageLoader() {
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.color.radio_cor)
				.showImageForEmptyUri(R.color.radio_cor).showImageOnFail(R.color.radio_cor).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(0)).build();
	}

	@Override
	protected void onStart() {
		super.onStart();
		framelayout = (FrameLayout) findViewById(R.id.framelayout_err_connet);
		ReplaceFragmentMethod(R.id.framelayout_err_connet, new ErrorConnetFragment());
		textsize_lay = (LinearLayout) findViewById(R.id.textsize_lay);
		textsize_btn = (ImageButton) findViewById(R.id.textsize_btn);
		textsize_btn.setOnClickListener(this);
		back_btn = (ImageButton) findViewById(R.id.back_btn);
		back_btn.setOnClickListener(this);
		secondTitleName = (TextView) findViewById(R.id.secondTitleName);
		secondTitleName.setText(titleBanner);
	}

	public void setTitle(String title) {
		secondTitleName.setText(title);
	}

	protected void executeRequest(Object tag, Request<?> request) {
		this.tag = tag;
		RequestManager.addRequest(tag, request);
		Log.d("request.getUrl()", request.getUrl());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaPlayer.release();
		RequestManager.cancelAll(tag);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClass().getSimpleName()); // 统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
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
		MobclickAgent.onPageEnd(getClass().getSimpleName());
		MobclickAgent.onPause(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_btn:
			finishActivity();
			break;
		case R.id.textsize_btn:
			if (textsize_lay.getVisibility() == View.VISIBLE) {
				textsize_lay.setVisibility(View.GONE);
				textsize_lay.setAnimation(AnimationUtils.loadAnimation(this, R.anim.right_left));
			} else if (textsize_lay.getVisibility() == View.GONE) {
				textsize_lay.setVisibility(View.VISIBLE);
				textsize_lay.setAnimation(AnimationUtils.loadAnimation(this, R.anim.left_right));
			}
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishActivity();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void finishActivity() {
		mediaPlayer.release();
		Log.d("mediaPlayer", "stop");
		finish();
		overridePendingTransition(R.anim.push_rigt_out, R.anim.push_right_in);
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
