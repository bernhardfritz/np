package exercise1;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Buffer buffer1 = new Buffer();
		Buffer buffer2 = new Buffer();
		
		RandomNumberGenerator thread1 = new RandomNumberGenerator(buffer1);
		RandomNumberFilter thread2 = new RandomNumberFilter(buffer1, buffer2);
		Server thread3 = new Server(buffer2);
		Client thread4 = new Client();
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		Thread.sleep(5000);
		
		thread1.done();
		thread1.join();
		System.out.println("Thread1 finished!");
		// thread2 will be poisoned by poisonPill from thread1
		thread2.join();
		System.out.println("Thread2 finished!");
		thread3.done();
		thread3.join();
		System.out.println("Thread3 finished!");
		thread4.interrupt();
		thread4.join();
		System.out.println("Thread4 finished!");
	}
}
