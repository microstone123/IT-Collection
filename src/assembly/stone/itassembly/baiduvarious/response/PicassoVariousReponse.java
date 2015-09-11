package assembly.stone.itassembly.baiduvarious.response;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import assembly.stone.itassembly.baiduvarious.entity.VariousHeadModel;
import assembly.stone.itassembly.jsouphttp.BaseResponse;

public class PicassoVariousReponse extends BaseResponse {

	public String webStr;
	public List<VariousHeadModel> picTurnsInfoList;

	public PicassoVariousReponse(Context context) {
		super(context);
	}

	@Override
	public void getDoubleData(Document doc) {
		getDetailData(doc);
	}

	private void getDetailData(Document doc) {
		try {
			picTurnsInfoList = new ArrayList<VariousHeadModel>();
//			Loger.d("Document", doc.toString());
			Element singerListDiv = doc.getElementsByAttributeValue("class", "thumbs").first();
			Elements links = singerListDiv.getElementsByAttributeValue("class", "thumb");
			for (Element link : links) {
				VariousHeadModel variousHeadModel = new VariousHeadModel();
				Elements title = link.getElementsByTag("a");
				variousHeadModel.setHref(title.attr("href"));
				Elements titlep = link.getElementsByTag("p");
				variousHeadModel.setTitle(titlep.text());
				Elements img = link.getElementsByTag("img");
				variousHeadModel.setSrc(img.attr("src"));
				picTurnsInfoList.add(variousHeadModel);
			}
		} catch (Exception e) {
		}
	}

}
