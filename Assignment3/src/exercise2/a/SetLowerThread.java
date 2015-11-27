package exercise2.a;

public class SetLowerThread extends Thread {
	
	private NumberRange numberRange;
	
	public SetLowerThread(String name, NumberRange numberRange) {
		super(name);
		this.numberRange = numberRange;
	}
	
	@Override
	public void run() {
		int lower = (int) (Math.random() * 100);
		
		try {
			numberRange.setLower(lower);
			System.out.println(getName() + ": Set lower to " + lower);
		} catch (IllegalArgumentException e) {
			System.out.println(getName() + ": " + e.getMessage());
		}
	}
}