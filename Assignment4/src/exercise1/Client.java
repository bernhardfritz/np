package exercise1;

import java.net.*;
import java.io.*;

// Acts as consumer of messages from Server
// aka Thread 4
/**
 * Receives numbers from Server until it is interrupted by an Object which is instance of Main.
 * The disableRestoration method with the "this" reference of the caller has to be called to successfully interrupt this Thread.
 * If the disableRestoration method was not called or the "this" reference of the caller does not match Main the interrupt will be restored and the Thread will not stop. 
 */
public class Client extends Thread{
	private Socket clientSocket;
	private BufferedReader in;
	private final String hostname = "localhost";
	private final int port = 9090;
	private boolean restore = true;

	public Client() {}

	private void receive() {
		try {
			String line = in.readLine();
			if(line == null) return;
			System.out.println("Thread 4 received " + line);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			in.close();
			clientSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Call this method with the "this" reference of the Object you are calling it from.
	 * If the "this" reference is instance of Main you will be able to interrupt this Thread successfully afterwards.
	 * @param o The "this" reference of the caller
	 */
	public void disableRestoration(Object o) {
		if(o instanceof Main) restore = false;
	}

	public void run() {
		try {
			clientSocket = new Socket(hostname, port);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
			try {
				receive();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				if(restore) {
					interrupted();
				} else {
					break;
				}
			}
		}
		
		close();
	}
}