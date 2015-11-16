package exercise1;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
	String name;
	private List<Integer> bufferList;
	
	public Buffer(String name) {
		this.name = name;
		this.bufferList = new ArrayList<Integer>();
	}

	public synchronized void put(Integer i) {
		bufferList.add(i);
		Log.add(Thread.currentThread().getName() + " produced " + i + " - " + this);
		notify();
	}
	
	public synchronized Integer get() {
		Integer tmp = bufferList.remove(0);
		Log.add(Thread.currentThread().getName() + " consumes " + tmp + " - " + this);
		return tmp;
	}
	
	public synchronized Integer peek() {
		if(bufferList.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Integer ret = bufferList.get(0);
		Log.add(Thread.currentThread().getName() + " peeked " + name);
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