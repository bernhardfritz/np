package exercise4;

public class Consumer implements Runnable {
	private Buffer buffer;
	private String name;
	
	public Consumer(String name, Buffer buffer) {
		this.name = name;
		this.buffer = buffer;
	}
	
	private void consume() {
		Integer tmp = null;
		do {
			tmp = buffer.get();
			if(tmp == null) {
				sleep();
			}
		} while(tmp == null || !tmp.equals(0));
	}
	
	private void sleep() {
		try {
			System.out.println(name + " sleeps for 2 seconds.\n");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		consume();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}