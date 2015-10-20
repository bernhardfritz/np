package exercise4;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
	private List<Integer> bufferList;
	
	public Buffer() {
		this.bufferList = new ArrayList<Integer>();
	}

	public synchronized void put(Integer i) {
		bufferList.add(i);
	}
	
	public synchronized Integer get() {
		if (bufferList.isEmpty()) {
			return null;
		}
		
		return bufferList.remove(0);
	}
}