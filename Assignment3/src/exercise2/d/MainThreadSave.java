package exercise2.d;

import exercise2.a.CheckInRangeThread;
import exercise2.a.SetLowerThread;
import exercise2.a.SetUpperThread;

public class MainThreadSave {

	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++) {
			NumberRangeThreadSave numberRange = new NumberRangeThreadSave();
			SetLowerThread lowerThread = new SetLowerThread("LowerThread" + i, numberRange);
			SetUpperThread upperThread = new SetUpperThread("UpperThread" + i, numberRange);
			CheckInRangeThread inRangeThread = new CheckInRangeThread("InRangeThread" + i, numberRange);
			
			upperThread.start();
			lowerThread.start();
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