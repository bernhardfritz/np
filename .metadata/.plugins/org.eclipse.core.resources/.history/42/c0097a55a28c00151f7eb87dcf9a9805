package exercise3.b;

public class ThreadSafeInit2 {
	private static class Helper {
        private static final ExpensiveObject INSTANCE = new ExpensiveObject();
    }
	
	public static ExpensiveObject getInstance() {
        return Helper.INSTANCE;
    }
	
	public static void main(String[] args) {
    	Thread[] threads = new Thread[3];
    	
    	for (int i = 0; i < threads.length; i++) {
    		threads[i] = new Thread(new Runnable() {
    			@Override
    			public void run() {
    				ThreadSafeInit2.getInstance(); // no reference needed
    			}
    		});
    		threads[i].start();
    	}
    	
    	for (int i = 0; i < threads.length; i++) {
    		try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
    	System.out.println(ExpensiveObject.count + " instance of ExpensiveObject have been created");
	}
}