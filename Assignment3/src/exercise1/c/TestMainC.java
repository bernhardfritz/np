package exercise1.c;

import java.util.ArrayList;
import java.util.List;

import exercise1.EventGeneratorThread;
import exercise1.ListeningObjectsThread;
import exercise1.MySafeListener;

public class TestMainC {

	public static void main(String[] args) {
		MySafeListener listener1 = MySafeListener.newInstance("Listener1");
		MySafeListener listener2 = MySafeListener.newInstance("Listener2");
		
		List<MySafeListener> generator1Listeners = new ArrayList<MySafeListener>();
		generator1Listeners.add(listener1);
		
		List<MySafeListener> generator2Listeners = new ArrayList<MySafeListener>();
		generator2Listeners.add(listener2);
		
		EventGeneratorThread generator1 = new EventGeneratorThread("EventGenerator1", generator1Listeners);
		EventGeneratorThread generator2 = new EventGeneratorThread("EventGenerator2", generator2Listeners);
		
		List<EventGeneratorThread> generatorThreads = new ArrayList<EventGeneratorThread>();
		generatorThreads.add(generator1);
		generatorThreads.add(generator2);
		
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