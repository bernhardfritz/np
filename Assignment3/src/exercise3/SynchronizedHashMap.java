package exercise3;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SynchronizedHashMap<T1, T2> implements Map<T1, T2> {
	Map<T1, T2> m = new HashMap<T1, T2>();
	
	@Override
	public synchronized int size() {
		return m.size();
	}

	@Override
	public synchronized boolean isEmpty() {
		return m.isEmpty();
	}

	@Override
	public synchronized boolean containsKey(Object key) {
		return m.containsKey(key);
	}

	@Override
	public synchronized boolean containsValue(Object value) {
		return m.containsValue(value);
	}

	@Override
	public synchronized T2 get(Object key) {
		return m.get(key);
	}

	@Override
	public synchronized T2 put(T1 key, T2 value) {
		return m.put(key, value);
	}

	@Override
	public synchronized T2 remove(Object key) {
		return m.remove(key);
	}

	@Override
	public synchronized void putAll(Map<? extends T1, ? extends T2> m) {
		this.m.putAll(m);
	}

	@Override
	public synchronized void clear() {
		m.clear();
	}

	@Override
	public synchronized Set<T1> keySet() {
		return m.keySet();
	}

	@Override
	public synchronized Collection<T2> values() {
		return m.values();
	}

	@Override
	public synchronized Set<java.util.Map.Entry<T1, T2>> entrySet() {
		return m.entrySet();
	}
}
