package projet_java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadEcoute extends Thread {

	private ServerSocket sockEcoute;

	public ThreadEcoute(ServerSocket sockEcoute) {
		super();
		this.sockEcoute = sockEcoute;
	}
public void run() {
		while(true){
			try {
				Socket sockService = sockEcoute.accept(); 
				ThreadService th   = new ThreadService(sockService);
				th.start();
				}
		
			catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	}
}
	


