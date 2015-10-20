package exercise4;

public class Producer implements Runnable{
	private Buffer buffer;
	
	public Producer(Buffer buffer) {
		this.buffer = buffer;
	}
	
	private void produce() {
		while(true) {
			int rand = (int) Math.random()*100;
			buffer.put(rand);
			if(rand == 0) break;
			sleep();
		}
	}

	private void sleep() {
		try {
			Thread.sleep((int)(Math.random()*4)*1000); // sleep for [0,3] seconds
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		produce();
	}
}
