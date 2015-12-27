package exercise1;

// Acts as random number consumer and produces filtered random numbers
// aka Thread 2
/**
 * Consumes numbers from a thread-safe Buffer object called "in", filters them, and puts them into a thread-safe Buffer object called "out" until a poisonPill (the number 42) is received.
 */
public class RandomNumberFilter extends Thread {
	private Buffer in;
	private Buffer out;
	private boolean poisoned = false;
	
	public RandomNumberFilter(Buffer in, Buffer out) {
		this.in = in;
		this.out = out;
	}
	
	private void consume() {
		Integer number = in.get();
		if(number == null) return;
		else if(number.equals(42)) poisoned = true;
		else if(isEven(number)) produce(number);
	}
	
	private void produce(int number) {
		out.put(number);
	}
	
	private boolean isEven(int number) {
		return number % 2 == 0;
	}

	public void run() {
		while(!poisoned) {
			consume();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
