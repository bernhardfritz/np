package exercise3;

import java.util.Map;

public class Task implements Runnable, Cloneable{
	private Map<Integer, String> m;
	private Config config;
	private int insertOperations;
	private int readOperations;
	private int removeOperations;
	
	public Task(Map<Integer, String> m, Config config) {
		this.m = m;
		this.config = config;
		this.insertOperations = config.insertOperations;
		this.readOperations = config.readOperations;
		this.removeOperations = config.removeOperations;
	}
	
	private String dec2bin(int dec) {
		StringBuilder sb = new StringBuilder();
		while(dec > 0) {
			sb.append(dec % 2);
			dec /= 2;
		}
		return sb.reverse().toString();
	}
	
	private void insert(int i) {
		m.put(i, dec2bin(i));
	}
	
	private void read(int i) {
		m.get(i);
	}
	
	private void remove(int i) {
		m.remove(i);
	}
	
	public void run() {
		while((insertOperations + readOperations + removeOperations) > 0) {
			int r = (int)(Math.random() * 100);
			int choice = (int)(Math.random() * 3);
			switch(choice) {
				case 0: {
					if(insertOperations > 0) {
						insert(r);
						insertOperations--;
						break;
					} else {
						continue;
					}
				}
				case 1: {
					if(readOperations > 0) {
						read(r);
						readOperations--;
						break;
					} else {
						continue;
					}
				}
				case 2: {
					if(removeOperations > 0) {
						remove(r);
						removeOperations--;
						break;
					} else {
						continue;
					}
				}
			}
		}
	}
	
	
	
	public Map<Integer, String> getM() {
		return m;
	}

	public Task clone() {
		return new Task(m, config);
	}
}
