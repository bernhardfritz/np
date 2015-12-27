package exercise1;

import java.net.*;
import java.io.*;

// Acts as consumer of messages from Server
// aka Thread 4
public class Client extends Thread{
	private Socket clientSocket;
	private BufferedReader in;
	private final String hostname = "localhost";
	private final int port = 9090;

	public Client() {}

	public void receive() {
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

	public void destroy() {
		try {
			in.close();
			clientSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		while (!isInterrupted()) {
			receive();
		}
		
		destroy();
	}
}