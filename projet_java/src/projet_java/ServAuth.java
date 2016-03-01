//#####################################################################################
// Class qui permet de lancer le serveur Authentification										 
//#####################################################################################

package projet_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

//#####################################################################################
//Fonction qui permet d'écouter les messages que le client lui envoi									 
//#####################################################################################
public class ServAuth 
{

	ServerSocket sockEcoute;  
	public ServAuth(){

		 
		try 
		{
			// Creation du socket d'écoute du serveur Annuaire sur le port 13214
			sockEcoute = new ServerSocket(13214); 
		} 
		catch(IOException ioe) 
		{ 
			System.out.println("Erreur de creation du server socket: " + ioe.getMessage()); 
			return; 
		}
	}

	//#####################################################################################
	// Fonction qui lance la classe GestionProtoAuthentification pour traiter les messages du client									 
	//#####################################################################################
	public void Service()
	{
		// Declaration du socket de service
		Socket sockService; 

		GestionProtoAuth gp = new GestionProtoAuth();  
		while(true) 
		{

			try 
			{
				// Quand on reçoit un message on lance un thread qui va les traiter
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
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ServAuth srv = new ServAuth();
		srv.Service();
	}
}



