//#####################################################################################
// Class qui permet de lancer le serveur Annuaire										 
//#####################################################################################

package projet_java;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

//#####################################################################################
// Fonction qui permet d'écouter les messages que le client lui envoi									 
//#####################################################################################
public class ServAnnuaire {

	// Declaration du socket de service
	ServerSocket sockEcoute;
	
	public ServAnnuaire(){

		try 
		{ 
			// Creation du socket d'écoute du serveur Annuaire sur le port 13215
			sockEcoute = new ServerSocket(13215); 
		} 
		catch(IOException ioe) 
		{ 
			System.out.println("Erreur de creation du server socket: " + ioe.getMessage()); 
			return; 
		}
	}

	//#####################################################################################
	// Fonction qui lance la classe GestionProtoAnnuaire pour traiter les messages du client									 
	//#####################################################################################
	public void Service()
	{
		// Declaration du socket de service
		Socket sockService; 

		GestionProtoAnnuaire gpannuaire = new GestionProtoAnnuaire();  
		while(true) 
		{

			try 
			{
				// Quand on reçoit un message on lance un thread qui va les traiter
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
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ServAnnuaire srv = new ServAnnuaire();
		srv.Service();
	}
}



