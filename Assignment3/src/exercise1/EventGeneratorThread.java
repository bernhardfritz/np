package exercise1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventGeneratorThread extends Thread implements EventSource {
	private static ThreadLocal<List<EventListener>> listeners = new ThreadLocal<List<EventListener>>() {
		@Override
		public List<EventListener> initialValue() {
			return new ArrayList<EventListener>();
		}
	};
	
	private List<MySafeListener> tempList;
	
	public EventGeneratorThread(String name, List<MySafeListener> listenerList) {
		super(name);
		this.tempList = new ArrayList<MySafeListener>(listenerList);
	}

	@Override
	public void registerListener(EventListener e) {
		listeners.get().add(e);
	}
	
	@Override
	public void run() {
		for (MySafeListener msl : tempList) {
			MySafeListener.register(msl, this);
		}
		
		while(true) {
			for (EventListener el : listeners.get()) {
				final Event event = new MyEvent(getName());
				el.onEvent(event);
			}
			
			if (interrupted()) {
				infoListeningObjects();
			}
			
			Long start = System.currentTimeMillis();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				infoListeningObjects();
				
				Long toSleep = 3000 - (System.currentTimeMillis() - start);
				try {
					Thread.sleep(Math.max(0, toSleep));
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void infoListeningObjects() {
		StringBuilder sb = new StringBuilder();
		sb.append("[INFO] Listening objects from " + getName() + ":");
		for (EventListener el : listeners.get()) {
			sb.append(" " + el.getName());
		}
		sb.append(" - " + new Date());
		System.out.println(sb.toString());
	}
}