package assembly.stone.itassembly.huxiu.entity;

import java.util.List;

public class HuXiuResult {
	
	private Integer catid;
	private Integer page;
	private Integer result;
	private List<HuXiuModel> content;
	private String img;
	public Integer getCatid() {
		return catid;
	}
	public void setCatid(Integer catid) {
		this.catid = catid;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public List<HuXiuModel> getContent() {
		return content;
	}

	public void setContent(List<HuXiuModel> content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

}
