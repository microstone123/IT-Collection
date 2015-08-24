package assembly.stone.itassembly.baiduvarious.request;

import java.util.HashMap;

import assembly.stone.itassembly.jsouphttp.BaseRequest;
import assembly.stone.itassembly.jsouphttp.BaseResponse;
import assembly.stone.itassembly.util.HttpContacts;

public class PicassoVariousRequest extends BaseRequest {

	public PicassoVariousRequest(BaseResponse baseResponse) {
		super(baseResponse);
	}

	@Override
	protected HashMap<String, String> appendUrlSegment() {
		return null;
	}

	@Override
	protected Requestmethod getMethod() {
		return Requestmethod.GET;
	}

	@Override
	protected String getUrl() {
		return HttpContacts.EXTEXT_BAIDU_VARIOUS;
	}

}
