package exercise2.a;

public class SetUpperThread extends Thread {
	
	private NumberRange numberRange;
	
	public SetUpperThread(String name, NumberRange numberRange) {
		super(name);
		this.numberRange = numberRange;
	}
	
	@Override
	public void run() {
		int upper = (int) (Math.random() * 100);
		
		try {
			numberRange.setUpper(upper);
			System.out.println(getName() + ": Set upper to " + upper);
		} catch (IllegalArgumentException e) {
			System.out.println(getName() + ": " + e.getMessage());
		}
	}
}