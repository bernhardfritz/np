package exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer extends Thread {
	private List<Buffer> buffers;
	private CountDownLatch startLatch;
	
	private Lock lock;
	private Condition consumable;
	
	public Consumer(String name, List<Buffer> buffers, CountDownLatch startLatch, Lock lock, Condition consumable) {
		setName(name);
		this.buffers = new ArrayList<Buffer>(buffers);
		this.startLatch = startLatch;
		this.lock = lock;
		this.consumable = consumable;
	}
	
	private void consume() {		
		while(buffers.size() > 0) {
			lock.lock();
			
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
			
			lock.unlock();
			
			// Consumer muss nach jedem Konsum kurz warten, um das Verhungern anderer Consumer zu vermeiden
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