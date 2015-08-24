package assembly.stone.itassembly.jsouphttp;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupRequest {

	private static int timeout = 5 * 1000;

	public static Document get(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla") // 请求参数
					.cookie("auth", "token") // 设置 cookie
					.timeout(timeout) // 设置连接超时时间
					.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

	public static Document post(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla") // 请求参数
					.cookie("auth", "token") // 设置 cookie
					.timeout(timeout) // 设置连接超时时间
					.post();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}

}
