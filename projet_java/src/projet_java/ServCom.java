//#####################################################################################
// Class qui permet de lancer le serveur de communication										 
//#####################################################################################

package projet_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

//#####################################################################################
//Fonction qui permet d'écouter les messages que le client lui envoi									 
//#####################################################################################
public class ServCom {

	ServerSocket sockEcoute;  
	public ServCom(){

		try { 
			// Creation du socket d'écoute du serveur de communication sur le port 15000
			sockEcoute = new ServerSocket(15000); 
		} 
		catch(IOException ioe) { 
			System.out.println("Erreur de creation du server socket: " + ioe.getMessage()); 
			return; 
		}
	}

	//#####################################################################################
	// Fonction qui lance la classe GestionProtoCom pour traiter les messages du client									 
	//#####################################################################################
	public void Service(){
		Socket sockService; 

		GestionProtoCom gpcom = new GestionProtoCom();  
		while(true) {

			try {
				// Quand on reçoit un message on lance un thread qui va les traiter
				sockService = sockEcoute.accept(); 
				ThreadTCPCom th = new ThreadTCPCom(sockService,gpcom);
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
		ServCom srv = new ServCom();
		srv.Service();
	}
}




