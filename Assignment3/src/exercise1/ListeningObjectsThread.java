package exercise1;

import java.util.List;

public class ListeningObjectsThread extends Thread {
	
	private List<EventGeneratorThread> generatorThreads;
	
	public ListeningObjectsThread(List<EventGeneratorThread> generatorThreads) {
		this.generatorThreads = generatorThreads;
	}

	@Override
	public void run() {
		while(true) {
			for (EventGeneratorThread egt : generatorThreads) {
				egt.interrupt();
			}
			
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}