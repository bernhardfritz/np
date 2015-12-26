package exercise3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CalculatePartOfPi {
	private int from;
	private int to;
	
	private final FutureTask<BigDecimal> future = new FutureTask<BigDecimal>(new Callable<BigDecimal>() {
		public BigDecimal call() {
			BigDecimal result = BigDecimal.ZERO;
			for(int i = from; i < to; i++) {
				result = result.add(calculateStep(i));
			}
			return result;
		}
	});
	
	private final Thread thread = new Thread(future);
	
	public CalculatePartOfPi(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	public void start() {
    	thread.start();
    }
    
    public BigDecimal get() throws InterruptedException {
		try {
			return future.get();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private BigDecimal calculateStep(int n) {
		return BigDecimal.valueOf(-1).pow(n)
				.divide(BigDecimal.valueOf(2)
						.multiply(BigDecimal.valueOf(n))
						.add(BigDecimal.ONE), 100, RoundingMode.HALF_UP);
	}
}