package exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainTest {

	public static void main(String[] args) throws Exception {
		List<Producer> producers = new ArrayList<Producer>();
		List<Buffer> buffers = new ArrayList<Buffer>();
		CountDownLatch startLatch = new CountDownLatch(1);
		
		Lock consumerLock = new ReentrantLock();
		Condition consumable = consumerLock.newCondition();
		
		
		for(int i = 1; i <= 4; i++) {
			Buffer buffer = new Buffer("Buffer" + i);
			buffers.add(buffer);
			producers.add(new Producer("Producer" + i, buffer, startLatch, consumerLock, consumable));
		}
		
		Consumer consumer1 = new Consumer("Consumer1", buffers, startLatch, consumerLock, consumable);
		Consumer consumer2 = new Consumer("Consumer2", buffers.subList(0, 2), startLatch, consumerLock, consumable);
		
		for(Producer producer : producers) {
			producer.start();
		}
		consumer1.start();
		consumer2.start();
		
		startLatch.countDown();
		
		for(Producer producer : producers) {
			producer.join();
		}
		consumer1.join();
		consumer2.join();
		
		Log.print();
	}
}