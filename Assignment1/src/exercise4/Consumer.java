package exercise4;

public class Consumer implements Runnable{
	private Buffer buffer;
	
	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}
	
	private void consume() {
		while(true) {
			Integer tmp = buffer.get();
			if(tmp.equals(0)) break;
			if(tmp == null) sleep();
		}
	}
	
	private void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		consume();
	}
}
