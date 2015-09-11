package assembly.stone.itassembly.baiduvarious.request;

import java.util.HashMap;

import assembly.stone.itassembly.jsouphttp.BaseRequest;
import assembly.stone.itassembly.jsouphttp.BaseResponse;

public class VariousRequest extends BaseRequest{
	
	private String url;
	
	public VariousRequest(String url,BaseResponse baseResponse) {
		super(baseResponse);
		this.url = url;
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
		return url;
	}

}
