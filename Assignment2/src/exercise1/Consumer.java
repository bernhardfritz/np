package exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Consumer extends Thread {
	private List<Buffer> buffers;
	private CountDownLatch latch;
	
	public Consumer(String name, List<Buffer> buffers, CountDownLatch latch) {
		setName(name);
		this.buffers = buffers;
		this.latch = latch;
	}
	
	private void consume() {
		while(buffers.size() > 0) {
			List<Buffer> trash = new ArrayList<Buffer>();
			for(Buffer buffer : buffers) {
				if(buffer.peek().equals(0)) {
					trash.add(buffer);
				}
			}
			for(Buffer buffer : trash) {
				buffers.remove(buffer);
			}
			for(Buffer buffer : buffers) {
				buffer.get();
			}
		}
	}
	
	public void run() {
		try {
			latch.await();
			consume();
			Log.add(getName() + " stopped");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}