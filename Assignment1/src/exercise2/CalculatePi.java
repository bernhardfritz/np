package exercise2;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalculatePi {
	public static void main(String[] args) {
		int threadCount = 1000;
		int stepsPerThread = 1000;
		int fractionDigits = 100;
		
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		BigDecimal result = BigDecimal.ZERO;
		for(int i = 0; i < threadCount; i++) {
			try {
				result = result.add(executorService.submit(new CalculateTask(i*stepsPerThread, i*stepsPerThread+stepsPerThread, fractionDigits)).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println(result.multiply(BigDecimal.valueOf(4)));
	}
}