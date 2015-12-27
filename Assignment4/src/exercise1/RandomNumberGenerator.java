package exercise1;

// Acts as random number producer
// aka Thread 1
/**
 * Produces random numbers and puts them into a thread-safe Buffer object called "out" until done() is called.
 */
public class RandomNumberGenerator extends Thread{
	private Buffer out;
	private final int from = 0;
	private final int to = 41;
	private boolean done = false;
	
	public RandomNumberGenerator(Buffer out) {
		this.out = out;
	}
	
	private void produce() {
		int number = from + (int)(Math.random() * (to - from + 1));
		out.put(number);
	}
	
	/**
	 * Call this method to stop the Thread safely
	 */
	public void done() {
		done = true;
	}

	public void run() {
		while(!done) {
			produce();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		out.put(42); // poisonPill for consumer
	}
}
