package exercise1;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
	private String name;
	private List<Integer> bufferList;	
	
	public Buffer(String name) {
		this.name = name;
		this.bufferList = new ArrayList<Integer>();
	}

	public synchronized void put(Integer i) {
		bufferList.add(i);
		Log.add(Thread.currentThread().getName() + " produced " + i + " - " + this);
	}
	
	public synchronized Integer get() {
		Integer tmp = bufferList.remove(0);
		Log.add(Thread.currentThread().getName() + " consumes " + tmp + " - " + this);
		return tmp;
	}
	
	public synchronized Integer peek() {
		Log.add(Thread.currentThread().getName() + " peeked " + name);
		
		if (bufferList.isEmpty()) {
			return null;
		}
		
		Integer ret = bufferList.get(0);
		return ret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public synchronized String toString() {
		return name + ": " + bufferList;
	}
}