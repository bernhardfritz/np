package exercise1;

// Acts as random number consumer and produces filtered random numbers
// aka Thread 2
public class RandomNumberFilter extends Thread {
	private Buffer in;
	private Buffer out;
	private boolean poisoned = false;
	
	public RandomNumberFilter(Buffer in, Buffer out) {
		this.in = in;
		this.out = out;
	}
	
	public void consume() {
		Integer number = in.get();
		if(number == null) return;
		else if(number.equals(42)) poisoned = true;
		else if(isEven(number)) produce(number);
	}
	
	public void produce(int number) {
		out.put(number);
	}
	
	public boolean isEven(int number) {
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
