package projet_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class ServAuth {

	ServerSocket sockEcoute;  
	public ServAuth(){

		 
		try { 
			sockEcoute = new ServerSocket(13214); 
		} 
		catch(IOException ioe) { 
			System.out.println("Erreur de creation du server socket: " + ioe.getMessage()); 
			return; 
		}
	}

	public void Service(){
		Socket sockService; // Declaration du socket de service

		GestionProtoAuth gp = new GestionProtoAuth();  
		while(true) {

			try {
				sockService = sockEcoute.accept(); 
				ThreadTCPAuth th = new ThreadTCPAuth(sockService,gp);
				th.start();
			}
			catch(IOException ioe) 
			{
				System.out.println("Erreur d ecriture : " + ioe.getMessage());
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServAuth srv = new ServAuth();
		srv.Service();
	}
}



