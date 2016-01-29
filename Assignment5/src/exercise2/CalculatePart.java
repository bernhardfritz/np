package exercise2;

import java.util.Random;

public class CalculatePart implements Runnable {
	
	private int m;
	private int n;
	
	private double s0;
	private Random random;
	private double nuDt;
	private double sigSqrtDt;
	private double k;
	
	private MySum mySum;
	
	public CalculatePart(int m, int n, double s0, Random random, double nuDt, double sigSqrtDt, double k, MySum mySum) {
		this.m = m;
		this.n = n;
		this.s0 = s0;
		this.random = random;
		this.nuDt = nuDt;
		this.sigSqrtDt = sigSqrtDt;
		this.k = k;
		this.mySum = mySum;
	}

	@Override
	public void run() {
		double st;
		double eps;
		double ct;
		double sum = 0.0;
		
		for (int j = 1; j <= m; j++) {
			st = s0;
			for (int i = 1; i <= n; i++) {
				eps = random.nextGaussian();
				st *= Math.exp(nuDt + sigSqrtDt * eps);
			}
			ct = Math.max(0.0, st - k);
			sum += ct;
		}
		
		mySum.add(sum);
	}
}