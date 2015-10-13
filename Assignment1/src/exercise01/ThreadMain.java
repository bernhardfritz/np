package exercise01;

public class ThreadMain {

	public static void main(String[] args) {
		int threadCount = 8;
		
		for (int i = 0; i < threadCount; i++) {
			new Thread(new Runnable() {
				
				public void run() {
					System.out.println("Thread-Nummer: " + Thread.currentThread().getId());
				}
			}).start();
		}
	}
}