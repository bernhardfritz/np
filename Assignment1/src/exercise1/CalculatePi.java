package exercise1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalculatePi {
	public static void main(String[] args) {
		int threadCount = 1000;
		int stepsPerThread = 1000;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		double result = 0;
		for(int i = 0; i<threadCount; i++) {
			try {
				result += executorService.submit(new CalculateTask(i*stepsPerThread, i*stepsPerThread+stepsPerThread)).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println(result*4);
	}
}

//TODO: add support for BigDecimal
