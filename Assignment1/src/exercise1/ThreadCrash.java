package exercise1;

public class ThreadCrash implements Runnable{
	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int threadCount = 10000;
		int i = 0;
		try {
			for(; i<threadCount; i++) {
				Thread t = new Thread(new ThreadCrash());
				System.out.println("Starting thread with id " + t.getId());
				t.start();
			}
		} catch(OutOfMemoryError err) {
			System.out.println(i-1 + " threads have been created before running out of memory");
			System.out.println(Runtime.getRuntime().maxMemory()/(1024*1024));
		}
	}
}
