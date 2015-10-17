package exercise3;

public class MyBlockedThread extends Thread {
	
	private MyRunnableThread blockingThread;

	public MyBlockedThread(String name, MyRunnableThread blockingThread) {
		super(name);
		this.blockingThread = blockingThread;
	}

	@Override
	public void run() {
		blockingThread.wasteTime();
	}
}