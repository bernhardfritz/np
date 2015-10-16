package exercise1;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

public class CalculateTask implements Callable<Double> {
	private int from;
	private int to;
	
	public CalculateTask(int from, int to) {
		this.from = from;
		this.to = to;
	}
	@Override
	public Double call() throws Exception {
		double result = 0;
		for(int i=from; i<to; i++) {
			result += calculateStep(i);
		}
		return result;
	}
	
	private double calculateStep(int n) {
		return Math.pow(-1, n)/(2*n+1);
	}
}