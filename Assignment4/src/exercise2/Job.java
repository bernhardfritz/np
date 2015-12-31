package exercise2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Job {
	
	private String name;
	
	private BigDecimal computingWork;
	
	private BigDecimal moneyAmount;

	public Job(String name, BigDecimal computationWork, BigDecimal moneyAmount) {
		this.name = name;
		this.computingWork = computationWork;
		this.moneyAmount = moneyAmount;
	}
	
	public String getName() {
		return name;
	}

	public BigDecimal getComputationWork() {
		return computingWork;
	}

	public BigDecimal getMoneyAmount() {
		return moneyAmount;
	}
	
	public BigDecimal getMoneyWorkProportion() {
		return moneyAmount.divide(computingWork, RoundingMode.HALF_UP);
	}
	
	@Override
	public String toString() {
		return name + " (" + computingWork + ", " + moneyAmount + ", " + getMoneyWorkProportion() + ")";
	}
}