package assembly.stone.itassembly.baiduvarious.entity;

import java.io.Serializable;

public class VariousHeadModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;
	private String title;
	private String src;
	private String href;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
