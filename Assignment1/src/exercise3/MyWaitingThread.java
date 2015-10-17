package exercise3;

public class MyWaitingThread extends Thread {
	
	public MyWaitingThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}