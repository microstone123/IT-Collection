package assembly.stone.itassembly.baiduvarious.entity;

import java.io.Serializable;
import java.util.List;

public class VariousResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer errno;

	private DataModel data;

	public Integer getErrno() {
		return errno;
	}

	public void setErrno(Integer errno) {
		this.errno = errno;
	}

	public DataModel getData() {
		return data;
	}

	public void setData(DataModel data) {
		this.data = data;
	}

	public class DataModel implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Integer total;
		private List<VariousModel> list;

		public Integer getTotal() {
			return total;
		}

		public void setTotal(Integer total) {
			this.total = total;
		}

		public List<VariousModel> getList() {
			return list;
		}

		public void setList(List<VariousModel> list) {
			this.list = list;
		}

	}
}
