package exercise2;

public class MySum {
	
	private double sum;
	
	public MySum() {
		this.sum = 0.0;
	}

	public synchronized void add(double v) {
		sum += v;
	}
	
	public synchronized double get() {
		return sum;
	}
}