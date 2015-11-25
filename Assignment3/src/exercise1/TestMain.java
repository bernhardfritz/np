package exercise1;

public class TestMain {

	public static void main(String[] args) {
		EventGeneratorThread generator1 = new EventGeneratorThread("EventGenerator1");
		EventGeneratorThread generator2 = new EventGeneratorThread("EventGenerator2");
		
		generator1.start();
		generator2.start();
		
		MySafeListener listener1 = MySafeListener.newInstance("Listener1", generator1);
		MySafeListener listener2 = MySafeListener.newInstance("Listener2", generator2);
	}
}