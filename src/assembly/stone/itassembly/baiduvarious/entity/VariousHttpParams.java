package assembly.stone.itassembly.baiduvarious.entity;

import java.io.Serializable;

//百度百家 page=4&pagesize=10&labelid=2&prevarticalid=41378
public class VariousHttpParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;

	private int page;
	private int pagesize;
	private int labelid = 2;
	private int prevarticalid;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getLabelid() {
		return labelid;
	}

	public void setLabelid(int labelid) {
		this.labelid = labelid;
	}

	public int getPrevarticalid() {
		return prevarticalid;
	}

	public void setPrevarticalid(int prevarticalid) {
		this.prevarticalid = prevarticalid;
	}

}
