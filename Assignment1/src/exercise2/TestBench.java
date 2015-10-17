package exercise2;

import java.util.ArrayList;
import java.util.List;

public class TestBench {
	public static void main(String[] args) {
		List<Double> results = new ArrayList<Double>();
		for(int i = 1; i <= 20; i++) {
			Stopwatch stopWatch = new Stopwatch();
			String[] arr = {String.valueOf(i)};
			CalculatePi.main(arr);
			results.add(stopWatch.elapsedTime());
		}
		System.out.println("#threads\t|\ttime");
		for(int i = 0; i < 20; i++) {
			System.out.println(i + "\t|\t" + results.get(i));
		}
	}

}
