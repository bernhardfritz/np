package exercise3;

public class MyRunnableThread extends Thread {
	
	public MyRunnableThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		wasteTime();
	}
	
	public synchronized void wasteTime() {
		while(true);
	}
}