package assembly.stone.itassembly.base.ui;

import android.app.Application;
import assembly.stone.itassembly.base.manager.RequestManager;

/**
 * Created by Administrator on 2014/12/15.
 */
public class ITApplication extends Application {
	public static ITApplication sInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		init();

	}

	private void init() {
		RequestManager.init(this);
	}

}
