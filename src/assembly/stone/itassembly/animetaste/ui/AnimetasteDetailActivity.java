package assembly.stone.itassembly.animetaste.ui;

import java.util.ArrayList;
import java.util.List;

import mipt.media.MediaPlayerHandler;
import mipt.media.MediaPlayerHandler.TYPE;
import mipt.media.MediaStatusCallback;
import mipt.media.SpeedThread;
import mipt.media.SpeedThread.RefreshSpeedListener;
import mipt.media.Utils;

import org.apache.commons.lang.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import assembly.stone.itassembly.R;
import assembly.stone.itassembly.animetaste.adapter.AnimentasteAdapter;
import assembly.stone.itassembly.animetaste.entity.AnimetasteModel;
import assembly.stone.itassembly.base.ui.BaseActivity;
import assembly.stone.itassembly.share.ShareOperating;
import assembly.stone.itassembly.util.ACache;
import assembly.stone.itassembly.util.ConstantUtils;
import assembly.stone.itassembly.util.NetworkUtil;
import assembly.stone.itassembly.util.ToastUtil;
import assembly.stone.itassembly.wedget.AnimeRecomListView;

public class AnimetasteDetailActivity extends BaseActivity implements RefreshSpeedListener, OnSeekBarChangeListener,
		OnGestureListener {
	private AnimetasteModel animetasteModel;
	private ImageView detailPic, volume_img;
	private ImageButton video_btn, arrow_img;
	private MediaPlayerHandler mediaPlayer;
	private RelativeLayout video_load_relat, the_title, volume_lay;
	private LinearLayout seep_relat, share_lay;
	private SurfaceView tvSurfaceView;
	private AnimeRecomListView mListView;
	private List<AnimetasteModel> list = new ArrayList<AnimetasteModel>();
	private AnimentasteAdapter adapter;
	// private TextView postionTv;
	private TextView durationTv, volume_num;
	private final static int HW_POSITION_SHOW = 1;
	private final static int HW_SPEED_SHOW = 2;
	private SeekBar mSeekBar;
	private TextView speed_tx;
	private boolean isPostion = false;
	private int postionTime = 0;
	private int volumeTime = 0;
	private final static int POSTION_TIME = 5;
	private final static int VOLUME_TIME = 3;
	private SpeedThread speedThread;
	private static final long SEEKBARLENGHT = 1000;
	private GestureDetector mGestureDetector;
	private long duration = 0;
	// private boolean isSeekBar = true;
	private long scrollLeng = 0;
	private boolean isScrollX = false;
	private OrientationEventListener mOrientationListener;
	private DisplayMetrics dm = new DisplayMetrics();
	private AudioManager mAudioManager;
	private int maxVolume;
	public ShareOperating shareOperating;
	private LinearLayout sina, wechat_circle, wechat, qq, qzone;
	private ACache mACache;
	private boolean isSeepbar = false;

	@SuppressLint("HandlerLeak")
	protected void sendHandleMessage(Message msg) {
		super.sendHandleMessage(msg);
		switch (msg.what) {
		case HW_POSITION_SHOW:
			refreshShowPosition();
			break;
		case HW_SPEED_SHOW:
			speed_tx.setText(String.valueOf(msg.obj));
			break;
		default:
			break;
		}
	}

	private void startSpeedThread() {
		stopSpeedThread();
		speedThread = new SpeedThread(this);
		speedThread.start();
		speedThread.setRefreshSpeedListener(this);
	}

	private void stopSpeedThread() {
		if (speedThread != null) {
			speedThread.interruptRefresh();
			speedThread = null;
		}
	}

	private void setVideoLayout() {
		postionTime = 0;
		volumeTime = 0;
		isPostion = true;
	}

	private void getVideoLayout() {
		if (mediaPlayer.getPlayer() != null) {
			if (isPostion) {
				++postionTime;
				if (postionTime == POSTION_TIME) {
					setPlay(View.GONE);
					setVideoLayout();
					volume_lay.setVisibility(View.GONE);
				}
			}
		}
		++volumeTime;
		if (volumeTime == VOLUME_TIME) {
			volume_lay.setVisibility(View.GONE);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mGestureDetector = new GestureDetector(this);
		setContentView(R.layout.animetaste_detail);
		super.onCreate(savedInstanceState);
		titleBanner = R.string.title_news_category_animetaster;
		animetasteModel = (AnimetasteModel) getIntent().getExtras().getSerializable(
				ConstantUtils.CONSTANT_ANIMETASTE_MODEL);
		mediaPlayer = MediaPlayerHandler.getInstance();
		getData();
		mediaPlayer.setDisplay(tvSurfaceView);
		Log.d("AnimetasteDetailActivity", "onCreate");
		startListener();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		// 最大音量
		maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		shareOperating = new ShareOperating(this);
		mACache = ACache.get(this);
		mACache.remove(animetasteModel.getName());
	}

	@Override
	protected void setView() {
		shareType();
		findViewById(R.id.sina_tv).setVisibility(View.GONE);
		findViewById(R.id.wechat_circle_tv).setVisibility(View.GONE);
		findViewById(R.id.wechat_tv).setVisibility(View.GONE);
		findViewById(R.id.qq_tv).setVisibility(View.GONE);
		findViewById(R.id.qzone_tv).setVisibility(View.GONE);
		speed_tx = (TextView) findViewById(R.id.speed_tx);
		mSeekBar = (SeekBar) findViewById(R.id.vod_player_seekbar);
		durationTv = (TextView) findViewById(R.id.tv_duration);
		volume_num = (TextView) findViewById(R.id.volume_num);
		tvSurfaceView = (SurfaceView) findViewById(R.id.animet_media);
		seep_relat = (LinearLayout) findViewById(R.id.seep_relat);
		share_lay = (LinearLayout) findViewById(R.id.share_lay);
		share_lay.setVisibility(View.VISIBLE);
		the_title = (RelativeLayout) findViewById(R.id.the_title);
		video_load_relat = (RelativeLayout) findViewById(R.id.video_load_relat);
		detailPic = (ImageView) findViewById(R.id.detailPic);
		volume_lay = (RelativeLayout) findViewById(R.id.volume_lay);
		volume_img = (ImageView) findViewById(R.id.volume_img);
		arrow_img = (ImageButton) findViewById(R.id.arrow_img);
		arrow_img.setOnClickListener(this);
		video_btn = (ImageButton) findViewById(R.id.video_btn);
		video_btn.setOnClickListener(this);
		mListView = (AnimeRecomListView) findViewById(R.id.adlistview);
		adapter = new AnimentasteAdapter(this, list);
		mListView.setAdapter(adapter);
		tvSurfaceView.setOnClickListener(this);
		tvSurfaceView.setOnTouchListener(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					mGestureDetector.onTouchEvent(event);
				} catch (Exception e) {
					// TODO: handle exception
				}
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

	@SuppressLint("NewApi")
	@Override
	protected void getData() {
		mSeekBar.setMax((int) SEEKBARLENGHT);
		mSeekBar.setKeyProgressIncrement(10);
		mSeekBar.setFocusable(false);
		mSeekBar.setOnSeekBarChangeListener(this);
		imageLoader.displayImage(animetasteModel.getDetailPic(), detailPic, options);
		mListView.setDetail(animetasteModel.getName(), animetasteModel.getAuthor() + " · " + animetasteModel.getYear(),
				animetasteModel.getBrief());
		mediaPlayer.setMediaStatusCallback(new MediaStatusCallback.SimpleMediaStatusCallback() {
			@SuppressLint("ShowToast")
			@Override
			public void onError(int what, int extra) {
				mediaPlayer.release();
				stopSpeedThread();
				video_load_relat.setVisibility(View.GONE);
				video_btn.setBackgroundResource(R.drawable.video_pause);
				ToastUtil.show(AnimetasteDetailActivity.this, "视频播放失败");
				setPlay(View.VISIBLE);

				Intent intent = new Intent();
				intent.putExtra(ConstantUtils.CONSTANT_ANIMETASTE_VIDEOURL, animetasteModel.getVideoUrl());
				String title = animetasteModel.getName();
				try {
					title = animetasteModel.getName().split("\\(")[0];
				} catch (Exception e) {
				}
				intent.putExtra(ConstantUtils.CONSTANT_ANIMETASTE_TITLE, title);
				intent.setClass(AnimetasteDetailActivity.this, AnimetasteWebVideoActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onLoading() {
				video_load_relat.setVisibility(View.VISIBLE);
			}

			@Override
			public void onStartPlay() {
				setPlay(View.GONE);
				video_load_relat.setVisibility(View.GONE);
				stopSpeedThread();
				setVideoLayout();
				if (isSeepbar) {
					try {
						isSeepbar = false;
						startSpeedThread();
						mSeekBar.setProgress(Integer.valueOf(mAcache.getAsString(animetasteModel.getName())));
						refreshProgressBar(Integer.valueOf(mAcache.getAsString(animetasteModel.getName())));
						Log.d("mSeekBar.getProgress()", mAcache.getAsString(animetasteModel.getName()));
					} catch (Exception e) {
					}
				}
			}

			@Override
			public void onContinuePlay() {
				video_load_relat.setVisibility(View.GONE);
				stopSpeedThread();
			}

			@Override
			public void onTimeOut() {
				mediaPlayer.release();
				setPlay(View.VISIBLE);
				video_btn.setBackgroundResource(R.drawable.video_pause);
			}

			@Override
			public void onCompletion() {
				mediaPlayer.release();
				handler.removeMessages(HW_POSITION_SHOW);
				handler.removeMessages(HW_SPEED_SHOW);
				stopSpeedThread();
				setPlay(View.VISIBLE);
				detailPic.setVisibility(View.VISIBLE);
				video_load_relat.setVisibility(View.GONE);
				video_btn.setBackgroundResource(R.drawable.video_pause);
			}
		});
	}

	private void setPlay(int visibility) {
		setVideoLayout();
		if (seep_relat.getVisibility() == visibility) {
			return;
		}
		seep_relat.setVisibility(visibility);
		if (visibility == View.VISIBLE) {
			Log.d("visibility", "View.VISIBLE");
			seep_relat.setAnimation(AnimationUtils.loadAnimation(this, R.anim.down_up));
		} else if (visibility == View.GONE) {
			Log.d("visibility", "View.GONE");
			seep_relat.setAnimation(AnimationUtils.loadAnimation(this, R.anim.up_down));
		}
	}

	@Override
	protected boolean fillData(String response) {
		return false;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		textsize_lay.setVisibility(View.GONE);
		textsize_btn.setVisibility(View.GONE);
		startVedio();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// videoPause();
		mACache.put(animetasteModel.getName(), String.valueOf(mSeekBar.getProgress()));
		isSeepbar = true;
		mediaPlayer.release();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isSeepbar = false;
		mACache.remove(animetasteModel.getName());
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void startVedio() {
		if (NetworkUtil.checkNetworkState(this)) {
			if (mediaPlayer.getPlayer() == null) {
				videoStart();
				Log.d("videoStart", "videoStart");
			} else if (mediaPlayer.getPlayer().isPlaying()) {
				videoPause();
				Log.d("videoPause", "videoPause");
			} else if (!mediaPlayer.getPlayer().isPlaying()) {
				videoContinuePlay();
				Log.d("videoContinuePlay", "videoContinuePlay");
			}
		} else {
			ToastUtil.show(this, "网络连接失败");
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.video_btn:
			startVedio();
			break;
		case R.id.arrow_img:
			switch (getResources().getConfiguration().orientation) {
			case 2:// 竖屏
				setOrientationPortrait();
				break;
			case 1:// 横屏
				setOrientationLandscape();
				break;
			default:
				break;
			}
			break;
		case R.id.sina:
			shareOperating.shareToSina(getResources().getString(R.string.share_content),
					getResources().getString(R.string.share_title));
			break;
		case R.id.wechat_circle:
			shareOperating.shareToCircleWithVideo(animetasteModel.getBrief(), animetasteModel.getName(),
					animetasteModel.getVideoUrl(), animetasteModel.getHomePic());
			break;
		case R.id.wechat:
			shareOperating.shareToWeixinWithVideo(animetasteModel.getBrief(), animetasteModel.getName(),
					animetasteModel.getVideoUrl(), animetasteModel.getHomePic());
			break;
		case R.id.qq:
			shareOperating.shareToQQWithVideo(animetasteModel.getBrief(), animetasteModel.getName(),
					animetasteModel.getVideoUrl(), animetasteModel.getHomePic());
			break;
		case R.id.qzone:
			shareOperating.shareToQZoneWithVideo(animetasteModel.getBrief(), animetasteModel.getName(),
					animetasteModel.getVideoUrl(), animetasteModel.getHomePic());
			break;
		default:
			break;
		}
	}

	private void videoPause() {
		try {
			mACache.put(animetasteModel.getName(), String.valueOf(mSeekBar.getProgress()));
			mediaPlayer.getPlayer().pause();
			video_btn.setBackgroundResource(R.drawable.video_pause);
			handler.removeMessages(HW_POSITION_SHOW);
		} catch (Exception e) {
		}
	}

	private void videoStart() {
		try {
			mediaPlayer.setType(TYPE.MIPT);
			if (StringUtils.isNotEmpty(getUrl(animetasteModel.getVideoSource().getHd()))) {
				mediaPlayer.start(getUrl(animetasteModel.getVideoSource().getHd()));
			} else if (StringUtils.isNotEmpty(getUrl(animetasteModel.getVideoSource().getUhd()))) {
				mediaPlayer.start(getUrl(animetasteModel.getVideoSource().getUhd()));
			} else if (StringUtils.isNotEmpty(getUrl(animetasteModel.getVideoSource().getSd()))) {
				mediaPlayer.start(getUrl(animetasteModel.getVideoSource().getSd()));
			}
			Log.d("getVideoSource().getHd()", getUrl(animetasteModel.getVideoSource().getHd()));
			startSpeedThread();
			video_load_relat.setVisibility(View.VISIBLE);
			detailPic.setVisibility(View.GONE);
			refreshShowPosition();
			video_btn.setBackgroundResource(R.drawable.video_start);
		} catch (Exception e) {
			Log.e("Exception", e + "");
		}
	}

	private void videoContinuePlay() {
		try {
			setVideoLayout();
			mediaPlayer.getPlayer().start();
			video_btn.setBackgroundResource(R.drawable.video_start);
			refreshShowPosition();
		} catch (Exception e) {
		}
	}

	private void refreshShowPosition() {
		getVideoLayout();
		handler.removeMessages(HW_POSITION_SHOW);
		Message msg = handler.obtainMessage(HW_POSITION_SHOW);
		handler.sendMessageDelayed(msg, 1000);
		if (mediaPlayer.getPlayer() != null) {
			if (mediaPlayer.getPlayer().isPlaying()) {
				if (duration == 0) {
					duration = mediaPlayer.getPlayer().getDuration();
				}
				long position = mediaPlayer.getPlayer() == null ? 0 : mediaPlayer.getPlayer().getCurrentPosition();
				String positionStr = Utils.formatDuration(getApplicationContext(), (int) position) + "/"
						+ animetasteModel.getDuration();
				durationTv.setText(positionStr);
				Log.i("durationTv", positionStr);
				if (duration != 0) {
					final long progress = (int) (SEEKBARLENGHT * position / duration);
					Log.d("progress", progress + "");
					mSeekBar.setProgress((int) progress);
				}
			}
		}
	}

	@Override
	public void refreshSpeed(String speed) {
		Message msg = handler.obtainMessage(HW_SPEED_SHOW);
		msg.what = HW_SPEED_SHOW;
		msg.obj = speed;
		handler.sendMessage(msg);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if (getResources().getConfiguration().orientation == 2) {
			return;
		}
		if (fromUser) {
			setVideoLayout();
			if (seekBar != null) {
				if (progress <= 0) {
					mSeekBar.setProgress(10);
				} else if (progress >= SEEKBARLENGHT) {
					mSeekBar.setProgress((int) (SEEKBARLENGHT - 10));
				} else {
					mSeekBar.setProgress((int) progress);
				}
				Log.d("seekBar", " " + mSeekBar.getProgress());
				progressChanged();
			}
		}
	}

	private void progressChanged() {
		setVideoLayout();
		video_load_relat.setVisibility(View.VISIBLE);
		handler.removeMessages(HW_POSITION_SHOW);
		refreshProgressBar(mSeekBar.getProgress());
	}

	private synchronized void refreshProgressBar(final int progress) {
		try {
			long current = duration * (long) progress / SEEKBARLENGHT;
			mediaPlayer.getPlayer().seekTo((int) current);
			mediaPlayer.getPlayer().setOnSeekCompleteListener(new OnSeekCompleteListener() {

				@Override
				public void onSeekComplete(MediaPlayer mp) {
					refreshShowPosition();
				}
			});
		} catch (Exception e) {
			// mediaPlayer.release();startVedio();
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	protected void finishActivity() {
		super.finishActivity();
		Log.d("finishActivity", "finishActivity");
		isSeepbar = false;
		mACache.remove(animetasteModel.getName());
		stopSpeedThread();
		handler.removeMessages(HW_POSITION_SHOW);
		handler.removeMessages(HW_SPEED_SHOW);
	}

	private String getUrl(String url) {
		String[] arry = url.split(":");
		if (arry.length < 3) {
			return url;
		} else {
			for (int i = 0; i < arry.length; i++) {
				String str = arry[i];
				if (str.contains("http")) {
					url = "http:" + arry[i + 1].split("\"")[0];
					return url;
				}
			}
		}
		return url;
	}

	@SuppressWarnings("static-access")
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d("onConfigurationChanged", "onConfigurationChanged");
		// 切换为竖屏
		if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_PORTRAIT) {
			Log.i("ORIENTATION_LANDSCAPE", "切换为横屏");
			setPlay(View.VISIBLE);
			mListView.setVisibility(View.VISIBLE);
			the_title.setVisibility(View.VISIBLE);
			final WindowManager.LayoutParams attrs = getWindow().getAttributes();
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setAttributes(attrs);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			share_lay.setVisibility(View.VISIBLE);
		} else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
			Log.i("ORIENTATION_PORTRAIT", "切换为竖屏");
			setPlay(View.VISIBLE);
			mListView.setVisibility(View.GONE);
			the_title.setVisibility(View.GONE);
			// 设置全屏
			getWindow()
					.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			share_lay.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (getResources().getConfiguration().orientation) {
			case 2:// 竖屏
				setPlay(View.VISIBLE);
				setOrientationPortrait();
				break;
			case 1:// 横屏
				finishActivity();
				break;
			default:
				break;
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.i("onDown", "onDown");
		scrollLeng = 0;
		setVideoLayout();
		if (seep_relat.getVisibility() == View.GONE) {
			setPlay(View.VISIBLE);
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Log.i("onLongPress", "onLongPress");
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		try {
			if (isScrollX) {
				Log.d("onFling", mSeekBar.getProgress() + " onFling:" + (e2.getX() - e1.getX()));
				progressChanged();
				isScrollX = false;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		setVideoLayout();
		if (getResources().getConfiguration().orientation == 1) {
			return false;
		}
		float dx = distanceX > 0 ? distanceX : (-distanceX);
		float dy = distanceY > 0 ? distanceY : (-distanceY);
		if (dx > dy) {
			isScrollX = true;
			long progress = (long) (mSeekBar.getProgress() + ((long) e2.getX() - (long) e1.getX() - scrollLeng));
			scrollLeng = (long) (e2.getX() - e1.getX());
			if (progress <= 0) {
				mSeekBar.setProgress(10);
			} else if (progress > SEEKBARLENGHT) {
				mSeekBar.setProgress((int) (SEEKBARLENGHT - 10));
			} else {
				mSeekBar.setProgress((int) progress);
			}
			setVideoLayout();

			long position = mSeekBar.getProgress() * duration / SEEKBARLENGHT;
			String positionStr = Utils.formatDuration(getApplicationContext(), (int) position) + "/"
					+ animetasteModel.getDuration();
			durationTv.setText(positionStr);
		} else {
			// 当前音量
			int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//			int dmW = dm.heightPixels / 3 * 2;
			// if (e1.getX() > dmW) {
			setVolume(currentVolume);
			volume_lay.setVisibility(View.VISIBLE);
			if (e1.getY() > e2.getY()) {
				++currentVolume;
				if (currentVolume <= maxVolume) {
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
					setVolume(currentVolume);
				}
			} else {
				--currentVolume;
				if (currentVolume >= 0) {
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
					setVolume(currentVolume);
				}
				// }
			}
		}
		return false;
	}

	private void setVolume(int currentVolume) {
		if (currentVolume <= 0) {
			volume_img.setBackgroundResource(R.drawable.volume_none);
		} else {
			volume_img.setBackgroundResource(R.drawable.volume_img);
		}
		float fla = ((float) currentVolume) / ((float) maxVolume);
		Log.i("currentVolume", "currentVolume:" + currentVolume + " maxVolume:" + maxVolume + " " + (int) fla * 100);
		String str = (int) (fla * 100) + "%";
		volume_num.setText(str);
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Log.i("onShowPress", "onShowPress");
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.i("onSingleTapUp", "onSingleTapUp");
		refreshShowPosition();
		return false;
	}

	/**
	 * 开启监听器
	 */
	private void startListener() {
		mOrientationListener = new OrientationEventListener(this) {
			@Override
			public void onOrientationChanged(int rotation) {
				// 设置竖屏
				if (((rotation >= 0) && (rotation <= 30)) || (rotation >= 330)) {
					setOrientationPortrait();
				}
				// 设置横屏
				else if (((rotation >= 230) && (rotation <= 310))) {
					setOrientationLandscape();
				}
			}
		};
		mOrientationListener.enable();
	}

	private void setOrientationLandscape() {
		arrow_img.setBackgroundResource(R.drawable.arrow_tip_t_bg);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	private void setOrientationPortrait() {
		arrow_img.setBackgroundResource(R.drawable.arrow_tip_bg);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}