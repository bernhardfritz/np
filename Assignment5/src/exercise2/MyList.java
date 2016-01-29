package exercise2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<V> implements Iterable<V> {
	
	private List<V> list;
	
	public MyList() {
		this.list = new ArrayList<V>();
	}
	
	public MyList(MyList<V> list) {
		this.list = new ArrayList<V>();
		for (V v : list) {
			this.list.add(v);
		}
	}
	
	public synchronized void add(V element) {
		list.add(element);
	}
	
	public synchronized V get(int index) {
		return list.get(index);
	}
	
	public synchronized int size() {
		return list.size();
	}
	
	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}
	
	public synchronized void clear() {
		list.clear();
	}
	
	@Override
	public synchronized Iterator<V> iterator() {
		return list.iterator();
	}
}