package exercise3;

import java.util.*;

public class BlockingTaskExecutor {
	private List<Task> tasks;
	
	public BlockingTaskExecutor() {
		tasks = new ArrayList<Task>();
	}
	
	public BlockingTaskExecutor addTask(Task t) {
		tasks.add((Task) t.clone());
		return this;
	}
	
	public BlockingTaskExecutor addTasks(Task t, int n) {
		for(int i = 0; i < n; i++) {
			addTask(t);
		}
		return this;
	}
	
	public long execute() {
		long start = System.currentTimeMillis();
		Thread threads[] = new Thread[tasks.size()];
		int i = 0;
		for(Runnable task : tasks) {
			threads[i++] = new Thread(task);
		}
		for(Thread thread : threads) {
			thread.start();
		}
		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tasks.clear();
		return System.currentTimeMillis() - start;
	}
}
