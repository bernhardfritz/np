package exercise2;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class MyRejectedExecutionHandler implements RejectedExecutionHandler {
	
	MyList<Runnable> rejectedRunnables;
	
	public MyRejectedExecutionHandler(MyList<Runnable> rejectedRunnables) {
		this.rejectedRunnables = rejectedRunnables;
	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		rejectedRunnables.add(r);
	}
}