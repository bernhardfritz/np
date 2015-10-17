package exercise3;

public class MyTimedWaitingThread extends Thread {
	
	public MyTimedWaitingThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}