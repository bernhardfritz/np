package exercise1;

public class Log {
	private static StringBuilder sb = new StringBuilder();
	
	public synchronized static void add(String s) {
		sb.append(s+"\n");
	}
	
	public synchronized static void print() {
		System.out.println(sb.toString());
	}
}
