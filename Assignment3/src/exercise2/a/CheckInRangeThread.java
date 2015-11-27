package exercise2.a;

public class CheckInRangeThread extends Thread {
	
	private NumberRange numberRange;
	
	public CheckInRangeThread(String name, NumberRange numberRange) {
		super(name);
		this.numberRange = numberRange;
	}
	
	@Override
	public void run() {
		int rand = (int) (Math.random() * 100);
		
		if (numberRange.isInRange(rand)) {
			System.out.println(getName() + ": " + rand + " is in range (" + numberRange.getLower() + " - " + numberRange.getUpper() + ")");
		} else {
			System.out.println(getName() + ": " + rand + " is not in range (" + numberRange.getLower() + " - " + numberRange.getUpper() + ")");
		}
	}
}