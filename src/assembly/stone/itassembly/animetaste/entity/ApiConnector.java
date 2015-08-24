package assembly.stone.itassembly.animetaste.entity;

import android.annotation.SuppressLint;

import java.util.Iterator;
import java.util.TreeMap;

@SuppressLint("DefaultLocale")
@SuppressWarnings("unused")
public class ApiConnector {
	private static final String ANIMATION_DETAIL_URL = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&vid=%d&access_token=%s";
	private static final String ANIMATION_RANDOM_URL = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&order=random&limit=%d&access_token=%s";
	private static final String ANIMATION_REQUEST_URL = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%s&page=%d&access_token=%s";
	private static final String API_KEY = "android";
	private static final String API_SECRET = "7763079ba6abf342a99ab5a1dfa87ba8";
	private static final String CATEGORY_REQUEST_URL = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&page=%d&category=%d&limit=%d&access_token=%s";
	private static final String INIT_REQUEST_URL = "http://i.animetaste.net/api/setup/?api_key=%s&timestamp=%s&anime=%d&feature=%d&advert=%d&access_token=%s";
	private static final String RECOMMEND_ALL_REQUEST = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&feature=1&limit=%d&access_token=%s";
	private static final String RECOMMEND_CATEGORY_REQUEST = "http://i.animetaste.net/api/animelist_v4/?api_key=%s&timestamp=%d&category=%d&feature=1&limit=%d&access_token=%s";
	private final static int limit = 10;

	@SuppressWarnings("unchecked")
	public static String getCategory(int category, int page) {
		long l = System.currentTimeMillis() / 1000L;
		@SuppressWarnings("rawtypes")
		TreeMap localTreeMap = new TreeMap();
		localTreeMap.put("api_key", API_KEY);
		localTreeMap.put("timestamp", String.valueOf(l));
		localTreeMap.put("page", String.valueOf(page));
		localTreeMap.put("category", String.valueOf(category));
		localTreeMap.put("limit", String.valueOf(limit));
		String str = getAccessToken(localTreeMap, "7763079ba6abf342a99ab5a1dfa87ba8");
		Object[] arrayOfObject = new Object[6];
		arrayOfObject[0] = "android";
		arrayOfObject[1] = Long.valueOf(l);
		arrayOfObject[2] = Integer.valueOf(page);
		arrayOfObject[3] = Integer.valueOf(category);
		arrayOfObject[4] = Integer.valueOf(limit);
		arrayOfObject[5] = str;
		return String.format(CATEGORY_REQUEST_URL, arrayOfObject);
	}

	public static String getAccessToken(TreeMap<String, String> paramTreeMap, String paramString) {
		String str1 = "";
		Iterator<String> localIterator = paramTreeMap.keySet().iterator();
		while (localIterator.hasNext()) {
			String str3 = localIterator.next();
			str1 = str1 + str3 + "=" + paramTreeMap.get(str3) + "&";
		}
		String str2 = str1.substring(0, -1 + str1.length());
		return MD5.digest(str2 + paramString);
	}
}