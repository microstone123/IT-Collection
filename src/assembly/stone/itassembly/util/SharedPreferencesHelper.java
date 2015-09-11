package assembly.stone.itassembly.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author: linhs 
 * @date: 2014-7-10 下午2:37:52 	
 * @Description: TODO(SharedPreferences工具类)
 */
public class SharedPreferencesHelper {
	SharedPreferences sp;
	SharedPreferences.Editor editor;

	Context context;

	@SuppressLint("CommitPrefEdits")
	public SharedPreferencesHelper(Context c, String name) {
		context = c;
		sp = context.getSharedPreferences(name, 0);
		editor = sp.edit();
	}

	public void putIntValue(String key, int value) {
		editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
		// Common.month_total = value;
	}

	public void putStrValue(String key, String value) {
		editor = sp.edit();
//		editor.remove(key);
		editor.putString(key, value);
		editor.commit();
	}
	
	public void removeKey(String key){
		editor.clear();
		editor.commit();
	}

	public String getValue(String key) {
		String value = sp.getString(key, "");
		// Common.month_total = value;
		return value;
	}
	
	public void putUserValue(String nameKey, String nameValue,String passWKey, String passWValue) {
		editor = sp.edit();
		editor.putString(nameKey, nameValue);
		editor.putString(passWKey, passWValue);
		editor.commit();
	}
}
