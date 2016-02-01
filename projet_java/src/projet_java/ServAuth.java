package projet_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServAuth {

	ServerSocket sockEcoute;  // DÃ©claration du ServerSocket 
	
	//constructeur par dÃ©faut sans paramÃ¨tre
	public ServAuth(){

		//  Instanciation du ServerSocket en utilisant le constr. le plus simple (choix port) 
		try { 
			sockEcoute = new ServerSocket(13214); 
		} 
		catch(IOException ioe) { 
			System.out.println("Erreur de crÃ©ation du server socket: " + ioe.getMessage()); 
			return; 
		}
	}

	public void Service(){
		Socket sockService; // Declaration du socket de service

		// On appelle accept() sur le ServerSocket pour accepter les connections, // quand une connexion est recÌ§ue, un nouvel objet de la classe Socket est // renvoyeÌ�
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



