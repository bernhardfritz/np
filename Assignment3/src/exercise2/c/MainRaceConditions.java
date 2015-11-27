package exercise2.c;

import exercise2.a.CheckInRangeThread;
import exercise2.a.NumberRange;
import exercise2.a.SetLowerThread;
import exercise2.a.SetUpperThread;

public class MainRaceConditions {

	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++) {
			NumberRange numberRange = new NumberRange();
			SetLowerThread lowerThread = new SetLowerThread("LowerThread" + i, numberRange);
			SetUpperThread upperThread = new SetUpperThread("UpperThread" + i, numberRange);
			CheckInRangeThread inRangeThread = new CheckInRangeThread("InRangeThread" + i, numberRange);
			
			lowerThread.start();
			upperThread.start();
			try {
				lowerThread.join();
				upperThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			inRangeThread.start();
			try {
				inRangeThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
	}
}