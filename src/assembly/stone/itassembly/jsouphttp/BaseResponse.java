package assembly.stone.itassembly.jsouphttp;

import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import assembly.stone.itassembly.util.ACache;

public abstract class BaseResponse {

	protected ACache mAcache;

	protected int statusCode;
	protected String msg;
	public boolean parseNeeded;
	protected Context context;
	protected String url;
	public boolean isPresence = false;

	public BaseResponse(Context context) {
		this.context = context;
		mAcache = ACache.get(context);
		this.parseNeeded = true;
	}

	void setUrl(String url) {
		this.url = url;
	}

	protected boolean isParseNeeded() {
		return parseNeeded;
	}

	public void setParseNeeded(boolean parseNeeded) {
		this.parseNeeded = parseNeeded;
	}

	protected void extractCode(XmlPullParser parser) throws Exception {
		statusCode = Integer.valueOf(parser.nextText());
	}

	protected void extractMsg(XmlPullParser parser) throws Exception {
		msg = parser.nextText();
	}

	protected void doExtraJob() {
		// nothing to do as default
	}

	public abstract void getDoubleData(Document doc);

	protected void checkPresence(String divStr, String url) throws Exception {
	}
}
