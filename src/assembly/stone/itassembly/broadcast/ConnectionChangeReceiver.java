package assembly.stone.itassembly.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import assembly.stone.itassembly.util.ConstantUtils;

/**
 * @ClassName: ConnectionChangeReceiver
 * @Description: TODO(网络连接广播)
 * @author linhs
 * @date 2014-2-18 下午2:55:34
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
	private static Handler handler = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		android.os.Message msg = new android.os.Message();
		if (activeNetInfo != null) {
			msg.what = ConstantUtils.SUCC_NETWORK; // 发送网络连接消息 1001

		} else {
			msg.what = ConstantUtils.ERROR_NETWORK; // 发送网络断开消息 1002

		}
		if (handler != null) {
			handler.sendMessage(msg);
		}
	}

	public static Handler getHandler() {
		return handler;
	}

	public static void setHandler(Handler handler) {
		ConnectionChangeReceiver.handler = handler;
	}

}