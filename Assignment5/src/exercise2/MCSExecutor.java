package exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Monte Carlo simulation of a European call option
 */
public class MCSExecutor {
	
	private final double s0 = 100.0;
	private final double k = 100.0;
	private final double t = 1.0;
	private final double r = 0.05;
	private final double sig = 0.2;
	
	public double simulate(int n) {
		
		final int runnableCount = 2000;
		final int runnableCalcSteps = 400;
		final int threadCount = 1000;
		
		double dt;
		double nuDt;
		double sigSqrtDt;
		double exprT;
		Random random = new Random();
		
		MySum mySum = new MySum();

		dt = t / (double)n;
		nuDt = (r - 0.5 * sig * sig) * dt;
		sigSqrtDt = sig * Math.sqrt(dt);
		exprT = Math.exp(-r * t);
		
		List<Runnable> calculatePartList = new ArrayList<Runnable>();
		for (int i = 0; i < 2000; i++) {
			calculatePartList.add(new CalculatePart(runnableCalcSteps, n, s0, random, nuDt, sigSqrtDt, k, mySum));
		}
		
		ExecutorService executor = Executors.unconfigurableExecutorService(
				new ThreadPoolExecutor(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS, 
						new LinkedBlockingDeque<Runnable>(threadCount), new ThreadPoolExecutor.CallerRunsPolicy()));
		
		long startTime = System.currentTimeMillis();
		for (Runnable calculatePart : calculatePartList) {
			executor.execute(calculatePart);
		}
		executor.shutdown();
		while(!executor.isTerminated()) {}
		long now = System.currentTimeMillis();
		double elapsedTime = (now - startTime) / 1000.0;
		
		int m = runnableCount * runnableCalcSteps;
		double value = mySum.get() * exprT / (double)m;
		
		System.out.println("Runtime: " + elapsedTime + "s");
		return value;
	}
	
	public static void main(String[] args) {
		int n = 100;
		
		MCSExecutor mcs = new MCSExecutor();
		System.out.println("Value = " + mcs.simulate(n));
	}	  
}