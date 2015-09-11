package assembly.stone.itassembly.animetaste.entity;

import java.util.List;

public class AnimetasteResult {

	private Animetaste data;

	public Animetaste getData() {
		return data;
	}

	public void setData(Animetaste data) {
		this.data = data;
	}

	public class Animetaste {
		private boolean result;
		private AnimeInfo list;

		public boolean getResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
		}

		public AnimeInfo getList() {
			return list;
		}

		public void setList(AnimeInfo list) {
			this.list = list;
		}

	}

	public class AnimeInfo {
		List<AnimetasteModel> anime = null;

		public List<AnimetasteModel> getAnime() {
			return anime;
		}

		public void setAnime(List<AnimetasteModel> anime) {
			this.anime = anime;
		}

	}
}
