package exercise1;

import java.util.ArrayList;
import java.util.List;

public class EventGeneratorThread extends Thread implements EventSource {
	private static ThreadLocal<List<EventListener>> listeners = new ThreadLocal<List<EventListener>>() {
		@Override
		public List<EventListener> initialValue() {
			System.out.println("Created for " + Thread.currentThread().getName());
			return new ArrayList<EventListener>();
		}
	};
	
	public static List<EventListener> get() {
//		List<EventListener> list = listeners.get();
//		if (list == null) {
//			list = new ArrayList<EventListener>();
//			listeners.set(list);
//		}
//		return list;
		return listeners.get();
	}
	
	public EventGeneratorThread(String name) {
		super(name);
	}

	@Override
	public void registerListener(EventListener e) {
		System.out.println(Thread.currentThread().getName() + ": " + EventGeneratorThread.get());
		listeners.get().add(e);
		System.out.println(Thread.currentThread().getName() + ": " + EventGeneratorThread.get());
	}
	
	@Override
	public void run() {
		
		while(true) {
			for (EventListener el : listeners.get()) {
				el.onEvent(new MyEvent(getName()));
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void infoListeningObjects() {
		System.out.println("[INFO] Listening objects from " + getName() + ":");
		for (EventListener el : listeners.get()) {
			System.out.println(el.getName());
		}
	}
}