package exercise1;

import java.util.concurrent.CountDownLatch;

public class Producer extends Thread {
	private Buffer buffer;
	private CountDownLatch latch;
	
	public Producer(String name, Buffer buffer, CountDownLatch latch) {
		setName(name);
		this.buffer = buffer;
		this.latch = latch;
	}
	
	private void produce() {
		int rand = 0;
		do {
			rand = (int) (Math.random()*10);
			buffer.put(rand);
		} while(rand != 0);
	}
	
	public void run() {
		try {
			latch.await();
			produce();
			Log.add(getName() + " stopped");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}