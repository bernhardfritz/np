package exercise1;

public class ThreadMain {

	public static void main(String[] args) {
		int threadCount = 100000;
		
		for (int i = 0; i < threadCount; i++) {
			new Thread(new Runnable() {
				
				public void run() {
					System.out.println("Thread-Nummer: " + Thread.currentThread().getId());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}