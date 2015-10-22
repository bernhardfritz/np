package exercise4;

public class Producer implements Runnable {
	private Buffer buffer;
	private String name;
	
	public Producer(String name, Buffer buffer) {
		this.name = name;
		this.buffer = buffer;
	}
	
	private void produce() {
		int rand = 0;
		do {
			rand = (int) (Math.random()*100);
			buffer.put(rand);
			sleep();
		} while(rand != 0);
	}

	private void sleep() {
		try {
			int t = (int)(Math.random()*4)*1000;
			
			String s = name + " sleeps for " + t/1000 + " second";
			s += (t/1000 != 1) ? "s.\n" : ".\n";
			System.out.println(s);
			
			Thread.sleep(t); // sleep for [0,3] seconds
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		produce();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}