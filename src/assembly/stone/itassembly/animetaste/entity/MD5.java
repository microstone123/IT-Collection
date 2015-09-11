package assembly.stone.itassembly.animetaste.entity;

import java.security.MessageDigest;

public class MD5 {
	static final String HEXES = "0123456789abcdef";

	public static String digest(String paramString) {
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("md5");
			localMessageDigest.update(paramString.getBytes());
			String str = getHex(localMessageDigest.digest());
			return str;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return null;
	}

	private static String getHex(byte[] paramArrayOfByte) {
		if (paramArrayOfByte == null)
			return null;
		StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
		int i = paramArrayOfByte.length;
		for (int j = 0; j < i; j++) {
			int k = paramArrayOfByte[j];
			localStringBuilder.append("0123456789abcdef".charAt((k & 0xF0) >> 4)).append(
					"0123456789abcdef".charAt(k & 0xF));
		}
		return localStringBuilder.toString();
	}
}
