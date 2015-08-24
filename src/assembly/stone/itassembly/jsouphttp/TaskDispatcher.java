package assembly.stone.itassembly.jsouphttp;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;

public class TaskDispatcher {
	static final Handler HANDLER = new Handler(Looper.getMainLooper());
	private SparseArray<HttpTask> taskMap;
	private static TaskDispatcher INSTANCE = null;

	private TaskDispatcher() {
		taskMap = new SparseArray<HttpTask>();
	}

	public static TaskDispatcher getInstance() {
		if (INSTANCE == null) {
			synchronized (TaskDispatcher.class) {
				if (INSTANCE == null) {
					INSTANCE = new TaskDispatcher();
				}
			}
		}
		return INSTANCE;
	}

	public void execute(HttpTask task) {
		synchronized (taskMap) {
			task.setDispatcher(this);
			taskMap.put(task.id, task);
		}
		new Thread(task).start();
	}

	protected void remove(int id) {
		synchronized (taskMap) {
			taskMap.remove(id);
		}
	}

	protected HttpTask get(int id) {
		HttpTask tsk = null;
		synchronized (taskMap) {
			tsk = taskMap.get(id);
		}
		return tsk;
	}
}
