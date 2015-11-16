package exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainTest {

	public static void main(String[] args) throws Exception {
		List<Producer> producers = new ArrayList<Producer>();
		List<Buffer> buffers = new ArrayList<Buffer>();
		CountDownLatch latch = new CountDownLatch(1);
		for(int i = 1; i <= 4; i++) {
			Buffer buffer = new Buffer("Buffer" + i);
			buffers.add(buffer);
			producers.add(new Producer("Producer" + i, buffer, latch));
		}
		
		
		Consumer consumer = new Consumer("Consumer1", buffers, latch);
		
		for(Producer producer : producers) {
			producer.start();
		}
		consumer.start();
		
		latch.countDown();
		
		for(Producer producer : producers) {
			producer.join();
		}
		consumer.join();
		
		Log.print();
	}
}