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
		System.out.println(Thread.currentThread().getName() + " added " + i + " to the buffer.");
		print();
	}
	
	public synchronized Integer get() {
		if (bufferList.isEmpty()) {
			System.out.println(Thread.currentThread().getName() + " tried to remove an element from the empty buffer.");
			print();
			return null;
		}
		
		Integer ret = bufferList.remove(0);
		System.out.println(Thread.currentThread().getName() + " removed " + ret + " from the buffer.");
		print();
		return ret;
	}
	
	public void print() {
		System.out.println("Buffer: " + bufferList + "\n");
	}
}