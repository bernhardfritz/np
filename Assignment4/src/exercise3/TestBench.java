package exercise3;

import java.util.ArrayList;
import java.util.List;

public class TestBench {
	public static void main(String[] args) {
		List<Double> taskTimes = new ArrayList<Double>();
		List<Double> totalTimes = new ArrayList<Double>();
		int numberOfThreads[] = {1, 2, 4, 80, 160, 320, 1024, 2048};
		for(int i : numberOfThreads) {
			String[] arr = {String.valueOf(i)};
			CalculatePi.main(arr);
			taskTimes.add(CalculatePi.taskTime);
			totalTimes.add(CalculatePi.totalTime);
		}
		System.out.println("#threads|\ttask time[s]\ttotal time[s]");
		System.out.println("---------------------------------------------");
		int count = 0;
		for(int i : numberOfThreads) {
			System.out.println(i + "\t|\t" + taskTimes.get(count) + "\t|\t" + totalTimes.get(count));
			count++;
		}
	}
}
