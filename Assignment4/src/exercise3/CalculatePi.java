package exercise3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculatePi {
	public static double taskTime;
	public static double totalTime;
	
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
		int numberOfThreads = 0;
		if(args.length > 0) {
			System.out.println("Using " + args[0] + " threads to calculate PI.");
			numberOfThreads = Integer.valueOf(args[0]);
		} else {
			System.out.println("Usage: CalculatePi numberOfThreads");
			System.out.println("E.g: CalculatePi 42");
			System.exit(1);
		}
		int n = 10000000;
		int stepsPerThread = n / numberOfThreads; // int division
		
		List<CalculatePartOfPi> partsOfPi = new ArrayList<CalculatePartOfPi>();
		Stopwatch stopWatchTotalTime = new Stopwatch();
		for(int i = 0; i < numberOfThreads; i++) {
			int from = i * stepsPerThread;
			int to = i * stepsPerThread + stepsPerThread;
			if(i == numberOfThreads - 1) { // last thread has to do more work if n % threadCount != 0
				to = n;
			}
			partsOfPi.add(new CalculatePartOfPi(from, to));
		}
		
		Stopwatch stopWatchTaskTime = new Stopwatch();
		for(CalculatePartOfPi partOfPi : partsOfPi) {
			partOfPi.start();
		}
		
		BigDecimal result = BigDecimal.ZERO;
		for(CalculatePartOfPi partOfPi : partsOfPi) {
			try {
				result = result.add(partOfPi.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		taskTime = stopWatchTaskTime.elapsedTime();
		totalTime = stopWatchTotalTime.elapsedTime();
		
		
		System.out.println("experiment PI:\t" + result.multiply(BigDecimal.valueOf(4)));
		System.out.println("control PI:\t" + Math.PI);
		System.out.print("\t\t  ");
		for(int i = 0; i < getAmountOfCorrectFractions(result.multiply(BigDecimal.valueOf(4)), BigDecimal.valueOf(Math.PI))-1; i++) {
			System.out.print(" ");
		}
		System.out.println("^");
	}
}