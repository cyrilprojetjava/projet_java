//#####################################################################################
// Class Thread qui permet la connexion de plusieurs utilisateurs sur le serveur annuaire										 
//#####################################################################################

package projet_java;

import com.mysql.jdbc.Blob;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;

public class ThreadTCPAnnuaire extends Thread{

	private Socket sockService;
	private GestionProtoAnnuaire gpannuaire;

	
//#####################################################################################
// Constructeur qui associe un socket de service et la classe GESTIOPROTO du serveur annuaire a chaque client										 
//#####################################################################################
public ThreadTCPAnnuaire(Socket sockService, GestionProtoAnnuaire gpannuaire) {
		super();
		this.sockService = sockService;
		this.gpannuaire = gpannuaire;
	}



//#####################################################################################
//Fonction qui lance la classe GestionProto pour annalyser le message du client										 
//#####################################################################################
public void run() {
		
		BufferedReader reader;
		try {
			reader = new BufferedReader (new InputStreamReader(sockService.getInputStream()));
			PrintStream pStream = new PrintStream(sockService.getOutputStream());
			while(true){
				// On lit les informations que le client nous envoit tant qu'il est différent de "null"
				String requete = reader.readLine();
				if (requete == null)
				{
					break;
				}
				// On traite le message du client dans la fonction analyser traiter de la classe gestion proto auth
				String reponse = gpannuaire.analyserTraiter(requete);
				
				// On envoi la réponse de la fonction au client
				pStream.println(reponse);
			}
			
			sockService.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
}
