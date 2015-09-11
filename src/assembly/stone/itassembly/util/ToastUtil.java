/**
 * 
 */
package assembly.stone.itassembly.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author: linhs 
 * @date: 2014-7-16 下午1:42:17 	
 * @Description: TODO(Toast工具类)
 */
public class ToastUtil {

	public static void show(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

	public static void show(Context context, int info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}
}
