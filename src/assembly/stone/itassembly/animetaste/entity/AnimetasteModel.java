package assembly.stone.itassembly.animetaste.entity;

import java.io.Serializable;

public class AnimetasteModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String Name;
	private String Duration;
	private String VideoUrl;
	private VideoSource VideoSource;
	private String Author;
	private String Year;
	private String Brief;
	private String HomePic;
	private String DetailPic;
	private String UpdatedTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}

	public String getVideoUrl() {
		return VideoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		VideoUrl = videoUrl;
	}

	public VideoSource getVideoSource() {
		return VideoSource;
	}

	public void setVideoSource(VideoSource videoSource) {
		VideoSource = videoSource;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getBrief() {
		return Brief;
	}

	public void setBrief(String brief) {
		Brief = brief;
	}

	public String getHomePic() {
		return HomePic;
	}

	public void setHomePic(String homePic) {
		HomePic = homePic;
	}

	public String getDetailPic() {
		return DetailPic;
	}

	public void setDetailPic(String detailPic) {
		DetailPic = detailPic;
	}

	public String getUpdatedTime() {
		return UpdatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		UpdatedTime = updatedTime;
	}

	public class VideoSource implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;
		private String uhd;
		private String hd;
		private String sd;

		public String getUhd() {
			return uhd;
		}

		public void setUhd(String uhd) {
			this.uhd = uhd;
		}

		public String getHd() {
			return hd;
		}

		public void setHd(String hd) {
			this.hd = hd;
		}

		public String getSd() {
			return sd;
		}

		public void setSd(String sd) {
			this.sd = sd;
		}
	}

}
