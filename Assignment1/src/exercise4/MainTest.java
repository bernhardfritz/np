package exercise4;

public class MainTest {

	public static void main(String[] args) {
		Buffer buffer = new Buffer();
		Thread producer = new Thread(new Producer("Producer", buffer), "Producer");
		Thread consumer = new Thread(new Consumer("Consumer", buffer), "Consumer");
		
		producer.start();
		consumer.start();
	}
}