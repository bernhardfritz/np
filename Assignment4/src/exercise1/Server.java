package exercise1;

import java.net.*;
import java.io.*;

// Acts as consumer of filtered random numbers and writes them into a socket
// aka Thread 3
/**
 * Consumes numbers from a thread-safe Buffer object called "in" and sends them to a socket until done() is called. 
 */
public class Server extends Thread{
	private ServerSocket connectionSocket;
	private Socket serverSocket;
	private Buffer in;
	private PrintWriter out;
	private final int port = 9090;
	private boolean done = false;

	public Server(Buffer in) {
		this.in = in;
	}
	
	private void consume() {
		Integer number = in.get();
		if(number == null) return;
		send(number);
	}

	private void send(int number) {
		out.println(number);
	}

	private void close() {
		try {
			out.close();
			serverSocket.close();
			connectionSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Call this method to stop the Thread safely
	 */
	public void done() {
		done = true;
	}

	public void run() {
		try {
			connectionSocket = new ServerSocket(port);
			serverSocket = connectionSocket.accept();
			out = new PrintWriter(serverSocket.getOutputStream(), true);
		} catch(SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (!done) {
			consume();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		close();
	}
}