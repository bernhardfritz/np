package exercise1;

// Acts as random number producer
// aka Thread 1
public class RandomNumberGenerator extends Thread{
	private Buffer out;
	private final int from = 0;
	private final int to = 41;
	private boolean done = false;
	
	public RandomNumberGenerator(Buffer out) {
		this.out = out;
	}
	
	public void produce() {
		int number = from + (int)(Math.random() * (to - from + 1));
		out.put(number);
	}
	
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
