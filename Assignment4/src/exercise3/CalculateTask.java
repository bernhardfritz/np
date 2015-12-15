package exercise3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.Callable;

public class CalculateTask implements Callable<BigDecimal> {
	private int from;
	private int to;
	
	public CalculateTask(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	@Override
	public BigDecimal call() throws Exception {
		BigDecimal result = BigDecimal.ZERO;
		for(int i = from; i < to; i++) {
			result = result.add(calculateStep(i));
		}
		return result;
	}
	
	private BigDecimal calculateStep(int n) {
		return BigDecimal.valueOf(-1).pow(n)
				.divide(BigDecimal.valueOf(2)
						.multiply(BigDecimal.valueOf(n))
						.add(BigDecimal.ONE), 100, RoundingMode.HALF_UP);
	}
}