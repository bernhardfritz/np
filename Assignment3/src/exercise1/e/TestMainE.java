package exercise1.e;

import java.util.ArrayList;
import java.util.List;

import exercise1.EventGeneratorThread;
import exercise1.ListeningObjectsThread;
import exercise1.MySafeListener;

public class TestMainE {

	public static void main(String[] args) {
		MySafeListener listener1 = MySafeListener.newInstance("Listener1");
		MySafeListener listener2 = MySafeListener.newInstance("Listener2");
		
		List<MySafeListener> generator1Listeners = new ArrayList<MySafeListener>();
		generator1Listeners.add(listener1);
		
		List<MySafeListener> generator2Listeners = new ArrayList<MySafeListener>();
		generator2Listeners.add(listener2);
		
		List<MySafeListener> generator3Listeners = new ArrayList<MySafeListener>();
		generator3Listeners.add(listener1);
		
		List<MySafeListener> generator4Listeners = new ArrayList<MySafeListener>();
		generator4Listeners.add(listener2);
		
		List<MySafeListener> generator5Listeners = new ArrayList<MySafeListener>();
		generator5Listeners.add(listener1);
		generator5Listeners.add(listener2);
		
		List<MySafeListener> generator6Listeners = new ArrayList<MySafeListener>();
		generator6Listeners.add(listener1);
		generator6Listeners.add(listener2);
		
		EventGeneratorThread generator1 = new EventGeneratorThread("EventGenerator1", generator1Listeners);
		EventGeneratorThread generator2 = new EventGeneratorThread("EventGenerator2", generator2Listeners);
		EventGeneratorThread generator3 = new EventGeneratorThread("EventGenerator3", generator3Listeners);
		EventGeneratorThread generator4 = new EventGeneratorThread("EventGenerator4", generator4Listeners);
		EventGeneratorThread generator5 = new EventGeneratorThread("EventGenerator5", generator5Listeners);
		EventGeneratorThread generator6 = new EventGeneratorThread("EventGenerator6", generator6Listeners);
		
		List<EventGeneratorThread> generatorThreads = new ArrayList<EventGeneratorThread>();
		generatorThreads.add(generator1);
		generatorThreads.add(generator2);
		generatorThreads.add(generator3);
		generatorThreads.add(generator4);
		generatorThreads.add(generator5);
		generatorThreads.add(generator6);
		
		for (EventGeneratorThread egt : generatorThreads) {
			egt.start();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		ListeningObjectsThread listeningObjectsThread = new ListeningObjectsThread(generatorThreads);
		listeningObjectsThread.start();
	}
}