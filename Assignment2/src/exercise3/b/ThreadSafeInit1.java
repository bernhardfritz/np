package exercise3.b;

public class ThreadSafeInit1 {
	private ExpensiveObject instance = null;

    public synchronized ExpensiveObject getInstance() {
        if(instance == null)
            instance = new ExpensiveObject();
        return instance;
    }
    
    public static void main(String[] args) {
    	ThreadSafeInit1 foo = new ThreadSafeInit1();
    	Thread[] threads = new Thread[3];
    	
    	for (int i = 0; i < threads.length; i++) {
    		threads[i] = new Thread(new Runnable() {
    			@Override
    			public void run() {
    				foo.getInstance(); // no reference needed
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
    	
    	System.out.println(ExpensiveObject.count + " instances of ExpensiveObject have been created");
    }
}