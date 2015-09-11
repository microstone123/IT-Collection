package assembly.stone.itassembly.guokr.entity;

import java.util.List;

public class GuoKrResult {

	private String now;
	private boolean ok;
	private List<GuoKrModel> result;

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public List<GuoKrModel> getResult() {
		return result;
	}

	public void setResult(List<GuoKrModel> result) {
		this.result = result;
	}

}
