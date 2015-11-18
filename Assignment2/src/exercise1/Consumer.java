package exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer extends Thread {
	private List<Buffer> buffers;
	private CountDownLatch startLatch;
	
	private Lock consumerLock;
	private Condition consumable;
	
	public Consumer(String name, List<Buffer> buffers, CountDownLatch startLatch, Lock consumerLock, Condition consumable) {
		setName(name);
		this.buffers = new ArrayList<Buffer>(buffers);
		this.startLatch = startLatch;
		this.consumerLock = consumerLock;
		this.consumable = consumable;
	}
	
	private void consume() {		
		while(buffers.size() > 0) {
			consumerLock.lock();
			int i = 0;
			int max = buffers.size();
			while (i < max) {
				Buffer buffer = buffers.get(i);
				Integer value = buffer.peek();
				if (value == null) {
					try {						
						consumable.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					i = 0;
					continue;
				}
				else {
					if (value.equals(0)) {
						buffers.remove(buffer);
						i = 0;
						max--;
						continue;
					}
					else {
						i++;
					}
				}
			}
			
			for(Buffer buffer : buffers) {
				buffer.get();
			}
			
			consumerLock.unlock();
			
			// Consumer muss nach jeder Konsumierung kurz warten, um Verhungern anderer Consumer zu vermeiden
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	
	public void run() {
		try {
			startLatch.await();
			consume();
			Log.add(getName() + " stopped");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}