package exercise3;

public class MyMonitoringThread extends Thread {
	
	public MyMonitoringThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		Thread newThread = new Thread("New");
		MyRunnableThread runnableThread = new MyRunnableThread("Runnable");
		MyTimedWaitingThread timedWaitingThread = new MyTimedWaitingThread("Timed Waiting");
		MyWaitingThread waitingThread = new MyWaitingThread("Waiting");
		MyBlockedThread blockedThread = new MyBlockedThread("Blocked", runnableThread);
		Thread terminatedThread = new Thread("Terminated");
		
		runnableThread.start();
		timedWaitingThread.start();
		waitingThread.start();
		blockedThread.start();
		terminatedThread.start();		
		
		for(int i = 0; ; i++) {
			System.out.println("Elapsed time: " + i*3 + "s");
			System.out.println("============================");
			System.out.println(newThread.getName() + ": " + newThread.getState());
			System.out.println(terminatedThread.getName() + ": " + terminatedThread.getState());
			
			for (Thread t : Thread.getAllStackTraces().keySet()) {
				System.out.println(t.getName() + ": " + t.getState());
			}
			
			System.out.println("============================");
			System.out.println();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}