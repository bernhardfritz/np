package exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Monte Carlo simulation of a European call option
 */
public class MCSSimple {
	
	private final double s0 = 100.0;
	private final double k = 100.0;
	private final double t = 1.0;
	private final double r = 0.05;
	private final double sig = 0.2;
	
	public double simulate(int n) {
		
		final int runnableCount = 2000;
		final int runnableCalcSteps = 400;
		
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
		
		List<Thread> calculatePartList = new ArrayList<Thread>();
		for (int i = 0; i < 2000; i++) {
			calculatePartList.add(new Thread(new CalculatePart(runnableCalcSteps, n, s0, random, nuDt, sigSqrtDt, k, mySum)));
		}
		
		long startTime = System.currentTimeMillis();
		for (Thread calculatePart : calculatePartList) {
			calculatePart.start();
		}
		for (Thread calculatePart : calculatePartList) {
			try {
				calculatePart.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long now = System.currentTimeMillis();
		double elapsedTime = (now - startTime) / 1000.0;
		
		int m = runnableCount * runnableCalcSteps;
		double value = mySum.get() * exprT / (double)m;
		
		System.out.println("Runtime: " + elapsedTime + "s");
		return value;
	}
	
	public static void main(String[] args) {
		int n = 100;
		
		MCSSimple mcs = new MCSSimple();
		System.out.println("Value = " + mcs.simulate(n));
	}	  
}