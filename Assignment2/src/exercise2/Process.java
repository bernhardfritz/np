package exercise2;

import java.io.*; 
import java.util.*;
import java.util.concurrent.CountDownLatch;


// S.Ostermann

// a receive object 
// stores the message received by another process
// and is used for synchronization
class Receive { 
	String message = null;
}


public class Process extends Thread {

	int pid; // process id
	Process[] p; // references to all other processes
	Receive[] receive; // the receive objects
	
	CountDownLatch startLatch;
	CountDownLatch receiveLatch;

	Process(int pid, int n, CountDownLatch startLatch, CountDownLatch receiveLatch) {
		this.pid = pid;
		this.startLatch = startLatch;
		this.receiveLatch = receiveLatch;
		
		if (this.pid == 0) { // p0 creates the vector of references
			this.p = new Process[n];
			this.p[0] = this;
		}
		
		receive = new Receive[n]; 
		for (int i = 0; i < n; i++) {
			receive[i] = new Receive();
		}
	}

	// remote, called by p0, at all others, to transfer the reference vector
	public synchronized void neighbours(Process[] p) { 
		this.p = p;
		this.notify(); 
	}

	// remote, called by others, at p0, to send their reference to p0
	public synchronized void register(Process p, int pid) { 
		this.p[pid] = p; // p0 stores references in the vector
		System.out.println("p" + pid + " registered");
	}

	// called by sender
	public void sendMessage(String m, int pid) {
		p[this.pid].receive[pid].message = m;
	}	

	// called by a process to receive a message from another process
	public String receiveMessage(int i) {
		return receive[i].message;
	} 

	// the activity of a process
	public void run() {
		String m;

		if (pid == 0) {
//			try{
//				Thread.sleep(3000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			try {
				startLatch.await();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			int c = 0;
			for (Process pro : p) {
				if (pro == null) {
					c++;
				}
			}
			System.out.println("asdf: " + c);

			// p0 sends the reference vector to the others
			p[pid] = (Process)this;
			for (int i = 1; i < p.length; i++) {
				try {   
					p[i].neighbours(p);
				} catch (Exception e) {
					System.err.println("init exception: ");
					e.printStackTrace();
				}
			}
		}
		else {
			// wait until they got the reference vector
			synchronized (this) {
				try {
					startLatch.countDown();
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// send hello to every other process
		for (int i = 0; i < p.length; i++) {
			if (i != pid)  {
				try {
					p[i].sendMessage("hello p" + i + ", this is p" + pid, pid);
				} catch (Exception e) {
					System.err.println("send exception: ");
				}
				
				System.out.println("p" + this.pid + " sent hello to p" + i);
			}
		}
		
		receiveLatch.countDown();
		try {
			receiveLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// receive message from every other one
		for (int i = p.length-1; i >= 0; i--) {
//		for (int i = 0; i < p.length; i++) {
			if (i != pid)  {
				System.out.println("p" + this.pid + " receiving from p" + i + "...");
				m = receiveMessage(i);
				System.out.println("p" + this.pid + " received from p" + i + ": " + m);
			}
		}
	}

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);

		System.out.println("number of processes: " + n);

		CountDownLatch startLatch = new CountDownLatch(n-1);
		CountDownLatch receiveLatch = new CountDownLatch(n);
		
		Process one = new Process(0, n, startLatch, receiveLatch);
		one.start();

		for (int i = 1; i < n; i++) {
			Process temp = new Process(i, n, startLatch, receiveLatch);
			one.register(temp, i);
			temp.start();
		}
	}
}