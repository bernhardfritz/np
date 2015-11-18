package exercise1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producer extends Thread {
	private Buffer buffer;
	private CountDownLatch startLatch;
	
	private Lock consumerLock;
	private Condition consumable;
	
	public Producer(String name, Buffer buffer, CountDownLatch startLatch, Lock consumerLock, Condition consumable) {
		setName(name);
		this.buffer = buffer;
		this.startLatch = startLatch;
		this.consumerLock = consumerLock;
		this.consumable = consumable;
	}
	
	private void produce() {
		int rand = 0;
		do {
			rand = (int) (Math.random()*10);
			consumerLock.lock();
			buffer.put(rand);
			consumable.signalAll();
			consumerLock.unlock();
		} while(rand != 0);
	}
	
	public void run() {
		try {
			startLatch.await();
			produce();
			Log.add(getName() + " stopped");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}