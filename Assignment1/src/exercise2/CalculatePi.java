package exercise2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CalculatePi {
	public static int getAmountOfCorrectFractions(BigDecimal experiment, BigDecimal control) {
		if(!experiment.toBigInteger().equals(control.toBigInteger())) {
			return 0;
		}
		int count = 0;
		while(experiment.toBigInteger().equals(control.toBigInteger())) {
			experiment = experiment.subtract(new BigDecimal(experiment.toBigInteger()));
			experiment = experiment.multiply(BigDecimal.TEN);
			control = control.subtract(new BigDecimal(control.toBigInteger()));
			control = control.multiply(BigDecimal.TEN);
			count++;
		}
		return count-1; // we only care about the amount of correct fractions
	}
	
	public static void main(String[] args) {
		int threadCount = 20;
		if(args.length > 0) {
			System.out.println("Program argument detected. Using " + args[0] + " threads to calculate PI.");
			threadCount = Integer.valueOf(args[0]);
		}
		int n = 100000000;
		int stepsPerThread = n/threadCount; // int division
		int desiredAmountOfFractions = 7;
		
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		BigDecimal result = BigDecimal.ZERO;
		List<Future<BigDecimal>> futures = new ArrayList<Future<BigDecimal>>();
		for(int i = 0; i < threadCount; i++) {
			int from = i*stepsPerThread;
			int to = i*stepsPerThread+stepsPerThread;
			if(i == threadCount - 1) { // last thread has to do more work if n%threadCount != 0
				to = n;
			}
			futures.add(executorService.submit(new CalculateTask(from, to)));
		}
		for(Future<BigDecimal> future: futures) {
			try {
				result = result.add(future.get());
				if(getAmountOfCorrectFractions(result.multiply(BigDecimal.valueOf(4)), BigDecimal.valueOf(Math.PI)) >= desiredAmountOfFractions) {
					System.out.println("Desired amount of "+ desiredAmountOfFractions + " fractions reached. Aborting calculation...");
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		if(getAmountOfCorrectFractions(result.multiply(BigDecimal.valueOf(4)), BigDecimal.valueOf(Math.PI)) < desiredAmountOfFractions) {
			System.out.println("Desired amount of " + desiredAmountOfFractions + " fractions could not be reached. Try increasing n to achieve higher accuracy.");			
		}
			
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("experiment PI:\t" + result.multiply(BigDecimal.valueOf(4)));
		System.out.println("control PI:\t" + Math.PI);
		System.out.print("\t\t  ");
		for(int i = 0; i < getAmountOfCorrectFractions(result.multiply(BigDecimal.valueOf(4)), BigDecimal.valueOf(Math.PI))-1; i++) {
			System.out.print(" ");
		}
		System.out.println("^");
	}
}