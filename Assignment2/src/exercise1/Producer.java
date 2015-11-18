package exercise1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producer extends Thread {
	private Buffer buffer;
	private CountDownLatch startLatch;
	
	private Lock lock;
	private Condition consumable;
	
	public Producer(String name, Buffer buffer, CountDownLatch startLatch, Lock lock, Condition consumable) {
		setName(name);
		this.buffer = buffer;
		this.startLatch = startLatch;
		this.lock = lock;
		this.consumable = consumable;
	}
	
	private void produce() {
		int rand = 0;
		do {
			rand = (int) (Math.random()*10);
			
			lock.lock();
			buffer.put(rand);
			consumable.signalAll();
			lock.unlock();
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