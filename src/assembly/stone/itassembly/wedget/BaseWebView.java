package assembly.stone.itassembly.wedget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import assembly.stone.itassembly.base.ui.ImagePagerActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("SetJavaScriptEnabled")
public class BaseWebView extends WebView {

	private WebSettings wSet;
	private OnWebProgressChanged onWebProgressChanged;
	private Context context;
	public String[] urls;
	public int TEXTZOOM = 100;

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public BaseWebView(Context context) {
		super(context);
		wSet = getSettings();
		setWebSet();
		this.context = context;
	}

	public BaseWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOverScrollMode(View.OVER_SCROLL_NEVER);
		wSet = getSettings();
		setWebSet();
		this.context = context;
	}

	@SuppressLint("NewApi")
	public void setTextZoom(int textZoom) {
		wSet.setTextZoom(textZoom);
	}

	@SuppressLint("NewApi")
	public void addTextZoom() {
		wSet.setTextZoom(wSet.getTextZoom() + 10);
	}

	@SuppressLint("NewApi")
	public void decreaseTextZoom() {
		wSet.setTextZoom(wSet.getTextZoom() - 10);
	}

	@SuppressLint("NewApi")
	private void setWebSet() {
		wSet.setSupportZoom(true);
		wSet.setBuiltInZoomControls(true);
		wSet.setJavaScriptEnabled(true);
		wSet.setDisplayZoomControls(false);
		wSet.setBlockNetworkImage(false);
		wSet.setLoadWithOverviewMode(true);
		wSet.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		setTextZoom(TEXTZOOM);
		wSet.setDefaultTextEncodingName("utf-8");
		wSet.setJavaScriptCanOpenWindowsAutomatically(true);
		wSet.setAllowFileAccess(true);
		wSet.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		setWebViewClient(new WebViewClientMonitor());
		addJavascriptInterface(new JavascriptInterface(context), "imagelistner");
	}

	public void setLoadDataWithBaseURL(String urlStr) {
		loadDataWithBaseURL(null, urlStr, "text/html", "utf-8", null);
		setWebChromeClient(new WebChoClient());
	}

	public void setLoadUrl(String urlStr) {
		loadUrl(urlStr);
		setWebChromeClient(new WebChoClient());
	}

	private class WebChoClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			onWebProgressChanged.webProgressChanged(newProgress);
			super.onProgressChanged(view, newProgress);
		}
	}

	// js通信接口
	public class JavascriptInterface {

		// private Context context;

		public JavascriptInterface(Context context) {
			// this.context = context;
		}

		public void openImage(String img) {
			img = getimgId(img);
			for (int i = 0; i < urls.length; i++) {
				if (urls[i].contains(img)) {
					imageBrower(i, urls);
					return;
				}
			}
		}
	}

	private String getimgId(String img) {
		String[] imgArray = img.split("/");
		return imgArray[imgArray.length - 1];
	}

	// 监听
	private class WebViewClientMonitor extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			return super.shouldOverrideUrlLoading(view, url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {

			view.getSettings().setJavaScriptEnabled(true);

			super.onPageFinished(view, url);
			// html加载完成之后，添加监听图片的点击js函数
			addImageClickListner();

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			view.getSettings().setJavaScriptEnabled(true);

			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

			super.onReceivedError(view, errorCode, description, failingUrl);

		}
	}

	// 注入js函数监听
	private void addImageClickListner() {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
		loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); "
				+ "for(var i=0;i<objs.length;i++) " + "{" + " objs[i].onclick=function() " + " { "
				+ " window.imagelistner.openImage(this.src); " + " } " + "}" + "})()");
	}

	public OnWebProgressChanged getOnWebProgressChanged() {
		return onWebProgressChanged;
	}

	public void setOnWebProgressChanged(OnWebProgressChanged onWebProgressChanged) {
		this.onWebProgressChanged = onWebProgressChanged;
	}

	public interface OnWebProgressChanged {
		void webProgressChanged(int newProgress);
	}

	/**
	 * 将图片URL数组传到多图片显示界面
	 * 
	 * @param position
	 *            当前图片所在数组中的下标
	 * @param urls
	 *            图片URL数组
	 */
	private void imageBrower(int position, String[] urls) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}
}
