package assembly.stone.itassembly.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
@SuppressLint("NewApi")
public class Loger {

	private static String TAG = "CODEC";
	private static File logFile = new File(Environment.getExternalStorageDirectory().getPath()
			+ "/APPSHARE_IT_it.txt");
	private static String fmt = "[%s] ";
	private static String df = "yyyy-MM-dd kk:mm:ss";
	private static FileWriter fw = null;
	private static BufferedWriter bw = null;
	private static StringWriter sw = null;
	private static PrintWriter pw = null;
	public static boolean log = true;
	public static boolean write = true;

	public Loger() {

	}

	static {
		setFile();
	}

	public static void setFile() {
		try {
			if (write) {
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				if (fw == null) {
					fw = new FileWriter(logFile, true);
				}
				if (bw == null) {
					bw = new BufferedWriter(fw);
				}
				File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Log.bin");
				if (file1.exists()) {
					log = true;
				}
				file1 = null;
				File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Write.bin");
				if (file2.exists()) {
					write = true;
				}
				file2 = null;
			}
		} catch (Exception e) {
			Log.e("LOGER", "��ʼ����־�齨ʧ�� ", e);
			write = false;
		}
	}

	@SuppressLint("NewApi")
	public static void write(String logs) {
		String logString = String.format(fmt, DateFormat.format(df, System.currentTimeMillis()).toString()) + logs;
		try {
			bw.write(logString);
			bw.newLine();
			bw.flush();
			fw.flush();
		} catch (Exception e) {
			Log.e("LOGER", "write() ", e);
			write = false;
		}
	}

	public static String getDate() {
		return DateFormat.format(df, System.currentTimeMillis()).toString();
	}

	public static void write(String log, Throwable throwable) {
		sw = new StringWriter();
		pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		String error = sw.getBuffer().toString();
		String logString = String.format(fmt, DateFormat.format(df, System.currentTimeMillis()).toString()) + log
				+ "==>" + error;
		try {
			bw.write(logString);
			bw.newLine();
			bw.flush();
			fw.flush();
		} catch (Exception e) {
			Log.e("LOGER", "write() error", e);
			write = false;
		} finally {
			try {
				sw.close();
				pw.close();
			} catch (Exception e) {
				Log.e("LOGER", "write() error", e);
				write = false;
			}
			sw = null;
			pw = null;
		}
	}

	public static void i(String msg) {
		if (log) {
			Log.i(TAG, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void i(String tag, String msg) {
		if (log) {
			Log.i(tag, msg);
		}
		if (write) {
			String str = "接口地址:" + tag + "  参数:" + msg;
			write(str);
		}
		Log.e("i(String tag, String msg)", write + "");
	}

	public static void v(String msg) {
		if (log) {
			Log.v(TAG, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void v(String tag, String msg) {
		if (log) {
			Log.v(tag, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void d(String msg) {
		if (log) {
			Log.d(TAG, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void d(String tag, String msg) {
		if (log) {
			Log.d(tag, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void w(String msg) {
		if (log) {
			Log.w(TAG, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void w(String tag, String msg) {
		if (log) {
			Log.w(tag, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void e(String msg) {
		if (log) {
			Log.e(TAG, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void e(String tag, String msg) {
		if (log) {
			Log.e(tag, msg);
		}
		if (write) {
			write(msg);
		}
	}

	public static void e(String msg, Throwable e) {
		if (log) {
			Log.e(TAG, msg, e);
		}
		if (write) {
			write(msg, e);
		}
	}

	public static void e(String tag, String msg, Throwable e) {
		if (log) {
			Log.e(tag, msg, e);
		}
		if (write) {
			write(msg, e);
		}
	}

	// ����ͷ���Դ�����˳�����ڼ�¼�ʹ�ӡ��־�����
	public static void close() {
		try {
			if (fw != null) {
				fw.close();
				fw = null;
			}
			if (bw != null) {
				bw.close();
				bw = null;
			}
			if (sw != null) {
				sw.close();
				sw = null;
			}
			if (pw != null) {
				pw.close();
				pw = null;
			}
		} catch (Exception e) {
			Log.e("LOGER", "close() error  ", e);
			write = false;
		}
	}

	/*
	 * �ж�sdcard�Ƿ񱻹���
	 */
	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
