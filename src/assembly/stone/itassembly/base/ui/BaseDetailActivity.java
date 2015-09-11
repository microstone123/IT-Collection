package assembly.stone.itassembly.base.ui;

import org.apache.commons.lang.StringUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.share.ShareOperating;
import assembly.stone.itassembly.util.ToastUtil;
import assembly.stone.itassembly.wedget.BaseWebView;
import assembly.stone.itassembly.wedget.BaseWebView.OnWebProgressChanged;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public abstract class BaseDetailActivity extends BaseActivity implements OnWebProgressChanged, OnGestureListener {

	protected BaseWebView wView;
	protected Integer tid;
	protected String url;
	protected String tag;
	protected RelativeLayout the_title;
	protected Button load_again_btn;
	protected LinearLayout load_err_layout;
	private GestureDetector mGestureDetector;
	private static final int SWIPE_MIN_DISTANCE = 200;
	protected ImageButton textsize_decrease_btn, textsize_add_btn;
	protected LinearLayout share_lay;
	protected String baseDetailUrl;
	protected LinearLayout sina, wechat_circle, wechat, qq, qzone;
	protected ShareOperating shareOperating;
	protected String shareContent, shareTitle, shareUrl, shareimgUrl = null;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mGestureDetector = new GestureDetector(this);
		setContentView(R.layout.various_detail);
		tid = getIntent().getExtras().getInt("detail_tid");
		url = url + tid;
		super.onCreate(savedInstanceState);
		baseDetailUrl = url;
		getData();
		shareOperating = new ShareOperating(this);
	}

	/*
	 * @SuppressLint("ClickableViewAccessibility")
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) {
	 * mGestureDetector.onTouchEvent(event); return true; }
	 */

	@Override
	public void webProgressChanged(int newProgress) {
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void setView() {
		shareType();
		findViewById(R.id.sina_tv).setVisibility(View.GONE);
		findViewById(R.id.wechat_circle_tv).setVisibility(View.GONE);
		findViewById(R.id.wechat_tv).setVisibility(View.GONE);
		findViewById(R.id.qq_tv).setVisibility(View.GONE);
		findViewById(R.id.qzone_tv).setVisibility(View.GONE);
		share_lay = (LinearLayout) findViewById(R.id.share_lay);
		textsize_decrease_btn = (ImageButton) findViewById(R.id.textsize_decrease_btn);
		textsize_decrease_btn.setOnClickListener(this);
		textsize_add_btn = (ImageButton) findViewById(R.id.textsize_add_btn);
		textsize_add_btn.setOnClickListener(this);
		the_title = (RelativeLayout) findViewById(R.id.the_title);
		load_err_layout = (LinearLayout) findViewById(R.id.load_err_layout);
		load_again_btn = (Button) findViewById(R.id.load_again_btn);
		progress_bar = (ProgressBar) findViewById(R.id.loading_progress);
		wView = (BaseWebView) findViewById(R.id.pic_web);
		wView.setOnWebProgressChanged(this);
		load_again_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				getData();
			}
		});

		wView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				return false;
			}
		});

	}

	public void shareType() {
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
	protected void getData() {
		progress_bar.setVisibility(View.VISIBLE);
		executeRequest(tag, new StringRequest(Method.GET, url, responseListener(), errorListener()));
	}

	protected Response.Listener<String> responseListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				share_lay.setVisibility(View.VISIBLE);
				share_lay.startAnimation(AnimationUtils.loadAnimation(BaseDetailActivity.this, R.anim.down_up));
				setLoadErr(response);
			}
		};
	}

	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (StringUtils.isNotEmpty(mAcache.getAsString(baseDetailUrl))) {
					setLoadErr(mAcache.getAsString(baseDetailUrl));
				} else {
					progress_bar.setVisibility(View.GONE);
					load_err_layout.setVisibility(View.VISIBLE);
					share_lay.setVisibility(View.GONE);
				}
			}
		};
	}

	protected void setLoadErr(String response) {
		Log.i("response", response + "");
		if (StringUtils.isNotEmpty(response)) {
			mAcache.remove(baseDetailUrl);
			mAcache.put(baseDetailUrl, response);
		}
		if (!fillData(response)) {
			share_lay.setVisibility(View.GONE);
			load_err_layout.setVisibility(View.VISIBLE);
		} else {
			load_err_layout.setVisibility(View.GONE);
		}
		progress_bar.setVisibility(View.GONE);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
			finishActivity();
		}
		return false;
	}

	@Override
	protected boolean fillData(String response) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.textsize_decrease_btn:
			wView.decreaseTextZoom();
			ToastUtil.show(this, wView.getSettings().getTextZoom() + "%");
			break;
		case R.id.textsize_add_btn:
			wView.addTextZoom();
			ToastUtil.show(this, wView.getSettings().getTextZoom() + "%");
			break;
		case R.id.sina:
			shareOperating.shareToSina(getResources().getString(R.string.share_content),
					getResources().getString(R.string.share_title));
			break;
		case R.id.wechat_circle:
			shareOperating.shareToCircle(shareContent, shareTitle, shareUrl);
			break;
		case R.id.wechat:
			shareOperating.shareToWeixin(shareContent, shareTitle, shareUrl);
			break;
		case R.id.qq:
			shareOperating.shareToQQ(shareContent, shareTitle, shareUrl);
			break;
		case R.id.qzone:
			shareOperating.shareToQZone(shareContent, shareTitle, shareUrl);
			break;
		default:
			break;
		}
	}

	public static class SimpleBaseActivity extends BaseActivity {

		@Override
		protected void setView() {
			// TODO Auto-generated method stub

		}

		@Override
		protected void getData() {
			// TODO Auto-generated method stub

		}

		@Override
		protected boolean fillData(String response) {
			// TODO Auto-generated method stub
			return false;
		}

	}
}