package exercise1;

/**
 * Main contains a main method and showcases the use of RandomNumberGenerator-, RandomNumberFilter-, Server- and Client-Thread. 
 */
public class Main {
	/**
	 * Starts a RandomNumberGenerator-, RandomNumberFilter-, Server- and Client-Thread.
	 * RandomNumberGenerator will be stopped by calling its done() method.
	 * RandomNumberFilter will be stopped by a poisonPill from RandomNumberGenerator.
	 * Server will be stopped by calling its done() method.
	 * Client will be stopped by its interrupt() method.
	 * It's important to note that Client will only be interrupted without restoration if it is explicitly disabled by calling its disableRestoration method.
	 * This method needs the this reference. If the this reference is an instance of Main the restoration procedure will be disabled. 
	 * @throws InterruptedException If sleep or join is interrupted.
	 */
	public void doSomething() throws InterruptedException {
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
		thread4.disableRestoration(this);
		thread4.interrupt();
		thread4.join();
		System.out.println("Thread4 finished!");
	}
	
	public static void main(String[] args) throws InterruptedException {
		Main m = new Main();
		m.doSomething();
	}
}
