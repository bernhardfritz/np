package exercise3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Benchmark {
	public static float insertPercentage = 0;
	public static float readPercentage = 0;
	public static float removePercentage = 0;
	public static int operationsPerThread = 0;
	
	public static double getOperationsPerSecond(int operations, long ms) {
		double seconds = (double) ms / 1000;
		return (double) operations / seconds;
	}
	
	public static void doBenchmark(Task task) {
		BlockingTaskExecutor executor = new BlockingTaskExecutor();
		System.out.println(String.format("# of threads | HashMap accesses per second (%d%% insert, %d%% read, %d%% remove)", (int)(insertPercentage*100), (int)(readPercentage*100), (int)(removePercentage * 100)));
		System.out.println("--------------------------------------------------------------------------------");
		for(int i = 1; i <= 16; i *= 2) {
			System.out.println(String.format("%d           " + (i < 10 ? " ": "") + "| %d", i, Math.round(getOperationsPerSecond(i * operationsPerThread, executor.addTasks(task, i).execute()))));
			task.getM().clear();
		}
	}
	
	public static void main(String[] args) {
		Config config = null;
		if(args.length > 3) {
			insertPercentage = Float.parseFloat(args[0]);
			readPercentage = Float.parseFloat(args[1]);
			removePercentage = Float.parseFloat(args[2]);
			operationsPerThread = Integer.parseInt(args[3]);
			if((insertPercentage + readPercentage + removePercentage) != 1.0f) {
				System.out.println("Percentage parameters must sum up to 1.0");
				System.exit(0);
			}
			if(operationsPerThread < 0) {
				System.out.println("operationsPerThread parameter cannot be negative");
				System.exit(0);
			}
			config = new Config(Math.round(insertPercentage*operationsPerThread), 
					Math.round(readPercentage*operationsPerThread), 
					Math.round(removePercentage*operationsPerThread));
		} else {
			System.out.println("Usage: benchmark insertPercent readPercent removePercent operationsPerThread");
			System.out.println("For example: benchmark 0.5 0.25 0.25 100000");
			System.exit(0);
		}
		Map<Integer, String> m1 = new HashMap<Integer, String>();
		Map<Integer, String> m2 = new SynchronizedHashMap<Integer, String>();
		Map<Integer, String> m3 = new ConcurrentHashMap<Integer, String>();
		
		Task withoutSynchronization = new Task(m1, config);
		System.out.println("Without synchronization:");
		System.out.println("========================");
		doBenchmark(withoutSynchronization);
		System.out.println();
		
		Task withSynchronization = new Task(m2, config);
		System.out.println("With synchronization:");
		System.out.println("=====================");
		doBenchmark(withSynchronization);
		System.out.println();
		
		Task concurrent = new Task(m3, config);
		System.out.println("Concurrent:");
		System.out.println("===========");
		doBenchmark(concurrent);
	}
}
