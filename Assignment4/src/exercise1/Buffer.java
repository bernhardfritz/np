package exercise1;

import java.util.ArrayList;
import java.util.List;

/**
 * Buffer is a FIFO thread-safe data structure.
 */
public class Buffer {
	private List<Integer> bufferList;
	
	public Buffer() {
		this.bufferList = new ArrayList<Integer>();
	}

	/**
	 * Puts Integer i into the buffer
	 * @param i The number to be put into the buffer
	 */
	public synchronized void put(Integer i) {
		bufferList.add(i);
		//System.out.println(Thread.currentThread().getName() + " added " + i + " to the buffer.");
		//print();
	}
	
	/**
	 * Removes the first element of the buffer and returns it 
	 * @return The first element of the buffer
	 */
	public synchronized Integer get() {
		if (bufferList.isEmpty()) {
			//System.out.println(Thread.currentThread().getName() + " tried to remove an element from the empty buffer.");
			//print();
			return null;
		}
		
		Integer ret = bufferList.remove(0);
		//System.out.println(Thread.currentThread().getName() + " removed " + ret + " from the buffer.");
		//print();
		return ret;
	}
	
	/**
	 * Prints the content of the buffer
	 */
	public void print() {
		System.out.println("Buffer: " + bufferList + "\n");
	}
}