package assembly.stone.itassembly.jsouphttp;

import java.util.HashMap;

import org.jsoup.nodes.Document;

import android.content.Context;
import android.util.Log;

public abstract class BaseRequest {

	protected Requestmethod requestmethod;
	protected String url;
	protected Context context;
	protected TaskDispatcher dispatcher;
	public BaseResponse result;
	protected int retryTimes;

	public BaseRequest(BaseResponse baseResponse) {
		this.result = baseResponse;
		result.setUrl(url);
		retryTimes = HttpUtils.DEF_HTTP_RETRY_TIMES;
	}

	public BaseRequest(Context context, BaseResponse baseResponse) {
		this.context = context;
		this.result = baseResponse;
		result.setUrl(url);
		retryTimes = HttpUtils.DEF_HTTP_RETRY_TIMES;
	}

	protected enum Requestmethod {
		GET, POST;
	}

	public boolean isAlive() {
		return this.result.parseNeeded;
	}

	public void cancel() {
		this.result.parseNeeded = false;
	}

	public Boolean send() {
		Document doc = null;
		for (int i = 0; isAlive() && doc == null && i < retryTimes; i++) {
			try {
				result.setUrl(getUrl());
				Log.i("getUrl()", getUrl());
				switch (getMethod()) {
				case GET:
					doc = JsoupRequest.get(getUrl());
					break;
				case POST:
					doc = JsoupRequest.post(getUrl());
					break;
				default:
					break;
				}
			} catch (Exception e) {
				continue;
			}
			if (doc == null) {
				continue;
			}
		}
		if (doc == null) {
			cancel();
		}
		result.getDoubleData(doc);
		return true;
	}

	public void setRequestmethod(Requestmethod requestmethod) {
		this.requestmethod = requestmethod;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDispatcher(TaskDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	protected abstract HashMap<String, String> appendUrlSegment();

	protected abstract Requestmethod getMethod();

	protected abstract String getUrl();
}
