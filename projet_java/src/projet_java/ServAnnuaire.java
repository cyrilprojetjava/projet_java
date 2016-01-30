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

	ServerSocket sockEcoute;  // Déclaration du ServerSocket 
	
	//constructeur par défaut sans paramètre
	public ServAnnuaire(){

		//  Instanciation du ServerSocket en utilisant le constr. le plus simple (choix port) 
		try { 
			sockEcoute = new ServerSocket(13215); 
		} 
		catch(IOException ioe) { 
			System.out.println("Erreur de création du server socket: " + ioe.getMessage()); 
			return; 
		}
	}

	public void Service(){
		Socket sockService; // Declaration du socket de service

		// On appelle accept() sur le ServerSocket pour accepter les connections, // quand une connexion est reçue, un nouvel objet de la classe Socket est // renvoyé
		GestionProtoAnnuaire gpa = new GestionProtoAnnuaire();  
		while(true) {

			try {
				sockService = sockEcoute.accept(); 
				ThreadTCP th = new ThreadTCP(sockService,gpa);
				th.start();
			}
			catch(IOException ioe) 
			{
				System.out.println("Erreur d’écriture : " + ioe.getMessage());
			}
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServAnnuaire srv = new ServAnnuaire();
		srv.Service();
	}
}



