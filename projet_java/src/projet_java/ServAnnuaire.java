package projet_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServAnnuaire {

	ServerSocket sockEcoute;  
	public ServAnnuaire(){

		try { 
			sockEcoute = new ServerSocket(13215); 
		} 
		catch(IOException ioe) { 
			System.out.println("Erreur de creation du server socket: " + ioe.getMessage()); 
			return; 
		}
	}

	public void Service(){
		Socket sockService; 

		GestionProtoAnnuaire gpannuaire = new GestionProtoAnnuaire();  
		while(true) {

			try {
				sockService = sockEcoute.accept(); 
				ThreadTCPAnnuaire th = new ThreadTCPAnnuaire(sockService,gpannuaire);
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
		ServAnnuaire srv = new ServAnnuaire();
		srv.Service();
	}
}



