package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadService extends Thread {

	private Socket sockService;

	public ThreadService(Socket sockService) {
		super();
		this.sockService = sockService;
	}
public void run() {
			try {
				BufferedReader  fluxEntreeSocket;
				fluxEntreeSocket = new BufferedReader(new InputStreamReader(sockService.getInputStream()));
				String message;
				while(true){
					message = fluxEntreeSocket.readLine();
					if (message == null) break;
					System.out.println(message);
				}
			}
		
			catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	}
	


