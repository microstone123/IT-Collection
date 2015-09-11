package assembly.stone.itassembly.leiphone.entity;

import java.util.List;

public class LeiphoneContent {

	private String category_name;
	private Integer pages;
	private Integer curPage;
	private List<LeiphoneModel> artlist;

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public List<LeiphoneModel> getArtlist() {
		return artlist;
	}

	public void setArtlist(List<LeiphoneModel> artlist) {
		this.artlist = artlist;
	}
}
