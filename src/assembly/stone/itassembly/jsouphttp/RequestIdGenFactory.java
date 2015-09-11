package assembly.stone.itassembly.jsouphttp;

public class RequestIdGenFactory {
	private static int id = 0;
	private static final int ID_MOD = 10000;

	public static int gen() {
		int newId;
		synchronized (RequestIdGenFactory.class) {
			newId = (++id) % ID_MOD;
		}
		return newId;
	}
}
