package assembly.stone.itassembly.baiduvarious.response;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.util.Log;
import assembly.stone.itassembly.jsouphttp.BaseResponse;

public class VariousReponse extends BaseResponse {

	public String webStr;
	public String[] imgUrls;

	public VariousReponse(Context context) {
		super(context);
	}

	@Override
	public void getDoubleData(Document doc) {
		getDetailData(doc);
	}

	private void getDetailData(Document doc) {
		// Log.e("Document", doc.toString());
		try {
			Elements ele_Img = doc.getElementsByTag("img");
			if (ele_Img.size() != 0) {
				for (Element e_Img : ele_Img) {
					e_Img.attr("style", "width:100%");
				}
			}
			Element singerListDiv = doc.getElementsByAttributeValue("class", "article-detail").first();
			singerListDiv.select("div.l-main-inner-ad").remove();
			webStr = singerListDiv.toString();
			webStr = webStr.replace("<h2", "<h2 style=\"margin:5px\"");
			webStr = webStr.replace("h2", "p");
			webStr = webStr.replace("<h3", "<h3 style=\"font-size:10px;color:green;margin:5px\"");
			webStr = webStr.replace("h3", "p");
			webStr = webStr.replace("data-originalurl=", "src=");
			webStr = webStr.replace("百度新闻", "");
			webStr = webStr.replace("查看原图", "");
			Elements elements = singerListDiv.getElementsByAttributeValue("class", "lazy-load");
			imgUrls = new String[elements.size()];
			for (int i = 0; i < elements.size(); i++) {
				imgUrls[i] = elements.get(i).attr("data-url");
			}
//			Log.e("webStr", webStr);
		} catch (Exception e) {
			Log.d("Exception", ";;");
		}
	}

}
